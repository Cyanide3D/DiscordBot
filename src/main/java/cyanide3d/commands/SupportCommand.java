package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.conf.Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SupportCommand extends Command {
    public SupportCommand(){
        this.name = "sc";
        this.help = "sup";
        this.hidden = true;
    }


//    Config sett = new Config();
//    String url = sett.getProperties("DATABASE_URL");
//    String username = sett.getProperties("DATABASE_LOGIN");
//    String password = sett.getProperties("DATABASE_PASSWORD");
    Connection con=null;

//    {
//        try {
//            con = DriverManager.getConnection(url, username, password);
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }


    @Override
    protected void execute(CommandEvent e) {
        try {
            PreparedStatement ps = con.prepareStatement("insert into userids(userid,permission) values('711616014252507289',0)");
            ps.executeUpdate();
            System.out.println("Use BD");
            ps.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }




//    public void createTable1() throws SQLException {
//        PreparedStatement ps = con.prepareStatement("create table if not exists userids(id integer NOT NULL PRIMARY KEY AUTOINCREMENT, userid MEDIUMTEXT, permission int);");
//        ps.executeUpdate();
//        System.out.println("Use BD");
//        ps.close();
//    }
//    public void createTable2() throws SQLException {
//        PreparedStatement ps = con.prepareStatement("create table if not exists badwords(id integer NOT NULL PRIMARY KEY AUTOINCREMENT, word MEDIUMTEXT);");
//        ps.executeUpdate();
//        System.out.println("Use BD");
//        ps.close();
//    }
}
