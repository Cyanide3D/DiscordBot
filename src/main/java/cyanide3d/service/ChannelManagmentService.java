package cyanide3d.service;

import cyanide3d.dao.ChannelManagmentDao;
import cyanide3d.exceprtion.UnsupportedActionException;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Map;

public class ChannelManagmentService {

    Logger logger = LoggerFactory.getLogger(ChannelManagmentService.class);
    public static ChannelManagmentService instance;
    private final ChannelManagmentDao dao;
    private final Map<String, String> channelIDs;
    private final String[] ACTION_LIST = {"joinleave", "blacklist", "joinform", "gainexp", "logging", "vkdiscord"};

    private ChannelManagmentService() {
        dao = new ChannelManagmentDao();
        channelIDs = dao.getAll();
    }

    public TextChannel eventLeaveJoinChannel(GuildMemberRemoveEvent e) {
        if (channelIDs.containsKey("joinleave"))
            return e.getGuild().getTextChannelById(channelIDs.get("joinleave"));
        else
            return e.getGuild().getDefaultChannel();
    }

    public TextChannel eventLeaveJoinChannel(GuildMemberJoinEvent e) {
        if (channelIDs.containsKey("joinleave"))
            return e.getGuild().getTextChannelById(channelIDs.get("joinleave"));
        else
            return e.getGuild().getDefaultChannel();
    }

    public TextChannel blackListChannel(GuildMessageReceivedEvent e) {
        if (channelIDs.containsKey("blacklist"))
            return e.getGuild().getTextChannelById(channelIDs.get("blacklist"));
        else
            return e.getGuild().getDefaultChannel();
    }

    public TextChannel joinFormChannel(GuildMessageReceivedEvent e) {
        if (channelIDs.containsKey("joinform"))
            return e.getGuild().getTextChannelById(channelIDs.get("joinform"));
        else
            return e.getGuild().getDefaultChannel();
    }

    public TextChannel gainExpChannel(GuildMessageReceivedEvent e) {
        if (channelIDs.containsKey("gainexp"))
            return e.getGuild().getTextChannelById(channelIDs.get("gainexp"));
        else
            return e.getGuild().getDefaultChannel();
    }

    public TextChannel loggingChannel(Guild guild) {
        if (channelIDs.containsKey("logging"))
            return guild.getTextChannelById(channelIDs.get("logging"));
        else
            return guild.getDefaultChannel();
    }

    public Map<String, String> getChannelIDs() {
        return Collections.unmodifiableMap(channelIDs);
    }

    public static ChannelManagmentService getInstance() {
        if (instance == null) {
            instance = new ChannelManagmentService();
        }
        return instance;
    }

    public boolean checkAction(String action) {
        for (String actions : ACTION_LIST) {
            if (actions.equalsIgnoreCase(action)) {
                return true;
            }
        }
        return false;
    }

    public void addChannel(String channelID, String action) throws UnsupportedActionException {
        if (channelIDs.containsKey(action)){
            changeChannel(channelID, action);
            return;
        }
        if (checkAction(action)) {
            channelIDs.put(action, channelID);
            dao.insert(action, channelID);
        } else {
            logger.error("ChannelManagmentService.AddChannel UnsupportedAction");
            throw new UnsupportedActionException(action);
        }
    }

    public void changeChannel(String channelID, String action) throws UnsupportedActionException {
        if (!channelIDs.containsKey(action)) return;
        if (checkAction(action)) {
            channelIDs.remove(action);
            channelIDs.put(action, channelID);
            dao.update(action, channelID);
        } else {
            logger.error("ChannelManagmentService.ChangeChannel UnsupportedAction");
            throw new UnsupportedActionException(action);
        }
    }

    public void deleteChannel(String action) throws UnsupportedActionException {
        if (!channelIDs.containsKey(action)) return;
        if (checkAction(action)) {
            channelIDs.remove(action);
            dao.delete(action);
        } else {
            logger.error("ChannelManagmentService.DeleteChannel UnsupportedAction");
            throw new UnsupportedActionException(action);
        }
    }

}
