package jp.ac.chitose.ui;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.AppendingStringBuffer;

public class CustomMultiLineLabel extends MultiLineLabel {
	private static final long serialVersionUID = -9122281225306532495L;


	public CustomMultiLineLabel(String id) {
		super(id);
	}

	public CustomMultiLineLabel(String id, String label) {
		super(id, label);
	}

	public CustomMultiLineLabel(String id, IModel<?> model) {
		super(id, model);
	}

	@Override
	public void onComponentTagBody(final MarkupStream markupStream,
			final ComponentTag openTag) {
		CharSequence body = toMultilineMarkup(getDefaultModelObjectAsString());
		replaceComponentTagBody(markupStream, openTag, body);
	}


	private static CharSequence toMultilineMarkup(final CharSequence s)
	{
		if (s == null)
		{
			return null;
		}

		final AppendingStringBuffer buffer = new AppendingStringBuffer();
		int newlineCount = 0;

		buffer.append("<p>");
		for (int i = 0; i < s.length(); i++)
		{
			final char c = s.charAt(i);

			switch (c)
			{
				case '\n' :
					newlineCount++;
					break;

				case '\r' :
					break;

				default :
					if (newlineCount == 1)
					{
						buffer.append("<br/>");
					}
					else if (newlineCount > 1)
					{
						for(int j = 0; j < newlineCount;j++){
							buffer.append("<br/>");
						}
					}

					buffer.append(c);
					newlineCount = 0;
					break;
			}
		}
		if (newlineCount == 1)
		{
			buffer.append("<br/>");
		}
		else if (newlineCount > 1)
		{
//			buffer.append("</p><p>");
			for(int j = 0; j < newlineCount;j++){
				buffer.append("<br/>");
			}
		}
		buffer.append("</p>");
		return buffer;
	}

}
