package jp.ac.chitose.ui.account;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.google.inject.Inject;

import jp.ac.chitose.bean.EditAccountBean;
import jp.ac.chitose.service.Interface.ILoginService;
import jp.ac.chitose.ui.MySession;
import jp.ac.chitose.ui.ParentPage;
import jp.ac.chitose.ui.TopPage;


public class EditAccountCompletedPage extends ParentPage {
	static final long serialVersionUID = -2004389034416686439L;
	@Inject
	ILoginService loginService;

	public EditAccountCompletedPage(IModel<EditAccountBean>editAccounIModel , boolean oterAccount) {
		setDefaultModel(CompoundPropertyModel.of(editAccounIModel));
		add(new Label("loginName"));
		add(new Label("belongSchool"));
		add(new Label("emailAddress"));
		add(new Label("name"));


		add(new Link<Void>("returnTopPage"){
			private static final long serialVersionUID = -4788088696847493926L;

			@Override
			public void onClick() {
				if(!oterAccount){
					MySession.get().authenticate(editAccounIModel.getObject().getLoginName(), editAccounIModel.getObject().getNewPassphrase());
					setResponsePage(TopPage.class);
				}else{
					setResponsePage(ManageAccountPage.class);
				}

			}

		});
	}

	@Override
	public String getTitle() {
		return "アカウント編集完了ページ";
	}
}
