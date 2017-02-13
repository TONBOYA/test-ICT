package jp.ac.chitose.dao.Interface;

import java.util.List;

import com.google.inject.ImplementedBy;

import jp.ac.chitose.bean.PracticeImageBean;
import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.dao.Class.PracticeImageDAO;

@ImplementedBy(PracticeImageDAO.class)
public interface IPracticeImageDAO {
	public boolean registerPracticeImage(PracticeRegisterBean practiceBean);
	public boolean editPracticeImage(PracticeRegisterBean practiceBean);
	public List<PracticeImageBean> searchImage(int unfoldingId);
	public PracticeImageBean selectImage(int unfoldingId,int ordinal);
	public boolean deleteImage(int unfoldingId);
}
