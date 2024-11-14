import testingstuff.Terminal;

import java.io.*;
import java.net.Socket;
import java.util.stream.Stream;

/**
 * TestIt-Klasse.
 */
public class FinalTestIt{
    /**
     * Main-Methode
     *
     */
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8000);

        try {

            while(true){
               // System.out.println("Debug aa");
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

                String input = Terminal.ask("> ");
               // System.out.println("Debug a1");
                out.println(input);
                out.flush();
                /*if(input.equals("exit")){
                    break;
                }*/
                //System.out.println("Debug a2");
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
