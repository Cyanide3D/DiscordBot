package commands;

import com.jagrosh.jdautilities.command.CommandEvent;
import conf.ChannelManagment;
import conf.DatabaseConnection;
import conf.Permission;
import conf.UserAccessToCommand;

public class ChannelSettings {

    EmbedSettings embed = new EmbedSettings();
    UserAccessToCommand userAccess = UserAccessToCommand.getInstance();
    ChannelManagment channelManagment = ChannelManagment.getInstance();
    DatabaseConnection db = new DatabaseConnection();

    public void setJoinLeaveChannel(CommandEvent e, String[] args){
        if (args.length == 4 && e.getMessage().getMentionedChannels().get(0) != null) {

        }
    }

}
