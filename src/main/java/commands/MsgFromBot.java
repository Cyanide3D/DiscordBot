package commands;

import com.jagrosh.jdautilities.command.Command;
import conf.Permission;
import conf.UserAcessToCommand;
import com.jagrosh.jdautilities.command.CommandEvent;

import java.util.Locale;
import java.util.ResourceBundle;

public class MsgFromBot extends Command {

    ResourceBundle bundle = ResourceBundle.getBundle("localization",new Locale("ru","RU"));

    public MsgFromBot(){
        this.name = "msg";
        this.aliases = new String[]{"message"};
        this.arguments = "[message]";
        this.help = bundle.getString("msg.help");
    }
    @Override
    protected void execute(CommandEvent event) {
        UserAcessToCommand usrAccess = UserAcessToCommand.getInstance();
        if(usrAccess.getAccess(event.getAuthor().getId(), Permission.ADMIN)) {
            String msg = event.getMessage().getContentRaw();
            event.getMessage().delete().queue();
            event.reply(msg.replaceAll("[$]msg ",""));
        }else{
            event.reply(String.format(bundle.getString("accessDenied"),this.name));
        }
    }
}
