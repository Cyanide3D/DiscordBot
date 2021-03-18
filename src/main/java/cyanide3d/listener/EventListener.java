package cyanide3d.listener;

import cyanide3d.handlers.listener.joinevent.EntryMessageHandler;
import cyanide3d.handlers.listener.joinevent.EntryRoleHandler;
import cyanide3d.handlers.listener.joinevent.JoinAlertHandler;
import cyanide3d.handlers.listener.joinevent.JoinEventHandler;
import cyanide3d.handlers.listener.leaveevent.DeleteUserHandler;
import cyanide3d.handlers.listener.leaveevent.LeaveAlertHandler;
import cyanide3d.handlers.listener.leaveevent.LeaveEventHandler;
import cyanide3d.handlers.listener.messagereaction.AutoroleHandler;
import cyanide3d.handlers.listener.messagereaction.MessageReactionHandler;
import cyanide3d.handlers.listener.messagereaction.PinParseHandler;
import cyanide3d.handlers.listener.receivedmessage.*;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GenericGuildMessageReactionEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.List;

public class EventListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        final List<JoinEventHandler> handlers = List.of(
                new JoinAlertHandler(),
                new EntryMessageHandler(),
                new EntryRoleHandler()
        );
        handlers.forEach(handler -> handler.execute(event));
    }

    @Override
    public void onGenericGuildMessageReaction(@Nonnull GenericGuildMessageReactionEvent event) {
        List<MessageReactionHandler> handlers = List.of(
                new PinParseHandler(),
                new AutoroleHandler()
        );
        handlers.forEach(handler -> handler.execute(event));
    }

    @Override
    public void onGuildMemberRemove(@Nonnull GuildMemberRemoveEvent event) {
        List<LeaveEventHandler> handlers = List.of(
                new LeaveAlertHandler(),
                new DeleteUserHandler()
        );
        handlers.forEach(handler -> handler.execute(event));
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
                new MentionHandler(),
                new SocketVkHandler(event.getJDA())
        );
        handlers.forEach(handler -> handler.execute(event));
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