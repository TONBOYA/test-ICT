package jp.ac.chitose.bean;

import java.io.Serializable;

public class ContentImageBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private int contentId;
	private String fileName;
	private int ordinal;

	public ContentImageBean(int contentId, String fileName, int ordinal) {
		this.fileName = fileName;
		this.contentId = contentId;
		this.ordinal = ordinal;
	}

	public ContentImageBean(){
		fileName = "";
		contentId = 0;
		ordinal = 0;
	}
}
