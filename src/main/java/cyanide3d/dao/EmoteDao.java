package cyanide3d.dao;

import cyanide3d.conf.Config;
import net.dv8tion.jda.api.entities.Role;
import org.sql2o.Sql2o;

import java.util.Map;

import static java.sql.Connection.TRANSACTION_SERIALIZABLE;

public class EmoteDao {

    private final Sql2o sql2o;

    public EmoteDao() {
        Config config = Config.getInstance();
        sql2o = new Sql2o(config.getUrl(), config.getUsename(), config.getPassword());
        sql2o.beginTransaction(TRANSACTION_SERIALIZABLE)
                .createQuery("create table if not exists emote(id integer not null primary key, channel_id text, emote text, role_id text);")
                .executeUpdate()
                .commit(true);
    }

    public Map<String, Map<String, Role>> findAll() {
        throw new UnsupportedOperationException("WiP");
    }
}
