package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.CustomCommandEntity;
import cyanide3d.exceptions.CommandDuplicateException;
import cyanide3d.model.CustomCommand;
import cyanide3d.util.Serializer;
import org.hibernate.query.Query;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomCommandService extends DAO<Long, CustomCommandEntity> {

    private static CustomCommandService instance;

    public CustomCommandService() {
        super(CustomCommandEntity.class);
    }

    public synchronized void add(String command, String body, String guildId) {
        findOneByCommand(command, guildId).ifPresent(e -> {
            throw new CommandDuplicateException("Command already exist");
        });

        create(new CustomCommandEntity(command, body, guildId));
    }

    public synchronized void delete(String command, String guildId) {
        findOneByCommand(command, guildId)
                .ifPresentOrElse(this::delete, () -> {
                    throw new IllegalArgumentException();
                });
    }

    public synchronized String getCommandNameList(String guildId) {
        return listByGuildId(guildId).stream()
                .map(CustomCommandEntity::getCommand)
                .collect(Collectors.joining(", "));
    }

    public synchronized Set<CustomCommand> getCommands(String guildId) {
        return new Serializer().deserializeCommands(listByGuildId(guildId));
    }

    private synchronized Optional<CustomCommandEntity> findOneByCommand(String command, String guildId) {
//        return sessionFactory.fromSession(session -> {
//            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
//            CriteriaQuery<CustomCommandEntity> query = criteriaBuilder.createQuery(CustomCommandEntity.class);
//            Root<CustomCommandEntity> root = query.from(CustomCommandEntity.class);
//            Predicate and = criteriaBuilder.and(
//                    criteriaBuilder.equal(root.get("command"), command),
//                    criteriaBuilder.equal(root.get("guildId"), guildId)
//            );
//            query.where(and);
//            return session.createQuery(query).uniqueResultOptional();
//        });
        return sessionFactory.fromSession(session -> {
            String asd = "from CustomCommandEntity E where E.guildId=:guildId and E.command=:command";
            final Query<CustomCommandEntity> query = session.createQuery(asd);
            query.setParameter("guildId", guildId);
            query.setParameter("command", command);
            return query.uniqueResultOptional();
        });
    }

    public static CustomCommandService getInstance() {
        if (instance == null) {
            instance = new CustomCommandService();
        }
        return instance;
    }

}
