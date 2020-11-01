package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.conf.Permission;
import cyanide3d.conf.UserAccessToCommand;

import java.util.Locale;
import java.util.ResourceBundle;

public class RemoveMessages extends Command {

    ResourceBundle bundle = ResourceBundle.getBundle("localization",new Locale("ru","RU"));

    public RemoveMessages() {
        this.name = "clear";
        this.aliases = new String[]{"clearmessage"};
        this.arguments = "[count]";
        this.help = bundle.getString("clear.help");
    }

    @Override
    protected void execute(CommandEvent e) {
        UserAccessToCommand userAccess = UserAccessToCommand.getInstance();
        if(userAccess.getAccess(e.getMember(), Permission.MODERATOR)) {
            e.getChannel().getIterableHistory().takeAsync(Integer.parseInt(e.getArgs()) + 1).thenAccept(e.getChannel()::purgeMessages);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            e.reply(String.format(bundle.getString("clear.successfully"), e.getArgs()));
        }else{
            e.reply(String.format(bundle.getString("accessDenied"),this.name));
        }
    }
}
