package jp.ac.chitose.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class ContentTagBean implements Serializable {
	private static final long serialVersionUID = 8470688573822067232L;
	private int contentId;
	private String tag;
	private String category;

	public ContentTagBean() {
		contentId = 0;
		tag = "";
		category = "";
	}

	public ContentTagBean(String tag, String category) {
		contentId = 0;
		this.tag = tag;
		this.category = category;
	}
}
