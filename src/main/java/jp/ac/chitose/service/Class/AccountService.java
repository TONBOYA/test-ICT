package jp.ac.chitose.service.Class;


import java.util.List;

import com.google.inject.Inject;

import jp.ac.chitose.bean.AccountBean;
import jp.ac.chitose.bean.PersonalBean;
import jp.ac.chitose.dao.Interface.IAccountDAO;
import jp.ac.chitose.dao.Interface.IPersonalDAO;
import jp.ac.chitose.service.Interface.IAccountService;

public class AccountService implements IAccountService {

	@Inject
	private IAccountDAO accountDAO;

	@Inject
	private IPersonalDAO personalDAO;

	@Override
	public PersonalBean getPersonalBean(int accountId) {
		return accountDAO.selectPersonal(accountId);
	}

	@Override
	public List<PersonalBean> getPersonalAllListBean() {
		return accountDAO.selectPersonlAllList();
	}

	@Override
	public int updatePersonalData(int accountId, String name,String belongSchool, String emailAddress) {
		return personalDAO.updatePersonal(accountId, name, belongSchool, emailAddress);
	}

	//追加
	@Override
	public int registerAccountData(String loginName,String passphrase, String role , String name,String belongSchool,String emailAddress) {
		return accountDAO.registerPersonalData((accountDAO.registerAccountAndSelectId(loginName, passphrase, role)), name, belongSchool, emailAddress);
	}

	public String selectLoginName(String loginName){
		return accountDAO.selectLoginNameData(loginName);
	}

	@Override
	public int updatePassphrase(int accountId, String passphrase) {
		return accountDAO.updatePassphrase(accountId, passphrase);
	}

	@Override
	public AccountBean getAccountBean(int accountId){
		return accountDAO.selectAccount(accountId);
	}

	public boolean deleteAccountData(int accountId){
		if(personalDAO.deletePersonal(accountId)){
			return accountDAO.deleteAccount(accountId);
		}else{
			return false;
		}
	}
}
