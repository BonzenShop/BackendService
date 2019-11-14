package com.bonzenshop.BackendService.service;

import com.bonzenshop.BackendService.model.Account;
import com.bonzenshop.BackendService.model.ChangeRoleRequest;
import com.bonzenshop.BackendService.model.Order;
import com.bonzenshop.BackendService.model.Product;

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
                        resultSet.getString("ImgData"),
                        resultSet.getString("ImgType"),
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
            PreparedStatement statement = con.prepareStatement("INSERT INTO Orders(User, OrderDate, Name, Category, Price, Amount, TotalPrice) VALUES(?,?,?,?,?,?,?)");
            for(int i = 0; i < orderList.size(); i++){
                Order order = orderList.get(i);
                if(i > 0){
                    statement.addBatch(",(?,?,?,?,?,?,?)");
                }
                statement.setInt(i*7+1, order.getUser());
                statement.setString(i*7+2, order.getOrderDate());
                statement.setString(i*7+3, order.getName());
                statement.setString(i*7+4, order.getCategory());
                statement.setDouble(i*7+5, order.getPrice());
                statement.setInt(i*7+6, order.getAmount());
                statement.setDouble(i*7+7, order.getTotalPrice());
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
                        resultSet.getDouble("TotalPrice")));
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
                        resultSet.getDouble("TotalPrice")));
            }
            return Optional.ofNullable(orders);
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
            return Optional.empty();
        }
    }

    public static int addProduct(Product product) {
        int rowsAffected = 0;
        try{
            PreparedStatement statement = con.prepareStatement("INSERT INTO Products(Name, Description, Category, Price, OnStock, ImgData, ImgType) VALUES(?,?,?,?,?,?,?)");
            statement.setString(1, product.getName());
            statement.setString(2, product.getDesc());
            statement.setString(3, product.getCategory());
            statement.setDouble(4, product.getPrice());
            statement.setDouble(5, product.getOnStock());
            statement.setString(6, product.getImgData());
            statement.setString(7, product.getImgType());
            rowsAffected = statement.executeUpdate();
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
        }
        return rowsAffected;
    }

    public static int updateProduct(Product product) {
        int rowsAffected = 0;
        try{
            PreparedStatement statement = con.prepareStatement("UPDATE Products SET Name = ?, Description = ?, Category = ?, Price = ?, OnStock = ?, ImgData = ?, ImgType = ? WHERE Id = ?");
            statement.setString(1, product.getName());
            statement.setString(2, product.getDesc());
            statement.setString(3, product.getCategory());
            statement.setDouble(4, product.getPrice());
            statement.setDouble(5, product.getOnStock());
            statement.setString(6, product.getImgData());
            statement.setString(7, product.getImgType());
            statement.setInt(8, product.getId());
            rowsAffected = statement.executeUpdate();
        }catch(SQLException e){
            System.out.println("SQL Error: "+e.getMessage());
        }
        return rowsAffected;
    }

    public static int updateAccount(Account account) {
        int rowsAffected = 0;
        try{
            PreparedStatement statement = con.prepareStatement("UPDATE Users SET ");
            int parameterIndex = 1;
            int indexCheck = 1;
            if(account.getFirstName() != null && !account.getFirstName().trim().isEmpty()){
                statement.addBatch("FirstName = ?");
                statement.setString(parameterIndex, account.getFirstName());
                parameterIndex++;
            }
            if(account.getLastName() != null && !account.getLastName().trim().isEmpty()){
                if(indexCheck < parameterIndex){
                    statement.addBatch(", ");
                    indexCheck++;
                }
                statement.addBatch("LastName = ?");
                statement.setString(parameterIndex, account.getLastName());
                parameterIndex++;
            }
            if(account.getEmail() != null && !account.getEmail().trim().isEmpty()){
                if(indexCheck < parameterIndex){
                    statement.addBatch(", ");
                    indexCheck++;
                }
                statement.addBatch("Email = ?");
                statement.setString(parameterIndex, account.getEmail());
                parameterIndex++;
            }
            if(account.getBirthDate() != null && !account.getBirthDate().trim().isEmpty()){
                if(indexCheck < parameterIndex){
                    statement.addBatch(", ");
                    indexCheck++;
                }
                statement.addBatch("BirthDate = ?");
                statement.setString(parameterIndex, account.getBirthDate());
                parameterIndex++;
            }
            if(account.getPassword() != null && !account.getPassword().trim().isEmpty()){
                if(indexCheck < parameterIndex){
                    statement.addBatch(", ");
                    indexCheck++;
                }
                statement.addBatch("Password = ?");
                statement.setString(parameterIndex, account.getPassword());
                parameterIndex++;
            }
            statement.addBatch(" WHERE Id = ?");
            statement.setInt(parameterIndex, account.getId());

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

    public static int resetPasswort(int userId) {
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

    private static void initDB(){
        try{
            //create tables
            con.createStatement().execute("CREATE TABLE Products(Id INTEGER PRIMARY KEY, Name text, Description text, Category varchar(20), Price bigint, OnStock INTEGER, ImgData text, ImgType varchar(20))");
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
                    //Transporttiere
                    "('Kamel', 'Sind mal wieder alle drei Helikopter ausgefallen? Dann ist dieses Kamel genau das Richtige für Sie. Der kleine Helfer ist stets zuverlässig und bietet allerhöchsten Komfort, sowohl auf Kurz- als auch auf Langstrecke. Seit der Geburt wurden die Trampeltiere nur mit den nährstoffreichsten Kräutern der Alpen und Quellwasser aus dem Himalaya Gebirge versorgt. Böse Zungen behaupten, Sie hätten Frau und Tochter für dieses Wunderwerk der Natur eingetauscht. Überzeugen Sie sich selbst!', 'Tiere', '1000000', '2500'), " +
                    "('Elefant', 'Elefanten sind die größten Landsäugetiere. Die Dickhäuter faszinieren Menschen seit Jahrtausenden mit ihrer Intelligenz und ihrem sensiblen Wesen. Elefanten gehören zur Ordnung der Rüsseltiere und bilden die Familie der Elefanten. Gemeinsam ist ihnen die typische Gestalt: Der mächtige Körper, die großen Ohren und der lange Rüssel sowie die vier säulenartigen Beine, deren Fußsohlen aus einem dicken Polster bestehen. Sie wirken wie Stoßdämpfer und helfen so, das enorme Gewicht der Tiere zu tragen.', 'Tiere', '1000000', '500'), " +

                    //Flugzeuge
                    "('Airbus A380', 'Der Airbus A380 ist ein Gigant der Lüfte. Mit einer Kapazität von 853 Passagieren bietet dieses Flugzeug ausreichend Platz für alle Untertanen an Board. Die Reichweite beträgt 16.300 Kilometer, also genug um einmal jede Ecke seines Grundstücks von oben zu betrachten. Der A380 darf in keinem Hangar fehlen, ein absolutes Pflichtprodukt für den kleinen Preis.', 'Flugzeuge', '10000000', '1'), " +
                    "('Boeing Business Jet', 'Die Fakten sprechen für sich: 18 Personen im Innenraum, Personalbereich mit 4 Sitzplätzen, Aufenthaltsraum mit großem Sofa und 2 Einzelsitzen, Ess- und Konferenzbereich für 7 Personen, privates Gästezimmer mit Hoch-Tief-Tisch und Etagen-/Ankerfunktion, Mastersuite mit Doppelbett, Einzelsitz und privates WC mit Dusche. HD-videofähiges Entertainmentsystem mit AVOD und 2-Zonen \"Surround-Sound\"-System. 5 externe Kameras - EFB Crew-Ruheplatz mit Einzelsitz - Crew-WC - Gäste-WC, Vorderküche mit zusätzlicher Speisekammer, Wassersystem-Design für Kaltwetterbetrieb, tankloses Warmwassersystem, Andrew Winch Interior Design, Lufttreppe nach vorne.', 'Flugzeuge', '15000000', '200'), " +

                    // Autos
                    "('Lamborghini Aventador', 'Der Herzschlag des Aventadors ist wohl der berühmte V12-Motor. Mit seiner unglaublichen Beschleunigung bei jeder Drehzahl, der blitzartigen Reaktionsfähigkeit, dem atemberaubenden Dröhnen und nun auch noch 40 PS mehr, die auf dem Asphalt losgelassen werden können, verkörpert er Kraft in seiner pursten Form. Der Aventador gibt sich damit allerdings noch nicht zufrieden: Das Leistungsspektrum des Supersportwagens zusammen mit der extremen Wendigkeit, hervorgerufen durch die Vierradlenkung, gipfelt in einer zuvor nie da gewesenen Fahrdynamik. Das exklusive Lamborghini-Design und ein neuer V12-Motor mit satten 740 PS treffen auf absolute Spitzentechnologie. Mit diesem Modell können nun all jene, die sich Tag für Tag aufs Neue selbst herausfordern wollen, ein Fahrerlebnis ohnegleichen genießen.', 'Autos', '12000000', '2000'), " +
                    "('Bugatti Veyron', 'Der Bugatti Veyron 16.4 ist ein französischer Supersportwagen von Bugatti Automobiles. Mit ihm wurden mehrere Geschwindigkeitsrekorde gebrochen. In der Modellvariante Bugatti Veyron 16.4 Super Sport (seit 2010) vereint das Fahrzeug etliche Superlative und Besonderheiten. Dazu zählen unter anderem die namensgebenden 16 Zylinder des 640 kg schweren Mittelmotors (530 kg ohne Getriebe), die Anordnung der Zylinder in VV-R-Form (kein echter W-Motor), das 110 kg schwere Siebenganggetriebe mit Doppelkupplung, die maximale Leistung von 883 kW (1200 PS), die Höchstgeschwindigkeit von 431 km/h, die vier Turbolader, insgesamt zehn Kühler, der Maximalverbrauch von bis zu 100 Litern auf 100 km, das maximale Drehmoment von 1500 Nm und die Beschleunigung von 0 auf 100 km/h in 2,5 Sekunden. Um das schwere Fahrzeug sicher abbremsen zu können, werden Carbon-Keramik-Bremsscheiben eingebaut, die den Wagen aus 100 km/h in 2,3 Sekunden auf einer Strecke von 31,4 m zum Stehen bringen. Bei Geschwindigkeiten über 240 km/h wird ab einer Bremspedalzeit von 0,4 s automatisch, wie beim Mercedes-Benz SLR McLaren, der Heckflügel steiler gestellt, wodurch der Auftrieb verringert bzw. der Anpressdruck des Autos erhöht wird.', 'Autos', '2450000', '100'), " +

                    //Yachten
                    "('Straßen von Monaco', 'Dieses 155 Meter lange Luxus-Schiff ist dem gleichnamigen Fürstentum an der Mittelmeerküste nachempfunden. Höhepunkt des Schiffes ist der detailgetreuen Nachbau der Grand-Prix-Rennstrecke inklusive Tunnel und Rascasse-Kurve. Sie ist sogar eine voll funktionstüchtige Kartbahn. Aber auch weitere Sehenswürdigkeiten, wie das legendäre Casino, verschiedene prominente Hotels und der Jachthafen sind nachgebaut worden. Und natürlich ist auch für ausreichendes Freizeitprogramm an Bord gesorgt: Es gibt einen Spa-Bereich, Tennis- und Basketballplätze, ein Fitness-Center, ein Kino sowie eine Bücherei. Für Landausflüge stehen Ihnen Schnellboote und Jet-Skis zur Verfügung. Und sogar ein U-Boot und ein Helikopter gehören zu der Ausstattung der schwimmenden Stadt. Mit dem Kauf erwerben Sie Ihr eigenes kleines Monaco. Ideal um in Bonzenrunden mal richtig den Dicken raushängen zu lassen – natürlich nur auf metaphorischer Ebene.', 'Yachten', '75000000000', '1'), " +
                    "('Solandge', 'Diese prächtige 2013 sechsdeckige Lurssen Yacht SOLANDGE hat ein zeitloses Superyacht-Design. Ihre Länge beträgt 85 Meter und sie hat ein Außendesign, das sich durch geschwungene Formen, Kurven und Linien auszeichnet. M/Y Solandge hat einen Stahlrumpf und einen Aluminiumüberbau, wie es für die meisten Boote dieser Größe und Art üblich ist. Die Yacht verfügt auch über großartige gemeinschaftliche Unterhaltungsräume im Inneren und großzügige Außenbereiche, so dass sie sich sowohl als Luxus-Charter-Yacht als auch als Privatyacht sehr gut eignet. Die Yacht bietet Platz für bis zu 16 Gäste in 8 herrlichen Kabinen. Sie verfügt auch über eine große, individuelle Eignerdecke und Suite. Die zahlreichen 29 Mann starke Besatzung des Schiffes schläft in 15 Mannschaftskabinen. Solandge verfügt über sehr große, zarte Garagentore, die sich auf ihrem Unterdeck zum Heck befinden. Sie hat auch eine weitere Tür, die als empfindlicher Andockbereich in ihrem Rumpf und auch als Strandbad genutzt werden kann.', 		      'Yachten', '175000000', '1'), " +

                    //Sonstige Transportmittel
                    "('Sänfte', 'Eine Sänfte ist die beste Transportmöglichkeit um sich unterwegs auch königlich zu fühlen. Der Vorgänger des Taxis, der ab dem 17. Jahrhundert eingesetzt wurde um die glorreichen Könige und Adligen sicher zu transportieren, hebt sich in der heutigen Zeit durch seine Rarität ab. Königsfamilien werden Sie auf der Straße neidisch anschauen, wenn Sie von Ihren würdevollen Sänftenträgern, mit unserer Sänfte, zu Ihren Bonzenrunden kutschiert werden und die nur mit einer gewöhnlichen Limousine aufkreuzen. Die an den Seiten angebrachten Stangen können von jeden Händen 1A getragen werden. Wird Ihnen kein Sänftenträger gerecht, kann auch ein Tier zum Ziehen eingesetzt werden.', 'Sonstige Transportmittel', '4500000', '1234'), " +

                    //Ringe
                    "('Diamant Ring Lady Diana', 'Der Diamant Ring Lady Diana steht vor allem für eins: Reichtum. Der Ring wurde vom exklusiven Juwelier Shawish aus der Schweiz gefertigt. Dieser Ring soll der erste Diamant-Ring der Welt sein. Er verfügt über 150 Karat und wurde aus einem einzigen Werkstück gefertigt. Für die spezielle Fertigung wurde ein ebenso spezieller Laser verwendet. Dieses Werkzeug wurde extra für die Fertigung dieses Rings gebaut.', 'Ringe', '30020000', '1'), " +
                    "('Betteridge 14.92 Carat Round Brilliant Diamond “Love Knot” Ring', 'Ein Diamant-Verlobungsring, der einen außergewöhnlichen farblosen runden Diamanten mit Brillantschliff und einem Gewicht von 14,92 Karat (D-Farbe/VS1-Klarheit) zeigt, umgeben von einer farblosen, durchbrochenen, diamantbesetzten \"Love Knot\"-Setzung mit einem natürlich rosa diamantbesetzten Gürtel, wobei der Ring in Platin mit 18k Rotgold-Akzent montiert ist. Entworfen und handgefertigt von Betteridge in den USA. Begleitet vom GIA-Laborzertifikat für den Center-Diamanten. Dieser Diamant wird im Handel als \"Triple Excellent\" bezeichnet, da er von der GIA in Bezug auf Schnitt, Politur und Symmetrie als ausgezeichnet eingestuft wurde. Perfekt wenn eine Ihrer Frauen ein besonderes Geschenk von Ihnen verdient hat.', 'Ringe', '38320000', '1'), " +

                    //Monumente
                    "('Freiheitsstatue', 'Die von Frédéric-Auguste Bartholdi geschaffene neoklassizistische Kolossalstatue findet sich neuerdings auch in unserem Angebot. Mit nur wenigen Klicks können Sie das Freiheitssymbol der Vereinigten Staaten erwerben und auf Ihrem eigenen Grundstück platzieren. Hinweis: Nach Kauf wird die Statue in mühevoller Handarbeit von Kindern aufgebaut. Selbstverständlich herrschen Arbeitsbedingungen wie es bei dem Bau der Pyramiden in Ägypten der Fall war. Pausen und genereller Arbeitsschutz werden natürlich nicht berücksichtigt, sodass Ihre sadistischen Triebe eine entsprechende Befriedigung finden.', 'Monumente', '250000000000', '1'), " +

                    //Ketten
                    "('Hutton-Mdivani Jadeit Halskette', '\"Ein Preis für Gold lässt sich finden, doch Jade ist unbezahlbar.\" Dieses chinesische Sprichwort ist unumgänglich wenn man mit der Hutton-Mdivani Jadeit Halskette in Kontakt gerät. Ein Unikat das von 27 Jadeit-Perlen geziert wird, die allesamt ein sanft violettes Lavendel, geheimnisvoll grün schimmerndes Schwarz und faszinierendes Grün beinhalten. Fein poliert zeigt jede Perle einen hohen Glanz und reflektiert das Licht wie ein Spiegel. 481-221 v. Chr. tauschte man ganze Städte gegen einen \"Stein des Himmels\" und diese Kette besteht gleich aus 27. Dagegen ist unser Preis ein Geschenk von Gott für Sie.', 'Ketten', '47300000', '1'), " +

                    //Residenzen
                    "('Les Cèdres', 'Die begehrteste und teuerste Villa unter Reichen befindet sich exklusiv bei uns zum Verkauf. Falls Sie eine kleine Erweiterung ihrer Grundstücke möchten, können Sie diese Residenz auf der Halbinsel Cap Ferrat, direkt an der Côte d''Azur erwerben. Mit zehn Schlafzimmern, einem Ballsaal, einem Wintergarten, einer Kapelle, Stallanlagen für 30 Rosse oder sonstigen Tieren und einem Garten, haben Sie wieder mehr Platz um Ihre wichtigen Tätigkeiten nachzugehen zu können.', 'Residenzen', '150000000', '1'), " +

                    //Uhren
                    "('Reference 57260', 'Diese maßgefertigte Taschenuhr ist eine beispiellose, technische Weiterentwicklung der hohen Uhrmacherkunst, die direkt einem Traum entsprungen zu sein scheint. Unter insgesamt 57 Komplikationen befinden sich verschiedene Neuheiten, wie etwa die Verbindung mehrerer Kalenderanzeigen und der zweifach retrograde Schleppzeiger-Chronograph. Angesichts der großen Anzahl der angezeigten Komplikationen gelingt den beidseitigen Zifferblättern der Uhr Reference 57260 das Meisterstück einer außergewöhnlich guten Ablesbarkeit. Das Gehäuse aus Weißgold ist perfekt ausgewogen und verfügt über diskret im Mittelteil integrierte Drückerknöpfe. Die 2800 Bauteile wurden allesamt in Handarbeit veredelt, die sich traditioneller Techniken wie Anglieren, Perlieren und Genfer Streifen bediente. Die ästhetische Vortrefflichkeit von Vacheron Constantin findet somit auf der gesamten Uhr ihren Ausdruck.', 'Uhren', '12000000', '1'), " +
                    "('Royal Oak Offshore 18-carat White Gold Diamond Pave Automatic', 'Diese Uhr besteht aus 18 Karat Weißgold Diamant Pflastergehäuse mit einem 18 Karat Weißgold Diamant Pflasterarmband und einem 18-karätiges Weißgold Diamant-Pflasterzifferblatt mit geschwärzten Goldzeigern. Das Zifferblatttyp ist Analog. Die Uhr enthält Leuchtende Zeiger und eine Datumsanzeige auf der 3-Uhr-Position. Das Saphirglas ist selbstverständlich kratzfestes und die Krone ist festgeschraubt. Das Skelett ist transparent und der Gehäuseboden ist durchsichtig. Der Durchmesser des runden Gehäuses beträgt 44 mm. Die Gehäusedicke beträgt 15,50 mm. Die Uhr ist Wasserdicht bis 200 Meter. Als krönender Abschluss ist das Zifferblatt mit Diamanten im Baguetteschliff angefertigt. Die Diamanten haben ungefähr 6,53 Karat.', 'Uhren', '2358000', '5000'), " +

                    //Straßen
                    "('Kurfürstendamm', 'Mit dem Kauf des Kurfürstendamms in Berlin stehen Ihnen alle Möglichkeiten offen. Es ist die Haupteinkaufstraße im Ortsteil Grunewald. Der Kurfürstendamm ist geprägt von Handel und Gastronomie und gilt als einer der touristischen Anlaufpunkte in der Berliner City West. Mit einer Gesamtlänge von 3.500 Metern können Sie diese Straße auch schnell in eine Landebahn für diverse Passagierflugzeuge oder Jets verwandeln.', 'Straßen', '3220000000', '1'), " +
                    "('Times Square', 'Neben etwa 40 Theatern befinden sich im Bezirk um den Times Square Cafés, Fast-Food-Ketten und Andenkenläden, aber auch Nobelrestaurants, Multiplex-Kinos, die MTV-Studios, Sony und Vogue. Auch die amerikanische Technologiebörse NASDAQ ist in der Nachbarschaft des Times Squares beheimatet. Der Times Square ist berühmt für seine zahlreichen Leuchtreklamen. Der Times Square wird in den USA oft als The Crossroads of the World, The Center of the Universe, The heart of the Great White Way – letzteres ist eine Bezeichnung für den Theaterdistrikt rund um den Broadway – oder The Heart of the World bezeichnet.', 'Straßen', '577200000', '1'), " +

                    //Haustiere
                    "('Löwe', 'Der Besitz eines Löwen ist ein absolutes Must-have für jeden Bonzen. Die Raubkatzen sind sehr muskulöse Tiere, deren Statur darauf ausgelegt ist, aus dem Stand eine hohe Geschwindigkeit zu erreichen. Sie sind in der Lage auf über 60 km/h zu beschleunigen und damit selbst Tiere, die eine höhere Endgeschwindigkeit erreichen können, wie viele Gazellenarten, zu erbeuten. Ein Löwenmännchen hat je nach Unterart eine Kopf-Rumpf-Länge von etwa 155-209 cm, eine Schulterhöhe von bis zu 114 cm sowie eine Schwanzlänge zwischen 68-104 cm. Ausgewachsenen bringt ein solches Männchen ein durchschnittliches Körpergewicht von etwa 225 kg (110 kg bis 272 kg) auf die Waage.', 'Haustiere', '1000000', '47000'), " +
                    "('Hai', 'Der Weiße Hai ist für viele das Ungeheuer aus der Tiefe und gehört zu den faszinierendsten Meeresbewohnern. Der Weiße Hai gehört zu den so genannten Echten Haien. Denn er hat die typische Gestalt eines Hais: Der Körper ist torpedoförmig, so dass er ein perfekter Schwimmer ist. Die Schnauze ist kegelförmig und spitz. Unverkennbar sind die sichelförmige Schwanzflosse, die dreieckige Rückenflosse und die langen Brustflossen, die unten an den Spitzen dunkel gefärbt sind. Der Bauch ist weißlich, der Rücken blau bis graubraun. Im Durchschnitt wird der Weiße Hai 4,5 bis 6,5 Meter lang, manche sogar bis zu sieben Meter.', 'Haustiere', '1000000', '98000'), " +

                    //Edelsteine
                    "('Blue Moon of Josephine', 'Der Blue Moon of Josephine Diamant wiegt 12,03 Karat und hat die beliebte Farbe \"Fancy vivid blue\". Das Blau ist demnach ein ausgezeichnet strahlendes Blau, das je nach Lichteinfall einen Graublaustich zeigt, mittel- bis dunkelblau changiert. Im Stein gibt es keine inneren Makel festzustellen, selbst unter zehnfacher Vergrößerung. Den ursprünglichen Namen \"Blue Moon Diamond\" erhielt der Diamant durch das astronomische Phänomen eines blauen Mondes, welcher der zweite Vollmond eines Monats sein soll. Dieser Diamant wurde als zweiter blauer Diamant innerhalb eines kurzen Zeitraums gefunden und erhielt somit den Namen. Ihr Vorgänger hat den Diamant schließlich nach seiner Tochter Josephine umgetauft. Wenn Ihnen der Name nicht gefällt, können Sie ihn selbstverständlich in Ihren Namen umtaufen.', 'Edelsteine', '33271000', '1'), " +

                    //Gold
                    "('Goldener Thron', 'Bei den Bonzenrunden sitzt jeder auf einem Thron und Sie wollen autoritärer wirken und mehr Prestige ausstrahlen? Mit einem vergoldeten Thron tritt genau dies ein. Als Symbol der Könige und Götter ist es Pflicht für jeden Bonzen über seine Untertanen von einem Thron zu herrschen. Mit einem vergoldeten Thron werden Sie in den Bonzenrunden zum König der Könige aufsteigen. Ihre Untertanen würden es Ihnen auch danken, wenn sie sich täglich vor Ihnen auf einem goldenen Thron verneigen müssen. Es ist also ein Win-Win Einkauf.', 'Gold', '12000000', '82000'), " +
                    "('Goldbarren', '\"Geld allein macht nicht glücklich. Es gehören auch noch Aktien, Gold und Grundstücke dazu.\" Danny Kaye. Dieses Zitat ist eins der treffendsten für Bonzen. Denn es gibt nichts was mehr glücklich macht, als dass, was man sich von seinem Geld kauft. Dabei ist es wichtig, dass Goldbarren auch dazu gehören, denn sie sind vielseitig einsetzbar. Beim Bezahlvorgang kann man die Goldbarren auf den Tisch klatschen um von den anderen bewundert zu werden. Wenn die Türen offen bleiben sollen, kann man Goldbarren als Türstopper verwenden oder falls man wieder bei bonzigen Aktivitäten gestört wird, kann man aus Goldbarren Flüssiggold gewinnen um den Störenden stilvoll zu vergolden.', 'Gold', '1000000', '1000000'), " +

                    //Untertanen
                    "('Hofnarr', 'Ein Hofnarr ist ein wichtiger Untertan eines Bonzen. Er hat die Aufgabe Sie zu bespaßen und zu unterhalten. Als Bonze konsumieren Sie natürlich nicht die Unterhaltungsmedien des einfachen Pöbels, weswegen Sie einen Hofnarren benötigen, wenn Sie einfach nur auf Ihrem Thron unterhalten werden möchten. Natürlich befolgt er auch alle anderen Befehle, die nicht zu seinen sonstigen Hofnarrenaufgaben gehören. Ihren Hofnarren können Sie selbstverständlich selber aussuchen.', 'Untertanen', '1000000', '6000000000'), " +
                    "('Sänftenträger', 'Sie haben sich bereits eine Sänfte genehmigt und Ihnen fehlen noch Träger? Dann haben wir genau das richtige für Sie! Je nachdem, wie Sie sich Ihre Träger vorstellen, können wir Sie Ihnen schicken. Ob Sie wie früher sein sollen oder normal. Unsere Produktpalette streckt sich von verarmten Produkten der Inzucht mit Hasenscharten und deformierten Köpfen bis hin zu muskulösen Models, die aussehen wie \"The Rock\". Auch außergewöhnliche Produkte mit weniger Gliedmaßen oder schwarze Liliputaner können bei uns erworben werden.', 'Untertanen', '1000000', '6000000000')");

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
