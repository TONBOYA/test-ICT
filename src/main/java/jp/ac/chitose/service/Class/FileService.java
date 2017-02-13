package jp.ac.chitose.service.Class;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.option.PageType;
import jp.ac.chitose.service.Interface.IFileService;

public class FileService implements IFileService {

	@Override
	public File uploadDir(IModel<ContentPageBean> contentModel) {
		CompoundPropertyModel.of(contentModel);

		File uploadDir;
		// アップロードされたファイルを保存するディレクトリを決める
		try {
			uploadDir = getDirectory("/ict-epdms/content/" + contentModel.getObject().getAccountId());
		} catch (IOException e) {
			e.printStackTrace();
			uploadDir = null;
			// TODO errorPageに飛ばす
		}
		return uploadDir;
	}

	// アップロードされたクライアントのファイルをサーバーのファイルに書き込む
	@Override
	public void fileWriting(FileUpload upload, File uploadFile, PageType TYPE) {
		try {
			switch (TYPE) {
			case REGISTER:
				System.out.println("tryRegister"); break;
			case EDIT:
				System.out.println("tryEdit"); break;
			default: break;
			}
			upload.writeTo(uploadFile);
		} catch (Exception e) {
			// TODO errorPageに飛ばす
			e.printStackTrace();
			System.out.println("Exce");
			return;
		} finally {
			System.out.println("finally");
			upload.closeStreams();
		}
	}
	// アップロードされたファイルをDeleteするメソッド
	@Override
	public void fileDelete(File[] fileList, String beforeFileName) {
		// ディレクトリのファイル数を確認
		System.out.println(fileList.length);
		// ファイル数だけ回してfileNameを照合させる
		for (int i = 0; i < fileList.length; i++) {
			System.out.println(fileList[i]);
			if (fileList[i].getName().equals(beforeFileName)) {
				System.out.println(beforeFileName);
				System.out.println("tryDelete");
				fileList[i].delete();
			}
		}
	}
//	// アップロードされたファイルの一覧を表示するListViewを作成する
//	@Override
//	public PageableListView<File> createFileListView(IModel<ContentPageBean> contentModel, PageType TYPE) {
//
//		// ファイル一覧を返すモデルを作成する
//		IModel<ArrayList<File>> uploadListModel = new LoadableDetachableModel<ArrayList<File>>() {
//			private static final long serialVersionUID = -674556500134384372L;
//			@Override
//			protected ArrayList<File> load() {
//				// アップロードされたファイルのリスト(一覧)を返す
//				File[] fileList = uploadDir(contentModel).listFiles();
//				ArrayList<File> content = new ArrayList<>();
//				System.out.println(fileList.length);
//				for (int i = 0; i < fileList.length; i++) {
//					if (fileList[i].getName().equals(contentModel.getObject().getFileName())) {
//						content.add(fileList[i]);
//					}
//				}
//				return content;
//			}
//		};
//
//		PageableListView<File> listView = new PageableListView<File>("uploadList", uploadListModel, 5) {
//			private static final long serialVersionUID = 542364013907494144L;
//			@Override
//			protected void populateItem(ListItem<File> listItem) {
//				File file = (File) listItem.getModelObject();
//
//				switch (TYPE) {
//				case EDIT:
//					// 表示用のラベルを作成
//					listItem.add(new Label("fileName", file.getName()));
//					break;
//				case DISTRIBUTION:
//					// ファイルダウンロード用のリンクを作成
//					DownloadLink link = new DownloadLink("downloadLink", file);
//					link.add(new Label("fileName", file.getName()));
//					listItem.add(link);
//				    // ファイルサイズを表示する
////					listItem.add(new Label("fileSize", file.length() + "B"));
//					break;
//				default: break;
//				}
//				// 日付を表示する
//				Date date = new Date(file.lastModified());
//				SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
//				listItem.add(new Label("uploadTime", format.format(date)));
//			}
//		};
//		return listView;
//	}

	protected ArrayList<File> load(IModel<ContentPageBean> contentModel) {
		// アップロードされたファイルのリスト(一覧)を返す
		File[] fileList = uploadDir(contentModel).listFiles();
		ArrayList<File> content = new ArrayList<>();
		System.out.println(fileList.length);
		for (int i = 0; i < fileList.length; i++) {
			if (fileList[i].getName().equals(contentModel.getObject().getFileName())) {
				content.add(fileList[i]);
			}
		}
		return content;
	}

	public File getDirectory(String dirName) throws IOException {

		Path uploadPath = FileSystems.getDefault().getPath(dirName);
		System.out.println(uploadPath.toString());
		Files.createDirectories(uploadPath);

		return uploadPath.toFile();
	}
}
