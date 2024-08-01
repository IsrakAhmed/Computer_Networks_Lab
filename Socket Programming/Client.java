import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        System.out.println("Client Started...");

        try(
            Socket socket = new Socket("127.0.0.1", 22222);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            Scanner scanner = new Scanner(System.in);) {

            System.out.println("Client Connected...");

            //receive name from server...
            Object serverName = objectInputStream.readObject();
            System.out.println((String) serverName + " Connected...");

            System.out.print("Enter Client Name : ");
            String clientName = scanner.nextLine();
            System.out.println(clientName + " Connected...");

            //sent name to server...
            objectOutputStream.writeObject(clientName);

            while (true) {
                try{
                    System.out.print(clientName + " : ");
                    String message = scanner.nextLine();

                    //sent to server...
                    objectOutputStream.writeObject(message);

                    if(message.equals("exit")) {
                        System.out.println(clientName + " Disconnected...");
                        break;
                    }

                    else {
                        //receive from server...
                        Object fromServer = objectInputStream.readObject();
                        System.out.println(serverName + " : " + (String) fromServer);
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
