package events;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import conf.DatabaseConnection;

public class BadWordsEvent extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
        DatabaseConnection db = new DatabaseConnection();
        if (!e.getAuthor().isBot()) {
            String[] words = e.getMessage().getContentRaw().split("[:,./ !?]");
            for (int i = 0; i < words.length; i++) {
                if (db.checkBadWords(words[i]) && !words[0].equalsIgnoreCase("$removeword")) {
                    e.getMessage().delete().queue();
                    e.getChannel().sendMessage("Рот на минус, шакал!").queue();
                }
            }
        }
    }
}
