package conf;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection {
    String url = "jdbc:mysql://localhost:1527/testdb?serverTimezone=Europe/Minsk&useSSL=false";
    String username = "root";
    String password = "root";
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
        ps.executeUpdate() ;
        ps.close();
    }

    public void removeBadWord(String word) throws SQLException {
        PreparedStatement ps = con.prepareStatement("delete from badwords where word=?");
        ps.setString(1, word.toLowerCase());
        ps.executeUpdate() ;
        ps.close();
    }

    public boolean checkBadWords(String word) {
        Statement stm;
        ResultSet ts;
        try {
            stm = con.createStatement();
            ts = stm.executeQuery("select * from badwords");
            while (ts.next()) {
                String badword = ts.getString("word");
                if (word.equalsIgnoreCase(badword)) {
                    return true;
                }
            }
            ts.close();
            stm.close();
        }catch (java.sql.SQLException exception){exception.getStackTrace();}
        return false;
    }
    public ArrayList listBadWords() throws SQLException {
        Statement stm;
        ResultSet ts;
        ArrayList<String> badWords = new ArrayList<>();
        stm = con.createStatement();
        ts = stm.executeQuery("select * from badwords");
        while (ts.next()){
            badWords.add(ts.getString("word"));
        }
        ts.close();
        stm.close();
        return badWords;
    }
}
