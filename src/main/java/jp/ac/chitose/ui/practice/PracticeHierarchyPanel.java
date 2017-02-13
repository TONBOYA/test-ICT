package jp.ac.chitose.ui.practice;

import java.util.Iterator;
import java.util.Set;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.tree.ITreeProvider;
import org.apache.wicket.extensions.markup.html.repeater.tree.NestedTree;
import org.apache.wicket.extensions.markup.html.repeater.tree.theme.WindowsTheme;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import com.google.inject.Inject;

import jp.ac.chitose.bean.PracticePageBean;
import jp.ac.chitose.bean.StyleCustomTreeContent;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.bean.TreeExpansion;
import jp.ac.chitose.service.Interface.IHierarchyService;
import jp.ac.chitose.service.Interface.IPracticeService;

public class PracticeHierarchyPanel extends Panel {
	private static final long serialVersionUID = -4139675553090674162L;

	@Inject
	IPracticeService practiceService;

	@Inject
	private IHierarchyService hierarchyService;

	private NestedTree<TreeBean> tree;

	private StyleCustomTreeContent<TreeBean> content;


	public PracticeHierarchyPanel(String id) {
		super(id);

		ITreeProvider<TreeBean> treeProvider = new ITreeProvider<TreeBean>() {
			private static final long serialVersionUID = -8080232376599372045L;

			@Override
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

			class TreeModel extends LoadableDetachableModel<TreeBean> {
				private static final long serialVersionUID = 3731779681051307933L;

				private String treeName;

				public TreeModel(TreeBean tree) {
					super(tree);
					treeName = tree.getName();
				}

				@Override
				protected TreeBean load() {
					return hierarchyService.getTree(treeName);
				}

				@Override
				public boolean equals(Object obj) {
					if (obj instanceof TreeModel) {
						return ((TreeModel) obj).treeName.equals(treeName);
					}
					return false;
				}

				@Override
				public int hashCode() {
					// System.out.println(treeName.hashCode());
					return treeName.hashCode();
				}
			}
		};

		// treeの中のファイルを開いてるか開いてないか、移動後も開いたまま保存してある
		tree = new NestedTree<TreeBean>("tree", treeProvider, new TreeExpansionModel()) {
			private static final long serialVersionUID = 4605903356194693911L;

			@Override
			protected Component newContentComponent(String id, IModel<TreeBean> model) {
				return PracticeHierarchyPanel.this.newContentComponent(id, model);
			}
		};

		tree.add(new WindowsTheme());

		content = new StyleCustomTreeContent<TreeBean>() {
			private static final long serialVersionUID = 6820037555318602275L;

			// 121開いているものが末端であればページ移動
			@Override
			public void onLeafClick(AjaxRequestTarget target, IModel<TreeBean> model) {

					// model.getObject()でクリックしたleafのTreeBeanを取得
					switch (model.getObject().getTreeType()) {
					case HIERARCHY:
						break;

					case PRACTICE:
//						newModel = Model.of(practiceService.selectPractice(model.getObject().getId()));
//						getTargetPractice(target,newModel);
						nextPracticePage(model.getObject().getId());
						break;
					default:
						break;
				}
			}

			// トップページにパネルで移動
			@Override
			public String getClosedFolderClass() {
				return "tree-folder-closed";
			}

			@Override
			public String getOpenFolderClass() {
				return "tree-folder-open";
			}

			@Override
			public String getLeafClass(TreeBean t) {
				switch (t.getTreeType()) {
				case HIERARCHY:
					return "tree-folder-open";

				case PRACTICE:
					return "tree-folder-other";

				default:
					return "tree-folder-other";
				}
			}
		};
		add(tree);
	}

	protected Component newContentComponent(String id, IModel<TreeBean> model) {
		return content.newContentComponent(id, tree, model);
	}

	private class TreeExpansionModel extends AbstractReadOnlyModel<Set<TreeBean>> {
		private static final long serialVersionUID = 6136716461916282253L;

		@Override
		public Set<TreeBean> getObject() {
			return TreeExpansion.get();
		}
	}

	protected void nextPracticePage(int id) {
	}

	protected void getTargetPractice(AjaxRequestTarget target, IModel<PracticePageBean> newModel) {
	}

	protected PracticePageBean setTargetPractice(int nextId) {
		return null;
	}

}
