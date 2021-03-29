package cyanide3d.util;

import cyanide3d.repository.model.PunishmentUserEntity;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

public class iNakazator {

    public PunishmentUserEntity increaseViolations(PunishmentUserEntity user, int violationsBeforeMute, int punishmentTime) {
        int userViolations = user.getViolations();
        userViolations++;
        if (userViolations >= violationsBeforeMute) {
            punish(user, punishmentTime);
        } else {
            user.setViolations(userViolations);
        }
        return user;
    }

    private void punish(PunishmentUserEntity user, int punishmentTime) {
        user.setViolations(0);
        user.setMuted(true);
        user.setDateToUnmute(getDateToUnmute(punishmentTime));
    }

    private Date getDateToUnmute(int minutes) {
        return DateUtils.addMinutes(new Date(), minutes);
    }
}
