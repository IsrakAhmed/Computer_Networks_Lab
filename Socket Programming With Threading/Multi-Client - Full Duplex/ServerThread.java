import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread implements Runnable {

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

        Object nameOfClient = "Client";

        WriterThread writerThread = null;
        ReaderThread readerThread = null;

        try {

            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            Scanner scanner = new Scanner(System.in);

            //sent name to client...
            objectOutputStream.writeObject(serverName);

            //receive name from client...
            Object clientName = objectInputStream.readObject();
            System.out.println((String) clientName + " Connected...");

            nameOfClient = clientName;

            readerThread = new ReaderThread(objectInputStream, clientName);
            writerThread = new WriterThread(objectOutputStream, scanner, clientName);

            System.out.println("Start Chatting...");

        } catch (Exception e) {
            System.out.println(nameOfClient + " Disconnected...");

            if (writerThread != null) writerThread.shutdown();
            if (readerThread != null) readerThread.shutdown();
        }

    }
}
