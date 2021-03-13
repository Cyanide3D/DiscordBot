package cyanide3d.dto;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name="action")
public class ActionEntity implements Entity<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Basic
    private boolean state;
    @Basic
    private String action;

    public ActionEntity() {
    }

    public ActionEntity(boolean state, String action) {
        this.state = state;
        this.action = action;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
