package cyanide3d.dao;

import com.jagrosh.jdautilities.command.Command;
import cyanide3d.conf.Config;
import cyanide3d.model.Commands;
import cyanide3d.model.CommandsDaoModel;
import cyanide3d.model.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.ArrayList;
import java.util.List;

import static java.sql.Connection.TRANSACTION_SERIALIZABLE;

public class CommandsDao {
    private final Sql2o sql2o;

    public CommandsDao() {
        Config config = Config.getInstance();
        sql2o = new Sql2o(config.getUrl(), config.getUsename(), config.getPassword());
        sql2o.beginTransaction(TRANSACTION_SERIALIZABLE)
                .createQuery("create table if not exists commands(name text not null primary key, body text);")
                .executeUpdate()
                .commit(true);
    }
    public List<Commands> list() {
        try (Connection conn = sql2o.open()) {
            List<Commands> result = new ArrayList<>();
            for(CommandsDaoModel commandsDaoModel : list(conn)){
                result.add(new Commands(commandsDaoModel.getName(),commandsDaoModel.getBody()));
            }
            return result;
        }
    }
    private List<CommandsDaoModel> list(Connection connection) {
        return connection.createQuery("select * from commands;").executeAndFetch(CommandsDaoModel.class);
    }

    public Commands get(String name) {
        try (Connection connection = sql2o.open()) {
            CommandsDaoModel temp = get(name, connection);
            if (temp==null) return null;
            return new Commands(temp.getName(),temp.getBody());
        }
    }

    private CommandsDaoModel get(String name, Connection connection) {
        return connection.createQuery("select * from commands where name=:name")
                .addParameter("name", name)
                .executeAndFetchFirst(CommandsDaoModel.class);
    }

    public void delete(Commands command) {
        Connection transaction = sql2o.beginTransaction(TRANSACTION_SERIALIZABLE);
        delete(command, transaction);
        transaction.commit(true);
    }

    private void delete(Commands user, Connection connection) {
        connection.createQuery("delete from commands where name=:name;")
                .addParameter("name", user.getName())
                .executeUpdate();
    }

    public void create(Commands command) {
        Connection transaction = sql2o.beginTransaction(TRANSACTION_SERIALIZABLE);
        create(command, transaction);
        transaction.commit(true);
    }

    private void create(Commands command, Connection connection) {
        connection.createQuery("insert into commands values (:name, :body);")
                .addParameter("name", command.getName())
                .addParameter("body", command.getBody())
                .executeUpdate();
    }
}
