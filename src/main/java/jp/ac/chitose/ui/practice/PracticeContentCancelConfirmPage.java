package jp.ac.chitose.ui.practice;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

import com.google.inject.Inject;

import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.dao.Interface.IPracticeContentDAO;
import jp.ac.chitose.option.PageType;
import jp.ac.chitose.ui.ParentPage;

public class PracticeContentCancelConfirmPage extends ParentPage{

	@Inject
	private IPracticeContentDAO practiceContentDAO;

	@Override
	public String getTitle() {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	public PracticeContentCancelConfirmPage(TreeBean parent, IModel<PracticeRegisterBean> practiceModel, int contentId, PageType TYPE){


		add(new Link<Void>("toPracticeContentPage") {
			private static final long serialVersionUID = -24536046271589249L;

			@Override
			public void onClick() {
				setResponsePage(new PracticeContentPage(parent, practiceModel, TYPE));
			}
		});

		add(new Link<Void>("toCancelCompletedPage") {
			private static final long serialVersionUID = -3995644752145300523L;

			@Override
			public void onClick() {
				practiceContentDAO.deletePracticeContent(practiceModel.getObject().getPracticeId());
				setResponsePage(new PracticeContentCompletedPage(parent, practiceModel.getObject().getCategory()));
			}
		});


	}

}
