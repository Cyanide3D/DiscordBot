package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import conf.Permission;
import conf.UserAcessToCommand;
import net.dv8tion.jda.api.entities.User;

public class SupportCommand extends Command {
    public SupportCommand(){
        this.name = "sc";
        this.help = "sup";
        this.hidden = true;
    }
    @Override
    protected void execute(CommandEvent e) {
        UserAcessToCommand usrAccess = UserAcessToCommand.getInstance();
        if (!usrAccess.getAccess(e.getAuthor().getId(), Permission.OWNER)){
            return;
        }

        User usr = e.getGuild().getMemberById("320967415863312386").getUser();
        usr.openPrivateChannel().queue(msg->{
            msg.sendMessage("pidor").queue();
        });
    }
}
