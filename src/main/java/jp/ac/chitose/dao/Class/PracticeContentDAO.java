package jp.ac.chitose.dao.Class;

import java.sql.SQLException;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Query;

import com.google.inject.Inject;

import jp.ac.chitose.IDBCP;
import jp.ac.chitose.bean.PracticeContentBean;
import jp.ac.chitose.bean.PracticeContentPageBean;
import jp.ac.chitose.dao.Interface.IPracticeContentDAO;

public class PracticeContentDAO implements IPracticeContentDAO {

	@Inject
	IDBCP dbcp;

	@Override
	public boolean registerPracticeContent(PracticeContentPageBean bean) {
		String insertPracticeContent = "INSERT INTO practice_content (practice_id,contentid VALUES (:practiceId,:contentId);";
		try (Connection con = dbcp.getSql2o().beginTransaction()) {
			Query practiceContent = con.createQuery(insertPracticeContent);
			practiceContent.addParameter("practiceId", bean.getPracticeId())
						   .addParameter("contentId", bean.getContentId()).executeUpdate();
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public PracticeContentBean selectContentId(int practiceId) {
		PracticeContentBean practiceContent = new PracticeContentBean();
		try (Connection con = dbcp.getSql2o().open()){
			String sql = "select * from content_practice where practice_id = :id";
			return con.createQuery(sql).addParameter("id", practiceId).setAutoDeriveColumnNames(true)
					.executeAndFetchFirst(PracticeContentBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return practiceContent;
	}

	@Override
	public PracticeContentBean selectPracticeId(int contentId) {
		PracticeContentBean practiceContent = new PracticeContentBean();
		try (Connection con = dbcp.getSql2o().open()){
			String sql = "select * from content_practice where content_id = :id";
			return con.createQuery(sql).addParameter("id", contentId).setAutoDeriveColumnNames(true)
					.executeAndFetchFirst(PracticeContentBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return practiceContent;
	}

	@Override
	public List<PracticeContentBean> selectPressPractice(int contentId) {
		try (Connection con = dbcp.getSql2o().open()){
			String sql = "select * from content_practice where content_id = :id";
			return con.createQuery(sql).addParameter("id", contentId).setAutoDeriveColumnNames(true)
					.executeAndFetch(PracticeContentBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deletePracticeContent(int practiceId){
		try(Connection con = dbcp.getSql2o().open()){
			String sql = "delete from content_practice where practice_id = :practiceId";
			con.createQuery(sql).addParameter("practiceId", practiceId).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
