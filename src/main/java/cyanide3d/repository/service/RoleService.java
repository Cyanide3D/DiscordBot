package cyanide3d.repository.service;

import cyanide3d.repository.model.RoleEntity;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RoleService extends AbstractHibernateService<Long, RoleEntity> {

    private static RoleService instance;

    public RoleService() {
        super(RoleEntity.class);
    }

    public void addRoleByDate(String name, String data, String guildId) {
        final RoleEntity entity = findEntityByDateAndId(name, data, guildId)
                .orElse(new RoleEntity(name, data, guildId));

        incrementMentionAmount(entity);

        saveOrUpdate(entity);
    }

    public void incrementMentionAmount(RoleEntity entity) {
        int count = entity.getCount();
        entity.setCount(++count);
    }

    public String rolesAsString(String date, String guildId) {
        final List<RoleEntity> entities = findListByDate(date, guildId);

        if (entities.isEmpty()) {
            return "Список ролей пуст.";
        }

        return entities.stream().map(entity ->
                "`" + entity.getRoleName() + "` : " + entity.getCount() + " раз(а).")
                .collect(Collectors.joining("\n"));

    }

    private Optional<RoleEntity> findEntityByDateAndId(String roleName, String date, String guildId) {
        return sessionFactory.fromSession(session -> {
            String asd = "from RoleEntity E where E.guildId=:guildId and E.date=:date and  E.roleName=:roleName";
            final Query query = session.createQuery(asd);
            query.setParameter("guildId", guildId);
            query.setParameter("date", date);
            query.setParameter("roleName", roleName);
            return query.uniqueResultOptional();
        });
    }

    private List<RoleEntity> findListByDate(String date, String guildId) {
        return sessionFactory.fromSession(session -> {
            String asd = "from RoleEntity E where E.guildId=:guildId and E.date=:date";
            final Query query = session.createQuery(asd);
            query.setParameter("guildId", guildId);
            query.setParameter("date", date);
            return query.getResultList();
        });
    }

    public static RoleService getInstance() {
        if (instance == null) {
            instance = new RoleService();
        }
        return instance;
    }

}
