package com.bonzenshop.BackendService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class BackendServiceApplication {

	private static Connection con;

	private void getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:bonzenshopdb.sqlite");
		//initialise();
	}

	private void intialise(){

	}

	public static void main(String[] args) {
		SpringApplication.run(BackendServiceApplication.class, args);
	}

}
