package jp.ac.chitose.bean;

import lombok.Data;

@Data
public class UnfoldingBean {
	private int unfoldingId;
	private int practiceId;
	private int ordinal;
	private String name;
	private int requiredMinute;
	private String instruction;
	private String specialInstruction;
	private PracticeImageBean firstImage;
	private PracticeImageBean secondImage;
}
