package jp.ac.chitose.dao.Interface;

import java.util.List;

import com.google.inject.ImplementedBy;

import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.bean.UnfoldingBean;
import jp.ac.chitose.bean.UnfoldingFormBean;
import jp.ac.chitose.dao.Class.UnfoldingDAO;

@ImplementedBy(UnfoldingDAO.class)
public interface IUnfoldingDAO {
	public boolean registerUnfolding(PracticeRegisterBean practiceBean);
	public boolean editUnfolding(PracticeRegisterBean practiceBean);
	public List<UnfoldingBean> selectUnfoldings(int practiceId);
	public UnfoldingFormBean selectUnfolding(int practiceId,int ordinal);
	public boolean deleteUnfolding(int practiceId);
}
