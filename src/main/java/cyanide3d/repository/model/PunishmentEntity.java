package cyanide3d.repository.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "discord_punishment")
public class PunishmentEntity implements Entity<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "guild_id")
    private String guildId;
    @Column(name = "violations_before_mute")
    private int violationsBeforeMute;
    @Column(name = "punishment_role_id")
    private String punishmentRoleId;
    @Column(name = "punishment_time")
    private int punishmentTime;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "guildPunishment")
    private Set<PunishmentUserEntity> users;

    public PunishmentEntity() {
    }

    public PunishmentEntity(String guildId, int violationsBeforeMute, String punishmentRoleId, int punishmentTime) {
        this.guildId = guildId;
        this.punishmentTime = punishmentTime;
        this.violationsBeforeMute = violationsBeforeMute;
        this.punishmentRoleId = punishmentRoleId;
        users = new HashSet<>();
    }

    public PunishmentUserEntity addUser(PunishmentUserEntity entity) {
        users.add(entity);
        return entity;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuildId() {
        return guildId;
    }

    public void setGuildId(String guildId) {
        this.guildId = guildId;
    }

    public int getViolationsBeforeMute() {
        return violationsBeforeMute;
    }

    public void setViolationsBeforeMute(int violationsBeforeMute) {
        this.violationsBeforeMute = violationsBeforeMute;
    }

    public String getPunishmentRoleId() {
        return punishmentRoleId;
    }

    public void setPunishmentRoleId(String punishmentRoleId) {
        this.punishmentRoleId = punishmentRoleId;
    }

    public int getPunishmentTime() {
        return punishmentTime;
    }

    public void setPunishmentTime(int punishmentTime) {
        this.punishmentTime = punishmentTime;
    }

    public Set<PunishmentUserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<PunishmentUserEntity> users) {
        this.users = users;
    }
}
