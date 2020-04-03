package tqs.air.quality.metrics.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tqs.air.quality.metrics.utils.Time;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CacheEvictionTests {
    private Cache<Integer, Integer> emptyCache;
    private Cache<Integer, Integer> cacheWithResults;

    @Mock
    private Time time;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        cacheWithResults = new Cache<>(5L * 1000, key -> 1, time);
        when(time.currentTimeMillis()).thenReturn(0L, 0L, 0L);
        cacheWithResults.get(1);
        cacheWithResults.get(2);
        cacheWithResults.get(3);

        emptyCache = new Cache<>(5L * 1000, key -> 1, time);
    }

    @Test
    void evictionCount() {
        when(time.currentTimeMillis()).thenReturn(6L * 1000, 6L * 1000, 6L * 1000); // Exceeds time to live
        cacheWithResults.get(1);
        cacheWithResults.get(2);
        cacheWithResults.get(3);
        assertEquals(3, cacheWithResults.computeStats().getEvictionCount());
    }

    @Test
    void getBeforeExpiration() {
        when(time.currentTimeMillis()).thenReturn(0L, 2L * 1000);
        assertAll("getBeforeExpiration",
                () -> assertEquals(1, emptyCache.get(1)), // There is no key in the cache
                () -> assertEquals(1, emptyCache.get(1)) // There is already a key stored in the cache
        );
    }

    @Test
    void getAfterExpiration() {
        when(time.currentTimeMillis()).thenReturn(6L * 1000); // Exceeds time to live
        assertAll("get",
                () -> assertEquals(1, cacheWithResults.get(1)), // Key storage has expired
                () -> assertEquals(1, cacheWithResults.computeStats().getEvictionCount()) //Key was evicted
        );
    }
}
