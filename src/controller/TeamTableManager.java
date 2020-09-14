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

public class TeamTableManager implements Initializable {
    @FXML
    private TableView<Teams> teamTable;

    @FXML
    private TableColumn<Teams, String> name;
    @FXML
    private TableColumn<Teams, Integer> members;
    @FXML
    private TableColumn<Teams, String> project;


    @FXML
    private Button addButton;

    @FXML
    private TextField nameText;

    @FXML
    private TextField memberText;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField portNoText;

    @FXML
    private ComboBox<Project> projectBox;

    private final ProjectManager projectManager = new ProjectManager();
    private final TeamManager teamManager = new TeamManager();


    @Override
    public void initialize(URL arg0, ResourceBundle arg1)
    {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        members.setCellValueFactory(new PropertyValueFactory<>("members"));
        project.setCellValueFactory(new PropertyValueFactory<>("project"));

        teamTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        teamTable.setOnMouseClicked(this::onMouseClick);

        if (SessionManager.getEmployeeData().getPosition().equals(Position.Admin.toString())) {
            addButtonMode();
        }else {
            employeeMode();
        }

        refresh();
    }

    private void employeeMode() {
        nameText.setEditable(false);
        memberText.setEditable(false);
        portNoText.setEditable(false);
        addButton.setDisable(true);
        deleteButton.setDisable(true);
        projectBox.setDisable(true);
    }

    public void refresh()
    {
        teamTable.setItems(null);
        teamTable.setItems(teamManager.getTeams());
    }


    private void addButtonMode() {
        addButton.setText("Add New");
        addButton.setOnAction(event -> {
            teamManager.addTeam(
                    new Teams(
                            nameText.getText(),
                            Integer.parseInt(memberText.getText()),
                            projectBox.getSelectionModel().getSelectedItem().getName(),
                            Integer.parseInt(portNoText.getText())
                    )
            );
        });
    }

    private void onMouseClick(MouseEvent event)
    {
        if((event.getClickCount() == 2 ) && (event.getSource() instanceof TableView) )
        {
            Teams team = teamTable.getSelectionModel().getSelectedItem();

            nameText.setText(team.getName());
            memberText.setText(String.valueOf(team.getMembers()));
            projectBox.getSelectionModel().select(projectManager.getProject(team.getProject()));
            deleteButton.setDisable(false);
            deleteButton.setOnAction(event1 ->{
                teamManager.removeTeam(team);
            });
        }
    }
}
