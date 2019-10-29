package com.bonzenshop.BackendService;

import com.bonzenshop.BackendService.service.DatabaseService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class BackendServiceApplication {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		SpringApplication.run(BackendServiceApplication.class, args);
		System.out.println("App running");
		DatabaseService.getConnection();
	}

}
