package controller;

import chat.server.ChatServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.Window;
import login.SessionManager;
import model.staff.Position;
import model.staff.StaffManager;
import model.teams.TeamManager;
import model.teams.Teams;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable, ChatServer.HostApplication {
	/**
	 * Handles Login to application and addition of new employee.
	 */
	
	@FXML
	private TextField emailText;
	
	@FXML
	private PasswordField passText;
	
	@FXML
	private ComboBox<Position> positionBox;
	@FXML
	private Label promptLabel;
	
	private StaffManager manager = new StaffManager();

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) 
	{
		ObservableList<Position> list = FXCollections.observableArrayList();
		list.addAll(Position.Admin, Position.Employee);
		positionBox.setItems(list);

		TeamManager teamManager = new TeamManager();
		try {
			for (Teams teams : teamManager.getTeams()) {
					new ChatServer(teams.getName(), this, teams.getPort_no()).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	private void login(ActionEvent event) {
		Window window = ((Button)event.getSource()).getScene().getWindow();

//		launchWindow((Stage)window,	"/view/StaffWindow.fxml"); // DELETE DIS
		if (positionBox.getSelectionModel().getSelectedItem() == Position.Admin) {
			if (manager.checkEmployee(emailText.getText(), passText.getText()))
			{
				SessionManager.setEmployeeData(manager.getEmployee(emailText.getText(), passText.getText()));
				launchWindow((Stage)window,	"/view/MainWindow.fxml");
			}else {
				promptLabel.setTextFill(Paint.valueOf(Color.color(1,0,0).toString()));
				promptLabel.setText("Unauthorized User: Admin");
			}
		}else {
			if (manager.checkEmployee(emailText.getText(), passText.getText()))
			{
				SessionManager.setEmployeeData(manager.getEmployee(emailText.getText(), passText.getText()));
				launchWindow((Stage)window, "/view/MainWindow.fxml");
			}else {
				promptLabel.setText("Unauthorized User: Employee");
			}
		}
	}
	
	@FXML
	private void newAccount(ActionEvent event)
	{
		Window window = ((Button)event.getSource()).getScene().getWindow();
		launchWindow((Stage) window, "/view/RegistrationPanel.fxml");
	}

	private void launchWindow(Stage stage, String fxml) 
	{
		stage.close();
		
		try {
			Parent root = FXMLLoader.load(getClass().getResource(fxml));
			Scene scene = new Scene(root, 1280, 720);
//			scene.getStylesheets().add(getClass().getResource("/view/themes/modena_dark.css").toExternalForm());

			Stage newStage = new Stage();

			newStage.setTitle("Inventory Management");
			newStage.setScene(scene);
			newStage.setResizable(false);

			newStage.show();
		} catch (IOException e) {
			// DONT READ THIS
			e.printStackTrace();
		}
	}

	@Override
	public void isRunning(boolean status) {
		if (status) {
			promptLabel.setText("Chat Server running.");
		} else {
			promptLabel.setText("Chat Server Stopped");
		}
	}
}
