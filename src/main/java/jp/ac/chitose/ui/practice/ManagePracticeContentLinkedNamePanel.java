package jp.ac.chitose.ui.practice;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.option.PageType;

public class ManagePracticeContentLinkedNamePanel extends Panel {
	private static final long serialVersionUID = -8060945364641658940L;

	public ManagePracticeContentLinkedNamePanel(String id,IModel<TreeBean> model, IModel<PracticeRegisterBean> practiceModel, PageType TYPE){
		super(id,model);

		setDefaultModel(CompoundPropertyModel.of(model));
		add(new Link<Void>("nextHierarchy") {
			private static final long serialVersionUID = -8246836591547089194L;

			@Override
			public void onClick() {
				setResponsePage(new PracticeContentPage(model.getObject(), practiceModel, TYPE));
			}
		}.add(new Label("name")));
	}


	}
