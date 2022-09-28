package za.co.nemesisnet.supreme.invention.server;
import java.io.*;
import java.net.*;

public class ServerTest {

    public static void main(String[] arg) {
//Employee joe = new Employee(150, "Joe");
        Employee employee = new Employee(0,"");

        try {

            ServerSocket socketConnection = new ServerSocket(11111);

            System.out.println("Server Waiting");

            Socket pipe = socketConnection.accept();
            System.out.println("Server connection accepted");
            ObjectInputStream serverInputStream = new
                    ObjectInputStream(pipe.getInputStream());
            System.out.println("Server ObjectInputStream created");
            ObjectOutputStream serverOutputStream = new
                    ObjectOutputStream(pipe.getOutputStream());
            System.out.println("Server ObjectOutputStream created");

            employee = (Employee)serverInputStream.readObject();
            System.out.println("Server read object,and created employee object");
            employee .setEmployeeNumber(256);
            employee .setEmployeeName("John");

            serverOutputStream.writeObject(employee);
            System.out.println("Server wrote object to client");
            serverInputStream.close();
            System.out.println("Server closed ObjectInputStream");
            serverOutputStream.close();
            System.out.println("Server closed ObjectOutputStream");

        }  catch(Exception e) {System.out.println(e);
        }
    }

}