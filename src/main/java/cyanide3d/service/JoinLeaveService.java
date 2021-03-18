package cyanide3d.service;

import cyanide3d.dao.DAO;
import cyanide3d.dto.JoinLeaveEntity;
import cyanide3d.util.ActionType;
import cyanide3d.util.DefaultEventMessage;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
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
    public synchronized MessageEmbed getEventMessage(ActionType type, String guildId, User user) {
        final JoinLeaveEntity entity = findOneByAction(type, guildId).orElse(null);

        return entity != null ? getMessage(entity, type, user) : getDefaultMessage(type, user);
    }

    private synchronized MessageEmbed getMessage(JoinLeaveEntity entity, ActionType type, User user) {
        String title = entity.getTitle().equals("-")
                ? DefaultEventMessage.getEventTitle(type)
                : entity.getTitle();
        String body = entity.getBody().equals("-")
                ? DefaultEventMessage.getEventBody(type)
                : entity.getBody();
        String image = entity.getImageUrl().equals("-")
                ? DefaultEventMessage.getEventImage(type)
                : entity.getImageUrl();

        return messageTemplate(title, body, image, user);
    }

    private synchronized MessageEmbed getDefaultMessage(ActionType type, User user) {
        return messageTemplate(
                DefaultEventMessage.getEventTitle(type),
                DefaultEventMessage.getEventBody(type),
                DefaultEventMessage.getEventImage(type),
                user
        );

    }

    private synchronized MessageEmbed messageTemplate(String title, String body, String image, User user) {
        return new EmbedBuilder()
                .setImage(image)
                .setTitle(filtered(title, user))
                .setDescription(filtered(body, user))
                .setColor(Color.ORANGE)
                .build();
    }

    private String filtered(String element, User user) {
        return element
                .replaceAll("\\$\\{username}", user.getName())
                .replaceAll("\\$\\{tag}", user.getAsTag())
                .replaceAll("\\$\\{id}", user.getId());
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
