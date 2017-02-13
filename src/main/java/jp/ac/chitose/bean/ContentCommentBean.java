package jp.ac.chitose.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.Data;

@Data
public class ContentCommentBean  implements Serializable {

	private int contentId;
	private int accountId;
	private String contentComment;
}
