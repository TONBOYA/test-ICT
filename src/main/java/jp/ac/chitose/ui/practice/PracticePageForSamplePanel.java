package jp.ac.chitose.ui.practice;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

public class PracticePageForSamplePanel extends Panel{

	public PracticePageForSamplePanel(String id) {
		super(id);
		add(new Label("sampleHead","サンプルページ"));
		add(new Label("sampleBody","ここに実践一覧から選択した実践が表示されます"));
	}

}
