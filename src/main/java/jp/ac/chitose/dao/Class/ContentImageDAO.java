package jp.ac.chitose.dao.Class;

import java.sql.SQLException;

import jp.ac.chitose.DBCP;
import jp.ac.chitose.bean.ContentImageBean;
import jp.ac.chitose.dao.Interface.IContentImageDAO;

import org.sql2o.Connection;

import com.google.inject.Inject;

public class ContentImageDAO implements IContentImageDAO{

	@Inject
	DBCP dbcp;

	@Override
	public ContentImageBean searchContentImage(int contentId) {
		ContentImageBean contentImage = new ContentImageBean();

		try(Connection con = dbcp.getSql2o().open()){
			String sql = "SELECT * from content_image where content_id = :contentId";
			contentImage = con.createQuery(sql).addParameter("contentId", contentId).setAutoDeriveColumnNames(true).executeAndFetchFirst(ContentImageBean.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return contentImage;
	}

	@Override
	public ContentImageBean selectContentImage(int contentId, int ordinal) {
		ContentImageBean contentImage = new ContentImageBean();
		try(Connection con = dbcp.getSql2o().open()){
				String sql = "SELECT * from content_image where content_id = :contnentId and ordinal = :ordinal";
				contentImage = con.createQuery(sql).addParameter("contentId", contentId).addParameter("ordinal", ordinal).setAutoDeriveColumnNames(true).executeAndFetchFirst(ContentImageBean.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contentImage;
	}

	@Override
	public boolean deleteContentImage(int contentId) {

		try(Connection con = dbcp.getSql2o().open()){
			String sql = "DLETE from content_image where content_id = :contentId";
			con.createQuery(sql).addParameter("contentId", contentId).executeUpdate();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

}
