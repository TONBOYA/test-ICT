package jp.ac.chitose.ui.content.content;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

import com.google.inject.Inject;

import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.service.Interface.IAccountService;
import jp.ac.chitose.service.Interface.IFileService;
import jp.ac.chitose.ui.CustomMultiLineLabel;

public class ContentPageForDistributionPanel extends Panel{

	@Inject
	protected IAccountService accountService;

	@Inject
	private IFileService fileService;

	public ContentPageForDistributionPanel(String id, IModel<ContentPageBean> contentModel) {
		super(id, contentModel);


		add(new Label("name"));
		add(new Label("author", accountService.getPersonalBean(contentModel.getObject().getAccountId()).getName()));
		add(new Label("keyword", String.join(",  ", contentModel.getObject().getKeyword())));
		add(new Label("tag", String.join(",   ", contentModel.getObject().getTag())));
		add(new Label("fileName"));
		add(new CustomMultiLineLabel("howToUse"));
		if (contentModel.getObject().isDeleteRequest() == true) {
			add(new Label("deleteRequest", "このコンテンツは削除申請されています"));
		} else {
			add(new Label("deleteRequest", ""));
		}

		// ファイルの一覧を表示するテーブル(ListViewの親コンポーネント)
				WebMarkupContainer table = new WebMarkupContainer("table");
				// Ajaxで更新可能にする
				table.setOutputMarkupId(true);
				table.setVisible(true);
				// アップロードされたファイルの一覧を表示するListViewを作成する
				PageableListView<File> listView = createFileListView(contentModel);
				table.add(listView);
				add(table);

	}

	// アップロードされたファイルの一覧を表示するListViewを作成する
		public PageableListView<File> createFileListView(IModel<ContentPageBean> contentModel) {
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

					// ファイルダウンロード用のリンクを作成
					DownloadLink link = new DownloadLink("downloadLink", file);
					link.add(new Label("fileName", file.getName()));
					listItem.add(link);
					// ファイルサイズを表示する
					// listItem.add(new Label("fileSize", file.length() + "B"));

					// 日付を表示する
					Date date = new Date(file.lastModified());
					SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
					listItem.add(new Label("uploadTime", format.format(date)));
				}
			};
			return listView;
		}

}
