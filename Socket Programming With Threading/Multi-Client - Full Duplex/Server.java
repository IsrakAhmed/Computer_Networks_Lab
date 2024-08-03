import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/*
    Socket Programming - Multi-Client with Threading - Full Duplex
*/

public class Server {
    public static void main(String[] args) {

        Object nameOfServer = "Server";

        try {

            ServerSocket serverSocket = new ServerSocket(22222);
            System.out.println("Server Started...");

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter Server Name : ");
            String serverName = scanner.nextLine();
            System.out.println(serverName + " Connected...");

            nameOfServer = serverName;

            while(true){
                Socket socket = serverSocket.accept();
                System.out.println("Client Connected...");

                // New Server Thread Start.....
                new ServerThread(socket,serverName);
            }

        } catch (Exception e) {
            System.out.println(nameOfServer + " Disconnected...");
        }

    }
}
