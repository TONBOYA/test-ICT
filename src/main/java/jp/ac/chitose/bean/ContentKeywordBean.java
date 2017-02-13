package jp.ac.chitose.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class ContentKeywordBean implements Serializable {
	private static final long serialVersionUID = 4777172628910497320L;

	private int contentId;
	private String keyword;

	public ContentKeywordBean(){
		contentId = 0;
		keyword = "";
	}

	public ContentKeywordBean(String keyword){
		contentId = 0;
		this.keyword = keyword;
	}
}
