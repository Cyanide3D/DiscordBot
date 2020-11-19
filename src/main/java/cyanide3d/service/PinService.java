package cyanide3d.service;

import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PinService {
    private static PinService INSTANCE;
    List<String> pins = Collections.synchronizedList(new ArrayList<>());
    List<User> reactedUser = Collections.synchronizedList(new ArrayList<>());


    public List<User> getReactedUser() {
        return reactedUser;
    }

    public void setReactedUser(User user) {
        reactedUser.add(user);
    }

    public void setPins(String[] pins){
        this.pins.addAll(Arrays.stream(pins).collect(Collectors.toList()));
    }

    public void removePin(int index){
        pins.remove(index);
    }

    public void clear(){
        pins.clear();
        reactedUser.clear();
    }

    public List<String> getPins(){
        return pins;
    }

    public static PinService getInstance(){
        if (INSTANCE == null) INSTANCE = new PinService();
        return INSTANCE;
    }
}
