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
import za.co.nemesisnet.supreme.invention.ServerPackages.controller.Read;
import za.co.nemesisnet.supreme.invention.model.Book;
import za.co.nemesisnet.supreme.invention.model.Message;
import za.co.nemesisnet.supreme.invention.model.User;


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

    private void setupGui() {   //method to setup the GUI
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
        //a server that revices connections untll told to stop

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
                clientTxtArea.append("Server says : " + "Login request recived" + "...\n ");
                clientTxtArea.append("Attempting to login with user crededntials : " + "\n" + "User Name : " + user.getUserName() + "\n User Password : " + user.getPassword() + "...\n ");
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

            case "findBook":
                Book book = (Book) object;
                findBook(book);
                break;
            case "LISTALLBOOKS":
                setBooks(listAllBooks());
                 {
                    try {
                    //    out.writeObject(new Message("server says : Book List results"));
                        out.writeObject(books);
                    } catch (IOException ex) {
                        Logger.getLogger(GuiServerApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

                break;

            default:
                //   throw new AssertionError();
                System.err.println("default case triggered");
        }

    }

    private ArrayList listAllBooks() {
        clientTxtArea.append("Server says : " + "List all books request recived" + "...\n ");
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

