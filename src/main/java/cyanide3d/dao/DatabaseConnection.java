package cyanide3d.dao;

import com.mysql.cj.protocol.Resultset;
import cyanide3d.conf.Permission;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseConnection {
    private Connection conn;


    public DatabaseConnection(String url, String username, String password) {
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String getString(String query, Object... params) {
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            int index = 1;
            for (Object param : params) {
                statement.setObject(index++, param);
            }
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getString(1);
        } catch (SQLException ex) {
            //TODO logging
            return null;
        }
    }

    public <T> List<T> getListSet(String query, Object... params) {
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            int index = 1;
            for (Object param : params) {
                statement.setObject(index++, param);
            }
            ResultSet resultSet = statement.executeQuery();
            List<T> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add((T) resultSet.getObject(1));
            }
            return result;
        } catch (SQLException ex) {
            //TODO logging
            return null;
        }
    }

    public Map<String, Permission> getListPermissions(String query, Object... params) {
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            int index = 1;
            for (Object param : params) {
                statement.setObject(index++, param);
            }
            ResultSet resultSet = statement.executeQuery();
            Map<String, Permission> result = new HashMap<>();
            while (resultSet.next()) {
                Permission permission = Permission.valueOf(checkPermissions(resultSet.getInt(3)));
                result.put(resultSet.getString(2),permission);
            }
            return result;
        } catch (SQLException ex) {
            //TODO logging
            return null;
        }
    }
    public Map<String, String> getListChannels(String query, Object... params) {
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            int index = 1;
            for (Object param : params) {
                statement.setObject(index++, param);
            }
            ResultSet resultSet = statement.executeQuery();
            Map<String, String> result = new HashMap<>();
            while (resultSet.next()) {
                result.put(resultSet.getString(2),resultSet.getString(1));
            }
            return result;
        } catch (SQLException ex) {
            //TODO logging
            return null;
        }
    }

    public Map<String, String> getBlacklist(String query, Object... params) {
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            int index = 1;
            for (Object param : params) {
                statement.setObject(index++, param);
            }
            ResultSet resultSet = statement.executeQuery();
            Map<String, String> result = new HashMap<>();
            while (resultSet.next()) {
                result.put(resultSet.getString(1),resultSet.getString(2));
            }
            return result;
        } catch (SQLException ex) {
            //TODO logging
            return null;
        }
    }

    public String checkPermissions(int code){
        if(code==0)
            return "OWNER";
        if(code==1)
            return "ADMIN";
        if(code==2)
            return "MODERATOR";
        return "MODERATOR";
    }

    public void update(String query, Object... params) {
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            int index = 1;
            for (Object param : params) {
                statement.setObject(index++, param);
            }
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void insert(String query, Object... params) {
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            int index = 1;
            for (Object param : params) {
                statement.setObject(index++, param);
            }
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public int delete(String query, Object... params){
        int rowCount = 0;
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            int index = 1;
            for (Object param : params) {
                statement.setObject(index++, param);
            }
            rowCount = statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return rowCount;
    }




    /*
    public void addBadWords(String word) throws SQLException {

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

    public Set<String> listBadWords() throws SQLException {
        Statement stm;
        ResultSet ts;
        Set<String> badWords = new HashSet<>();
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
            PreparedStatement ps = con.prepareStatement("insert into userids(userid,permission) values(?,?)");
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
    }*/
}
