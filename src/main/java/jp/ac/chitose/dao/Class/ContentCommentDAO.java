package jp.ac.chitose.dao.Class;

import java.sql.SQLException;

import org.sql2o.Connection;
import org.sql2o.Query;

import com.google.inject.Inject;

import jp.ac.chitose.DBCP;
import jp.ac.chitose.bean.ContentPageBean;
import jp.ac.chitose.dao.Interface.IContentCommentDAO;

public class ContentCommentDAO implements IContentCommentDAO  {

	@Inject
	DBCP dbcp;

	@Override
	public boolean registerContentComment(ContentPageBean contentBean) {
		try (Connection con = dbcp.getSql2o().beginTransaction()) {
			String insertContentKeyword = "INSERT INTO content_keyword (content_id,keyword) VALUES (:contentId,:keyword);";
			if (!contentBean.getKeyword().isEmpty()) {
				Query contentKeyword = con.createQuery(insertContentKeyword);
				contentBean.getKeyword().stream().forEach(keyword -> {
					contentKeyword.addParameter("contentId", contentBean.getContentId())
								   .addParameter("keyword", keyword).executeUpdate();
				});
			}
			con.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
