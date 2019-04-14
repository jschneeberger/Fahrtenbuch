package de.thd.pms;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is not used yet.
 * The purpose is to create an empty database and to demonstrate spring bean programming.
 * If possible, this class will be reactivated in the near future.
 * @author jschneeberger
 *
 */
public class Startup {
	private static Logger log = LogManager.getLogger(Startup.class);
	private String driverClass;
	private String user;
	private String password;
	private String defaultDBurl;
	private String newDB;

	public void initDB() {
		// attempt to create the user specified database
		try {
			log.info("Try to create database " + newDB);
			Class.forName(driverClass);
			Connection db = DriverManager.getConnection(defaultDBurl, user, password);
			Statement st = db.createStatement();
			// create the user specified database. Assigned ownership to
			// user specified super user
			String command = "CREATE DATABASE " + newDB 
					+ " default charset utf8"
				    + " COLLATE utf8_general_ci;"
					;
			log.info(command);
			st.execute(command);
			st.close();
			db.close();
			log.info("Creating database " + newDB + " successful!");
		} catch (Exception e) {
			log.info("Create database " + newDB + " failed - it probably already exists.");
		}
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setDefaultDBurl(String defaultDBurl) {
		this.defaultDBurl = defaultDBurl;
	}

	public void setNewDB(String newDB) {
		this.newDB = newDB;
	}
}
