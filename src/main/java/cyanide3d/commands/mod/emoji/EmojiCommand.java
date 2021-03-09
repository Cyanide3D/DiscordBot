package cyanide3d.commands.mod.emoji;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

import java.util.function.Predicate;

public class EmojiCommand extends Command {

    private final EventWaiter waiter;

    public EmojiCommand(EventWaiter waiter) {
        this.waiter = waiter;
        super.name = "emoji";
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
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
