package jp.ac.chitose.ui.content.content;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;

import com.google.inject.Inject;

import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.service.Interface.IContentHierarchyService;
import jp.ac.chitose.service.Interface.IContentService;
import jp.ac.chitose.ui.ParentPage;
import lombok.val;





public class ContentAndCategoryChoicePage extends ParentPage{

	@Inject
	IContentService contentService;

	@Inject
	private IContentHierarchyService contentHierarchyService;

	@Override
	public String getTitle() {
		return "項目と教科を選択するページ";
	}

	public ContentAndCategoryChoicePage(){

		TreeBean parent = contentHierarchyService.searchRoot();

		WebMarkupContainer topPanel = new WebMarkupContainer("top");
		topPanel.add(new Link<Void>("topLink") {
			private static final long serialVersionUID = -999788449542738436L;

			@Override
			public void onClick() {
				setResponsePage(new ContentPage());
			}
		});
		add(topPanel);
		topPanel.setVisible(true);

		add(new ContentTreePanel("contentTreePanel"));

		val parents = contentHierarchyService.searchChildren(parent);
		ListView<TreeBean> categoryList = new PropertyListView<TreeBean>("category", parents) {
			private static final long serialVersionUID = -4505699943937945442L;
			@Override
			protected void populateItem(ListItem<TreeBean> item) {
					item.add(new ContentLinkedNamePanel("categoryName", item.getModel(), parents));
			}
		};

		WebMarkupContainer searchPanel = new WebMarkupContainer("search");
		searchPanel.add(new Label("categoryLabel","カテゴリー選択: "));
		searchPanel.add(categoryList);
		add(searchPanel);
	}


}
