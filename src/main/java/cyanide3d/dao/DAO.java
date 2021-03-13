package cyanide3d.dao;

import cyanide3d.conf.Config;
import cyanide3d.dto.Entity;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class DAO<K, T extends Entity<K>> {
    protected final SessionFactory sessionFactory;
    private final Logger logger = LoggerFactory.getLogger(DAO.class);
    private final Class<T> entityClass;

    public DAO(Class<T> entityClass) {
        this.entityClass = entityClass;
        sessionFactory = Config.getInstance().getSessionFactory();
    }

    public Entity<K> get(K id){
        return sessionFactory.fromSession(session -> session.load(entityClass, id));
    }

    public void update(T entity) {
        sessionFactory.fromTransaction(session -> {
            session.update(entity);
            return session.load(entityClass, entity.getId());//в принципе можно и убрать
//            return null;
        });
    }

    public List<T> listByGuildId(String guildId) {
        return sessionFactory.fromSession(session -> {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = criteriaBuilder.createQuery(entityClass);
            Root<T> root = query.from(entityClass);
            query.where(criteriaBuilder.equal(root.get("guild_id"), guildId));//FIXME проверь имя параметра
            return session.createQuery(query).getResultList();
        });
    }

    public T findOneByField(String field, String param, String guildId) {
        return sessionFactory.fromSession(session -> {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = criteriaBuilder.createQuery(entityClass);
            Root<T> root = query.from(entityClass);
            query.where(criteriaBuilder.equal(root.get(field), param),
                    criteriaBuilder.equal(root.get("guild_id"), guildId));
            return session.createQuery(query).getSingleResult();
        });
    }

    public void create(Entity<K> entity) {
        sessionFactory.fromTransaction(session -> session.save(entity));
    }

    public void delete(Entity<K> entity) {
        sessionFactory.fromTransaction((session) -> {
            session.delete(entity);
            return null;
        });
    }
}
