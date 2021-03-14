package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.RoleEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

public class RoleService extends DAO<Long, RoleEntity> {

    private static RoleService instance;

    public RoleService(Class<RoleEntity> entityClass) {
        super(entityClass);
    }

    public synchronized void add(String id, String data, String guildId) {
        final RoleEntity entity = findEntityByDateAndId(id, data, guildId)
                .orElse(new RoleEntity(id, data, guildId));

        saveOrUpdate(entity);
    }

    //TODO make another AND with guildId
    private synchronized Optional<RoleEntity> findEntityByDateAndId(String roleId, String date, String guildId) {
        return sessionFactory.fromSession(session -> {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<RoleEntity> query = criteriaBuilder.createQuery(RoleEntity.class);
            Root<RoleEntity> root = query.from(RoleEntity.class);
            final Predicate fieldQuery = criteriaBuilder.equal(root.get("roleId"), roleId);
            final Predicate guildQuery = criteriaBuilder.equal(root.get("date"), date);
            query.where(criteriaBuilder.and(fieldQuery, guildQuery));
            return session.createQuery(query).uniqueResultOptional();
        });
    }

    public static RoleService getInstance() {
        if (instance == null) {
            instance = new RoleService(RoleEntity.class);
        }
        return instance;
    }

}
