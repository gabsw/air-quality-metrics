package tqs.air.quality.metrics.model;

import com.google.common.cache.CacheStats;

import java.io.Serializable;

public class CacheStatsDTO implements Serializable {
    private long totalRequests;
    private long hitCount;
    private double hitRate;
    private long missCount;
    private double missRate;
    private long loadSuccessCount;
    private long loadExceptionCount;
    private long totalLoadTime;
    private long evictionCount;

    public CacheStatsDTO(CacheStats cacheStats) {
        this.totalRequests = cacheStats.requestCount();
        this.hitCount = cacheStats.hitCount();
        this.hitRate = cacheStats.hitRate();
        this.missCount = cacheStats.missCount();
        this.missRate = cacheStats.missRate();
        this.loadSuccessCount = cacheStats.loadSuccessCount();
        this.loadExceptionCount = cacheStats.loadExceptionCount();
        this.totalLoadTime = cacheStats.totalLoadTime();
        this.evictionCount = cacheStats.evictionCount();
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

    public long getLoadSuccessCount() {
        return loadSuccessCount;
    }

    public void setLoadSuccessCount(long loadSuccessCount) {
        this.loadSuccessCount = loadSuccessCount;
    }

    public long getLoadExceptionCount() {
        return loadExceptionCount;
    }

    public void setLoadExceptionCount(long loadExceptionCount) {
        this.loadExceptionCount = loadExceptionCount;
    }

    public long getTotalLoadTime() {
        return totalLoadTime;
    }

    public void setTotalLoadTime(long totalLoadTime) {
        this.totalLoadTime = totalLoadTime;
    }

    public long getEvictionCount() {
        return evictionCount;
    }

    public void setEvictionCount(long evictionCount) {
        this.evictionCount = evictionCount;
    }
}
