package za.co.nemesisnet.supreme.invention.ServerPackages.controller;

/*
 *
 *
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import za.co.nemesisnet.supreme.invention.model.Book;
import za.co.nemesisnet.supreme.invention.model.DatabaseConnection;
import za.co.nemesisnet.supreme.invention.model.Loan;
import za.co.nemesisnet.supreme.invention.model.User;

import javax.swing.*;


/**
 * @author Peter Buckingham
 */
public class Read {
     String title, subTitle, author, ISBN, description, rating, imageLink;

   public User readUser(String userName, String password){
       //conn
      // User user =  new User();
       //user = conn.searchUser(username , passowrd)
       //result stream to user object
     DatabaseConnection databaseConnection =  new DatabaseConnection();
     Connection conn = databaseConnection.getDatabaseConnection();
 

        User user = null;
        System.out.println(userName);
        System.out.println("from USER LOGIN form, passed to  readClass:  " + password);

      

        String sql = "SELECT * FROM usertable WHERE userName='" + userName + "'";
        Statement statement = null;
        try {
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);
            int count = 0;
            String output = "Connected: \n";
            while (result.next()) {
                System.out.println("User data found!" + result.getString(4));
                result.getString(4);
                if (result.getString(4).equalsIgnoreCase(userName)) {
                    if (result.getString(6).equals(password)) {
                        user = new User(result.getString(1), result.getString(2), result.getString(3), result.getString(4), result.getString(5), result.getString(6), result.getString(7));
                        conn.close();
                        return user;
                    } else {
                        System.out.println("Missmatch - close ");

                    }

                }
                System.out.println("Missmatch ");
            }
            // JOptionPane.showMessageDialog(null, "Error - User name or password are incorrect! \n Check for typos \n Try register for a new account!");

        } catch (SQLException ex) {
            Logger.getLogger(Read.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            conn.close();
             System.out.println("conn closed ");
        } catch (SQLException ex) {
            Logger.getLogger(Read.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;

    }
    public Book readBookByTitle(String title) {
        String inputTitle = title;
        DatabaseConnection databaseConnection = new DatabaseConnection();
        Connection conn = databaseConnection.getDatabaseConnection();

//        while (inputTitle == null || inputTitle.isEmpty() || inputTitle.isEmpty()) {
//            inputTitle = JOptionPane.showInputDialog(null, "Please enter the name of the book you would like to find:");
//        }
        String sql = "SELECT * FROM booktable WHERE title='" + title + "'";
        Statement statement = null;
        System.out.println("trying to send sql... query : " + sql );
        try {
            statement = conn.createStatement();
            System.out.println("connection.create statement" );
            ResultSet result = statement.executeQuery(sql);
             System.out.println("ResultSet result = statement.executeQuery(sql) - completed" );
            int count = 0;
            String output = "Connected: \n";
            System.out.println(output);
            if (!result.isBeforeFirst()) {//did DB give data back ? if not do this...
               // JOptionPane.showMessageDialog(null, "Error : The book titled " + inputTitle + " was not found!");
                System.out.println("Error : The book titled " + inputTitle + " was not found!");
                System.out.println("No data");
            } else {///dose have data do this
                while (result.next()) {
                    title = result.getString(2);
                    subTitle = result.getString(3);
                    author = result.getString("author");
                    ISBN = result.getString(5);
                    description = result.getString(6);
                    rating = result.getString(7);
                    imageLink = result.getString(8);

                    output = output + ++count + " " + title + " " + subTitle + " " + author + " " + ISBN + " " + rating + " \n" + description + "\n" + imageLink;
                    System.out.println(output);
                    break;
                }
                conn.close();
                JOptionPane.showMessageDialog(null, "Success - Book titled " + inputTitle + " found: \n" + output);
  //  public Book(int bookshelfNumber, String title, String subTitle, String ISBN, String authors, String description, String category, String imageLink, boolean availableForLoan) {

  //              Book book = new Book(title, subTitle, ISBN, author, description, rating, imageLink);
                //displayBookForm = new DisplayBookForm(book);
   //             System.out.println(book.getISBN() + "from read class");

                System.out.println(title + " " + subTitle);
 //               return book;

            }
        } catch (SQLException ex) {
            Logger.getLogger(Read.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
   public Book readBook(){
       return null;
   }
   public Loan readloan(){
       return null;
   }
   public ArrayList<Book> readAllBooks() {
        ArrayList<Book> bookList = new ArrayList<Book>();
        Book book = new Book();
        DatabaseConnection databaseConnection = new DatabaseConnection();

        Connection conn = databaseConnection.getDatabaseConnection();
        String sql = "SELECT * FROM booktable";
        Statement statement = null;
        try {
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            int count = 0;
            String output = "Connected: \n";

            while (result.next()) {
                String id = result.getString(1);
                String title = result.getString(2);
                String subTitle = result.getString(3);
                String author = result.getString("author");
                String ISBN = result.getString(5);
                String description = result.getString(6);
                String rating = result.getString(7);
                String imageLink = result.getString(8);

                output = output + ++count + " " + title + " " + subTitle + " " + author + " " + ISBN + " " + rating + " \n" + description + "\n" + imageLink;
                System.out.println(output);
                bookList.add(new Book(count, title, subTitle, ISBN, author, description, author, imageLink, true));
                            
            }
            conn.close();
            //  JOptionPane.showMessageDialog(null, output);

        } catch (SQLException ex) {
            Logger.getLogger(Read.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bookList;
    }
}
