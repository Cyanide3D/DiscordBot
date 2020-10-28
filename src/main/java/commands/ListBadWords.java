package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import conf.DatabaseConnection;
import events.BadWordsEvent;
import net.dv8tion.jda.api.EmbedBuilder;

import java.sql.SQLException;
import java.util.ArrayList;

public class ListBadWords extends Command {
    public ListBadWords() {
        this.name = "listword";
        this.aliases = new String[]{"listbadwords"};
        this.arguments = "[word]";
        this.help = "Просмотр списка запрещённых слов.";
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        BadWordsEvent bve = new BadWordsEvent();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Список запрещённых слов: ");
        for (String words : bve.badWords) { //ВОТ ТУТ СЕТ ПУСТОЙ УЖЕ.
            eb.addField("", words, false);
        }
        commandEvent.reply(eb.build());
    }
}
