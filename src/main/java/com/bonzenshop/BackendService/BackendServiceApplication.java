/**
 * @author Daniel Wunder
 * @version 1.0.
 */
package com.bonzenshop.BackendService;

import com.bonzenshop.BackendService.service.DatabaseService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

/**
 * Diese Klasse startet die Anwendung
 */
@SpringBootApplication
public class BackendServiceApplication {
	/**
	 * Die main Methode startet die Spring Anwendung und somit auch den integrierten Tomcat Server
	 * @param args -
	 * @throws ClassNotFoundException Wird geworfen, falls der {@link DatabaseService} den JDBC Driver nicht findet.
	 * @throws SQLException Wird geworfen, falls im {@link DatabaseService} keine Verbindung mit der Datenbank hergestellt werden kann.
	 */
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		SpringApplication.run(BackendServiceApplication.class, args);
		System.out.println("App running");
		DatabaseService.createConnection();
	}

}
