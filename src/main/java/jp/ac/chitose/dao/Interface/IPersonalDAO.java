package jp.ac.chitose.dao.Interface;

import com.google.inject.ImplementedBy;

import jp.ac.chitose.dao.Class.PersonalDAO;

@ImplementedBy(PersonalDAO.class)
public interface IPersonalDAO {
		public int updatePersonal(int accountId,String name,String belongSchool,String emainAddress);
		public boolean deletePersonal(int accountId);
}