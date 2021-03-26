package cyanide3d.commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.service.PermissionService;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.entities.Member;

import java.util.List;

public class KickCommand extends Command {

    private final Localization localization = Localization.getInstance();

    public KickCommand() {
        this.name = "kick";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }

        if (event.getArgs().isEmpty()) {
            event.reply("Синтаксис команды:" +
                    "\n`$kick [@user] [reason]`");
            return;
        }

        List<Member> mentionedMembers = event.getMessage().getMentionedMembers();
        String[] args = event.getArgs().split(" ");

        if (mentionedMembers.isEmpty() || args.length != 2 || mentionedMembers.size() > 1) {
            event.reply("Ошибка в синтаксисе команды.");
            return;
        }

        mentionedMembers.get(0).kick(args[1]).queue();
    }
}
