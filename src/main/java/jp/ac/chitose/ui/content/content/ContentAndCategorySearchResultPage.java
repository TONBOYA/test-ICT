package jp.ac.chitose.ui.content.content;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;

import com.google.inject.Inject;

import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.bean.PracticeBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.service.Interface.IContentService;
import jp.ac.chitose.ui.ParentPage;

public class ContentAndCategorySearchResultPage  extends ParentPage {
	private static final long serialVersionUID = 7976729355035366298L;

	@Inject
	IContentService contentService;

	List<ContentPageBean> searchBeanList = new ArrayList<ContentPageBean>();

	List<PracticeBean> list = new ArrayList<PracticeBean>();

	@Override
	public String getTitle() {
		return "検索結果ページ";
	}



	public ContentAndCategorySearchResultPage(TreeBean parent, List<TreeBean>parents, List<String> searchList, String itemCategory){

		WebMarkupContainer topPanel = new WebMarkupContainer("top");
		topPanel.add(new Link<Void>("topLink") {
			private static final long serialVersionUID = -999788449542738436L;

			@Override
			public void onClick() {
				setResponsePage(new ContentPage());
			}
		});
		WebMarkupContainer backPanel = new WebMarkupContainer("back");
		backPanel.add(new Link<Void>("backLink") {
			private static final long serialVersionUID = -999788449542738436L;
			@Override
			public void onClick()
				{setResponsePage(new ContentAndCategorySearchPage(parent,parents));}
		});
		add(topPanel);
		add(backPanel);
		topPanel.setVisible(true);
		backPanel.setVisible(true);

		add(new ContentTreePanel("contentTreePanel"));

		WebMarkupContainer searchPanel = new WebMarkupContainer("search");

		if(itemCategory.equals("keyword")){
			System.out.println(itemCategory);
			searchBeanList = contentService.keywordSearchOfContent(searchList);
			System.out.print(searchBeanList);
		}else if(itemCategory.equals("tag")){
			System.out.println(itemCategory);
			searchBeanList = contentService.tagSearchOfContent(searchList);
			System.out.print(searchBeanList);
					}

		ListView<ContentPageBean> viewList = new PropertyListView<ContentPageBean>("contentList", searchBeanList) {
			private static final long serialVersionUID = -4505699943937945442L;
			@Override
			protected void populateItem(ListItem<ContentPageBean> item) {
				item.add(new Label("num", String.valueOf(item.getModelObject().getContentId())));
				item.add(new ContentLinkedNamePanel("name", item.getModel()));
			};
		};
		searchPanel.add(viewList);
		searchPanel.add(new Label("categoryChoiceLabel","選択教科： " + parent.getName()));
		if(itemCategory.equals("keyword")){
			searchPanel.add(new Label("keywordChoiceLabel","使用したICT機器： " + String.join(", ", searchList)));
			searchPanel.add(new Label("tagChoiceLabel","検索用キーワード： 設定なし"));
		}else if(itemCategory.equals("tag")){
			searchPanel.add(new Label("keywordChoiceLabel","使用したICT機器： 設定なし"));
			searchPanel.add(new Label("tagChoiceLabel","検索用キーワード： " + String.join(", ", searchList)));
		}
		add(searchPanel);
	}

}

