package jp.ac.chitose.dao.Class;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.sql2o.Connection;

import com.google.inject.Inject;

import jp.ac.chitose.IDBCP;
import jp.ac.chitose.bean.ContentBean;
import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.dao.Interface.IContentDAO;

public class ContentDAO implements IContentDAO {

	@Inject
	private IDBCP dbcp;

	@Override
	public boolean registerContent(ContentPageBean contentBean) {
		String insertContent = "INSERT INTO content (file_name,name,input_at,content_hierarchy_id,account_id,how_to_use,ordinal,type,delete_request) VALUES(:fileName,:name,:inputAt,:contentHierarchyId,:accountId,:howToUse,:ordinal,:type,:deleteRequest) RETURNING content_id;";
		try (Connection con = dbcp.getSql2o().beginTransaction()) {
			contentBean.setContentId(con.createQuery(insertContent)
					.addParameter("fileName", contentBean.getFileName())
					.addParameter("name", contentBean.getName())
					.addParameter("inputAt", contentBean.getInputAt())
					.addParameter("contentHierarchyId", contentBean.getContentHierarchyId())
					.addParameter("accountId", contentBean.getAccountId())
					.addParameter("howToUse", contentBean.getHowToUse())
					.addParameter("ordinal", contentBean.getOrdinal())
					.addParameter("type", contentBean.getType())
					.addParameter("deleteRequest", false)
					.executeAndFetchFirst(Integer.class));
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean editContent(ContentPageBean contentBean) {
		String updateContent = "update content set file_Name = :fileName,name = :name,input_at = :inputAt,content_hierarchy_id = :contentHierarchyId,how_to_use = :howToUse where content_id = :contentId";
		try (Connection con = dbcp.getSql2o().beginTransaction()) {
			con.createQuery(updateContent)
					.addParameter("fileName", contentBean.getFileName())
					.addParameter("name", contentBean.getName())
					.addParameter("inputAt", contentBean.getInputAt())
					.addParameter("contentHierarchyId", contentBean.getContentHierarchyId())
					.addParameter("howToUse", contentBean.getHowToUse())
					.addParameter("contentId", contentBean.getContentId())
					.executeUpdate();
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteContent(int contentId) {
		try (Connection con = dbcp.getSql2o().open()) {
			String sql = "DELETE from content where content_id = :contentId";
			con.createQuery(sql).addParameter("contentId", contentId).executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteRequestContent(int contentId) {
		try (Connection con = dbcp.getSql2o().open()) {
			String sql = "update content set delete_request = :deleteRequest where content_id = :contentId";
			con.createQuery(sql)
			.addParameter("deleteRequest", true)
			.addParameter("contentId", contentId)
			.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public ContentBean selectContent(int contentId) {
		ContentBean content = new ContentBean();
		try (Connection con = dbcp.getSql2o().open()) {
			String sql = "select * from content where content_id = :id";
			content = con.createQuery(sql).addParameter("id", contentId)
					.setAutoDeriveColumnNames(true)
					.executeAndFetchFirst(ContentBean.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return content;
	}

	@Override
	public ContentPageBean searchContent(int contentId) {
		ContentPageBean content = new ContentPageBean();
		try (Connection con = dbcp.getSql2o().open()) {
			String sql = "select * from content where content_id = :id";
			content = con.createQuery(sql).addParameter("id", contentId)
					.setAutoDeriveColumnNames(true)
					.executeAndFetchFirst(ContentPageBean.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return content;
	}

	@Override
	public List<ContentBean> selectContentOfHierarchy(int contentHierarchyId) {
		List<ContentBean> contentOfHierachy = new ArrayList<ContentBean>();
		try (Connection con = dbcp.getSql2o().open()) {
			String sql = "SELECT * FROM content WHERE content_hierarchy_id = :contentHierarchyId";
			contentOfHierachy = con.createQuery(sql).setAutoDeriveColumnNames(true)
					.addParameter("contentHierarchyId", contentHierarchyId).executeAndFetch(ContentBean.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return contentOfHierachy;
	}
}
