package cyanide3d.dao;

import cyanide3d.conf.Config;
import cyanide3d.model.User;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ReadOnlyBufferException;
import java.util.List;
import java.util.concurrent.TransferQueue;

public class UserDao {

    private final Sql2o sql2o;

    public UserDao() {
        Config config = Config.getInstance();
        sql2o = new Sql2o(config.getUrl(), config.getUsename(), config.getPassword());
        sql2o.beginTransaction()
                .createQuery("create table users(id text not null primary key, level integer, experience integer);")
                .executeUpdate()
                .commit(true);
    }

    public List<User> list() {
        try (Connection conn = sql2o.open()) {
            return list(conn);
        }
    }

    public void create(User user) {
        Connection transaction = sql2o.beginTransaction();
        create(user, transaction);
        transaction.commit(true);
    }

    public User get(String id) {
        try (Connection connection = sql2o.open()) {
            return get(id, connection);
        }
    }

    public void update(User user) {
        Connection transaction = sql2o.beginTransaction();
        update(user, transaction);
        transaction.commit(true);
    }

    public void save(User user) {
        Connection transaction = sql2o.beginTransaction();
        if (get(user.getId()) == null) {
            create(user, transaction);
        } else {
            update(user, transaction);
        }
        transaction.commit(true);
    }

    private List<User> list(Connection connection) {
        return connection.createQuery("select * from users;").executeAndFetch(User.class);
    }

    private void create(User user, Connection connection) {
        connection.createQuery("insert into users values (:id, :level, :exp)")
                .addParameter("id", user.getId())
                .addParameter("exp", user.getExperience())
                .addParameter("lvl", user.getLevel())
                .executeUpdate();
    }

    private void update(User user, Connection connection) {
        connection.createQuery("update users set experience = :exp, level = :lvl where id=:id;")
                .addParameter("id", user.getId())
                .addParameter("exp", user.getExperience())
                .addParameter("lvl", user.getLevel())
                .executeUpdate();
    }

    private User get(String id, Connection connection) {
        return connection.createQuery("select * from users where id=:id")
                .addParameter("id", id)
                .executeAndFetchFirst(User.class);
    }

}
