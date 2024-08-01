import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


/*
    Socket Programming - SIngle-Client without Threading - Half Duplex
*/


public class Server {
    public static void main(String[] args) throws Exception {

        try( ServerSocket serverSocket = new ServerSocket(22222); ) {

            System.out.println("Server Started...");

            Socket socket = serverSocket.accept();
            System.out.println("Client Connected...");

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter Server Name : ");
            String serverName = scanner.nextLine();
            System.out.println(serverName + " Connected...");

            //sent name to client...
            objectOutputStream.writeObject(serverName);

            //receive name from client...
            Object clientName = objectInputStream.readObject();
            System.out.println((String) clientName + " Connected...");

            while (true) {

                try {
                    //read from client...
                    Object cMsg = objectInputStream.readObject();

                    if (cMsg.equals("exit")) {
                        System.out.println(clientName + " Disconnected...");
                        System.out.println(serverName + " Disconnected...");
                        break;
                    }

                    else{
                        System.out.println(clientName + ": " + (String) cMsg);

                        System.out.print(serverName + " : ");
                        String serverMsg = scanner.nextLine();

                        //send to client...
                        objectOutputStream.writeObject(serverMsg);
                    }

                } catch (Exception e) {
                    throw new Exception(e);
                }
            }

        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
