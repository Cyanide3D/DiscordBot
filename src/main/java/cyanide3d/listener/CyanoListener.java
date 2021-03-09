package cyanide3d.listener;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import cyanide3d.filters.MessageMentionFilter;
import cyanide3d.handlers.listener.JoinMemberHandler;
import cyanide3d.handlers.listener.LeaveMemberHandler;
import cyanide3d.handlers.listener.MessageReceivedHandler;
import cyanide3d.handlers.listener.ParsePinReactionHandler;
import cyanide3d.misc.MyGuild;
import cyanide3d.misc.TimerToPlayer;
import cyanide3d.service.EmoteManageService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GenericGuildMessageReactionEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Random;

public class CyanoListener extends ListenerAdapter {

    private final String[] joinGifs = {"https://cdn.discordapp.com/attachments/573773778480398337/771325629491707924/tenor.gif",
            "https://cdn.discordapp.com/attachments/573773778480398337/771325641009135626/tenor_1.gif",
            "https://i.gifer.com/EIGB.gif",
            "https://i.gifer.com/D85T.gif",
            "https://i.gifer.com/Pvm.gif"};

    private final String[] leaveGifs = {"https://media.discordapp.net/attachments/614472783715500052/767371392466812938/tenor.gif.gif",
            "https://media.discordapp.net/attachments/614472783715500052/767371396354408458/good_bye_2.gif.gif",
            "https://i.gifer.com/53HC.gif", "https://i.gifer.com/9TEx.gif", "https://i.gifer.com/7A25.gif",
            "https://cdn.discordapp.com/attachments/614472783715500052/767371392110297088/good_bye_1.gif.gif"};

    private final Random random;

    public CyanoListener() {
        random = new Random();
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        new JoinMemberHandler(getRandomGifUrl(joinGifs), event).handle();
    }

    @Override
    public void onGuildReady(@Nonnull GuildReadyEvent event) {
        TimerToPlayer.getInstance().setGuild(event.getGuild());
        MyGuild.getInstance().setGuild(event.getGuild());
    }

    @Override
    public void onGenericGuildMessageReaction(@Nonnull GenericGuildMessageReactionEvent event) {
        final EmoteManageService service = EmoteManageService.getInstance();
        final String roleId = service.getRole(event.getMessageId(), event.getReactionEmote().getName());

        if (roleId == null) {
            return;
        }

        Role role = event.getGuild().getRoleById(roleId);

        if (role == null || event.getMember() == null) {
            return;
        }
        
        giveClickReactionRole(role, event.getMember(), event.getGuild());
    }



    private void giveClickReactionRole(Role role, Member member, Guild guild) {
        if (member.getRoles().contains(role)) {
            guild.removeRoleFromMember(member, role).queue();
        } else {
            guild.addRoleToMember(member, role).queue();
        }
    }

    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {
        new ParsePinReactionHandler(event).handle();
    }

    @Override
    public void onGuildMemberRemove(@Nonnull GuildMemberRemoveEvent event) {
        new LeaveMemberHandler(event, getRandomGifUrl(leaveGifs)).handle();
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        new MessageReceivedHandler(event).handle();
//        if (!event.getAuthor().isBot()) {
//            event.getChannel().sendMessage(new MessageMentionFilter(event.getMessage().getContentRaw()).toVk()).queue();
//        }
    }

    @Override
    public void onPrivateMessageReceived(@Nonnull PrivateMessageReceivedEvent event) {
        if (!event.getAuthor().isBot()) {
            event
                    .getChannel()
                    .sendMessage("**Не нужно писать боту.**\n" +
                            "Заявки и вопросы **необходимо** оставлять в **соответствующих каналах** нашего дискорд-сервера.")
                    .queue();
        }
    }

    private String getRandomGifUrl(String[] gifs) {
        return gifs[random.nextInt(gifs.length)];
    }

}