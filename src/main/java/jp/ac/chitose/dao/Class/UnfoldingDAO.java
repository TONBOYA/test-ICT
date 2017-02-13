package jp.ac.chitose.dao.Class;

import java.sql.SQLException;
import java.util.List;

import org.sql2o.Connection;
import org.sql2o.Query;

import com.google.inject.Inject;

import jp.ac.chitose.IDBCP;
import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.bean.UnfoldingBean;
import jp.ac.chitose.bean.UnfoldingFormBean;
import jp.ac.chitose.dao.Interface.IUnfoldingDAO;

public class UnfoldingDAO implements IUnfoldingDAO {

	@Inject
	IDBCP dbcp;

	@Override
	public boolean registerUnfolding(PracticeRegisterBean practiceBean) {
		try (Connection con = dbcp.getSql2o().beginTransaction()) {
			String insertUnfolding = "INSERT INTO unfolding (practice_id,ordinal,name,required_minute,instruction,special_instruction) VALUES(:practiceId,:ordinal,:name,:requiredMinute,:instruction,:specialInstruction) RETURNING unfolding_id;";
			Query unfoldings = con.createQuery(insertUnfolding);
			practiceBean.getUnfoldingList().stream().forEach(unfolding -> {
				unfolding.setUnfoldingId(unfoldings.addParameter("practiceId", practiceBean.getPracticeId())
						.addParameter("ordinal", unfolding.getOrdinal()).addParameter("name", unfolding.getName())
						.addParameter("requiredMinute", unfolding.getRequiredMinute())
						.addParameter("instruction", unfolding.getInstruction())
						.addParameter("specialInstruction", unfolding.getSpecialInstruction())
						.executeAndFetchFirst(Integer.class));
			});
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean editUnfolding(PracticeRegisterBean practiceBean) {
		try (Connection con = dbcp.getSql2o().beginTransaction()) {
			String updateUnfolding = "update unfolding set name = :name, required_minute = :requiredMinute,instruction = :instruction, special_instruction = :specialInstruction where unfolding_id = :unfoldingId";
			Query unfoldings = con.createQuery(updateUnfolding);
			practiceBean.getUnfoldingList().stream().forEach(unfolding -> {
				unfoldings.addParameter("name", unfolding.getName())
						.addParameter("requiredMinute", unfolding.getRequiredMinute())
						.addParameter("instruction", unfolding.getInstruction())
						.addParameter("specialInstruction", unfolding.getSpecialInstruction())
						.addParameter("unfoldingId", unfolding.getUnfoldingId()).executeUpdate();
			});
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<UnfoldingBean> selectUnfoldings(int practiceId) {
		try (Connection con = dbcp.getSql2o().open()) {
			String sql = "select * from unfolding where practice_id = :id order by ordinal";
			return con.createQuery(sql).addParameter("id", practiceId).setAutoDeriveColumnNames(true)
					.executeAndFetch(UnfoldingBean.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public UnfoldingFormBean selectUnfolding(int practiceId, int ordinal) {
		try (Connection con = dbcp.getSql2o().open()) {
			String sql = "select * from unfolding where practice_id = :id and ordinal = :ordinal";
			return con.createQuery(sql).addParameter("id", practiceId).addParameter("ordinal", ordinal)
					.setAutoDeriveColumnNames(true).executeAndFetchFirst(UnfoldingFormBean.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean deleteUnfolding(int practiceId) {
		try (Connection con = dbcp.getSql2o().open()) {
			String sql = "DELETE from unfolding where practice_id = :practiceId";
			con.createQuery(sql).addParameter("practiceId", practiceId).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
