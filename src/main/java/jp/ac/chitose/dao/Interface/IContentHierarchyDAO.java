package jp.ac.chitose.dao.Interface;

import java.util.List;

import com.google.inject.ImplementedBy;

import jp.ac.chitose.bean.ContentHierarchyBean;
import jp.ac.chitose.dao.Class.ContentHierarchyDAO;

@ImplementedBy(ContentHierarchyDAO.class)
public interface IContentHierarchyDAO {
	public boolean changeHierarchy(ContentHierarchyBean afterHyararchyBean, int beforeHierarchyId);
	public ContentHierarchyBean selectHyerarchy(int hierarchyId);
	public List<ContentHierarchyBean> searchChildren(int contentHierarchyId);
	public ContentHierarchyBean searchRoot();
	public int searchLastHierarchy();
	}
