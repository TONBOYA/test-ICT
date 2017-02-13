package jp.ac.chitose.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class PracticeCommentBean implements Serializable {
	private static final long serialVersionUID = -6830771410872922197L;

	private int practiceId;
	private int accountId;
	private String practiceComment;

}
