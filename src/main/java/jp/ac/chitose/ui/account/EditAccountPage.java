package jp.ac.chitose.ui.account;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.StringValidator;

import com.google.inject.Inject;

import jp.ac.chitose.bean.EditAccountBean;
import jp.ac.chitose.service.Interface.IAccountService;
import jp.ac.chitose.service.Interface.ITextFormLimmiterService;
import jp.ac.chitose.ui.ParentPage;
import lombok.val;

@AuthorizeInstantiation({"ADMIN","AUTHOR","USER"})
public class EditAccountPage extends ParentPage {
	private static final long serialVersionUID = -4039748335893185534L;
	@Inject
	IAccountService accountService;

	@Inject
	ITextFormLimmiterService textFormLimmiterService;

	public EditAccountPage(IModel<EditAccountBean> editAccountIModel , boolean oterAccount) {
		setDefaultModel(CompoundPropertyModel.of(editAccountIModel));
		val form = new Form<Void>("form") {
			private static final long serialVersionUID = 6030531818660019554L;

			@Override
			protected void onInitialize() {
				// TODO Auto-generated method stub
				super.onInitialize();
			}

			@Override
			protected void onSubmit() {
				boolean inputError = false;
				boolean newPassFlag = true;

				if(null == editAccountIModel.getObject().getNewPassphrase() || editAccountIModel.getObject().getNewPassphrase().equals("")){
					newPassFlag = true;
					editAccountIModel.getObject().setNewPassphrase(editAccountIModel.getObject().getInputedPassphrase());
				}else{
					if(editAccountIModel.getObject().getNewPassphrase().equals(editAccountIModel.getObject().getCheckPassphrase())){
						newPassFlag = true;
					}else{
						error("パスワード 欄と パスワードの確認 欄は同じものを入力してください。");
						newPassFlag = false;
					}
				}

				if(textFormLimmiterService.measureCharactersBlank(editAccountIModel.getObject().getPresentPassphrase())){
					error("現在のパスワード 欄にスペースを入れないでください。");
					inputError = true;
				}
				if(textFormLimmiterService.measureCharactersBlank(editAccountIModel.getObject().getNewPassphrase())){
					error("変更後のパスワード 欄にスペースを入れないでください。");
					inputError = true;
				}
				if(textFormLimmiterService.measureCharactersBlank(editAccountIModel.getObject().getName())){
					error("名前 欄に全角スペースを入れないでください。");
					inputError = true;
				}
				if(textFormLimmiterService.measureCharactersBlank(editAccountIModel.getObject().getBelongSchool())){
					error("所属学校 欄に全角スペースを入れないでください。");
					inputError = true;
				}

				if(newPassFlag && !(inputError)){
					if (editAccountIModel.getObject().getPresentPassphrase().equals(editAccountIModel.getObject().getInputedPassphrase())) {
						setResponsePage(new EditAccountConfirmPage(editAccountIModel , oterAccount));
					}else{
						error("現在のパスワード 欄の入力に誤りがあります。");
					}
				}else{
					error("入力内容に誤りがあります。");
				}
			}
		};
		add(new FeedbackPanel("feedback"));
		add(form);

		form.add(new TextField<String>("loginName") {
			private static final long serialVersionUID = 1664332472169407777L;

			@Override
			protected void onInitialize() {
				// TODO Auto-generated method stub
				super.onInitialize();
				setRequired(true);
				setLabel(Model.of("ログインID"));
			}
		});

		form.add(new TextField<String>("role") {
			private static final long serialVersionUID = 1664332472169407777L;

			@Override
			protected void onInitialize() {
				// TODO Auto-generated method stub
				super.onInitialize();
				setRequired(true);
				setLabel(Model.of("権限"));
			}
		});

		form.add(new PasswordTextField("inputedPassphrase") {
			private static final long serialVersionUID = -3643702229742007517L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				//add(StringValidator.lengthBetween(8, 32));
				setLabel(Model.of("現在のパスワード"));
			}
		});

		form.add(new PasswordTextField("newPassphrase") {
			private static final long serialVersionUID = 8850125073354161369L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				setRequired(false);
				add(StringValidator.lengthBetween(8, 32));
				setLabel(Model.of("変更後のパスワード"));
			}
		});

		form.add(new PasswordTextField("checkPassphrase") {
			private static final long serialVersionUID = -988695417188965042L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				setRequired(false);
				add(StringValidator.lengthBetween(8, 32));
				setLabel(Model.of("変更後のパスワードの確認"));
			}
		});

		form.add(new TextField<String>("name") {
			private static final long serialVersionUID = 6333697060970652L;

			@Override
			protected void onInitialize() {
				// TODO Auto-generated method stub
				super.onInitialize();
				setRequired(true);
				setLabel(Model.of("名前"));
			}
		});

		form.add(new TextField<String>("belongSchool") {
			private static final long serialVersionUID = -3551203123278210342L;

			@Override
			protected void onInitialize() {
				// TODO Auto-generated method stub
				super.onInitialize();
				setRequired(true);
				setLabel(Model.of("所属学校"));
			}
		});

		form.add(new TextField<String>("emailAddress") {
			private static final long serialVersionUID = -6509657502968072752L;

			@Override
			protected void onInitialize() {
				// TODO Auto-generated method stub
				super.onInitialize();
				setRequired(true);
				add(EmailAddressValidator.getInstance());
				setLabel(Model.of("Eメールアドレス"));
			}
		});
	}


	@Override
	public String getTitle() {
		return "アカウント情報編集ページ";
	}
}
