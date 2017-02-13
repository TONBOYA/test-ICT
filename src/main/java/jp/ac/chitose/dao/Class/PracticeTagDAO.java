package jp.ac.chitose.dao.Class;

import java.sql.SQLException;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Query;

import com.google.inject.Inject;

import jp.ac.chitose.IDBCP;
import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.bean.PracticeTagBean;
import jp.ac.chitose.dao.Interface.IPracticeTagDAO;

public class PracticeTagDAO implements IPracticeTagDAO {

	@Inject
	IDBCP dbcp;

	@Override
	public boolean registerPracticeTag(PracticeRegisterBean practiceBean) {
		String insertPracticeTag = "INSERT INTO practice_tag (practice_id,tag,category) VALUES (:practiceId,:tag,:category);";
		try (Connection con = dbcp.getSql2o().beginTransaction()) {
			if (!practiceBean.getTag().isEmpty()) {
				Query practiceTag = con.createQuery(insertPracticeTag);
				practiceBean.getTag().stream().forEach(tag -> {
					practiceTag.addParameter("practiceId", practiceBean.getPracticeId())
							   .addParameter("tag", tag)
							   .addParameter("category", practiceBean.getCategory()).executeUpdate();
				});}
			con.commit();
			return true;
			} catch (SQLException e) {
			e.printStackTrace();
			return false;}}

	@Override
	public List<PracticeTagBean> selectPracticeTagList(int practiceId) {
		try (Connection con = dbcp.getSql2o().open()){
			String sql = "select * from practice_tag where practice_id = :id";
			return con.createQuery(sql).addParameter("id", practiceId).setAutoDeriveColumnNames(true).executeAndFetch(PracticeTagBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<PracticeTagBean> selectCategoryTagList(String category) {
		try (Connection con = dbcp.getSql2o().open()){
			String sql = "select * from practice_tag where category = :category";
			return con.createQuery(sql).addParameter("category", category).setAutoDeriveColumnNames(true).executeAndFetch(PracticeTagBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<PracticeTagBean> selectAllTagList() {
		try (Connection con = dbcp.getSql2o().open()){
			String sql = "select * from practice_tag";
			return con.createQuery(sql).setAutoDeriveColumnNames(true).executeAndFetch(PracticeTagBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deletePracticeTag(int practiceId) {
		try(Connection con = dbcp.getSql2o().open()){
			String sql = "delete from practice_tag where practice_id = :practiceId";
			con.createQuery(sql).addParameter("practiceId", practiceId).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}