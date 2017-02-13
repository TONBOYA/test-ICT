package jp.ac.chitose.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jp.ac.chitose.option.TreeType;

public class TempTreeBean implements Serializable {
	private static final long serialVersionUID = 1724460099367565037L;
	private int id;
	private String name;
	private TempTreeBean parent;
	private List<TempTreeBean> children;
	private TreeType treeType;
	private boolean loaded;
	private int accountId;
	private int oneLevelUp;
	private int ordinal;

	public TempTreeBean(PracticeHierarchyBean hierarchy,TreeType treeType){
		id = hierarchy.getPracticeHierarchyId();
		parent = null;
		name = hierarchy.getName();
		children = new ArrayList<TempTreeBean>();
		this.treeType = treeType;
		accountId = 0;
		oneLevelUp = hierarchy.getOneLevelUp();
		ordinal = hierarchy.getOrdinal();
	}

	public TempTreeBean(PracticeHierarchyBean hierarchy,TempTreeBean parent,TreeType treeType){
		id = hierarchy.getPracticeHierarchyId();
		name = hierarchy.getName();
		this.parent = parent;
		this.parent.children.add(this);
		children = new ArrayList<TempTreeBean>();
		this.treeType = treeType;
		accountId = 0;
		oneLevelUp = hierarchy.getOneLevelUp();
		ordinal = hierarchy.getOrdinal();
	}

	public TempTreeBean(PracticeBean content,TempTreeBean parent,TreeType treeType){
		this.parent = parent;
		this.parent.children.add(this);
		id = content.getPracticeId();
		name = content.getName();
		children = new ArrayList<TempTreeBean>();
		this.treeType = treeType;
		accountId = content.getAccountId();
	}

	public TempTreeBean(ContentHierarchyBean hierarchy,TreeType treeType){
		id = hierarchy.getContentHierarchyId();
		parent = null;
		name = hierarchy.getName();
		children = new ArrayList<TempTreeBean>();
		this.treeType = treeType;
		accountId = 0;
		oneLevelUp = hierarchy.getOneLevelUp();
		ordinal = hierarchy.getOrdinal();
	}

	public TempTreeBean(ContentHierarchyBean hierarchy,TempTreeBean parent,TreeType treeType){
		id = hierarchy.getContentHierarchyId();
		name = hierarchy.getName();
		this.parent = parent;
		this.parent.children.add(this);
		children = new ArrayList<TempTreeBean>();
		this.treeType = treeType;
		accountId = 0;
		oneLevelUp = hierarchy.getOneLevelUp();
		ordinal = hierarchy.getOrdinal();
	}

	public TempTreeBean(ContentBean content,TempTreeBean parent,TreeType treeType){
		this.parent = parent;
		this.parent.children.add(this);
		id = content.getContentId();
		name = content.getName();
		children = new ArrayList<TempTreeBean>();
		this.treeType = treeType;
		accountId = content.getAccountId();
	}

	public TempTreeBean(){
		id = -1;
		name = "";
		parent = null;
		children = new ArrayList<TempTreeBean>();
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

	public TempTreeBean getParent() {
		return parent;
	}

	public void setParent(TempTreeBean parent) {
		this.parent = parent;
	}

	public List<TempTreeBean> getChildren() {
		return children;
	}

	public void setChildren(List<TempTreeBean> children) {
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
