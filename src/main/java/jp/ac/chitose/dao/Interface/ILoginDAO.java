package jp.ac.chitose.dao.Interface;

import com.google.inject.ImplementedBy;

import jp.ac.chitose.dao.Class.LoginDAO;

@ImplementedBy(LoginDAO.class)
public interface ILoginDAO {
	public boolean loginCheck(String loginName, String passphrase);
}
