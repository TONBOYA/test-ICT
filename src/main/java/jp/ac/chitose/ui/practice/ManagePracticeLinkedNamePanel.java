package jp.ac.chitose.ui.practice;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import jp.ac.chitose.bean.TempTreeBean;
import jp.ac.chitose.bean.TreeBean;

public class ManagePracticeLinkedNamePanel extends Panel {
	private static final long serialVersionUID = 5470611499579548286L;

	public ManagePracticeLinkedNamePanel(String id,IModel<TreeBean> model, String category){
		super(id,model);
		setDefaultModel(CompoundPropertyModel.of(model));
		add(new Link<Void>("nextHierarchy") {
			private static final long serialVersionUID = -4619289878813277763L;
			@Override
			public void onClick() {
				setResponsePage(new ManagePracticePage(model.getObject(),category));
			}
		}.add(new Label("name")));
	}

	public ManagePracticeLinkedNamePanel(String id, TreeBean beforeHierarchy, IModel<TempTreeBean> model, String category){
		super(id,model);
		setDefaultModel(CompoundPropertyModel.of(model));
		add(new Link<Void>("nextHierarchy") {
			private static final long serialVersionUID = -8246836591547089194L;
			@Override
			public void onClick() {
				setResponsePage(new PracticeHierarchyChangePage(beforeHierarchy, model.getObject(), category));
			}
		}.add(new Label("name")));
	}
}
