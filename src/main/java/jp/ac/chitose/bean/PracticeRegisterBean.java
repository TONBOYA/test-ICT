package jp.ac.chitose.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class PracticeRegisterBean implements Serializable {
	private static final long serialVersionUID = -1077188844577531070L;

	private int practiceId;
	private int accountId;
	private int contentId;
	private int practiceHierarchyId;
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
	private String reaction;
	private boolean approval;

	private List<UnfoldingFormBean> unfoldingList;

	public PracticeRegisterBean(List<UnfoldingFormBean> unfolfingList,int accountId,int practiceHierarchyId,String category){
		practiceId = 0;
		this.practiceHierarchyId = practiceHierarchyId;
		this.accountId = accountId;
		contentId = 0;
		name = "";
		startOn = null;
		keyword = new ArrayList<String>();
		tag = new ArrayList<String>();
		tag1 = "";
		tag2 = "";
		tag3 = "";
		this.category = category;
		tag = new ArrayList<String>();
		summary = "";
		aim = "";
		challenge = "";
		reaction = "";
		this.unfoldingList = unfolfingList;
	}

	public PracticeRegisterBean(PracticeBean practice,List<UnfoldingFormBean> unfoldingList,List<String> keyword,List<String> tag,String category){
		practiceId = practice.getPracticeId();
		practiceHierarchyId = practice.getPracticeHierarchyId();
		accountId = practice.getAccountId();
		name = practice.getName();
		startOn = practice.getStartOn();
		this.keyword = keyword;
		this.tag = tag;
		tag1 = tag.get(0);
		tag2 = tag.get(1);
		tag3 = tag.get(2);
		this.category = category;
		summary = practice.getSummary();
		aim = practice.getAim();
		challenge = practice.getChallenge();
		reaction = practice.getReaction();
		this.unfoldingList = unfoldingList;
		approval = false;
		contentId = 0;
	}

	public PracticeRegisterBean(PracticeFormBean practice,List<UnfoldingFormBean> unfolfingList,int accountId,int practiceHierarchyId){
		practiceId = practice.getPracticeId();
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
		this.category = practice.getCategory();
		summary = practice.getSummary();
		aim = practice.getAim();
		challenge = practice.getChallenge();
		reaction = "";
		this.unfoldingList = unfolfingList;
	}

	public PracticeRegisterBean(){
		practiceId = 0;
		practiceHierarchyId = 0;
		accountId = 0;
		name = "";
		startOn = null;
		keyword = new ArrayList<String>();
		tag1 = "";
		tag2 = "";
		tag3 = "";
		category = "";
		summary = "";
		aim = "";
		challenge = "";
		reaction = "";
		unfoldingList = new ArrayList<UnfoldingFormBean>();
	}
}
