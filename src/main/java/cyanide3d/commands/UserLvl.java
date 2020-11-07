package cyanide3d.commands;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.actions.ExpTemplateAction;
import cyanide3d.service.GainExpService;

import java.io.File;

public class UserLvl extends Command {
    public UserLvl() {
        this.name = "level";
        this.help = "Просмотр уровня.";
    }

    @Override
    protected void execute(CommandEvent event) {
        ExpTemplateAction makeExpTemplateAction = new ExpTemplateAction();
        int userlevel = GainExpService.getInstance().getUserLvl(event.getAuthor().getId());
        int userexp = GainExpService.getInstance().getUserExp(event.getAuthor().getId());
        String userAvatarUrl = event.getAuthor().getAvatarUrl();
        String username = event.getAuthor().getName();
        makeExpTemplateAction.makeTemplate(username,userlevel,userexp,userAvatarUrl);
        event.reply(new File("picture\\output.jpg"),"output.jpg");
    }
}