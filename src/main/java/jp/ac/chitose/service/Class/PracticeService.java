package jp.ac.chitose.service.Class;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import jp.ac.chitose.bean.PracticeBean;
import jp.ac.chitose.bean.PracticeImageBean;
import jp.ac.chitose.bean.PracticeKeywordBean;
import jp.ac.chitose.bean.PracticePageBean;
import jp.ac.chitose.bean.PracticeRegisterBean;
import jp.ac.chitose.bean.PracticeTagBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.bean.UnfoldingBean;
import jp.ac.chitose.bean.UnfoldingFormBean;
import jp.ac.chitose.dao.Interface.IPracticeContentDAO;
import jp.ac.chitose.dao.Interface.IPracticeDAO;
import jp.ac.chitose.dao.Interface.IPracticeImageDAO;
import jp.ac.chitose.dao.Interface.IPracticeKeywordDAO;
import jp.ac.chitose.dao.Interface.IPracticeTagDAO;
import jp.ac.chitose.dao.Interface.IUnfoldingDAO;
import jp.ac.chitose.service.Interface.IImageService;
import jp.ac.chitose.service.Interface.IPracticeService;

public class PracticeService implements IPracticeService {
	int ITEM_SIZE;
	int keyNum;
	int PRACTICE_KEY_ID;
	int keywordNum;
	int LOOP_SIZE;

	@Inject
	IPracticeDAO practiceDAO;

	@Inject
	IUnfoldingDAO unfoldingDAO;

	@Inject
	IPracticeKeywordDAO practiceKeywordDAO;

	@Inject
	IPracticeTagDAO practiceTagDAO;

	@Inject
	IPracticeContentDAO practiceContentDAO;

	@Inject
	IPracticeImageDAO practiceImageDAO;

	@Inject
	private IImageService imageService;

	@Override
	public boolean registerPractice(PracticeRegisterBean practiceBean) {
		practiceDAO.registerPractice(practiceBean);
		practiceKeywordDAO.registerPracticeKeyword(practiceBean);
		practiceTagDAO.registerPracticeTag(practiceBean);
		if (!practiceBean.getUnfoldingList().isEmpty()) {
			unfoldingDAO.registerUnfolding(practiceBean);
			practiceImageDAO.registerPracticeImage(practiceBean);
		}
		practiceBean.getUnfoldingList().stream().forEach(unfolding -> {
			if (!unfolding.getFirstImage().getFileName().equals("")) {
				imageService.TranceferToImage(practiceBean, unfolding.getFirstImage().getFileName());
			}
			if (!unfolding.getSecondImage().getFileName().equals("")) {
				imageService.TranceferToImage(practiceBean, unfolding.getSecondImage().getFileName());
			}
		});
		return true;
	}

	@Override
	public List<UnfoldingFormBean> registerUnfoldingList(int unfoldingNumber) {

		List<UnfoldingFormBean> unfoldingList = new ArrayList<>();
		for (int i = 1; i <= unfoldingNumber; i++) {
			unfoldingList.add(new UnfoldingFormBean(i));

			if (unfoldingNumber == 3) {
				if (unfoldingList.size() == 1) {
					unfoldingList.get(i - 1).setName("導入");
				} else if (unfoldingList.size() == 2) {
					unfoldingList.get(i - 1).setName("展開");
				} else if (unfoldingList.size() == 3) {
					unfoldingList.get(i - 1).setName("まとめ");
				}
			} else if (unfoldingNumber == 4) {
				if (unfoldingList.size() == 1) {
					unfoldingList.get(i - 1).setName("第一次");
				} else if (unfoldingList.size() == 2) {
					unfoldingList.get(i - 1).setName("第二次");
				} else if (unfoldingList.size() == 3) {
					unfoldingList.get(i - 1).setName("第三次");
				} else if (unfoldingList.size() == 4) {
					unfoldingList.get(i - 1).setName("第四次");
				}
			}
		}
		return unfoldingList;
	}

