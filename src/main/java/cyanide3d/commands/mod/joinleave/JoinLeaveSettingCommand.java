package cyanide3d.commands.mod.joinleave;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;


public class JoinLeaveSettingCommand extends Command {

    private final EventWaiter waiter;

    public JoinLeaveSettingCommand(EventWaiter waiter) {
        this.waiter = waiter;
        this.name = "jls";
    }

    @Override
    protected void execute(CommandEvent event) {
        event.reply("Для какого оповещения применить настройки? (JOIN/LEAVE).");
        event(event, new JoinLeaveStateful(event.getGuild().getId()));
    }

    private void event(CommandEvent event, JoinLeaveStateful stateful) {
        waiter.waitForEvent(GuildMessageReceivedEvent.class, e -> event.getAuthor().equals(e.getAuthor()), message -> {
            event.reply(stateful.parse(message.getMessage().getContentRaw()));
            if (!stateful.isDone())
                event(event, stateful);
        });
    }
}
