package jp.ac.chitose.ui.practice;

import org.apache.wicket.markup.html.link.Link;

import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.ui.ParentPage;

public class PracticeRegisterCompletedPage extends ParentPage {
	static final long serialVersionUID = 3492282850229847851L;

	public PracticeRegisterCompletedPage(TreeBean parent, String category) {
		add(new Link<Void>("returnManagePracticePage"){
			private static final long serialVersionUID = -5524603382085206671L;

			@Override
			public void onClick() {
				setResponsePage(new ManagePracticePage(parent, category));
			}
		});
	}

	@Override
	public String getTitle() {
		return "実践登録完了ページ";
	}
}
