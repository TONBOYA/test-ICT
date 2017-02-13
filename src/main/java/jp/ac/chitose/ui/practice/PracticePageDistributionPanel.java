package jp.ac.chitose.ui.practice;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import com.google.inject.Inject;

import jp.ac.chitose.bean.PracticePageBean;
import jp.ac.chitose.bean.UnfoldingBean;
import jp.ac.chitose.service.Interface.IAccountService;
import jp.ac.chitose.ui.CustomMultiLineLabel;

public class PracticePageDistributionPanel extends Panel {

	@Inject
	protected IAccountService accountService;

	public PracticePageDistributionPanel(String id, IModel<PracticePageBean> practiceModel) {
		super(id, practiceModel);

		add(new Label("keyword", String.join(",  ", practiceModel.getObject().getKeyword())));
		add(new Label("tag", String.join(",  ", practiceModel.getObject().getTag())));
		add(new Label("author", accountService.getPersonalBean(practiceModel.getObject().getAccountId()).getName()));
		add(new Label("name"));
		add(new CustomMultiLineLabel("summary"));
		add(new CustomMultiLineLabel("challenge"));
		add(new CustomMultiLineLabel("reaction"));
		add(new PropertyListView<UnfoldingBean>("unfoldingList") {
			private static final long serialVersionUID = -2513092799296664907L;

			@Override
			protected void populateItem(ListItem<UnfoldingBean> item) {
				item.add(new UnfoldingPanel("panel", item.getModel(), item.getModelObject().getPracticeId()));
			}
		});

	}

}
