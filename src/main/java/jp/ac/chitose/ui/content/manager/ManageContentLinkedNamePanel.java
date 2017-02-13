package jp.ac.chitose.ui.content.manager;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import jp.ac.chitose.bean.TempTreeBean;
import jp.ac.chitose.bean.TreeBean;

public class ManageContentLinkedNamePanel extends Panel{
	private static final long serialVersionUID = 5695390476595346040L;


	public ManageContentLinkedNamePanel(String id,IModel<TreeBean> model, String category){
		super(id,model);

		setDefaultModel(CompoundPropertyModel.of(model));
		add(new Link<Void>("nextHierarchy") {
			private static final long serialVersionUID = -8246836591547089194L;

			@Override
			public void onClick() {
				setResponsePage(new ManageContentPage(model.getObject(), category));
			}
		}.add(new Label("name")));
	}

	public ManageContentLinkedNamePanel(String id, TreeBean beforeHierarchy, IModel<TempTreeBean> model, String category){
		super(id,model);

		setDefaultModel(CompoundPropertyModel.of(model));
		add(new Link<Void>("nextHierarchy") {
			private static final long serialVersionUID = -8246836591547089194L;

			@Override
			public void onClick() {
				setResponsePage(new ContentHierarchyChangePage(beforeHierarchy, model.getObject(), category));
			}
		}.add(new Label("name")));
	}
}