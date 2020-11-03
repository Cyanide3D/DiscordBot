package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;

import java.util.Locale;
import java.util.ResourceBundle;

public class MsgFromBot extends Command {

    private Localization localization = new Localization(new Locale("ru", "RU"));

    public MsgFromBot(){
        this.name = "msg";
        this.aliases = new String[]{"message"};
        this.arguments = "[message]";
        this.help = localization.getMessage("msg.help");
    }
    @Override
    protected void execute(CommandEvent event) {
            event.getMessage().delete().queue();
            event.reply(event.getMessage().getContentRaw().replaceAll("[$]msg ", ""));
    }
}
