package cyanide3d.model;

public class UserLevel {
    private final String userId;
    private int userExp;
    private int userLvl;

    public UserLevel(String userId, int userExp, int userLvl) {
        this.userId = userId;
        this.userExp = userExp;
        this.userLvl = userLvl;
    }

    public String getUserId() {
        return userId;
    }

    public int getUserExp() {
        return userExp;
    }

    public void setUserExp(int userExp) {
        this.userExp = userExp;
    }

    public int getUserLvl() {
        return userLvl;
    }

    public void setUserLvl(int userLvl) {
        this.userLvl = userLvl;
    }
}
