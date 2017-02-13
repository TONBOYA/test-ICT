package jp.ac.chitose.ui.account;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.google.inject.Inject;

import jp.ac.chitose.bean.RegisterAccountBean;
import jp.ac.chitose.service.Interface.IAccountService;
import jp.ac.chitose.ui.ParentPage;


//public class RegisterAccountConfirmPage extends ParentPageWithoutLogin {
public class RegisterAccountConfirmPage extends ParentPage {
	private static final long serialVersionUID = -8270741715941783605L;
	@Inject
	IAccountService accountService;

	public RegisterAccountConfirmPage(IModel<RegisterAccountBean> registerAccountBean) {

		setDefaultModel(CompoundPropertyModel.of(registerAccountBean));
		add(new Label("loginName"));
		add(new Label("belongSchool"));
		add(new Label("emailAddress"));
		add(new Label("name"));
		add(new Label("role",String.join(" ", registerAccountBean.getObject().getRole())));

		add(new Link<Void>("toTryRegisterAccountPage"){

			private static final long serialVersionUID = -9035657330839204314L;

			@Override
			public void onClick() {
				setResponsePage(new TryRegisterAccountPage(registerAccountBean));
			}

		});

		add(new Link<Void>("toRegisterAccountCompletedPage"){
			private static final long serialVersionUID = -4876029117713458742L;

			@Override
			public void onClick() {
				String role = registerAccountBean.getObject().getRole();
				if(role == "ユーザー"){
					registerAccountBean.getObject().setRole("USER");
				}else if(role == "教員"){
					registerAccountBean.getObject().setRole("AUTHOR");
				}else if(role == "管理者"){
					registerAccountBean.getObject().setRole("ADMIN");
				}else{
					registerAccountBean.getObject().setRole("USER");
				}

				if(registerAccountBean.getObject().getLoginName().equals(accountService.selectLoginName(registerAccountBean.getObject().getLoginName()))){
					setResponsePage(new TryRegisterAccountPage(registerAccountBean));
					error("ログインID '" + registerAccountBean.getObject().getLoginName() + "' は既に登録されています。");
				}else{
					accountService.registerAccountData(
							registerAccountBean.getObject().getLoginName(),
							registerAccountBean.getObject().getPassphrase(),
							registerAccountBean.getObject().getRole(),
							registerAccountBean.getObject().getName(),
							registerAccountBean.getObject().getBelongSchool(),
							registerAccountBean.getObject().getEmailAddress());
					setResponsePage(new RegisterAccountCompletedPage());
				}
			}
		});
	}

	//	@Override
	//	public String setTitle() {
	//		return "アカウント登録確認ページ";
	//	}

	@Override
	public String getTitle() {
		return "アカウント登録確認ページ";
		// TODO Auto-generated method stub
		//		return null;
	}

}
