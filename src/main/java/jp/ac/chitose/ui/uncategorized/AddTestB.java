package jp.ac.chitose.ui.uncategorized;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;

import jp.ac.chitose.bean.PracticePageBean;
import jp.ac.chitose.service.Interface.IPracticeService;
import jp.ac.chitose.ui.ParentPage;
import jp.ac.chitose.ui.practice.PracticePage;

public class AddTestB extends ParentPage {

	@Inject
	private IPracticeService practiceService;

	@Override
	public String getTitle() {
		// TODO 自動生成されたメソッド・スタブ
		return "ああああ";
	}

	public AddTestB() {
		IModel<PracticePageBean> practiceModel = Model.of(practiceService.selectPractice(1));
		setDefaultModel(CompoundPropertyModel.of(practiceModel));



//		List<String> tagList = new ArrayList<String>();
//		tagList.add(practiceModel.getObject().getTag1());
//		tagList.add(practiceModel.getObject().getTag2());
//		tagList.add(practiceModel.getObject().getTag3());
//		practiceModel.getObject().setTag(tagList);

//		add(new Label("path", contentHierarchyService.makeContentHierarchyPath(parent)));
		add(new Label("name"));
		add(new Label("summary"));
		add(new Label("challenge"));
		add(new Label("reaction"));
		add(new Label("keyword", String.join(",  ", practiceModel.getObject().getKeyword())));
		add(new Label("tag", String.join(",  ", practiceModel.getObject().getTag())));



		add(new Link<Void>("toPracticeContentPage"){
			private static final long serialVersionUID = 1000370685618617097L;

			@Override
			public void onClick() {

					setResponsePage(PracticePage.class);
			};
		});

		add(new Link<Void>("toRegisterCompletedPage"){
			private static final long serialVersionUID = -7623251497583018942L;

			@Override
			public void onClick() {
				setResponsePage(PracticePage.class);
			};
		});


		add(new Link<Void>("returnRegisterPracticePage"){
			private static final long serialVersionUID = -7623251497583018942L;

			@Override
			public void onClick() {
				setResponsePage(PracticePage.class);
			};
		});
	}
}
