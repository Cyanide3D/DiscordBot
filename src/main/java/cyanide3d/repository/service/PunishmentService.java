package cyanide3d.repository.service;

import cyanide3d.exceptions.PunishmentDuplicateException;
import cyanide3d.exceptions.PunishmentNotFoundException;
import cyanide3d.repository.model.PunishmentEntity;
import cyanide3d.repository.model.PunishmentUserEntity;
import org.apache.commons.lang3.time.DateUtils;

import java.util.*;
import java.util.stream.Collectors;

public class PunishmentService extends AbstractHibernateService<Long, PunishmentEntity> {

    private static PunishmentService instance;

    public PunishmentService() {
        super(PunishmentEntity.class);
    }

    public void enable(String guildId, int violationsBeforeMute, String roleId, int punishmentTime) {
        findOneByGuildId(guildId).ifPresent(e -> {
            throw new PunishmentDuplicateException();
        });

        create(new PunishmentEntity(guildId, violationsBeforeMute, roleId, punishmentTime));
    }

    public Map<String, Set<PunishmentUserEntity>> getUsersToUnmute() {
        return findAll().stream()
                .collect(Collectors.toMap(
                        PunishmentEntity::getGuildId,
                        this::prepareUserSet
                ));
    }

    private Set<PunishmentUserEntity> prepareUserSet(PunishmentEntity entity) {
        return entity.getUsers().stream()
                .filter(this::isPresentAndDateBeforeUnmute)
                .collect(Collectors.toSet());
    }

    private boolean isPresentAndDateBeforeUnmute(PunishmentUserEntity entity) {
        return Optional.ofNullable(entity.getDateToUnmute())
                .map(e -> e.before(new Date()))
                .orElse(false);
    }

    public void disable(String guildId) {
        findOneByGuildId(guildId).ifPresentOrElse(this::deleteEntityWithUsers,
                () -> {
                    throw new PunishmentNotFoundException();
                });
    }

    public boolean increaseViolation(String guildId, String userId) {
        PunishmentEntity punishmentEntity = findOneByGuildId(guildId).orElseThrow(PunishmentNotFoundException::new);

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

    public void deleteUserFromEntity(PunishmentUserEntity user, String guildId) {
        findOneByGuildId(guildId).ifPresent(e -> {
            e.removeUser(user);
            delete(user);
            update(e);
        });
    }

    private Optional<PunishmentEntity> findOneByGuildId(String guildId) {
        return findOneByField("guildId", guildId, guildId);
    }

    private void deleteEntityWithUsers(PunishmentEntity entity){
        sessionFactory.inTransaction(session -> {
            String query = "delete from PunishmentUserEntity where guildPunishment = :id";
            session.createQuery(query)
                    .setParameter("id", entity)
                    .executeUpdate();
        });
    }

    private List<PunishmentEntity> findAll() {
        return sessionFactory.fromSession(session -> {
            String asd = "from PunishmentEntity";
            return session
                    .createQuery(asd, PunishmentEntity.class)
                    .list();
        });
    }

    public static PunishmentService getInstance() {
        if (instance == null) {
            instance = new PunishmentService();
        }
        return instance;
    }
}
