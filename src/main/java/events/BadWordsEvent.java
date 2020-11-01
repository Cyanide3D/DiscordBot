package events;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import conf.DatabaseConnection;

import java.sql.SQLException;
import java.util.*;


public class BadWordsEvent extends ListenerAdapter {

    private static BadWordsEvent instance;

    public Set<String> badWords = new HashSet<>();

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {

        ResourceBundle bundle = ResourceBundle.getBundle("localization",new Locale("ru","RU"));

        if (!e.getAuthor().isBot()) {
            String[] words = e.getMessage().getContentRaw().split("[:,./ !?]");
            for (int i = 0; i < words.length; i++) {
                if (badWords.contains(words[i].toLowerCase()) && !words[0].equalsIgnoreCase("$removeword")) {
                    e.getMessage().delete().queue();
                    e.getChannel().sendMessage(bundle.getString("badword.answer")).queue();
                }
            }
        }
    }


    public void setBadWords(){
        badWords.clear();
        DatabaseConnection db = new DatabaseConnection();
        try {
            ArrayList<String> badWordsList = db.listBadWords();
            for(int i = 0; i < badWordsList.size(); i++){
                badWords.add(badWordsList.get(i));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static BadWordsEvent getInstance(){
        if(instance == null){
            instance = new BadWordsEvent();
        }
        return instance;
    }

}
