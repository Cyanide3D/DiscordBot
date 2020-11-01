package cyanide3d.events;

import cyanide3d.Localization;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.Locale;


public class BlackList {
    private static Localization localization = new Localization(new Locale("ru", "RU"));

    public static EmbedBuilder add(String nickname, String reason, String moderator) {
        //TODO really add user to blacklist
        return new EmbedBuilder()
                .setTitle(localization.getMessage("blacklist.title"))
                .addField(localization.getMessage("blacklist.nick"), nickname, false)
                .setColor(Color.ORANGE)
                .addField(localization.getMessage("blacklist.reason"), reason, false)
                .setFooter(localization.getMessage("blacklist.form"))
                .setDescription(localization.getMessage("blacklist.add", nickname, moderator));
    }
}
