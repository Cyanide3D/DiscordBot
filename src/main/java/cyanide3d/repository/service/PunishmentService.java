package cyanide3d.repository.service;

import cyanide3d.repository.model.PunishmentEntity;
import cyanide3d.repository.model.PunishmentUserEntity;
import cyanide3d.util.iNakazator;
import net.dv8tion.jda.api.entities.Guild;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class PunishmentService extends AbstractHibernateService<Long, PunishmentEntity>{

    Logger logger = LoggerFactory.getLogger(PunishmentService.class);
    private static PunishmentService instance;
    iNakazator punishator = new iNakazator();

    public PunishmentService() {
        super(PunishmentEntity.class);
    }

    public void enable(String guildId, int violationsBeforeMute, String roleId, int punishmentTime) {
        create(new PunishmentEntity(guildId, violationsBeforeMute, roleId, punishmentTime));
    }

    public void increaseViolation(String guildId, String userId) {
        PunishmentEntity punishmentEntity = findOneByGuildId(guildId).orElseThrow();

        int violationsBeforeMute = punishmentEntity.getViolationsBeforeMute();
        int punishmentTime = punishmentEntity.getPunishmentTime();

        PunishmentUserEntity entity = punishmentEntity.getUsers().stream()
                .filter(e -> e.getUserId().equals(userId))
                .findFirst().orElse(punishmentEntity.addUser(createUser(userId)));

        punishator.increaseViolations(entity, violationsBeforeMute, punishmentTime);
        update(punishmentEntity);
    }

    public PunishmentUserEntity createUser(String userId) {
        return new PunishmentUserEntity(userId);
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
