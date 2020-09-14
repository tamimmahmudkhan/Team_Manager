package model.teams;

import database.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamManager {

    private PreparedStatement statement;
    private ResultSet set;

    private ObservableList<Teams> tableData;
    private final String INSERT_QUERY_STRING = "insert into teams(name, members, project, port_no) values(?,?,?,?)";
    private final String REMOVE_QUERY_STRING = "delete from teams where name=?";
    private final String SELECT_ALL_QUERY_STRING = "select * from teams";
    private final String UPDATE_QUERY_STRING = "update teams set members=? where name=?";
    private String SEARCH_QUERY_STRING = "select * from teams where name=?";


    public ObservableList<Teams> getTeams() {
        tableData = FXCollections.observableArrayList();

        try (Connection database = dbConnection.getConnection()) {
            ResultSet resultSet = database.createStatement().executeQuery(SELECT_ALL_QUERY_STRING);

            while (resultSet.next()) {
                tableData.add(
                        new Teams(
                                resultSet.getString(1),
                                resultSet.getInt(2),
                                resultSet.getString(3),
                                resultSet.getInt(4)
                ));
            }

            return tableData;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection to db failed: ProjectManager");
            return null;
        }
    }

    public Teams getTeam(String name) {
        Teams teams = null;

        try(Connection database = dbConnection.getConnection()) {
            statement = database.prepareStatement(SEARCH_QUERY_STRING);
            statement.setString(1, name);
            ResultSet set = statement.executeQuery();

            teams = new Teams(set.getString(1), set.getInt(2), set.getString(3), set.getInt(4));

            statement.close();
            database.close();
            return teams;
        } catch (SQLException | NullPointerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    public boolean addTeam(Teams data)
    {
        try(Connection database = dbConnection.getConnection()) {
            statement = database.prepareStatement(INSERT_QUERY_STRING);
            statement.setString(1, data.getName());
            statement.setInt(2, data.getMembers());
            statement.setString(3, data.getProject());
            statement.setInt(4, data.getPort_no());

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

    public boolean removeTeam(Teams data) {
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

    public boolean addMember(Teams data) {
        try(Connection database = dbConnection.getConnection()) {
            statement = database.prepareStatement(UPDATE_QUERY_STRING);
            int member  = data.getMembers() + 1;
            statement.setInt(1, member);
            statement.setString(2, data.getName());
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

    public boolean removeMember(Teams data) {
        try(Connection database = dbConnection.getConnection()) {
            statement = database.prepareStatement(UPDATE_QUERY_STRING);
            int member  = data.getMembers() - 1;
            if (member == -1) {
                member = 0;
            }
            statement.setInt(1, member);
            statement.setString(2, data.getName());
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
