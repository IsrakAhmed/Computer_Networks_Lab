import java.io.ObjectOutputStream;
import java.util.Scanner;

public class WriterThread implements Runnable {

    ObjectOutputStream objectOutputStream;
    Thread thread;
    Scanner scanner;
    Object userName;
    private volatile boolean running = true;

    WriterThread(ObjectOutputStream objectOutputStream, Scanner scanner, Object userName) {
        this.objectOutputStream = objectOutputStream;
        this.scanner = scanner;
        this.userName = userName;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (running) {
            try {

                String message = scanner.nextLine();

                //send message...
                objectOutputStream.writeObject(message);

            } catch (Exception e) {
                //System.out.println(userName + " Disconnected...");
                //break;
            }
        }
    }

    public void shutdown() {
        running = false;
    }
}