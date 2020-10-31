package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import events.BadWordsEvent;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class ListBadWords extends Command {

    ResourceBundle bundle = ResourceBundle.getBundle("localization",new Locale("ru","RU"));

    public ListBadWords() {
        this.name = "listword";
        this.aliases = new String[]{"listbadwords"};
        this.arguments = "[word]";
        this.help = bundle.getString("listword.help");
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        BadWordsEvent bve = BadWordsEvent.getInstance();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.RED);
        eb.setTitle(bundle.getString("listword.list"));
        for (String words : bve.badWords) {
            eb.addField("", words, false);
        }
        commandEvent.reply(eb.build());
    }

}
