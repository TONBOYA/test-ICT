package jp.ac.chitose.service.Class;

import java.util.Iterator;

import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.service.Interface.IContentHierarchyService;

import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import com.google.inject.Inject;

/**
 * TreeContent
 *
 * @author kazuki
 *
 * @param <T>
 */
public class ContentTreeProvider implements ITreeProvider<TreeBean> {
	private static final long serialVersionUID = -5229537594124958264L;
	@Inject
	IContentHierarchyService contentHierarchyService;

	public void detach() {
	}

	@Override
	public Iterator<? extends TreeBean> getChildren(TreeBean parent) {
		return parent.getChildren().iterator();
	}

	@Override
	public Iterator<? extends TreeBean> getRoots() {
		return contentHierarchyService.makeContentTree().iterator();
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
		private static final long serialVersionUID = 816817713603960468L;

		@Inject
		IContentHierarchyService contentHierarchyService;

		private String id;

		public TreeModel(TreeBean tree) {
			super(tree);
			id = tree.getName();
		}

		@Override
		protected TreeBean load() {
			return contentHierarchyService.getTree(id);
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
