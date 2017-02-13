package jp.ac.chitose.service.Interface;

import jp.ac.chitose.bean.AccountBean;
import jp.ac.chitose.service.Class.LoginService;

import com.google.inject.ImplementedBy;

@ImplementedBy(LoginService.class)
public interface ILoginService {
	public boolean login(String loginName, String passphrase);

	public AccountBean getAccount(String loginName, String passphrase);
}
