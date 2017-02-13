package jp.ac.chitose.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import jp.ac.chitose.option.ContentType;
import lombok.Data;
@Data
public class ContentRegisterBean implements Serializable {
	private static final long serialVersionUID = -5854205786819258818L;

	//使っていないBean

	private int contentId;
	private int accountId;
	private int contentHierarchyId;
	private String name;
	private String fileName;
	private Timestamp inputAt;
	private List<String> keyword;
	private List<String> tag;
	private String tag1;
	private String tag2;
	private String tag3;
	private String category;

	private String howToUse;
	private int ordinal;
	private ContentType type;

	private List<ContentImageBean> contentImage;



//	//新規(register)
//	public RegisterContentBean(int accountId,int contentHierarchyId){
//		contentId = 0;
//		this.contentHierarchyId = contentHierarchyId;
//		this.accountId = accountId;
//		fileName = "";
//		name = "";
//		inputAt = Timestamp.valueOf(LocalDateTime.now());
//		howToUse = "";
//		ordinal = 0;
//		type = ContentType.OTHER;
//
////		keyword = new ArrayList<String>();
//
////		contentImage = new ArrayList<ContentImageBean>();
//	}
//	//編集用(edit)
//	public RegisterContentBean(ContentBean content){
//		contentId = content.getContentId();
//		fileName = content.getFileName();
//		name = content.getName();
//		inputAt = content.getInputAt();
//		contentHierarchyId = content.getContentHierarchyId();
//		accountId = content.getAccountId();
//		howToUse = content.getHowToUse();
//		ordinal = content.getOrdinal();
//		type = content.getType();
//	}
//
//	public RegisterContentBean(){
//		contentId = 0;
//		fileName = "";
//		name = "";
//		inputAt = Timestamp.valueOf(LocalDateTime.now());
//		contentHierarchyId = 0;
//		accountId = 0;
//		howToUse = "";
//		ordinal = 0;
//		type = ContentType.OTHER;
//
//		keyword = new ArrayList<String>();
//
//		contentImage = new ArrayList<ContentImageBean>();
//	}
//
//	public RegisterContentBean(ContentBean content,List<ContentImageBean> contentImage,List<String> keyword){
//		contentId = content.getContentId();
//		fileName = content.getFileName();
//		name = content.getName();
//		inputAt = content.getInputAt();
//		contentHierarchyId = content.getContentHierarchyId();
//		accountId = content.getAccountId();
//		howToUse = content.getHowToUse();
//		ordinal = content.getOrdinal();
//		type = content.getType();
//
////		this.keyword = keyword;
////
////		this.contentImage = contentImage;
//	}
}
