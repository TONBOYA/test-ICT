package jp.ac.chitose.service.Class;

import jp.ac.chitose.bean.AccountBean;
import jp.ac.chitose.dao.Interface.IAccountDAO;
import jp.ac.chitose.dao.Interface.ILoginDAO;
import jp.ac.chitose.service.Interface.ILoginService;

import com.google.inject.Inject;

public class LoginService implements ILoginService {

	@Inject
	private ILoginDAO loginDAO;

	@Inject
	private IAccountDAO accountDAO;

	@Override
	public boolean login(String loginName, String passphrase) {
		return loginDAO.loginCheck(loginName, passphrase);
	}

	@Override
	public AccountBean getAccount(String loginName, String passphrase) {
		return accountDAO.selectAccount(loginName, passphrase);
	}

}
