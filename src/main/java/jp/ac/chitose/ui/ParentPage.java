package jp.ac.chitose.ui;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;

import jp.ac.chitose.bean.EditAccountBean;
import jp.ac.chitose.service.Interface.IAccountService;
import jp.ac.chitose.service.Interface.IContentHierarchyService;
import jp.ac.chitose.service.Interface.IHierarchyService;
import jp.ac.chitose.ui.account.EditAccountPage;
import jp.ac.chitose.ui.account.ManageAccountPage;
import jp.ac.chitose.ui.content.content.ContentPage;
import jp.ac.chitose.ui.content.manager.ManageContentPage;
import jp.ac.chitose.ui.practice.ManagePracticePage;
import jp.ac.chitose.ui.practice.PracticePage;

/**
 * ログイン後の全てのページの継承元となるクラス
 *
 * @author Kazuki Madokoro
 *
 */
// 英語の確認重要
@AuthorizeInstantiation({ "ADMIN", "AUTHOR", "USER" })
public abstract class ParentPage extends ICTEPDMSPage {
	private static final long serialVersionUID = 7789138712900853981L;

	@Inject
	private IHierarchyService hierarchyService;

	@Inject
	private IContentHierarchyService contentHierarchyService;

	@Inject
	protected IAccountService accountService;

	public ParentPage() {
		String category = "ルート";

		add(new Link<Void>("toTopPage") {
			private static final long serialVersionUID = -404363137583203507L;

			@Override
			public void onClick() {
				setResponsePage(TopPage.class);
			}
		});

		add(new Link<Void>("toPracticePage") {
			private static final long serialVersionUID = 2197875810513608142L;

			@Override
			public void onClick() {

				setResponsePage(PracticePage.class);
			}
		});

		add(new Link<Void>("toManagePracticePage") {
			private static final long serialVersionUID = -3831505251917453845L;

			protected void onInitialize() {
				super.onInitialize();
				setVisibilityAllowed(!MySession.get().getAccountBean().getRole().equals("USER"));
			}

			@Override
			public void onClick() {
				setResponsePage(new ManagePracticePage(hierarchyService.searchRoot(), category));
			}
		});

		add(new Link<Void>("toContentPage") {
			private static final long serialVersionUID = -4310845959045763661L;

			@Override
			public void onClick() {
				setResponsePage(ContentPage.class);
			}
		});

		add(new Link<Void>("toManageContentPage") {
			private static final long serialVersionUID = 2197875810513608142L;

			protected void onInitialize() {
				super.onInitialize();
				setVisibilityAllowed(!MySession.get().getAccountBean().getRole().equals("USER"));
			}

			@Override
			public void onClick() {
				setResponsePage(new ManageContentPage(contentHierarchyService.searchRoot(), category));
			}
		});

		add(new Link<Void>("toLogoutCompletedPage") {
			private static final long serialVersionUID = -6811016252788188659L;

			@Override
			public void onClick() {
				MySession.get().signOut();
				setResponsePage(LogoutCompletedPage.class);
			}
		});

		add(new Link<Void>("toManageAccountPage") {
			private static final long serialVersionUID = -4310845959045763661L;

			protected void onInitialize() {
				super.onInitialize();
				setVisibilityAllowed(MySession.get().getAccountBean().getRole().equals("ADMIN"));
			}

			@Override
			public void onClick() {
				setResponsePage(ManageAccountPage.class);
			}
		});

		add(new Link<Void>("toEditAccountPage") {
			private static final long serialVersionUID = -2611212684759673173L;

			@Override
			public void onClick() {
				EditAccountBean editAccountBean = new EditAccountBean(MySession.get().getAccountBean(),
						accountService.getPersonalBean(MySession.get().getAccountBean().getAccountId()));
				editAccountBean.setOtherAccount(false);
				setResponsePage(new EditAccountPage(Model.of(editAccountBean), editAccountBean.isOtherAccount()));
			}
		});

		add(new Label("title", getTitle()));

//		 add(new Link<Void>("toTestPage"){
//		 private static final long serialVersionUID = 9035877091556594489L;
//		 @Override public void onClick() {setResponsePage(AddTest.class);}});
//
//		 add(new Link<Void>("toTestPageB"){
//		 private static final long serialVersionUID = -4514567731145687465L;
//		 @Override public void onClick() {
//			 setResponsePage(AddTestB.class);}});

	}

	abstract public String getTitle();
}