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

public class CustomCommandService extends DAO<Long, CustomCommandEntity> {

    private static CustomCommandService instance;

    public CustomCommandService() {
        super(CustomCommandEntity.class);
    }

    public synchronized void add(String command, String body, String guildId) {
        create(new CustomCommandEntity(command, body, guildId));
    }

    public synchronized void delete(String command) {
        findOneByCommand(command)
                .ifPresent(this::delete);
    }

    public synchronized Set<CustomCommand> getCommands(String guildId) {
        return new Serializer().deserializeCommands(listByGuildId(guildId));
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
            instance = new CustomCommandService();
        }
        return instance;
    }

}
