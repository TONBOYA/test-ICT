package jp.ac.chitose.dao.Interface;

import com.google.inject.ImplementedBy;

import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.dao.Class.ContentCommentDAO;

@ImplementedBy(ContentCommentDAO.class)
public interface IContentCommentDAO {

	public boolean registerContentComment(ContentPageBean contentBean);

}
