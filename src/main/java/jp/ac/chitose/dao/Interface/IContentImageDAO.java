package jp.ac.chitose.dao.Interface;

import jp.ac.chitose.bean.ContentImageBean;
import jp.ac.chitose.dao.Class.ContentImageDAO;

import com.google.inject.ImplementedBy;

@ImplementedBy(ContentImageDAO.class)
public interface IContentImageDAO {
	public ContentImageBean searchContentImage(int contentId);
	public ContentImageBean selectContentImage(int contentId,int ordinal);
	public boolean deleteContentImage(int contentId);
}
