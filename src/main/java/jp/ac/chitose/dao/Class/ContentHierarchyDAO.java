package jp.ac.chitose.dao.Class;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.sql2o.Connection;

import com.google.inject.Inject;

import jp.ac.chitose.IDBCP;
import jp.ac.chitose.bean.ContentHierarchyBean;
import jp.ac.chitose.dao.Interface.IContentHierarchyDAO;

public class ContentHierarchyDAO implements IContentHierarchyDAO {

	@Inject
	IDBCP dbcp;

	@Override
	public boolean changeHierarchy(ContentHierarchyBean afterHyararchyBean, int beforeHierarchyId) {
		String updateContent = "update content_hierarchy set content_hierarchy_id = :contentHierarchyId,name = :name,one_level_up = :oneLevelUp,ordinal = :ordinal where content_hierarchy_id = :contentHierarchyId";
		try (Connection con = dbcp.getSql2o().beginTransaction()) {
			con.createQuery(updateContent)
					.addParameter("contentHierarchyId", afterHyararchyBean.getContentHierarchyId())
					.addParameter("name", afterHyararchyBean.getName())
					.addParameter("oneLevelUp", afterHyararchyBean.getOneLevelUp())
					.addParameter("ordinal", afterHyararchyBean.getOrdinal())
					.addParameter("contentHierarchyId", beforeHierarchyId)
					.executeUpdate();
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ContentHierarchyBean selectHyerarchy(int hierarchyId) {
		ContentHierarchyBean hierarchy = new ContentHierarchyBean();
		try (Connection con = dbcp.getSql2o().open()) {
			String sql = "select * from content_hierarchy where content_hierarchy_id = :id";
			hierarchy = con.createQuery(sql).addParameter("id", hierarchyId)
					.setAutoDeriveColumnNames(true)
					.executeAndFetchFirst(ContentHierarchyBean.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hierarchy;
	}

	@Override
	public List<ContentHierarchyBean> searchChildren(int contentHierarchyId) {
		List<ContentHierarchyBean> children = new ArrayList<ContentHierarchyBean>();

		try(Connection con = dbcp.getSql2o().open()){
			String sql = "select * from content_hierarchy where one_level_up = :contentHierarchyId order by ordinal";
			children = con.createQuery(sql)
					.setAutoDeriveColumnNames(true)
					.addParameter("contentHierarchyId", contentHierarchyId)
					.executeAndFetch(ContentHierarchyBean.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return children;
	}

	@Override
	public ContentHierarchyBean searchRoot() {
		ContentHierarchyBean root = new ContentHierarchyBean();

		try (Connection con = dbcp.getSql2o().open()){
			String sql = "select * from content_hierarchy where one_level_up is null";

			root = con.createQuery(sql)
					.setAutoDeriveColumnNames(true)
					.executeAndFetchFirst(ContentHierarchyBean.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return root;
	}

	@Override
	public int searchLastHierarchy() {
		ContentHierarchyBean last = new ContentHierarchyBean();

		try (Connection con = dbcp.getSql2o().open()){
			String sql = "select * from content_hierarchy where content_hierarchy_id is lastval()";

			last = con.createQuery(sql)
					.setAutoDeriveColumnNames(true)
					.executeAndFetchFirst(ContentHierarchyBean.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return last.getContentHierarchyId();
	}

}
