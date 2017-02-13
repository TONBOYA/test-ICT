package jp.ac.chitose.ui.content.content;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;

import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.service.Interface.IAccountService;
import jp.ac.chitose.service.Interface.IContentService;
import jp.ac.chitose.service.Interface.IFileService;
import jp.ac.chitose.ui.ParentPage;

public class ContentPage extends ParentPage {
	private static final long serialVersionUID = -5779545647192480509L;

	@Inject
	IContentService contentService;

	@Inject
	IAccountService accountService;

	@Inject
	private IFileService fileService;

	@Override
	public String getTitle() {
		return "コンテンツページ";
	}

	public ContentPage() {
		IModel<ContentPageBean> contentModel = Model.of(contentService.selectContent(1));
		setDefaultModel(CompoundPropertyModel.of(contentModel));

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

		add(new ContentTreePanel("contentTreePanel"));

		add(new ContentPageForDistributionPanel("contentPanel",contentModel));
	}
}
