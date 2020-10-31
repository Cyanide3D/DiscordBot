package events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class ChatEvent extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        ResourceBundle bundle = ResourceBundle.getBundle("localization",new Locale("ru","RU"));
        String msg = event.getMessage().getContentRaw();
        EmbedBuilder eb = new EmbedBuilder();
        /*if(msg.equalsIgnoreCase("Аниме")){
            eb.setTitle("Аниме хуета");
            eb.setColor(Color.PINK);
            eb.setImage("https://cdn.discordapp.com/attachments/562213174057893908/770442969000968272/4321.jpg");
            event.getChannel().sendMessage(eb.build()).queue();
        }*/
        if(msg.equalsIgnoreCase("$help")){
            event.getChannel().sendMessage(bundle.getString("helpmessage")).queue();
        }
    }
}