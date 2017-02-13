package jp.ac.chitose.ui.practice;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;

import jp.ac.chitose.bean.PracticePageBean;
import jp.ac.chitose.service.Interface.IAccountService;
import jp.ac.chitose.service.Interface.IContentService;
import jp.ac.chitose.service.Interface.IPracticeService;
import jp.ac.chitose.ui.DamiyPageForDistribution;
import jp.ac.chitose.ui.ParentPage;
import jp.ac.chitose.ui.content.content.ContentPageForDistribution;

public class PracticePageForDistribution extends ParentPage {
	private static final long serialVersionUID = 6439566802315569193L;

	@Inject
	private IPracticeService practiceService;

	@Inject
	private IContentService contentService;

	@Inject
	private IAccountService accountService;

	@Override
	public String getTitle() {
		return "配布用実践閲覧ページ";
	}

	public PracticePageForDistribution(int practiceId) {

		// 実践モデル
		IModel<PracticePageBean> thisPracticeModel = Model.of(practiceService.selectPractice(practiceId));
		setDefaultModel(CompoundPropertyModel.of(thisPracticeModel));

		int contentId = 0;
		String contentName = "現在関連のあるコンテンツはありません";

		try {
			contentId = practiceService.selectContentId(practiceId);
			contentName = contentService.selectContent(contentId).getName();
		} catch (NullPointerException e) {

		};

		// TopPageリンクパネル
		WebMarkupContainer topPanel = new WebMarkupContainer("top");
		topPanel.add(new Link<Void>("topLink") {
			private static final long serialVersionUID = -999788449542738436L;

			@Override
			public void onClick() {
				setResponsePage(PracticePage.class);
			}
		});
		add(topPanel);
		topPanel.setVisible(true);

		// 検索リンクパネル
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

		// コンテンツリンクパネル
		WebMarkupContainer contentPanel = new WebMarkupContainer("content");
		contentPanel.add(new Link<Void>("contentLink") {
			private static final long serialVersionUID = -999788449542738436L;

			@Override
			public void onClick() {
				try {
					setResponsePage(new ContentPageForDistribution(practiceService.selectContentId(practiceId)));
				} catch (NullPointerException e) {
					setResponsePage(new DamiyPageForDistribution());
				}
			}
		}.add(new Label("contentLabel", contentName)).setVisibilityAllowed(!(contentId == 0)));
		contentPanel.add(new Label("noDataLabel", "現在関連のあるコンテンツはありません")
				.setVisibilityAllowed(contentId == 0));
		contentPanel.setVisible(true);
		add(contentPanel);


		add(new PracticePageDistributionPanel("practicePanel", thisPracticeModel));


//		WebMarkupContainer practicePanel = new WebMarkupContainer("practice");
//		add(practicePanel);
//
//		practicePanel.add(new Label("keyword", String.join("  ", thisPracticeModel.getObject().getKeyword())));
//		practicePanel.add(new Label("tag", String.join("  ", thisPracticeModel.getObject().getTag())));
//		practicePanel.add(new Label("author",
//				accountService.getPersonalBean(thisPracticeModel.getObject().getAccountId()).getName()));
//		practicePanel.add(new Label("startOn"));
//		practicePanel.add(new Label("name"));
//		practicePanel.add(new CustomMultiLineLabel("summary"));
//		practicePanel.add(new CustomMultiLineLabel("aim"));
//		practicePanel.add(new CustomMultiLineLabel("challenge"));
//		practicePanel.add(new CustomMultiLineLabel("reaction"));
//
//		practicePanel.add(new PropertyListView<UnfoldingBean>("unfoldingList") {
//			private static final long serialVersionUID = -2513092799296664907L;
//
//			@Override
//			protected void populateItem(ListItem<UnfoldingBean> item) {
//				item.add(new UnfoldingPanel("panel", item.getModel(), practiceId));
//			}
//		});
	}
}
