package jp.ac.chitose.service.Class;

import static jp.ac.chitose.option.TreeType.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Inject;

import jp.ac.chitose.bean.PracticeBean;
import jp.ac.chitose.bean.PracticeHierarchyBean;
import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.bean.TempTreeBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.dao.Interface.IPracticeDAO;
import jp.ac.chitose.dao.Interface.IPracticeHierarchyDAO;
import jp.ac.chitose.service.Interface.IHierarchyService;

public class HierarchyService implements IHierarchyService {
	private List<TreeBean> treeList = new ArrayList<TreeBean>();

	@Inject
	private IPracticeDAO practiceDAO;

	@Inject
	private IPracticeHierarchyDAO practiceHierarchyDAO;

	@Override
	public List<TreeBean> makePracticeTree() {
		treeList = new ArrayList<>();

		TreeBean root = new TreeBean(practiceHierarchyDAO.searchRoot(), ROOT);

		List<PracticeHierarchyBean> rootList = practiceHierarchyDAO.searchChildren(root.getId());

		rootList.forEach(bean -> treeList.add(new TreeBean(bean, HIERARCHY)));

		treeList.forEach(bean -> searchChildHierarchy(bean));

		return treeList;
	}

	@Override
	public void searchChildHierarchy(TreeBean parent) {
		List<PracticeHierarchyBean> children = practiceHierarchyDAO.searchChildren(parent.getId());
		List<PracticeBean> practiceChild = practiceDAO.selectPracticeOfHierarchy(parent.getId());
		if (!children.isEmpty()) {
			children.forEach(child -> {
				if (parent.getTreeType() == ROOT) {
					searchChildHierarchy(new TreeBean(child, parent, CATEGORY));
				} else {
					searchChildHierarchy(new TreeBean(child, parent, HIERARCHY));
				}
			});
		}
		if (!practiceChild.isEmpty()) {
			practiceChild.forEach(child -> new TreeBean(child, parent, PRACTICE));
		}
	}

	@Override
	public TreeBean getTree(String id) {
		if (treeList.isEmpty()) {
			treeList = makePracticeTree();
		}
		return findTree(treeList, id);
	}

	@Override
	public TreeBean findTree(List<TreeBean> findtreeList, String id) {

		for (TreeBean tree : findtreeList) {
			if (tree.getName().equals(id)) {
				return tree;
			}

			TreeBean temp = findTree(tree.getChildren(), id);
			if (temp != null) {
				return temp;
			}
		}

		return null;
	}

	@Override
	public List<TreeBean> searchChildrenCategory(TreeBean parent) {
		List<TreeBean> children = new ArrayList<>();
		List<PracticeHierarchyBean> list = practiceHierarchyDAO.searchChildren(parent.getId());
		list.stream().forEach(hierarchy -> {
				children.add(new TreeBean(hierarchy, parent, CATEGORY));

		});
		List<PracticeBean> practiceList = practiceDAO.selectPracticeOfHierarchy(parent.getId());
		practiceList.forEach(practiceBean -> children.add(new TreeBean(practiceBean, parent, PRACTICE)));
		return children;
	}

	@Override
	public List<TreeBean> searchChildren(TreeBean parent) {
		List<TreeBean> children = new ArrayList<>();
		List<PracticeHierarchyBean> list = practiceHierarchyDAO.searchChildren(parent.getId());
		list.forEach(hierarchy -> children.add(new TreeBean(hierarchy, parent, HIERARCHY)));
		List<PracticeBean> practiceList = practiceDAO.selectPracticeOfHierarchy(parent.getId());
		practiceList.forEach(practiceBean -> children.add(new TreeBean(practiceBean, parent, PRACTICE)));
		return children;
	}

	@Override
	public TreeBean searchRoot() {
		return new TreeBean(practiceHierarchyDAO.searchRoot(), ROOT);
	}

	@Override
	public TreeBean searchParent(int parentHierarchyId) {
		return new TreeBean(practiceHierarchyDAO.searchParent(parentHierarchyId), ROOT);
	}

	@Override
	public String printWorkingDirectory(TreeBean workingDirectory) {

		return null;
	}

	@Override
	public String makePracticeHierarchyPath(TreeBean parent) {
		List<TreeBean> parents = new ArrayList<>();
		if(parent.getId() != 1){
			parents.add(parent);
		}
		while (parent.getParent() != null) {
			parent = parent.getParent();
			parents.add(parent);
			for(int i = 0;i<parents.size();i++){
				if(parents.get(i).getName().equals("ルート")){
					parents.remove(i);
				}
			}
		}
			// forEach
			List<String> pathList = parents.stream().map(bean -> bean.getName()).collect(Collectors.toList());

			Collections.reverse(pathList);
			return String.join(" / ", pathList);
	}

	@Override
	public List<TreeBean> selectParent(int parentHierarchyId) {
		// TreeBean parent = new TreeBean();
		// List<TreeBean> parents = new ArrayList<TreeBean>();
		return null;
	}

	@Override
	public boolean searchSameName(TreeBean parent, PracticeRegisterBean practice) {
		List<TreeBean> searchList = searchChildren(parent);
		boolean result = true;
		if (searchList != null) {
			for (TreeBean bean : searchList) {
				if (bean.getTreeType() == PRACTICE && bean.getName().equals(practice.getName())) {
					result = false;
				}
			}
		}
		return result;
	}

	@Override
	public TempTreeBean searchSubRoot() {
		return new TempTreeBean(practiceHierarchyDAO.searchRoot(), ROOT);
	}

	@Override
	public List<TempTreeBean> searchSubChildren(TempTreeBean parent) {
		List<TempTreeBean> children = new ArrayList<>();
		List<PracticeHierarchyBean> list = practiceHierarchyDAO.searchChildren(parent.getId());
		list.forEach(hierarchy -> children.add(new TempTreeBean(hierarchy, parent, HIERARCHY)));
		List<PracticeBean> practiceList = practiceDAO.selectPracticeOfHierarchy(parent.getId());
		practiceList.forEach(practiceBean -> children.add(new TempTreeBean(practiceBean, parent, PRACTICE)));
		return children;
	}

	@Override
	public PracticeHierarchyBean hierarchy(int hierarchyId) {
		return new PracticeHierarchyBean(practiceHierarchyDAO.selectHyerarchy(hierarchyId));
	}

	@Override
	public boolean changeHierarchy(PracticeHierarchyBean afterHierarchyBean, int beforeHierarchyId) {
		practiceHierarchyDAO.changeHierarchy(afterHierarchyBean, beforeHierarchyId);

		return true;
	}

}
