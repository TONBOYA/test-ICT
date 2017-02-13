package jp.ac.chitose.ui.practice;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.google.inject.Inject;

import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.bean.UnfoldingFormBean;
import jp.ac.chitose.option.PageType;
import jp.ac.chitose.service.Interface.IHierarchyService;
import jp.ac.chitose.service.Interface.IPracticeService;
import jp.ac.chitose.ui.ParentPage;

public class PracticeEditConfirmPage extends ParentPage {
	private static final long serialVersionUID = -490363993264068070L;

	@Inject
	IHierarchyService hierarchyService;

	@Inject
	IPracticeService practiceService;

	private PageType TYPE = PageType.EDIT;

	@Override
	public String getTitle() {
		return "実践編集確認ページ";
	}

	public PracticeEditConfirmPage(TreeBean parent,IModel<PracticeRegisterBean> practiceModel){
		setDefaultModel(CompoundPropertyModel.of(practiceModel));
		List<String> tagList = new ArrayList<String>();
		tagList.add(practiceModel.getObject().getTag1());
		tagList.add(practiceModel.getObject().getTag2());
		tagList.add(practiceModel.getObject().getTag3());
		practiceModel.getObject().setTag(tagList);

		add(new Label("path", hierarchyService.makePracticeHierarchyPath(parent)));
		add(new Label("name"));
		add(new Label("summary"));
		add(new Label("challenge"));
		add(new Label("reaction"));
		add(new Label("keyword",String.join(", ", practiceModel.getObject().getKeyword())));
		add(new Label("tag",String.join(", ",practiceModel.getObject().getTag())));

		ListView<UnfoldingFormBean> unfoldings = new PropertyListView<UnfoldingFormBean>("list",practiceModel.getObject().getUnfoldingList()) {
			private static final long serialVersionUID = 9101904819978658664L;

			@Override
			protected void populateItem(ListItem<UnfoldingFormBean> item) {
				item.add(new UnfoldingPreviewPanel("panel",item.getModel(),practiceModel.getObject().getAccountId(),TYPE));
			}
		};
		add(unfoldings);

		add(new Link<Void>("toPracticeContentPage"){
			private static final long serialVersionUID = 1000370685618617097L;

			@Override
			public void onClick() {

					setResponsePage(new PracticeContentPage(parent, practiceModel, TYPE));
			};
		});

		add(new Link<Void>("toEditPracticeCompletedPage"){
			private static final long serialVersionUID = 1000370685618617097L;

			@Override
			public void onClick() {
					practiceService.editPractice(practiceModel.getObject());
					setResponsePage(new PracticeEditCompletedPage(parent, practiceModel.getObject().getCategory()));

			}
		});

		add(new Link<Void>("returnEditPracticePage"){
			private static final long serialVersionUID = -3119920173245911285L;

			@Override
			public void onClick() {
				setResponsePage(new PracticeEditPage(parent, practiceModel));
			};
		});
	}
}
