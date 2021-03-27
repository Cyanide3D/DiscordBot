package cyanide3d.commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.repository.service.PermissionService;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.entities.Member;

import java.util.List;

public class BanCommand extends Command {

    private final Localization localization = Localization.getInstance();
    private final int REQUIRED_ARGS_SIZE = 2;
    private final int MIN_MENTION_USERS = 1;
    private final int REASON_INDEX = 1;

    public BanCommand() {
        this.name = "ban";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.ADMIN, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }

        if (event.getArgs().isEmpty()) {
            event.reply("Синтаксис команды:" +
                    "\n`$ban [@user] [reason]`");
            return;
        }

        List<Member> mentionedMembers = event.getMessage().getMentionedMembers();
        String[] args = event.getArgs().split(" ");

        if (mentionedMembers.isEmpty() || args.length != REQUIRED_ARGS_SIZE || mentionedMembers.size() > MIN_MENTION_USERS) {
            event.reply("Ошибка в синтаксисе команды.");
            return;
        }

        mentionedMembers.get(0).ban(0, args[REASON_INDEX]).queue();
    }
}
