package jp.ac.chitose.bean;

import java.io.File;

import lombok.Data;


@Data
public class RegisterUnfoldingBean {
	private int unfoldingId;
	private int practiceId;
	private int ordinal;
	private String name;
	private int requiredMinute;
	private String instruction;
	private String specialInstruction;
	private File firstScene;
	private File secondScene;
}
