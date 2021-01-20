package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.conf.Logging;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

import java.nio.charset.Charset;
import java.util.logging.Logger;

public class SupportCommand extends Command {
    Logger logger = Logging.getInstance().getLogger();

    public SupportCommand() {
        this.name = "sc";
        this.help = "sup";
        this.hidden = true;
    }


    @Override
    protected void execute(CommandEvent event) {
//        Member memberById = event.getGuild().getMemberById("397153487747809292");
//        if (memberById != null){
//            System.out.println("user found");
//            User yanky = memberById.getUser();
//            yanky.openPrivateChannel().queue(privateChannel -> {
//                for (int i = 0; i < 100; i++) {
//                    privateChannel.sendMessage("Коммунизм сосёт!").queue();
//                    try {
//                        Thread.sleep(1500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            });
//        }
    }
}
