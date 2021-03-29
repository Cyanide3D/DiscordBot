package cyanide3d.repository.service;

import cyanide3d.repository.model.PunishmentEntity;
import cyanide3d.repository.model.PunishmentUserEntity;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Optional;

public class PunishmentService extends AbstractHibernateService<Long, PunishmentEntity> {

    Logger logger = LoggerFactory.getLogger(PunishmentService.class);
    private static PunishmentService instance;

    public PunishmentService() {
        super(PunishmentEntity.class);
    }

    public void enable(String guildId, int violationsBeforeMute, String roleId, int punishmentTime) {
        create(new PunishmentEntity(guildId, violationsBeforeMute, roleId, punishmentTime));
    }

    public boolean increaseViolation(String guildId, String userId) {
        PunishmentEntity punishmentEntity = findOneByGuildId(guildId).orElseThrow(IllegalArgumentException::new);

        int punishmentTime = punishmentEntity.getPunishmentTime();

        PunishmentUserEntity userEntity = getUserEntity(userId, punishmentEntity);
        boolean isMuted = userEntity.isPunished(getDateToUnmute(punishmentTime));

        updateUser(userEntity);
        update(punishmentEntity);

        return isMuted;
    }

    private Date getDateToUnmute(int minutes) {
        return DateUtils.addMinutes(new Date(), minutes);
    }

    public String getMutedRoleId(String guildId) {
        return findOneByGuildId(guildId).orElseThrow().getPunishmentRoleId();
    }

    private PunishmentUserEntity getUserEntity(String userId, PunishmentEntity punishmentEntity) {
        return punishmentEntity.getUsers().stream()
                .filter(e -> e.getUserId().equals(userId))
                .findFirst().orElseGet(() -> punishmentEntity.addUser(createAndSaveUser(userId, punishmentEntity)));
    }

    public PunishmentUserEntity createAndSaveUser(String userId, PunishmentEntity punishmentEntity) {
        return saveUser(new PunishmentUserEntity(userId, punishmentEntity));
    }

    private PunishmentUserEntity saveUser(PunishmentUserEntity entity) {
        return sessionFactory.fromSession(session -> session.load(PunishmentUserEntity.class, session.save(entity)));
    }

    private void updateUser(PunishmentUserEntity entity) {
        sessionFactory.inTransaction(session -> session.update(entity));
    }

    private Optional<PunishmentEntity> findOneByGuildId(String guildId) {
        return findOneByField("guildId", guildId, guildId);
    }

    public static PunishmentService getInstance() {
        if (instance == null) {
            instance = new PunishmentService();
        }
        return instance;
    }

}
