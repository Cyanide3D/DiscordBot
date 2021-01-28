package cyanide3d.commands.mod;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.conf.Logging;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;

import java.nio.charset.Charset;
import java.util.logging.Logger;

public class SupportCommand extends Command {

    public SupportCommand() {
        this.name = "sc";
    }


    @Override
    protected void execute(CommandEvent event) {
//        if (!event.getAuthor().getId().equals("534894366448156682")){
//            return;
//        }
//        Member memberById = event.getGuild().getMemberById("397153487747809292");
//        if (memberById != null){
//            event.reply("user found");
//            User yanky = memberById.getUser();
//            yanky.openPrivateChannel().queue(privateChannel -> {
//                for (int i = 0; i < 150; i++) {
//                    privateChannel.sendMessage("Коммунизм не пройдет").queue();
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        System.out.println("Wow, SC RIP");
//                    }
//                }
//                event.reply("150 messages has been sended.");
//            });
//        }
    }
}
