package jp.ac.chitose.ui.account;

import org.apache.wicket.markup.html.link.Link;

import jp.ac.chitose.ui.ParentPage;

//public class RegisterAccountCompletedPage extends ParentPageWithoutLogin {
public class RegisterAccountCompletedPage extends ParentPage {
	private static final long serialVersionUID = 5210585939095843696L;

	public RegisterAccountCompletedPage() {
		add(new Link<Void>("returnLoginPage"){
			private static final long serialVersionUID = -8408296948547298642L;

			@Override
			public void onClick() {
				//setResponsePage(LoginPage.class);
				setResponsePage(ManageAccountPage.class);
			}
		});
	}

//	@Override
//	public String setTitle() {
//		return "アカウント登録完了ページ";
//	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "アカウント登録完了ページ";
	}
}
