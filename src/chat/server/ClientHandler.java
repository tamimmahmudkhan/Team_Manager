package chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Relays messages from client to server and vice versa;
 */

public class ClientHandler extends Thread {

    private Socket client;
    private ServerNotifier notifier;
    private PrintWriter serverMessage;

    private BufferedReader clientMessage;

    interface ServerNotifier{
        void onMessageReceived(String message);
    }

    public ClientHandler(Socket socket, ServerNotifier serverNotifier) throws IOException {
        client = socket;
        notifier = serverNotifier;

        clientMessage = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        serverMessage = new PrintWriter(socket.getOutputStream(), true);
    }

    //Waits for input from client and notifies server
    @Override
    public void run() {

        while (true) {
            try {
                String input = clientMessage.readLine();

                if (!input.isEmpty()) {
                    notifier.onMessageReceived(input);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //Server messages relayed to Client
    public void notifyClient(String message) {
        serverMessage.println(message);
    }
}
