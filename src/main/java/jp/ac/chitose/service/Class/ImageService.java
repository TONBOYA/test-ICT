package jp.ac.chitose.service.Class;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.util.file.Files;
import org.apache.wicket.util.file.Folder;

import com.google.inject.Inject;

import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.bean.UnfoldingBean;
import jp.ac.chitose.option.RegisterDefinition;
import jp.ac.chitose.service.Interface.IImageService;
import jp.ac.chitose.service.Interface.IPracticeService;
import jp.ac.chitose.ui.MySession;

public class ImageService implements IImageService {

	@Inject
	private IPracticeService practiceService;

	@Override
	public String makeShelterImageFile(int accountId, final List<FileUpload> image, int unfoldingOrdinal,
			int imageOrdinal) {
		Folder directory = new Folder(String.format(RegisterDefinition.PRACTICE_SHELTER_PATH_FORMAT, accountId));

		if (!directory.exists()) {
			directory.mkdir();
		}

		String fileName = String.format(RegisterDefinition.PRACTICE_IMAGE_NAME_FORMAT, accountId, unfoldingOrdinal,
				imageOrdinal, StringUtils.substringAfterLast(image.get(0).getClientFileName(), "."));

		try {
			if (image != null) {
				File file = new File(directory, fileName);

				checkFileExists(file);

				image.get(0).writeTo(file);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return fileName;
	}

	@Override
	public void checkFileExists(File newFile) {
		if (newFile.exists()) {
			if (!Files.remove(newFile)) {
				throw new IllegalStateException("Unable to overwrite " + newFile.getAbsolutePath());
			}
		}
	}

	@Override
	public String deleteShelterImageFile(int accountId, String fileName) {
		if (!StringUtils.isBlank(fileName)) {
			File file = new File(String.format(RegisterDefinition.PRACTICE_SHELTER_PATH_FORMAT, accountId) + fileName);

			file.delete();
		}
		return "";

	}

	@Override
	public String deletePracticeImageFile(int practiceId, String fileName) {
		if (!StringUtils.isBlank(fileName)) {
			System.out.println("id= " + practiceId +" fileName= "+fileName+" deleteしました");
			File file = new File(String.format(RegisterDefinition.PRACTICE_IMAGE_PATH_FORMAT, practiceId) + fileName);

			file.delete();
		}
		return "";

	}

	@Override
	public boolean TranceferToImage(PracticeRegisterBean practiceBean, String fileName) {
		System.out.println(RegisterDefinition.PRACTICE_SHELTER_PATH_FORMAT + " + " +practiceBean.getAccountId()+" + "+ fileName);
		File file = new File(
				String.format(RegisterDefinition.PRACTICE_SHELTER_PATH_FORMAT, practiceBean.getAccountId()) + fileName);
		Folder directory = new Folder(
				String.format(RegisterDefinition.PRACTICE_IMAGE_PATH_FORMAT, practiceBean.getPracticeId()));
		System.out.println("Sysout( isDirectory?? " + directory.exists() +" ) ");
		if (!directory.exists()) {
			System.out.println("Sysout( directory.mkdir!! " + directory.mkdir() +" ) ");
			directory.mkdir();
		}System.out.println("isFile??" + directory.isFile());
		File destination = new File(
				String.format(RegisterDefinition.PRACTICE_IMAGE_PATH_FORMAT, practiceBean.getPracticeId()) + fileName);

		file.renameTo(destination);
		System.out.println("file.exists()?? " + file.exists());
		System.out.println("destination.exists()?? " + destination.exists());

		return true;
	}

	@Override
	public byte[] fileToByte(File file) {

		byte[] bImage = null;

		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				BufferedOutputStream bos = new BufferedOutputStream(baos);) {

			BufferedImage bs = imageRead(file);
			ImageIO.write(bs, "png", bos);
			bos.flush();
			bImage = baos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bImage;
	}

	public BufferedImage imageRead(File file) throws IOException {
		BufferedImage bs = ImageIO.read(file);
		int reSizeX = bs.getWidth();
		int reSizeY = bs.getHeight();
		if (bs.getWidth() >= 1000 || bs.getHeight() >= 1000) {
			if (bs.getWidth() >= 1200 || bs.getHeight() >= 1200) {
				System.out.println("1200以上です");
				reSizeX = reSizeX / 2;
				reSizeY = reSizeY / 2;
			} else {
				System.out.println("1000以上です");
				reSizeX = (int) (reSizeX / 1.5);
				reSizeY = (int) (reSizeY / 1.5);
			}
			BufferedImage reSize = new BufferedImage(reSizeX, reSizeY, bs.getType());
			reSize.getGraphics().drawImage(bs.getScaledInstance(reSizeX, reSizeY, Image.SCALE_AREA_AVERAGING), 0, 0,
					reSizeX, reSizeY, null);
			bs = reSize;
		}
		return bs;
	}

	@Override
	public boolean TranceferToShelter(int practiceId, String fileName) {
		int accountId = MySession.get().getAccountBean().getAccountId();
		File file = new File(
				String.format(RegisterDefinition.PRACTICE_IMAGE_PATH_FORMAT, practiceId) + fileName);
		Folder directory = new Folder(
				String.format(RegisterDefinition.PRACTICE_SHELTER_PATH_FORMAT, accountId));

		if (!directory.exists()) {
			directory.mkdir();
		}
		File destination = new File(
				String.format(RegisterDefinition.PRACTICE_SHELTER_PATH_FORMAT, accountId) + fileName);
		try {
			FileUtils.copyFile(file, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean Trancefer(int practiceId) {
		List<UnfoldingBean> unfoldingList = practiceService.selectUnfoldingList(practiceId);
		unfoldingList.stream().forEach(unfolding -> {
			if (unfolding.getFirstImage() != null) {
				TranceferToShelter(practiceId, unfolding.getFirstImage().getFileName());
			}
			if (unfolding.getSecondImage() != null) {
				TranceferToShelter(practiceId, unfolding.getSecondImage().getFileName());
			}
		});

		return true;
	}

}
