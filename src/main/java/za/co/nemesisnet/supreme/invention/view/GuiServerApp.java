/*
 *     
 * 
 */
package za.co.nemesisnet.supreme.invention.view;

/**
 *
 * @author Peter B
 */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import za.co.nemesisnet.supreme.invention.ServerPackages.controller.Create;
import za.co.nemesisnet.supreme.invention.ServerPackages.controller.Read;
import za.co.nemesisnet.supreme.invention.model.*;


public class GuiServerApp extends JFrame implements ActionListener {

    private ServerSocket listener;
    private String msg = "";
    private String upCaseMsg = "";
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private JButton exitBtn = new JButton("EXIT");
    private JTextArea clientTxtArea = new JTextArea(5, 40);
    private String response = "";
    private JPanel topPanel = new JPanel();
    private JPanel centerPanel = new JPanel();

    private ArrayList<Book> books;
    private ArrayList<User> users;
    private ArrayList<Learner> learners;
    private ArrayList<Loan> loans;

    private Book book;
    private User user;
    private Learner learner;

    Message message;
    Object object;
    String command = "0";


    // Client connection
    private Socket client;
//----------------------------------------------------------------------------------    
    //Define a constructor in which you construct a ServerSocket object and setup the Gui

    public GuiServerApp() {
        createSeverSocketObject();
        setupGui(); // Setup the GUI

    }

    public Learner getLearner() {
        return learner;
    }

