package jp.ac.chitose.dao.Class;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.sql2o.Connection;

import com.google.inject.Inject;

import jp.ac.chitose.IDBCP;
import jp.ac.chitose.bean.PracticeHierarchyBean;
import jp.ac.chitose.dao.Interface.IPracticeHierarchyDAO;

public class PracticeHierarchyDAO implements IPracticeHierarchyDAO {

	@Inject
	IDBCP dbcp;

	@Override
	public List<PracticeHierarchyBean> searchChildren(int practiceHierarchyId) {
		List<PracticeHierarchyBean> children = new ArrayList<PracticeHierarchyBean>();

		try(Connection con = dbcp.getSql2o().open()){
			String sql = "select * from practice_hierarchy where one_level_up = :practiceHierarchyId order by ordinal";
			children = con.createQuery(sql)
					.setAutoDeriveColumnNames(true)
					.addParameter("practiceHierarchyId", practiceHierarchyId)
					.executeAndFetch(PracticeHierarchyBean.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return children;
	}

	@Override
	public PracticeHierarchyBean searchRoot() {
		PracticeHierarchyBean root = new PracticeHierarchyBean();

		try (Connection con = dbcp.getSql2o().open()){
			String sql = "select * from practice_hierarchy where one_level_up is null";
			//カラムのアンダーバーを無視setAutoDeriveColumnNames
			//セレクトした結果一番最初に出てきたものをｐヒエラルキーにぶちこみexecuteAndFetchFirst
			root = con.createQuery(sql)
					.setAutoDeriveColumnNames(true)
					.executeAndFetchFirst(PracticeHierarchyBean.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return root;
	}

	@Override
	public PracticeHierarchyBean selectHyerarchy(int hierarchyId) {
		PracticeHierarchyBean hierarchy = new PracticeHierarchyBean();
		try (Connection con = dbcp.getSql2o().open()) {
			String sql = "select * from practice_hierarchy where practice_hierarchy_id = :id";
			hierarchy = con.createQuery(sql).addParameter("id", hierarchyId)
					.setAutoDeriveColumnNames(true)
					.executeAndFetchFirst(PracticeHierarchyBean.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hierarchy;
	}

	@Override
	public boolean changeHierarchy(PracticeHierarchyBean afterHyararchyBean, int beforeHierarchyId) {
		String updateContent = "update practice_hierarchy set practice_hierarchy_id = :practiceHierarchyId,name = :name,one_level_up = :oneLevelUp,ordinal = :ordinal where practice_hierarchy_id = :practiceHierarchyId";
		try (Connection con = dbcp.getSql2o().beginTransaction()) {
			con.createQuery(updateContent)
					.addParameter("practiceHierarchyId", afterHyararchyBean.getPracticeHierarchyId())
					.addParameter("name", afterHyararchyBean.getName())
					.addParameter("oneLevelUp", afterHyararchyBean.getOneLevelUp())
					.addParameter("ordinal", afterHyararchyBean.getOrdinal())
					.addParameter("practiceHierarchyId", beforeHierarchyId)
					.executeUpdate();
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public PracticeHierarchyBean searchParent(int practiceHierarchyId) {
		PracticeHierarchyBean root = new PracticeHierarchyBean();

		try (Connection con = dbcp.getSql2o().open()){
			String sql = "select * from practice_hierarchy where  practice_hierarchy_id = :practiceHierarchyId";
			//カラムのアンダーバーを無視setAutoDeriveColumnNames
			//セレクトした結果一番最初に出てきたものをｐヒエラルキーにぶちこみexecuteAndFetchFirst
			root = con.createQuery(sql)
					.addParameter("practiceHierarchyId", practiceHierarchyId)
					.setAutoDeriveColumnNames(true)
					.executeAndFetchFirst(PracticeHierarchyBean.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return root;
	}
}
