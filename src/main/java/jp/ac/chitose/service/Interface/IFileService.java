package jp.ac.chitose.service.Interface;

import java.io.File;

import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.model.IModel;

import com.google.inject.ImplementedBy;

import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.option.PageType;
import jp.ac.chitose.service.Class.FileService;

@ImplementedBy(FileService.class)
public interface IFileService {

	public File uploadDir(IModel<ContentPageBean> content);

	//public PageableListView<File> createFileListView(IModel<ContentPageBean> contentModel,PageType TYPE);

	public void fileWriting(FileUpload upload, File uploadFile, PageType TYPE);

	public void fileDelete(File[] fileList, String beforeFileName);
}
