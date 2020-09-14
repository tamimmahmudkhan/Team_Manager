package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.project.Project;
import model.project.ProjectManager;
import model.staff.EmployeeData;
import model.staff.Position;
import model.staff.StaffManager;
import model.teams.TeamManager;
import model.teams.Teams;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class EditMenuController implements Initializable
{
	/**
	 * Controller for the EditMenu.fxml. Handles editing of employee in database through the UI.
	 */
	@FXML
	private TextField fNameText;
	@FXML 
	private TextField lNameText;
	@FXML
	private DatePicker dobSelect;
	@FXML
	private VBox editMenu;

	private EmployeeData workingEmployeeData;
	
	private final StaffManager manager = new StaffManager();
	private final TeamManager teamManager = new TeamManager();
	private final ProjectManager projectManager = new ProjectManager();
	
	@FXML
	private ComboBox<Position> positionBox;

	@FXML
	private ComboBox<Teams> teamSelector;

	@FXML
	private ComboBox<Project> projectSelector;


	private StaffWindowController mainController;

	public void injectMainController(StaffWindowController mainController)
	{
		this.mainController = mainController;
	}
	
	@FXML
	private void previousMenu()
	{
		HBox parent = (HBox) editMenu.getParent();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/EmployeeDisplay.fxml"));
		try {
			Node newNode = loader.load();
			EmployeeDisplayController controller = loader.getController();
			controller.init(mainController);
			mainController.setEmployeeDisplayController(controller);
			parent.getChildren().remove(0);
			parent.getChildren().add(0, newNode);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void populateFields(EmployeeData data) 
	{
		workingEmployeeData = data;
		fNameText.setText(data.getFname());
		lNameText.setText(data.getLname());
		dobSelect.getEditor().setText(data.getDob());
		positionBox.getSelectionModel().select(Position.valueOf(data.getPosition()));
		projectSelector.getSelectionModel().select(projectManager.getProject(data.getProject()));
		teamSelector.getSelectionModel().select(teamManager.getTeam(data.getTeam()));
	}
	
	@FXML
	public void editEmployee() 
	{
		if (manager.isEmployee(workingEmployeeData))
		{
			EmployeeData editedEmployeeData = new EmployeeData.Builder()
					.fname(fNameText.getText())
					.lname(lNameText.getText())
					.dob(dobSelect.getEditor().getText())
					.position(positionBox.getSelectionModel().getSelectedItem().toString())
					.project(projectSelector.getSelectionModel().getSelectedItem().getName())
					.team(teamSelector.getSelectionModel().getSelectedItem().getName())
					.email(workingEmployeeData.getEmail())
					.password(workingEmployeeData.getPassword())
					.build();
			if (workingEmployeeData.getTeam() == null || workingEmployeeData.getTeam().isEmpty()) {
				teamManager.addMember(teamSelector.getSelectionModel().getSelectedItem());
			}
			manager.updateEmployeeData(editedEmployeeData);
			mainController.refreshTable();
		}
		previousMenu();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<Position> list = FXCollections.observableArrayList();
		list.addAll(Position.Admin, Position.Employee);
		positionBox.setItems(list);
		teamSelector.setItems(teamManager.getTeams());
		projectSelector.setItems(projectManager.getProjects());

	}
}
