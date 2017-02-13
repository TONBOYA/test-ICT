package jp.ac.chitose.ui.practice;

import org.apache.wicket.markup.html.link.Link;

import com.google.inject.Inject;

import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.service.Interface.IAccountService;
import jp.ac.chitose.service.Interface.IHierarchyService;
import jp.ac.chitose.ui.ParentPage;

public class PracticeDeleteCompletedPage extends ParentPage {
	private static final long serialVersionUID = -5698880012086885153L;

	@Inject
	IHierarchyService hierarchyService;

	@Inject
	IAccountService accountService;

	@Override
	public String getTitle() {
		return "実践削除完了ページ";
	}

	public PracticeDeleteCompletedPage(TreeBean parent, String category) {

		add(new Link<Void>("returnManagePracticePage"){
			private static final long serialVersionUID = 7699517300728763664L;

			@Override
			public void onClick() {
				setResponsePage(new ManagePracticePage(parent,category));
			}
		});
	}
}
