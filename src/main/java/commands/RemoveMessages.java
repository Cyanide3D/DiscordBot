package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import conf.Permission;
import conf.UserAcessToCommand;
import net.dv8tion.jda.api.entities.Message;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

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
        UserAcessToCommand usrAccess = UserAcessToCommand.getInstance();
        if(usrAccess.getAccess(e.getAuthor().getId(), Permission.MODERATOR)) {
            e.getChannel().getIterableHistory().takeAsync(Integer.parseInt(e.getArgs()) + 1).thenAccept(e.getChannel()::purgeMessages);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
            e.reply(String.format(bundle.getString("clear.successfully"),e.getArgs()));
        }else{
            e.reply(String.format(bundle.getString("accessDenied"),this.name));
        }
    }
}
