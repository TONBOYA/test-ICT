package jp.ac.chitose.ui.content.manager;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import jp.ac.chitose.bean.TempTreeBean;
import jp.ac.chitose.bean.TreeBean;

public class ManageContentNamePanel extends Panel {

	private static final long serialVersionUID = 3374489557821378049L;

	public ManageContentNamePanel(String id){
		super(id);
	}

	public ManageContentNamePanel(String id, IModel<TreeBean> model) {
		super(id, model);
		setDefaultModel(CompoundPropertyModel.of(model));
		add(new Label("name"));
	}

	public ManageContentNamePanel(String id, TreeBean beforeHierarchy, IModel<TempTreeBean> model) {
		super(id, model);
		setDefaultModel(CompoundPropertyModel.of(model));
		add(new Label("name"));
	}
}