package jp.ac.chitose.ui;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;

public class DamiyPageForDistribution extends ParentPage {
	private static final long serialVersionUID = -3423662261388895669L;

	@Override
	public String getTitle() {
		return "削除されたページ";
	}

	public DamiyPageForDistribution() {

		WebMarkupContainer practicePanel = new WebMarkupContainer("addData");
		add(practicePanel);
		practicePanel.setVisible(true);

		practicePanel.add(new Label("name","この項目は削除されました。"));
	}

}
