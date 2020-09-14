 package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import login.SessionManager;
import model.staff.EmployeeData;
import model.staff.Position;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

 public class EmployeeDisplayController implements Initializable
{
	/**
	 * Controller strictly in charge of displaying full employee information. Responsible for launching EditMenu.
	 */
	@FXML
	private Label nameLabel;
	@FXML
	private Label dobLabel;
	@FXML
	private Label positionLabel;
	@FXML
	private Label workDayLabel;
	@FXML
	private Label absentDayLabel;
	@FXML
	private Label projectLabel;
	@FXML
	private Label teamLabel;
	@FXML
	private VBox employeeDisplay;
	@FXML
	private Button editButton;

	private EditMenuController editMenuController;

	
	@FXML
	private StaffWindowController mainController;
	
	public void init(StaffWindowController controller) 
	{
		mainController= controller;
	}

	// Event Listener on Button[#editButton].onAction
	@FXML
	public void editMenu(ActionEvent event) 
	{
		HBox parent = (HBox) employeeDisplay.getParent();
		parent.getChildren().remove(employeeDisplay);
		parent.getChildren().add(0, displayEditor());
		if (editMenuController != null)
		{
			mainController.setEditMenu(editMenuController);
		}
	}
	
	private Node displayEditor()
	{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EditMenu.fxml"));

		Node pane = null;
		try {
			pane = loader.load();
			editMenuController = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
		}
		editMenuController.injectMainController(mainController);
		System.out.println(editMenuController);
		return pane;
	}
	
	public void setLabels(EmployeeData data) 
	{
		nameLabel.setText("Name: " + data.getFname() + " " + data.getLname());
		dobLabel.setText("Date of Birth: " + data.getDob());
		positionLabel.setText("Position: " + data.getPosition());
		workDayLabel.setText("Worked Days: " + data.getWorkDays());
		absentDayLabel.setText("Absent Days: " + data.getAbsentDays());
		projectLabel.setText("Project: " + data.getProject());
		teamLabel.setText("Team: "+ data.getTeam());
	}

	public EditMenuController getEditMenuController()
	{
		return editMenuController;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (!SessionManager.getEmployeeData().getPosition().equals(Position.Admin)) {
			editButton.setDisable(true);
		}
	}
}
