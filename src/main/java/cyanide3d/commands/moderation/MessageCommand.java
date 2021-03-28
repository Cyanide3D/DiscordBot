package cyanide3d.commands.moderation;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.repository.service.PermissionService;
import cyanide3d.util.Permission;
import net.dv8tion.jda.api.entities.Message;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class MessageCommand extends Command {

    private final Localization localization = Localization.getInstance();
    private final PermissionService service;

    public MessageCommand() {
        service = PermissionService.getInstance();
        this.name = "msg";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (service.isAvailable(event.getMember(), Permission.ADMIN, event.getGuild().getId())) {
            event.reply(event.getArgs());
            printAttachment(event);

            event.getMessage().delete().queue();
        } else {
            event.reply(localization.getMessage("accessDenied", name));
        }
    }

    private void printAttachment(CommandEvent event) {
        getAttachmentFile(event.getMessage().getAttachments()).ifPresent(a -> {
            event.reply(a, a.getName());
            a.delete();
        });
    }

    private Optional<File> getAttachmentFile(List<Message.Attachment> attachments) {
        try {
            return Optional.ofNullable(attachments.get(0).downloadToFile().get());
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
