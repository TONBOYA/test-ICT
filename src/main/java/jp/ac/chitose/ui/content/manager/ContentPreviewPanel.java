package jp.ac.chitose.ui.content.manager;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.google.inject.Inject;

import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.service.Interface.IAccountService;
import jp.ac.chitose.ui.CustomMultiLineLabel;
import jp.ac.chitose.ui.MySession;

public class ContentPreviewPanel extends Panel {
	private static final long serialVersionUID = -8782715671169411324L;

	@Inject
	private IAccountService accountService;

	public ContentPreviewPanel(String id, IModel<ContentPageBean> model) {
		super(id, model);

		setDefaultModel(CompoundPropertyModel.of(model));

		add(new Label("account",accountService.getPersonalBean(MySession.get().getAccountBean().getAccountId()).getName()));
		add(new Label("name"));
		add(new	ListView<String>("keyword"){
			private static final long serialVersionUID = 3059949390576613435L;

			@Override
			protected void populateItem(ListItem<String> item) {
				item.add(new Label("label",item.getModel().getObject()));
			}
		});
		add(new Label("tag1"));
		add(new Label("tag2"));
		add(new Label("tag3"));
		add(new CustomMultiLineLabel("howToUse"));

	}

}
