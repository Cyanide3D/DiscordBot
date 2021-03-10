package cyanide3d.dao;

import cyanide3d.conf.Config;
import cyanide3d.model.CustomCommand;
import cyanide3d.model.CommandsDaoModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;
import java.util.stream.Collectors;

import static java.sql.Connection.TRANSACTION_SERIALIZABLE;

public class CustomCommandsDao {
    private final Sql2o sql2o;
    Logger logger = LoggerFactory.getLogger(CustomCommandsDao.class);

    public CustomCommandsDao() {
        Config config = Config.getInstance();
        sql2o = new Sql2o(config.getUrl(), config.getUsename(), config.getPassword());
        sql2o.beginTransaction(TRANSACTION_SERIALIZABLE)
                .createQuery("create table if not exists commands(name text not null primary key, body text);")
                .executeUpdate()
                .commit(true);
    }
    public List<CustomCommand> list() {
        try (Connection conn = sql2o.open()) {
            return list(conn).stream().map(CommandsDaoModel::toCustomCommand).collect(Collectors.toList());
        } catch (Exception e){
            logger.error("Error load custom commands ", e);
        }
        return null;
    }
    private List<CommandsDaoModel> list(Connection connection) {
        return connection.createQuery("select * from commands;").executeAndFetch(CommandsDaoModel.class);
    }

    public CustomCommand get(String name) {
        try (Connection connection = sql2o.open()) {
            CommandsDaoModel temp = get(name, connection);
            if (temp==null) return null;
            return new CustomCommand(temp.getName(),temp.getBody());
        }
    }

    private CommandsDaoModel get(String name, Connection connection) {
        return connection.createQuery("select * from commands where name=:name")
                .addParameter("name", name)
                .executeAndFetchFirst(CommandsDaoModel.class);
    }

    public void delete(CustomCommand command) {
        Connection transaction = sql2o.beginTransaction(TRANSACTION_SERIALIZABLE);
        delete(command, transaction);
        transaction.commit(true);
    }

    private void delete(CustomCommand user, Connection connection) {
        connection.createQuery("delete from commands where name=:name;")
                .addParameter("name", user.getName())
                .executeUpdate();
    }

    public void create(CustomCommand command) {
        Connection transaction = sql2o.beginTransaction(TRANSACTION_SERIALIZABLE);
        create(command, transaction);
        transaction.commit(true);
    }

    private void create(CustomCommand command, Connection connection) {
        connection.createQuery("insert into commands values (:name, :body);")
                .addParameter("name", command.getName())
                .addParameter("body", command.getBody())
                .executeUpdate();
    }
}