package commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import conf.DatabaseConnection;
import net.dv8tion.jda.api.EmbedBuilder;

import java.sql.SQLException;
import java.util.ArrayList;

public class ListBadWords extends Command {
    public ListBadWords(){
        this.name = "listword";
        this.aliases = new String[]{"listbadwords"};
        this.arguments = "[word]";
        this.help = "Просмотр списка запрещённых слов.";
    }

    @Override
    protected void execute(CommandEvent commandEvent) {
        DatabaseConnection db = new DatabaseConnection();
        EmbedBuilder eb = new EmbedBuilder();
        try {
            ArrayList<String> badWords = db.listBadWords();
            eb.setTitle("Список запрещённых слов: ");
            for(int i = 0; i < badWords.size(); i++){
                eb.addField("",badWords.get(i),false);
            }
            commandEvent.reply(eb.build());

        } catch (SQLException throwables) {
            commandEvent.reply("Нет доступа к базе данных.");
        }
    }
}
