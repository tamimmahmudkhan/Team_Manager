package model.project;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Project {

    private StringProperty name;
    private StringProperty status;
    private StringProperty team;

    public Project(String projectName, String statusNow, String assignedTeam) {
        this.name = new SimpleStringProperty(projectName);
        this.status = new SimpleStringProperty(statusNow);
        this.team = new SimpleStringProperty(assignedTeam);
    }

    public String getName() {
        return name.get();
    }

    public String getStatus() {
        return status.get();
    }

    public String getTeam() {
        return team.get();
    }

    public void setName(String name) {
        this.name = new SimpleStringProperty(name);
    }

    public void setTeam(String team) {
        this.team = new SimpleStringProperty(team);
    }

    @Override
    public String toString() {
        return name.get();
    }
}
