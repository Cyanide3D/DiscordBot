package cyanide3d.commands.basic;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.repository.model.UserEntity;
import cyanide3d.repository.service.UserService;
import net.dv8tion.jda.api.entities.Member;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Comparator;
import java.util.List;

public class LeaderBoardCommand extends Command {
    public LeaderBoardCommand() {
        this.name = "leaderboard";
    }

    @Override
    protected void execute(CommandEvent event) {
        StringBuilder leaderBoard = new StringBuilder();
        UserService service = UserService.getInstance();
        List<UserEntity> users = service.getAllUsers(event.getGuild().getId());
        users.sort(Comparator.comparing(UserEntity::getLvl).thenComparing(UserEntity::getExp).reversed());
        for (UserEntity user : users) {
            final Member member = event.getGuild().getMemberById(user.getUserId());
            if (member != null) {
                String username = ObjectUtils.defaultIfNull(member.getNickname(), member.getUser().getName());
                leaderBoard.append(username)
                        .append(" : ")
                        .append(user.getLvl())
                        .append(" ур. | ")
                        .append(user.getExp())
                        .append(" ед. опыта.\n");
            }
        }
        event.reply(leaderBoard.toString());
    }
}