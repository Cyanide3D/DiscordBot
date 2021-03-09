package cyanide3d.commands.mod.emoji;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
import cyanide3d.service.PermissionService;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

import java.util.function.Predicate;

public class EmojiCommand extends Command {

    private final Localization localization = Localization.getInstance();
    private final EventWaiter waiter;

    public EmojiCommand(EventWaiter waiter) {
        this.waiter = waiter;
        super.name = "autorole";
    }

    @Override
    protected void execute(CommandEvent commandEvent) {

        if (!PermissionService.getInstance().checkPermission(commandEvent.getMember(), Permission.MODERATOR)) {
            commandEvent.reply(localization.getMessage("accessDenied", name));
            return;
        }

        commandEvent.reply("Введите заголовок сообщения.");
        step(commandEvent, new StatefulParser());
    }

    private void step(CommandEvent replyTarget, StatefulParser parser) {

        Class<?> clazz = parser.getEventClass();

        if (clazz.equals(GuildMessageReceivedEvent.class)) {
            waiter.waitForEvent(GuildMessageReceivedEvent.class, e ->  e.getAuthor().equals(replyTarget.getAuthor()), event -> {
                replyTarget.reply(parser.parse(event.getMessage()));
                if (!parser.isComplete()) {
                    step(replyTarget, parser);
                } else {
                    parser.init(replyTarget);
                }
            });
        } else {
            waiter.waitForEvent(GuildMessageReactionAddEvent.class, e ->  e.getUser().equals(replyTarget.getAuthor()), event -> {
                replyTarget.reply(parser.parse(event.getReactionEmote()));
                if (!parser.isComplete()) {
                    step(replyTarget, parser);
                } else {
                    parser.init(replyTarget);
                }
            });
        }
    }
}
