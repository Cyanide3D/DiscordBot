package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.entities.Member;

public class About extends Command {
    public About(){
        this.name = "about";
        this.aliases = new String[]{"aboutbot"};
        this.help = "Информация о боте.";
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        Member usr = commandEvent.getGuild().getMemberById("534894366448156682");
        commandEvent.reply("Бот создавался с нуля исключительно для того, что бы понабраться опыта.\n" +
                "Если имеются какие либо претензии или предложения для улучшения бота то смело обращайтесь к автору бота :)\n\n" +
                "***Автор:*** *" + usr.getUser().getAsMention() + "*\n" +
                "***Дата создания:*** *26.10.2020*");
    }
}
