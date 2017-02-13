package jp.ac.chitose.ui.practice;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;

import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.option.PageType;
import jp.ac.chitose.service.Interface.IAccountService;
import jp.ac.chitose.service.Interface.IContentHierarchyService;
import jp.ac.chitose.service.Interface.IContentService;
import jp.ac.chitose.service.Interface.IHierarchyService;
import jp.ac.chitose.service.Interface.IPracticeService;
import jp.ac.chitose.ui.ParentPage;

public class PracticeContentConfirmPage  extends ParentPage {
	private static final long serialVersionUID = -366858459004658065L;

	@Inject
	private IContentHierarchyService contentHierarchyService;

	@Inject
	private IHierarchyService hierarchyService;

	@Inject
	IAccountService accountService;

	@Inject
	IPracticeService practiceService;

	@Inject
	IContentService contentService;

	public PracticeContentConfirmPage(TreeBean parent, List<TreeBean> contentList, IModel<PracticeRegisterBean> practiceModel, PageType TYPE) {

		add(new Label("contentPath", contentHierarchyService.makeContentHierarchyPath(contentList.get(0))));

		add(new PropertyListView<TreeBean>("contentList",contentList) {
			private static final long serialVersionUID = -8499805238951866222L;

			@Override
			protected void populateItem(ListItem<TreeBean> item) {
				item.add(new Label("contentName", item.getModel().getObject().getName()));
				item.add(new Label("contentAuthor",accountService.getPersonalBean(
						item.getModel().getObject().getAccountId()).getName()));
			}
		});

		add(new Label("practiceName", practiceModel.getObject().getName()));

		add(new Link<Void>("toPracticeContentPage") {
			private static final long serialVersionUID = -24536046271589249L;

			@Override
			public void onClick() {
				setResponsePage(new PracticeContentPage(parent, practiceModel, TYPE));
			}
		});

		add(new Link<Void>("toRegisterPracticeCompletedPage") {
			private static final long serialVersionUID = -3995644752145300523L;

			@Override
			public void onClick() {
					contentList.stream().forEach(content -> {
						practiceModel.getObject().setContentId(Model.of(new ContentPageBean(contentService.content(content.getId()))).getObject().getContentId());
						if(TYPE.equals(PageType.REGISTER)){
							System.out.println("登録ページの処理"+TYPE);

						practiceService.registerPractice(practiceModel.getObject());
						}else if(TYPE.equals(PageType.EDIT)){

							practiceService.editPractice(practiceModel.getObject());
						}
					});
				setResponsePage(new PracticeContentCompletedPage(parent, practiceModel.getObject().getCategory()));
			}
		});
	}

	@Override
	public String getTitle() {
		return "関連付け確認ページ";
	}

}
