package conf;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseConnection {
    Settings sett = new Settings();
    String url = sett.getProperties("DATABASE_URL");
    String username = sett.getProperties("DATABASE_LOGIN");
    String password = sett.getProperties("DATABASE_PASSWORD");
    Connection con;
    {
        try {
            con = DriverManager.getConnection(url, username, password);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void addBadWords(String word) throws SQLException {
        PreparedStatement ps = con.prepareStatement("insert badwords(word) values(?)");
        ps.setString(1, word.toLowerCase());
        ps.executeUpdate();
        System.out.println("Use BD");
        ps.close();
    }

    public void removeBadWord(String word) throws SQLException {
        PreparedStatement ps = con.prepareStatement("delete from badwords where word=?");
        ps.setString(1, word.toLowerCase());
        ps.executeUpdate();
        System.out.println("Use BD");
        ps.close();
    }
    public void removeIDs(String ID) {
        try {
        PreparedStatement ps = con.prepareStatement("delete from userids where userid=?");
            ps.setString(1, ID.toLowerCase());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public ArrayList listBadWords() throws SQLException {
        Statement stm;
        ResultSet ts;
        ArrayList<String> badWords = new ArrayList<>();
        stm = con.createStatement();
        ts = stm.executeQuery("select * from badwords");
        while (ts.next()) {
            badWords.add(ts.getString("word"));
        }
        System.out.println("Use BD");
        ts.close();
        stm.close();
        return badWords;
    }

    public Map getLstIDs() throws SQLException {
        Statement stm;
        ResultSet ts;
        Map<String, Integer> userIDs = new HashMap<>();
        stm = con.createStatement();
        ts = stm.executeQuery("select * from userids");
        while (ts.next()) {
            userIDs.put(ts.getString("userid"), ts.getInt("permission"));
        }
        System.out.println("Use BD");
        ts.close();
        stm.close();
        return userIDs;
    }

    public void insertIDs(String ID, int permission) {
        try {
            PreparedStatement ps = con.prepareStatement("insert userids(userid,permission) values(?,?)");
            ps.setString(1, ID.toLowerCase());
            ps.setInt(2, permission);
            System.out.println("Use BD");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void changePermToID(String ID, int permission) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE userids SET permission=? WHERE userid=?");
            ps.setString(2, ID.toLowerCase());
            ps.setInt(1, permission);
            System.out.println("Use BD");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void insertChannelIDs(String channelID, String using) {
        try {
            PreparedStatement ps = con.prepareStatement("insert channels(chID,using) values(?,?)");
            ps.setString(1, channelID.toLowerCase());
            ps.setString(2, using.toLowerCase());
            System.out.println("Use BD");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public Map getChannelIDs() throws SQLException {
        Statement stm;
        ResultSet ts;
        Map<String, String> channelIDs = new HashMap<>();
        stm = con.createStatement();
        ts = stm.executeQuery("select * from channels");
        while (ts.next()) {
            channelIDs.put(ts.getString("chID"), ts.getString("using"));
        }
        System.out.println("Use BD");
        ts.close();
        stm.close();
        return channelIDs;
    }

    public void changeChannelID(String chID, String using) {
        try {
            PreparedStatement ps = con.prepareStatement("UPDATE channels SET chID=? WHERE using=?");
            ps.setString(2, chID.toLowerCase());
            ps.setString(1, using.toLowerCase());
            System.out.println("Use BD");
            ps.executeUpdate();
            ps.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void removeChannelIDs(String chID) {
        try {
            PreparedStatement ps = con.prepareStatement("delete from channels where chID=?");
            ps.setString(1, chID.toLowerCase());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

}