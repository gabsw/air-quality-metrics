package tqs.air.quality.metrics.model;

import java.io.Serializable;
import java.util.Objects;

public class CacheStats implements Serializable {
    private long totalRequests;
    private long hitCount;
    private double hitRate;
    private long missCount;
    private double missRate;
    private long evictionCount;

    public CacheStats(long totalRequests, long hitCount, long missCount, long evictionCount) {
        this.totalRequests = totalRequests;
        this.hitCount = hitCount;
        this.missCount = missCount;
        this.evictionCount = evictionCount;
        this.hitRate = (double) hitCount / totalRequests;
        this.missRate = 1 - hitRate;
    }

    public long getTotalRequests() {
        return totalRequests;
    }

    public long getHitCount() {
        return hitCount;
    }

    public double getHitRate() {
        return hitRate;
    }

    public long getMissCount() {
        return missCount;
    }

    public double getMissRate() {
        return missRate;
    }


    public long getEvictionCount() {
        return evictionCount;
    }


    @Override
    public String toString() {
        return "CacheStats{" +
                "totalRequests=" + totalRequests +
                ", hitCount=" + hitCount +
                ", hitRate=" + hitRate +
                ", missCount=" + missCount +
                ", missRate=" + missRate +
                ", evictionCount=" + evictionCount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheStats that = (CacheStats) o;
        return totalRequests == that.totalRequests &&
                hitCount == that.hitCount &&
                Double.compare(that.hitRate, hitRate) == 0 &&
                missCount == that.missCount &&
                Double.compare(that.missRate, missRate) == 0 &&
                evictionCount == that.evictionCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(totalRequests, hitCount, hitRate, missCount, missRate, evictionCount);
    }
}
