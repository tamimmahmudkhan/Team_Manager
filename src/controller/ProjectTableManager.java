package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import login.SessionManager;
import model.project.Project;
import model.project.ProjectManager;
import model.staff.Position;
import model.teams.TeamManager;
import model.teams.Teams;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectTableManager implements Initializable{

    @FXML
    private TableView<Project> projectWindow;

    @FXML
    private TableColumn<Project, String> nameCol;
    @FXML
    private TableColumn<Project, String> statusCol;
    @FXML
    private TableColumn<Project, String> teamCol;

    @FXML
    private Button addButton;

    @FXML
    private TextField nameText;

    @FXML
    private TextField statusText;

    @FXML
    private ComboBox<Teams> teamBox;

    private final ProjectManager projectManager = new ProjectManager();
    private final TeamManager teamManager = new TeamManager();

    @Override
    public void initialize(URL arg0, ResourceBundle arg1)
    {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        teamCol.setCellValueFactory(new PropertyValueFactory<>("team"));

        projectWindow.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        projectWindow.setOnMouseClicked(this::onMouseClick);

        teamBox.setItems(teamManager.getTeams());

        if (SessionManager.getEmployeeData().getPosition().equals(Position.Admin.toString())) {
            addButtonMode();
        } else {
            employeeMode();
        }
        refresh();
    }

    private void employeeMode() {
        addButton.setDisable(true);
        nameText.setEditable(false);
        teamBox.setDisable(true);
    }

    private void addButtonMode() {
        addButton.setText("Add New");
        addButton.setOnAction(event -> {
            projectManager.addNewProject(
                    new Project(
                            nameText.getText(),
                            statusText.getText(),
                            teamBox.getSelectionModel().getSelectedItem().getName()
                    )
            );
        });
    }

    public void refresh()
    {
        projectWindow.setItems(null);
        projectWindow.setItems(projectManager.getProjects());
    }

    private void onMouseClick(MouseEvent event)
    {
        if((event.getClickCount() == 2 ) && (event.getSource() instanceof TableView) )
        {
            Project project = projectWindow.getSelectionModel().getSelectedItem();

            nameText.setText(project.getName());
            statusText.setText(project.getStatus());
            teamBox.getSelectionModel().select(teamManager.getTeam(project.getTeam()));

           editButtonMode();
        }
    }

    private void editButtonMode() {
        addButton.setText("Edit");
        addButton.setOnAction(event1 -> {
            projectManager.editProject(nameText.getText(), statusText.getText(), teamBox.getSelectionModel().getSelectedItem().getName());
            addButtonMode();
        });
    }
}
