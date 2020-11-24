package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
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
        List<User> users = UserService.getInstance().getAllUsers();
        users.sort(Comparator.comparing(User::getLevel).reversed());
        for (User user : users){
            final Member member = event.getGuild().getMemberById(user.getId());
            if(member != null) {
                String username = member.getUser().getName();
                leaderBoard.append(username)
                        .append(" : ")
                        .append(user.getLevel())
                        .append(" ур. | ")
                        .append(user.getExperience())
                        .append("ед. опыта.\n");
                if (leaderBoard.length() >= 900) {
                    event.reply(EmbedTemplates.leaderBoard(leaderBoard.toString()));
                    leaderBoard.delete(0, leaderBoard.length() - 1);
                }
            }
        }
        event.reply(EmbedTemplates.leaderBoard(leaderBoard.toString()));
    }
}