	@Override
	public boolean editPractice(PracticeRegisterBean practiceBean) {
		practiceDAO.editPractice(practiceBean);
		practiceKeywordDAO.deletePracticeKeyword(practiceBean.getPracticeId());
		practiceKeywordDAO.registerPracticeKeyword(practiceBean);
		practiceTagDAO.deletePracticeTag(practiceBean.getPracticeId());
		practiceTagDAO.registerPracticeTag(practiceBean);
		if (!practiceBean.getUnfoldingList().isEmpty()) {
			unfoldingDAO.editUnfolding(practiceBean);
			practiceImageDAO.editPracticeImage(practiceBean);
		}

		practiceBean.getUnfoldingList().stream().forEach(unfolding -> {
			if (!unfolding.getFirstImage().getFileName().equals("")) {

				System.out.println("Sysout( " + unfolding.getFirstImage().getFileName()+" ) ");
				imageService.deletePracticeImageFile(practiceBean.getPracticeId(), unfolding.getFirstImage().getFileName());
				imageService.TranceferToImage(practiceBean, unfolding.getFirstImage().getFileName());
				imageService.deleteShelterImageFile(practiceBean.getAccountId(), unfolding.getFirstImage().getFileName());
			}
			if (!unfolding.getSecondImage().getFileName().equals("")) {
				System.out.println("Sysout( " + unfolding.getSecondImage().getFileName()+" ) ");

				imageService.deletePracticeImageFile(practiceBean.getPracticeId(), unfolding.getSecondImage().getFileName());
				imageService.TranceferToImage(practiceBean, unfolding.getSecondImage().getFileName());
				imageService.deleteShelterImageFile(practiceBean.getAccountId(), unfolding.getSecondImage().getFileName());
			}
		});
		return true;
	}

	@Override
	public List<PracticeImageBean> searchImage(int unfoldingId) {
		return practiceImageDAO.searchImage(unfoldingId);
	}

	public int selectContentId (int practiceId){

		return practiceContentDAO.selectContentId(practiceId).getContentId();
	}

	@Override
	public PracticePageBean selectPracticeAndContent(int practiceId) {

		List<PracticeKeywordBean> keyword = practiceKeywordDAO.selectPracticeKeywordList(practiceId);
		List<String> stringKeyword = new ArrayList<String>();
		keyword.stream().forEach(bean -> {
			stringKeyword.add(bean.getKeyword());
		});

		List<PracticeTagBean> tag = practiceTagDAO.selectPracticeTagList(practiceId);
		List<String> stringTag = new ArrayList<String>();
		tag.stream().forEach(bean -> {
			stringTag.add(bean.getTag());
		});

		return new PracticePageBean(practiceDAO.selectPractice(practiceId), selectUnfoldingList(practiceId),
				stringKeyword, stringTag, practiceContentDAO.selectContentId(practiceId).getContentId());
	}

	@Override
	public PracticePageBean selectPractice(int practiceId) {
		List<PracticeKeywordBean> keyword = practiceKeywordDAO.selectPracticeKeywordList(practiceId);
		List<String> stringKeyword = new ArrayList<String>();
		keyword.stream().forEach(bean -> {
			stringKeyword.add(bean.getKeyword());
		});

		List<PracticeTagBean> tag = practiceTagDAO.selectPracticeTagList(practiceId);
		List<String> stringTag = new ArrayList<String>();
		tag.stream().forEach(bean -> {
			stringTag.add(bean.getTag());
		});

		return new PracticePageBean(practiceDAO.selectPractice(practiceId), selectUnfoldingList(practiceId),
				stringKeyword, stringTag);
	}

	@Override
	public UnfoldingFormBean selectUnfolding(int practiceId, int ordinal) {
		return unfoldingDAO.selectUnfolding(practiceId, ordinal);
	}

	@Override
	public List<PracticeKeywordBean> selectPracticeKeyword(int practiceId) {
		return practiceKeywordDAO.selectPracticeKeywordList(practiceId);
	}

	@Override
	public List<PracticeTagBean> selectPracticeTag(int practiceId) {
		return practiceTagDAO.selectPracticeTagList(practiceId);
	}

	@Override
	public boolean deletePracticeList(List<TreeBean> praciticeList) {
		praciticeList.stream().forEach(practice -> {
			deletePractice(practice.getId());
		});
		return true;
	}

