package events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class ChatEvent extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String msg = event.getMessage().getContentRaw();
        EmbedBuilder eb = new EmbedBuilder();
        /*if(msg.equalsIgnoreCase("Аниме")){
            eb.setTitle("Аниме хуета");
            eb.setColor(Color.PINK);
            eb.setImage("https://cdn.discordapp.com/attachments/562213174057893908/770442969000968272/4321.jpg");
            event.getChannel().sendMessage(eb.build()).queue();
        }*/
        if(!event.getMessage().getMentionedMembers().isEmpty()) {
            Member usr = event.getMessage().getMentionedMembers().get(0);
            if (usr.getUser().getName().equalsIgnoreCase(event.getJDA().getSelfUser().getName())) {
                event.getChannel().sendMessage("Шо надо?").queue();
            }
        }
        if(msg.equalsIgnoreCase("$help")){
            event.getChannel().sendMessage("Смотри в ЛС ;)").queue();
        }
    }
}