package jp.ac.chitose.dao.Class;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.sql2o.Connection;

import com.google.inject.Inject;

import jp.ac.chitose.DBCP;
import jp.ac.chitose.bean.PracticeImageBean;
import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.dao.Interface.IPracticeImageDAO;

public class PracticeImageDAO implements IPracticeImageDAO {

	@Inject
	DBCP dbcp;

	@Override
	public boolean registerPracticeImage(PracticeRegisterBean practiceBean) {
		try (Connection con = dbcp.getSql2o().beginTransaction()) {
			String insertImage = "INSERT INTO practice_image (unfolding_id,file_name,ordinal) VALUES (:unfoldingId,:fileName,:ordinal);";
			practiceBean.getUnfoldingList().stream().forEach(unfolding -> {
				if (!unfolding.getFirstImage().getFileName().equals("")) {
					con.createQuery(insertImage).addParameter("unfoldingId", unfolding.getUnfoldingId())
							.addParameter("fileName", unfolding.getFirstImage().getFileName())
							.addParameter("ordinal", 1).executeUpdate();
				}
				if (!unfolding.getSecondImage().getFileName().equals("")) {
					con.createQuery(insertImage).addParameter("unfoldingId", unfolding.getUnfoldingId())
							.addParameter("fileName", unfolding.getSecondImage().getFileName())
							.addParameter("ordinal", 2).executeUpdate();
				}
			});
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean editPracticeImage(PracticeRegisterBean practiceBean) {
		try (Connection con = dbcp.getSql2o().beginTransaction()) {
			String insertImage = "INSERT INTO practice_image (unfolding_id,file_name,ordinal) VALUES (:unfoldingId,:fileName,:ordinal);";
			String updateImage = "update practice_image set file_name = :fileName where unfolding_id = :unfoldingId and ordinal = :ordinal";
			practiceBean.getUnfoldingList().stream().forEach(unfolding -> {
				if (!unfolding.getFirstImage().getFileName().equals("")) {
					if (selectImage(unfolding.getFirstImage().getUnfoldingId(),
							unfolding.getFirstImage().getOrdinal()) != null) {
						con.createQuery(updateImage)
								.addParameter("fileName", unfolding.getFirstImage().getFileName())
								.addParameter("unfoldingId", unfolding.getUnfoldingId())
								.addParameter("ordinal", 1)
								.executeUpdate();
					} else {
						con.createQuery(insertImage)
								.addParameter("fileName", unfolding.getFirstImage().getFileName())
								.addParameter("unfoldingId", unfolding.getUnfoldingId())
								.addParameter("ordinal", 1)
								.executeUpdate();
					}
				}
				if (!unfolding.getSecondImage().getFileName().equals("")) {
					if (selectImage(unfolding.getSecondImage().getUnfoldingId(),
							unfolding.getSecondImage().getOrdinal()) != null) {
						con.createQuery(updateImage)
								.addParameter("fileName", unfolding.getSecondImage().getFileName())
								.addParameter("unfoldingId", unfolding.getUnfoldingId())
								.addParameter("ordinal", 2)
								.executeUpdate();
					} else {
						con.createQuery(insertImage)
								.addParameter("fileName", unfolding.getSecondImage().getFileName())
								.addParameter("unfoldingId", unfolding.getUnfoldingId())
								.addParameter("ordinal", 2)
								.executeUpdate();
					}
				}
			});
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<PracticeImageBean> searchImage(int unfoldingId) {
		List<PracticeImageBean> sceneList = new ArrayList<PracticeImageBean>();

		try (Connection con = dbcp.getSql2o().open()) {
			String sql = "SELECT * from practice_image where unfolding_id = :unfoldingId";
			sceneList = con.createQuery(sql).addParameter("unfoldingId", unfoldingId).setAutoDeriveColumnNames(true)
					.executeAndFetch(PracticeImageBean.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return sceneList;
	}

	@Override
	public PracticeImageBean selectImage(int unfoldingId, int ordinal) {
		PracticeImageBean scene = new PracticeImageBean();

		try (Connection con = dbcp.getSql2o().open()) {
			String sql = "SELECT * from practice_image where unfolding_id = :unfoldingId and ordinal = :ordinal";
			scene = con.createQuery(sql).addParameter("unfoldingId", unfoldingId).addParameter("ordinal", ordinal)
					.setAutoDeriveColumnNames(true).executeAndFetchFirst(PracticeImageBean.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return scene;
	}

	@Override
	public boolean deleteImage(int unfoldingId) {
		try (Connection con = dbcp.getSql2o().open()) {
			String sql = "DELETE from practice_image where unfolding_id = :unfoldingId";
			con.createQuery(sql).addParameter("unfoldingId", unfoldingId).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
