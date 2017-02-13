package jp.ac.chitose.ui.uncategorized;

import org.apache.wicket.markup.html.link.Link;

import jp.ac.chitose.ui.ParentPage;
import jp.ac.chitose.ui.practice.PracticePage;

public class CommentConfirmPage extends ParentPage {
	private static final long serialVersionUID = -1033633622633261720L;

	public CommentConfirmPage() {
		add(new Link<Void>("toCommentCompletedPage"){
			private static final long serialVersionUID = 4988023375293241305L;

			@Override
			public void onClick() {
				setResponsePage(CommentCompletedPage.class);
			}

		});

		add(new Link<Void>("returnPracticePage"){
			private static final long serialVersionUID = 761637653303068365L;

			@Override
			public void onClick() {
				setResponsePage(PracticePage.class);
			}

		});
	}

	@Override
	public String getTitle() {
		return "コメント確認ページ";
	}
}
