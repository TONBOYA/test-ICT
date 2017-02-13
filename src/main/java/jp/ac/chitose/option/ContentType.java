package jp.ac.chitose.option;

public enum ContentType {
	FLASH, ZIP, RAR, OTHER;
	private ContentType TYPE;

	public ContentType getTYPE() {
		return TYPE;
	}

	public void setTYPE(ContentType TYPE) {
		this.TYPE = TYPE;
	}

	public static final ContentType of(String str) {
		switch (str) {
		case "flash":

			return ContentType.FLASH;

		case "zip":

			return ContentType.ZIP;

		case "rar":

			return ContentType.RAR;

		default:

			return ContentType.OTHER;

		}
	}
}
