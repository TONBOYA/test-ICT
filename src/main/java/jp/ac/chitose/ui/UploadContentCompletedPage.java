package jp.ac.chitose.ui;

import org.apache.wicket.markup.html.link.Link;

import jp.ac.chitose.ui.content.manager.ManageContentPage;

public class UploadContentCompletedPage extends ParentPage {
	private static final long serialVersionUID = 6152820871674662573L;

	public UploadContentCompletedPage() {
		add(new Link<Void>("returnManageContentPage"){
			private static final long serialVersionUID = -7649551959150525553L;

			@Override
			public void onClick() {
				setResponsePage(ManageContentPage.class);
			}
		});
	}

	@Override
	public String getTitle() {
		return "コンテンツアップロード完了ページ";
	}
}
