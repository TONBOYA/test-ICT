package jp.ac.chitose.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class PracticeContentPageBean implements Serializable {
	private static final long serialVersionUID = -3019692892748929802L;

	private int practiceId;
	private int contentId;

	public PracticeContentPageBean(PracticeContentBean bean){
		this.practiceId = practiceId;
		this.contentId = contentId;
	}

	public PracticeContentPageBean(){
		practiceId = 0;
		contentId = 0;
	}


}
