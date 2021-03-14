package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.Entity;
import cyanide3d.dto.RoleEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

public class RoleService extends DAO<Long, RoleEntity> {

    private final String guildId;

    public RoleService(Class<RoleEntity> entityClass, String guildId) {
        super(entityClass);
        this.guildId = guildId;
    }

    public void add(String id, String data) {
        final RoleEntity entity = findEntityByDateAndId(id, data)
                .orElse(new RoleEntity(id, data, guildId));

        saveOrUpdate(entity);
    }

    //TODO make another AND with guildId
    private Optional<RoleEntity> findEntityByDateAndId(String roleId, String date) {
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

}
