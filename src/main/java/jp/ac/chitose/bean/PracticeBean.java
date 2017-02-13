package jp.ac.chitose.bean;

import java.io.Serializable;
import java.util.Date;

import jp.ac.chitose.option.PracticeType;
import lombok.Data;

@Data
public class PracticeBean implements Serializable {
	private static final long serialVersionUID = 1664606328187054934L;
	private int practiceId;
	private String name;
	private String summary;
	private String aim;
	private String reaction;
	private Date startOn;
	private int practiceHierarchyId;
	private int AccountId;
	private String challenge;
	private int readTimes;
	private PracticeType type;
	private boolean approval;
	private int ordinal;

}
