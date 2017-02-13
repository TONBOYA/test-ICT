package jp.ac.chitose.dao.Interface;

import java.util.List;

import com.google.inject.ImplementedBy;

import jp.ac.chitose.bean.PracticeBean;
import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.dao.Class.PracticeDAO;


/**
 * practiceテーブルを操作するDAO
 *
 * @author kazuki
 *
 */
@ImplementedBy(PracticeDAO.class)
public interface IPracticeDAO {
	public boolean registerPractice(PracticeRegisterBean practiceBean);
	public boolean editPractice(PracticeRegisterBean practiceBean);
	public PracticeBean selectPractice(int practiceId);
	public PracticeBean searchOfPractice(int practiceId);
	public List<PracticeBean> selectPracticeOfHierarchy(int practiceHierarchyId);
	public boolean deletePractice(int practiceId);
}
