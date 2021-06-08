package thyme.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class JdbcDao {

	private static final Logger log = LogManager.getLogger(JdbcDao.class);

	public Connection getConnection() throws ClassNotFoundException {
		Class.forName("org.postgresql.Driver");
		log.trace("Entered getConnection() method");
		Connection connection = null;
		try {
			log.trace("Getting connection");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbunit", "postgres", "password");
			log.debug("Created " + connection);
		} catch (SQLException e) {
			log.error("Cannot create connection", e);
			throw new TestJdbcException("Cannot create connection");
		}
		return connection;
	}
}