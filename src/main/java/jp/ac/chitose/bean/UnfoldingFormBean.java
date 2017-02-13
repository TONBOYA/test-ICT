package jp.ac.chitose.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.form.upload.FileUpload;

import lombok.Data;

@Data
public class UnfoldingFormBean implements Serializable {

	private static final long serialVersionUID = 4086529566583542967L;
	private int unfoldingId;
	private int practiceId;
	private int ordinal;
	private String name;
	private int requiredMinute;
	private String instruction;
	private String specialInstruction;
	private List<FileUpload> firstImageUploader;
	private PracticeImageBean firstImage;
	private List<FileUpload> secondImageUploader;
	private PracticeImageBean secondImage;

	public UnfoldingFormBean(int unfoldingId, int practiceId, int ordinal, String name, int requiredMinute,
			String instruction, String specialInstruction) {
		this.instruction = instruction;
		this.unfoldingId = unfoldingId;
		this.practiceId = practiceId;
		this.ordinal = ordinal;
		this.name = name;
		this.requiredMinute = requiredMinute;
		this.specialInstruction = specialInstruction;

	}

	public UnfoldingFormBean(UnfoldingBean unfolding){
		unfoldingId = unfolding.getUnfoldingId();
		practiceId = unfolding.getPracticeId();
		ordinal = unfolding.getOrdinal();
		name = unfolding.getName();
		requiredMinute = unfolding.getRequiredMinute();
		instruction = unfolding.getInstruction();
		firstImageUploader = new ArrayList<FileUpload>();
		firstImage = unfolding.getFirstImage();
		secondImageUploader = new ArrayList<FileUpload>();
		secondImage = unfolding.getSecondImage();
		specialInstruction = unfolding.getSpecialInstruction();

	}

	public UnfoldingFormBean(int ordinal){
		unfoldingId = 0;
		practiceId = 0;
		this.ordinal = ordinal;
		name = "";
		requiredMinute = 0;
		instruction = "";
		firstImageUploader = new ArrayList<FileUpload>();
		firstImage = new PracticeImageBean();
		secondImageUploader = new ArrayList<FileUpload>();
		secondImage = new PracticeImageBean();
		specialInstruction = "";
	}
	public UnfoldingFormBean(){
		unfoldingId = 0;
		practiceId = 0;
		this.ordinal = 4;
		name = "";
		requiredMinute = 0;
		instruction = "";
		firstImageUploader = new ArrayList<FileUpload>();
		firstImage = new PracticeImageBean();
		secondImageUploader = new ArrayList<FileUpload>();
		secondImage = new PracticeImageBean();
		specialInstruction = "";
	}
}
