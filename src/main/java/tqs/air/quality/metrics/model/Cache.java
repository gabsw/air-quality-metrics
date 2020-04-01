package tqs.air.quality.metrics.model;

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

    public Cache(long millisToLive, Function<K, V> loader) {
        this.millisToLive = millisToLive;
        this.loader = loader;
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

        return extractFromNode(node);
    }

    public CacheStats stats() {
        return new CacheStats(totalRequests, hitCount, missCount, evictionCount);
    }

    private V extractFromNode(Node node) {
        return node == null ? null : node.getValue();
    }

    private class Node {
        private final V value;
        private final long timestamp = System.currentTimeMillis();

        public Node(V value) {
            this.value = value;
        }

        public boolean isExpired(long millisToLive) {
            return System.currentTimeMillis() > timestamp + millisToLive;
        }

        public V getValue() {
            return value;
        }
    }
}
