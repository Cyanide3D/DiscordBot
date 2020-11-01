package events;

import conf.ChannelManagment;
import conf.DatabaseConnection;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

public class JoinEvent extends ListenerAdapter {
    String[] gifList = {"https://cdn.discordapp.com/attachments/573773778480398337/771325629491707924/tenor.gif",
            "https://cdn.discordapp.com/attachments/573773778480398337/771325641009135626/tenor_1.gif","https://i.gifer.com/EIGB.gif","https://i.gifer.com/D85T.gif",
    "https://i.gifer.com/Pvm.gif"};
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent e) {
        ChannelManagment channelManagment = ChannelManagment.getInstance();
        TextChannel parseMessage = channelManagment.eventLeaveJoinChannel(e);
        ResourceBundle bundle = ResourceBundle.getBundle("localization",new Locale("ru","RU"));
        DatabaseConnection db = new DatabaseConnection();
        Random rnd = new Random();
        EmbedBuilder eb = new EmbedBuilder();
        User usr = e.getUser();
        Role role = e.getGuild().getRolesByName("Гости",true).get(0);
        String ment = usr.getAsMention();
        eb.setTitle(bundle.getString("joinevent.title"));
        eb.addField("",ment + bundle.getString("joinevent.field"),false);
        eb.setThumbnail(e.getUser().getAvatarUrl());
        eb.setColor(Color.GREEN);
        eb.setAuthor(e.getUser().getAsTag(),e.getUser().getAvatarUrl(),e.getUser().getAvatarUrl());
        eb.setImage(gifList[rnd.nextInt(gifList.length)]);
        e.getGuild().addRoleToMember(e.getUser().getId(),role).queue();
        parseMessage.sendMessage(eb.build()).queue();
        db.insertIDs(e.getUser().getId(),3);
    }
}
