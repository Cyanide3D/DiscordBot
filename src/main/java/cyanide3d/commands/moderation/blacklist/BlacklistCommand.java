package cyanide3d.commands.moderation.blacklist;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import cyanide3d.Localization;
import cyanide3d.repository.model.BlacklistEntity;
import cyanide3d.repository.service.BlacklistService;
import cyanide3d.repository.service.PermissionService;
import cyanide3d.util.Permission;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BlacklistCommand extends Command {

    private final Localization localization;
    private List<BlacklistEntity> blacklisted;
    BlacklistService service;

    public BlacklistCommand() {
        localization = Localization.getInstance();
        service = BlacklistService.getInstance();
        this.name = "blacklist";
    }

    @Override
    protected void execute(CommandEvent event) {
        if (!PermissionService.getInstance().isAvailable(event.getMember(), Permission.MODERATOR, event.getGuild().getId())) {
            event.reply(localization.getMessage("accessDenied", name));
            return;
        }

        blacklisted = service.giveBlacklistedUsers(event.getGuild().getId());

        String message = StringUtils.isNotBlank(event.getArgs())
                ? getBlacklistedUserInfo(service.findOneByUsername(event.getArgs().toLowerCase(), event.getGuild().getId()).orElse(null))
                : getBlacklistedUserList();

        event.reply(message);
    }

    private String getBlacklistedUserList() {
        return blacklisted.isEmpty()
                ? localization.getMessage("command.blacklist.empty")
                : blacklisted.stream().map(e -> String.format("`%-20.20s`**%s**", e.getName(), e.getUserId()))
                .collect(Collectors.joining("\n"));
    }

    private String getBlacklistedUserInfo(BlacklistEntity entity) {
        return entity != null
                ? localization.getMessage("command.blacklist.user.info", entity.getReason(), entity.getUserId())
                : localization.getMessage("command.blacklist.user.not.found");
    }
}