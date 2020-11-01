package conf;

import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ChannelManagment {

    public static ChannelManagment instance;
    Map<String, String> channelIDs = new HashMap<>();

    public TextChannel eventLeaveJoinChannel(GuildMemberRemoveEvent e){
        if(channelIDs.containsKey("Join/Leave"))
            return e.getGuild().getTextChannelById(channelIDs.get("Join/Leave"));
        else
            return e.getGuild().getDefaultChannel();
    }
    public TextChannel eventLeaveJoinChannel(GuildMemberJoinEvent e){
        if(channelIDs.containsKey("Join/Leave"))
            return e.getGuild().getTextChannelById(channelIDs.get("Join/Leave"));
        else
            return e.getGuild().getDefaultChannel();
    }

    public void setChannelIDs(){
        DatabaseConnection db = new DatabaseConnection();
        try {
            channelIDs = db.getChannelIDs();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static ChannelManagment getInstance(){
        if(instance == null){
            instance = new ChannelManagment();
        }
        return instance;
    }

}
