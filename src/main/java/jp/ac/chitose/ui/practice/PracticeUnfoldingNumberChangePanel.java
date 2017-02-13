package jp.ac.chitose.ui.practice;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;

import jp.ac.chitose.bean.PracticeFormBean;
import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.option.PageType;
import jp.ac.chitose.service.Interface.IPracticeService;

public class PracticeUnfoldingNumberChangePanel extends Panel {
	private static final long serialVersionUID = -4047355799335533225L;

	@Inject
	private IPracticeService practiceService;

	public PracticeUnfoldingNumberChangePanel(String id, IModel<PracticeRegisterBean> practiceModel, TreeBean parent,
			int DEFAULT_UNFOLDING_SIZE, PageType TYPE) {
		super(id, practiceModel);
		setDefaultModel(CompoundPropertyModel.of(practiceModel));
		int UNFOLDING_SIZE;

		if (DEFAULT_UNFOLDING_SIZE == 3) {
			UNFOLDING_SIZE = 4;
		} else {
			UNFOLDING_SIZE = 3;
		}

		add(new Link<Void>("unfoldingNumberChange") {
			private static final long serialVersionUID = 1508655814128552892L;

			@Override
			public void onClick() {
				System.out.println(practiceModel);
				PracticeFormBean pFormBean = new PracticeFormBean(practiceModel.getObject(), TYPE);
				System.out.println(pFormBean);

				if (TYPE == PageType.REGISTER) {
					setResponsePage(new PracticeRegisterPage2(parent,
							Model.of(new PracticeRegisterBean(pFormBean,
									practiceService.registerUnfoldingList(UNFOLDING_SIZE), pFormBean.getAccountId(),
									pFormBean.getPracticeHierarchyId()))));
				} else if (TYPE == PageType.EDIT) {
					setResponsePage(new PracticeEditPage2(parent,
							Model.of(new PracticeRegisterBean(pFormBean,
									practiceService.registerUnfoldingList(UNFOLDING_SIZE), pFormBean.getAccountId(),
									pFormBean.getPracticeHierarchyId()))));
				}
			}
		}.add(new Label("name", UNFOLDING_SIZE + "項目に変更する")));
	}

}
