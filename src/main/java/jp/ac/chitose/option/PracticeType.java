package jp.ac.chitose.option;

public enum PracticeType {
	EDITED, EDITING, OTHER;
	private PracticeType TYPE;

	public PracticeType getTYPE() {
		return TYPE;
	}

	public void setTYPE(PracticeType TYPE) {
		this.TYPE = TYPE;
	}

	public static final PracticeType of(String str) {
		switch (str) {
		case "edited":

			return PracticeType.EDITED;

		case "editing":

			return PracticeType.EDITING;

		default:

			return PracticeType.OTHER;

		}
	}
}
