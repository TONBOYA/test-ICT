package jp.ac.chitose.dao.Class;

import java.sql.SQLException;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Query;

import com.google.inject.Inject;

import jp.ac.chitose.IDBCP;
import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.bean.ContentTagBean;
import jp.ac.chitose.dao.Interface.IContentTagDAO;

public class ContentTagDAO implements IContentTagDAO {

	@Inject
	IDBCP dbcp;

	@Override
	public boolean registerContentTag(ContentPageBean contentBean) {
		String insertContentTag = "INSERT INTO content_tag (content_id,tag,category) VALUES (:contentId,:tag,:category);";
		try (Connection con = dbcp.getSql2o().beginTransaction()) {
			if (!contentBean.getTag().isEmpty()) {
				Query contentTag = con.createQuery(insertContentTag);
				contentBean.getTag().stream().forEach(tag -> {
					contentTag.addParameter("contentId", contentBean.getContentId())
							   .addParameter("tag", tag)
							   .addParameter("category", contentBean.getCategory()).executeUpdate();
				});}
			con.commit();
			return true;
			} catch (SQLException e) {
			e.printStackTrace();
			return false;}}

	@Override
	public List<ContentTagBean> selectContentTagList(int contentId) {
		try (Connection con = dbcp.getSql2o().open()){
			String sql = "select * from content_tag where content_id = :id";
			return con.createQuery(sql).addParameter("id", contentId).setAutoDeriveColumnNames(true).executeAndFetch(ContentTagBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ContentTagBean> selectCategoryTagList(String category) {
		try (Connection con = dbcp.getSql2o().open()){
			String sql = "select * from content_tag where category = :category";
			return con.createQuery(sql).addParameter("category", category).setAutoDeriveColumnNames(true).executeAndFetch(ContentTagBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<ContentTagBean> selectAllTagList() {
		try (Connection con = dbcp.getSql2o().open()){
			String sql = "select * from content_tag";
			return con.createQuery(sql).setAutoDeriveColumnNames(true).executeAndFetch(ContentTagBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteContentTag(int contentId) {
		try(Connection con = dbcp.getSql2o().open()){
			String sql = "delete from content_tag where content_id = :contentId";
			con.createQuery(sql).addParameter("contentId", contentId).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}