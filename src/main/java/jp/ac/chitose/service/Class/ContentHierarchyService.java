package jp.ac.chitose.service.Class;

import static jp.ac.chitose.option.TreeType.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.inject.Inject;

import jp.ac.chitose.bean.ContentBean;
import jp.ac.chitose.bean.ContentHierarchyBean;
import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.bean.TempTreeBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.dao.Interface.IContentDAO;
import jp.ac.chitose.dao.Interface.IContentHierarchyDAO;
import jp.ac.chitose.option.TreeType;
import jp.ac.chitose.service.Interface.IContentHierarchyService;

public class ContentHierarchyService implements IContentHierarchyService {
	List<TreeBean> treeList = new ArrayList<TreeBean>();

	TreeType TREE = TreeType.HIERARCHY;
	TreeType CATEGORY = TreeType.CATEGORY;
	TreeType CONTENT = TreeType.CONTENT;
	TreeType ROOT = TreeType.ROOT;

	@Inject
	IContentHierarchyDAO contentHierarchyDAO;

	@Inject
	IContentDAO contentDAO;

	@Override
	public List<TreeBean> makeContentTree() {
		treeList = new ArrayList<>();

		TreeBean root = new TreeBean(contentHierarchyDAO.searchRoot(), ROOT);

		List<ContentHierarchyBean> rootList = contentHierarchyDAO.searchChildren(root.getId());

		rootList.forEach(bean -> treeList.add(new TreeBean(bean, TREE)));

		treeList.forEach(bean -> searchChildHierarchy(bean));

		return treeList;
	}

	@Override
	public void searchChildHierarchy(TreeBean parent) {
		List<ContentHierarchyBean> children = contentHierarchyDAO.searchChildren(parent.getId());
		List<ContentBean> contentChild = contentDAO.selectContentOfHierarchy(parent.getId());
		if (!children.isEmpty()) {
			children.forEach(child -> {
				searchChildHierarchy(new TreeBean(child, parent, TREE));
			});
		}
		if (!contentChild.isEmpty()) {
			contentChild.forEach(child -> new TreeBean(child, parent, CONTENT));
		}
	}

	@Override
	public TreeBean getTree(String id) {
		System.out.println("動いたよよおおお");
		if (treeList.isEmpty()) {
			System.out.println("動いたよ");
			treeList = makeContentTree();
		}
		return findTree(treeList, id);
	}

	@Override
	public TreeBean findTree(List<TreeBean> treeList, String id) {
		System.out.println("id = "+ id);
		for (TreeBean tree : treeList) {
			System.out.println("tree = "+ tree.getName());
			if (tree.getName().equals(id)) {
				System.out.println("動いたよよよおおおあああ");
				return tree;
			}

			TreeBean temp = findTree(tree.getChildren(), id);
			System.out.println("temp = "+temp);
			if (temp != null) {
				System.out.println("動いたよぜよ");
				return temp;
			}
		}

		return null;
	}

	@Override
	public List<TreeBean> searchChildrenCategory(TreeBean parent) {
		List<TreeBean> children = new ArrayList<>();
		List<ContentHierarchyBean> list = contentHierarchyDAO.searchChildren(parent.getId());
		list.stream().forEach(hierarchy -> {
				children.add(new TreeBean(hierarchy, parent, CATEGORY));

		});
		List<ContentBean> contentList = contentDAO.selectContentOfHierarchy(parent.getId());
		contentList.forEach(contentBean -> children.add(new TreeBean(contentBean, parent, PRACTICE)));
		return children;
	}

	@Override
	public List<TreeBean> searchChildren(TreeBean parent) {
		List<TreeBean> children = new ArrayList<>();
		List<ContentHierarchyBean> list = contentHierarchyDAO.searchChildren(parent.getId());
		list.forEach(contentHierarchy -> children.add(new TreeBean(contentHierarchy, parent, TREE)));
		List<ContentBean> contentList = contentDAO.selectContentOfHierarchy(parent.getId());
		contentList.forEach(contentBean -> children.add(new TreeBean(contentBean, parent, CONTENT)));
		return children;
	}

	@Override
	public TreeBean searchRoot() {
		return new TreeBean(contentHierarchyDAO.searchRoot(), ROOT);
	}

	@Override
	public String makeContentHierarchyPath(TreeBean parent) {
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

		List<String> pathList = parents.stream().map(bean -> bean.getName()).collect(Collectors.toList());

		Collections.reverse(pathList);
		return String.join(" / ", pathList);
	}

	public String theCurrentHierarchy(TreeBean parent) {

		String hierarchy = parent.getName();
		return hierarchy;
	}

	@Override
	public List<TreeBean> selectParent(int parentHierarchyId) {
		// TreeBean parent = new TreeBean();
		// List<TreeBean> parents = new ArrayList<TreeBean>();
		return null;
	}

	@Override // isNotExistsSameContentName
	public boolean searchSameName(TreeBean parent, ContentPageBean content) {
		List<TreeBean> searchList = searchChildren(parent);
		boolean result = true;
		if (searchList != null) {
			for (TreeBean bean : searchList) {
				if (bean.getTreeType() == CONTENT && bean.getName().equals(content.getName())) {
					result = false;
				}
			}
		}
		return result;
	}

	@Override
	public TempTreeBean searchSubRoot() {
		return new TempTreeBean(contentHierarchyDAO.searchRoot(), ROOT);
	}

	@Override
	public List<TempTreeBean> searchSubChildren(TempTreeBean parent) {
		List<TempTreeBean> children = new ArrayList<>();
		List<ContentHierarchyBean> list = contentHierarchyDAO.searchChildren(parent.getId());
		list.forEach(contentHierarchy -> children.add(new TempTreeBean(contentHierarchy, parent, TREE)));
		List<ContentBean> contentList = contentDAO.selectContentOfHierarchy(parent.getId());
		contentList.forEach(contentBean -> children.add(new TempTreeBean(contentBean, parent, CONTENT)));
		return children;
	}

	@Override
	public boolean changeHierarchy(ContentHierarchyBean afterHierarchyBean,int beforeHierarchyId) {
		contentHierarchyDAO.changeHierarchy(afterHierarchyBean,beforeHierarchyId);

		return true;
	}

	@Override
	public ContentHierarchyBean hierarchy(int hierarchyId) {
		return contentHierarchyDAO.selectHyerarchy(hierarchyId);
	}

	@Override
	public int lastHierarchy() {
		return contentHierarchyDAO.searchLastHierarchy();
	}

}
