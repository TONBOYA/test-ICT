package jp.ac.chitose.dao.Interface;

import java.util.List;

import com.google.inject.ImplementedBy;

import jp.ac.chitose.bean.ContentBean;
import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.dao.Class.ContentDAO;

@ImplementedBy(ContentDAO.class)
public interface IContentDAO {
	public boolean registerContent(ContentPageBean contentBean);
	public boolean editContent(ContentPageBean contentBean);
	public ContentBean selectContent(int contentId);
	public ContentPageBean searchContent(int contentId);
	public List<ContentBean> selectContentOfHierarchy(int hierarchyId);
	public boolean deleteContent(int contentId);
	public boolean deleteRequestContent(int contentId);
}
