package jp.ac.chitose.option;

public enum TreeType {
	HIERARCHY,
	PRACTICE,
	CONTENT,
	ROOT,
	CATEGORY;
	private TreeType TYPE;

	public TreeType getTYPE() {
		return TYPE;
	}

	public void setTYPE(TreeType TYPE) {
		this.TYPE = TYPE;
	}

}
