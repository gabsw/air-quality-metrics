package tqs.air.quality.metrics.utils;

public class TimeImpl implements Time {

    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }
}
