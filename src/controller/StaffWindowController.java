package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import model.staff.EmployeeData;
import model.staff.StaffManager;

import java.net.URL;
import java.util.ResourceBundle;

public class StaffWindowController implements Initializable {
	/**
	 * Main Controller classs for the base window of the application.Responsible for loading and displaying the fxml files for employee edit and display.
	 **/
	@FXML
	private TableManager tableWindowController;

	@FXML
	private HBox parentNode;

	@FXML
	private EmployeeDisplayController employeeDisplayController;


	private EditMenuController editMenu;

	private StaffManager manager;

	FXMLLoader loader = new FXMLLoader();

	private boolean isEditMode;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println(employeeDisplayController);
		employeeDisplayController.init(this);
		tableWindowController.init(this);
	}

	public void setEditMenu(EditMenuController editMenu) {
		this.editMenu = editMenu;
		isEditMode = true;
	}

	public void setEmployeeDisplayController(EmployeeDisplayController controller)
	{
		this.employeeDisplayController = controller;
		isEditMode = false;
	}


	public void displayData(EmployeeData data) {
		if (isEditMode) {
			editMenu.populateFields(data);
		} else {
			employeeDisplayController.setLabels(data);
			System.out.println(data.toString());
		}
	}

	public void refreshTable()
	{
		tableWindowController.refresh();
	}

}
