package jp.ac.chitose.ui.content.manager;

import java.io.File;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PropertyListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;

import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.service.Interface.IAccountService;
import jp.ac.chitose.service.Interface.IContentHierarchyService;
import jp.ac.chitose.service.Interface.IContentService;
import jp.ac.chitose.service.Interface.IFileService;
import jp.ac.chitose.ui.ParentPage;

public class ContentDeleteConfirmPage extends ParentPage {
	private static final long serialVersionUID = 6112396874398811877L;

	@Inject
	private IContentHierarchyService contentHierarchyService;

	@Inject
	IAccountService accountService;

	@Inject
	IContentService contentService;


	@Inject
	private IFileService fileService;

	@Override
	public String getTitle() {
		return "コンテンツ削除確認ページ";
	}

	public ContentDeleteConfirmPage(TreeBean parent, List<TreeBean> contentList, String category) {

		add(new Label("path", contentHierarchyService.makeContentHierarchyPath(parent)));

		add(new PropertyListView<TreeBean>("contentList", contentList) {
			private static final long serialVersionUID = -8499805238951866222L;

			@Override
			protected void populateItem(ListItem<TreeBean> item) {
				item.add(new Label("name", item.getModel().getObject().getName()));
				item.add(new Label("editor",accountService.getPersonalBean(
						item.getModel().getObject().getAccountId()).getName()));
			}
		});

		add(new Link<Void>("returnManageContentPage") {
			private static final long serialVersionUID = -24536046271589249L;

			@Override
			public void onClick() {
				setResponsePage(new ManageContentPage(parent, category));
			}
		});

		add(new Link<Void>("toDeleteContentCompletedPage") {
			private static final long serialVersionUID = -3995644752145300523L;

			@Override
			public void onClick() {

				contentList.stream().forEach(content -> {
					Model<ContentPageBean> contentModel = Model.of(new ContentPageBean(
							contentService.content(content.getId())));
					setDefaultModel(CompoundPropertyModel.of(contentModel));


					File[] fileList = fileService.uploadDir(contentModel).listFiles();
					String beforeFileName = contentModel.getObject().getFileName();
					fileService.fileDelete(fileList, beforeFileName);
					contentService.deleteContent(content.getId());
				});
				setResponsePage(new ContentDeleteCompletedPage(parent, contentList, category));
			}
		});
	}
}
