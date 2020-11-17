package cyanide3d.dao;

import cyanide3d.conf.Config;
import cyanide3d.conf.Logging;
import cyanide3d.model.Message;
import cyanide3d.model.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.logging.Logger;

import static java.sql.Connection.TRANSACTION_SERIALIZABLE;

public class MessageCacheDao {
    private final Sql2o sql2o;
    Logger logger = Logging.getInstance().getLogger();
    
    public MessageCacheDao(){
        Config config = Config.getInstance();
        sql2o = new Sql2o(config.getUrl(), config.getUsename(), config.getPassword());
        sql2o.beginTransaction(TRANSACTION_SERIALIZABLE)
                .createQuery("create table if not exists messages(id text not null primary key, body text);")
                .executeUpdate()
                .commit(true);
    }

    public Message get(String id) {
        try (Connection connection = sql2o.open()) {
            return get(id, connection);
        }
    }

    private Message get(String id, Connection connection) {
        return connection.createQuery("select * from messages where id=:id")
                .addParameter("id", id)
                .executeAndFetchFirst(Message.class);
    }

    public void add(Message message) {
        Connection transaction = sql2o.beginTransaction(TRANSACTION_SERIALIZABLE);
        add(message, transaction);
        transaction.commit(true);
    }

    private void add(Message message, Connection connection) {
        connection.createQuery("insert into messages values (:id, :body)")
                .addParameter("id", message.getId())
                .addParameter("body", message.getBody())
                .executeUpdate();
    }

    public void delete(String id) {
        Connection transaction = sql2o.beginTransaction(TRANSACTION_SERIALIZABLE);
        delete(id, transaction);
        transaction.commit(true);
    }

    private void delete(String id, Connection connection) {
        connection.createQuery("delete from messages where id=:id;")
                .addParameter("id", id)
                .executeUpdate();
    }
    
}
