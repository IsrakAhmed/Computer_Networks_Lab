import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.net.InetAddress;


public class Server {
    public static void main(String[] args){

        try(ServerSocket serverSocket = new ServerSocket(3000);) {
            
            System.out.println("Server Started...");

            while(true){
                Socket socket = serverSocket.accept();
                System.out.println("Client Connected...");

                new ServerThread(socket);
            }

        } catch (Exception e) {
            System.out.println("Server Disconnected...");
        }

    }
}

class ServerThread implements Runnable {

    Socket clientSocket;
    Thread thread;

    ServerThread(Socket clientSocket) {
        this.clientSocket = clientSocket;
        thread = new Thread(this);
        thread.start();
    }

    public void run(){

        try {

            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());

            while(true){

                Object clientMessage = objectInputStream.readObject();

                if(clientMessage.equals("Hi")){
                    objectOutputStream.writeObject("Hello");
                }

                else if(clientMessage.equals("Date")){
                    objectOutputStream.writeObject(LocalDateTime.now().toLocalDate().toString());
                }

                else if(clientMessage.equals("Time")){
                    objectOutputStream.writeObject(LocalDateTime.now().toLocalTime().toString());
                }

                else if(clientMessage.equals("IP")){
                    objectOutputStream.writeObject(java.net.InetAddress.getLocalHost().getHostAddress());
                }

                else{
                    objectOutputStream.writeObject("Unknown");
                }

            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }
}
