package tqs.air.quality.metrics.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CacheTests {

    private Cache<Integer, Integer> emptyCache;


    private Cache<Integer, Integer> cacheWithResults;

    @BeforeEach
    void createCacheWithResults() {
         cacheWithResults = new Cache<>(5L * 1000, key -> 1);

         cacheWithResults.get(1);
         cacheWithResults.get(2);
         cacheWithResults.get(3);
    }

    @BeforeEach
    void createEmptyCache() {
        emptyCache = new Cache<>(5L * 1000, key -> 1);
    }

    @Test
    void emptyCacheStats() {
        assertAll("emptyCacheStats",
                () -> assertEquals(0, emptyCache.computeStats().getTotalRequests()),
                () -> assertEquals(0, emptyCache.computeStats().getEvictionCount()),
                () -> assertEquals(0, emptyCache.computeStats().getHitCount()),
                () -> assertEquals(0, emptyCache.computeStats().getMissCount()),
                () -> assertEquals(Double.NaN, emptyCache.computeStats().getHitRate()),
                () -> assertEquals(Double.NaN, emptyCache.computeStats().getMissRate())
        );
    }

    @Test
    void firstMiss() {
        emptyCache.get(10);
        assertAll("firstMissStats",
                () -> assertEquals(1, emptyCache.computeStats().getTotalRequests()),
                () -> assertEquals(0, emptyCache.computeStats().getEvictionCount()),
                () -> assertEquals(0, emptyCache.computeStats().getHitCount()),
                () -> assertEquals(1, emptyCache.computeStats().getMissCount()),
                () -> assertEquals(0, emptyCache.computeStats().getHitRate()),
                () -> assertEquals(1, emptyCache.computeStats().getMissRate())
        );
    }

    @Test
    void firstHit() {
        emptyCache.get(1);
        emptyCache.get(1);
        assertAll("firstHitStats",
                () -> assertEquals(2, emptyCache.computeStats().getTotalRequests()),
                () -> assertEquals(0, emptyCache.computeStats().getEvictionCount()),
                () -> assertEquals(1, emptyCache.computeStats().getHitCount()),
                () -> assertEquals(1, emptyCache.computeStats().getMissCount()),
                () -> assertEquals(0.5, emptyCache.computeStats().getHitRate()),
                () -> assertEquals(0.5, emptyCache.computeStats().getMissRate())
        );
    }


}
