package jp.ac.chitose.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import jp.ac.chitose.option.PageType;
import lombok.Data;

@Data
public class PracticeFormBean implements Serializable {
	private static final long serialVersionUID = 3351250155237844642L;
	private int practiceHierarchyId;
	private int accountId;
	private int practiceId;
	private int contentId;
	private String name;
	private Date startOn;
	private List<String> keyword;
	private List<String> tag;
	private String tag1;
	private String tag2;
	private String tag3;
	private String category;
	private String summary;
	private String aim;
	private String challenge;

	public PracticeFormBean(PracticeRegisterBean practice, PageType TYPE){
		if(TYPE == PageType.REGISTER){
			practiceId = 0;
		}else if(TYPE == PageType.EDIT){
			practiceId = practice.getPracticeId();
		}

		practiceHierarchyId = practice.getPracticeHierarchyId();
		accountId = practice.getAccountId();
		contentId = 0;
		name = practice.getName();
		startOn = practice.getStartOn();
		keyword = practice.getKeyword();
		tag = practice.getTag();
		tag1 = practice.getTag1();
		tag2 = practice.getTag2();
		tag3 = practice.getTag3();
		category = practice.getCategory();
		summary = practice.getSummary();
		aim = practice.getAim();
		challenge = practice.getChallenge();
	}

}
