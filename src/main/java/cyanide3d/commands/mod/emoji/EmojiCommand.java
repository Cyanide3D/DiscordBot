package cyanide3d.commands.mod.emoji;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.function.Predicate;

public class EmojiCommand extends Command {

    private final EventWaiter waiter;

    public EmojiCommand(EventWaiter waiter) {
        this.waiter = waiter;
        super.name = "emoji";
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        commandEvent.reply("Введите название.");
        step(GuildMessageReceivedEvent.class, e -> e.getAuthor().equals(commandEvent.getAuthor()),
                commandEvent, new StatefulParser());
    }

    private <T extends GuildMessageReceivedEvent> void step(Class<T> eventClass, Predicate<T> eventFilter, CommandEvent replyTarget, StatefulParser parser) {
        waiter.waitForEvent(eventClass, eventFilter, event -> {
            replyTarget.reply(parser.parse(event.getMessage()));
            if (!parser.isComplete()) {
                step(eventClass, eventFilter, replyTarget, parser);
            }
        });
    }
}
