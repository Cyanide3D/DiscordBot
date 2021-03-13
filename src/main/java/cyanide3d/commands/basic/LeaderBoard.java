package cyanide3d.commands.basic;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.commands.basic.EmbedTemplates;
import cyanide3d.dto.UserEntity;
import cyanide3d.model.User;
import cyanide3d.service.UserService;
import net.dv8tion.jda.api.entities.Member;

import java.util.Comparator;
import java.util.List;

public class LeaderBoard extends Command {
    public LeaderBoard() {
        this.name = "leaderboard";
    }

    @Override
    protected void execute(CommandEvent event) {
        StringBuilder leaderBoard = new StringBuilder();
        UserService service = new UserService(UserEntity.class, event.getGuild().getId());
        List<UserEntity> users = service.getAllUsers();
        users.sort(Comparator.comparing(UserEntity::getLvl).thenComparing(UserEntity::getExp).reversed());
        for (UserEntity user : users){
            final Member member = event.getGuild().getMemberById(user.getId());
            if(member != null) {
                String username = member.getUser().getName();
                leaderBoard.append(username)
                        .append(" : ")
                        .append(user.getLvl())
                        .append(" ур. | ")
                        .append(user.getExp())
                        .append(" ед. опыта.\n");
                if (leaderBoard.length() >= 900){
                    event.reply(EmbedTemplates.leaderBoard(leaderBoard.toString()));
                    leaderBoard.delete(0,leaderBoard.length()-1);
                }
            }
        }
        event.reply(EmbedTemplates.leaderBoard(leaderBoard.toString()));
    }
}