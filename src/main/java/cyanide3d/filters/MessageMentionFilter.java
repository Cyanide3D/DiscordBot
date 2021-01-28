package cyanide3d.filters;

import cyanide3d.misc.MyGuild;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageMentionFilter {
    String message;
    Guild guild = MyGuild.getInstance().getGuild();

    public MessageMentionFilter(String message) {
        this.message = message;
    }





    public String toDiscord() {
        replaceAllMention();
        findVkMention();
        return message;
    }

    public String toVk() {
        findDiscordMention();
        return message;
    }






    private void findVkMention() {
        Pattern pattern = Pattern.compile("\\[id.*?\\|.*?\\]");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String mention = matcher.group();
            message = message.replace(mention, "**" + StringUtils.substringAfter(mention, "|").replace("]", "") + "**");
        }
    }

    private void findDiscordMention() {
        Pattern pattern = Pattern.compile("<@.*?>");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String mention = matcher.group();
            String id = getIdFromMention(mention);
            replaceMemberMention(id, mention);
            replaceRoleMention(id, mention);
        }
    }

    private void replaceRoleMention(String id, String mention) {
        Role role = guild.getRoleById(id);
        if (role != null) {
            message = message.replace(mention, "@" + role.getName().toUpperCase());
        }
    }

    private void replaceMemberMention(String id, String mention) {
        Member member = guild.getMemberById(id);
        if (member != null) {
            String name = member.getNickname() == null ? member.getUser().getName() : member.getNickname();
            message = message.replace(mention, name);
        }
    }

    private String getIdFromMention(String mention) {
        String id = StringUtils.substringAfter(mention, "!");
        if (id.isEmpty()) {
            id = StringUtils.substringAfter(mention, "&");
            if (id.isEmpty()) {
                id = StringUtils.substringAfter(mention, "@");
            }
        }
        return id.replace(">", "");
    }

    private void replaceAllMention() {
        List<Role> roles = guild.getRolesByName("Лентяи", true);
        if (!roles.isEmpty()) {
            Role role = roles.get(0);
            message = message.replace("@all", role.getAsMention());
        }
    }
}