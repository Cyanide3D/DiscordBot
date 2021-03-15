package cyanide3d.dao;

import cyanide3d.conf.Config;
import cyanide3d.dto.Entity;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public abstract class DAO<K, T extends Entity<K>> {
    protected final SessionFactory sessionFactory;
    private final Logger logger = LoggerFactory.getLogger(DAO.class);
    private final Class<T> entityClass;

    public DAO(Class<T> entityClass) {
        this.entityClass = entityClass;
        sessionFactory = Config.getInstance().getSessionFactory();
    }

    public Entity<K> get(K id) {
        return sessionFactory.fromSession(session -> session.load(entityClass, id));
    }

    public void update(T entity) {
        sessionFactory.fromTransaction(session -> {
            session.update(entity);
            return null;
        });
    }

    public T saveOrUpdate(T entity) {
        return sessionFactory.fromTransaction(session -> {
            session.saveOrUpdate(entity);
            return session.load(entityClass, entity.getId());//в принципе можно и убрать
//            return null;
        });
    }

    public List<T> listByGuildId(String guildId) {
//        return sessionFactory.fromSession(session -> {
//            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//            CriteriaQuery<T> query = criteriaBuilder.createQuery(entityClass);
//            Root<T> root = query.from(entityClass);
//            query.where(criteriaBuilder.equal(root.get("guildId"), guildId));//FIXME проверь имя параметра
//            return session.createQuery(query).getResultList();
//        });
        return sessionFactory.fromSession(session -> {
            String asd = "from " + entityClass.getName() + " E where E.guildId=:guildId";
            final Query<T> query = session.createQuery(asd, entityClass);
            query.setParameter("guildId", guildId);
            return query.list();
        });
    }

    public Optional<T> findOneByField(String field, String param, String guildId) {
//        return sessionFactory.fromSession(session -> {
//            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//            CriteriaQuery<T> query = criteriaBuilder.createQuery(entityClass);
//            Root<T> root = query.from(entityClass);
//            final Predicate fieldQuery = criteriaBuilder.equal(root.get(field), param);
//            final Predicate guildQuery = criteriaBuilder.equal(root.get("guildId"), guildId);
//            query.where(criteriaBuilder.and(fieldQuery, guildQuery));
//            return session.createQuery(query).uniqueResultOptional();
//        });
        return sessionFactory.fromSession(session -> {
            try {
                String wqe = "from " + entityClass.getSimpleName() + " E where E.guildId=:guildId and E." + field + "=:param";
                Query<T> query = session.createQuery(wqe, entityClass);
                query.setParameter("guildId", guildId);
                query.setParameter("param", param);
                return Optional.ofNullable(query.getSingleResult());
            } catch (Exception e) {
                return Optional.empty();
            }
        });
    }

    public T create(Entity<K> entity) {
        return sessionFactory.fromTransaction(session -> session.load(entityClass, session.save(entity)));
    }

    public void delete(Entity<K> entity) {
        sessionFactory.inTransaction((session) -> session.delete(entity));
    }
}
