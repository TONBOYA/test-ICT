package jp.ac.chitose.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jp.ac.chitose.option.ContentType;
import lombok.Data;
@Data
public class ContentPageBean implements Serializable{
	private static final long serialVersionUID = 5237082428541074892L;


	private int accountId;
	private int contentId;
	private int contentHierarchyId;
	private String fileName;
	private String name;
	private List<String> keyword;
	private List<String> tag;
	private String tag1;
	private String tag2;
	private String tag3;
	private String category;
	private Timestamp inputAt;
	private String howToUse;
	private int ordinal;
	private ContentType type;
	private boolean deleteRequest;


	//新規(register)
		public ContentPageBean(int accountId,int contentHierarchyId,String category){
			contentId = 0;
			this.contentHierarchyId = contentHierarchyId;
			this.accountId = accountId;
			fileName = "";
			name = "";
			keyword = new ArrayList<String>();
			tag = new ArrayList<String>();
			tag1 = "";
			tag2 = "";
			tag3 = "";
			this.category = category;
			inputAt = Timestamp.valueOf(LocalDateTime.now());
			howToUse = "";
			ordinal = 0;
			type = ContentType.OTHER;
		}
		//編集(edit)
		public ContentPageBean(ContentBean content){
			contentId = content.getContentId();
			fileName = content.getFileName();
			name = content.getName();
			inputAt = content.getInputAt();
			contentHierarchyId = content.getContentHierarchyId();
			accountId = content.getAccountId();
			howToUse = content.getHowToUse();
			ordinal = content.getOrdinal();
			type = content.getType();
		}
		//表示(distribution)
	public ContentPageBean(ContentBean content, List<String> keyword, List<String> tag){
		contentId = content.getContentId();
		fileName = content.getFileName();
		name = content.getName();
		this.keyword = keyword;
		this.tag = tag;
		tag1 = tag.get(0);
		tag2 = tag.get(1);
		tag3 = tag.get(2);
		this.category = category;
		inputAt = content.getInputAt();
		contentHierarchyId = content.getContentHierarchyId();
		accountId = content.getAccountId();
		howToUse = content.getHowToUse();
		ordinal = content.getOrdinal();
		type = content.getType();
		deleteRequest = content.isDeleteRequest();
	}

	public ContentPageBean(){
		contentId = 0;
		fileName = "";
		name = "";
		inputAt = Timestamp.valueOf(LocalDateTime.now());
		contentHierarchyId = 0;
		accountId = 0;
		howToUse = "";
		ordinal = 0;
		type = ContentType.OTHER;
		deleteRequest = false;

	}

}
