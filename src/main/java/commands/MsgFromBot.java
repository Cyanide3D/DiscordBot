package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import conf.Permission;
import conf.UserAccessToCommand;

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
    protected void execute(CommandEvent e) {
        UserAccessToCommand userAccess = UserAccessToCommand.getInstance();
        if(userAccess.getAccess(e.getMember(), Permission.ADMIN)) {
            String msg = e.getMessage().getContentRaw();
            e.getMessage().delete().queue();
            e.reply(msg.replaceAll("[$]msg ", ""));
        }else{
            e.reply(String.format(bundle.getString("accessDenied"),this.name));
        }
    }
}
