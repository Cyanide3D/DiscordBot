package cyanide3d.model;

public class User {
    private final String id;
    private int experience;
    private int level;

    public User(String id, int level, int experience) {
        this.id = id;
        this.experience = experience;
        this.level = level;
    }

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void incrementExp() {
        experience++;
    }

    public void incrementExp(int exp) {
        experience += exp;
    }

    public void levelUp() {
        experience = 0;
        level++;
    }
}
