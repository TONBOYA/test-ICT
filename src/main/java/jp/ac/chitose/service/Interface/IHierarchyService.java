package jp.ac.chitose.service.Interface;

import java.util.List;

import com.google.inject.ImplementedBy;

import jp.ac.chitose.bean.PracticeHierarchyBean;
import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.bean.TempTreeBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.service.Class.HierarchyService;

@ImplementedBy(HierarchyService.class)
public interface IHierarchyService {
	public List<TreeBean> makePracticeTree();

	public void searchChildHierarchy(TreeBean parent);

	public TreeBean getTree(String id);

	public TreeBean findTree(List<TreeBean> treeList, String id);

	public List<TreeBean> searchChildrenCategory(TreeBean parent);

	public List<TreeBean> searchChildren(TreeBean parent);

	public TreeBean searchRoot();

	public TreeBean searchParent(int parentHierarchyId);

	public String printWorkingDirectory(TreeBean workingDirectory);

	public String makePracticeHierarchyPath(TreeBean parent);

	public List<TreeBean> selectParent(int parentHierarchyId);

	public boolean searchSameName(TreeBean parent, PracticeRegisterBean practice);

	public TempTreeBean searchSubRoot();

	public List<TempTreeBean> searchSubChildren(TempTreeBean parent);

	public PracticeHierarchyBean hierarchy(int hierarchyId);

	public boolean changeHierarchy(PracticeHierarchyBean afterHierarchyBean,int beforeHierarchyId);

}
