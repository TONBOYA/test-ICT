package jp.ac.chitose.ui.content.manager;

import java.io.File;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.util.lang.Bytes;

import com.google.inject.Inject;

import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.option.PageType;
import jp.ac.chitose.service.Interface.IContentHierarchyService;
import jp.ac.chitose.service.Interface.IFileService;
import jp.ac.chitose.service.Interface.ITextFormLimmiterService;
import jp.ac.chitose.ui.ParentPage;
import lombok.val;

public class ContentRegisterPage extends ParentPage {
	private static final long serialVersionUID = -1292025358662614782L;
	private final FileUploadField uploadField;

	@Inject
	private IContentHierarchyService contentHierarchyService;
	@Inject
	private IFileService fileService;
	@Inject
	private ITextFormLimmiterService textFormLimmiterService;

	@Override
	public String getTitle() {
		return "コンテンツ登録ページ";
	}

	public ContentRegisterPage(TreeBean parent, IModel<ContentPageBean> contentModel) {
		setDefaultModel(CompoundPropertyModel.of(contentModel));

		FeedbackPanel feedback = new FeedbackPanel("feedback") {
			private static final long serialVersionUID = 5700101250723122903L;

			@Override
			protected void onConfigure() {
				super.onConfigure();
				setOutputMarkupId(true);
			}
		};
		add(feedback);
//		add(new Label("path", contentHierarchyService.makeContentHierarchyPath(parent)));

		val form = new Form<Void>("form");

		// ファイルのアップロードに必要な設定
		form.setMultiPart(true);
		// アップロードできるのは10キロバイトまで
		form.setMaxSize(Bytes.megabytes(50));

		uploadField = new FileUploadField("uploadField", new ListModel<FileUpload>());

		ContentPreviewPanel previewPanel = new ContentPreviewPanel("preview", contentModel) {
			private static final long serialVersionUID = -7491956974520189882L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				setOutputMarkupId(true);
			}
		};
		AjaxButton button = new AjaxButton("submit", form) {
			private static final long serialVersionUID = -3792379128764156126L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);
				if (textFormLimmiterService.measureCharactersBlank(contentModel.getObject().getName())) {
					error("名前 欄に全角スペースを入れないでください。");
				} else if (contentModel.getObject().getTag2().equals(contentModel.getObject().getTag1())
						|| contentModel.getObject().getTag3().equals(contentModel.getObject().getTag1())
						|| contentModel.getObject().getTag3().equals(contentModel.getObject().getTag2())) {
					error("検索用キーワード 欄に同じ言葉を入れないでください。");
					target.add(feedback);
				} else {
					if (contentHierarchyService.searchSameName(parent, contentModel.getObject())) {
						// bean.getObject().setInputAt(LocalDateTime.now());

						System.out.println(uploadField.getFileUploads().size());
						FileUpload upload = uploadField.getFileUpload();
						if (upload == null) {
							// アップロードするファイルが見つからなければエラーメッセージ表示
							// して後は何もしない
							error("アップロードするファイルが見つかりません");
							target.add(feedback);
							return;
						}
						// アップロードしたファイルの名前を取得
						String fileName = upload.getClientFileName();
						File uploadFile = new File(fileService.uploadDir(contentModel), fileName);
						contentModel.getObject().setFileName(fileName);
						setResponsePage(new ContentRegisterConfirmPage(parent, contentModel, upload, uploadFile));
					} else {
						error("同じ名前のコンテンツが同じ階層にあります。コンテンツ名を変更してください。");
						target.add(feedback);
					}
				}
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				super.onError(target, form);
				target.add(feedback);
			}
		};
		form.add(button);
		form.add(previewPanel);
		form.add(new ContentFormPanel("panel", contentModel) {
			private static final long serialVersionUID = 8271215614639935158L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
			}

			@Override
			public void setContentTarget(AjaxRequestTarget target) {
				target.add(previewPanel);
			}
		});
		form.add(uploadField);
		add(form);
	}

	public ContentRegisterPage(TreeBean parent, IModel<ContentPageBean> contentModel,
			IModel<PracticeRegisterBean> practiceModel, PageType TYPE) {
		setDefaultModel(CompoundPropertyModel.of(contentModel));

		FeedbackPanel feedback = new FeedbackPanel("feedback") {
			private static final long serialVersionUID = 5700101250723122903L;

			@Override
			protected void onConfigure() {
				super.onConfigure();
				setOutputMarkupId(true);
			}
		};

		add(feedback);
//		add(new Label("path", contentHierarchyService.makeContentHierarchyPath(parent)));

		val form = new Form<Void>("form");
		// ファイルのアップロードに必要な設定
		form.setMultiPart(true);
		// アップロードできるのは10キロバイトまで
		form.setMaxSize(Bytes.megabytes(50));

		uploadField = new FileUploadField("uploadField", new ListModel<FileUpload>());

		ContentPreviewPanel previewPanel = new ContentPreviewPanel("preview", contentModel) {
			private static final long serialVersionUID = -7491956974520189882L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				setOutputMarkupId(true);
			}
		};

		AjaxButton button = new AjaxButton("submit", form) {
			private static final long serialVersionUID = -3792379128764156126L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);
				if (textFormLimmiterService.measureCharactersBlank(contentModel.getObject().getName())) {
					error("名前 欄に全角スペースを入れないでください。");
				} else {
					if (contentHierarchyService.searchSameName(parent, contentModel.getObject())) {
						// bean.getObject().setInputAt(LocalDateTime.now());

						System.out.println(uploadField.getFileUploads().size());
						FileUpload upload = uploadField.getFileUpload();
						if (upload == null) {
							// アップロードするファイルが見つからなければエラーメッセージ表示
							// して後は何もしない
							error("アップロードするファイルが見つかりません");
							target.add(feedback);
							return;
						}

						// アップロードしたファイルの名前を取得
						String fileName = upload.getClientFileName();
						File uploadFile = new File(fileService.uploadDir(contentModel), fileName);
						contentModel.getObject().setFileName(fileName);

						setResponsePage(new ContentRegisterConfirmPage(parent, contentModel, practiceModel, upload,
								uploadFile,TYPE));
					} else {
						System.out.println("else");
						error("同じ名前のコンテンツが同じ階層にあります。コンテンツ名を変更してください。");
						target.add(feedback);
					}
				}
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				super.onError(target, form);
				target.add(feedback);
			}
		};

		form.add(button);
		form.add(previewPanel);
		form.add(new ContentFormPanel("panel", contentModel) {
			private static final long serialVersionUID = 8271215614639935158L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
			}

			@Override
			public void setContentTarget(AjaxRequestTarget target) {
				target.add(previewPanel);
			}
		});
		form.add(uploadField);
		add(form);
	}
}
