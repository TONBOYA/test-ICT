package jp.ac.chitose.ui.content.content;

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

import jp.ac.chitose.bean.StyleCustomTreeContent;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.bean.TreeExpansion;
import jp.ac.chitose.service.Interface.IContentHierarchyService;

public class ContentHierarchyPanel extends Panel {
	private static final long serialVersionUID = 5893480577862123537L;

	@Inject
	private IContentHierarchyService contentHierarchyService;

	private NestedTree<TreeBean> tree;

	private StyleCustomTreeContent<TreeBean> content;


	public ContentHierarchyPanel(String id) {
		super(id);
		ITreeProvider<TreeBean> treeProvider = new ITreeProvider<TreeBean>() {
			private static final long serialVersionUID = 4156516260122115496L;
			@Override
			public void detach() {
				System.out.println("1");
			}

			@Override
			public Iterator<? extends TreeBean> getChildren(TreeBean parent) {
				System.out.println("2");
				return parent.getChildren().iterator();
			}

			@Override
			public Iterator<? extends TreeBean> getRoots() {
				System.out.println("3");
				return contentHierarchyService.makeContentTree().iterator();
			}

			@Override
			public boolean hasChildren(TreeBean parent) {
				System.out.println("4");
				return !parent.getChildren().isEmpty();
			}

			@Override
			public IModel<TreeBean> model(TreeBean treeBean) {
				System.out.println("5");
				return new TreeModel(treeBean);

			}

			class TreeModel extends LoadableDetachableModel<TreeBean>{
				private static final long serialVersionUID = -5165319990278063044L;
				private String treeName;

				public TreeModel(TreeBean tree) {
					super(tree);
					System.out.println("6");
					treeName = tree.getName();
					System.out.println(treeName);
				}

				@Override
				protected TreeBean load() {
					System.out.println("7");
					return contentHierarchyService.getTree(treeName);
				}

				@Override
				public boolean equals(Object obj){
					System.out.println("8");
					if(obj instanceof TreeModel){
						System.out.println("9");
						return ((TreeModel)obj).treeName.equals(treeName);
					}
					System.out.println("10");
					return false;
				}

				@Override
				public int hashCode(){
					System.out.println("11");
					return treeName.hashCode();
				}

			}
		};
//treeの中のファイルを開いてるか開いてないか、移動後も開いたまま保存してある
		tree = new NestedTree<TreeBean>("tree",treeProvider,new TreeExpansionModel()) {
			private static final long serialVersionUID = -5932787695382751899L;

			@Override
			protected Component newContentComponent(String id,
					IModel<TreeBean> model) {
				System.out.println("12");
				return ContentHierarchyPanel.this.newContentComponent(id, model);
			}
		};
		System.out.println("13");
		tree.add(new WindowsTheme());

		content = new StyleCustomTreeContent<TreeBean>() {
			private static final long serialVersionUID = 4881822228565060969L;

			//121開いているものが末端であればページ移動
			@Override
			public void onLeafClick(AjaxRequestTarget target,
					IModel<TreeBean> model) {

				//model.getObject()でクリックしたleafのTreeBeanを取得
				switch (model.getObject().getTreeType()) {
				case HIERARCHY:
					System.out.println("50");
					break;

				case CONTENT:
//					if(false){};
					System.out.println("100");
					nextContentPage(model.getObject().getId());
//					setResponsePage(new PracticePage(model.getObject().getId()));
					break;
				default:
					break;
				}
			}

//トップページにパネルで移動

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

				case CONTENT:
					return "tree-folder-other";

				default:
					return "tree-folder-other";
				}

			}

		};

		add(tree);


	}

	protected Component newContentComponent(String id,IModel<TreeBean> model){
		System.out.println("14");
		return content.newContentComponent(id, tree, model);
	}


	private class TreeExpansionModel extends AbstractReadOnlyModel<Set<TreeBean>>{
		private static final long serialVersionUID = -4660083619547621566L;

		@Override
		public Set<TreeBean> getObject(){
			return TreeExpansion.get();
		}
	}

	protected void nextContentPage(int id) {
	}

}
