package jp.ac.chitose.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class AccountBean implements Serializable {
	private static final long serialVersionUID = -8603853850303581536L;

	private int accountId;
	private String loginName;
	private String passphrase;
	private String role;
	private String available;
}
