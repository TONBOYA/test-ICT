package jp.ac.chitose.ui.account;

import java.util.ArrayList;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.StringValidator;

import com.google.inject.Inject;

import jp.ac.chitose.bean.RegisterAccountBean;
import jp.ac.chitose.service.Interface.IAccountService;
import jp.ac.chitose.service.Interface.ITextFormLimmiterService;
import jp.ac.chitose.ui.ParentPageWithoutLogin;
import lombok.val;

public class TryRegisterAccountPage extends ParentPageWithoutLogin {

	@Inject
	IAccountService accountService;
	@Inject
	ITextFormLimmiterService textFormLimmiterService;

	private static final long serialVersionUID = 7924352497960032221L;
	public TryRegisterAccountPage(IModel<RegisterAccountBean> model){

		setDefaultModel(CompoundPropertyModel.of(model));

		ArrayList<String> roleList = new ArrayList<String>();
		roleList.add("ユーザー");
		roleList.add("教員");
		roleList.add("管理者");

		FeedbackPanel feedback = new FeedbackPanel("feedback"){
			private static final long serialVersionUID = -2637820862777375626L;

			@Override
			protected void onConfigure() {
				super.onConfigure();
				setOutputMarkupId(true);

			}
		};
		add(feedback);

		val form = new Form<RegisterAccountBean>("form",model){


			/**
			 *
			 */
			private static final long serialVersionUID = -383095914051906622L;

			@Override
			protected void onInitialize() {
				// TODO Auto-generated method stub
				super.onInitialize();
				add(new EqualPasswordInputValidator((PasswordTextField)get("passphrase"), (PasswordTextField)get("retypedPassphrase")));
			}

			@Override
			protected void onSubmit() {
				boolean inputError = false;

				if(textFormLimmiterService.measureCharactersBlank(getModelObject().getPassphrase())){
					error("パスワード 欄にスペースを入れないでください。");
					inputError = true;
				}
				if(textFormLimmiterService.measureCharactersBlank(getModelObject().getName())){
					error("名前 欄に全角スペースを入れないでください。");
					inputError = true;
				}
				if(textFormLimmiterService.measureCharactersBlank(getModelObject().getBelongSchool())){
					error("所属学校 欄に全角スペースを入れないでください。");
					inputError = true;
				}

				if(!(textFormLimmiterService.measureCharactersToTwoByte(getModelObject().getLoginName()))){
					error("ログインID 欄には、半角英数字を入力してください。");
					inputError = true;
				}

				if(!(inputError)){
					if(getModelObject().getLoginName().equals(accountService.selectLoginName(getModelObject().getLoginName()))){
						error("ログインID '" + getModelObject().getLoginName() + "' は既に登録されています。");
					}else{
						setResponsePage(new RegisterAccountConfirmPage(getModel()));
					}
				}else{
					error("入力内容に誤りがあります。");
				}
			}
		};

		add(form);

		form.add(new TextField<String>("loginName"){
			private static final long serialVersionUID = -3149688335470671958L;

			@Override
			protected void onInitialize() {
				// TODO Auto-generated method stub
				super.onInitialize();
				setRequired(true);
				add(StringValidator.lengthBetween(4, 32));
				setLabel(Model.of("ログインID"));
			}
		});

		form.add(new PasswordTextField("passphrase"){
			private static final long serialVersionUID = -8507844246049701353L;

			@Override
			protected void onInitialize() {
				// TODO Auto-generated method stub
				super.onInitialize();
				add(StringValidator.lengthBetween(8, 32));
				setLabel(Model.of("パスワード"));
			}
		});

		form.add(new PasswordTextField("retypedPassphrase"){
			private static final long serialVersionUID = 675709905729885854L;

			@Override
			protected void onInitialize() {
				// TODO Auto-generated method stub
				super.onInitialize();
				add(StringValidator.lengthBetween(8, 32));
				setLabel(Model.of("パスワードの確認"));
			}


		});

		form.add(new TextField<String>("name"){
			private static final long serialVersionUID = 3087546707786602777L;

			@Override
			protected void onInitialize() {
				// TODO Auto-generated method stub
				super.onInitialize();
				setRequired(true);
				setLabel(Model.of("名前"));
			}
		});

		form.add(new TextField<String>("belongSchool"){
			private static final long serialVersionUID = -8784699418754055226L;

			@Override
			protected void onInitialize() {
				// TODO Auto-generated method stub
				super.onInitialize();
				setRequired(true);
				setLabel(Model.of("所属学校"));
			}
		});

		form.add(new TextField<String>("emailAddress"){
			private static final long serialVersionUID = -23940782174712809L;

			@Override
			protected void onInitialize() {
				// TODO Auto-generated method stub
				super.onInitialize();
				setRequired(true);
				add(EmailAddressValidator.getInstance());
				setLabel(Model.of("Eメールアドレス"));
			}
		});

		form.add(new RadioChoice<String>("role",roleList){
			private static final long serialVersionUID = 4068982944468304580L;
			@Override
			protected void onInitialize() {
				super.onInitialize();
				setRequired(true);
				setLabel(Model.of("権限"));
			}
		});

	}

	@Override
	public String setTitle() {
		return "アカウント登録ページ";
	}
}
