package chat.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Clientside Socket. Displays messages from server to chatWindow. Delegates input from InputHandler to Server.
 */
public class ClientSocket extends Thread {

    Socket server;
    ChatHostView hostView;
    BufferedReader bufferedReader;
    PrintWriter printWriter;

    public interface ChatHostView{
        void onMessageReceived(String message);
    }

    public ClientSocket(ChatHostView chatHostView, int port) throws IOException {
        System.out.println("ClientSocket created with port no: " + port);
        server = new Socket("localhost", port);
        hostView = chatHostView;
    }

    @Override
    public void run() {

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(server.getInputStream()));
            printWriter = new PrintWriter(server.getOutputStream(), true);

            while (true) {
                String message = bufferedReader.readLine();

                if (!message.isEmpty()) {
                    hostView.onMessageReceived(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        printWriter.println(message);
    }
}
