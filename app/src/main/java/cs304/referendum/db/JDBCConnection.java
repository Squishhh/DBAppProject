package cs304.referendum.db;

/**
 * Created by b0ss on 2017-03-28.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import cs304.referendum.utils.Voter;
import cs304.referendum.utils.VoterRef;
import cs304.referendum.utils.VotesOn;

public class JDBCConnection {
    private Connection connection;
    private Statement statement;

    public JDBCConnection() throws ClassNotFoundException
    {
        try
        {
            //Class.forName("oracle.jdbc.driver.OracleDriver");
            DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
            String url = "jdbc:oracle:thin:@10.0.2.2:1522:ug";
            this.connection = DriverManager.getConnection(url, "ora_k1b0b", "a12501145");
            this.connection.setAutoCommit(true);
            System.out.println("Connection Passed");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public String getResult() throws SQLException{
        statement = connection.createStatement();

        //INSERT INTO VOTER
        String insert1 = "INSERT INTO voter (UserID, Name, Username, Email)" +
                "VALUES (6, 'Fuck', 'This', 'faiz@shit.ca')";
        statement.executeUpdate(insert1);


        ResultSet rSet = statement.executeQuery("select * from voter");
        System.out.println("Query Finished");
        String name = "";

        while(rSet.next()) {
            System.out.println("Name: " + rSet.getString(2));
            name = rSet.getString(2);
        }

        //statement.close();
        return name;

    }

    //1) SELECT/PROJECT QUERY
    public ArrayList<String> getDescription(String description, Integer year) throws SQLException{
        System.out.println("Starting Selection/Projection Query");

        statement = connection.createStatement();

        String sql = "SELECT r.Description " +
                     "FROM Referendum r, Archive a " +
                     "WHERE r.ArchiveID = a.ArchiveID " +
                     "AND r.Description LIKE '%" + description + "%' AND a.Year > " + year;


        ResultSet rSet = statement.executeQuery(sql);
        ArrayList<String> descriptionResults = new ArrayList<>();
        System.out.println("Parsing Selection/Projection Query");

        while (rSet.next()){
            System.out.println("Description: " + rSet.getString(1));
            descriptionResults.add(rSet.getString(1));
        }
        System.out.println("Finished Selection/Projection Query");
        return descriptionResults;
    }

    //2) JOIN QUERY
    public ArrayList<VoterRef> getVoterRef(Integer startYear, Integer endYear) throws SQLException{
        String sql = "SELECT v.UserName, r.Description, a.Year " +
                     "FROM Voter v " +
                     "INNER JOIN votesOn vo ON vo.UserID = v.UserID " +
                     "INNER JOIN Referendum r ON vo.RefID = r.RefID " +
                     "INNER JOIN Archive a on r.ArchiveID = a.ArchiveID " +
                     "WHERE a.Year > " + startYear + " AND a.Year < " + endYear;
        statement = connection.createStatement();
        ResultSet rSet = statement.executeQuery(sql);
        ArrayList<VoterRef> voterRefResults = new ArrayList<>();

        while (rSet.next()){
            //System.out.println("Description: " + rSet.getString(1));
            String userName = rSet.getString(1);
            String description = rSet.getString(2);
            Integer year = rSet.getInt(3);

            VoterRef vr = new VoterRef(userName, description, year);
            voterRefResults.add(vr);
        }
        return voterRefResults;
    }

    //3) DIVISION QUERY
    public ArrayList<String> getNames() throws SQLException {
        String sql = "SELECT Name " +
                "FROM Voter v " +
                "WHERE NOT EXISTS (SELECT RefID FROM VotesOn MINUS SELECT RefID FROM VotesON vo WHERE v.UserID = vo.UserID)";
        statement = connection.createStatement();

        ResultSet rSet = statement.executeQuery(sql);
        ArrayList<String> namesResults = new ArrayList<>();

        while (rSet.next()) {
            System.out.println("Names: " + rSet.getString(1));
            namesResults.add(rSet.getString(1));
        }
        return namesResults;
    }

    //4) AGGREGATION QUERY
    public Integer getRegisteredUsers() throws SQLException{
        String sql = "SELECT count(*) FROM Voter";
        statement = connection.createStatement();
        ResultSet rSet = statement.executeQuery(sql);
        Integer userCount = 0;

        while (rSet.next()){
            System.out.println("User Count: " + rSet.getInt(1));
            userCount = rSet.getInt(1);
        }
        return userCount;
    }

    //5) NESTED AGGREGATION QUERY
    public Double averageRefVotedOn(String aggregationType) throws SQLException{
        String sql = "SELECT " + aggregationType + "(Votecount.count) " +
                     "FROM (SELECT v.UserID AS id, count(*) as count " +
                     "FROM Voter v, votesOn vo, referendum r " +
                     "WHERE v.UserID = vo.UserID and r.RefID = vo.RefID GROUP BY v.UserID) Votecount";

        statement = connection.createStatement();
        ResultSet rSet = statement.executeQuery(sql);
        Double avgVoted = 0.0;

        while (rSet.next()){
            System.out.println("Average Voted On: " + rSet.getDouble(1));
            avgVoted = rSet.getDouble(1);
        }
        return avgVoted;
    }

    //6) DELETE WITH CASCADE QUERY
    public ArrayList<Voter> deleteUserQuery(String Username) throws SQLException {
        System.out.println("Starting Deletion");
        String sql = "DELETE FROM Voter " +
                     "WHERE Voter.Username = '" + Username + "'";

        statement = connection.createStatement();
        statement.executeUpdate(sql);

        System.out.println("User Deleted, Returning List Of Users");

        sql = "SELECT * FROM Voter";
        ResultSet rSet = statement.executeQuery(sql);

        ArrayList<Voter> deletedResults = new ArrayList<>();
        while (rSet.next()) {
            //System.out.println("User: " + rSet.getString(1));
            Integer userID = rSet.getInt(1);
            String name = rSet.getString(2);
            String userName = rSet.getString(3);
            String email = rSet.getString(4);

            Voter v = new Voter(userID, name, userName, email);
            deletedResults.add(v);
        }
        System.out.println("Returned Updated Users");
        return deletedResults;
    }

    public ArrayList<VotesOn> getAllVotes() throws SQLException{
        String sql = "SELECT * FROM VotesOn";

        ArrayList<VotesOn> queryResults = new ArrayList<>();
        statement = connection.createStatement();
        ResultSet rSet = statement.executeQuery(sql);

        while (rSet.next()) {
            //System.out.println("User: " + rSet.getString(1));
            Integer userID = rSet.getInt(1);
            Integer refID = rSet.getInt(2);
            String option = rSet.getString(3);

            VotesOn vo = new VotesOn(userID, refID, option);
            queryResults.add(vo);
        }
        return queryResults;
    }

    //7) UPDATE WITH CHECKS
    public ArrayList<Voter> updateUserName(String username, String newName) throws SQLException{
        statement = connection.createStatement();
        String sql = "ALTER TABLE Voter " +
                     "ADD CHECK (lower(Name) NOT LIKE '%fuck%')";

        statement.executeUpdate(sql);

        sql = "UPDATE Voter " +
              "Set Name = '"+ newName +"' "+
              "WHERE UserName = '"+ username +"'";

        statement.executeUpdate(sql);

        sql = "SELECT * FROM Voter";
        ResultSet rSet = statement.executeQuery(sql);

        ArrayList<Voter> updatedResults = new ArrayList<>();
        while (rSet.next()) {
            //System.out.println("User: " + rSet.getString(1));
            Integer userID = rSet.getInt(1);
            String name = rSet.getString(2);
            String userName = rSet.getString(3);
            String email = rSet.getString(4);

            Voter v = new Voter(userID, name, userName, email);
            updatedResults.add(v);
        }
        return updatedResults;
    }
}
