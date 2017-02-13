package jp.ac.chitose.service.Interface;

import java.util.List;

import com.google.inject.ImplementedBy;

import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.bean.UnfoldingFormBean;
import jp.ac.chitose.service.Class.PracticeTempService;

@ImplementedBy(PracticeTempService.class)
public interface IPracticeTempService {

	public boolean registerTempPractice(PracticeRegisterBean practiceBean);
	public List<UnfoldingFormBean> registerTempUnfoldingList(int unfoldingNumber);
	public boolean editTempPractice(PracticeRegisterBean practiceBean);
//	public PracticeBean makeTempPractice(int practiceId);

}
