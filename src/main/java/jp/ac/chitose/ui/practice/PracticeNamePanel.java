package jp.ac.chitose.ui.practice;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import jp.ac.chitose.bean.TreeBean;

public class PracticeNamePanel extends Panel{
	private static final long serialVersionUID = 2987810374963172212L;

	public PracticeNamePanel(String id, IModel<TreeBean> model) {
		super(id, model);
		setDefaultModel(CompoundPropertyModel.of(model));
		add(new Label("name"));
	}

}
