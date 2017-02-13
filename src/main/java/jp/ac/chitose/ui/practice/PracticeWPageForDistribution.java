package jp.ac.chitose.ui.practice;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;

import jp.ac.chitose.bean.PracticePageBean;
import jp.ac.chitose.bean.UnfoldingBean;
import jp.ac.chitose.service.Interface.IAccountService;
import jp.ac.chitose.service.Interface.IPracticeService;
import jp.ac.chitose.ui.CustomMultiLineLabel;
import jp.ac.chitose.ui.DamiyPageForDistribution;
import jp.ac.chitose.ui.ParentPage;

public class PracticeWPageForDistribution extends ParentPage {
	private static final long serialVersionUID = 6439566802315569193L;

	@Inject
	private IPracticeService practiceService;

	@Inject
	private IAccountService accountService;

	@Override
	public String getTitle() {
		return "配布用実践閲覧ページ";
	}

	public PracticeWPageForDistribution(int practiceId) {

		IModel<PracticePageBean> practiceModel = Model.of(practiceService.selectPractice(practiceId));
		setDefaultModel(CompoundPropertyModel.of(practiceModel));

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

		add(new PracticeHierarchyPanel("practiceTreePanel") {
			private static final long serialVersionUID = 972002804967801210L;

			@Override
			protected void nextPracticePage(int id) {
				try {
					if (practiceService.selectPractice(id).isApproval() == true) {
						setResponsePage(new PracticeWPageForDistribution(id));
					} else {
						IModel<PracticePageBean> practiceModels = Model
								.of(practiceService.selectPracticeAndContent(id));
						setDefaultModel(CompoundPropertyModel.of(practiceModels));
						setResponsePage(new PracticePageForDistribution(id));
					}
				} catch (NullPointerException e) {
					setResponsePage(new DamiyPageForDistribution());
				}
			}
		});
//		IModel<PracticePageBean> thisPracticeModel = Model.of(practiceService.selectPractice(practiceId));
//		setDefaultModel(CompoundPropertyModel.of(thisPracticeModel));

		WebMarkupContainer practicePanel = new WebMarkupContainer("practice");
		add(practicePanel);

		practicePanel.add(new Link<Void>("searchPage"){
			private static final long serialVersionUID = 1748425402568425760L;
			@Override
			public void onClick() {
				setResponsePage(new PracticeAndCategoryChoicePage());
			}
		}.setVisible(false));

		practicePanel.add(new Label("keyword", String.join("  ",
				practiceModel.getObject().getKeyword())));
		practicePanel.add(new Label("tag", String.join("  ",
				practiceModel.getObject().getTag())));
		practicePanel.add(new Label("author", accountService.getPersonalBean(
				practiceModel.getObject().getAccountId()).getName()));
		practicePanel.add(new Label("startOn"));
		practicePanel.add(new Label("name"));
		practicePanel.add(new CustomMultiLineLabel("summary"));
		practicePanel.add(new CustomMultiLineLabel("aim"));
		practicePanel.add(new CustomMultiLineLabel("challenge"));
		practicePanel.add(new CustomMultiLineLabel("reaction"));
		practicePanel.add(new PropertyListView<UnfoldingBean>("unfoldingList") {
			private static final long serialVersionUID = -2513092799296664907L;
			@Override
			protected void populateItem(ListItem<UnfoldingBean> item) {
				item.add(new UnfoldingPanel("panel", item.getModel(),practiceId));
			}
		});
	}
}

