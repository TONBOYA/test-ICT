package jp.ac.chitose.service.Class;

import java.util.Iterator;

import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import com.google.inject.Inject;

import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.service.Interface.IHierarchyService;

public class TreeProvider implements ITreeProvider<TreeBean> {

	private static final long serialVersionUID = 2467390466208172577L;
	@Inject
	IHierarchyService hierarchyService;

	public void detach() {
	}

	@Override
	public Iterator<? extends TreeBean> getChildren(TreeBean parent) {
		return parent.getChildren().iterator();
	}

	@Override
	public Iterator<? extends TreeBean> getRoots() {
		return hierarchyService.makePracticeTree().iterator();
	}

	@Override
	public boolean hasChildren(TreeBean parent) {
		return !parent.getChildren().isEmpty();
	}

	@Override
	public IModel<TreeBean> model(TreeBean treeBean) {
		return new TreeModel(treeBean);

	}

	public static class TreeModel extends LoadableDetachableModel<TreeBean> {
		private static final long serialVersionUID = 4662158944211064800L;

		@Inject
		IHierarchyService hierarchyService;

		private String id;

		public TreeModel(TreeBean tree) {
			super(tree);
			id = tree.getName();
		}

		@Override
		protected TreeBean load() {
			return hierarchyService.getTree(id);
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof TreeModel) {
				return ((TreeModel) obj).id.equals(id);
			}
			return false;
		}

		@Override
		public int hashCode() {
			return id.hashCode();
		}

	}

}

// class FooModel extends LoadableDetachableModel<TreeBean>
// {
//
// private final String id;
//
// public FooModel(TreeBean treeBean)
// {
// super(treeBean);
//
// id = treeBean.getId();
// }
//
// @Override
// protected TreeBean load()
// {
// return TreeApplication.get().getFoo(id);
// }
//
// /**
// * Important! Models must be identifyable by their contained object.
// */
// @Override
// public boolean equals(Object obj)
// {
// if (obj instanceof FooModel)
// {
// return ((FooModel)obj).id.equals(id);
// }
// return false;
// }
//
// /**
// * Important! Models must be identifyable by their contained object.
// */
// @Override
// public int hashCode()
// {
// return id.hashCode();
// }
// }
//
//
