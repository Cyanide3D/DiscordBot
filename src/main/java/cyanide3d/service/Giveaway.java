package cyanide3d.service;

import jdk.dynalink.beans.StaticClass;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class Giveaway {
    private static Giveaway INSTANCE;
    private final Map<String, Message> messages = new HashMap<>();
    private final Map<String, Stack<String>> pins = new HashMap<>();
    private final Map<String, List<Member>> reactedUsers = new HashMap<>();

    public static Giveaway getInstance() {
        if (INSTANCE == null) INSTANCE = new Giveaway();
        return INSTANCE;
    }

    public synchronized void start(String[] pins, String guildId) {
        clear(guildId);
        this.pins.computeIfAbsent(guildId, e ->
                new Stack<>()).addAll(Arrays.asList(pins));
    }

    public synchronized String getReactedUserList(String guildId) {
        return reactedUsers.getOrDefault(guildId, Collections.emptyList())
                .stream().map(member -> ObjectUtils.defaultIfNull(member.getNickname(), member.getUser().getName()))
                .collect(Collectors.joining("\n"));
    }

    public synchronized String getPinForMember(Member member, String guildId) {
        reactedUsers.getOrDefault(guildId, new ArrayList<>()).add(member);
        reactedUsers.computeIfAbsent(guildId, k -> new ArrayList<>()).add(member);

        String pin = pins.get(guildId).pop();

        if (isEnd(guildId)) {
            messages.get(guildId).delete().queue();
            pins.remove(guildId);
        }

        return pin;
    }

    private void clear(String guildId) {
        pins.getOrDefault(guildId, new Stack<>()).clear();
        reactedUsers.getOrDefault(guildId, new ArrayList<>()).clear();

        messages.computeIfPresent(guildId, (k, v) -> {
            v.delete().queue();
            return null;
        });

    }

    public synchronized boolean isEnd(String guildId) {
        return pins.getOrDefault(guildId, new Stack<>()).isEmpty() && messages.containsKey(guildId);
    }

    public synchronized boolean isEmpty() {
        return pins.isEmpty();
    }

    public Message getMessage(String guildId) {
        return messages.get(guildId);
    }

    public void setMessage(Message message, String guildId) {
        messages.put(guildId, message);
    }

    public String getPinList(String guildId) {
        return StringUtils.join(pins.getOrDefault(guildId, new Stack<>()), "\n");
    }

    public List<Member> getReactedUsers(String guildId) {
        return reactedUsers.getOrDefault(guildId, Collections.emptyList());
    }
}
