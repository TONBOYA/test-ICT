package jp.ac.chitose.dao.Class;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Query;

import com.google.inject.Inject;

import jp.ac.chitose.IDBCP;
import jp.ac.chitose.bean.PracticeImageBean;
import jp.ac.chitose.bean.PracticeKeywordBean;
import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.bean.UnfoldingBean;
import jp.ac.chitose.bean.UnfoldingFormBean;
import jp.ac.chitose.dao.Interface.IPracticeTempDAO;

public class PracticeTempDAO implements IPracticeTempDAO{

	@Inject
	IDBCP dbcp;

	@Override
	public boolean registerTempPractice(PracticeRegisterBean practiceBean) {

		String insertPractice = "INSERT INTO temp_practice (name,summary,aim,challenge,reaction,start_on,account_id,practice_hierarchy_id) VALUES(:name,:summary,:aim,:challenge,:reaction,:startOn,:accountId,:practiceHierarchyId) RETURNING temp_practice_id;";
		String insertUnfolding = "INSERT INTO temp_unfolding (temp_practice_id,ordinal,name,required_minute,instruction,special_instruction) VALUES(:tempPracticeId,:ordinal,:name,:requiredMinute,:instruction,:specialInstruction) RETURNING temp_unfolding_id;";
		String insertImage = "INSERT INTO temp_practice_image (temp_unfolding_id,file_name,ordinal) VALUES (:tempUnfoldingId,:fileName,:ordinal);";
		String insertPracticeKeyword = "INSERT INTO temp_practice_keyword (temp_practice_id,keyword) VALUES (:tempPracticeId,:keyword);";

		java.util.Date utilDate = practiceBean.getStartOn();
		DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		String today = format.format(utilDate);
		try {
			utilDate = format.parse(today);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		java.sql.Date sqlToday = new java.sql.Date(utilDate.getTime());

		try (Connection con = dbcp.getSql2o().beginTransaction()) {

			practiceBean.setPracticeId(con.createQuery(insertPractice)
					.addParameter("name", practiceBean.getName())
					.addParameter("summary", practiceBean.getSummary())
					.addParameter("aim", practiceBean.getAim())
					.addParameter("challenge", practiceBean.getChallenge())
					.addParameter("reaction", practiceBean.getReaction())
					.addParameter("startOn", sqlToday)
					.addParameter("practiceHierarchyId", practiceBean.getPracticeHierarchyId())
					.addParameter("accountId", practiceBean.getAccountId())
					.executeAndFetchFirst(Integer.class));

			if (!practiceBean.getKeyword().isEmpty()) {

				Query practiceKeyword = con.createQuery(insertPracticeKeyword);
				practiceBean.getKeyword().stream().forEach(keyword -> {
					practiceKeyword.addParameter("tempPracticeId", practiceBean.getPracticeId())
								   .addParameter("keyword", keyword).executeUpdate();
				});
			}

			if (!practiceBean.getUnfoldingList().isEmpty()) {
				Query unfoldings = con.createQuery(insertUnfolding);
				practiceBean.getUnfoldingList().stream().forEach(unfolding -> {
					unfolding.setUnfoldingId(unfoldings.addParameter("tempPracticeId", practiceBean.getPracticeId())
							.addParameter("ordinal", unfolding.getOrdinal())
							.addParameter("name", unfolding.getName())
							.addParameter("requiredMinute", unfolding.getRequiredMinute())
							.addParameter("instruction", unfolding.getInstruction())
							.addParameter("specialInstruction", unfolding.getSpecialInstruction())
							.executeAndFetchFirst(Integer.class));
					if (!unfolding.getFirstImage().getFileName().equals("")) {
						con.createQuery(insertImage).addParameter("tempUnfoldingId", unfolding.getUnfoldingId())
								.addParameter("fileName", unfolding.getFirstImage().getFileName())
								.addParameter("ordinal", 1).executeUpdate();
					}
					if (!unfolding.getSecondImage().getFileName().equals("")) {
						con.createQuery(insertImage).addParameter("tempUnfoldingId", unfolding.getUnfoldingId())
								.addParameter("fileName", unfolding.getSecondImage().getFileName())
								.addParameter("ordinal", 2).executeUpdate();
					}
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
			public boolean editTempPractice(PracticeRegisterBean practiceBean) {

				String updatePractice = "update temp_practice set name = :name,summary = :summary,aim = :aim,challenge = :challenge,reaction = :reaction,start_on = :startOn,practice_hierarchy_id = :practiceHierarchyId where temp_practice_id = :tempPracticeId";
				String updateUnfolding = "update temp_unfolding set name = :name, required_minute = :requiredMinute,instruction = :instruction, special_instruction = :specialInstruction where temp_unfolding_id = :tempUnfoldingId";
				String insertImage = "INSERT INTO temp_practice_image (temp_unfolding_id,file_name,ordinal) VALUES (:tempUnfoldingId,:fileName,:ordinal);";
				String updateImage = "update temp_practice_image set file_name = :fileName where temp_unfolding_id = :tempUnfoldingId and ordinal = :ordinal";
				String deletePracticeKeyword = "delete from temp_practice_keyword where temp_practice_id = :tempPracticeId";
				String insertPracticeKeyword = "INSERT INTO temp_practice_keyword (temp_practice_id,keyword) VALUES (:tempPracticeId,:keyword);";

				java.util.Date utilDate = practiceBean.getStartOn();
				DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
				String today = format.format(utilDate);
				try {
					utilDate = format.parse(today);
				} catch (ParseException e) {
					e.printStackTrace();
				}

				java.sql.Date sqlToday = new java.sql.Date(utilDate.getTime());

				try (Connection con = dbcp.getSql2o().beginTransaction()) {
					con.createQuery(updatePractice).addParameter("name", practiceBean.getName())
							.addParameter("summary", practiceBean.getSummary())
							.addParameter("aim", practiceBean.getAim())
							.addParameter("challenge", practiceBean.getChallenge())
							.addParameter("reaction", practiceBean.getReaction())
							.addParameter("startOn", sqlToday)
							.addParameter("practiceHierarchyId", practiceBean.getPracticeHierarchyId())
							.addParameter("tempPracticeId", practiceBean.getPracticeId())
							.executeUpdate();

					if (!practiceBean.getKeyword().isEmpty()) {

						con.createQuery(deletePracticeKeyword).addParameter("tempPracticeId", practiceBean.getPracticeId())
								.executeUpdate();

						Query practiceKeyword = con.createQuery(insertPracticeKeyword);
						practiceBean.getKeyword().stream().forEach(keyword -> {
							practiceKeyword.addParameter("tempPracticeId", practiceBean.getPracticeId())
										   .addParameter("keyword", keyword).executeUpdate();
						});
					}
					if (!practiceBean.getUnfoldingList().isEmpty()) {
						Query unfoldings = con.createQuery(updateUnfolding);
						practiceBean.getUnfoldingList().stream().forEach(unfolding -> {
							unfoldings.addParameter("name", unfolding.getName())
									  .addParameter("requiredMinute", unfolding.getRequiredMinute())
									  .addParameter("instruction", unfolding.getInstruction())
									  .addParameter("specialInstruction", unfolding.getSpecialInstruction())
									  .addParameter("tempUnfoldingId", unfolding.getUnfoldingId())
									  .executeUpdate();
							if (!unfolding.getFirstImage().getFileName().equals("")) {
								if (selectTempImage(unfolding.getFirstImage().getUnfoldingId(),
										unfolding.getFirstImage().getOrdinal()) != null) {
									con.createQuery(updateImage)
											.addParameter("fileName", unfolding.getFirstImage().getFileName())
											.addParameter("tempUnfoldingId", unfolding.getUnfoldingId())
											.addParameter("ordinal", 1)
											.executeUpdate();
								} else {
									con.createQuery(insertImage)
											.addParameter("fileName", unfolding.getFirstImage().getFileName())
											.addParameter("tempUnfoldingId", unfolding.getUnfoldingId())
											.addParameter("ordinal", 1)
											.executeUpdate();
								}
							}
							if (!unfolding.getSecondImage().getFileName().equals("")) {
								if (selectTempImage(unfolding.getSecondImage().getUnfoldingId(),
										unfolding.getSecondImage().getOrdinal()) != null) {
									con.createQuery(updateImage)
											.addParameter("fileName", unfolding.getSecondImage().getFileName())
											.addParameter("tempUnfoldingId", unfolding.getUnfoldingId()).addParameter("ordinal", 2)
											.executeUpdate();
								} else {
									con.createQuery(insertImage)
											.addParameter("fileName", unfolding.getSecondImage().getFileName())
											.addParameter("tempUnfoldingId", unfolding.getUnfoldingId()).addParameter("ordinal", 2)
											.executeUpdate();
								}
							}
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
	public boolean deleteTempPractice(int practiceId) {
		try(Connection con = dbcp.getSql2o().open()){
			String sql = "DELETE from temp_practice where temp_practice_id = :tempPracticeId";
			con.createQuery(sql).addParameter("tempPracticeId", practiceId).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<UnfoldingBean> selectTempUnfoldings(int practiceId) {
		try(Connection con = dbcp.getSql2o().open()){
			String sql = "select * from temp_unfolding where temp_practice_id = :id order by ordinal";
			return con.createQuery(sql).addParameter("id", practiceId).setAutoDeriveColumnNames(true).executeAndFetch(UnfoldingBean.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public UnfoldingFormBean selectTempUnfolding(int practiceId, int ordinal) {
		try(Connection con = dbcp.getSql2o().open()) {
			String sql = "select * from temp_unfolding where temp_practice_id = :id and ordinal = :ordinal";
			return con.createQuery(sql).addParameter("id", practiceId).addParameter("ordinal", ordinal).setAutoDeriveColumnNames(true).executeAndFetchFirst(UnfoldingFormBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public boolean deleteTempUnfolding(int practiceId) {
		try(Connection con = dbcp.getSql2o().open()){
			String sql = "DELETE from temp_unfolding where temp_practice_id = :tempPracticeId";
			con.createQuery(sql).addParameter("practiceId", practiceId).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<PracticeKeywordBean> selectTempPracticeKeywordList(int practiceId) {
		try (Connection con = dbcp.getSql2o().open()){
			String sql = "select * from temp_practice_keyword where temp_practice_id = :id";
			return con.createQuery(sql).addParameter("id", practiceId).setAutoDeriveColumnNames(true).executeAndFetch(PracticeKeywordBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public boolean deleteTempPracticeKeyword(int practiceId) {
		try(Connection con = dbcp.getSql2o().open()){
			String sql = "delete from temp_practice_keyword where temp_practice_id = :tempPracticeId";
			con.createQuery(sql).addParameter("tempPracticeId", practiceId).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}


	@Override
	public List<PracticeImageBean> searchTempImage(int unfoldingId) {
		List<PracticeImageBean> imageList = new ArrayList<PracticeImageBean>();
		try(Connection con = dbcp.getSql2o().open()){
			String sql = "SELECT * from practice_image where unfolding_id = :unfoldingId";
			imageList = con.createQuery(sql).addParameter("unfoldingId", unfoldingId).setAutoDeriveColumnNames(true).executeAndFetch(PracticeImageBean.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return imageList;
	}

	@Override
	public PracticeImageBean selectTempImage(int unfoldingId, int ordinal) {
		PracticeImageBean image = new PracticeImageBean();
		try(Connection con = dbcp.getSql2o().open()){
			String sql = "SELECT * from temp_practice_image where temp_unfolding_id = :tempUnfoldingId and ordinal = :ordinal";
			image = con.createQuery(sql).addParameter("unfoldingId", unfoldingId).addParameter("ordinal", ordinal).setAutoDeriveColumnNames(true).executeAndFetchFirst(PracticeImageBean.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return image;
	}

	@Override
	public boolean deleteTempImage(int unfoldingId) {
		try(Connection con = dbcp.getSql2o().open()){
			String sql = "DELETE from practice_image where unfolding_id = :unfoldingId";
			con.createQuery(sql).addParameter("unfoldingId", unfoldingId).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
