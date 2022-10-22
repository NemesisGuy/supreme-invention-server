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

import za.co.nemesisnet.supreme.invention.model.*;

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
        System.out.println("Read Class : USER LOGIN form, passed to  readClass:  " + password);
    //SELECT * FROM ADMINISTRATOR.USERTABLE FETCH FIRST 100 ROWS ONLY;
      String sql = "SELECT * FROM ADMINISTRATOR.USERTABLE WHERE userName='" + userName + "'";

      //  String sql = "SELECT * FROM usertable WHERE userName='" + userName + "'";
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
        //SELECT * FROM ADMINISTRATOR.BOOK
       // String sql = "SELECT * FROM booktable";
        String sql = "SELECT * FROM ADMINISTRATOR.BOOK";
        Statement statement = null;
        try {
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            int count = 0;
            String output = "Connected: \n";

            while (result.next()) {
                String ISBN = result.getString(1); //isbn
                String title = result.getString(2); //title
                String author = result.getString("author"); //author
                String category = result.getString(4); //category  
                boolean availableForLoan = result.getBoolean(5); //availableForLoan
              
                output = output + ++count + "ISBN :  " + ISBN + "Title :  " + title + "Author: " + author + "Category: " + category + "Available For Loan : " + availableForLoan;
                System.out.println(output);
                bookList.add(new Book(ISBN, title, author, category, availableForLoan));
                            
            }
            conn.close();
            //  JOptionPane.showMessageDialog(null, output);

        } catch (SQLException ex) {
            Logger.getLogger(Read.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bookList;
    }

    public ArrayList<User> readAllUsers() {
        ArrayList<User> userList = new ArrayList<User>();
        User user = new User();
        DatabaseConnection databaseConnection = new DatabaseConnection();

        Connection conn = databaseConnection.getDatabaseConnection();
        String sql = "SELECT * FROM ADMINISTRATOR.USERTABLE";
        Statement statement = null;
        try {
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            int count = 0;
            String output = "Connected: \n";

            while (result.next()) {
                String userId = result.getString(1); //userid
                String firstName = result.getString(2); //firstname
                String lastName = result.getString(3);     //lastName
                String userName = result.getString(4); //userName
                String email = result.getString(5); //email
                String password = result.getString(6); //password
                String userAccessLevel = result.getString(7); //userAccessLevel

                output = output +"User ID" + userId + "FirstName :  " + firstName + " LastName :  " + lastName +  " UserName : " + userName +" Email : " + email + " Password : " + password + " userAccessLevel : " + userAccessLevel;
                System.out.println(output);
                userList.add(new User(userId, firstName, lastName, userName, email, password, userAccessLevel ));
                //               User(String userId, String firstName, String lastName, String userName, String email, String password, String accessLevel) {

            }
            conn.close();
            //  JOptionPane.showMessageDialog(null, output);

        } catch (SQLException ex) {
            Logger.getLogger(Read.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userList;
    }

    //public Learner(int studentNumber, String firstName, String lastName,  boolean canBorrow)
    public ArrayList<Learner> readAllLearners() {
        ArrayList<Learner> learnerList = new ArrayList<Learner>();
        Learner learner = new Learner();
        DatabaseConnection databaseConnection = new DatabaseConnection();

        Connection conn = databaseConnection.getDatabaseConnection();
        String sql = "SELECT * FROM ADMINISTRATOR.LEARNER";
        Statement statement = null;
        try {
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            int count = 0;
            String output = "Connected: \n";

            while (result.next()) {
                int studentNumber = result.getInt(1); //studentNumber
                String firstName = result.getString(2); //firstname
                String lastName = result.getString(3);     //lastName   
                boolean canBorrow = result.getBoolean(4); //canBorrow

                output = output + ++count + "Student Number :  " + studentNumber + "FirstName :  " + firstName + " LastName :  " + lastName +  " Can Borrow : " + canBorrow;
                System.out.println(output);
                learnerList.add(new Learner(studentNumber, firstName, lastName, canBorrow));
                //               User(String userId, String firstName, String lastName, String userName, String email, String password, String accessLevel) {

            }
            conn.close();
            //  JOptionPane.showMessageDialog(null, output);

        } catch (SQLException ex) {
            Logger.getLogger(Read.class.getName()).log(Level.SEVERE, null, ex);
        }
        return learnerList;

    }

    public ArrayList<Loan> readAllLoans() {
        ArrayList<Loan> loanList = new ArrayList<Loan>();
        Loan loan = new Loan();
        DatabaseConnection databaseConnection = new DatabaseConnection();

        Connection conn = databaseConnection.getDatabaseConnection();
        String sql = "SELECT * FROM ADMINISTRATOR.LOAN";
        Statement statement = null;
        try {
            statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            int count = 0;
            String output = "Connected: \n";

            while (result.next()) {
                int loanId = result.getInt(1); //loanId
                String userId = result.getString(2); //userId
                String ISBN = result.getString(3);     //ISBN
                String dateBorrowed = result.getString(4); //dateBorrowed
                String dateReturned = result.getString(5); //dateReturned
                String dateDue = result.getString(6); //dateDue

                output = output + ++count + "Loan ID :  " + loanId + "User ID :  " + userId + " ISBN :  " + ISBN +  " Date Borrowed : " + dateBorrowed + " Date Returned : " + dateReturned + " Date Due : " + dateDue;
                System.out.println(output);
                loanList.add(new Loan(loanId, Integer.parseInt(userId), ISBN, dateBorrowed, dateReturned, dateDue));
                //               User(String userId, String firstName, String lastName, String userName, String email, String password, String accessLevel) {

            }
            conn.close();
            //  JOptionPane.showMessageDialog(null, output);

        } catch (SQLException ex) {
            Logger.getLogger(Read.class.getName()).log(Level.SEVERE, null, ex);
        }
        return loanList;
        //cast string to int
        //int userId = Integer.parseInt(result.getString(2)); //userid

    }
}
