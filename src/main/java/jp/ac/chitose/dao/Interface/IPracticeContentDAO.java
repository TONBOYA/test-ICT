package jp.ac.chitose.dao.Interface;

import java.util.List;

import com.google.inject.ImplementedBy;

import jp.ac.chitose.bean.PracticeContentBean;
import jp.ac.chitose.bean.PracticeContentPageBean;
import jp.ac.chitose.dao.Class.PracticeContentDAO;
//LinkageItemBean
@ImplementedBy(PracticeContentDAO.class)
public interface IPracticeContentDAO {
	public boolean registerPracticeContent(PracticeContentPageBean bean);
	public PracticeContentBean selectContentId(int practiceId);
	public PracticeContentBean selectPracticeId(int contentId);
	public List<PracticeContentBean> selectPressPractice(int contentId);
	public boolean deletePracticeContent(int practiceId);
}
