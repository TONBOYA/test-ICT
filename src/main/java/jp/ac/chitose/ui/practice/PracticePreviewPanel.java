package jp.ac.chitose.ui.practice;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.google.inject.Inject;

import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.service.Interface.IAccountService;
import jp.ac.chitose.ui.CustomMultiLineLabel;
import jp.ac.chitose.ui.MySession;

public class PracticePreviewPanel extends Panel {
	private static final long serialVersionUID = -8993720589389068801L;

	@Inject
	private IAccountService accountService;

	public PracticePreviewPanel(String id, IModel<PracticeRegisterBean> model) {
		super(id, model);

		setDefaultModel(CompoundPropertyModel.of(model));

		add(new Label("account",accountService.getPersonalBean(MySession.get().getAccountBean().getAccountId()).getName()));
		add(new Label("name"));
		add(new	ListView<String>("keyword"){
			private static final long serialVersionUID = 3841939401739685094L;
			@Override
			protected void populateItem(ListItem<String> item) {
				item.add(new Label("keywordLabel",item.getModel().getObject()));
			}
		});
		add(new Label("tag1"));
		add(new Label("tag2"));
		add(new Label("tag3"));
		add(new CustomMultiLineLabel("summary"));
		add(new CustomMultiLineLabel("challenge"));

//		add(new PropertyListView<UnfoldingFormBean>("unfoldingList") {
//			private static final long serialVersionUID = 6561257675695957444L;
//
//			@Override
//			protected void populateItem(ListItem<UnfoldingFormBean> item) {
//				item.add(new UnfoldingPreviewPanel("panel",Model.of(item.getModel()),model.getObject().getAccountId()));
//			}
//
//		});
		add(new CustomMultiLineLabel("reaction"));
	}

}