    public void setLearner(Learner learner) {
        this.learner = learner;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public ArrayList<Loan> getLoans() {
        return loans;
    }

    public void setLoans(ArrayList<Loan> loans) {
        this.loans = loans;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Learner> getLearners() {
        return learners;
    }

    public void setLearners(ArrayList<Learner> learners) {
        this.learners = learners;
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    private void createSeverSocketObject() {
        //Create a ServerSocket object
        try {
            listener = new ServerSocket();
            listener.setReuseAddress(true);
            listener.bind(new InetSocketAddress(5050));
        } catch (IOException ex) {
            Logger.getLogger(GuiServerApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //declare a method to listen for client connections
    private void listenForClients() {
        try {
            client = listener.accept();
            //  this.msg = "";
            System.out.println("Client connected");
            //  System.out.println("Got streams");
            
            processClient();  //call the method to process the client

        } catch (Exception ex) {
            Logger.getLogger(GuiServerApp.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

//declare a method to initiate communication streams
    private void getStreams() {
        try {
            out = new ObjectOutputStream(client.getOutputStream()); //get the output stream
        } catch (IOException ex) {
            Logger.getLogger(GuiServerApp.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            in = new ObjectInputStream(client.getInputStream()); //get the input stream
        } catch (IOException ex) {
            Logger.getLogger(GuiServerApp.class.getName()).log(Level.SEVERE, null, ex);

        }
        System.out.println("Got I/O streams"); //display message

    }



//declare a method in which you close the streams and the socket connection    
//declare a method in which you continuously read from the client; process the incoming data; and write results back to client.    
    public void processClient() {
        //   method to continuously read from the client; process the incoming data; and write results back to client.
        try {
            getStreams();       //call the method to initiate communication streams
            System.out.println("Got streams");          //display message
            do {
                try {System.out.println("process client");
                    Message message = (Message) in.readObject();    // read new message
                    System.out.println("Message received: " + message.getText()); // display message
                    //send response message to client
                    Message response = new Message("Received Message, Processing...");   //create response message
                    out.writeObject(response);  //send response message to client
                    out.flush();    //flush the stream
                    object = in.readObject();   //read the object from the client

                    //message.setText("Hello");
                    msg = message.getText();    // get message text
                    System.out.println("Server received: " + msg);  // display message
                    String temp = clientTxtArea.getText();  // get text from text area

                    clientTxtArea.append("Client sent: " + msg + "\n");  // append new text
                    // clientTxtArea.setText(msg);  // set text of text area

                    commandSelection(message, object);    //call the method to process the command sent by the client
                  //  upCaseMsg = msg.toUpperCase();
                   // sendData(upCaseMsg);    //send the message back to the client

                } catch (ClassNotFoundException classNotFoundException) {
                    System.err.println("Unknown object type received");
                }
            } while (!msg.equals("terminate")); //continue until the client sends the message "terminate"
        } catch (IOException ioException) { //catch any IO exceptions
            System.err.println("Connection terminated");    //display message
        } finally {
            //  closeConnection();
        }
    }

    private void closeConnection() {
        try {
            out.close();
            in.close();
            client.close();
        } catch (IOException ex) {
            Logger.getLogger(GuiServerApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {    //main method
        initServer();   //call the method to start the server

    }

    public static void initServer() {   //method to start the server
        GuiServerApp server = new GuiServerApp();   //create a new server object
        server.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //set the default close operation
        server.setSize(500, 300);
        server.setVisible(true);    //set the size and make the frame visible
        server.setTitle("Server");  //set the title of the frame
        server.setLocationRelativeTo(null);         //set the location of the frame
        server.setResizable(false); //set the frame to be non-resizable

        server.startServer();   //call the method to start the server
    }

    private void setupGui() {   //method to set up the GUI
        //scrollable textarea for clientTxtArea
        JScrollPane scrollPane = new JScrollPane(clientTxtArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        topPanel.setLayout(new FlowLayout());
        topPanel.add(new JLabel("Client Requests:"));
        //add the scrollable textarea to the center panel
        centerPanel.add(scrollPane);

        // topPanel.add(clientTxtArea);
        clientTxtArea.setEditable(false);
        centerPanel.setLayout(new FlowLayout());
        centerPanel.add(exitBtn);
        exitBtn.addActionListener(this);
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitBtn) {
            exit();
        }
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void startServer() {
        //a server that receives connections until told to stop

        System.out.println("ServerSocket awaiting connections...");
        try {
            listenForClients(); //call the method to listen for client connections
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }

    public void getObjectStreams() {

        Object obj = null;
        System.out.println("Object received : " + obj.toString());
        clientTxtArea.setCaretPosition(clientTxtArea.getDocument().getLength());
        clientTxtArea.setText(clientTxtArea.getText() + "Server says : " + obj.toString().toUpperCase() + "...\n ");

    }

    public void commandSelection(Message message, Object object) {
        setMessage(message);
        setObject(object);
        setCommand(message.getText());
        command = getCommand();

        System.err.println("command received : " + command);
        switch (command) {
            case "LOGIN":
                User user = (User) object;
                clientTxtArea.append("Server says : " + "Login request received" + "...\n ");
                clientTxtArea.append("Attempting to login with user credentials : " + "\n" + "User Name : " + user.getUserName() + "\n User Password : " + user.getPassword() + "...\n ");
                System.out.println("User received : " + user.toString());
                User userFromDataBase = login(user);

                response = "User found : " + userFromDataBase.toString();
                System.out.println("response : " + response);
                clientTxtArea.append("Server says : " + response + "...\n ");

                try {
                 //   out.writeObject(new Message(" server says : Logged in!"));
                    out.writeObject(userFromDataBase);
                     System.out.println("Server sent User object as a response : "+ userFromDataBase.toString());
                } catch (IOException ex) {
                    System.out.println("error :could not sent user object");
                    Logger.getLogger(GuiServerApp.class.getName()).log(Level.SEVERE, null, ex);
                }

                break;

            case "FINDBOOK":
                Book book = (Book) object;
                findBook(book);
                break;
            case "LISTALLBOOKS":
                setBooks(listAllBooks());
                 {
                    try {
                        out.writeObject(books);
                        System.out.println("Server sent books object as a response : "+ books.toString());
                    } catch (IOException ex) {
                        Logger.getLogger(GuiServerApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                break;

            case "LIST_ALL_USERS":
                setUsers(listAllUsers());
                 {
                    try {
                        out.writeObject(users);
                        System.out.println("Server sent User object as a response : "+ users.toString());
                    } catch (IOException ex) {
                        Logger.getLogger(GuiServerApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                break;
            case "LIST_ALL_LEARNERS":
                setLearners(listAllLearners());
                 {
                    try {
                        out.writeObject(learners);
                        System.out.println("Server sent Learner object as a response : "+ learners.toString());
                    } catch (IOException ex) {
                        Logger.getLogger(GuiServerApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                break;
            case "LIST_ALL_LOANS":
                setLoans(listAllLoans());
                 {
                    try {
                        out.writeObject(loans);
                        System.out.println("Server sent Loan object as a response : "+ loans.toString());
                    } catch (IOException ex) {
                        Logger.getLogger(GuiServerApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                break;
            case "CREATE_BOOK":
                Book book1 = (Book) object;
               // createBook(book1);
                Book book2 = new Book();
                book2 = createBook(book1);
                setBook(book2);
                 try {
                        out.writeObject(book2);
                        System.out.println("Server sent Book object as a response : "+ book2.toString());
                    } catch (IOException ex) {
                        Logger.getLogger(GuiServerApp.class.getName()).log(Level.SEVERE, null, ex);
                    }


                break;
                 //create learner
            case "CREATE_LEARNER":
                Learner learner = (Learner) object;
                Learner learner2 = new Learner();
                learner2 = createLearner(learner);
                setLearner(learner2);
                 try {
                        out.writeObject(learner2);
                        System.out.println("Server sent Learner object as a response : "+ learner2.toString());
                    } catch (IOException ex) {
                        Logger.getLogger(GuiServerApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                 break;


    
            case "CHAT":
                String chat = (String) object;
                String chatMessage = "Client says : " + chat + "...\n ";
                clientTxtArea.append(chatMessage + "...\n ");
                System.out.println("Chat message received from client : " + chat);
                try {
                    out.writeObject(new Message("Server says : " + "message received " + chat));
                } catch (IOException ex) {
                    System.out.println("Error : Could not sent chat message");
                    Logger.getLogger(GuiServerApp.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;


            default:
                //   throw new AssertionError();
                System.err.println("Default case triggered, Command : " + command + " is not recognised!");
                break;
                
        }

    }

    private Learner createLearner(Learner learner) {
        System.out.println("Server says : " + "Create learner request received" + "...\n ");
        clientTxtArea.append("Server says : " + "Create learner request received" + "...\n ");
        clientTxtArea.append("Attempting to create learner with learner details : " + "\n" + "Learner Name : " + learner.getFirstName() + "\n Learner Surname : " + learner.getLastName() + "...\n ");
        System.out.println("Learner received : " + learner.toString());
        Create create = new Create();
        Learner learnerFromDataBase = create.createLearner(learner);

        response = "Learner created : " + learnerFromDataBase.toString();
        System.out.println("response : " + response);
        clientTxtArea.append("Server says : " + response + "...\n ");
        return learnerFromDataBase;
    }

    private Book createBook(Book bookIn) {
        clientTxtArea.append("Server says : " + "Create Book request received" + "...\n ");
        Book bookFromDatabase = new Book();
        Create create = new Create();
        bookFromDatabase = create.createBook(bookIn.getISBN(),bookIn.getTitle(),bookIn.getAuthors(),bookIn.getCategory(),bookIn.isAvailableForLoan());
        return bookFromDatabase;
    }


    private ArrayList listAllBooks() {
        clientTxtArea.append("Server says : " + "List all books request received" + "...\n ");
        ArrayList<Book> books = listAllBooksFromDataBase();
        clientTxtArea.append("Server says : " + "List of all books in the database" + "...\n ");
        for (Book book : books) {
            clientTxtArea.append("Server says : " + book.toString() + "...\n ");
        }
        return books;
    }

    private ArrayList listAllBooksFromDataBase() {
        ArrayList<Book> books = new ArrayList<>();
        Read read = new Read();
        books = read.readAllBooks();

        return books;
    }
    private ArrayList<Loan> listAllLoans() {
        clientTxtArea.append("Server says : " + "List all loans request received" + "...\n ");
        ArrayList<Loan> loans = listAllLoansFromDataBase();

        return loans;
    }

    private ArrayList<Loan> listAllLoansFromDataBase() {
        ArrayList<Loan> loans = new ArrayList<>();
        Read read = new Read();
        loans = read.readAllLoans();

        return loans;
    }

    private ArrayList listAllLearners() {
        clientTxtArea.append("Server says : " + "List all learners request received" + "...\n ");
        ArrayList<Learner> learners = listAllLearnersFromDataBase();
        clientTxtArea.append("Server says : " + "List of all learners in the database" + "...\n ");
        for (Learner learner : learners) {
            clientTxtArea.append("Server says : " + learner.toString() + "...\n ");
        }
        return learners;
    }

    private ArrayList<Learner> listAllLearnersFromDataBase() {
        ArrayList<Learner> learners = new ArrayList<>();
        Read read = new Read();
        learners = read.readAllLearners();

        return learners;
    }

    public User login(User user) {

        System.out.println("User object input Name: " + user.getUserName());
        System.out.println("User object input Number: " + user.getPassword());
        Read read = new Read();
        User userFromDatabase = read.readUser(user.getUserName(), user.getPassword());
        System.err.println("User email found in Database : " + userFromDatabase.getEmail());

        return userFromDatabase;

    }

    public Book findBook(Book book) {
        System.out.println(book.getTitle());
        Read read = new Read();
        // read.readAllBooks();//read and print all books
        Book bookFromDatabase = read.readBookByTitle(book.getTitle());
        System.out.println("from db info : " + bookFromDatabase.getAuthors());
        return book;
    }
    private ArrayList listAllUsers() {
        clientTxtArea.append("Server says : " + "List all users request recived" + "...\n ");
        ArrayList<User> users = listAllUsersFromDataBase();
        clientTxtArea.append("Server says : " + "List of all users in the database" + "...\n ");
        for (User user : users) {
            clientTxtArea.append("Server says : " + user.toString() + "...\n ");
        }
        return users;


        }
    private ArrayList listAllUsersFromDataBase() {
        ArrayList<User> usersFromDatabase ;
        Read read = new Read();
        usersFromDatabase = read.readAllUsers();
        return usersFromDatabase;
    }
    public void exit() {
        JOptionPane.showMessageDialog(new JFrame(), "Thanks for using my program!  \n \n " + "Author : Peter Buckingham \n Student Number: ****65289 \n Date: Oct 2022", "Server System - Goodbye ", JOptionPane.INFORMATION_MESSAGE);
        System.out.println("");
        System.out.println("Thanks for using my program!");
        System.out.println("Author : Peter Buckingham \n");
        System.err.println("");
        System.exit(0);
    }

}

//----------------------------------------------------------------------------------    

