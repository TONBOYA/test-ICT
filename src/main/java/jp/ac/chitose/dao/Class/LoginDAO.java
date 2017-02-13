package jp.ac.chitose.dao.Class;


import java.sql.SQLException;

import jp.ac.chitose.IDBCP;
import jp.ac.chitose.dao.Interface.ILoginDAO;
import lombok.val;

import org.sql2o.Connection;

import com.google.inject.Inject;

public class LoginDAO implements ILoginDAO {

	@Inject
	private IDBCP dbcp;

	@Override
	public boolean loginCheck(String loginName, String passphrase) {
		try(Connection con = dbcp.getSql2o().open()){
			val sql = "select * from account where login_name = :loginName and passphrase = :passphrase";
			return con.createQuery(sql)
					.addParameter("loginName", loginName)
					.addParameter("passphrase", passphrase)
					.setAutoDeriveColumnNames(true).executeScalar(boolean.class);
		}catch(SQLException | NullPointerException e){
			return false;
		}
	}

}
