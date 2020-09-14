package chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;


/**
 * Headless Server
 */
public class ChatServer extends Thread implements ClientHandler.ServerNotifier {

    private final HostApplication hostApplication;
    private final ServerSocket serverSocket;
    private final String serverID;
    private Socket client;
    private HashSet<ClientHandler> clientList;

    public interface HostApplication{
        void isRunning(boolean status);
    }

    @Override
    public void onMessageReceived(String message) {
        notifyClients(message);
    }

    public ChatServer(String ID, HostApplication hostApplication, int portNo) throws IOException {
        this.hostApplication = hostApplication;
        serverSocket = new ServerSocket(portNo);
        System.out.println("New Server with Port No: "+ portNo + " has been created");
        serverID = ID;
        clientList = new HashSet<>();
    }

    @Override
    public void run() {
        try {
            client = serverSocket.accept();
            ClientHandler newClient = new ClientHandler(client, this);
            newClient.notifyClient("Welcome to the " + serverID+ " chat server!");
            newClient.start();
            clientList.add(newClient);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void notifyClients(String serverMessage) {
        for (ClientHandler clientHandler : clientList) {
            clientHandler.notifyClient(serverMessage);
        }
    }
}
