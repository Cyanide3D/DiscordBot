package cyanide3d.handlers.listener.receivedmessage.dialog;

import com.fasterxml.jackson.databind.ObjectMapper;
import cyanide3d.model.json.CatModel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.io.IOException;
import java.net.URL;

public class CatPhotoInterceptor implements MessageInterceptor {
    @Override
    public void execute(GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        if (!event.getAuthor().isBot() && message.contains("ай") && message.contains("фото") && message.contains("кота") && message.split(" ").length < 4) {
            try {
                String url = new ObjectMapper().readValue(new URL("https://api.thecatapi.com/v1/images/search"), CatModel[].class)[0].getUrl();
                event.getChannel().sendMessage(url).queue();
            } catch (IOException e) {
                event.getChannel().sendMessage("ERROR:Что то пошло не так.").queue();
            }
        }
    }
}
