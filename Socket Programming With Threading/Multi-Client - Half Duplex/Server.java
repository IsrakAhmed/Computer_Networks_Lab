import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/*
    Socket Programming - Multi-Client with Threading - Half Duplex
*/

public class Server {
    public static void main(String[] args) {

        Object nameOfServer = "Server";

        try(ServerSocket serverSocket = new ServerSocket(22222);) {
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

class ServerThread implements Runnable {

    Socket clientSocket;
    Thread thread;
    Object serverName;

    ServerThread(Socket clientSocket, Object serverName) {
        this.clientSocket = clientSocket;
        this.serverName = serverName;
        thread = new Thread(this);
        thread.start();
    }

    public void run(){

        Object nameOfServer = "Server";
        Object nameOfClient = "Client";

        try {

            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            Scanner scanner = new Scanner(System.in);

            nameOfServer = serverName;

            //sent name to client...
            objectOutputStream.writeObject(serverName);

            //receive name from client...
            Object clientName = objectInputStream.readObject();
            System.out.println((String) clientName + " Connected...");

            nameOfClient = clientName;

            while (true) {

                try {
                    //read from client...
                    Object cMsg = objectInputStream.readObject();

                    if (cMsg == null) {
                        break;
                    }

                    else{
                        System.out.println(clientName + " : " + (String) cMsg);

                        //System.out.print(serverName + " : ");
                        String serverMsg = scanner.nextLine();

                        //send to client...
                        objectOutputStream.writeObject(serverMsg);
                    }

                } catch (Exception e) {
                    System.out.println(clientName + " Disconnected...");
                    System.out.println(serverName + " Disconnected...");
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println(nameOfServer + " Disconnected...");
            System.out.println(nameOfClient + " Disconnected...");
        }

    }
}
