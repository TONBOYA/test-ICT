package jp.ac.chitose.ui.practice;



import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

import com.google.inject.Inject;

import jp.ac.chitose.bean.PracticeHierarchyBean;
import jp.ac.chitose.bean.TempTreeBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.service.Interface.IHierarchyService;
import jp.ac.chitose.ui.ParentPage;

public class PracticeHierarchyChangeConfirmPage extends ParentPage{

	@Inject
	private IHierarchyService hierarchyService;

	@Override
	public String getTitle() {
		return "階層変更確認ページ";
	}

	public PracticeHierarchyChangeConfirmPage(TreeBean beforeHierarchy, TempTreeBean afterHierarchy,int lastOrdinal, String category){
		PracticeHierarchyBean hierarchy = hierarchyService.hierarchy(beforeHierarchy.getId());
		hierarchy.setOneLevelUp(afterHierarchy.getId());
		hierarchy.setOrdinal(lastOrdinal);
		System.out.println(hierarchy);
		add(new Label("beforeHierarchyName", beforeHierarchy.getName()));
		add(new Label("afterHierarchyName", afterHierarchy.getName()));

		add(new Link<Void>("toChangeHierarchyCompletedPage") {
			static final long serialVersionUID = -4214959807629581609L;
			@Override
			public void onClick() {

					hierarchyService.changeHierarchy(hierarchy, beforeHierarchy.getId());

					setResponsePage(new PracticeHierarchyChangeCompletedPage(beforeHierarchy,category));
			}
		});
	}

}
