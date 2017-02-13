package jp.ac.chitose.dao.Class;

import java.sql.SQLException;

import org.sql2o.Connection;

import com.google.inject.Inject;

import jp.ac.chitose.IDBCP;
import jp.ac.chitose.dao.Interface.IPersonalDAO;

public class PersonalDAO implements IPersonalDAO {

	@Inject
	private IDBCP dbcp;

	@Override
	public int updatePersonal(int accountId, String name,String belongSchool, String emainAddress) {
		try (Connection con = dbcp.getSql2o().open()) {
			String sql = "update personal set name = :name, belong_school = :belongSchool, email_address = :emailAddress where account_id = :accountId";
			return con.createQuery(sql).addParameter("accountId",accountId).addParameter("name",name).addParameter("belongSchool", belongSchool)
									.addParameter("emailAddress", emainAddress).executeUpdate().getResult();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public boolean deletePersonal(int accountId) {
		try(Connection con = dbcp.getSql2o().open()){
			String sql = "DELETE from personal where account_id = :accountId";
			con.createQuery(sql).addParameter("accountId", accountId).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}


}
