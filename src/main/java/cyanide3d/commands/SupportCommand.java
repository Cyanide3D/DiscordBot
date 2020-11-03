package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.conf.Config;
import cyanide3d.conf.Permission;
import cyanide3d.dao.ChannelManagmentDao;
import cyanide3d.service.ChannelManagmentService;
import cyanide3d.service.PermissionService;

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



    @Override
    protected void execute(CommandEvent e) {

    }
}
