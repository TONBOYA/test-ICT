package jp.ac.chitose.ui.practice;

import org.apache.wicket.markup.html.link.Link;

import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.ui.ParentPage;

public class PracticeEditCompletedPage extends ParentPage {
	private static final long serialVersionUID = 6858283886741926265L;

	@Override
	public String getTitle() {
		return "実践編集完了ページ";
	}

	public PracticeEditCompletedPage(TreeBean parent,String category) {
		add(new Link<Void>("returnManagePracticePage"){
			private static final long serialVersionUID = 3219316849990222293L;

			@Override
			public void onClick() {
				setResponsePage(new ManagePracticePage(parent, category));
			}
		});
	}
}
