package jp.ac.chitose.dao.Class;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Query;

import com.google.inject.Inject;

import jp.ac.chitose.IDBCP;
import jp.ac.chitose.bean.PracticeBean;
import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.dao.Interface.IPracticeDAO;

public class PracticeDAO implements IPracticeDAO {

	@Inject
	IDBCP dbcp;

	@Override
	public boolean registerPractice(PracticeRegisterBean practiceBean) {
		String insertPractice = "INSERT INTO practice (name,summary,aim,challenge,reaction,account_id,practice_hierarchy_id) VALUES(:name,:summary,:aim,:challenge,:reaction,:accountId,:practiceHierarchyId) RETURNING practice_id;";

		try (Connection con = dbcp.getSql2o().beginTransaction()) {
				practiceBean.setPracticeId(con.createQuery(insertPractice)
						.addParameter("name", practiceBean.getName())
						.addParameter("summary", practiceBean.getSummary())
						.addParameter("aim", practiceBean.getAim())
						.addParameter("challenge", practiceBean.getChallenge())
						.addParameter("reaction", practiceBean.getReaction())
						.addParameter("practiceHierarchyId", practiceBean.getPracticeHierarchyId())
						.addParameter("accountId", practiceBean.getAccountId())
						.executeAndFetchFirst(Integer.class));

			if(!(practiceBean.getContentId() == 0)){
				String insertPracticeContent = "INSERT INTO content_practice (practice_id,content_id) VALUES (:practiceId,:contentId);";
			Query practiceContent = con.createQuery(insertPracticeContent);
			practiceContent.addParameter("practiceId", practiceBean.getPracticeId())
						   .addParameter("contentId", practiceBean.getContentId()).executeUpdate();
			}
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean editPractice(PracticeRegisterBean practiceBean) {

		String updatePractice = "update practice set name = :name, summary = :summary, aim = :aim, challenge = :challenge, reaction = :reaction, practice_hierarchy_id = :practiceHierarchyId where practice_id = :practiceId";

		try (Connection con = dbcp.getSql2o().beginTransaction()) {
			con.createQuery(updatePractice).addParameter("name", practiceBean.getName())
			.addParameter("summary", practiceBean.getSummary())
			.addParameter("aim", practiceBean.getAim())
			.addParameter("challenge", practiceBean.getChallenge())
			.addParameter("reaction", practiceBean.getReaction())
			.addParameter("practiceHierarchyId", practiceBean.getPracticeHierarchyId())
			.addParameter("practiceId", practiceBean.getPracticeId())
			.executeUpdate();

			if(!(practiceBean.getContentId() == 0)){
				String sql = "delete from content_practice where practice_id = :practiceId";
				con.createQuery(sql).addParameter("practiceId", practiceBean.getPracticeId()).executeUpdate();
				String insertPracticeContent = "INSERT INTO content_practice (practice_id,content_id) VALUES (:practiceId,:contentId);";
			Query practiceContent = con.createQuery(insertPracticeContent);
			practiceContent.addParameter("practiceId", practiceBean.getPracticeId())
						   .addParameter("contentId", practiceBean.getContentId()).executeUpdate();
			}
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}


	@Override
	public PracticeBean selectPractice(int practiceId) {
		PracticeBean practice = new PracticeBean();
		try(Connection con = dbcp.getSql2o().open()){
			String sql = "select * from practice where practice_id = :id";
			practice = con.createQuery(sql)
					.addParameter("id", practiceId)
					.setAutoDeriveColumnNames(true)
					.executeAndFetchFirst(PracticeBean.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return practice;
	}

	@Override
	public PracticeBean searchOfPractice(int practiceId) {
		try(Connection con = dbcp.getSql2o().open()){
			String sql = "SELECT * FROM practice WHERE practice_id = :practiceId";
			PracticeBean practiceBean = con.createQuery(sql)
					.setAutoDeriveColumnNames(true)
					.addParameter("practiceId", practiceId)
					.executeAndFetchFirst(PracticeBean.class);
			return practiceBean;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}



	@Override
	public List<PracticeBean> selectPracticeOfHierarchy(int practiceHierarchyId) {
		List<PracticeBean> practiceOfHierachy = new ArrayList<PracticeBean>();
		try(Connection con = dbcp.getSql2o().open()){
			String sql = "SELECT * FROM practice WHERE practice_hierarchy_id = :practiceHierarchyId";
			practiceOfHierachy = con.createQuery(sql)
					.setAutoDeriveColumnNames(true)
					.addParameter("practiceHierarchyId", practiceHierarchyId)
					.executeAndFetch(PracticeBean.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return practiceOfHierachy;
	}

	@Override
	public boolean deletePractice(int practiceId) {
		try(Connection con = dbcp.getSql2o().open()){
			String sql = "DELETE from practice where practice_id = :practiceId";
			con.createQuery(sql).addParameter("practiceId", practiceId).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}



}
