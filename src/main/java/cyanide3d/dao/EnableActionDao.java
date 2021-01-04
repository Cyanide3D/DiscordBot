package cyanide3d.dao;

import cyanide3d.conf.Config;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.HashMap;
import java.util.Map;

import static java.sql.Connection.TRANSACTION_SERIALIZABLE;

public class EnableActionDao {
    private final Sql2o sql2o;

    public EnableActionDao() {
        Config config = Config.getInstance();
        sql2o = new Sql2o(config.getUrl(), config.getUsename(), config.getPassword());
        sql2o.beginTransaction(TRANSACTION_SERIALIZABLE)
                .createQuery("create table if not exists state(action text not null primary key, state boolean);")
                .executeUpdate()
                .commit(true);
    }

    public void create(String action, boolean state) {
        Connection transaction = sql2o.beginTransaction(TRANSACTION_SERIALIZABLE);
        create(action, state, transaction);
        transaction.commit(true);
    }

    private void create(String action, boolean enabled, Connection connection) {
        connection.createQuery("insert into state values (:action, :state);")
                .addParameter("action", action)
                .addParameter("state", enabled)
                .executeUpdate();
    }

    public void update(String action, boolean enabled) {
        Connection transaction = sql2o.beginTransaction(TRANSACTION_SERIALIZABLE);
        update(action, enabled, transaction);
        transaction.commit(true);
    }

    private void update(String action, boolean enabled, Connection connection) {
        connection.createQuery("update state set state = :state where action=:action;")
                .addParameter("action", action)
                .addParameter("state", enabled)
                .executeUpdate();
    }

    public Map<String, Boolean> list() {
        try (Connection conn = sql2o.open()) {

            return list(conn);
        }
    }

    private Map<String, Boolean> list(Connection connection) {
        Map<String, Boolean> res = new HashMap<>();
        connection.createQuery("select * from state;").executeAndFetchTable().rows()
                .forEach(row -> res.put(row.getString("action"), row.getObject("state", Boolean.class)));
        return res;
    }
}
