package cyanide3d.handlers.listener.receivedmessage.dialog;

import com.fasterxml.jackson.databind.ObjectMapper;
import cyanide3d.model.json.DogModel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.net.URL;

public class DogPhotoInterceptor implements MessageInterceptor{
    @Override
    public void execute(GuildMessageReceivedEvent event) {
        if (isNotAbort(event)) {
            String message;
            try {
                URL url = new URL("https://dog.ceo/api/breeds/image/random");
                message = new ObjectMapper().readValue(url, DogModel.class).getMessage();
            } catch (IOException e) {
                message = "ERROR:Что то пошло не так.";
            }
            event.getChannel().sendMessage(message).queue();
        }
    }

    private boolean isNotAbort(GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        return !event.getAuthor().isBot()
                && message.contains("ай")
                && message.contains("фото")
                && message.contains("собаки")
                && message.split(" ").length < 4;
    }

}
