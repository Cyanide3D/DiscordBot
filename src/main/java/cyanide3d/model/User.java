package cyanide3d.model;

public class User {
    private final String id;
    private int experience;
    private int level;

    public User(String id) {
        this.id = id;
    }

    public User(String id, int level, int experience) {
        this.id = id;
        this.experience = experience;
        this.level = level;
    }

    public void incrementExp() {
        if (++experience >= getLevelTreshold()) {
            levelUp();
        }
    }

    public void incrementExp(int exp) {
        experience += exp;
        if (experience >= getLevelTreshold()) {
            levelUp();
        }
    }

    private int getLevelTreshold() {
        return level * 2 + 15;
    }

    private void levelUp() {
        experience = 0;
        level++;
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
}
