package jp.ac.chitose.service.Class;

import java.io.Serializable;

import com.google.inject.Inject;

import jp.ac.chitose.bean.PracticeContentPageBean;
import jp.ac.chitose.dao.Interface.IPracticeContentDAO;
import lombok.Data;
@Data
public class PracticeContentService implements Serializable {
	private static final long serialVersionUID = 2031649144027050232L;

	@Inject
	IPracticeContentDAO practiceContentDAO;

	public boolean registerPracticeContent(PracticeContentPageBean bean){
		boolean result = practiceContentDAO.registerPracticeContent(bean);
		return result;

	}

}
