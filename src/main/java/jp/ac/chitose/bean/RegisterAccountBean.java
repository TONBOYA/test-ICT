package jp.ac.chitose.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class RegisterAccountBean implements Serializable {

	/**　久保作成
	 *
	 */
	private static final long serialVersionUID = -6002891884460592660L;
	private String loginName;
	private String passphrase;
	private String retypedPassphrase;
	private String name;
	private String belongSchool;
	private String emailAddress;
	private String role;
}