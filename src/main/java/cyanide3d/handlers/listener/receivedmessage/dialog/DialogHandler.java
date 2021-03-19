package cyanide3d.handlers.listener.receivedmessage.dialog;

import cyanide3d.handlers.listener.receivedmessage.ReceivedMessageHandler;
import cyanide3d.service.ActionService;
import cyanide3d.util.ActionType;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

public class DialogHandler implements ReceivedMessageHandler {

    @Override
    public void execute(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot())
            return;
        ActionService actionService = ActionService.getInstance();
        if (!actionService.isActive(ActionType.DIALOG, event.getGuild().getId())){
            return;
        }
        List<MessageInterceptor> handlers = List.of(
                new DogPhotoInterceptor(),
                new CatPhotoInterceptor(),
                new AvatarInterceptor(),
                new DefaultInterceptor()
        );
        handlers.forEach(interceptor -> interceptor.execute(event));
    }
}
