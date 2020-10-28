package events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class LeaveEvent extends ListenerAdapter {
    @Override
    public void onGuildMemberRemove(@Nonnull GuildMemberRemoveEvent e) {
        EmbedBuilder eb = new EmbedBuilder();
        User usr = e.getUser();
        String ment = usr.getAsMention();
        eb.setTitle(e.getUser().getName() + " сбежал с нашего сервера Defiant'S");
        eb.addField("",ment + ", надеюсь мы больше не увидимся.",false);
        eb.setThumbnail(e.getUser().getAvatarUrl());
        eb.setImage("https://media.discordapp.net/attachments/614472783715500052/767371392466812938/tenor.gif.gif");
        e.getGuild().getTextChannelById("664824167497203722").sendMessage(eb.build()).queue();
    }
}
