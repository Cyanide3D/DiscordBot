package cyanide3d.commands.mod.emoji;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import com.sun.xml.internal.bind.v2.TODO;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
import cyanide3d.service.PermissionService;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GenericGuildMessageEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;

public class EmojiCommand extends Command {

    private final Localization localization = Localization.getInstance();
    private final EventWaiter waiter;

    public EmojiCommand(EventWaiter waiter) {
        this.waiter = waiter;
        this.name = "autorole";
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

    private <T extends GenericGuildMessageEvent> void step(CommandEvent replyTarget, StatefulParser parser) {
        if (parser.getNextEventClass() == null) {
            //TODO do something here (вообще невозможный кейс - если попало сюда значит что-то сломано)
            replyTarget.reply("ВСЁ УПАЛО!");
            return;
        }
        waiter.waitForEvent(parser.getNextEventClass(), e -> isAuthor(e, replyTarget.getAuthor()), event -> {
            replyTarget.reply(parser.parse(event));
            if (parser.isComplete()) {
                parser.apply(replyTarget);
            } else {
                step(replyTarget, parser);
            }
        });
    }

    private boolean isAuthor(GenericGuildMessageEvent event, User user) {
        if (event instanceof GuildMessageReceivedEvent) {
            return ((GuildMessageReceivedEvent) event).getAuthor().equals(user);
        } else if (event instanceof GuildMessageReactionAddEvent) {
            return ((GuildMessageReactionAddEvent) event).getUser().equals(user);
        } else {
            return false;
        }
    }
}
