package jp.ac.chitose.ui.content.manager;

import java.util.List;

import org.apache.wicket.markup.html.link.Link;

import com.google.inject.Inject;

import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.service.Interface.IAccountService;
import jp.ac.chitose.service.Interface.IContentHierarchyService;
import jp.ac.chitose.ui.ParentPage;

public class ContentDeleteCompletedPage extends ParentPage {
	private static final long serialVersionUID = 7126781665213829602L;

	@Inject
	private IContentHierarchyService contentHierarchyService;

	@Inject
	IAccountService accountService;

	@Override
	public String getTitle() {
		return "コンテンツ削除完了ページ";
	}

	public ContentDeleteCompletedPage(TreeBean parent, List<TreeBean> contentList, String category) {

		add(new Link<Void>("returnManageContentPage") {
			private static final long serialVersionUID = 7536738304743274452L;

			@Override
			public void onClick() {
				setResponsePage(new ManageContentPage(parent, category));
			}
		});
	}
}
