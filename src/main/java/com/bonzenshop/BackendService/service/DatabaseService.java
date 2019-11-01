package com.bonzenshop.BackendService.service;

import com.bonzenshop.BackendService.model.Order;
import com.bonzenshop.BackendService.model.Product;
import com.bonzenshop.BackendService.model.Account;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DatabaseService {
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String JDBC_URL = "jdbc:sqlite:bonzenshopdb.sqlite";
    private static Connection con;

    public static void getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);

        //checking if database already exists
        File db = new File("bonzenshopdb.sqlite");
        if(!db.exists()){
            con = DriverManager.getConnection(JDBC_URL);
            initDB();
        }else{
            con = DriverManager.getConnection(JDBC_URL);
        }
    }

    public static List<Product> getProducts() throws SQLException {
        ResultSet resultSet = con.createStatement().executeQuery("select * from Products");
        List<Product> products = new ArrayList<Product>();
        while(resultSet.next()){
            products.add(new Product(resultSet.getInt("Id"),
                    resultSet.getString("Name"),
                    resultSet.getString("Description"),
                    resultSet.getString("Category"),
                    resultSet.getDouble("Price"),
                    resultSet.getInt("OnStock")));
        }

        return products;
    }

    public static List<Account> getAccounts() throws SQLException {
        ResultSet resultSet = con.createStatement().executeQuery("select * from Users");
        List<Account> accounts = new ArrayList<Account>();
        while(resultSet.next()){
            accounts.add(new Account(resultSet.getInt("Id"),
                    resultSet.getString("Email"),
                    resultSet.getString("FirstName"),
                    resultSet.getString("LastName"),
                    resultSet.getString("BirthDate"),
                    resultSet.getString("Role")));
        }

        return accounts;
    }

    public static Optional<Account> getAccount(String email) {
        Account acc = null;
        try{
            ResultSet resultSet = con.createStatement().executeQuery("select * from Users where Email = '"+email+"'");
            acc = new Account(resultSet.getInt("Id"),
                    resultSet.getString("Email"),
                    resultSet.getString("Password"),
                    resultSet.getString("FirstName"),
                    resultSet.getString("LastName"),
                    resultSet.getString("BirthDate"),
                    resultSet.getString("Role"));
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
        }
        return Optional.ofNullable(acc);
    }

    public static int createAccount(Account account) {
        int rowsAffected = 0;
        try{
            rowsAffected = con.createStatement().executeUpdate("INSERT INTO Users(FirstName,LastName,BirthDate,Email,Password,Role) VALUES"+
            "('"+account.getFirstName()+"','"+account.getLastName()+"','"+account.getBirthDate()+"','"+account.getEmail()+"','"+account.getPassword()+"','Kunde')");
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
        }
        return rowsAffected;
    }

    public static int createOrder(Order order) {
        int rowsAffected = 0;
        try{
            rowsAffected = con.createStatement().executeUpdate("INSERT INTO Orders(User, OrderDate, Name, Category, Price, Amount, TotalPrice) VALUES"+
                    "('"+order.getUser()+"','"+order.getOrderDate()+"','"+order.getName()+"','"+order.getCategory()+"','"+order.getPrice()+"','"+order.getAmount()+"','"+order.getTotalPrice()+"')");
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
        }
        return rowsAffected;
    }

    public static List<Order> getOrders() throws SQLException {
        ResultSet resultSet = con.createStatement().executeQuery("select * from Orders");
        List<Order> orders = new ArrayList<Order>();
        while(resultSet.next()){
            orders.add(new Order(resultSet.getInt("Id"),
                    resultSet.getInt("User"),
                    resultSet.getString("OrderDate"),
                    resultSet.getString("Name"),
                    resultSet.getString("Category"),
                    resultSet.getDouble("Price"),
                    resultSet.getInt("Amount"),
                    resultSet.getDouble("TotalPrice")));
        }

        return orders;
    }

    private static void initDB(){
        try{
            //create tables
            con.createStatement().execute("CREATE TABLE Images(Id INTEGER PRIMARY KEY, Imgdata blob, Imgtype carchar(20))");
            con.createStatement().execute("CREATE TABLE Products(Id INTEGER PRIMARY KEY, Name text, Description text, Category varchar(20), Price bigint, OnStock INTEGER, Picture INTEGER, FOREIGN KEY(Picture) REFERENCES Image(Id))");
            con.createStatement().execute("CREATE TABLE Users(Id INTEGER PRIMARY KEY, FirstName text, LastName text, BirthDate date, Email text, Password text, Role varchar(20))");
            con.createStatement().execute("CREATE TABLE Orders(Id INTEGER PRIMARY KEY, User INTEGER, OrderDate date, Name text, Category varchar(20), Price bigint,  Amount INTEGER, TotalPrice bigint, FOREIGN KEY(User) REFERENCES User(Id))");

            //fill data user
            con.createStatement().execute("INSERT INTO Users(FirstName, Lastname, BirthDate, Email, Password, Role) VALUES " +
                    "('Daniel', 'Wunder', '31.01.1998', 'daniel.wunder@edu.fhdw.de', 'adminDaniel', 'Admin'), " +
                    "('Nicolas', 'Schrade', '03.12.1998', 'nicolas.schrade@edu.fhdw.de', 'adminNicolas', 'Admin'), " +
                    "('Simon', 'Berendes', '08.07.1999', 'simon.berendes@edu.fhdw.de', 'adminSimon', 'Admin'), " +
                    "('Adrian', 'Bayerdorffer', '27.12.1998', 'adrian.bayerdorffer@edu.fhdw.de', 'adminAdrian', 'Admin'), " +
                    "('Max', 'Mustermann', '01.01.1990', 'max.mustermann@bonzenshop.com', 'max123', 'Mitarbeiter'), " +
                    "('Marie', 'Musterfrau', '02.02.1995', 'marie.musterfrau@gmail.com', 'marie123', 'Kunde')");

            //fill data product
            con.createStatement().execute("INSERT INTO Products(Name,Description,Category,Price,OnStock) VALUES " +
                    "('Kamel', 'Reittier', 'Tiere', '1000000', '500'), " +
                    "('Gulfstream 650', 'Jet', 'Flugzeuge', '100000000', '100'), " +
                    "('Koenigsegg Agera RS', 'Sportwagen', 'Autos', '1000000000', '10'), " +
                    "('SY A', 'Luxusyacht', 'Yachten', '75000000', '3'), " +
                    "('Barock Sänfte', 'Sänfte aus der Barockzeit', 'Sänfte', '302000', '12'), " +
                    "('The Hublot', 'Luxusuhr', 'Uhren', '1200000', '10000'), " +
                    "('Champs Elysee ', 'Straße in Paris', 'Straßen', '50000000000', '1'), " +
                    "('Tiger', 'Ein Tiger zum spielen', 'Haustiere', '832156', '500000'), " +
                    "('Sänftenträger', 'Eine Hilfskraft die Ihre Sänfte trägt', 'Untertanen', '27000000', '6000000')");

            //fill data order
            con.createStatement().execute("INSERT INTO Orders(User,OrderDate,Name,Category,Price,Amount,TotalPrice) VALUES " +
                    "('6', '21.10.2019', 'Kamel', 'Tiere', '1000000', '2', '2000000'), " +
                    "('6', '01.09.2019', 'The Hublot', 'Uhren', '1200000', '1', '1200000'), " +
                    "('5', '29.10.2019', 'Koenigsegg Agera RS', 'Sportwagen', '1000000000', '1', '1000000000')");
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
        }

    }
}
