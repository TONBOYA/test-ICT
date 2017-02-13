package jp.ac.chitose.ui;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

public class LogoutCompletedPage extends ParentPageWithoutLogin {
	private static final long serialVersionUID = 5616120999286931417L;

	@Override
	public String setTitle() {
		return "ログアウト完了ページ";
	}

	public LogoutCompletedPage(){

		add(new Label("name",""));

		add(new Link<Void>("returnLoginPage"){
			private static final long serialVersionUID = -8947682062024890825L;

			@Override
			public void onClick() {
				setResponsePage(LoginPage.class);
			}
		});
	}
}
