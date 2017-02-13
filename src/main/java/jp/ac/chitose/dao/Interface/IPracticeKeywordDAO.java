package jp.ac.chitose.dao.Interface;

import java.util.List;

import com.google.inject.ImplementedBy;

import jp.ac.chitose.bean.PracticeKeywordBean;
import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.dao.Class.PracticeKeywordDAO;

@ImplementedBy(PracticeKeywordDAO.class)
public interface IPracticeKeywordDAO {

	public boolean registerPracticeKeyword(PracticeRegisterBean practiceBean);
	public List<PracticeKeywordBean> selectPracticeKeywordList(int practiceId);
	public List<PracticeKeywordBean> selectAllKeywordList();
	public boolean deletePracticeKeyword(int practiceId);
}
