package com.bonzenshop.BackendService.service;

import com.bonzenshop.BackendService.model.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Der DatabaseService bildet die Schnittstelle zur Datenbank und liefert alle notwendigen Methoden, um Daten zu lesen oder zu manipulieren.
 */
@Service
public class DatabaseService {
    /**
     * Beschreibt, welcher Driver verwendet werden soll.
     */
    private static final String DRIVER = "org.sqlite.JDBC";
    /**
     * Beschreib, wo die Datenbank-Datei zu finden ist.
     */
    private static final String JDBC_URL = "jdbc:sqlite:bonzenshopdb.sqlite";
    /**
     * Beinhaltet die Verbindung zu der Datenbank, welche in bei allen Datenbankzugriffen verwendet wird.
     */
    private static Connection con;

    /**
     * Erstellt die initiale Verbindung zur Datenbank und speichert die Verbindung in der Variable "con"
     * @throws ClassNotFoundException Wird geworfen, falls der JDBC Driver nicht gefunden werden konnte.
     * @throws SQLException Wird geworfen, falls keine Verbindung mit der Datenbank hergestellt werden konnte.
     */
    public static void createConnection() throws ClassNotFoundException, SQLException {
        Class.forName(DRIVER);

        //checking if database already exists
        File db = new File("bonzenshopdb.sqlite");
        if(!db.exists()){
            con = DriverManager.getConnection(JDBC_URL);
        }else{
            con = DriverManager.getConnection(JDBC_URL);
        }
    }

