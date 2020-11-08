package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.model.User;
import cyanide3d.service.UserService;

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
            if(!event.getGuild().getMemberById(user.getId()).equals(null)) {
                String username = event.getGuild().getMemberById(user.getId()).getUser().getName();
                leaderBoard.append(username + " : " + user.getLevel() + " ур. | " + user.getExperience() + " ед. опыта.\n");
            }
        }
        event.reply(new EmbedTemplates().leaderBoard(leaderBoard.toString()));
    }
}