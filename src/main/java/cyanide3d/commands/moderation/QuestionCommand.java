package cyanide3d.commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.repository.service.PermissionService;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.entities.Message;

public class QuestionCommand extends Command{

    private final Localization localization = Localization.getInstance();

    public QuestionCommand() {
        this.name = "question";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        if (event.getArgs().isEmpty()){
            event.reply("Необходимо после комманды ввести текст для опроса.");
            return;
        }
        event.getMessage().delete().queue();
        Message message = event.getTextChannel().sendMessage( " **" + event.getArgs() + "**").complete();
        message.addReaction("\uD83D\uDC9A").queue();
        message.addReaction("\uD83D\uDEAB").queue();
    }
}
