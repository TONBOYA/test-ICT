package jp.ac.chitose.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class EditAccountBean implements Serializable {
	
	private static final long serialVersionUID = -4487517388691272770L;
	private int accountId;
	private String loginName;
	private String presentPassphrase;
	private String inputedPassphrase;
	private String newPassphrase;
	private String checkPassphrase;
	private String name;
	private String belongSchool;
	private String emailAddress;
	private String role;
	private boolean otherAccount;
	
	public EditAccountBean(AccountBean accountBean, PersonalBean personalBean) {
		accountId = accountBean.getAccountId();
		loginName = accountBean.getLoginName();
		presentPassphrase = accountBean.getPassphrase();
		inputedPassphrase = "";
		newPassphrase = "";
		checkPassphrase = "";
		name = personalBean.getName();
		belongSchool = personalBean.getBelongSchool();
		emailAddress = personalBean.getEmailAddress();
		if(accountBean.getRole().equals("USER")){
			role = "ユーザー";
		}else if(accountBean.getRole().equals("AUTHOR")){
			role = "教員";
		}else if(accountBean.getRole().equals("ADMIN")){
			role = "管理者";
		}else{
			role = "不明";
		}
		otherAccount = false;
	}
}
