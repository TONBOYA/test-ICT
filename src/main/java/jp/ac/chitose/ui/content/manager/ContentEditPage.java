package jp.ac.chitose.ui.content.manager;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.util.ListModel;
import org.apache.wicket.util.lang.Bytes;

import com.google.inject.Inject;

import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.option.PageType;
import jp.ac.chitose.service.Interface.IContentHierarchyService;
import jp.ac.chitose.service.Interface.IFileService;
import jp.ac.chitose.service.Interface.ITextFormLimmiterService;
import jp.ac.chitose.ui.ParentPage;
import lombok.val;

public class ContentEditPage extends ParentPage {
	private static final long serialVersionUID = 3696805542197364622L;
	private final FileUploadField uploadField;

	@Inject
	private IContentHierarchyService contentHierarchyService;

	@Inject
	private IFileService fileService;

	@Inject
	private ITextFormLimmiterService textFormLimmiterService;

	PageType TYPE = PageType.EDIT;

	@Override
	public String getTitle() {
		return "コンテンツ編集ページ";
	}

	public ContentEditPage(TreeBean parent, IModel<ContentPageBean> contentModel, String category) {
		setDefaultModel(CompoundPropertyModel.of(contentModel));
		contentModel.getObject().setCategory(category);
		// 更新される前のcontentBeanのNameを取得するため
		String beforeName = contentModel.getObject().getName();
		// 更新される前のcontentBeanのfileNameを取得するため
		String beforeFileName = contentModel.getObject().getFileName();

		FeedbackPanel feedback = new FeedbackPanel("feedback") {
			private static final long serialVersionUID = 2258521264195276538L;

			@Override
			protected void onConfigure() {
				super.onConfigure();
				setOutputMarkupId(true);
			}
		};

		add(feedback);
//		add(new Label("path", contentHierarchyService.makeContentHierarchyPath(parent)));

		val form = new Form<Void>("form");

		uploadField = new FileUploadField("uploadField", new ListModel<FileUpload>());
		// ファイルのアップロードに必要な設定
		form.setMultiPart(true);
		// アップロードできるのは50MBまで
		form.setMaxSize(Bytes.megabytes(50));

		// ファイルの一覧を表示するテーブル(ListViewの親コンポーネント)
		WebMarkupContainer table = new WebMarkupContainer("table");
		// Ajaxで更新可能にする
		table.setOutputMarkupId(true);
		table.setVisible(true);

		// アップロードされたファイルの一覧を表示するListViewを作成する
		PageableListView<File> listView = createFileListView(contentModel, TYPE);
		// 表示用のラベルを作成
		table.add(listView);
		form.add(table);

		ContentPreviewPanel previewPanel = new ContentPreviewPanel("preview", contentModel) {
			private static final long serialVersionUID = -9142745536410910041L;

			@Override
			protected void onInitialize() {
				super.onInitialize();
				setOutputMarkupId(true);
			}
		};

		AjaxButton button = new AjaxButton("submit", form) {
			private static final long serialVersionUID = 8415203038366657521L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);

				System.out.println(uploadField.getFileUploads().size());
				FileUpload upload = uploadField.getFileUpload();
				if (textFormLimmiterService.measureCharactersBlank(contentModel.getObject().getName())) {
					error("名前 欄に全角スペースを入れないでください。");
				}else if (contentModel.getObject().getTag2().equals(contentModel.getObject().getTag1())
						|| contentModel.getObject().getTag3().equals(contentModel.getObject().getTag1())
						|| contentModel.getObject().getTag3().equals(contentModel.getObject().getTag2())) {
					error("検索用キーワード 欄に同じ言葉を入れないでください。");
				}
				else if (contentModel.getObject().getTag1() == null || contentModel.getObject().getTag2() == null
						|| contentModel.getObject().getTag3() == null) {
					error("検索用キーワード 欄を埋めてください。");
				}else {
					if (contentModel.getObject().getName().equals(beforeName)) {
						if (upload == null) {
							setResponsePage(new ContentEditConfirmPage(parent, contentModel));
						} else {
							// アップロードしたファイルの名前を取得
							String fileName = upload.getClientFileName();
							File uploadFile = new File(fileService.uploadDir(contentModel), fileName);
							contentModel.getObject().setFileName(fileName);
							setResponsePage(new ContentEditConfirmPage(parent, contentModel, upload, uploadFile,
									beforeFileName));
						}
					} else {
						if (contentHierarchyService.searchSameName(parent, contentModel.getObject())) {
							// bean.getObject().setInputAt(LocalDateTime.now());

							if (upload == null) {
								setResponsePage(new ContentEditConfirmPage(parent, contentModel));
							} else {
								// アップロードしたファイルの名前を取得
								String fileName = upload.getClientFileName();
								File uploadFile = new File(fileService.uploadDir(contentModel), fileName);
								contentModel.getObject().setFileName(fileName);
								setResponsePage(new ContentEditConfirmPage(parent, contentModel, upload, uploadFile,
										beforeFileName));
							}
						} else {
							System.out.println("else");
							error("同じ名前のコンテンツが同じ階層にあります。コンテンツ名を変更してください。");
						}
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
			private static final long serialVersionUID = 6448365461192773682L;

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

	// アップロードされたファイルの一覧を表示するListViewを作成する
	public PageableListView<File> createFileListView(IModel<ContentPageBean> contentModel, PageType TYPE) {

		// ファイル一覧を返すモデルを作成する
		IModel<ArrayList<File>> uploadListModel = new LoadableDetachableModel<ArrayList<File>>() {
			private static final long serialVersionUID = -674556500134384372L;

			@Override
			protected ArrayList<File> load() {
				// アップロードされたファイルのリスト(一覧)を返す
				File[] fileList = fileService.uploadDir(contentModel).listFiles();
				ArrayList<File> content = new ArrayList<>();
				System.out.println(fileList.length);
				for (int i = 0; i < fileList.length; i++) {
					if (fileList[i].getName().equals(contentModel.getObject().getFileName())) {
						content.add(fileList[i]);
					}
				}
				return content;
			}
		};

		PageableListView<File> listView = new PageableListView<File>("uploadList", uploadListModel, 5) {
			private static final long serialVersionUID = 542364013907494144L;

			@Override
			protected void populateItem(ListItem<File> listItem) {
				File file = (File) listItem.getModelObject();

				switch (TYPE) {
				case EDIT:
					// 表示用のラベルを作成
					listItem.add(new Label("fileName", file.getName()));
					break;
				case DISTRIBUTION:
					// ファイルダウンロード用のリンクを作成
					DownloadLink link = new DownloadLink("downloadLink", file);
					link.add(new Label("fileName", file.getName()));
					listItem.add(link);
					// ファイルサイズを表示する
					// listItem.add(new Label("fileSize", file.length() + "B"));
					break;
				default:
					break;
				}
				// 日付を表示する
				Date date = new Date(file.lastModified());
				SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
				listItem.add(new Label("uploadTime", format.format(date)));
			}
		};
		return listView;
	}
}
