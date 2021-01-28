package cyanide3d.dao;

import cyanide3d.conf.Config;
import cyanide3d.conf.Logging;
import cyanide3d.model.RoleUse;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.List;
import java.util.logging.Logger;

import static java.sql.Connection.TRANSACTION_SERIALIZABLE;

public class RoleUseCacheDao {
    private final Sql2o sql2o;
    Logger logger = Logging.getLogger(this.getClass());

    public RoleUseCacheDao(){
        Config config = Config.getInstance();
        sql2o = new Sql2o(config.getUrl(), config.getUsename(), config.getPassword());
        sql2o.beginTransaction(TRANSACTION_SERIALIZABLE)
                .createQuery("create table if not exists roles(id text, date text, count text);")
                .executeUpdate()
                .commit(true);
    }

    public void add(RoleUse roleUse) {
        Connection transaction = sql2o.beginTransaction(TRANSACTION_SERIALIZABLE);
        add(roleUse, transaction);
        transaction.commit(true);
    }

    private void add(RoleUse roleUse, Connection connection) {
        connection.createQuery("insert into roles values (:id, :date, :count)")
                .addParameter("id", roleUse.getId())
                .addParameter("date", roleUse.getDate())
                .addParameter("count", roleUse.getCount())
                .executeUpdate();
    }

    public RoleUse get(String date) {
        try (Connection connection = sql2o.open()) {
            return get(date, connection);
        }
    }

    private RoleUse get(String date, Connection connection) {
        return connection.createQuery("select * from roles where date=:date")
                .addParameter("date", date)
                .executeAndFetchFirst(RoleUse.class);
    }

    public List<RoleUse> list() {
        try (Connection conn = sql2o.open()) {
            return list(conn);
        } catch (Exception e){
            logger.warning("RoleUseCache DAO: " + e.getStackTrace().toString());
        }
        return null;
    }

    private List<RoleUse> list(Connection connection) {
        return connection.createQuery("select * from roles;").executeAndFetch(RoleUse.class);
    }

    public void update(RoleUse roleUse) {
        Connection transaction = sql2o.beginTransaction(TRANSACTION_SERIALIZABLE);
        update(roleUse, transaction);
        transaction.commit(true);
    }

    private void update(RoleUse roleUse, Connection connection) {
        connection.createQuery("update roles set count = :count where date=:date AND id=:id;")
                .addParameter("date", roleUse.getDate())
                .addParameter("id", roleUse.getId())
                .addParameter("count", roleUse.getCount())
                .executeUpdate();
    }
}
