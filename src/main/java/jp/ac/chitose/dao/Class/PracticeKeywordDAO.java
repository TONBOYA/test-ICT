package jp.ac.chitose.dao.Class;

import java.sql.SQLException;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Query;

import com.google.inject.Inject;

import jp.ac.chitose.IDBCP;
import jp.ac.chitose.bean.PracticeKeywordBean;
import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.dao.Interface.IPracticeKeywordDAO;

public class PracticeKeywordDAO implements IPracticeKeywordDAO {

	@Inject
	IDBCP dbcp;

	@Override
	public boolean registerPracticeKeyword(PracticeRegisterBean practiceBean) {
		String insertPracticeKeyword = "INSERT INTO practice_keyword (practice_id,keyword) VALUES (:practiceId,:keyword);";
		try (Connection con = dbcp.getSql2o().beginTransaction()) {
			if (!practiceBean.getKeyword().isEmpty()) {
				Query practiceKeyword = con.createQuery(insertPracticeKeyword);
				practiceBean.getKeyword().stream().forEach(keyword -> {
					practiceKeyword.addParameter("practiceId", practiceBean.getPracticeId())
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
	public List<PracticeKeywordBean> selectPracticeKeywordList(int practiceId) {
		try (Connection con = dbcp.getSql2o().open()){
			String sql = "select * from practice_keyword where practice_id = :id";
			return con.createQuery(sql).addParameter("id", practiceId).setAutoDeriveColumnNames(true).executeAndFetch(PracticeKeywordBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<PracticeKeywordBean> selectAllKeywordList() {
		try (Connection con = dbcp.getSql2o().open()){
			String sql = "select * from practice_keyword";
			return con.createQuery(sql).setAutoDeriveColumnNames(true).executeAndFetch(PracticeKeywordBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public boolean deletePracticeKeyword(int practiceId) {
		try(Connection con = dbcp.getSql2o().open()){
			String sql = "delete from practice_keyword where practice_id = :practiceId";
			con.createQuery(sql).addParameter("practiceId", practiceId).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
