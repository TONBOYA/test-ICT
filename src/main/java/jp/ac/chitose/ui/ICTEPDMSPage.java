package jp.ac.chitose.ui;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.WebPage;

public class ICTEPDMSPage extends WebPage {
	private static final long serialVersionUID = -3353864922701417569L;

	@Override
	public void renderHead(IHeaderResponse response) {
		response.render(JavaScriptHeaderItem.forUrl("js/bootstrap.min.js"));
		response.render(CssHeaderItem.forUrl("./css/bootstrap.min.css"));
		response.render(CssHeaderItem.forUrl("./css/custombootstrap.css"));
		response.render(CssHeaderItem.forUrl("./css/manager-data.css"));
	}
}
