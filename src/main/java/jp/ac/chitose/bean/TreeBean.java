package jp.ac.chitose.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.ac.chitose.option.TreeType;

public class TreeBean implements Serializable {
	private static final long serialVersionUID = -1040617829350136854L;
	private int id;
	private String name;
	private TreeBean parent;
	private List<TreeBean> children;
	private TreeType treeType;
	private boolean loaded;
	private int accountId;
	private int oneLevelUp;
	private int ordinal;


	public TreeBean(PracticeHierarchyBean hierarchy,TreeType treeType){
		id = hierarchy.getPracticeHierarchyId();
		parent = null;
		name = hierarchy.getName();
		children = new ArrayList<TreeBean>();
		this.treeType = treeType;
		accountId = 0;
	}

	public TreeBean(ContentHierarchyBean hierarchy,TreeType treeType){
		id = hierarchy.getContentHierarchyId();
		parent = null;
		name = hierarchy.getName();
		children = new ArrayList<TreeBean>();
		this.treeType = treeType;
		accountId = 0;
		ordinal = hierarchy.getOrdinal();
		oneLevelUp = hierarchy.getOneLevelUp();
	}

	public TreeBean(PracticeHierarchyBean hierarchy,TreeBean parent,TreeType treeType){
		id = hierarchy.getPracticeHierarchyId();
		name = hierarchy.getName();
		this.parent = parent;
		this.parent.children.add(this);
		children = new ArrayList<TreeBean>();
		this.treeType = treeType;
		accountId = 0;
	}

	public TreeBean(ContentHierarchyBean hierarchy,TreeBean parent,TreeType treeType){
		id = hierarchy.getContentHierarchyId();
		name = hierarchy.getName();
		this.parent = parent;
		this.parent.children.add(this);
		children = new ArrayList<TreeBean>();
		this.treeType = treeType;
		accountId = 0;
		oneLevelUp = hierarchy.getOneLevelUp();
	}


	public TreeBean(PracticeBean practice,TreeBean parent,TreeType treeType){
		this.parent = parent;
		this.parent.children.add(this);
		id = practice.getPracticeId();
		name = practice.getName();
		children = new ArrayList<TreeBean>();
		this.treeType = treeType;
		accountId = practice.getAccountId();
	}

	public TreeBean(ContentBean content,TreeBean parent,TreeType treeType){
		this.parent = parent;
		this.parent.children.add(this);
		id = content.getContentId();
		name = content.getName();
		children = new ArrayList<TreeBean>();
		this.treeType = treeType;
		accountId = content.getAccountId();
	}



//	public TreeBean(PracticeBean practice,TreeType treeType){
//		name = practice.getName();
//		id = practice.getPracticeId();
//		this.treeType = treeType;
//	}



	public TreeBean(){
		id = -1;
		name = "";
		parent = null;
		children = new ArrayList<TreeBean>();
		treeType = null;
		accountId = -1;
	}

	public int getId() {
		return id;
	}

	public void setId(int Id) {
		this.id = Id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TreeBean getParent() {
		return parent;
	}

	public void setParent(TreeBean parent) {
		this.parent = parent;
	}

	public List<TreeBean> getChildren() {
		return children;
	}

	public void setChildren(List<TreeBean> children) {
		this.children = children;
	}

	public TreeType getTreeType() {
		return treeType;
	}

	public void setTreeType(TreeType treeType) {
		this.treeType = treeType;
	}

	@Override
	public String toString(){
		return name;
	}

	public boolean isLoaded() {
		return loaded;
	}

	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public void setOrdinal(int ordinal) {
		this.ordinal = ordinal;
	}

	public int getOneLevelUp() {
		return oneLevelUp;
	}

	public void setOneLevelUp(int oneLevelUp) {
		this.oneLevelUp = oneLevelUp;
	}
}
