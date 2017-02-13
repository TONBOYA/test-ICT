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
import jp.ac.chitose.service.Interface.IContentHierarchyService;
import jp.ac.chitose.service.Interface.IHierarchyService;
import jp.ac.chitose.service.Interface.IPracticeService;
import jp.ac.chitose.service.Interface.IPracticeTempService;
import jp.ac.chitose.ui.ParentPage;

public class PracticeRegisterConfirmPage extends ParentPage {

	@Inject
	IHierarchyService hierarchyService;

	@Inject
	IPracticeService practiceService;

	@Inject
	IPracticeTempService practiceTempService;

	@Inject
	private IContentHierarchyService contentHierarchyService;

	PageType TYPE = PageType.REGISTER;

	private static final long serialVersionUID = 2799409870537686281L;

	public PracticeRegisterConfirmPage(TreeBean parent,IModel<PracticeRegisterBean> practiceModel){
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
		add(new Label("keyword", String.join(",  ", practiceModel.getObject().getKeyword())));
		add(new Label("tag", String.join(",  ", practiceModel.getObject().getTag())));

		ListView<UnfoldingFormBean> unfoldings = new PropertyListView<UnfoldingFormBean>("list",practiceModel.getObject().getUnfoldingList()) {

			private static final long serialVersionUID = 9101904819978658664L;

			@Override
			protected void populateItem(ListItem<UnfoldingFormBean> item) {
				item.add(new UnfoldingPreviewPanel("panel",item.getModel(),practiceModel.getObject().getAccountId()));
			}
		};
		add(unfoldings);

		add(new Link<Void>("toPracticeContentPage"){
			private static final long serialVersionUID = 1000370685618617097L;

			@Override
			public void onClick() {
				if(hierarchyService.searchSameName(parent, practiceModel.getObject())){

					setResponsePage(new PracticeContentPage(parent,practiceModel, TYPE));
				}else{
					setResponsePage(new PracticeRegisterConfirmPage(parent,practiceModel));
					error("同じ名前の実践が同じ階層にあります。実践名を変更してください。");
				}
			};
		});

		add(new Link<Void>("toRegisterCompletedPage"){
			private static final long serialVersionUID = -7623251497583018942L;

			@Override
			public void onClick() {
				practiceService.registerPractice(practiceModel.getObject());
				setResponsePage(new PracticeRegisterCompletedPage(parent, practiceModel.getObject().getCategory()));
			};
		});


		add(new Link<Void>("returnRegisterPracticePage"){
			private static final long serialVersionUID = -7623251497583018942L;

			@Override
			public void onClick() {
				setResponsePage(new PracticeRegisterPage(parent,practiceModel));
			};
		});
	}

	@Override
	public String getTitle() {
		return "実践登録確認ページ";
	}
}
