package jp.ac.chitose.ui.content.manager;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.option.PageType;
import jp.ac.chitose.ui.ParentPage;
import jp.ac.chitose.ui.practice.PracticeContentPage;

public class ContentRegisterCompletedPage extends ParentPage {
	private static final long serialVersionUID = -5356392425604018938L;

	@Override
	public String getTitle() {
		return "コンテンツ	登録完了ページ";
	}

	public ContentRegisterCompletedPage(TreeBean parent ,String category) {
		add(new Link<Void>("returnManageContentPage"){
			private static final long serialVersionUID = -1234471348680369110L;

			@Override
			public void onClick() {
				setResponsePage(new ManageContentPage(parent, category));
			}
		});
	}

	public ContentRegisterCompletedPage(TreeBean parent, IModel<PracticeRegisterBean> practiceModel, String category,PageType TYPE) {
		add(new Link<Void>("returnManageContentPage"){
			private static final long serialVersionUID = -1234471348680369110L;

			@Override
			public void onClick() {
				setResponsePage(new PracticeContentPage(parent,practiceModel,TYPE));
			}
		});
	}
}
