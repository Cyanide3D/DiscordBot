package cyanide3d.model;

public class RoleUse {
    String id;
    String date;
    String count;

    public RoleUse(String id, String date, String count) {
        this.id = id;
        this.date = date;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
