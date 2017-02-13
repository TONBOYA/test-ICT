package jp.ac.chitose.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class PracticeTagBean implements Serializable {
	private static final long serialVersionUID = 8470688573822067232L;
	private int practiceId;
	private String tag;
	private String category;

	public PracticeTagBean(){
		practiceId = 0;
		tag = "";
		category = "";
	}
	public PracticeTagBean(String tag, String category){
		practiceId = 0;
		this.tag = tag;
		this.category = category;
	}
}