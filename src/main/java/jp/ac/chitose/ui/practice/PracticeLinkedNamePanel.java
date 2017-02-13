package jp.ac.chitose.ui.practice;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;

import jp.ac.chitose.bean.PracticeBean;
import jp.ac.chitose.bean.PracticePageBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.service.Interface.IPracticeService;
import jp.ac.chitose.ui.DamiyPageForDistribution;

public class PracticeLinkedNamePanel extends Panel {
	private static final long serialVersionUID = 4159240731830588634L;

	@Inject
	IPracticeService practiceService;

	public PracticeLinkedNamePanel(String id, IModel<TreeBean> treeModel, List<TreeBean> parents) {
		super(id, treeModel);
		setDefaultModel(CompoundPropertyModel.of(treeModel));
		add(new Link<Void>("nextSearchPage") {
			private static final long serialVersionUID = -4619289878813277763L;

			@Override
			public void onClick() {
				setResponsePage(new PracticeAndCategorySearchPage(treeModel.getObject(), parents));
			}
		}.add(new Label("name")));
	}

	public PracticeLinkedNamePanel(String id, IModel<PracticeBean> practiceModel) {
		super(id, practiceModel);
		setDefaultModel(CompoundPropertyModel.of(practiceModel));
		add(new Link<Void>("nextSearchPage") {
			private static final long serialVersionUID = -4619289878813277763L;

			@Override
			public void onClick() {

				IModel<PracticePageBean> practicePageModel = Model
						.of(practiceService.selectPractice(practiceModel.getObject().getPracticeId()));
				setDefaultModel(CompoundPropertyModel.of(practicePageModel));

				try {
					setResponsePage(new PracticePageForDistribution(practicePageModel.getObject().getPracticeId()));
				} catch (NullPointerException e) {
					setResponsePage(new DamiyPageForDistribution());
				}
			}
		}.add(new Label("name")));
	}

}
