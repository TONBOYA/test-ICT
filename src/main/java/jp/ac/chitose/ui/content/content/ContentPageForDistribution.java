package jp.ac.chitose.ui.content.content;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;

import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.bean.PracticePageBean;
import jp.ac.chitose.option.PageType;
import jp.ac.chitose.service.Interface.IContentService;
import jp.ac.chitose.service.Interface.IPracticeService;
import jp.ac.chitose.ui.DamiyPageForDistribution;
import jp.ac.chitose.ui.ParentPage;
import jp.ac.chitose.ui.practice.PracticePageForDistribution;

public class ContentPageForDistribution extends ParentPage {
	private static final long serialVersionUID = -8377636512572312983L;

	@Inject
	private IContentService contentService;

	@Inject
	private IPracticeService practiceService;

	PageType TYPE = PageType.DISTRIBUTION;

	@Override
	public String getTitle() {
		return "配布用コンテンツ閲覧ページ";
	}

	public ContentPageForDistribution(int contentId) {
		IModel<ContentPageBean> contentModel = Model.of(contentService.selectContent(contentId));
		setDefaultModel(CompoundPropertyModel.of(contentModel));

		// TopPageリンクパネル
		WebMarkupContainer topPanel = new WebMarkupContainer("top");
		topPanel.add(new Link<Void>("topLink") {
			private static final long serialVersionUID = -999788449542738436L;

			@Override
			public void onClick() {
				setResponsePage(ContentPage.class);
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
				setResponsePage(new ContentAndCategoryChoicePage());
			}
		});
		add(searchPanel);
		searchPanel.setVisible(true);

		WebMarkupContainer pressPracticePanel = new WebMarkupContainer("pressPractice");
		List<PracticePageBean> practiceList = new ArrayList<PracticePageBean>();

		try {
			practiceList = contentService.selectPressPractice(contentId);

		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		pressPracticePanel.add(new PropertyListView<PracticePageBean>("practices", practiceList) {
			private static final long serialVersionUID = -4505699943937945442L;

			@Override
			protected void populateItem(ListItem<PracticePageBean> item) {

				Link<Void> data = new Link<Void>("data") {

					@Override
					public void onClick() {
						try {
							setResponsePage(new PracticePageForDistribution(item.getModelObject().getPracticeId()));
						} catch (NullPointerException e) {
							setResponsePage(new DamiyPageForDistribution());
						} catch (IllegalArgumentException ee) {
							ee.printStackTrace();
						}
					}
				};
				data.add(new Label("dataLabel", item.getModelObject().getName()));

				item.add(data);
			}
		}.setVisibilityAllowed(practiceList.size() != 0));

		pressPracticePanel
				.add(new Label("noDataLabel", "現在関連のある実践はありません").setVisibilityAllowed(practiceList.size() == 0));

		add(pressPracticePanel);

		add(new ContentPageForDistributionPanel("contentPanel", contentModel));
	}
}