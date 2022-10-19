/*
 */
package za.co.nemesisnet.supreme.invention.model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author Peter Buckingham
 */
public class DatabaseConnection {
//derby port 1527

   

    public Connection getDatabaseConnection() {
       
        String dbUrl = "librarydatabase.nemesisnet.co.za";
        String dbName = "LibraryDatabase";
        String dbPort = "3306";
        String dbUser = "LibraryDbUser";
        String dbPassword = "NcP_?+3gV63g";
        // String dbURL = "jdbc:mysql://" + dbUrl + ":" + dbPort + "/" + dbName + "";
        //String dbURL = "jdbc:mysql://" +"librarydatabase.nemesisnet.co.za"+ ":" + "9042" + "/" +"Library " + "";
   
        String dbURL = "jdbc:mysql://" + "librarydatabase.nemesisnet.co.za" + ":" + "3306" + "/" + "LibraryDatabase " + "";
        Connection conn = null;

        try {

            //  Class.forName("com.mysql.jdbc.Driver"); old driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println(dbURL + " " + dbUser + " " + dbPassword);
            conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

            // conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            if (conn != null) {
                System.out.println("Connected");
            }

        } catch (SQLException ex) {
            System.out.println("Connection Failed!!");
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    public static void main(String[] args) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.getDatabaseConnection();
    }
}
