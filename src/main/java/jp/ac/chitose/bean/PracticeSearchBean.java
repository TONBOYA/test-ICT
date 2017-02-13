package jp.ac.chitose.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class PracticeSearchBean implements Serializable {
	private static final long serialVersionUID = -6916398582309846826L;

	private int practiceId;
	private int practiceHierarchyId;
	private String categoryName;
	private List<String> keyword;
	private List<PracticePageBean> practice;

	public PracticeSearchBean(){
		practiceId = 0;
		practiceHierarchyId = 0;
		categoryName = "";
		keyword = new ArrayList<String>();
		practice = new ArrayList<PracticePageBean>();
	}

}
