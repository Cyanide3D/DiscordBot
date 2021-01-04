package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.conf.Permission;
import cyanide3d.exceprtion.UnsupportedActionException;
import cyanide3d.exceprtion.UnsupportedStateException;
import cyanide3d.service.EnableActionService;
import cyanide3d.service.PermissionService;

public class ActivateAction extends Command {
    private final Localization localization = Localization.getInstance();

    public ActivateAction(){
        this.name = "activate";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().checkPermission(event.getMember(), Permission.MODERATOR)) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        String[] args = event.getArgs().split(" ");
        if(args.length!=2){
            event.reply("Неправильный синтаксис команды!");
            return;
        }
        try {
            EnableActionService.getInstance().setState(args[0], args[1]);
            event.reply("Состояние функции успешно обновлено!");
        } catch (UnsupportedStateException e) {
            event.reply(e.getMessage());
        } catch (UnsupportedActionException ex) {
            event.reply(ex.getMessage());
        } catch (ArrayIndexOutOfBoundsException exe) {
            event.reply("Нужно больше аргументов.");
        }

    }
}
