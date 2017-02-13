package jp.ac.chitose.dao.Interface;

import java.util.List;

import com.google.inject.ImplementedBy;

import jp.ac.chitose.bean.PracticeHierarchyBean;
import jp.ac.chitose.dao.Class.PracticeHierarchyDAO;

@ImplementedBy(PracticeHierarchyDAO.class)
public interface IPracticeHierarchyDAO {
	public List<PracticeHierarchyBean> searchChildren(int practiceHierarchyId);
	public PracticeHierarchyBean searchRoot();
	public PracticeHierarchyBean searchParent(int practiceHierarchyId);
	public PracticeHierarchyBean selectHyerarchy(int hierarchyId);
	public boolean changeHierarchy(PracticeHierarchyBean afterHyararchyBean, int beforeHierarchyId);
}