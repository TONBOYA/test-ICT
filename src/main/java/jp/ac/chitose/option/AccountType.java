package jp.ac.chitose.option;

public enum AccountType {
	ADMIN, AUTHOR, USER;
	private AccountType TYPE;

	public AccountType getTYPE() {
		return TYPE;
	}

	public void setTYPE(AccountType TYPE) {
		this.TYPE = TYPE;
	}

	public static final AccountType of(String str) {
		switch (str) {
		case "ADMIN":

			return AccountType.ADMIN;

		case "AUTHOR":

			return AccountType.AUTHOR;

		default:

			return AccountType.USER;

		}
	}
}
