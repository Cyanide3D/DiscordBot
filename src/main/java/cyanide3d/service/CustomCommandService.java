package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.CustomCommandEntity;
import cyanide3d.model.CustomCommand;
import cyanide3d.util.Serializer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CustomCommandService extends DAO<String, CustomCommandEntity> {

    private static CustomCommandService instance;

    public CustomCommandService(Class<CustomCommandEntity> entityClass) {
        super(entityClass);
    }

    public synchronized void add(String command, String body) {
        create(new CustomCommandEntity(command, body));
    }

    public synchronized void delete(String command) {
        findOneByCommand(command)
                .ifPresent(this::delete);
    }

    public synchronized Set<CustomCommand> getCommands() {
        return new Serializer().deserializeCommands(findAll());
    }

    private synchronized List<CustomCommandEntity> findAll() {
        return sessionFactory.fromSession(session -> {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<CustomCommandEntity> query = criteriaBuilder.createQuery(CustomCommandEntity.class);
            Root<CustomCommandEntity> root = query.from(CustomCommandEntity.class);
            query.select(root);
            return session.createQuery(query).getResultList();
        });
    }

    private synchronized Optional<CustomCommandEntity> findOneByCommand(String command) {
        return sessionFactory.fromSession(session -> {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<CustomCommandEntity> query = criteriaBuilder.createQuery(CustomCommandEntity.class);
            Root<CustomCommandEntity> root = query.from(CustomCommandEntity.class);
            query.where(criteriaBuilder.equal(root.get("command"), command));
            return session.createQuery(query).uniqueResultOptional();
        });
    }

    public static CustomCommandService getInstance() {
        if (instance == null) {
            instance = new CustomCommandService(CustomCommandEntity.class);
        }
        return instance;
    }

}
