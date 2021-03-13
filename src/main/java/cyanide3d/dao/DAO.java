package cyanide3d.dao;

import cyanide3d.conf.Config;
import cyanide3d.dto.Entity;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class DAO {
    protected final Session session;
    private final Logger logger = LoggerFactory.getLogger(DAO.class);

    public DAO() {
        session = Config.getInstance().getSessionFactory().openSession();
    }

    public void update(Entity entity) {
        try {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Failed update entity ", e);
        }
    }

    public <T> List<T> findAll(String table) {
        List<T> result = new ArrayList<>();
        try {
            String query = "from " + table;
            result = session.createQuery(query).list();
        } catch (Exception e) {
            logger.error("Failed get list");
        }
        return result;
    }

    public void create(Entity entity) {
        try {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Failed save entity ", e);
        }
    }

    public void delete(Entity entity) {
        try {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
        } catch (Exception e) {
            logger.error("Failed delete entity ", e);
        }
    }
}
