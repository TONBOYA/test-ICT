package jp.ac.chitose.service.Interface;

import java.util.List;

import com.google.inject.ImplementedBy;

import jp.ac.chitose.bean.AccountBean;
import jp.ac.chitose.bean.PersonalBean;
import jp.ac.chitose.service.Class.AccountService;

@ImplementedBy(AccountService.class)
public interface IAccountService {
	public PersonalBean getPersonalBean(int accountId);
	public List<PersonalBean> getPersonalAllListBean();
	public int updatePersonalData(int accountId,String name,String belongSchool,String emailAddress);

	//追加
	public int registerAccountData(String loginName, String passphrase, String role, String name, String belongSchool,
			String emailAddress);

	public String selectLoginName(String loginName);
	public int updatePassphrase(int accountId, String passphrase);
	public AccountBean getAccountBean(int accountId);
	public boolean deleteAccountData(int accountId);
}
