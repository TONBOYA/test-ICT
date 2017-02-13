package jp.ac.chitose.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.ac.chitose.option.PracticeType;
import lombok.Data;

@Data
public class PracticePageBean implements Serializable {

	private static final long serialVersionUID = -7642945419149488311L;
	private int practiceId;
	private int accountId;
	private int contentId;
	private int practiceHierarchyId;
	private String name;
	private Date startOn;
	private List<String> keyword;
	private List<String> tag;
	private String summary;
	private String aim;
	private String challenge;
	private String reaction;
	private int readTimes;
	private PracticeType type;
	private boolean approval;
	private int ordinal;

	private List<UnfoldingBean> unfoldingList;

	public PracticePageBean(PracticeBean practice,List<UnfoldingBean> unfoldingList,List<String> keyword,List<String> tag,int contentId){
		practiceId = practice.getPracticeId();
		practiceHierarchyId = practice.getPracticeHierarchyId();
		accountId = practice.getAccountId();
		this.contentId = contentId;
		name = practice.getName();
		startOn = practice.getStartOn();
		this.keyword = keyword;
		this.tag = tag;
		summary = practice.getSummary();
		aim = practice.getAim();
		challenge = practice.getChallenge();
		reaction = practice.getReaction();
		this.unfoldingList = unfoldingList;
		approval = practice.isApproval();
	}

	public PracticePageBean(PracticeBean practice,List<UnfoldingBean> unfoldingList,List<String> keyword,List<String> tag){
		practiceId = practice.getPracticeId();
		practiceHierarchyId = practice.getPracticeHierarchyId();
		accountId = practice.getAccountId();
		name = practice.getName();
		startOn = practice.getStartOn();
		this.keyword = keyword;
		this.tag = tag;
		summary = practice.getSummary();
		aim = practice.getAim();
		challenge = practice.getChallenge();
		reaction = practice.getReaction();
		this.unfoldingList = unfoldingList;
		approval = practice.isApproval();
	}

	public PracticePageBean(){
		practiceId = 0;
		practiceHierarchyId = 0;
		accountId = 0;
		name = "";
		startOn = null;
		keyword = new ArrayList<String>();
		tag = new ArrayList<String>();
		summary = "";
		aim = "";
		challenge = "";
		reaction = "";
		unfoldingList = new ArrayList<UnfoldingBean>();
	}

	public PracticePageBean(PracticePageBean practice){
		practiceId = practice.getPracticeId();
		practiceHierarchyId = practice.getPracticeHierarchyId();
		accountId = practice.getAccountId();
		name = practice.getName();
		startOn = practice.getStartOn();
		this.keyword = keyword;
		this.tag = tag;
		summary = practice.getSummary();
		aim = practice.getAim();
		challenge = practice.getChallenge();
		reaction = practice.getReaction();
		this.unfoldingList = unfoldingList;
	}

	public PracticePageBean(PracticeBean practice){
		practiceId = practice.getPracticeId();
		practiceHierarchyId = practice.getPracticeHierarchyId();
		accountId = practice.getAccountId();
		name = practice.getName();
		startOn = practice.getStartOn();
		this.keyword = keyword;
		this.tag = tag;
		summary = practice.getSummary();
		aim = practice.getAim();
		challenge = practice.getChallenge();
		reaction = practice.getReaction();
		this.unfoldingList = unfoldingList;
	}
}
