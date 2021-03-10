package cyanide3d.dao;

import cyanide3d.conf.Config;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Map;
import java.util.stream.Collectors;

import static java.sql.Connection.TRANSACTION_SERIALIZABLE;

public class EmoteDao {

    private final Sql2o sql2o;

    public EmoteDao() {
        Config config = Config.getInstance();
        sql2o = new Sql2o(config.getUrl(), config.getUsename(), config.getPassword());
        sql2o.beginTransaction(TRANSACTION_SERIALIZABLE)
                .createQuery("create table if not exists emote(id integer primary key autoincrement , message_id text, emote text, role_id text);")
                .executeUpdate()
                .commit(true);
    }

    public void save(String message, Map<String, String> map) {
        Connection transaction = sql2o.beginTransaction(TRANSACTION_SERIALIZABLE);
        create(message, map, transaction);
        transaction.commit(true);
    }

    private void create(String message, Map<String, String> map, Connection connection) {
        map.forEach((k, v) ->
                connection.createQuery("insert into emote (message_id, emote, role_id) values (:message_id, :emote, :role_id);")
                        .addParameter("message_id", message)
                        .addParameter("emote", k)
                        .addParameter("role_id", v)
                        .executeUpdate());
    }

    public Map<String, Map<String, String>> findAll() {
        try (Connection conn = sql2o.open()) {
            return list(conn);
        }
    }

    private Map<String, Map<String, String>> list(Connection connection) {
        return connection.createQuery("select * from emote;").executeAndFetchTable().rows().stream()
                .collect(Collectors.groupingBy(row -> row.getString("message_id"),
                        Collectors.toMap(row -> row.getString("emote"),
                                row -> row.getString("role_id"))));
    }
}
