package jp.ac.chitose.ui.content.manager;

import org.apache.wicket.markup.html.link.Link;

import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.ui.ParentPage;

public class ContentEditCompletedPage extends ParentPage {
	private static final long serialVersionUID = 3708114944613285877L;

	@Override
	public String getTitle() {
		return "コンテンツ編集完了ページ";
	}

	public ContentEditCompletedPage(TreeBean parent, String category) {
		add(new Link<Void>("returnManageContentPage"){
			private static final long serialVersionUID = 3759350343134878951L;

			@Override
			public void onClick() {
				setResponsePage(new ManageContentPage(parent, category));
			}
		});
	}
}