package jp.ac.chitose.service.Interface;

import java.util.List;

import com.google.inject.ImplementedBy;

import jp.ac.chitose.bean.PracticeBean;
import jp.ac.chitose.bean.PracticeImageBean;
import jp.ac.chitose.bean.PracticeKeywordBean;
import jp.ac.chitose.bean.PracticePageBean;
import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.bean.PracticeTagBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.bean.UnfoldingBean;
import jp.ac.chitose.bean.UnfoldingFormBean;
import jp.ac.chitose.service.Class.PracticeService;

@ImplementedBy(PracticeService.class)
public interface IPracticeService {

	public boolean registerPractice(PracticeRegisterBean practiceBean);

	public List<UnfoldingFormBean> registerUnfoldingList(int unfoldingNumber);

	public boolean editPractice(PracticeRegisterBean practiceBean);

	public List<PracticeImageBean> searchImage(int unfoldingId);

	public int selectContentId (int practiceId);

	public PracticePageBean selectPracticeAndContent(int practiceId);

	public PracticePageBean selectPractice(int practiceId);

	public UnfoldingFormBean selectUnfolding(int practiceId, int ordinal);

	public List<PracticeKeywordBean> selectPracticeKeyword(int practiceId);

	public List<PracticeTagBean> selectPracticeTag(int practiceId);

	public List<UnfoldingBean> selectUnfoldingList(int practiceId);

	public boolean deletePracticeList(List<TreeBean> praciticeList);

	public List<String> selectAllTagList(String category);

	public List<PracticeBean> keywordSearchOfPractice(List<String> keywords);

	public List<PracticeBean> tagSearchOfPractice(List<String> tags);

	public List<PracticeBean> searchPractice(List<String> keyword, List<String> tagList);

	public boolean deletePractice(int practiceId);

	public List<String> selectPracticeKeywordToString(int practiceId);

	public List<String> selectPracticeTagToString(int practiceId);

	public List<UnfoldingFormBean> editUnfolding(int practiceId);

	public PracticeBean makePractice(int practiceId);
}
