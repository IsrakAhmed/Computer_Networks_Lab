import java.io.ObjectInputStream;

public class ReaderThread implements Runnable {

    ObjectInputStream objectInputStream;
    Thread thread;
    Object userName;
    private volatile boolean running = true;

    ReaderThread(ObjectInputStream objectInputStream, Object userName) {
        this.objectInputStream = objectInputStream;
        this.userName = userName;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (running) {
            try {

                //read message...
                Object message = objectInputStream.readObject();
                System.out.println(userName + " : " + (String) message);

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