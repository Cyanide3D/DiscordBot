package cyanide3d.commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.repository.service.PermissionService;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.entities.Message;

public class ClearCommand extends Command {

    private final Localization localization = Localization.getInstance();

    public ClearCommand() {
        this.name = "clear";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }
        if (event.getArgs().isEmpty()) {
            event.reply("Необходимо указать кол-во сообщений.");
            return;
        }
        try {
            event.getChannel().getIterableHistory().takeAsync(Integer.parseInt(event.getArgs()) + 1).thenAccept(messages -> {
                for (Message message : messages) {
                    message.delete().queue();
//                    event.getChannel().purgeMessages(message);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).thenAccept(wops -> event.reply(localization.getMessage("clear.successfully", event.getArgs())));
        } catch (Exception e) {
            event.reply("Что то пошло не так.");
        }
    }
}
