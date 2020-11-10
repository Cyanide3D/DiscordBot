package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
import cyanide3d.listener.CommandListener;
import cyanide3d.service.PermissionService;

import java.util.Arrays;
import java.util.Locale;

public class SetPrefix extends Command {

    private Localization localization = new Localization(new Locale("ru", "RU"));
    String[] availablePrefixes = {"$",",","+"};

    public SetPrefix(){
        this.name = "setprefix";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().checkPermission(event.getMember(), Permission.ADMIN)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        if (event.getArgs().equals("") || !checkPrefix(event.getArgs())){
            event.reply("Некорректный префикс.");
            return;
        }
        CommandListener.getInstance().setEvent(event).setPrefix(event.getArgs());
        event.reply("Префикс успешно установлен!");
    }
    private boolean checkPrefix(String prefix){
        for(String availablePrefix : availablePrefixes){
            if(availablePrefix.equalsIgnoreCase(prefix)) return true;
        }
        return false;
    }
}
