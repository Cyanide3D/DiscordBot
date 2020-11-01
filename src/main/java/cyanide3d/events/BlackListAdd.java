package cyanide3d.events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;


public class BlackListAdd extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        ResourceBundle bundle = ResourceBundle.getBundle("localization",new Locale("ru","RU"));
        if(!e.getChannel().getId().equalsIgnoreCase("664823753116745758")){
            return;
        }
        String[] msg = e.getMessage().getContentRaw().split("&");
        if(!e.getAuthor().isBot()&&msg.length==2) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle(bundle.getString("blacklist.title"));
            eb.addField(bundle.getString("blacklist.nick"), msg[0], false);
            eb.setThumbnail(e.getGuild().getIconUrl());
            eb.setColor(Color.ORANGE);
            eb.addField(bundle.getString("blacklist.reason"), msg[1], false);
            eb.setFooter(bundle.getString("blacklist.form"));
            eb.setDescription(String.format(bundle.getString("blacklist.in"),e.getMember().getNickname(),e.getAuthor().getName()));
            e.getMessage().delete().queue();
            e.getChannel().sendMessage(eb.build()).queue();
        }
    }
}