    /**
     * Lädt alle Produkte aus der Datenbank.
     * @return Eine Liste von Produkte in einem Optional, falls der Datenbankaufruf erfolgreich war. Ansonsten ein leeres Optional.
     */
    public static Optional<List<Product>> getProducts() {
        try{
            List<Product> products = new ArrayList<Product>();
            ResultSet resultSet = con.createStatement().executeQuery("select * from Product");
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

    /**
     * Lädt alle Benutzerkonten aus der Datenbank.
     * @return Eine Liste von Benutzerkonten in einem Optional, falls der Datenbankaufruf erfolgreich war. Ansonsten ein leeres Optional.
     */
    public static Optional<List<Account>> getAccounts() {
        try {
            ResultSet resultSet = con.createStatement().executeQuery("select * from Account");
            List<Account> accounts = new ArrayList<Account>();
            while (resultSet.next()) {
                accounts.add(new Account(resultSet.getInt("Id"),
                        resultSet.getString("Email"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName"),
                        resultSet.getString("BirthDate"),
                        resultSet.getString("Role"),
                        resultSet.getString("Country"),
                        resultSet.getString("City"),
                        resultSet.getString("PostalCode"),
                        resultSet.getString("Street")));
            }
            return Optional.ofNullable(accounts);
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Lädt das der Email zugehörige Benutzerkonto.
     * @param email Die E-Mail des gewünschten Benutzerkontos
     * @return Ein Benutzerkonto in einem Optional, falls der Datenbankaufruf erfolgreich war. Ansonste ein leeres Optional.
     */
    public static Optional<Account> getAccountByEmail(String email) {
        Account acc = null;
        try{
            ResultSet resultSet = con.createStatement().executeQuery("select * from Account where Email = '"+email+"'");
            acc = new Account(resultSet.getInt("Id"),
                    resultSet.getString("Email"),
                    resultSet.getString("Password"),
                    resultSet.getString("FirstName"),
                    resultSet.getString("LastName"),
                    resultSet.getString("BirthDate"),
                    resultSet.getString("Role"),
                    resultSet.getString("Country"),
                    resultSet.getString("City"),
                    resultSet.getString("PostalCode"),
                    resultSet.getString("Street"));
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
        }
        return Optional.ofNullable(acc);
    }

    /**
     * Lädt das der ID zugehörige Benutzerkonto.
     * @param id Die ID des gewünschten Benutzerkontos
     * @return Ein Benutzerkonto in einem Optional, falls der Datenbankaufruf erfolgreich war. Ansonste ein leeres Optional.
     */
    public static Optional<Account> getAccountById(int id) {
        Account acc = null;
        try{
            ResultSet resultSet = con.createStatement().executeQuery("select * from Account where Id = '"+id+"'");
            acc = new Account(resultSet.getInt("Id"),
                    resultSet.getString("Email"),
                    resultSet.getString("Password"),
                    resultSet.getString("FirstName"),
                    resultSet.getString("LastName"),
                    resultSet.getString("BirthDate"),
                    resultSet.getString("Role"),
                    resultSet.getString("Country"),
                    resultSet.getString("City"),
                    resultSet.getString("PostalCode"),
                    resultSet.getString("Street"));
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
        }
        return Optional.ofNullable(acc);
    }

    /**
     * Erstellen eines neuen Benutzerkontos.
     * @param account Das Account, welches erstellt werden soll.
     * @return Die Anzahl der veränderten Zeilen in der Datenbank.
     */
    public static int createAccount(Account account) {
        int rowsAffected = 0;
        try{
            PreparedStatement statement = con.prepareStatement("INSERT INTO Account(FirstName,LastName,BirthDate,Email,Password,Role,Country,City,PostalCode,Street) VALUES(?,?,?,?,?,?,?,?,?,?)");
            statement.setString(1, account.getFirstName());
            statement.setString(2, account.getLastName());
            statement.setString(3, account.getBirthDate());
            statement.setString(4, account.getEmail());
            statement.setString(5, account.getPassword());
            statement.setString(6, account.getRole());
            statement.setString(7, account.getCountry());
            statement.setString(8, account.getCity());
            statement.setString(9, account.getPostalCode());
            statement.setString(10, account.getStreet());
            rowsAffected = statement.executeUpdate();
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
        }
        return rowsAffected;
    }

    /**
     * Erstellen einer oder mehrerer neuer Bestellungen.
     * @param orderList Eine Liste von Bestellung, welche erstellt werden sollen.
     * @return Die Anzahl der veränderten Zeilen in der Datenbank.
     */
    public static int createOrder(List<Order> orderList) {
        int rowsAffected = 0;
        try{
            String sqlString = "INSERT INTO OrderItem(User, OrderDate, Name, Category, Price, Amount, TotalPrice, Image) VALUES(?,?,?,?,?,?,?,?)";
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
                statement.setDouble(i*8+7, order.getAmount()*order.getPrice());
                statement.setDouble(i*8+8, order.getImage());
            }
            rowsAffected = statement.executeUpdate();
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
        }
        return rowsAffected;
    }

    /**
     * Lädt alle Bestellungen.
     * @return Eine Liste von Bestellungen in einem Optional, falls der Datenbankaufruf erfolgreich war. Ansonsten ein leeres Optional.
     */
    public static Optional<List<Order>> getOrders() {
        try{
            ResultSet resultSet = con.createStatement().executeQuery("select * from OrderItem");
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

    /**
     * Lädt alle Bestellungen eines gewünschten Benutzers.
     * @param email Die E-Mail des Benutzers, dessen Bestellungen geladen werden sollen.
     * @return Eine Liste von Bestellungen in einem Optional, falls der Datenbankaufruf erfolgreich war. Ansonsten ein leeres Optional.
     */
    public static Optional<List<Order>> getOrders(String email) {
        try{
            ResultSet resultSet = con.createStatement().executeQuery("SELECT * FROM OrderItem WHERE User = (SELECT Id FROM Account WHERE Email = '"+email+"')");
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

    /**
     * Erstellen eines neuen Produktes inklusive Bild.
     * @param request Beinhaltet das Produkt welches gespeichert werden soll inklusive Bild.
     * @return Die Anzahl der veränderten Zeilen in der Datenbank.
     */
    public static int addProduct(SaveProductRequest request) {
        Product product = request.getProduct();
        Image image = request.getImage();
        int rowsAffected = 0;
        try{
            PreparedStatement statement = con.prepareStatement("INSERT INTO )Image(ImgData, ImgType) VALUES(?,?)");
            statement.setString(1, image.getImgData());
            statement.setString(2, image.getImgType());
            statement.executeUpdate();

            statement = con.prepareStatement("INSERT INTO Product(Name, Description, Category, Price, OnStock, Image) VALUES(?,?,?,?,?,last_insert_rowid())");
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

    /**
     * Bearbeiten eines bestehenden Produktes.
     * @param request Beinhaltet das Produkt mit den neuen Daten und das dazugehörige Bild. Soll ein neues Bild hinzugefügt werden, so muss die ID des Bildes auf 0 gesetzt werden.
     * @return Die Anzahl der veränderten Zeilen in der Datenbank.
     */
    public static int updateProduct(SaveProductRequest request) {
        Product product = request.getProduct();
        Image image = request.getImage();
        int rowsAffected = 0;
        System.out.println(image.getId());
        System.out.println(product.getId());
        System.out.println(product.getName());
        try{
            if(image != null && image.getId() == 0){
                PreparedStatement statement = con.prepareStatement("INSERT INTO )Image(ImgData, ImgType) VALUES(?,?)");
                statement.setString(1, image.getImgData());
                statement.setString(2, image.getImgType());
                statement.executeUpdate();

                statement = con.prepareStatement("UPDATE Product SET Name = ?, Description = ?, Category = ?, Price = ?, OnStock = ?, Image = last_insert_rowid() WHERE Id = ?");
                statement.setString(1, product.getName());
                statement.setString(2, product.getDesc());
                statement.setString(3, product.getCategory());
                statement.setDouble(4, product.getPrice());
                statement.setDouble(5, product.getOnStock());
                statement.setInt(6, product.getId());
                rowsAffected = statement.executeUpdate();
            }else{
                PreparedStatement statement = con.prepareStatement("UPDATE Product SET Name = ?, Description = ?, Category = ?, Price = ?, OnStock = ? WHERE Id = ?");
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

    /**
     * Bearbeiten eines bestehenden Benutzerkontos.
     * @param account Das Benutzerkonto mit den neuen Daten. Ist das Passwort null oder leer, so wird es ignoriert.
     * @return Die Anzahl der veränderten Zeilen in der Datenbank.
     */
    public static int updateAccount(Account account) {
        int rowsAffected = 0;
        try{
            PreparedStatement statement;
            if(account.getPassword() != null && !account.getPassword().trim().isEmpty()){
                statement = con.prepareStatement("UPDATE Account SET FirstName = ?, LastName = ?, Email = ?, BirthDate = ?, Country = ?, City = ?, PostalCode = ?, Street = ?, Password = ? WHERE Id = ?");
                statement.setString(9, account.getPassword());
                statement.setInt(10, account.getId());
            }else{
                statement = con.prepareStatement("UPDATE Account SET FirstName = ?, LastName = ?, Email = ?, BirthDate = ?, Country = ?, City = ?, PostalCode = ?, Street = ? WHERE Id = ?");;
                statement.setInt(9, account.getId());
            }
            statement.setString(1, account.getFirstName());
            statement.setString(2, account.getLastName());
            statement.setString(3, account.getEmail());
            statement.setString(4, account.getBirthDate());
            statement.setString(5, account.getCountry());
            statement.setString(6, account.getCity());
            statement.setString(7, account.getPostalCode());
            statement.setString(8, account.getStreet());

            rowsAffected = statement.executeUpdate();
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
        }
        return rowsAffected;
    }

    /**
     * Ändern der Rolle eines Benutzerkontos.
     * @param data Beinhaltet die ID des Benutzer, dessen Rolle geändert werden soll und die neu zu vergebende Rolle.
     * @return Die Anzahl der veränderten Zeilen in der Datenbank.
     */
    public static int changeRole(ChangeRoleRequest data) {
        int rowsAffected = 0;
        try{
            PreparedStatement statement = con.prepareStatement("UPDATE Account SET Role = ? WHERE Id = ?");
            statement.setString(1, data.getRole());
            statement.setInt(2, data.getUser());
            rowsAffected = statement.executeUpdate();
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
        }
        return rowsAffected;
    }

    /**
     * Zurücksetzen des Passworts eines Benutzerkontos. Das Passwort wird immer auf "ichbinreich" gesetzt.
     * @param userId Die ID des Benutzerkontos, dessen Passwort zurückgesetzt werden soll.
     * @return Die Anzahl der veränderten Zeilen in der Datenbank.
     */
    public static int resetPassword(int userId) {
        int rowsAffected = 0;
        try{
            PreparedStatement statement = con.prepareStatement("UPDATE Account SET Password = 'ichbinreich' WHERE Id = ?");
            statement.setInt(1, userId);
            rowsAffected = statement.executeUpdate();
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
        }
        return rowsAffected;
    }

    /**
     * Löschen eines Produktes.
     * @param productId Die Id des Produktes, welches gelöscht werden soll.
     * @return Die Anzahl der veränderten Zeilen in der Datenbank.
     */
    public static int deleteProduct(int productId) {
        int rowsAffected = 0;
        try{
            PreparedStatement statement = con.prepareStatement("DELETE FROM Product WHERE Id = ?");
            statement.setInt(1, productId);
            rowsAffected = statement.executeUpdate();
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
        }
        return rowsAffected;
    }

    /**
     * Lädt die Liste aller Produktbilder.
     * @return Eine Liste aller Bilder in einem Optional, falls der Datenbankaufruf erfolgreich war. Ansonsten ein leeres Optional.
     */
    public static Optional<List<Image>> getImages() {
        try{
            List<Image> images = new ArrayList<Image>();
            ResultSet resultSet = con.createStatement().executeQuery("select * from Image");
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

    /**
     * Lädt die MainInfos des Webshops.
     * @return MainInfos in einem Optional, falls der Datenbankaufruf erfolgreich war. Ansonsten ein leeres Optional.
     */
    public static Optional<MainInfos> getMainInfos() {
        try{
            MainInfos mainInfos = new MainInfos();
            List<TopCustomer> topCustomerList = new ArrayList<TopCustomer>();
            ResultSet bestsellerResult = con.createStatement().executeQuery("SELECT Name FROM Product WHERE Name = (SELECT Name FROM OrderItem GROUP BY Name ORDER BY SUM(Amount) DESC, SUM(TotalPrice) DESC LIMIT 1)");
            ResultSet topCustomerResult = con.createStatement().executeQuery("SELECT (SELECT FirstName FROM Account WHERE Id = User) AS FirstName, (SELECT LastName FROM Account WHERE Id = User) AS LastName, SUM(TotalPrice) AS TotalPurchase FROM OrderItem GROUP BY User ORDER BY SUM(TotalPrice) DESC LIMIT 5");
            while(bestsellerResult.next()){
                mainInfos.setBestseller(bestsellerResult.getString("Name"));
            }
            while(topCustomerResult.next()){
                topCustomerList.add(new TopCustomer(topCustomerResult.getString("FirstName"),
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

    /**
     * Überprüfen, ob eine E-Mail bereits vergeben ist
     * @param email Die zu überprüfende E-Mail
     * @param exceptOfId Die ID eines Benutzerkontos, welches bei der Prüfung ignoriert werden soll. Soll kein Benutzerkonto ignoriert werden, soll muss dieser Parameter auf 0 gesetzt werden.
     * @return true, falls die E-Mail bereits vergeben ist. Ansonsten false.
     */
    public static boolean isEmailAlreadyTaken(String email, int exceptOfId) {
        try{
            PreparedStatement statement = con.prepareStatement("SELECT Id FROM Account WHERE Email = ? AND Id != ?");;
            statement.setString(1, email);
            statement.setInt(2, exceptOfId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
            return true;
        }
    }
}
