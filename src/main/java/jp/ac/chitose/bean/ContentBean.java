package jp.ac.chitose.bean;

import java.io.Serializable;
import java.sql.Timestamp;

import jp.ac.chitose.option.ContentType;
import lombok.Data;

@Data
public class ContentBean implements Serializable {
	private static final long serialVersionUID = -5345221182805602822L;
	private int contentId;
	private String fileName;
	private String name;
	private Timestamp inputAt;
	private int contentHierarchyId;
	private int accountId;
	private String howToUse;
	private int ordinal;
	private ContentType type;
	private boolean deleteRequest;

}
