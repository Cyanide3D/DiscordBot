package cyanide3d.listener;

import cyanide3d.handlers.listener.JoinEventHandler;
import cyanide3d.handlers.listener.LeaveEventHandler;
import cyanide3d.handlers.listener.MessageHandler;
import cyanide3d.handlers.listener.PinHandler;
import cyanide3d.service.AutoroleService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GenericGuildMessageReactionEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class EventListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        new JoinEventHandler(event).handle();
    }

    @Override
    public void onGenericGuildMessageReaction(@Nonnull GenericGuildMessageReactionEvent event) {
        final AutoroleService service = AutoroleService.getInstance();
        final String roleId = service.getRoleId(event.getMessageId(), event.getReactionEmote().getName(), event.getGuild().getId());

        if (roleId == null) {
            return;
        }

        Role role = event.getGuild().getRoleById(roleId);

        if (role == null || event.getMember() == null || event.getUser().isBot()) {
            return;
        }

        giveClickReactionRole(role, event.getMember(), event.getGuild());
    }



    private void giveClickReactionRole(Role role, Member member, Guild guild) {
        String message;
        if (member.getRoles().contains(role)) {
            guild.removeRoleFromMember(member, role).queue();
            message = "Роль успешно убрана.";
        } else {
            guild.addRoleToMember(member, role).queue();
            message = "Роль успешно выдана.";
        }

        member.getUser().openPrivateChannel()
                .queue(channel -> channel.sendMessage(message).queue());
    }

    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {
        new PinHandler(event).handle();
    }

    @Override
    public void onGuildMemberRemove(@Nonnull GuildMemberRemoveEvent event) {
        new LeaveEventHandler(event).handle();
    }

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        new MessageHandler(event).handle();
//        if (!event.getAuthor().isBot()) {
//            event.getChannel().sendMessage(new MessageMentionFilter(event.getMessage().getContentRaw()).toVk()).queue();
//        }
    }

    @Override
    public void onPrivateMessageReceived(@Nonnull PrivateMessageReceivedEvent event) {
        if (!event.getAuthor().isBot()) {
            event
                    .getChannel()
                    .sendMessage("**Не стоит писать боту.**\n" +
                            "Вряд ли он на это как то отреагирует.")
                    .queue();
        }
    }
}