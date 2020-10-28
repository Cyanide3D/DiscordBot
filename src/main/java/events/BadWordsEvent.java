package events;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import conf.DatabaseConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class BadWordsEvent extends ListenerAdapter {

    public Set<String> badWords = new HashSet<>(); //ВОТ ТУТ ОНО ОБЪЯВЛЯЕТСЯ, ЗНАЧЕНИЕ ПРИСВАЕВАЕТСЯ НИЖЕ.

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {

        if(badWords.size()==0){ //ЕСЛИ СЕТ ПУСТОЙ ТО ХУЯРИМ В НЕГО ЗАПРЕЩЕННЫЕ СЛОВА.
            setBadWords();
        }
        if (!e.getAuthor().isBot()) {
            String[] words = e.getMessage().getContentRaw().split("[:,./ !?]");
            for (int i = 0; i < words.length; i++) {
                if (badWords.contains(words[i]) && !words[0].equalsIgnoreCase("$removeword")) {
                    e.getMessage().delete().queue();
                    e.getChannel().sendMessage("Рот на минус, шакал!").queue();
                }
            }
        }
    }
    public void setBadWords(){ //ТУТ ЗАПОЛНЯЕМ СЕТ
        DatabaseConnection db = new DatabaseConnection();
        try {
            ArrayList<String>badWordFromBD = db.listBadWords();
            for(String words : badWordFromBD){
                badWords.add(words);
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
