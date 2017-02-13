package jp.ac.chitose.ui.practice;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;

import com.google.inject.Inject;

import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.option.PageType;
import jp.ac.chitose.service.Interface.IAccountService;
import jp.ac.chitose.service.Interface.IContentService;
import jp.ac.chitose.service.Interface.IFileService;
import jp.ac.chitose.ui.CustomMultiLineLabel;
import jp.ac.chitose.ui.DamiyPageForDistribution;
import jp.ac.chitose.ui.ParentPage;
import jp.ac.chitose.ui.content.content.ContentAndCategoryChoicePage;

public class PracticeContentForDistribution  extends ParentPage {
	private static final long serialVersionUID = 5629522355409011210L;

	@Inject
	private IContentService contentService;

	@Inject
	private IAccountService accountService;

	@Inject
	private IFileService fileService;


	PageType TYPE = PageType.DISTRIBUTION;

	@Override
	public String getTitle() {
		return "配布用紐づけコンテンツ閲覧ページ";
	}

	public PracticeContentForDistribution(int contentId, int practiceId) {

		IModel<ContentPageBean> contentModel = Model.of(contentService.selectContent(contentId));
		setDefaultModel(CompoundPropertyModel.of(contentModel));

		WebMarkupContainer searchPanel = new WebMarkupContainer("search");
		searchPanel.add(new Link<Void>("searchLink") {
			private static final long serialVersionUID = -999788449542738436L;

			@Override
			public void onClick() {
				setResponsePage(new ContentAndCategoryChoicePage());
			}
		});
		add(searchPanel);
		searchPanel.setVisible(true);


		WebMarkupContainer contentPanel = new WebMarkupContainer("content");
		add(contentPanel);
		contentPanel.setVisible(true);
		System.out.println(contentModel.getObject().isDeleteRequest());
		contentPanel.add(
				new Label("author", accountService.getPersonalBean(contentModel.getObject().getAccountId()).getName()));
		contentPanel.add(new Label("name"));
		contentPanel.add(new Label("keyword", String.join(" ", contentModel
				.getObject().getKeyword())));
		contentPanel.add(new Label("fileName"));
		contentPanel.add(new CustomMultiLineLabel("howToUse"));

		if(contentModel.getObject().isDeleteRequest() == true){
			contentPanel.add(new Label("deleteRequest","このコンテンツは削除申請されています"));

		}else{
			contentPanel.add(new Label("deleteRequest",""));
		}


		contentPanel.add(new Link<Void>("toPracticePageForDistribution") {
			static final long serialVersionUID = -5120511680469212239L;

			@Override
			public void onClick() {

				try{setResponsePage(new PracticePageForDistribution(practiceId));
				}catch(NullPointerException e){
					setResponsePage(new DamiyPageForDistribution());
				}
			}
		});

		// ファイルの一覧を表示するテーブル(ListViewの親コンポーネント)
		WebMarkupContainer table = new WebMarkupContainer("table");
		// Ajaxで更新可能にする
		table.setOutputMarkupId(true);
		table.setVisible(true);

		// アップロードされたファイルの一覧を表示するListViewを作成する
		PageableListView<File> listView = createFileListView(contentModel,TYPE);

		table.add(listView);
		contentPanel.add(table);
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
//							listItem.add(new Label("fileSize", file.length() + "B"));
							break;
						default: break;
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
