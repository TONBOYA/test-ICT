package jp.ac.chitose.ui.uncategorized;

import org.apache.wicket.markup.html.link.Link;

import jp.ac.chitose.ui.ParentPage;
import jp.ac.chitose.ui.practice.PracticePage;

public class CommentCompletedPage extends ParentPage {
	private static final long serialVersionUID = -1676599650392323533L;

	public CommentCompletedPage() {
		add(new Link<Void>("returnPracticePage"){
			private static final long serialVersionUID = -3836231830366789951L;

			@Override
			public void onClick() {
				setResponsePage(PracticePage.class);
			}

		});
	}

	@Override
	public String getTitle() {
		return "コメント完了ページ";
	}
}
