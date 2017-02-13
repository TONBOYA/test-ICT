package jp.ac.chitose.ui.practice;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;

import jp.ac.chitose.bean.PracticePageBean;
import jp.ac.chitose.service.Interface.IPracticeService;
import jp.ac.chitose.ui.DamiyPageForDistribution;
import jp.ac.chitose.ui.ParentPage;

public class PracticePage extends ParentPage {
	private static final long serialVersionUID = -6969940203019887012L;

	@Inject
	IPracticeService practiceService;

	@Override
	public String getTitle() {
		return "実践ページ";
	}

	public PracticePage() {
		IModel<PracticePageBean> practiceModel = Model.of(practiceService.selectPractice(1));
		setDefaultModel(CompoundPropertyModel.of(practiceModel));

		add(new PracticePageDistributionPanel("practicePanel", practiceModel));

		add(new PracticeHierarchyPanel("practiceTreePanel") {
			private static final long serialVersionUID = 972002804967801210L;

			@Override
			protected void nextPracticePage(int id) {

				IModel<PracticePageBean> practiceModel = Model.of(practiceService.selectPractice(id));
				setDefaultModel(CompoundPropertyModel.of(practiceModel));
				try {
						setResponsePage(new PracticePageForDistribution(id));
				} catch (NullPointerException e) {
					setResponsePage(new DamiyPageForDistribution());
				}
			}
		});

		WebMarkupContainer searchPanel = new WebMarkupContainer("search");
		searchPanel.add(new Link<Void>("searchLink") {
			private static final long serialVersionUID = -999788449542738436L;
			@Override
			public void onClick() {
				setResponsePage(new PracticeAndCategoryChoicePage());
			}
		});
		add(searchPanel);
		searchPanel.setVisible(true);
	}
}
