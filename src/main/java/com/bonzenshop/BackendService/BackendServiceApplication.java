package com.bonzenshop.BackendService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class BackendServiceApplication {

    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String JDBC_URL = "jdbc:sqlite:bonzenshopdb.sqlite";
	private static Connection con;

	private static void getConnection() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER);

		//checking if database already exists
		File db = new File("bonzenshopdb.sqlite");
		if(!db.exists()){
			con = DriverManager.getConnection(JDBC_URL);
			//TODO: create tables and fill with data
			con.createStatement().execute("CREATE TABLE test(id varchar(20), name varchar(20))");
			con.createStatement().execute("insert into test values ('id', 'test'),('id2', 'test2')");
		}else{
			con = DriverManager.getConnection(JDBC_URL);
		}
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		SpringApplication.run(BackendServiceApplication.class, args);
		getConnection();
	}

}
