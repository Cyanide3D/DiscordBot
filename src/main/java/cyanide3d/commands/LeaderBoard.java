package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.model.UserLevel;
import cyanide3d.service.GainExpService;

import java.util.Comparator;
import java.util.List;

public class LeaderBoard extends Command {
    public LeaderBoard() {
        this.name = "leaderboard";
    }

    @Override
    protected void execute(CommandEvent event) {
        StringBuilder leaderBoard = new StringBuilder();
        List<UserLevel> userLevels = GainExpService.getInstance().getUsers();
        userLevels.sort(Comparator.comparing(UserLevel::getUserLvl).reversed());
        for (UserLevel user : userLevels){
            if(!event.getGuild().getMemberById(user.getUserId()).equals(null)) {
                String username = event.getGuild().getMemberById(user.getUserId()).getUser().getName();
                leaderBoard.append(username + " : " + user.getUserLvl() + " ур. | " + user.getUserExp() + " ед. опыта.\n");
            }
        }
        event.reply(new EmbedTemplates().leaderBoard(leaderBoard.toString()));
    }
}
