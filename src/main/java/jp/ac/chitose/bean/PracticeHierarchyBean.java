package jp.ac.chitose.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class PracticeHierarchyBean implements Serializable {
//種類が違うからわかるなまえにentity実体
	private static final long serialVersionUID = -3249062799708398672L;

	private int practiceHierarchyId;

	private String name;

	private int oneLevelUp;

	private int ordinal;

	public PracticeHierarchyBean(PracticeHierarchyBean bean){
		practiceHierarchyId = bean.getPracticeHierarchyId();
		name = bean.getName();
		ordinal = bean.getOrdinal();
		oneLevelUp = bean.getOneLevelUp();
	}

	public PracticeHierarchyBean(){
		practiceHierarchyId = 0;
		name = "";
		ordinal = 0;
		oneLevelUp = 0;
	}

}