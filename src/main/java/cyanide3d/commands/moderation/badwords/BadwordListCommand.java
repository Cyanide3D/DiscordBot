package cyanide3d.commands.moderation.badwords;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.repository.service.PermissionService;
import cyanide3d.repository.service.SpeechService;
import cyanide3d.util.Permission;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

public class BadwordListCommand extends Command {

    private final Localization localization = Localization.getInstance();

    public BadwordListCommand() {
        this.name = "listword";
        this.aliases = new String[]{"listbadwords"};
        this.arguments = "[word]";
        this.help = localization.getMessage("listword.help");
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }

        final SpeechService service = SpeechService.getInstance();
        final Set<String> badWords = service.getBadWords(event.getGuild().getId());

        String list = badWords.isEmpty()
                ? "***Список запрещенных слов пуст.***"
                : StringUtils.join(badWords, ", ");
        event.reply(localization.getMessage("listword.list") + "\n" + list);
    }

}