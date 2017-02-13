package jp.ac.chitose.ui.content.content;

import jp.ac.chitose.ui.DamiyPageForDistribution;

public class ContentTreePanel extends ContentHierarchyPanel{
	private static final long serialVersionUID = -9008458465751091055L;

	public ContentTreePanel(String id) {
		super(id);
	}
	@Override
	protected void nextContentPage(int id) {
		try {
			setResponsePage(new ContentPageForDistribution(id));
		} catch (NullPointerException e) {
			setResponsePage(new DamiyPageForDistribution());
		}
	}

}
