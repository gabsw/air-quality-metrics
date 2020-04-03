package tqs.air.quality.metrics.model;

import tqs.air.quality.metrics.utils.Time;
import tqs.air.quality.metrics.utils.TimeImpl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class Cache<K, V> {
    private final Map<K, Node> map = new ConcurrentHashMap<>();
    private final long millisToLive;
    private final Function<K, V> loader;
    private long totalRequests;
    private long hitCount;
    private long missCount;
    private long evictionCount;
    private Time time;

    public Cache(long millisToLive, Function<K, V> loader) {
        this.millisToLive = millisToLive;
        this.loader = loader;
        this.time = new TimeImpl();
    }

    public Cache(long millisToLive, Function<K, V> loader, Time time) {
        this.millisToLive = millisToLive;
        this.loader = loader;
        this.time = time;
    }

    public V get(K key) {
        totalRequests++;
        Node node = map.compute(key, (k, n) -> {
            if (n == null) {
                missCount++;
                V value = loader.apply(k);
                return new Node(value);
            } else if (n.isExpired(millisToLive)) {
                evictionCount++;
                V value = loader.apply(k);
                return new Node(value);
            } else {
                hitCount++;
                return n;
            }
        });
        return node.getValue();
    }

    public CacheStats computeStats() {
        return new CacheStats(totalRequests, hitCount, missCount, evictionCount);
    }

    private class Node {
        private final V value;
        private final long timestamp = time.currentTimeMillis();

        Node(V value) {
            this.value = value;
        }

        boolean isExpired(long millisToLive) {
            return time.currentTimeMillis() > timestamp + millisToLive;
        }

        V getValue() {
            return value;
        }
    }
}
