package cyanide3d.util;

import java.util.TimerTask;

public abstract class TriggeredObject extends TimerTask {

    public abstract int getDelay();
    public abstract int getPeriod();

}
