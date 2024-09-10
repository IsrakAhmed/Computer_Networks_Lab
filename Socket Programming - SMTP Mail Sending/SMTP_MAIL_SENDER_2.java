import java.io.*;
import java.time.LocalDateTime;
import javax.net.ssl.*;
import java.util.*;

class SMTP_MAIL_SENDER_2 {

  private static DataOutputStream dos;
  public static BufferedReader br;

  public static void main(String argv[]) throws Exception {
    // Read credentials from a text file
    BufferedReader credReader = new BufferedReader(new FileReader("credentials.txt"));
    String user = credReader.readLine(); // The first line is the email
    String pass = credReader.readLine(); // The second line is the password
    credReader.close();
    
    // Encode the username and password
    String username = new String(Base64.getEncoder().encode(user.getBytes()));
    String password = new String(Base64.getEncoder().encode(pass.getBytes()));

    // Take receiver's email as input from the user
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter receiver's email: ");
    String receiverEmail = scanner.nextLine();

    // Create SSL socket
    SSLSocket s = (SSLSocket) SSLSocketFactory.getDefault().createSocket("smtp.gmail.com", 465);
    dos = new DataOutputStream(s.getOutputStream());
    br = new BufferedReader(new InputStreamReader(s.getInputStream()));

    // SMTP conversation
    send("EHLO smtp.gmail.com\r\n");
    for (int i = 0; i < 8; i++) {
      System.out.println("SERVER: " + br.readLine());
    }

    send("AUTH LOGIN\r\n");
    System.out.println("SERVER: " + br.readLine());

    send(username + "\r\n");
    System.out.println("SERVER: " + br.readLine());

    send(password + "\r\n");
    System.out.println("SERVER: " + br.readLine());

    send("MAIL FROM:<" + user + ">\r\n");
    System.out.println("SERVER: " + br.readLine());

    send("RCPT TO:<" + receiverEmail + ">\r\n");
    System.out.println("SERVER: " + br.readLine());

    send("DATA\r\n");
    System.out.println("SERVER: " + br.readLine());

    send("FROM: " + user + "\r\n");
    send("TO: " + receiverEmail + "\r\n");
    send("Subject: Email test " + LocalDateTime.now() + "\r\n");
    send("THIS IS A TEST EMAIL. THANK YOU\r\n");
    send(".\r\n");
    System.out.println("SERVER: " + br.readLine());

    send("QUIT\r\n");
    System.out.println("SERVER: " + br.readLine());
  }

  private static void send(String s) throws Exception {
    dos.writeBytes(s);
    Thread.sleep(1000);
    System.out.println("CLIENT: " + s);
  }
}
