package model.project;

import database.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProjectManager {

    private PreparedStatement statement;
    private ResultSet set;

    private ObservableList<Project> tableData;
    private final String INSERT_QUERY_STRING = "insert into projects(name, status, team) values(?,?,?)";
    private final String REMOVE_QUERY_STRING = "delete from projects where name=?";
    private final String SELECT_ALL_QUERY_STRING = "select * from projects";
    private final String EDIT_QUERY_STRING = "update projects set status=?, team=? where name=?";
    private final String GET_QUERY_STRING = "select * from projects where name=?";


    public ObservableList<Project> getProjects() {
        tableData = FXCollections.observableArrayList();

        try (Connection database = dbConnection.getConnection()) {
            ResultSet resultSet = database.createStatement().executeQuery(SELECT_ALL_QUERY_STRING);

            while (resultSet.next()) {
                tableData.add(
                        new Project(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3))
                );
            }

            return tableData;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection to db failed: ProjectManager");
            return null;
        }
    }

    public Project getProject(String name) {
        try(Connection database = dbConnection.getConnection()) {
            statement = database.prepareStatement(GET_QUERY_STRING);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();

            Project project = new Project(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));

            statement.close();
            database.close();
            return project;
        } catch (SQLException | NullPointerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public boolean editProject(String name, String status, String team) {
        // 1) status 2) team 3) name
        try(Connection database = dbConnection.getConnection()) {
            statement = database.prepareStatement(EDIT_QUERY_STRING);
            statement.setString(1, status);
            statement.setString(2, team);
            statement.setString(2, name);

            statement.execute();
            statement.close();
            database.close();
            return true;
        } catch (SQLException | NullPointerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }

    }

    public boolean addNewProject(Project data)
    {
            String query = INSERT_QUERY_STRING;
            try(Connection database = dbConnection.getConnection()) {
                statement = database.prepareStatement(query);
                statement.setString(1, data.getName());
                statement.setString(2, data.getName());
                statement.setString(2, data.getTeam());

                statement.execute();
                statement.close();
                database.close();
                return true;
            } catch (SQLException | NullPointerException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return false;
            }
    }

    public boolean removeProject(Project data) {
        try(Connection database = dbConnection.getConnection()) {
            statement = database.prepareStatement(REMOVE_QUERY_STRING);
            statement.setString(1, data.getName());
            statement.execute();
            statement.close();
            database.close();
            return true;
        } catch (SQLException | NullPointerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
}
