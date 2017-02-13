package jp.ac.chitose.service.Class;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.inject.Inject;

import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.bean.UnfoldingFormBean;
import jp.ac.chitose.dao.Interface.IPracticeTempDAO;
import jp.ac.chitose.service.Interface.IImageService;
import jp.ac.chitose.service.Interface.IPracticeTempService;

public class PracticeTempService implements IPracticeTempService{

	@Inject
	IPracticeTempDAO practiceTempDAO;

	@Inject
	private IImageService imageService;

	@Override
	public boolean registerTempPractice(PracticeRegisterBean practiceBean) {
		boolean result = practiceTempDAO.registerTempPractice(practiceBean);
		practiceBean.getUnfoldingList().stream().forEach(unfolding -> {
			if (!unfolding.getFirstImage().getFileName().equals("")) {
				imageService.TranceferToImage(practiceBean, unfolding.getFirstImage().getFileName());
			}
			if (!unfolding.getSecondImage().getFileName().equals("")) {
				imageService.TranceferToImage(practiceBean, unfolding.getSecondImage().getFileName());
			}
		});
		return result;
	}

	@Override
	public List<UnfoldingFormBean> registerTempUnfoldingList(int unfoldingNumber) {
		return IntStream.rangeClosed(1, unfoldingNumber).mapToObj(i -> new UnfoldingFormBean(i))
				.collect(Collectors.toList());

		// List<UnfoldingFormBean> unfoldingList = new ArrayList<>();
		// for(int i = 1;i <= unfoldingNumber;i++){
		// unfoldingList.add(new UnfoldingFormBean(i));
		// }
		// return unfoldingList;
	}

	@Override
	public boolean editTempPractice(PracticeRegisterBean practiceBean) {
		boolean result = practiceTempDAO.editTempPractice(practiceBean);
		practiceBean.getUnfoldingList().stream().forEach(unfolding -> {
			if (!unfolding.getFirstImage().getFileName().equals("")) {
				imageService.TranceferToImage(practiceBean, unfolding.getFirstImage().getFileName());
			}
			if (!unfolding.getSecondImage().getFileName().equals("")) {
				imageService.TranceferToImage(practiceBean, unfolding.getSecondImage().getFileName());
			}
		});
		return result;
	}

//	@Override
//	public PracticeBean makeTempPractice(int practiceId) {
//		return practiceTempDAO.selectTempPractice(practiceId);
//	}

}
