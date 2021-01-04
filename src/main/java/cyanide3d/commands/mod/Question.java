package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
import cyanide3d.service.PermissionService;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;

public class Question extends Command{

    private final Localization localization = Localization.getInstance();

    public Question() {
        this.name = "question";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().checkPermission(event.getMember(), Permission.MODERATOR)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        if (event.getArgs().isEmpty()){
            event.reply("Необходимо после комманды ввести текст для опроса.");
            return;
        }
        event.getMessage().delete().queue();
        Message message = event.getTextChannel().sendMessage(event.getGuild().getRoleById("664863242199236629").getAsMention() + " **" + event.getArgs() + "**").complete();
        message.addReaction("\uD83D\uDC9A").queue();
        message.addReaction("\uD83D\uDEAB").queue();
    }
}
