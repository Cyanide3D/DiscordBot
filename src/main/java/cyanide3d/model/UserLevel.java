package cyanide3d.model;

public class UserLevel {
    private String userId;
    private String userExp;
    private String userLvl;

    public UserLevel(String userId, String userExp, String userLvl) {
        this.userId = userId;
        this.userExp = userExp;
        this.userLvl = userLvl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserExp() {
        return userExp;
    }

    public void setUserExp(String userExp) {
        this.userExp = userExp;
    }

    public String getUserLvl() {
        return userLvl;
    }

    public void setUserLvl(String userLvl) {
        this.userLvl = userLvl;
    }
}
