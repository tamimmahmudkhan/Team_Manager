package model.teams;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Teams {

    private StringProperty name;
    private IntegerProperty members;
    private StringProperty project;
    private IntegerProperty port_no;

    public Teams(String name, int members, String project, int portNo) {
        this.name = new SimpleStringProperty(name);
        this.members = new SimpleIntegerProperty(members);
        this.project = new SimpleStringProperty(project);
        this.port_no = new SimpleIntegerProperty(portNo);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getMembers() {
        return members.get();
    }

    public void setMembers(int members) {
        this.members.set(members);
    }

    public String getProject() {
        return project.get();
    }

    public void setProject(String project) {
        this.project.set(project);

    }

    public int getPort_no() {
        return port_no.get();
    }

    public void setPort_no(int port_no) {
        this.port_no.set(port_no);
    }

    @Override
    public String toString() {
        return name.get();
    }
}
