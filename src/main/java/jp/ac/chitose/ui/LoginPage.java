package jp.ac.chitose.ui;

import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import lombok.val;

public class LoginPage extends ParentPageWithoutLogin {
	private static final long serialVersionUID = -1311081143185250935L;

	public static final String loginFailedMsg = "ログインできません。\"ログインID\"と\"パスワード\"をご確認ください。";

	private IModel<String> loginName;

	private IModel<String> passphrase;

	@Override
	public String setTitle() {
		return "ログインページ";
	}

	public LoginPage() {
		loginName = new Model<>("");
		passphrase = new Model<>("");

		add(new FeedbackPanel("error"));

		val form = new StatelessForm<Void>("toTopPage") {
			private static final long serialVersionUID = 5584542912241108672L;
			@Override
			public void onSubmit() {
				if(MySession.get().signIn(loginName.getObject(), passphrase.getObject())){
					setResponsePage(TopPage.class);
				}else{
					error(loginFailedMsg);
				}
			}
		};
		add(form);

		val loginNameField = new TextField<String>("loginName", loginName){
			private static final long serialVersionUID = 332468525852220720L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				setRequired(true);
				setLabel(Model.of("ログインID"));
			}
		};
//文字列制限４６と５６でバリデーション
		val passphraseField = new PasswordTextField("passphrase", passphrase){
			private static final long serialVersionUID = -5186459757493662662L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				setLabel(Model.of("パスワード"));
			}
		};

		form.add(loginNameField);
		form.add(passphraseField);
	}
}
