package events;

import conf.DatabaseConnection;
import conf.UserAcessToCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.Locale;
import java.util.Random;
import java.util.ResourceBundle;

public class LeaveEvent extends ListenerAdapter {
    String[] gifList = {"https://media.discordapp.net/attachments/614472783715500052/767371392466812938/tenor.gif.gif",
    "https://media.discordapp.net/attachments/614472783715500052/767371396354408458/good_bye_2.gif.gif",
    "https://i.gifer.com/53HC.gif","https://i.gifer.com/9TEx.gif","https://i.gifer.com/7A25.gif",
    "https://cdn.discordapp.com/attachments/614472783715500052/767371392110297088/good_bye_1.gif.gif"};
    @Override
    public void onGuildMemberRemove(@Nonnull GuildMemberRemoveEvent e) {
        ResourceBundle bundle = ResourceBundle.getBundle("localization",new Locale("ru","RU"));
        DatabaseConnection db = new DatabaseConnection();
        UserAcessToCommand usrAccess = UserAcessToCommand.getInstance();
        Random rnd = new Random();
        EmbedBuilder eb = new EmbedBuilder();
        User usr = e.getUser();
        String ment = usr.getAsMention();
        eb.setTitle(bundle.getString("leaveevent.title"));
        eb.addField("",ment + bundle.getString("leaveevent.field"),false);
        eb.setThumbnail(e.getUser().getAvatarUrl());
        eb.setColor(Color.RED);
        eb.setAuthor(e.getUser().getAsTag(),e.getUser().getAvatarUrl(),e.getUser().getAvatarUrl());
        eb.setImage(gifList[rnd.nextInt(gifList.length)]);
        e.getGuild().getTextChannelById("664814377702260756").sendMessage(eb.build()).queue();
        db.removeIDs(e.getUser().getId());
        usrAccess.setUserIDs();
    }
}
