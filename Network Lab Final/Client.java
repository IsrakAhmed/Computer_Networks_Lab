import java.net.Socket;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;


public class Client {
    
    public static void main(String[] args){
        try(
            Socket socket = new Socket("192.168.25.98", 3000);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            Scanner scanner = new Scanner(System.in);) {

            System.out.println("Client Connected...");    

            while(true){

                System.out.print("Enter your query : ");
                String clientQuery = scanner.nextLine();

                objectOutputStream.writeObject(clientQuery);

                Object serverResponse = objectInputStream.readObject();

                System.out.println("Server : " + serverResponse);

            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
