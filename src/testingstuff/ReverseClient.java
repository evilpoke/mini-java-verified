package testingstuff;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ReverseClient {

  public static void test(){

  }

  public static void main(String[] args) throws UnknownHostException, IOException {
    Socket socket = new Socket("127.0.0.1", 8000);

    socket.toString();
    try {

      while(true){

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

        String input = Terminal.ask("Bitte einen String zum Umdrehen eingeben: ");

        out.println(input);
        out.flush();

        //while (in.ready()){

        do{
          String reversed = in.readLine();
          System.out.println(reversed);
        } while(in.ready());

         // in.toString();
        //}
       // reversed = in.readLine();
       // System.out.println(reversed);
      }

    } finally {
      socket.close();
    }

  }

}
