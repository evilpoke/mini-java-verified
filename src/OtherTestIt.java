

import testingstuff.Terminal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class OtherTestIt{



  public static void main(String[] args) throws IOException {
    Socket socket = new Socket("127.0.0.1", 8000);

    try {

      while(true){
        // System.out.println("Debug aa");
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

        String input = Terminal.ask("> ");

        out.println(input);
        out.flush();

      //String testing = in.readLine();
      //System.out.println(testing);

        do{
          String reversed = in.readLine();
          if(!reversed.equals("> ")) {
            System.out.println(reversed);
          }
        }while (in.ready());
        // System.out.println("Debug a3");
        //Stream<String> result = in.lines();
        //result.forEach(System.out::println);  //<<<intellij's work

      }

    } catch (Exception e){

    }finally {
      socket.close();
    }

  }




}
