package jp.ac.chitose;

import java.sql.SQLException;

import lombok.val;

import org.sql2o.Sql2o;
import org.sql2o.quirks.PostgresQuirks;

import com.google.inject.Singleton;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
//dbcpはシステムの中にゆいつ一つだけにする
@Singleton
public class DBCP implements IDBCP {
	private final Sql2o sql2o;

	public DBCP() {
		val url = getClass().getClassLoader().getResource("hikari.properties");
		sql2o = new Sql2o(new HikariDataSource(new HikariConfig(url.getFile())), new PostgresQuirks());
	}


	@Override
	public Sql2o getSql2o() throws SQLException {
		return sql2o;
	}

}