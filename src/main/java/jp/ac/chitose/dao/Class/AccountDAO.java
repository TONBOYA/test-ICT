package jp.ac.chitose.dao.Class;

import java.sql.SQLException;
import java.util.List;

import org.sql2o.Connection;

import com.google.inject.Inject;

import jp.ac.chitose.IDBCP;
import jp.ac.chitose.bean.AccountBean;
import jp.ac.chitose.bean.PersonalBean;
import jp.ac.chitose.dao.Interface.IAccountDAO;

public class AccountDAO implements IAccountDAO {

	@Inject
	private IDBCP dbcp;

	@Override
	public AccountBean selectAccount(int accountId){
		try(Connection con = dbcp.getSql2o().open()) {
			String sql = "select * from account where account_id = :id";
			return con.createQuery(sql).addParameter("id", accountId).setAutoDeriveColumnNames(true).executeAndFetchFirst(AccountBean.class);
		}catch (SQLException e) {

		}
		return null;
	}

//null危険......
	@Override
	public AccountBean selectAccount(String loginName, String passphrase) {
		try(Connection con = dbcp.getSql2o().open()){
			String sql = "select * from account where login_name = :loginName and passphrase = :passphrase";
			return con.createQuery(sql)
					.addParameter("loginName", loginName)
					.addParameter("passphrase", passphrase)
					.setAutoDeriveColumnNames(true)
					.executeAndFetchFirst(AccountBean.class);
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public PersonalBean selectPersonal(int accountId) {
		try(Connection con = dbcp.getSql2o().open()){
			String sql = "select * from personal where account_id = :accountId";
			return con.createQuery(sql)
					.addParameter("accountId", accountId)
					.setAutoDeriveColumnNames(true)
					.executeAndFetchFirst(PersonalBean.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public List<PersonalBean> selectPersonlAllList() {
		try(Connection con = dbcp.getSql2o().open()){
			String sql = "select * from personal AS X where exists (select * from account where account_id = X.account_id)";
			return con.createQuery(sql).setAutoDeriveColumnNames(true).executeAndFetch(PersonalBean.class);
		} catch (SQLException e) {
		e.printStackTrace();
		}
		return null;
	}
	@Override
	public boolean Available(int accountId){
		try(Connection con = dbcp.getSql2o().open()) {
			String sql = "select available from account where account_id = :id";
			return con.createQuery(sql).addParameter("accountId", accountId).executeAndFetchFirst(boolean.class);
		}catch (SQLException e) {
		e.printStackTrace();
		}
		return false;

	}

	//追加
	@Override
	public int registerAccountAndSelectId(String loginName,String passphrase, String role) {
		int accountId = 0;
		try(Connection con = dbcp.getSql2o().open()){
			String sql = "INSERT INTO account (login_name,passphrase,role,available) VALUES(:loginName,:passphrase,:role,:available) RETURNING account_id ";
			return con.createQuery(sql)
					.addParameter("loginName", loginName)
					.addParameter("passphrase", passphrase)
					.addParameter("role", role)
					.addParameter("available", true).executeAndFetchFirst(Integer.class);

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return accountId;
	}

	@Override
	public int registerPersonalData(int accountId,String name,String belongSchool,String emailAddress){
		try(Connection con = dbcp.getSql2o().open()){
			String sql = "INSERT INTO personal (account_id,name,belong_school,email_address) VALUES(:accountId,:name,:belongSchool,:emailAddress)";
			con.createQuery(sql)
					.addParameter("accountId", accountId)
					.addParameter("name", name)
					.addParameter("belongSchool", belongSchool)
					.addParameter("emailAddress", emailAddress).executeUpdate();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public String selectLoginNameData(String loginName){
		try(Connection con = dbcp.getSql2o().open()) {
			String sql = "select login_name from account where login_name = :loginName";
			return con.createQuery(sql).addParameter("loginName", loginName).executeAndFetchFirst(String.class);
		}catch (SQLException e) {
		e.printStackTrace();
		}
		return "a";
	}

	@Override
	public int updatePassphrase(int accountId, String passphrase) {
		try (Connection con = dbcp.getSql2o().open()) {
			String sql = "update account set passphrase = :passphrase where account_id = :accountId";
			con.createQuery(sql).addParameter("accountId",accountId).addParameter("passphrase",passphrase).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public boolean deleteAccount(int accountId) {
		try(Connection con = dbcp.getSql2o().open()){
			String sql = "DELETE from account where account_id = :accountId";
			con.createQuery(sql).addParameter("accountId", accountId).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}


}
