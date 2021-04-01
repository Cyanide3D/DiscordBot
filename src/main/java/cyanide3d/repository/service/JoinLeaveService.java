package cyanide3d.repository.service;

import cyanide3d.repository.model.JoinLeaveEntity;
import cyanide3d.util.ActionType;
import cyanide3d.util.DefaultAlertMessages;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import org.hibernate.query.Query;

import java.awt.*;
import java.util.Optional;

public class JoinLeaveService extends AbstractHibernateService<Long, JoinLeaveEntity> {

    private static JoinLeaveService instance;

    public JoinLeaveService() {
        super(JoinLeaveEntity.class);
    }

    public void saveOrUpdate(ActionType type, String title, String body, String imageUrl, String guildId) {
        findOneByAction(type, guildId).ifPresentOrElse(e -> {
            e.setTitle(title);
            e.setBody(body);
            e.setImageUrl(imageUrl);
            update(e);
        }, () -> create(new JoinLeaveEntity(type, title, body, imageUrl, guildId)));
    }


    public MessageEmbed getEventMessage(ActionType type, String guildId, User user) {
        final JoinLeaveEntity entity = findOneByAction(type, guildId).orElse(null);

        return entity != null ? getSimpleMessage(entity, type, user) : getDefaultMessage(type, user);
    }

    private MessageEmbed getSimpleMessage(JoinLeaveEntity entity, ActionType type, User user) {
        String title = entity.getTitle().equals("-")
                ? DefaultAlertMessages.getJoinLeaveEventTitle(type)
                : entity.getTitle();
        String body = entity.getBody().equals("-")
                ? DefaultAlertMessages.getJoinLeaveEventBody(type)
                : entity.getBody();
        String image = entity.getImageUrl().equals("-")
                ? DefaultAlertMessages.getJoinLeaveEventImage(type)
                : entity.getImageUrl();

        return messageTemplate(title, body, image, user);
    }

    private MessageEmbed getDefaultMessage(ActionType type, User user) {
        return messageTemplate(
                DefaultAlertMessages.getJoinLeaveEventTitle(type),
                DefaultAlertMessages.getJoinLeaveEventBody(type),
                DefaultAlertMessages.getJoinLeaveEventImage(type),
                user
        );

    }

    private MessageEmbed messageTemplate(String title, String body, String image, User user) {
        return new EmbedBuilder()
                .setImage(image)
                .setTitle(filtered(title, user))
                .setDescription(filtered(body, user))
                .setColor(Color.ORANGE)
                .setAuthor(user.getAsTag(), null, user.getAvatarUrl())
                .setFooter("ID пользователя: " + user.getId())
                .build();
    }

    private String filtered(String element, User user) {
        return element
                .replaceAll("\\{username}", user.getName())
                .replaceAll("\\{tag}", user.getAsTag())
                .replaceAll("\\{mention}", user.getAsMention())
                .replaceAll("\\{id}", user.getId());
    }

    private Optional<JoinLeaveEntity> findOneByAction(ActionType type, String guildId) {
        return sessionFactory.fromSession(session -> {
            String asd = "from JoinLeaveEntity E where E.guildId=:guildId and E.type=:type";
            final Query<JoinLeaveEntity> query = session.createQuery(asd, JoinLeaveEntity.class)
                    .setParameter("guildId", guildId)
                    .setParameter("type", type.name());
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
