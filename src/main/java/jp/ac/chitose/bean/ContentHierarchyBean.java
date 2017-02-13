package jp.ac.chitose.bean;

import java.io.Serializable;

import lombok.Data;

@Data
public class ContentHierarchyBean implements Serializable {
	
	private static final long serialVersionUID = -2447705320638954806L;

	private int contentHierarchyId;
	
	private String name;
	
	private int oneLevelUp;
	
	private int ordinal;
}
