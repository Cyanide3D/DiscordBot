package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.JoinLeaveEntity;
import cyanide3d.util.ActionType;
import cyanide3d.util.DefaultEventMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.hibernate.query.Query;

import java.awt.*;
import java.util.Optional;

public class JoinLeaveService extends DAO<Long, JoinLeaveEntity> {

    private static JoinLeaveService instance;

    public JoinLeaveService() {
        super(JoinLeaveEntity.class);
    }

    public synchronized void saveOrUpdate(ActionType type, String title, String body, String imageUrl, String guildId) {
        findOneByAction(type, guildId).ifPresentOrElse(e -> {
            e.setTitle(title);
            e.setBody(body);
            e.setImageUrl(imageUrl);
            update(e);
        }, () -> create(new JoinLeaveEntity(type, title, body, imageUrl, guildId)));
    }


    //Метод, который будет выдавать сообщение(точка входа)
    public synchronized MessageEmbed getEventMessage(ActionType type, String guildId) {
        final JoinLeaveEntity entity = findOneByAction(type, guildId).orElse(null);

        return entity != null ? getMessage(entity, type) : getDefaultMessage(type);
    }

    private synchronized MessageEmbed getMessage(JoinLeaveEntity entity, ActionType type) {
        String title = entity.getTitle().equals("-")
                ? DefaultEventMessage.getEventTitle(type)
                : entity.getTitle();
        String body = entity.getTitle().equals("-")
                ? DefaultEventMessage.getEventBody(type)
                : entity.getBody();
        String image = entity.getTitle().equals("-")
                ? DefaultEventMessage.getEventImage(type)
                : entity.getImageUrl();

        return messageTemplate(title, body, image);
    }

    private synchronized MessageEmbed getDefaultMessage(ActionType type) {
        return messageTemplate(
                DefaultEventMessage.getEventTitle(type),
                DefaultEventMessage.getEventBody(type),
                DefaultEventMessage.getEventImage(type)
        );

    }

    private synchronized MessageEmbed messageTemplate(String title, String body, String image) {
        return new EmbedBuilder()
                .setImage(image)
                .setTitle(title)
                .setDescription(body)
                .setColor(Color.ORANGE)
                .build();
    }

    private synchronized Optional<JoinLeaveEntity> findOneByAction(ActionType type, String guildId) {
        return sessionFactory.fromSession(session -> {
            String asd = "from JoinLeaveEntity E where E.guildId=:guildId and E.type=:type";
            final Query<JoinLeaveEntity> query = session.createQuery(asd);
            query.setParameter("guildId", guildId);
            query.setParameter("type", type.name());
            return query.uniqueResultOptional();
        });
    }

    public static JoinLeaveService getInstance() {
        if (instance == null) {
            instance = new JoinLeaveService();
        }
        return instance;
    }
}
