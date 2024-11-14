import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

class Counter  {
  private int count = 0;
  
  public int inc() {
    return count++;
  }
}

public class TestItThread extends Thread {
  private SynchronizedLdcWrapper slw;
  private Socket client;

  public TestItThread(SynchronizedLdcWrapper slw, Socket client) {
    this.slw = slw;
    this.client = client;
  }

  private static String askString(BufferedReader in, PrintWriter out, String prompt) throws IOException {
    out.print(prompt);
    out.flush();
    return in.readLine();
  }

  @Override
  public void run() {
    try {
      BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
      PrintWriter out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));

      String command;

      boolean exit = false;

      while (!exit) {
        command = in.readLine(); //askString(in, out, "> ");
        if (command == null)
          break;

        out.println("LabelContainer");

        if (command == null || command.equals("exit")) {
          /* Exit program */
          exit = true;
        } else if (command.startsWith("add ")) {
          /* add a new document */
          String titleAndText = command.substring(4);

          /* title and text separated by : */
          int separator = titleAndText.indexOf(':');
          String title = titleAndText.substring(0, separator);
          String text = titleAndText.substring(separator + 1);

          slw.appendDocument(new LinkedDocument(title, "", "", null, null, text, title));
          //System.out.println(title+" added");
        } else if (command.startsWith("list")) {
          /* list all document in collection */

          slw.forEach(doc -> {
            out.println(doc.getTitle());
          });
        } else if (command.startsWith("query ")) {
          /* query on the documents in the collection */
          String query = command.substring(6);
          
          Counter i = new Counter();

          slw.query(query, (doc, relevance) -> {
            out.println(i.inc() + ". " + doc.getTitle() + "; Relevanz: " + relevance);
          });

          out.println();
        } else if (command.startsWith("count ")) {
          /* print the count of a word in each document */
          String word = command.substring(6);

          slw.forEach(doc -> {
            WordCountsArray docWordCounts = doc.getWordCounts();

            int count = docWordCounts.getCount(docWordCounts.getIndexOfWord(word));

            /* -1 and 0 makes a difference! */
            if (count == -1) {
              out.println(doc.getTitle() + ": gar nicht.");
            } else {
              out.println(doc.getTitle() + ": " + count + "x ");
            }
          });
        } else if (command.startsWith("pageRank")) {

          slw.pageRank((doc, pageRank) -> {
            out.println(doc.getTitle() + "; PageRank: " + pageRank);
          });
        } else if (command.startsWith("crawl")) {
          slw.crawl();
        } else if(command.startsWith("ping")){

          out.println("pong");
          out.println("next pong");
        }

        out.flush();
      }
    } catch (IOException exp) {
    } finally {
      try {
        client.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
