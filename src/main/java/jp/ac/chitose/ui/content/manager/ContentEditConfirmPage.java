package jp.ac.chitose.ui.content.manager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.google.inject.Inject;

import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.option.PageType;
import jp.ac.chitose.service.Interface.IContentHierarchyService;
import jp.ac.chitose.service.Interface.IContentService;
import jp.ac.chitose.service.Interface.IFileService;
import jp.ac.chitose.ui.ParentPage;

public class ContentEditConfirmPage extends ParentPage {
	private static final long serialVersionUID = 5201857215542718384L;

	@Inject
	private IContentHierarchyService contentHierarchyService;

	@Inject
	IContentService contentService;

	@Inject
	private IFileService fileService;

	PageType TYPE = PageType.EDIT;

	@Override
	public String getTitle() {
		return "コンテンツ編集確認ページ";
	}

	public ContentEditConfirmPage(TreeBean parent, IModel<ContentPageBean> contentModel) {
		setDefaultModel(CompoundPropertyModel.of(contentModel));

		String category = contentModel.getObject().getCategory();

		List<String> tagList = new ArrayList<String>();
		tagList.add(contentModel.getObject().getTag1());
		tagList.add(contentModel.getObject().getTag2());
		tagList.add(contentModel.getObject().getTag3());
		contentModel.getObject().setTag(tagList);

		add(new Label("path", contentHierarchyService.makeContentHierarchyPath(parent)));
		add(new Label("fileName"));
		add(new Label("name"));
		add(new Label("howToUse"));
		add(new Label("keyword",String.join(" ", contentModel.getObject().getKeyword())));
		add(new Label("tag",String.join(" ",contentModel.getObject().getTag())));

		add(new Link<Void>("toEditContentCompletedPage") {
			static final long serialVersionUID = -4214959807629581609L;
			@Override
			public void onClick() {

					contentService.editContent(contentModel.getObject());
					setResponsePage(new ContentEditCompletedPage(parent, category));
			}
		});

		add(new Link<Void>("toEditContentPage") {
			private static final long serialVersionUID = 7192092120904967127L;
			@Override
			public void onClick() {
				setResponsePage(new ContentEditPage(parent, contentModel, category));
			}
		});
	}

	public ContentEditConfirmPage(TreeBean parent, IModel<ContentPageBean> contentModel, FileUpload upload,
			File uploadFile,String beforeFileName) {
		setDefaultModel(CompoundPropertyModel.of(contentModel));

		String category = contentModel.getObject().getCategory();

		List<String> tagList = new ArrayList<String>();
		tagList.add(contentModel.getObject().getTag1());
		tagList.add(contentModel.getObject().getTag2());
		tagList.add(contentModel.getObject().getTag3());
		contentModel.getObject().setTag(tagList);

		add(new Label("path", contentHierarchyService.makeContentHierarchyPath(parent)));
		add(new Label("fileName"));
		add(new Label("name"));
		add(new Label("howToUse"));
		add(new Label("keyword",String.join(" ", contentModel.getObject().getKeyword())));
		add(new Label("tag",String.join(" ",contentModel.getObject().getTag())));

		File[] fileList = fileService.uploadDir(contentModel).listFiles();
		System.out.println(contentModel.getObject().getFileName());

		add(new Link<Void>("toEditContentCompletedPage") {
			private static final long serialVersionUID = -6819793457153644989L;

			@Override
			public void onClick() {

					fileService.fileDelete(fileList, beforeFileName);
					fileService.fileWriting(upload, uploadFile, TYPE);
					contentService.editContent(contentModel.getObject());
					setResponsePage(new ContentEditCompletedPage(parent, category));
			}
		});

		add(new Link<Void>("toEditContentPage") {
			private static final long serialVersionUID = 7192092120904967127L;

			@Override
			public void onClick() {

				setResponsePage(new ContentEditPage(parent, contentModel, category));
			}
		});
	}
}
