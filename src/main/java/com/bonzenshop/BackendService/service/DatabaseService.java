package com.bonzenshop.BackendService.service;

import com.bonzenshop.BackendService.model.*;

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
        }else{
            con = DriverManager.getConnection(JDBC_URL);
        }
    }

    public static Optional<List<Product>> getProducts() {
        try{
            List<Product> products = new ArrayList<Product>();
            ResultSet resultSet = con.createStatement().executeQuery("select * from Products");
            while(resultSet.next()){
                products.add(new Product(resultSet.getInt("Id"),
                        resultSet.getString("Name"),
                        resultSet.getString("Description"),
                        resultSet.getString("Category"),
                        resultSet.getDouble("Price"),
                        resultSet.getInt("Image"),
                        resultSet.getInt("OnStock")));
            }
            return Optional.ofNullable(products);
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
            return Optional.empty();
        }
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

    public static Optional<Account> getAccountByEmail(String email) {
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

    public static Optional<Account> getAccountById(int id) {
        Account acc = null;
        try{
            ResultSet resultSet = con.createStatement().executeQuery("select * from Users where Id = '"+id+"'");
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
            PreparedStatement statement = con.prepareStatement("INSERT INTO Users(FirstName,LastName,BirthDate,Email,Password,Role) VALUES(?,?,?,?,?,?)");
            statement.setString(1, account.getFirstName());
            statement.setString(2, account.getLastName());
            statement.setString(3, account.getBirthDate());
            statement.setString(4, account.getEmail());
            statement.setString(5, account.getPassword());
            statement.setString(6, account.getRole());
            rowsAffected = statement.executeUpdate();
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
        }
        return rowsAffected;
    }

    public static int createOrder(List<Order> orderList) {
        int rowsAffected = 0;
        try{
            String sqlString = "INSERT INTO Orders(User, OrderDate, Name, Category, Price, Amount, TotalPrice, Image) VALUES(?,?,?,?,?,?,?,?)";
            for(int i = 1; i < orderList.size(); i++){
                sqlString += ",(?,?,?,?,?,?,?,?)";
            }
            PreparedStatement statement = con.prepareStatement(sqlString);
            for(int i = 0; i < orderList.size(); i++){
                System.out.println(i);
                Order order = orderList.get(i);
                System.out.println(order.getName());
                statement.setInt(i*8+1, order.getUser());
                statement.setString(i*8+2, order.getOrderDate());
                statement.setString(i*8+3, order.getName());
                statement.setString(i*8+4, order.getCategory());
                statement.setDouble(i*8+5, order.getPrice());
                statement.setInt(i*8+6, order.getAmount());
                statement.setDouble(i*8+7, order.getTotalPrice());
                statement.setDouble(i*8+8, order.getImage());
            }
            rowsAffected = statement.executeUpdate();
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
        }
        return rowsAffected;
    }

    public static Optional<List<Order>> getOrders() {
        try{
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
                        resultSet.getDouble("TotalPrice"),
                        resultSet.getInt("Image")));
            }
            return Optional.ofNullable(orders);
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
            return Optional.empty();
        }
    }

    public static Optional<List<Order>> getOrders(String email) {
        try{
            ResultSet resultSet = con.createStatement().executeQuery("SELECT * FROM Orders WHERE User = (SELECT Id FROM Users WHERE Email = '"+email+"')");
            List<Order> orders = new ArrayList<Order>();
            while(resultSet.next()){
                orders.add(new Order(resultSet.getInt("Id"),
                        resultSet.getInt("User"),
                        resultSet.getString("OrderDate"),
                        resultSet.getString("Name"),
                        resultSet.getString("Category"),
                        resultSet.getDouble("Price"),
                        resultSet.getInt("Amount"),
                        resultSet.getDouble("TotalPrice"),
                        resultSet.getInt("Image")));
            }
            return Optional.ofNullable(orders);
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
            return Optional.empty();
        }
    }

    public static int addProduct(SaveProductRequest request) {
        Product product = request.getProduct();
        Image image = request.getImage();
        int rowsAffected = 0;
        try{
            PreparedStatement statement = con.prepareStatement("INSERT INTO Images(ImgData, ImgType) VALUES(?,?)");
            statement.setString(1, image.getImgData());
            statement.setString(2, image.getImgType());
            statement.executeUpdate();

            statement = con.prepareStatement("INSERT INTO Products(Name, Description, Category, Price, OnStock, Image) VALUES(?,?,?,?,?,last_insert_rowid())");
            statement.setString(1, product.getName());
            statement.setString(2, product.getDesc());
            statement.setString(3, product.getCategory());
            statement.setDouble(4, product.getPrice());
            statement.setDouble(5, product.getOnStock());
            rowsAffected = statement.executeUpdate();
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
        }
        return rowsAffected;
    }

    public static int updateProduct(SaveProductRequest request) {
        Product product = request.getProduct();
        Image image = request.getImage();
        int rowsAffected = 0;
        System.out.println(image.getId());
        System.out.println(product.getId());
        System.out.println(product.getName());
        try{
            if(image.getId() == 0){
                PreparedStatement statement = con.prepareStatement("INSERT INTO Images(ImgData, ImgType) VALUES(?,?)");
                statement.setString(1, image.getImgData());
                statement.setString(2, image.getImgType());
                statement.executeUpdate();

                statement = con.prepareStatement("UPDATE Products SET Name = ?, Description = ?, Category = ?, Price = ?, OnStock = ?, Image = last_insert_rowid() WHERE Id = ?");
                statement.setString(1, product.getName());
                statement.setString(2, product.getDesc());
                statement.setString(3, product.getCategory());
                statement.setDouble(4, product.getPrice());
                statement.setDouble(5, product.getOnStock());
                statement.setInt(6, product.getId());
                rowsAffected = statement.executeUpdate();
            }else{
                PreparedStatement statement = con.prepareStatement("UPDATE Products SET Name = ?, Description = ?, Category = ?, Price = ?, OnStock = ? WHERE Id = ?");
                statement.setString(1, product.getName());
                statement.setString(2, product.getDesc());
                statement.setString(3, product.getCategory());
                statement.setDouble(4, product.getPrice());
                statement.setDouble(5, product.getOnStock());
                statement.setInt(6, product.getId());
                rowsAffected = statement.executeUpdate();
            }
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
        }
        return rowsAffected;
    }

    public static int updateAccount(Account account) {
        int rowsAffected = 0;
        try{
            PreparedStatement statement;
            if(account.getPassword() != null && !account.getPassword().trim().isEmpty()){
                statement = con.prepareStatement("UPDATE Users SET FirstName = ?, LastName = ?, Email = ?, BirthDate = ?, Password = ? WHERE Id = ?");
                statement.setString(5, account.getPassword());
                statement.setInt(6, account.getId());
            }else{
                statement = con.prepareStatement("UPDATE Users SET FirstName = ?, LastName = ?, Email = ?, BirthDate = ? WHERE Id = ?");;
                statement.setInt(5, account.getId());
            }
            statement.setString(1, account.getFirstName());
            statement.setString(2, account.getLastName());
            statement.setString(3, account.getEmail());
            statement.setString(4, account.getBirthDate());

            rowsAffected = statement.executeUpdate();
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
        }
        return rowsAffected;
    }

    public static int changeRole(ChangeRoleRequest data) {
        int rowsAffected = 0;
        try{
            PreparedStatement statement = con.prepareStatement("UPDATE Users SET Role = ? WHERE Id = ?");
            statement.setString(1, data.getRole());
            statement.setInt(2, data.getUser());
            rowsAffected = statement.executeUpdate();
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
        }
        return rowsAffected;
    }

    public static int resetPassword(int userId) {
        int rowsAffected = 0;
        try{
            PreparedStatement statement = con.prepareStatement("UPDATE Users SET Password = 'ichbinreich' WHERE Id = ?");
            statement.setInt(1, userId);
            rowsAffected = statement.executeUpdate();
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
        }
        return rowsAffected;
    }

    public static int deleteProduct(int productId) {
        int rowsAffected = 0;
        try{
            PreparedStatement statement = con.prepareStatement("DELETE FROM Products WHERE Id = ?");
            statement.setInt(1, productId);
            rowsAffected = statement.executeUpdate();
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
        }
        return rowsAffected;
    }

    public static Optional<List<Image>> getImages() {
        try{
            List<Image> images = new ArrayList<Image>();
            ResultSet resultSet = con.createStatement().executeQuery("select * from Images");
            while(resultSet.next()){
                images.add(new Image(resultSet.getInt("Id"),
                        resultSet.getString("ImgData"),
                        resultSet.getString("ImgType")));
            }
            return Optional.ofNullable(images);
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
            return Optional.empty();
        }
    }

    public static Optional<MainInfos> getMainInfos() {
        try{
            MainInfos mainInfos = new MainInfos();
            List<TopCustomer> topCustomerList = new ArrayList<TopCustomer>();
            ResultSet bestsellerResult = con.createStatement().executeQuery("SELECT Id FROM Products WHERE Name = (SELECT Name FROM Orders GROUP BY Name ORDER BY SUM(Amount) DESC, SUM(TotalPrice) DESC LIMIT 1)");
            ResultSet topCustomerResult = con.createStatement().executeQuery("SELECT (SELECT FirstName FROM Users WHERE Id = User) AS FirstName, (SELECT LastName FROM Users WHERE Id = User) AS LastName, SUM(TotalPrice) AS TotalPurchase FROM Orders GROUP BY User ORDER BY SUM(TotalPrice) DESC LIMIT 3");
            while(bestsellerResult.next()){
                mainInfos.setBestsellerId(bestsellerResult.getInt("Id"));
            }
            while(topCustomerResult.next()){
                topCustomerList.add(new TopCustomer(topCustomerResult.getRow(),
                        topCustomerResult.getString("FirstName"),
                        topCustomerResult.getString("LastName"),
                        topCustomerResult.getDouble("TotalPurchase")));
            }
            mainInfos.setTopCustomerList(topCustomerList);
            return Optional.ofNullable(mainInfos);
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
            return Optional.empty();
        }
    }

    public static boolean emailAlreadyTaken(String email, int exceptOfId) {
        try{
            PreparedStatement statement = con.prepareStatement("SELECT Id FROM Users WHERE Email = ? AND Id != ?");;
            statement.setString(1, email);
            statement.setInt(1, exceptOfId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
            return true;
        }
    }
}
