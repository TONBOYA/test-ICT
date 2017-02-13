package jp.ac.chitose.service.Interface;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.wicket.markup.html.form.upload.FileUpload;

import com.google.inject.ImplementedBy;

import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.service.Class.ImageService;

@ImplementedBy(ImageService.class)
public interface IImageService {
	public String makeShelterImageFile(int AccountId, List<FileUpload> image, int unfoldingOrdinal, int imageOrdinal);

	public String deleteShelterImageFile(int accountId, String fileName);

	public String deletePracticeImageFile(int practiceId, String fileName);

	public void checkFileExists(File newFile);

	public boolean TranceferToImage(PracticeRegisterBean practiceBean, String fileName);

	public boolean TranceferToShelter(int practiceId, String fileName);

	public byte[] fileToByte(File file);

	public BufferedImage imageRead(File file) throws IOException;

	public boolean Trancefer(int practiceId);
}
