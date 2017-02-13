package jp.ac.chitose.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class PracticeKeywordBean implements Serializable {
	private static final long serialVersionUID = 8470688573822067232L;
	private int practiceId;
	private String keyword;
	
	public PracticeKeywordBean(){
		practiceId = 0;
		keyword = "";
	}
	
	public PracticeKeywordBean(String keyword){
		practiceId = 0;
		this.keyword = keyword;
	}
}
