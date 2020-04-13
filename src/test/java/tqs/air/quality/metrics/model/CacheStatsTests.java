package tqs.air.quality.metrics.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tqs.air.quality.metrics.utils.Equals;

import static org.junit.jupiter.api.Assertions.*;


public class CacheStatsTests {

    private CacheStats cacheStats;
    private CacheStats differentCacheStats;
    private CacheStats equalCacheStats;

    @BeforeEach
    void initializeCacheStats() {
        cacheStats = new CacheStats(3, 0, 3, 0);
        differentCacheStats = new CacheStats(2, 1, 1, 0);
        equalCacheStats = new CacheStats(3, 0, 3, 0);
    }

    @Test
    void checkEquals() {
        Equals.verifyEquals(CacheStats.class);
    }

    @Test
    void checkHashCode() {
        assertAll("hashCode",
                () -> assertEquals(cacheStats.hashCode(), equalCacheStats.hashCode()),
                () -> assertNotEquals(cacheStats.hashCode(), differentCacheStats.hashCode())
        );
    }

    @Test
    void checkToString() {
        assertAll("toString",
                () -> assertEquals(cacheStats.toString(), equalCacheStats.toString()),
                () -> assertNotEquals(cacheStats.toString(), differentCacheStats.toString())
        );
    }
}
