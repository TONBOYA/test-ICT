package jp.ac.chitose.ui.content.content;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.google.inject.Inject;

import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.service.Interface.IContentService;
import jp.ac.chitose.ui.DamiyPageForDistribution;

public class ContentLinkedNamePanel extends Panel{

	@Inject
	IContentService contentService;

	public ContentLinkedNamePanel(String id, IModel<TreeBean> treeModel, List<TreeBean> parents) {
		super(id, treeModel);
		setDefaultModel(CompoundPropertyModel.of(treeModel));
		add(new Link<Void>("nextSearchPage") {
			private static final long serialVersionUID = -4619289878813277763L;

			@Override
			public void onClick() {
				setResponsePage(new ContentAndCategorySearchPage(treeModel.getObject(), parents));
			}
		}.add(new Label("name")));
	}

	public ContentLinkedNamePanel(String id, IModel<ContentPageBean> contentModel) {
		super(id, contentModel);
		setDefaultModel(CompoundPropertyModel.of(contentModel));
		add(new Link<Void>("nextSearchPage") {
			private static final long serialVersionUID = -4619289878813277763L;
			@Override
			public void onClick() {

				try {
					setResponsePage(new ContentPageForDistribution(contentModel.getObject().getContentId()));
				} catch (NullPointerException e) {
					setResponsePage(new DamiyPageForDistribution());
				}
			}
		}.add(new Label("name")));
	}

}
