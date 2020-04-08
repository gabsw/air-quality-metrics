package tqs.air.quality.metrics.utils;

import java.time.LocalDateTime;

public class TimeImpl implements Time {

    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    @Override
    public LocalDateTime now() {
        return LocalDateTime.now();
    }
}
