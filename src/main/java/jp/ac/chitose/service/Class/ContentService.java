package jp.ac.chitose.service.Class;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Inject;

import jp.ac.chitose.bean.ContentBean;
import jp.ac.chitose.bean.ContentKeywordBean;
import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.bean.ContentTagBean;
import jp.ac.chitose.bean.PracticeContentBean;
import jp.ac.chitose.bean.PracticePageBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.dao.Interface.IContentDAO;
import jp.ac.chitose.dao.Interface.IContentKeywordDAO;
import jp.ac.chitose.dao.Interface.IContentTagDAO;
import jp.ac.chitose.dao.Interface.IPracticeContentDAO;
import jp.ac.chitose.dao.Interface.IPracticeImageDAO;
import jp.ac.chitose.service.Interface.IContentService;
import jp.ac.chitose.service.Interface.IPracticeService;

public class ContentService implements IContentService {

	@Inject
	IContentDAO contentDAO;

	@Inject
	IPracticeService practiceServise;

	@Inject
	IPracticeContentDAO pContentDAO;

	@Inject
	IContentKeywordDAO contentKeywordDAO;

	@Inject
	IContentTagDAO contentTagDAO;

	@Inject
	IPracticeImageDAO practiceImageDAO;

	int ITEM_SIZE = 0;
	int PRACTICE_ITEM_ID;
	int LOOP_SIZE = 0;

	@Override
	public boolean registerContent(ContentPageBean contentBean) {
		contentDAO.registerContent(contentBean);
		contentKeywordDAO.registerContentKeyword(contentBean);
		contentTagDAO.registerContentTag(contentBean);

		return true;
	}

	@Override
	public boolean editContent(ContentPageBean contentBean) {
		contentDAO.editContent(contentBean);
		contentKeywordDAO.deleteContentKeyword(contentBean.getContentId());
		contentKeywordDAO.registerContentKeyword(contentBean);
		contentTagDAO.deleteContentTag(contentBean.getContentId());
		contentTagDAO.registerContentTag(contentBean);

		return true;
	}

	@Override
	public boolean deleteContentList(List<TreeBean> contentList) {
		contentList.stream().forEach(content -> {
			deleteContent(content.getId());
		});
		return true;
	}

	@Override
	public boolean deleteContent(int contentId) {
		contentKeywordDAO.deleteContentKeyword(contentId);
		contentTagDAO.deleteContentTag(contentId);
		contentDAO.deleteContent(contentId);
		return true;
	}

	public boolean deleteRequestContent(int contentId) {
		contentDAO.deleteRequestContent(contentId);
		return true;
	}

	@Override
	public ContentPageBean selectContent(int contentId) {
		List<ContentKeywordBean> keywordBeans = contentKeywordDAO.selectContentKeywordList(contentId);
		List<ContentTagBean> tagBeans = contentTagDAO.selectContentTagList(contentId);

		System.out.println(tagBeans);
		List<String> stringKeyword = new ArrayList<String>();
		List<String> stringTag = new ArrayList<String>();
		keywordBeans.stream().forEach(keyBean -> {
			stringKeyword.add(keyBean.getKeyword());
		});
		if (tagBeans.size() > 0) {
			System.out.println(tagBeans.get(0));
			tagBeans.forEach(tagBean -> {
				stringTag.add(tagBean.getTag());
			});
		} else {
			System.out.println("NULL");
			stringTag.add("");
			stringTag.add("");
			stringTag.add("");
			System.out.println(stringTag.get(0));
		}

		return new ContentPageBean(contentDAO.selectContent(contentId), stringKeyword, stringTag);
	}

	@Override
	public List<PracticePageBean> selectPressPractice(int contentId) {
		List<PracticePageBean> practice = new ArrayList<PracticePageBean>();
		List<PracticeContentBean> prcoBean = pContentDAO.selectPressPractice(contentId);
		prcoBean.stream().forEach(action -> {
			practice.add(practiceServise.selectPractice(action.getPracticeId()));
		});
		return practice;

	}

