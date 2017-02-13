package jp.ac.chitose.ui.account;

import java.util.ArrayList;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;

import com.google.inject.Inject;

import jp.ac.chitose.bean.PersonalBean;
import jp.ac.chitose.service.Interface.IAccountService;
import jp.ac.chitose.ui.ParentPage;

public class DeleteAccountConfirmPage extends ParentPage {
	private static final long serialVersionUID = -7625583208176512200L;

	@Inject
	IAccountService accountService;

	boolean deleteAccount = false;

	@Override
	public String getTitle() {
		return "アカウント削除確認ページ";
	}

	public DeleteAccountConfirmPage(ArrayList<PersonalBean> personalList) {
		add(new PropertyListView<PersonalBean>("personalList",personalList){
			/**
			 *
			 */
			private static final long serialVersionUID = -6389288292286044179L;

			protected void populateItem(ListItem<PersonalBean> item) {
				item.add(new Label("name",item.getModel().getObject().getName()));
				item.add(new Label("belongSchool",accountService.getPersonalBean(item.getModel().getObject().getAccountId()).getBelongSchool()));
			}

		});



		add(new Link<Void>("toDeleteAccountCompletedPage"){
			private static final long serialVersionUID = -3626520900670325686L;

			@Override
			public void onClick() {
				personalList.stream().forEach(personal ->{
					deleteAccount = accountService.deleteAccountData(personal.getAccountId());
				});
				if(deleteAccount){
					setResponsePage(DeleteAccountCompletedPage.class);
				}else{
					setResponsePage(ManageAccountPage.class);
				}
			}
		});

		add(new Link<Void>("returnManageAccountPage"){
			private static final long serialVersionUID = 2444561265623636514L;

			@Override
			public void onClick() {
				setResponsePage(ManageAccountPage.class);
			}
		});
	}
}