	@Override
	public List<String> selectAllTagList(String category) {

		List<PracticeTagBean> tagBeans = practiceTagDAO.selectCategoryTagList(category);
		List<String> tagList = new ArrayList<String>();// return
		LOOP_SIZE = 0;

		for (int TAG_SIZE = 1; TAG_SIZE <= tagBeans.size(); TAG_SIZE++) {
			LOOP_SIZE++;
			if (LOOP_SIZE <= tagBeans.size()) {
				System.out.println("loop " + LOOP_SIZE + " = " + tagBeans.size());
				for (int REMOVE_SIZE = 1; REMOVE_SIZE <= tagBeans.size(); REMOVE_SIZE++) {

					if (LOOP_SIZE == REMOVE_SIZE) {
						System.out.print(tagBeans.get(TAG_SIZE - 1).getTag());
						System.out.print(" = false, Not remove ");
						System.out.println(tagBeans.get(REMOVE_SIZE - 1));
					} else if (tagBeans.get(TAG_SIZE - 1).getTag().equals(tagBeans.get(REMOVE_SIZE - 1).getTag())) {
						System.out.print(tagBeans.get(TAG_SIZE - 1).getTag());
						System.out.print(" b true, remove this ");
						System.out.println(tagBeans.remove(REMOVE_SIZE - 1));
						REMOVE_SIZE--;
					} else {
						System.out.print(tagBeans.get(TAG_SIZE - 1).getTag());
						System.out.print(" p false, Not remove ");
						System.out.println(tagBeans.get(REMOVE_SIZE - 1));
					}
				}
				System.out.println("tagList.add(tag)");
				tagList.add(tagBeans.get(TAG_SIZE - 1).getTag());
			} else {
				System.out.println("break " + LOOP_SIZE + " = " + tagBeans.size());
				break;
			}
		}
		;
		return tagList;
	}

	@Override
	public List<PracticeBean> keywordSearchOfPractice(List<String> keywords) {
		List<PracticeKeywordBean> keyBeans = practiceKeywordDAO.selectAllKeywordList();
		List<Integer> selectedKeywordsId = new ArrayList<Integer>();
		List<PracticeBean> practiceBeans = new ArrayList<PracticeBean>();// return
		ITEM_SIZE = 0;
		LOOP_SIZE = 0;
		keywords.stream().forEach(keyword -> {
			LOOP_SIZE++;
			System.out.println(keyword);
			System.out.println();
			for (int REMOVE_SIZE = 1; REMOVE_SIZE <= keyBeans.size(); REMOVE_SIZE++) {
				// keyword または practiceId で(keyBeans.size() - 1)される
				// LOOP_SIZE <= keyBeans.size() まで判定を続ける
				if (keyBeans.get(REMOVE_SIZE - 1).getKeyword().equals(keyword)) {
					PRACTICE_KEY_ID = keyBeans.get(REMOVE_SIZE - 1).getPracticeId();
					selectedKeywordsId.add(PRACTICE_KEY_ID);
					System.out.println("REMOVE_SIZE: " + (REMOVE_SIZE - 1) + " getKeyword good "
							+ keyBeans.remove(REMOVE_SIZE - 1) + " " + keyword);
					System.out.println("remove:" + keyBeans.size() + "count: " + ITEM_SIZE++);
					System.out.println();
					REMOVE_SIZE--;
				} else if (keyBeans.get(REMOVE_SIZE - 1).getPracticeId() == PRACTICE_KEY_ID) {
					System.out.println("REMOVE_SIZE: " + (REMOVE_SIZE - 1) + " getKeyId good "
							+ keyBeans.remove(REMOVE_SIZE - 1) + " " + PRACTICE_KEY_ID);
					System.out.println(" remove:" + keyBeans.size() + "count: " + ITEM_SIZE++);
					System.out.println();
					REMOVE_SIZE--;
				} else {
					System.out.println("REMOVE_SIZE: " + (REMOVE_SIZE - 1) + " getElseData bad " + keyword + ", "
							+ keyBeans.get(REMOVE_SIZE - 1));
					System.out.println("notRemove:" + keyBeans.size());
					System.out.println();
				}
			}
		});
		selectedKeywordsId.stream().forEach(selectedKeywordId -> {
			System.out.println(selectedKeywordId);
			practiceBeans.add(practiceDAO.searchOfPractice(selectedKeywordId));
		});
		return practiceBeans;
	}

