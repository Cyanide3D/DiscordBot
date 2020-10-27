package events;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class JoinEvent extends ListenerAdapter {
    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent e) {
        EmbedBuilder eb = new EmbedBuilder();
        User usr = e.getUser();
        Role role = e.getGuild().getRoleById("664863945684680737");
        String ment = usr.getAsMention();
        eb.setTitle(e.getUser().getName() + ", добро пожаловать на наш клановый сервер! Defiant'S");
        eb.addField("",ment + ", рады приветствовать тебя!",false);
        eb.setThumbnail(e.getUser().getAvatarUrl());
        eb.setImage("https://media.discordapp.net/attachments/614472783715500052/767371396354408458/good_bye_2.gif.gif");
        e.getGuild().addRoleToMember(e.getUser().getIdLong(),role).queue();
        e.getGuild().getTextChannelById("664824167497203722").sendMessage(eb.build()).queue();
    }
}
