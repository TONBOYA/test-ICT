package jp.ac.chitose.ui;

import org.apache.wicket.markup.html.link.Link;

public class UploadContentPage extends ParentPage {
	private static final long serialVersionUID = 6944954713434894671L;

	public UploadContentPage() {
		add(new Link<Void>("toUploadContentConfirmPage"){
			private static final long serialVersionUID = -8622538629073801437L;

			@Override
			public void onClick() {
				setResponsePage(UploadContentConfirmPage.class);
			}
		});
	}

	@Override
	public String getTitle() {
		return "コンテンツアップロードページ";
	}
}
