package controller;

import chat.client.ClientSocket;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import login.SessionManager;
import model.staff.EmployeeData;
import model.teams.TeamManager;
import model.teams.Teams;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatWindowController implements Initializable, ClientSocket.ChatHostView  {

    @FXML
    private TextArea chatWindow;

    @FXML
    private TextField chatInput;

    @FXML
    private Button sendButton;

    ClientSocket clientSocket;

    StringBuilder messageHistory;
    private EmployeeData data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        data = SessionManager.getEmployeeData();
        TeamManager teamManager = new TeamManager();
        System.out.println(data + " " + data.getTeam());
        Teams teams = teamManager.getTeam(data.getTeam());

        try {
            clientSocket = new ClientSocket(this, teams.getPort_no());
            messageHistory = new StringBuilder();

            sendButton.setOnAction(event -> {
                clientSocket.sendMessage(data.getFname() + " " + data.getLname() + ": "+  chatInput.getText());
            });

            clientSocket.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMessageReceived(String message) {
        if (messageHistory.length() != 0) {
            messageHistory.append("\n");
        }
        messageHistory.append(message);
        chatWindow.setText(messageHistory.toString());
    }
}
