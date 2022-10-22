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

        //private static String dbURL = "jdbc:derby://localhost:1527/myDB;create=true;user=me;password=mine";
        //  private static String tableName = "restaurants";
        // jdbc Connection
        // private static Connection conn = null;
        //  private static Statement stmt = null;
        String dbUrl = "librarydatabase.nemesisnet.co.za";
        String dbName = "LibraryDatabase";
        String dbPort = "3306";
        String dbUser = "Administrator";
        String dbPassword = "password";
        // String dbURL = "jdbc:mysql://" + dbUrl + ":" + dbPort + "/" + dbName + "";
        //String dbURL = "jdbc:mysql://" +"librarydatabase.nemesisnet.co.za"+ ":" + "9042" + "/" +"Library " + "";
        String dbURL = "jdbc:derby://localhost:1527/LibraryDb";
        // String dbURL = "jdbc:mysql://" + "librarydatabase.nemesisnet.co.za" + ":" + "3306" + "/" + "LibraryDatabase " + "";
        Connection conn = null;

        try {

            //  Class.forName("com.mysql.jdbc.Driver"); old driver
          //  Class.forName("com.mysql.cj.jdbc.Driver");
            Class.forName("org.apache.derby.jdbc.ClientDriver").newInstance();
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
        } catch (InstantiationException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }

    public static void main(String[] args) {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        databaseConnection.getDatabaseConnection();
    }
}