	@Override
	public List<String> selectAllTagList(String category) {

		List<ContentTagBean> tagBeans = contentTagDAO.selectCategoryTagList(category);
		List<String> tagList = new ArrayList<String>();// return

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
	public List<ContentPageBean> keywordSearchOfContent(List<String> keywords) {
		List<ContentKeywordBean> keyBeans = contentKeywordDAO.selectAllKeywordList();
		List<Integer> selectedKeywordsId = new ArrayList<Integer>();
		List<ContentPageBean> contentBeans = new ArrayList<ContentPageBean>();// return

		keywords.stream().forEach(keyword -> {
			LOOP_SIZE++;
			System.out.println(keyword);
			System.out.println();
			for (int REMOVE_SIZE = 1; REMOVE_SIZE <= keyBeans.size(); REMOVE_SIZE++) {
				// keyword または practiceId で(keyBeans.size() - 1)される
				// LOOP_SIZE <= keyBeans.size() まで判定を続ける
				if (keyBeans.get(REMOVE_SIZE - 1).getKeyword().equals(keyword)) {
					PRACTICE_ITEM_ID = keyBeans.get(REMOVE_SIZE - 1).getContentId();
					selectedKeywordsId.add(PRACTICE_ITEM_ID);
					System.out.println("REMOVE_SIZE: " + (REMOVE_SIZE - 1) + " getKeyword good "
							+ keyBeans.remove(REMOVE_SIZE - 1) + " " + keyword);
					System.out.println("remove:" + keyBeans.size() + "count: " + ITEM_SIZE++);
					System.out.println();
					REMOVE_SIZE--;
				} else if (keyBeans.get(REMOVE_SIZE - 1).getContentId() == PRACTICE_ITEM_ID) {
					System.out.println("REMOVE_SIZE: " + (REMOVE_SIZE - 1) + " getKeyId good "
							+ keyBeans.remove(REMOVE_SIZE - 1) + " " + PRACTICE_ITEM_ID);
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
			contentBeans.add(contentDAO.searchContent(selectedKeywordId));
		});
		return contentBeans;
	}

	@Override
	public List<ContentPageBean> tagSearchOfContent(List<String> tags) {

		List<ContentTagBean> tagBeans = contentTagDAO.selectAllTagList();
		List<Integer> selectedTagsId = new ArrayList<Integer>();
		List<ContentPageBean> contentBeans = new ArrayList<ContentPageBean>();// return

		tags.stream().forEach(tag -> {
			LOOP_SIZE++;
			System.out.println(tag);
			System.out.println();
			for (int REMOVE_SIZE = 1; REMOVE_SIZE <= tagBeans.size(); REMOVE_SIZE++) {
				// keyword または practiceId で(keyBeans.size() - 1)される
				// LOOP_SIZE <= keyBeans.size() まで判定を続ける
				if (tagBeans.get(REMOVE_SIZE - 1).getTag().equals(tag)) {
					PRACTICE_ITEM_ID = tagBeans.get(REMOVE_SIZE - 1).getContentId();
					selectedTagsId.add(PRACTICE_ITEM_ID);
					System.out.println("REMOVE_SIZE: " + (REMOVE_SIZE - 1) + " getKeyword good "
							+ tagBeans.remove(REMOVE_SIZE - 1) + " " + tag);
					System.out.println("remove:" + tagBeans.size() + "count: " + ITEM_SIZE++);
					System.out.println();
					REMOVE_SIZE--;
				} else if (tagBeans.get(REMOVE_SIZE - 1).getContentId() == PRACTICE_ITEM_ID) {
					System.out.println("REMOVE_SIZE: " + (REMOVE_SIZE - 1) + " getKeyId good "
							+ tagBeans.remove(REMOVE_SIZE - 1) + " " + PRACTICE_ITEM_ID);
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
			contentBeans.add(contentDAO.searchContent(selectedTagId));
		});

		return contentBeans;

	}

	@Override
	public List<ContentPageBean> searchContent(String keyword) {
		List<ContentKeywordBean> keywords = contentKeywordDAO.searchContent(keyword);
		// List<ContentTagBean> tags = contentTagDAO.searchContent(tag);
		List<ContentPageBean> content = new ArrayList<ContentPageBean>();

		keywords.stream().forEach(bean -> {
			content.add(contentDAO.searchContent(bean.getContentId()));
		});
		// tags.stream().forEach(bean ->{
		// content.add(contentDAO.searchContent(bean.getContentId()));
		// });

		return content;
	}

	@Override
	public ContentBean content(int contentId) {
		return contentDAO.selectContent(contentId);
	}

}