	@Override
	public List<PracticeBean> tagSearchOfPractice(List<String> tags) {

		List<PracticeTagBean> tagBeans = practiceTagDAO.selectAllTagList();
		List<Integer> selectedTagsId = new ArrayList<Integer>();
		List<PracticeBean> practiceBeans = new ArrayList<PracticeBean>();// return
		ITEM_SIZE = 0;
		LOOP_SIZE = 0;
		tags.stream().forEach(tag -> {
			LOOP_SIZE++;
			System.out.println(tag);
			System.out.println();
			for (int REMOVE_SIZE = 1; REMOVE_SIZE <= tagBeans.size(); REMOVE_SIZE++) {
				// keyword または practiceId で(keyBeans.size() - 1)される
				// LOOP_SIZE <= keyBeans.size() まで判定を続ける
				if (tagBeans.get(REMOVE_SIZE - 1).getTag().equals(tag)) {
					PRACTICE_KEY_ID = tagBeans.get(REMOVE_SIZE - 1).getPracticeId();
					selectedTagsId.add(PRACTICE_KEY_ID);
					System.out.println("REMOVE_SIZE: " + (REMOVE_SIZE - 1) + " getKeyword good "
							+ tagBeans.remove(REMOVE_SIZE - 1) + " " + tag);
					System.out.println("remove:" + tagBeans.size() + "count: " + ITEM_SIZE++);
					System.out.println();
					REMOVE_SIZE--;
				} else if (tagBeans.get(REMOVE_SIZE - 1).getPracticeId() == PRACTICE_KEY_ID) {
					System.out.println("REMOVE_SIZE: " + (REMOVE_SIZE - 1) + " getKeyId good "
							+ tagBeans.remove(REMOVE_SIZE - 1) + " " + PRACTICE_KEY_ID);
					System.out.println(" remove:" + tagBeans.size() + "count: " + ITEM_SIZE++);
					System.out.println();
					REMOVE_SIZE--;
				} else {
					System.out.println("REMOVE_SIZE: " + (REMOVE_SIZE - 1) + " getElseData bad " + tag + ", "
							+ tagBeans.get(REMOVE_SIZE - 1));
					System.out.println("notRemove:" + tagBeans.size());
					System.out.println();
				}
			}
		});
		selectedTagsId.stream().forEach(selectedTagId -> {
			System.out.println(selectedTagId);
			practiceBeans.add(practiceDAO.searchOfPractice(selectedTagId));
		});

		return practiceBeans;

	}

	@Override
	public List<PracticeBean> searchPractice(List<String> keywords, List<String> tagList) {

		List<Integer> selectedKeywordsId = new ArrayList<Integer>();
		List<PracticeBean> practiceBean = new ArrayList<PracticeBean>();

		List<PracticeKeywordBean> keyBeans = practiceKeywordDAO.selectAllKeywordList();
		List<PracticeTagBean> tagBeans = new ArrayList<PracticeTagBean>();
		keyNum = 1;

		keywordNum = 0;

		keyBeans.stream().forEach(sysout -> {// 確認用
			System.out.print(keyNum + " ");
			System.out.print(sysout.getKeyword() + " ");
			System.out.print(sysout.getPracticeId());
			keyNum++;
		});

		keywords.stream().forEach(keyword -> {// せｎ
			keywordNum++;
			PRACTICE_KEY_ID = 0;
			System.out.println(keywordNum + ", " + keyword + ", " + keyBeans.size());
			for (int i = 1; i <= keyBeans.size(); i++) {
				if (keyBeans.size() > 0) {

					if (keyBeans.get(i - 1).getKeyword().equals(keyword)) {
						PRACTICE_KEY_ID = keyBeans.get(i - 1).getPracticeId();
						System.out.println();
						System.out.println(selectedKeywordsId.add(PRACTICE_KEY_ID));
						System.out.println(i - 1 + " getKeyword good" + keyBeans.remove(i - 1) + " " + keyword);
						System.out.println(" remove:" + keyBeans.size());
						System.out.println("count: " + ITEM_SIZE);
						System.out.println();
						i--;
						ITEM_SIZE++;
						if (keyBeans.size() == 1) {
							i = 0;
							keyBeans.add(null);
							System.out.println();
							System.out.println(i + " notRemove:" + keyBeans.size());
							System.out.println();
							break;
						} else if (keywordNum == keywords.size()) {

						}
					} else if (keyBeans.get(i - 1).getPracticeId() == PRACTICE_KEY_ID) {
						System.out.println();
						System.out.print(i - 1 + " getPracticeId good ");
						System.out.println(keyBeans.remove(i - 1) + " " + PRACTICE_KEY_ID);
						System.out.println(" remove:" + keyBeans.size());
						System.out.println("count: " + ITEM_SIZE);
						System.out.println();
						i--;
						ITEM_SIZE++;
					} else {
						System.out.println();
						System.out.print(i - 1 + " getElseData bad " + keyword);
						System.out.print(" " + keyBeans.get(i - 1).getKeyword() + " "
								+ keyBeans.get(i - 1).getPracticeId() + " add:");
						System.out.println(i - 1 + " notRemove:" + keyBeans.size());
						System.out.println();
					}
				}
				System.out.println("continue " + keyBeans.size());
				continue;
			}
			if (keywordNum == keywords.size()) {
				System.out.println();
				if (keyBeans.get(0) == null) {
					System.out.println("中身はnullです");
					keyBeans.remove(0);
				}
				System.out.println(0 + " remove:" + keyBeans.size());
				System.out.println();
			}
		});
		selectedKeywordsId.stream().forEach(selectedKeyword -> {

			practiceTagDAO.selectPracticeTagList(selectedKeyword).stream().forEach(tag -> {
				tagBeans.add(tag);
			});

			// tagBeans.add(practiceTagDAO.selectPracticeTagList(selectedKeyword));
			practiceBean.add(practiceDAO.searchOfPractice(selectedKeyword));
		});

		System.out.println(tagBeans);
		return practiceBean;
	}

