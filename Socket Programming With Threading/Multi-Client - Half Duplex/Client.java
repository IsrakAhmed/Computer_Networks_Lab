import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        System.out.println("Client Started...");

        Object nameOfServer = "Server";
        Object nameOfClient = "Client";


        try(
            Socket socket = new Socket("192.168.1.104", 22222);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            Scanner scanner = new Scanner(System.in);) {

            System.out.println("Client Connected...");

            //receive name from server...
            Object serverName = objectInputStream.readObject();
            System.out.println((String) serverName + " Connected...");

            nameOfServer = serverName;

            System.out.print("Enter Client Name : ");
            String clientName = scanner.nextLine();
            System.out.println(clientName + " Connected...");

            nameOfClient = clientName;

            //sent name to server...
            objectOutputStream.writeObject(clientName);

            while (true) {
                try{
                    //System.out.print(clientName + " : ");
                    String message = scanner.nextLine();

                    try {
                        //sent to server...
                        objectOutputStream.writeObject(message);
                    } catch (Exception e) {
                        break;
                    }

                    //receive from server...
                    Object fromServer = objectInputStream.readObject();

                    if(message == null) {
                        break;
                    }

                    System.out.println(serverName + " : " + (String) fromServer);


                } catch (Exception e) {
                    System.out.println(serverName + " Disconnected...");
                    System.out.println(clientName + " Disconnected...");
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println(nameOfServer + " Disconnected...");
            System.out.println(nameOfClient + " Disconnected...");
        }
    }
}


