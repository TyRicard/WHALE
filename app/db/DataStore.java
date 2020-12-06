package db;

import models.Whale;

import java.io.IOException;
import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataStore {
    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String URL = "jdbc:h2:mem";
    private static final String DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String WHALE_CONNECTION = "jdbc:h2:~/whale";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    private List<Whale> whaleList;

    public DataStore() {
        Connection dbConnection = null;
        try {
            dbConnection = DriverManager.getConnection(WHALE_CONNECTION, DB_USER, DB_PASSWORD);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String logPath = String.valueOf((getClass().getClassLoader().getResource("logging.properties")));
        Statement stmt = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        boolean pass = true;
        try {

            stmt = dbConnection.createStatement();
            stmt.execute("DROP TABLE WHALES IF EXISTS");
            stmt.execute("CREATE TABLE WHALES(id int primary key, species varchar(255), weight integer, " +
                    "gender varchar(255))");
            stmt.close();
            dbConnection.commit();
        } catch (SQLException e) {
            pass = false;
            String err = "Exception Message " + e.getLocalizedMessage();
            System.out.println(err);
        } catch (Exception e) {
            pass = false;
            e.printStackTrace();
        } finally {
            try {
                dbConnection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        assertEquals(true, pass);
    }

    public void addWhale(Whale w) throws SQLException {
        Connection dbConnection = DriverManager.getConnection("jdbc:h2:~/whale", "sa", "");
        String logPath = String.valueOf((getClass().getClassLoader().getResource("logging.properties")));
        PreparedStatement pStmt = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        boolean pass = true;
        try {
            String sql = "INSERT INTO WHALES(id, species, weight, gender) VALUES(?,?,?,?)";
            System.out.println("sql:"+sql);
            pStmt = dbConnection.prepareStatement(sql);
            pStmt.setInt(1,(int)w.id);
            pStmt.setString(2,w.species);
            pStmt.setInt(3,(int)w.weight);
            pStmt.setString(4,w.gender);
            System.out.println("+pStmt.toString():"+pStmt.toString());
            int res = pStmt.executeUpdate();
            System.out.println("res:"+res);
        } catch (SQLException e) {
            pass = false;
            String err = "Exception Message " + e.getLocalizedMessage();
            System.out.println(err);
        } catch (Exception e) {
            pass = false;
            e.printStackTrace();
        } finally {
            try {
                dbConnection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        assertEquals(true, pass);
    }

    public void addWhales(List<Whale> newWhales) throws SQLException {
        Connection dbConnection = DriverManager.getConnection("jdbc:h2:~/whale", "sa", "");
        String logPath = String.valueOf((getClass().getClassLoader().getResource("logging.properties")));
        PreparedStatement pStmt = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        boolean pass = true;
        try {
            for(Whale w : newWhales){
                String sql = "INSERT INTO WHALES(id, species, weight, gender) VALUES(?,?,?,?)";
                System.out.println("sql:"+sql);
                pStmt = dbConnection.prepareStatement(sql);
                pStmt.setInt(1,(int)w.id);
                pStmt.setString(2,w.species);
                pStmt.setInt(3,(int)w.weight);
                pStmt.setString(4,w.gender);
                System.out.println("+pStmt.toString():"+pStmt.toString());
                int res = pStmt.executeUpdate();
                System.out.println("res:"+res);
            }
            pStmt.close();
            dbConnection.close();
        } catch (SQLException e) {
            pass = false;
            String err = "Exception Message " + e.getLocalizedMessage();
            System.out.println(err);
        } catch (Exception e) {
            pass = false;
            e.printStackTrace();
        } finally {
            try {
                dbConnection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        assertEquals(true, pass);
    }

    public List<Whale> getWhales() throws IOException, SQLException {
        Connection connection = DriverManager.getConnection("jdbc:h2:~/whale", "sa", "");
        List<Whale> whaleList = new ArrayList<>();
        String logPath = String.valueOf((getClass().getClassLoader().getResource("logging.properties")));
        System.out.println(getClass().getClassLoader().getResource("logging.properties"));
        //Connection connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        boolean pass = true;
        try (var con = connection;
             var stm = con.createStatement();
             var rs = stm.executeQuery("SELECT * from WHALES")) {
            while (rs.next()) {
                int id = rs.getInt(1);
                String species = rs.getString(2);
                int weight = rs.getInt(3);
                String gender = rs.getString(4);
                Whale w = new Whale(species, weight, gender);;
                whaleList.add(w);
            }
        } catch (SQLException ex) {
            pass = false;
            var lgr = Logger.getLogger(DataStore.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        assertEquals(true, pass);
        connection.close();
        return whaleList;
    }
}
