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
import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.option.PageType;
import jp.ac.chitose.service.Interface.IContentHierarchyService;
import jp.ac.chitose.service.Interface.IContentService;
import jp.ac.chitose.service.Interface.IFileService;
import jp.ac.chitose.ui.ParentPage;

public class ContentRegisterConfirmPage extends ParentPage {
	private static final long serialVersionUID = 3785026762884613958L;

	@Inject
	private IContentHierarchyService contentHierarchyService;

	@Inject
	IContentService contentService;

	@Inject
	private IFileService fileService;

	PageType TYPE = PageType.REGISTER;

	@Override
	public String getTitle() {
		return "コンテンツ登録確認ページ";
	}

	public ContentRegisterConfirmPage(TreeBean parent, IModel<ContentPageBean> contentModel, FileUpload upload,
			File uploadFile) {
		setDefaultModel(CompoundPropertyModel.of(contentModel));
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

		add(new Link<Void>("toRegisterContentCompletedPage") {
			private static final long serialVersionUID = 1000370685618617097L;

			@Override
			public void onClick() {

				if (contentHierarchyService.searchSameName(parent, contentModel.getObject())) {
					contentService.registerContent(contentModel.getObject());
					fileService.fileWriting(upload, uploadFile, TYPE);
					setResponsePage(new ContentRegisterCompletedPage(parent, contentModel.getObject().getCategory()));
				} else {
					// target.add(feedback);
					setResponsePage(new ContentRegisterPage(parent, contentModel));
					error("同じ名前の実践が同じ階層にあります。実践名を変更してください。");
				}
			};
		});

		add(new Link<Void>("returnRegisterContentPage") {
			private static final long serialVersionUID = 4190229190073540744L;

			@Override
			public void onClick() {
				setResponsePage(new ContentRegisterPage(parent, contentModel));
			};
		});
	}

	public ContentRegisterConfirmPage(TreeBean parent, IModel<ContentPageBean> contentModel,IModel<PracticeRegisterBean> practiceModel, FileUpload upload,
			File uploadFile, PageType TYPE) {
		setDefaultModel(CompoundPropertyModel.of(contentModel));

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

		add(new Link<Void>("toRegisterContentCompletedPage") {
			private static final long serialVersionUID = 1000370685618617097L;

			@Override
			public void onClick() {

				if (contentHierarchyService.searchSameName(parent, contentModel.getObject())) {
					contentService.registerContent(contentModel.getObject());
					fileService.fileWriting(upload, uploadFile, TYPE);
					setResponsePage(new ContentRegisterCompletedPage(parent, practiceModel, contentModel.getObject().getCategory(),TYPE));
				} else {
					setResponsePage(new ContentRegisterPage(parent, contentModel,practiceModel,TYPE));
					error("同じ名前の実践が同じ階層にあります。実践名を変更してください。");
				}
			};
		});

		add(new Link<Void>("returnRegisterContentPage") {
			private static final long serialVersionUID = 4190229190073540744L;

			@Override
			public void onClick() {
				setResponsePage(new ContentRegisterPage(parent, contentModel));
			};
		});
	}
}
