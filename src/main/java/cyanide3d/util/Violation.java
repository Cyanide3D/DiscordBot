package cyanide3d.util;

import cyanide3d.repository.model.PunishmentUserEntity;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

public class Violation {

    public static boolean increase(PunishmentUserEntity user, int violationsBeforeMute, int punishmentTime) {
        int userViolations = user.increaseViolation();

        if (userViolations >= violationsBeforeMute) {
            setPunishState(user, punishmentTime);
            return true;
        }
        return false;
    }

    private static void setPunishState(PunishmentUserEntity user, int punishmentTime) {
        user.punishUntilDate(getDateToUnmute(punishmentTime));
    }

    private static Date getDateToUnmute(int minutes) {
        return DateUtils.addMinutes(new Date(), minutes);
    }
}
