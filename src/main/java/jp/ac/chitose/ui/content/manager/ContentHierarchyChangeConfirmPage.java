package jp.ac.chitose.ui.content.manager;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

import com.google.inject.Inject;

import jp.ac.chitose.bean.ContentHierarchyBean;
import jp.ac.chitose.bean.TempTreeBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.service.Interface.IContentHierarchyService;
import jp.ac.chitose.ui.ParentPage;

public class ContentHierarchyChangeConfirmPage extends ParentPage{
	private static final long serialVersionUID = -7780700314139050967L;

	@Inject
	private IContentHierarchyService contentHierarchyService;

	@Override
	public String getTitle() {
		return "階層変更確認ページ";
	}

	public ContentHierarchyChangeConfirmPage(TreeBean beforeHierarchy, TempTreeBean afterHierarchy,int lastOrdinal, String category){
		ContentHierarchyBean hierarchy = contentHierarchyService.hierarchy(beforeHierarchy.getId());
		hierarchy.setOneLevelUp(afterHierarchy.getId());
		hierarchy.setOrdinal(lastOrdinal);
		System.out.println(hierarchy);
		add(new Label("beforeHierarchyName", beforeHierarchy.getName()));
		add(new Label("afterHierarchyName", afterHierarchy.getName()));

		add(new Link<Void>("toChangeHierarchyCompletedPage") {
			static final long serialVersionUID = -4214959807629581609L;
			@Override
			public void onClick() {

					contentHierarchyService.changeHierarchy(hierarchy, beforeHierarchy.getId());
					setResponsePage(new ContentHierarchyChangeCompletedPage(beforeHierarchy, category));
			}
		});
	}

}
