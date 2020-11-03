package cyanide3d.service;

import cyanide3d.dao.BadWordsDao;
import cyanide3d.dao.ChannelManagmentDao;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ChannelManagmentService {

    public static ChannelManagmentService instance;
    private final ChannelManagmentDao dao;
    private Map<String, String> channelIDs;

    private ChannelManagmentService() {
        dao = new ChannelManagmentDao();
        channelIDs = dao.getAll();
    }
    /*Actions:
    Join/Leave
    Blacklist
    JoinForm
     */



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
    public TextChannel blackListChannel(GuildMessageReceivedEvent e){
        if(channelIDs.containsKey("Blacklist"))
            return e.getGuild().getTextChannelById(channelIDs.get("Blacklist"));
        else
            return e.getGuild().getDefaultChannel();
    }
    public TextChannel joinFormChannel(GuildMessageReceivedEvent e){
        if(channelIDs.containsKey("JoinForm"))
            return e.getGuild().getTextChannelById(channelIDs.get("JoinForm"));
        else
            return e.getGuild().getDefaultChannel();
    }

    public Map<String, String> getChannelIDs() {
        return Collections.unmodifiableMap(channelIDs);
    }

    public static ChannelManagmentService getInstance(){
        if(instance == null){
            instance = new ChannelManagmentService();
        }
        return instance;
    }

    public void addChannel(String channelID, String action){
        channelIDs.put(action,channelID);
        dao.insert(action,channelID);
    }
    public void changeChannel(String channelID, String action){
        channelIDs.remove(action);
        channelIDs.put(action,channelID);
        dao.update(action,channelID);
    }
    public void deleteChannel(String action){
        channelIDs.remove(action);
        dao.delete(action);
    }

}
