package tqs.air.quality.metrics.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CacheTests {

    private Cache<Integer, Integer> emptyCache = new Cache<>(10L * 1000, key -> 1);


    private Cache<Integer, Integer> cacheWithResults;

    @BeforeEach
    void createCacheWithResults() {
         cacheWithResults = new Cache<>(10L * 1000, key -> 1);

         cacheWithResults.get(1);
         cacheWithResults.get(2);
         cacheWithResults.get(3);
    }

    /*
    @Test
    void firstRequest() {
        assertAll("firstRequest",
                () -> assertEquals(1, emptyCache.),
                () -> assertEquals(3, nonEmptyStack.size())
        );
    }

    */

}
