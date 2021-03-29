package cyanide3d.repository.service;

import cyanide3d.repository.model.PunishmentEntity;
import cyanide3d.repository.model.PunishmentUserEntity;
import cyanide3d.util.iNakazator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class PunishmentService extends AbstractHibernateService<Long, PunishmentEntity>{

    Logger logger = LoggerFactory.getLogger(PunishmentService.class);
    private static PunishmentService instance;
    iNakazator nakazator = new iNakazator();

    public PunishmentService() {
        super(PunishmentEntity.class);
    }

    public void enable(String guildId, int violationsBeforeMute, String roleId, int punishmentTime) {
        create(new PunishmentEntity(guildId, violationsBeforeMute, roleId, punishmentTime));
    }

    public void increaseViolation(String guildId, String userId) {
        PunishmentEntity punishmentEntity = findOneByGuildId(guildId).orElseThrow(IllegalArgumentException::new);

        int violationsBeforeMute = punishmentEntity.getViolationsBeforeMute();
        int punishmentTime = punishmentEntity.getPunishmentTime();

        PunishmentUserEntity entity = punishmentEntity.getUsers().stream()
                .filter(e -> e.getUserId().equals(userId))
                .findFirst().orElseGet(() -> punishmentEntity.addUser(createAndSaveUser(userId, punishmentEntity)));

        nakazator.increaseViolation(entity, violationsBeforeMute, punishmentTime);
        updateUser(entity);
        update(punishmentEntity);
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
