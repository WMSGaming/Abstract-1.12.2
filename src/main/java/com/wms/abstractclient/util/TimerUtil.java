package com.wms.abstractclient.util;

public class TimerUtil {
    private static long prevMS;

    public long lastMS = System.currentTimeMillis();;

    public void rs() {
        lastMS = System.currentTimeMillis();
    }

    public  boolean hasTimeElapsed(long time, boolean reset) {
        if (System.currentTimeMillis() - lastMS > time) {
            if (reset)
                rs();

            return true;
        }
        return false;
    }
}
