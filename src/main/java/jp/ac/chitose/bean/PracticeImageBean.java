package jp.ac.chitose.bean;

import java.io.Serializable;

import jp.ac.chitose.option.SceneType;
import lombok.Data;

@Data
public class PracticeImageBean implements Serializable {
	private static final long serialVersionUID = 7692345104453769121L;

	private int unfoldingId;
	private String fileName;
	private int ordinal;
	private SceneType type;

	public PracticeImageBean(int unfoldingId, String fileName,int ordinal) {
		this.fileName = fileName;
		this.unfoldingId = unfoldingId;
		this.ordinal = ordinal;
	}
	public PracticeImageBean(){
		fileName = "";
		unfoldingId = 0;
		ordinal = 0;
	}
}
