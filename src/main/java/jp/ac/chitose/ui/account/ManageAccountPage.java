package jp.ac.chitose.ui.account;

import java.util.ArrayList;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.util.ListModel;

import com.google.inject.Inject;

import jp.ac.chitose.bean.EditAccountBean;
import jp.ac.chitose.bean.PersonalBean;
import jp.ac.chitose.option.Definition;
import jp.ac.chitose.service.Interface.IAccountService;
import jp.ac.chitose.ui.MySession;
import jp.ac.chitose.ui.ParentPage;
import lombok.val;

/**
 * アカウントを管理するページ
 *
 * @author taka
 *
 */
@AuthorizeInstantiation({"ADMIN"})
public class ManageAccountPage extends ParentPage {
	private static final long serialVersionUID = 387409120334717824L;
	@Inject
	IAccountService accountService;

	public ManageAccountPage() {
		val checkList = new ArrayList<PersonalBean>();
		val checkGroup = new CheckGroup<PersonalBean>("checkGroup",checkList);
		ListView<PersonalBean> personalList = new PropertyListView<PersonalBean>(
				"personal", new ListModel<>(
						accountService.getPersonalAllListBean())) {
			private static final long serialVersionUID = 9164618444380863399L;

			@Override
			protected void populateItem(ListItem<PersonalBean> item) {
				item.add(new Label("name"));
				item.add(new Label("belongSchool"));
				item.add(new Label("emailAddress"));

				item.add(new Link<Void>("edit"){
					/**
					 *
					 */
					private static final long serialVersionUID = 1167727521758781528L;

					public void onClick(){
						EditAccountBean editAccountBean = new EditAccountBean(accountService.getAccountBean(item.getModelObject().getAccountId()),item.getModelObject());
						editAccountBean.setOtherAccount(true);
						setResponsePage(new EditAccountPage(Model.of(editAccountBean),editAccountBean.isOtherAccount()));
					}
				});
				item.add(new Check<>("check",item.getModel())).setVisibilityAllowed(item.getModel().getObject().getAccountId() != MySession.get().getAccountBean().getAccountId());
			}
		};

		//add(personalList);
		checkGroup.add(personalList);

		val form = new Form<Void>("form");
		AjaxButton toDeleteAccountConfirmPageButton = new AjaxButton("toDeleteAccountConfirmPage",form) {

			private static final long serialVersionUID = 1156395975558590961L;
			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);
				if(checkList.size() >= 1){
					setResponsePage(new DeleteAccountConfirmPage(checkList));
				}else{
					error(Definition.TO_DELETEPRACTICEPAGE_ADMIN_ERROR);
				}
			}
		};
		form.add(toDeleteAccountConfirmPageButton);
		form.add(checkGroup);

		//		add(new Link<Void>("toDeleteAccountConfirmPage") {
		//			private static final long serialVersionUID = -9207212970028338543L;
		//
		//			@Override
		//			public void onClick() {
		//				setResponsePage(DeleteAccountConfirmPage.class);
		//			}
		//		});

		/**Kubo
		 *
		 */
		form.add(new Link<Void>("toRegisterAccountPage") {

			private static final long serialVersionUID = -4147246529894699936L;

			public void onClick() {
				setResponsePage(RegisterAccountPage.class);
			}
		});

		add(form);


	}

	@Override
	public String getTitle() {
		return "アカウント管理ページ";
	}

}
