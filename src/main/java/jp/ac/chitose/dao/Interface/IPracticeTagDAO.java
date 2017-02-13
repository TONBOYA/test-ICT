package jp.ac.chitose.dao.Interface;

import java.util.List;

import com.google.inject.ImplementedBy;

import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.bean.PracticeTagBean;
import jp.ac.chitose.dao.Class.PracticeTagDAO;

@ImplementedBy(PracticeTagDAO.class)
public interface IPracticeTagDAO {

	public boolean registerPracticeTag(PracticeRegisterBean practiceBean);
	public List<PracticeTagBean> selectPracticeTagList(int practiceId);
	public List<PracticeTagBean> selectCategoryTagList(String category);
	public List<PracticeTagBean> selectAllTagList();
	public boolean deletePracticeTag(int practiceId);
}
