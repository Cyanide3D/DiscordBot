package cyanide3d.actions;

import com.fasterxml.jackson.databind.ObjectMapper;
import cyanide3d.dto.ActionEntity;
import cyanide3d.model.CatModel;
import cyanide3d.model.DogModel;
import cyanide3d.service.ActionService;
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
        ActionService actionService = new ActionService(ActionEntity.class, event.getGuild().getId());
        if (!actionService.getState("answer")){
            return;
        }
        String message = event.getMessage().getContentRaw();
        int wordCount = message.split(" ").length;
        if (!event.getAuthor().isBot() && message.contains("ай") && message.contains("фото") && message.contains("кота") && wordCount < 4) {
            try {
                String url = new ObjectMapper().readValue(new URL("https://api.thecatapi.com/v1/images/search"), CatModel[].class)[0].getUrl();
                event.getChannel().sendMessage(url).queue();
            } catch (IOException e) {
                event.getChannel().sendMessage("ERROR:Что то пошло не так.").queue();
            }
        }
        if (!event.getAuthor().isBot() && message.contains("ай") && message.contains("фото") && message.contains("собаки") && wordCount < 4) {
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
        if (!event.getAuthor().isBot() && message.contains("ай") && message.contains("аватар")) {
            String url;
            if (!event.getMessage().getMentionedMembers().isEmpty()) {
                url = event.getMessage().getMentionedMembers().get(0).getUser().getEffectiveAvatarUrl();
            } else if (wordCount < 3){
                url = event.getAuthor().getEffectiveAvatarUrl();
            } else {
                return;
            }
            event.getChannel().sendMessage(url).queue();
        }
    }
}
