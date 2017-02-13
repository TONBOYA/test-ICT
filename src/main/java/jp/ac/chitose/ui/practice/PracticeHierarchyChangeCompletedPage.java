package jp.ac.chitose.ui.practice;

import org.apache.wicket.markup.html.link.Link;

import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.ui.ParentPage;

public class PracticeHierarchyChangeCompletedPage extends ParentPage{
	private static final long serialVersionUID = -4710100655344682447L;
	@Override
	public String getTitle() {
		return "実践登録完了ページ";
	}
	public PracticeHierarchyChangeCompletedPage(TreeBean parent, String category) {
		add(new Link<Void>("returnManagePracticePage"){
			private static final long serialVersionUID = -1234471348680369110L;
			@Override
			public void onClick() {
				setResponsePage(new ManagePracticePage(parent, category));
			}
		});
	}
}
