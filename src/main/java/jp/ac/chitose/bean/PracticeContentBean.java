package jp.ac.chitose.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class PracticeContentBean implements Serializable {
	private static final long serialVersionUID = -3072493865039781551L;
	private int practiceId;
	private int contentId;
}