	@Override
	public boolean deletePractice(int practiceId) {
		List<UnfoldingBean> unfoldingList = selectUnfoldingList(practiceId);
		unfoldingList.stream().forEach(bean -> {
			practiceImageDAO.deleteImage(bean.getUnfoldingId());
		});
		unfoldingDAO.deleteUnfolding(practiceId);
		practiceKeywordDAO.deletePracticeKeyword(practiceId);
		if (!(selectPractice(practiceId).getTag().size() == 0)) {
			practiceTagDAO.deletePracticeTag(practiceId);
		}

		try {
			if (!(selectPracticeAndContent(practiceId).getContentId() == 0)) {
				practiceContentDAO.deletePracticeContent(practiceId);
			} else {
			}
		} catch (NullPointerException e) {

		}
		practiceDAO.deletePractice(practiceId);
		return true;
	}

	@Override
	public List<String> selectPracticeKeywordToString(int practiceId) {
		List<String> keywordList = new ArrayList<String>();
		practiceKeywordDAO.selectPracticeKeywordList(practiceId).stream().forEach(bean -> {
			keywordList.add(bean.getKeyword());
		});
		return keywordList;
	}

	@Override
	public List<String> selectPracticeTagToString(int practiceId) {
		// String category = "";
		List<String> tagList = new ArrayList<String>();
		if (practiceTagDAO.selectPracticeTagList(practiceId).size() > 0) {
			practiceTagDAO.selectPracticeTagList(practiceId).stream().forEach(bean -> {
				tagList.add(bean.getTag());
			});
		}else{
//		for(int i = 1; i <= 3; i++){
//			tagList.add("");
//		}
			tagList.add("");
			tagList.add("");
			tagList.add("");
		}

		return tagList;
	}

	@Override
	public List<UnfoldingBean> selectUnfoldingList(int practiceId) {
		List<UnfoldingBean> unfoldingList = unfoldingDAO.selectUnfoldings(practiceId);
		unfoldingList.stream().forEach(unfolding -> {
			unfolding.setFirstImage(new PracticeImageBean());
			unfolding.setSecondImage(new PracticeImageBean());
			if (practiceImageDAO.selectImage(unfolding.getUnfoldingId(), 1) != null) {
				unfolding.setFirstImage(practiceImageDAO.selectImage(unfolding.getUnfoldingId(), 1));
			}
			if (practiceImageDAO.selectImage(unfolding.getUnfoldingId(), 2) != null) {
				unfolding.setSecondImage(practiceImageDAO.selectImage(unfolding.getUnfoldingId(), 2));
			}
		});
		return unfoldingList;
	}

	@Override
	public List<UnfoldingFormBean> editUnfolding(int practiceId) {
		List<UnfoldingBean> unfoldingList = selectUnfoldingList(practiceId);
		List<UnfoldingFormBean> unfolingFormList = new ArrayList<UnfoldingFormBean>();
		unfoldingList.stream().forEach(bean -> {
			unfolingFormList.add(new UnfoldingFormBean(bean));
		});
		return unfolingFormList;
	}

	@Override
	public PracticeBean makePractice(int practiceId) {
		return practiceDAO.selectPractice(practiceId);
	}

	public PracticeRegisterBean registerPractice(int practiceId) {
		//return practiceDAO.selectPractice(practiceId);
		return null;
	}

}
