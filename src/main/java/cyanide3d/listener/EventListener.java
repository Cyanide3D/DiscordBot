package cyanide3d.listener;

import cyanide3d.handlers.listener.joinevent.EntryMessageHandler;
import cyanide3d.handlers.listener.joinevent.JoinAlertHandler;
import cyanide3d.handlers.listener.joinevent.JoinEventHandler;
import cyanide3d.handlers.listener.receivedmessage.*;
import cyanide3d.handlers.listenerrrrrrr.*;
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
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import java.util.List;

public class EventListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        final List<JoinEventHandler> handlers = List.of(
                new JoinAlertHandler(),
                new EntryMessageHandler()
        );
        handlers.forEach(handler -> handler.execute(event));
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
        List<ReceivedMessageHandler> handlers = List.of(
                new CommandManagerHandler(),
                new DialogHandler(),
                new BlacklistHandler(),
                new ExpirienceHandler(),
                new SpeechFilterHandler(),
                new StatementHandler(),
                new VacationHandler(),
                new MentionHandler()
        );
        handlers.forEach(handler -> handler.execute(event));
        //       if (event.getChannel().getId().equals("791636377145180191") && !event.getAuthor().isBot()){
//       new VkHandler(event).handle();
//   }
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