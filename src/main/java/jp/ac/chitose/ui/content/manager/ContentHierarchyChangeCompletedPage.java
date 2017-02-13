package jp.ac.chitose.ui.content.manager;

import org.apache.wicket.markup.html.link.Link;

import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.ui.ParentPage;

public class ContentHierarchyChangeCompletedPage extends ParentPage{

	@Override
	public String getTitle() {
		return "実践登録完了ページ";
	}

	public ContentHierarchyChangeCompletedPage(TreeBean parent, String category) {
		add(new Link<Void>("returnManageContentPage"){
			private static final long serialVersionUID = -1234471348680369110L;

			@Override
			public void onClick() {
				setResponsePage(new ManageContentPage(parent, category));
			}
		});
	}
}
