package cyanide3d.msg;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class UserStats implements Externalizable {
    String name;
    String asTag;
    String avatarUrl;
    int exp;
    int level;

    public UserStats(){}

    public UserStats(String name, String asTag, String avatarUrl, int exp, int level) {
        this.name = name;
        this.asTag = asTag;
        this.avatarUrl = avatarUrl;
        this.exp = exp;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAsTag() {
        return asTag;
    }

    public void setAsTag(String asTag) {
        this.asTag = asTag;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeObject(asTag);
        out.writeObject(avatarUrl);
        out.writeInt(exp);
        out.writeInt(level);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        asTag = (String) in.readObject();
        avatarUrl = (String) in.readObject();
        exp = in.readInt();
        level = in.readInt();
    }
}