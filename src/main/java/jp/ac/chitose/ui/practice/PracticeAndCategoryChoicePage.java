package jp.ac.chitose.ui.practice;

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

import jp.ac.chitose.bean.PracticePageBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.service.Interface.IHierarchyService;
import jp.ac.chitose.service.Interface.IPracticeService;
import jp.ac.chitose.ui.DamiyPageForDistribution;
import jp.ac.chitose.ui.ParentPage;
import lombok.val;

public class PracticeAndCategoryChoicePage extends ParentPage{
	private static final long serialVersionUID = -367019324574699028L;

	@Inject
	IPracticeService practiceService;
	@Inject
	private IHierarchyService hierarchyService;

	@Override
	public String getTitle() {
		return "項目と教科を選択するページ";
	}

	public PracticeAndCategoryChoicePage(){

		TreeBean parent = hierarchyService.searchRoot();

		WebMarkupContainer topPanel = new WebMarkupContainer("top");
		topPanel.add(new Link<Void>("topLink") {
			private static final long serialVersionUID = -999788449542738436L;

			@Override
			public void onClick() {
				int practiceId = 0;
				setResponsePage(PracticePage.class);
			}
		});
		add(topPanel);
		topPanel.setVisible(true);

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

		val parents = hierarchyService.searchChildren(parent);
		ListView<TreeBean> categoryList = new PropertyListView<TreeBean>("category", parents) {
			private static final long serialVersionUID = -4505699943937945442L;
			@Override
			protected void populateItem(ListItem<TreeBean> item) {
					item.add(new PracticeLinkedNamePanel("categoryName", item.getModel(), parents));
			}
		};

		WebMarkupContainer searchPanel = new WebMarkupContainer("search");
		searchPanel.add(new Label("categoryLabel","カテゴリー選択: "));
		searchPanel.add(categoryList);
		add(searchPanel);
	}

}
