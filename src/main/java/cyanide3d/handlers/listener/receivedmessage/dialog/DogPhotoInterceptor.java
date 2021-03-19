package cyanide3d.handlers.listener.receivedmessage.dialog;

import com.fasterxml.jackson.databind.ObjectMapper;
import cyanide3d.model.json.DogModel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.net.URL;

public class DogPhotoInterceptor implements MessageInterceptor{
    @Override
    public void execute(GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if (!event.getAuthor().isBot() && message.contains("ай") && message.contains("фото") && message.contains("собаки") && message.split(" ").length < 4) {
            try {
                DogModel dog = new ObjectMapper().readValue(new URL("https://dog.ceo/api/breeds/image/random"), DogModel.class);
                event.getChannel().sendMessage(dog.getMessage()).queue();
            } catch (IOException e) {
                event.getChannel().sendMessage("ERROR:Что то пошло не так.").queue();
            }
        }
    }
}
