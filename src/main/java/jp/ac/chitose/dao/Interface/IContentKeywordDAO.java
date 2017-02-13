package jp.ac.chitose.dao.Interface;

import java.util.List;

import com.google.inject.ImplementedBy;

import jp.ac.chitose.bean.ContentKeywordBean;
import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.dao.Class.ContentKeywordDAO;

@ImplementedBy(ContentKeywordDAO.class)
public interface IContentKeywordDAO {

	public boolean registerContentKeyword(ContentPageBean contentBean);
	public List<ContentKeywordBean> selectContentKeywordList(int contentId);
	public List<ContentKeywordBean> searchContent(String keyword);
	public List<ContentKeywordBean> selectAllKeywordList();
	public boolean deleteContentKeyword(int contentId);

}
