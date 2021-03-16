package cyanide3d.commands.mod.entrymessage;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.service.EntryMessageService;
import org.apache.commons.lang3.StringUtils;

public class AddEntryMessageCommand extends Command {

    public AddEntryMessageCommand() {
        this.name = "addentrymessage";
        this.aliases = new String[]{"aem"};
    }

    @Override
    protected void execute(CommandEvent event) {
        String[] args = event.getArgs().split(" ");

        if (args.length < 2) {
            event.reply("Проверьте правильность введёных аргументов.");
            return;
        }

        EntryMessageService service = EntryMessageService.getInstance();
        service.add(args[0], StringUtils.substringAfter(event.getArgs(), args[0] + " "), event.getGuild().getId());

        event.reply("Сообщение успешно добавлено!");
    }
}
