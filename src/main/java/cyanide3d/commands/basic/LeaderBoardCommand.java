package cyanide3d.commands.basic;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.repository.model.UserEntity;
import cyanide3d.repository.service.UserService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class LeaderBoardCommand extends Command {
    public LeaderBoardCommand() {
        this.name = "leaderboard";
    }

    @Override
    protected void execute(CommandEvent event) {

        UserService service = UserService.getInstance();
        List<UserEntity> users = service.getAllUsers(event.getGuild().getId());

        event.reply(getUsersAsString(users, event.getGuild()));
    }

    private String getUsersAsString(List<UserEntity> users, Guild guild) {
        return users.stream().filter(e -> isNonNullMember(e, guild))
                .map(e -> formatUsers(e, guild))
                .collect(Collectors.joining("\n"));
    }

    private String formatUsers(UserEntity entity, Guild guild) {
        final Member member = guild.getMemberById(entity.getUserId());
        String username = ObjectUtils.defaultIfNull(member.getNickname(), member.getUser().getName());
        return String.format("`%-35.35s`**%d ур.** | **%d ед оп.**", username, entity.getLvl(), entity.getExp());
    }

    private boolean isNonNullMember(UserEntity entity, Guild guild) {
        return guild.getMemberById(entity.getUserId()) != null;
    }

}