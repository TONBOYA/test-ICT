package jp.ac.chitose.service.Interface;

import java.util.List;

import com.google.inject.ImplementedBy;

import jp.ac.chitose.bean.ContentHierarchyBean;
import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.bean.TempTreeBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.service.Class.ContentHierarchyService;

@ImplementedBy(ContentHierarchyService.class)
public interface IContentHierarchyService {
	public List<TreeBean> makeContentTree();

	public void searchChildHierarchy(TreeBean parent);

	public TreeBean getTree(String id);

	public TreeBean findTree(List<TreeBean> treeList, String id);

	public List<TreeBean> searchChildrenCategory(TreeBean parent);

	public List<TreeBean> searchChildren(TreeBean parent);

	public TreeBean searchRoot();

	public String makeContentHierarchyPath(TreeBean parent);

	public String theCurrentHierarchy(TreeBean parent);

	public List<TreeBean> selectParent(int parentHierarchyId);

	public boolean searchSameName(TreeBean parent, ContentPageBean content);

	public TempTreeBean searchSubRoot();

	public List<TempTreeBean> searchSubChildren(TempTreeBean parent);

	public boolean changeHierarchy(ContentHierarchyBean afterHierarchyBean,int beforeHierarchyId);

	public ContentHierarchyBean hierarchy(int hierarchyId);

	public int lastHierarchy();
}
