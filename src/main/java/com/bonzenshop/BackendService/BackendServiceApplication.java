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

			//create tables
			con.createStatement().execute("CREATE TABLE Images(Id int, Imgdata blob, Imgtype carchar(20), PRIMARY KEY(Id ASC))");
			con.createStatement().execute("CREATE TABLE Product(Id int, Name text, Description text, Category varchar(20), Price bigint, OnStock int, Picture int, PRIMARY KEY(Id ASC) FOREIGN KEY(Picture) REFERENCES Images(Id))");
			con.createStatement().execute("CREATE TABLE User(Id int, FirstName text, LastName text, BirthDate date, Email text, Role varchar(20), PRIMARY KEY(Id ASC))");
			con.createStatement().execute("CREATE TABLE ShoppingCartEntry(User int, Product int, Amount int, FOREIGN KEY(User) REFERENCES User(Id), FOREIGN KEY(Product) REFERENCES Product(Id))");

			//fill data user
			con.createStatement().execute("insert into User values " +
					"('1', 'Daniel', 'Wunder', '31.01.1998', 'daniel.wunder@edu.fhdw.de', 'Admin'), " +
					"('2', 'Nicolas', 'Schrade', '12.12.1998', 'nicolas.schrade@edu.fhdw.de', 'Admin'), " +
					"('3', 'Simon', 'Berendes', '17.08.1999', 'simon.berendes@edu.fhdw.de', 'Admin'), " +
					"('4', 'Adrian', 'Bayerdorffer', '27.12.1998', 'adrian.bayerdorffer@edu.fhdw.de', 'Admin')");

			//fill data product
			con.createStatement().execute("insert into Product values " +
					"('1', 'Kamel', 'Reittier', 'Tiere', '1000000', '500', ''), " +
					"('2', 'Gulfstream 650 ', 'Jet', 'Flugzeuge', '100000000', '100', ''), " +
					"('3', 'Koenigsegg Agera RS', 'Sportwagen', 'Autos', '1000000000', '10', ''), " +
					"('4', 'SY A', 'Luxusyacht', 'Yachten', '75000000', '3', ''), " +
					"('5', 'Barock Sänfte', 'Sänfte aus der Barockzeit', 'Sänfte', '302000', '12', ''), " +
					"('6', 'The Hublot', 'Luxusuhr', 'Uhren', '1200000', '10000', ''), " +
					"('7', 'Champs Elysee ', 'Straße in Paris', 'Straßen', '50000000000', '1', ''), " +
					"('8', 'Tiger', 'Ein Tiger zum spielen', 'Haustiere', '832156', '500000', ''), " +
					"('9', 'Sänftenträger', 'Eine Hilfskraft die Ihre Sänfte trägt', 'Untertanen', '27000000', '6000000', '')");
		}else{
			con = DriverManager.getConnection(JDBC_URL);
		}
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		SpringApplication.run(BackendServiceApplication.class, args);
		getConnection();
	}

}
