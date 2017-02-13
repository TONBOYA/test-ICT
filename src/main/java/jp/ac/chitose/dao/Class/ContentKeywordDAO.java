package jp.ac.chitose.dao.Class;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Query;

import com.google.inject.Inject;

import jp.ac.chitose.IDBCP;
import jp.ac.chitose.bean.ContentKeywordBean;
import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.dao.Interface.IContentKeywordDAO;

public class ContentKeywordDAO implements IContentKeywordDAO {

	@Inject
	IDBCP dbcp;

	@Override
	public boolean registerContentKeyword(ContentPageBean contentBean) {
		try (Connection con = dbcp.getSql2o().beginTransaction()) {
			String insertContentKeyword = "INSERT INTO content_keyword (content_id,keyword) VALUES (:contentId,:keyword);";
			if (!contentBean.getKeyword().isEmpty()) {
				Query contentKeyword = con.createQuery(insertContentKeyword);
				contentBean.getKeyword().stream().forEach(keyword -> {
					contentKeyword.addParameter("contentId", contentBean.getContentId())
								   .addParameter("keyword", keyword).executeUpdate();
				});
			}
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<ContentKeywordBean> selectContentKeywordList(int contentId) {
		try (Connection con = dbcp.getSql2o().open()){
			String sql = "select * from content_keyword where content_id = :id";
			return con.createQuery(sql).addParameter("id", contentId).setAutoDeriveColumnNames(true).executeAndFetch(ContentKeywordBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ContentKeywordBean> searchContent(String keyword) {
		List<ContentKeywordBean> content = new ArrayList<ContentKeywordBean>();
		try (Connection con = dbcp.getSql2o().open()) {
			String sql = "select * from content_keyword where keyword = :" + keyword;
			content = con.createQuery(sql).addParameter("keyword", keyword)
					.setAutoDeriveColumnNames(true)
					.executeAndFetch(ContentKeywordBean.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return content;
	}

	@Override
	public List<ContentKeywordBean> selectAllKeywordList() {
		try (Connection con = dbcp.getSql2o().open()){
			String sql = "select * from content_keyword";
			return con.createQuery(sql).setAutoDeriveColumnNames(true).executeAndFetch(ContentKeywordBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteContentKeyword(int contentId) {
		try(Connection con = dbcp.getSql2o().open()){
			String sql = "delete from content_keyword where content_id = :contentId";
			con.createQuery(sql).addParameter("contentId", contentId).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
