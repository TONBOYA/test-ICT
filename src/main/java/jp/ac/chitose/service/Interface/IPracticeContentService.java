package jp.ac.chitose.service.Interface;

import com.google.inject.ImplementedBy;

import jp.ac.chitose.bean.PracticeContentPageBean;
import jp.ac.chitose.service.Class.PracticeContentService;

@ImplementedBy(PracticeContentService.class)
public interface IPracticeContentService {


	public boolean RegisterPracticeContent(PracticeContentPageBean bean);
}
