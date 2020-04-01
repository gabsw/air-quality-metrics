package tqs.air.quality.metrics.model;

import java.io.Serializable;

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

    public void setTotalRequests(long totalRequests) {
        this.totalRequests = totalRequests;
    }

    public long getHitCount() {
        return hitCount;
    }

    public void setHitCount(long hitCount) {
        this.hitCount = hitCount;
    }

    public double getHitRate() {
        return hitRate;
    }

    public void setHitRate(double hitRate) {
        this.hitRate = hitRate;
    }

    public long getMissCount() {
        return missCount;
    }

    public void setMissCount(long missCount) {
        this.missCount = missCount;
    }

    public double getMissRate() {
        return missRate;
    }

    public void setMissRate(double missRate) {
        this.missRate = missRate;
    }

    public long getEvictionCount() {
        return evictionCount;
    }

    public void setEvictionCount(long evictionCount) {
        this.evictionCount = evictionCount;
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
}
