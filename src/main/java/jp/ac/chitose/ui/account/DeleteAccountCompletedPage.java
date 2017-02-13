package jp.ac.chitose.ui.account;

import org.apache.wicket.markup.html.link.Link;

import jp.ac.chitose.ui.ParentPage;

public class DeleteAccountCompletedPage extends ParentPage {
	private static final long serialVersionUID = 7066456626153745707L;

	@Override
	public String getTitle() {
		return "アカウント削除完了ページ";
	}

	public DeleteAccountCompletedPage() {
		add(new Link<Void>("returnManageAccountPage"){
			private static final long serialVersionUID = -1023459105466276163L;

			@Override
			public void onClick() {
				setResponsePage(ManageAccountPage.class);
			}
		});
	}
}