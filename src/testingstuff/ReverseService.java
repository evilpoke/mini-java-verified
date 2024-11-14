package testingstuff;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ReverseService {

  public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = new ServerSocket(8000);
    System.out.println("debug: a");
      while (true) {
        try {
        Socket client = serverSocket.accept();
        System.out.println("debug: a1");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter socketWriter = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
        System.out.println("debug: a2");
        String line;
        do {
          System.out.println("debug: b");
          line = bufferedReader.readLine();
          if (line == null) // Verbindung geschlossen
            break;
          System.out.println("debug: b2");
          String result = "";
         // System.out.println(line);
          for (int i = 0; i < line.length(); i++)
            result += line.charAt(line.length() - i - 1);

          socketWriter.println(result);
          socketWriter.println(result);
          socketWriter.println(result);
         // socketWriter.println("this is a test");
          socketWriter.flush();
        } while (!line.equals("exit"));

        client.close();

    } catch (Exception e){
    //  serverSocket.close();
    //  serverSocket = null;
    //  serverSocket = new ServerSocket(8000);
    }
  }
  }
}
