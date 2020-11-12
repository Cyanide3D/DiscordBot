package cyanide3d.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import cyanide3d.model.CatModel;
import cyanide3d.model.DogModel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.net.URL;

public class AnswerAction implements Action {
    GuildMessageReceivedEvent event;

    public AnswerAction(GuildMessageReceivedEvent event) {
        this.event = event;
    }

    @Override
    public void execute() {
        String message = event.getMessage().getContentRaw();
        if (!event.getAuthor().isBot() && message.contains("дай") && message.contains("фото") && message.contains("кота")) {
            try {
                String url = new ObjectMapper().readValue(new URL("https://api.thecatapi.com/v1/images/search"), CatModel[].class)[0].getUrl();
                event.getChannel().sendMessage(url).queue();
            } catch (IOException e) {
                event.getChannel().sendMessage("ERROR:Что то пошло не так.").queue();
            }
        }
        if (!event.getAuthor().isBot() && message.contains("дай") && message.contains("фото") && message.contains("собаки")) {
            try {
                DogModel dog = new ObjectMapper().readValue(new URL("https://dog.ceo/api/breeds/image/random"), DogModel.class);
                event.getChannel().sendMessage(dog.getMessage()).queue();
            } catch (IOException e) {
                event.getChannel().sendMessage("ERROR:Что то пошло не так.").queue();
            }
        }
        if (!event.getAuthor().isBot() && message.contains("бот") && message.contains("лох")) {
            event.getChannel().sendMessage(".i. Соси жопу мудак.").queue();
        }
        if (!event.getAuthor().isBot() && message.contains("кек")) {
            event.getChannel().sendMessage("Тише будь, ишь расКЕКался тут.").queue();
        }
        if (!event.getAuthor().isBot() && message.contains("дай") && message.contains("аватар")) {
            String url;
            if (!event.getMessage().getMentionedMembers().isEmpty()){
                url = event.getMessage().getMentionedMembers().get(0).getUser().getEffectiveAvatarUrl();
            } else {
                url = event.getAuthor().getEffectiveAvatarUrl();
            }
            event.getChannel().sendMessage(url).queue();
        }
    }
}
