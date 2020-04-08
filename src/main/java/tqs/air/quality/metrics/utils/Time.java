package tqs.air.quality.metrics.utils;

import java.time.LocalDateTime;

public interface Time {
    long currentTimeMillis();

    LocalDateTime now();
}
