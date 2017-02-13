package jp.ac.chitose.ui.practice;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;

import jp.ac.chitose.bean.PracticeBean;
import jp.ac.chitose.bean.PracticePageBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.service.Interface.IPracticeService;
import jp.ac.chitose.ui.DamiyPageForDistribution;
import jp.ac.chitose.ui.ParentPage;

public class PracticeAndCategorySearchResultPage extends ParentPage {
	private static final long serialVersionUID = 7976729355035366298L;

	@Inject
	IPracticeService practiceService;

	List<PracticeBean> searchBeanList = new ArrayList<PracticeBean>();

	@Override
	public String getTitle() {
		return "検索結果ページ";
	}

	public PracticeAndCategorySearchResultPage(TreeBean parent, List<TreeBean> parents, List<String> searchList,
			String itemCategory) {

		WebMarkupContainer topPanel = new WebMarkupContainer("top");
		add(topPanel);
		topPanel.setVisible(true);

		topPanel.add(new Link<Void>("topLink") {
			private static final long serialVersionUID = -999788449542738436L;

			@Override
			public void onClick() {
				int practiceId = 0;
				setResponsePage(PracticePage.class);
			}
		});
		topPanel.add(new Link<Void>("backLink") {
			private static final long serialVersionUID = -999788449542738436L;

			@Override
			public void onClick() {
				setResponsePage(new PracticeAndCategorySearchPage(parent, parents));
			}
		});

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

		System.out.println(itemCategory);
		if (itemCategory.equals("keyword")) {
			searchBeanList = practiceService.keywordSearchOfPractice(searchList);
		} else if (itemCategory.equals("tag")) {
			searchBeanList = practiceService.tagSearchOfPractice(searchList);
		}

		ListView<PracticeBean> viewList = new PropertyListView<PracticeBean>("practiceList", searchBeanList) {
			private static final long serialVersionUID = -4505699943937945442L;

			@Override
			protected void populateItem(ListItem<PracticeBean> item) {
				item.add(new Label("num", String.valueOf(item.getModelObject().getPracticeId())));
				item.add(new PracticeLinkedNamePanel("name", item.getModel()));
			};
		};

		searchPanel.add(new Label("categoryChoiceLabel", "選択教科： " + parent.getName()));
		if (itemCategory.equals("keyword")) {
			searchPanel.add(new Label("keywordChoiceLabel", "使用したICT機器： " + String.join(", ", searchList)));
			searchPanel.add(new Label("tagChoiceLabel", "検索用キーワード： 設定なし"));
		} else if (itemCategory.equals("tag")) {
			searchPanel.add(new Label("keywordChoiceLabel", "使用したICT機器： 設定なし"));
			searchPanel.add(new Label("tagChoiceLabel", "検索キーワード： " + String.join(", ", searchList)));
		}
		searchPanel.add(viewList);
		add(searchPanel);
	}

}
