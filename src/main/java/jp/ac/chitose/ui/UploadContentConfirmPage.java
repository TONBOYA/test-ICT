package jp.ac.chitose.ui;

import org.apache.wicket.markup.html.link.Link;

public class UploadContentConfirmPage extends ParentPage {
	private static final long serialVersionUID = 7597209620468880318L;

	public UploadContentConfirmPage() {
		add(new Link<Void>("toUplaodContentCompletedPage"){
			private static final long serialVersionUID = -8008948029674056965L;

			@Override
			public void onClick() {
				setResponsePage(UploadContentCompletedPage.class);

			}
		});

		add(new Link<Void>("toUploadContentPage"){
			private static final long serialVersionUID = 5509395662907690078L;

			@Override
			public void onClick() {
				setResponsePage(UploadContentPage.class);

			}
		});
	}

	@Override
	public String getTitle() {
		return "コンテンツアップロード確認ページ";
	}
}
