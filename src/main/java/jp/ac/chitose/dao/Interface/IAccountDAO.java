package jp.ac.chitose.dao.Interface;

import java.util.List;

import com.google.inject.ImplementedBy;

import jp.ac.chitose.bean.AccountBean;
import jp.ac.chitose.bean.PersonalBean;
import jp.ac.chitose.dao.Class.AccountDAO;

@ImplementedBy(AccountDAO.class)
public interface IAccountDAO {
	public AccountBean selectAccount(int accountId);
	public AccountBean selectAccount(String loginName,String passphrase);
	public PersonalBean selectPersonal(int accountId);
	public List<PersonalBean> selectPersonlAllList();
	public boolean Available(int accountId);

	//追加
	public int registerAccountAndSelectId(String loginName,String passphrase, String role);
	public int registerPersonalData(int accountId, String name, String belongSchool, String emailAddress);
	public String selectLoginNameData(String loginName);
	public int updatePassphrase(int accountId, String passphrase);
	public boolean deleteAccount(int accountId);

}
