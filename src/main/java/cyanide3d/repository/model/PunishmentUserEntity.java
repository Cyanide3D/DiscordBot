package cyanide3d.repository.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@javax.persistence.Entity
@Table(name = "discord_punishment_users")
public class PunishmentUserEntity implements Entity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private String userId;
    @Basic
    private boolean muted;
    @Basic
    private int violations;
    @Column(name = "unmute_date")
    private Date dateToUnmute;
    @ManyToOne
    @JoinColumn(name = "punishment_entity_id")
    private PunishmentEntity guildPunishment;

    public PunishmentUserEntity() {
    }

    public PunishmentUserEntity(String userId) {
        this.userId = userId;
        muted = false;
        violations = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public int getViolations() {
        return violations;
    }

    public void setViolations(int violations) {
        this.violations = violations;
    }

    public Date getDateToUnmute() {
        return dateToUnmute;
    }

    public void setDateToUnmute(Date dateToUnmute) {
        this.dateToUnmute = dateToUnmute;
    }

    public PunishmentEntity getGuildPunishment() {
        return guildPunishment;
    }

    public void setGuildPunishment(PunishmentEntity guildPunishment) {
        this.guildPunishment = guildPunishment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PunishmentUserEntity that = (PunishmentUserEntity) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
