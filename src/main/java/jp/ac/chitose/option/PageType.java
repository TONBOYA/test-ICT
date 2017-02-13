package jp.ac.chitose.option;

public enum PageType {
	REGISTER,
	EDIT,
	DISTRIBUTION,
	DELETE,
	SEARCH;

	private PageType TYPE;

	public PageType getTYPE() {
		return TYPE;
	}

	public void setTYPE(PageType TYPE) {
		this.TYPE = TYPE;
	}
}
