package cyanide3d.handlers.listener.messagereaction;

import cyanide3d.service.AutoroleService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.react.GenericGuildMessageReactionEvent;

public class AutoroleHandler implements MessageReactionHandler{
    @Override
    public void execute(GenericGuildMessageReactionEvent event) {
        final AutoroleService service = AutoroleService.getInstance();
        final String roleId = service.getRoleId(event.getMessageId(), event.getReactionEmote().getName(), event.getGuild().getId());

        if (roleId == null) {
            return;
        }

        Role role = event.getGuild().getRoleById(roleId);

        if (role == null || event.getMember() == null || event.getUser().isBot()) {
            return;
        }

        giveClickReactionRole(role, event.getMember(), event.getGuild());
    }

    private void giveClickReactionRole(Role role, Member member, Guild guild) {
        String message;
        if (member.getRoles().contains(role)) {
            guild.removeRoleFromMember(member, role).queue();
            message = "Роль успешно убрана.";
        } else {
            guild.addRoleToMember(member, role).queue();
            message = "Роль успешно выдана.";
        }

        member.getUser().openPrivateChannel()
                .queue(channel -> channel.sendMessage(message).queue());
    }
}
