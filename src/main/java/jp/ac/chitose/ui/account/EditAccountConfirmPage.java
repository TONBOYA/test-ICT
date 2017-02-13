package jp.ac.chitose.ui.account;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.google.inject.Inject;

import jp.ac.chitose.bean.EditAccountBean;
import jp.ac.chitose.service.Interface.IAccountService;
import jp.ac.chitose.ui.ParentPage;

public class EditAccountConfirmPage extends ParentPage {
	private static final long serialVersionUID = 7852433387564259687L;

	@Inject
	IAccountService accountService;

	public EditAccountConfirmPage(IModel<EditAccountBean> editAccountIModel , boolean oterAccount) {


		setDefaultModel(CompoundPropertyModel.of(editAccountIModel));
		add(new Label("loginName"));
		add(new Label("belongSchool"));
		add(new Label("emailAddress"));
		add(new Label("name"));


		add(new Link<Void>("returnEditAccountPage"){
			private static final long serialVersionUID = 3832587568369824170L;

			@Override
			public void onClick() {
				setResponsePage(new EditAccountPage(editAccountIModel , oterAccount));
			}

		});

		add(new Link<Void>("toEditAccountCompletedPage"){
			private static final long serialVersionUID = -7517401951777446365L;

			@Override
			public void onClick() {
				accountService.updatePersonalData(editAccountIModel.getObject().getAccountId(),editAccountIModel.getObject().getName(),
													editAccountIModel.getObject().getBelongSchool(),editAccountIModel.getObject().getEmailAddress());
				accountService.updatePassphrase(editAccountIModel.getObject().getAccountId(), editAccountIModel.getObject().getNewPassphrase());
				setResponsePage(new EditAccountCompletedPage(editAccountIModel , oterAccount));
			}
		});
	}

	@Override
	public String getTitle() {
		return "アカウント編集確認ページ";
	}

}
