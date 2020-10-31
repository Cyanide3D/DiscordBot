package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import conf.DatabaseConnection;
import conf.Permission;
import conf.UserAcessToCommand;

import java.util.Locale;
import java.util.ResourceBundle;

public class AddUserToBase extends Command {
    ResourceBundle bundle = ResourceBundle.getBundle("localization",new Locale("ru","RU"));

    public AddUserToBase(){
        this.name = "addperm";
        this.aliases = new String[]{"addpermission"};
        this.arguments = "[user] [permission]";
        this.help = bundle.getString("addperm.help");
    }

    @Override
    protected void execute(CommandEvent e) {
        String[] args = e.getArgs().split(" ");
        String usrID="";
        try {
            usrID = e.getMessage().getMentionedMembers().get(0).getUser().getId();
        }catch (IndexOutOfBoundsException ex){
            e.reply(bundle.getString("addperm.missmention"));
            return;
        }
        UserAcessToCommand usrAccess = UserAcessToCommand.getInstance();
        if(usrAccess.getAccess(e.getAuthor().getId(), Permission.ADMIN)) {
            if (!usrAccess.checkUserInMap(usrID)) {
                DatabaseConnection db = new DatabaseConnection();
                if (Integer.parseInt(args[args.length-1]) >= Permission.values().length || Integer.parseInt(args[args.length-1]) < 0) {
                    e.reply(bundle.getString("noperm"));
                    return;
                }
                db.insertIDs(usrID, Integer.parseInt(args[args.length-1]));
                usrAccess.setUserIDs();
                e.reply(bundle.getString("addperm.successfully"));
            }else{
                e.reply(bundle.getString("addperm.nope"));
            }
        }else{
            e.reply(String.format(bundle.getString("accessDenied"),this.name));
        }
    }
}
