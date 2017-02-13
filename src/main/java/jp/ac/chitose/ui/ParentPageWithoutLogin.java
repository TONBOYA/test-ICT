package jp.ac.chitose.ui;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

public abstract class ParentPageWithoutLogin extends ICTEPDMSPage {

	private static final long serialVersionUID = -4272588568854944868L;

	public ParentPageWithoutLogin(){
		add(new Link<Void>("toLoginPage"){
			private static final long serialVersionUID = -9209976496913596655L;

			@Override
			public void onClick() {
				setResponsePage(LoginPage.class);

			}

		});

		add(new Label("title",setTitle()));
	}

	abstract public String setTitle();
}
