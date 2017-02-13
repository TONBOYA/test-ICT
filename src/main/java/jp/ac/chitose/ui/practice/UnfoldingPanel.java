package jp.ac.chitose.ui.practice;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import jp.ac.chitose.bean.UnfoldingBean;
import jp.ac.chitose.ui.CustomMultiLineLabel;

public class UnfoldingPanel extends Panel {
	private static final long serialVersionUID = 6465315152514063875L;

	public UnfoldingPanel(String id, IModel<UnfoldingBean> model,int practiceId) {
		super(id, model);
		setDefaultModel(CompoundPropertyModel.of(model));

		add(new Label("name"));
		add(new Label("requiredMinute"));
		add(new CustomMultiLineLabel("instruction"));
		add(new CustomMultiLineLabel("specialInstruction"));
		add(new ImagePanel("image", model, practiceId){
			private static final long serialVersionUID = -776429570547926984L;
			@Override
			protected void onConfigure() {
				super.onConfigure();
				setVisibilityAllowed(!StringUtils.isBlank(firstFileName) || !StringUtils.isBlank(secondFileName));
			}
		});
	}
}
