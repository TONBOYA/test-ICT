package jp.ac.chitose.dao.Interface;

import java.util.List;

import com.google.inject.ImplementedBy;

import jp.ac.chitose.bean.PracticeImageBean;
import jp.ac.chitose.bean.PracticeKeywordBean;
import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.bean.UnfoldingBean;
import jp.ac.chitose.bean.UnfoldingFormBean;
import jp.ac.chitose.dao.Class.PracticeTempDAO;

@ImplementedBy(PracticeTempDAO.class)
public interface IPracticeTempDAO {

	public boolean registerTempPractice(PracticeRegisterBean practiceBean);
	public boolean editTempPractice(PracticeRegisterBean practiceBean);
	public boolean deleteTempPractice(int practiceId);
	public List<UnfoldingBean> selectTempUnfoldings(int practiceId);
	public UnfoldingFormBean selectTempUnfolding(int practiceId,int ordinal);
	public boolean deleteTempUnfolding(int practiceId);
	public List<PracticeKeywordBean> selectTempPracticeKeywordList(int practiceId);
	public boolean deleteTempPracticeKeyword(int practiceId);
	public List<PracticeImageBean> searchTempImage(int unfoldingId);
	public PracticeImageBean selectTempImage(int unfoldingId,int ordinal);
	public boolean deleteTempImage(int unfoldingId);

}
