import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClientMessageSender implements ChatClientMessageSenderThread, Runnable {
    private Socket socket = null;
    private DataInputStream readFromConsoleStream = null;
    private DataOutputStream sendToServerStream = null;
    private ChatClientMessageReceiver messageReceiver = null;
    private boolean active = false;

    public ChatClientMessageSender(final String serverName, final int serverPort) {
        System.out.println("Establishing connection. Please wait ...");
        try {
            socket = new Socket(serverName, serverPort);
            System.out.println("Connected: " + socket);
            readFromConsoleStream = new DataInputStream(System.in);
            sendToServerStream = new DataOutputStream(socket.getOutputStream());
            messageReceiver = new ChatClientMessageReceiver(this);
            new Thread(messageReceiver).start();
            new Thread(this).start();
        } catch (UnknownHostException uhe) {
            System.out.println("Host unknown: " + uhe.getMessage());
        } catch (IOException ioe) {
            System.out.println("Unexpected exception: " + ioe.getMessage());
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public Socket getSocket() {
        return socket;
    }

    public void run() {
        setActive(true);
        while (isActive()) {
            try {
                for(int i = 0; i < 100; i++) {
                    //sendToServerStream.writeUTF(readFromConsoleStream.readLine());
                    sendToServerStream.writeUTF("God of death loves "+i+" apples");
                    sendToServerStream.flush();
                    Thread.sleep(500);
                }
            } catch (IOException ioe) {
                System.out.println("Sending error: " + ioe.getMessage());
                close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void handleMessage(final String msg) {
        if (msg.equals(".bye")) {
            System.out.println("Good bye. Press RETURN to exit ...");
            close();
        } else
            System.out.println(msg);
    }

    public void close() {
        if (isActive()) {
            try {
                if (readFromConsoleStream != null)
                    readFromConsoleStream.close();
                if (sendToServerStream != null)
                    sendToServerStream.close();
                if (socket != null)
                    socket.close();
            } catch (IOException ioe) {
                System.out.println("Error closing ...");
            }
            messageReceiver.close();
            messageReceiver.setActive(false);
        }
    }

    public static void main(final String[] args) {

            new ChatClientMessageSender("131.159.218.242", 8000);
    }
}
 
