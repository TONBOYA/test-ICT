package jp.ac.chitose;

import java.sql.SQLException;

import org.sql2o.Sql2o;

import com.google.inject.ImplementedBy;

/**
 * DB接続用のSql2oオブジェクトを提供する
 * 
 * 
 * @author kazuki
 *
 */
@ImplementedBy(DBCP.class)
public interface IDBCP {

	public Sql2o getSql2o() throws SQLException;
}
