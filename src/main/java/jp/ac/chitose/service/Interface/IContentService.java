package jp.ac.chitose.service.Interface;

import java.util.List;

import com.google.inject.ImplementedBy;

import jp.ac.chitose.bean.ContentBean;
import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.bean.PracticePageBean;
import jp.ac.chitose.bean.TreeBean;
import jp.ac.chitose.service.Class.ContentService;

@ImplementedBy(ContentService.class)
public interface IContentService {
	//コンテンツ作成(新規)
	public boolean registerContent(ContentPageBean contentBean);
	//コンテンツの作成(編集)
	public boolean editContent(ContentPageBean practiceBean);
	//コンテンツの削除(複数)
	public boolean deleteContentList(List<TreeBean> contentList);
	//コンテンツの削除(単体)
	public boolean deleteContent(int contentId);
	//コンテンツの削除依頼(単体)
	public boolean deleteRequestContent(int contentId);
	//コンテンツの選択(単体)
	public ContentPageBean selectContent(int contentId);
	//コンテンツの実践(紐付)
	public List<PracticePageBean> selectPressPractice(int contentId);
	//コンテンツの検索(キーワード)
	public List<ContentPageBean> searchContent(String keyword);

	public List<String> selectAllTagList(String category);

	public List<ContentPageBean> keywordSearchOfContent(List<String> keywords);

	public List<ContentPageBean> tagSearchOfContent(List<String> tags);

	//困ったときに使えるパーツ
	public ContentBean content(int contentId);

}
