package jp.ac.chitose.dao.Interface;

import java.util.List;

import com.google.inject.ImplementedBy;

import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.bean.ContentTagBean;
import jp.ac.chitose.dao.Class.ContentTagDAO;

@ImplementedBy(ContentTagDAO.class)
public interface IContentTagDAO {

	public boolean registerContentTag(ContentPageBean contentBean);
	public List<ContentTagBean> selectContentTagList(int contentId);
	public List<ContentTagBean> selectCategoryTagList(String category);
	public List<ContentTagBean> selectAllTagList();
	public boolean deleteContentTag(int contentId);
}
