package cyanide3d.repository.model;

import cyanide3d.util.ActionType;

import javax.persistence.*;

@javax.persistence.Entity
@Table(name = "discord_join_leave")
public class JoinLeaveEntity implements Entity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ActionType type;
    @Basic
    private String title;
    @Basic
    private String body;
    @Column(name = "image_url")
    private String imageUrl;
    @Basic
    private String guildId;

    public JoinLeaveEntity() {
    }

    public JoinLeaveEntity(ActionType type, String title, String body, String imageUrl, String guildId) {
        this.type = type;
        this.title = title;
        this.body = body;
        this.imageUrl = imageUrl;
        this.guildId = guildId;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ActionType getType() {
        return type;
    }

    public void setType(ActionType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }
}
