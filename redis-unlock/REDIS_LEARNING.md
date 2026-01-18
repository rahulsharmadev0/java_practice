# Redis & Redis Bloom Filter in Java - Learning Guide

## Table of Contents

1. [Introduction to Redis](#introduction-to-redis)
2. [Setting Up Redis](#setting-up-redis)
3. [Redis Data Types](#redis-data-types)
4. [Java Redis Client - Jedis](#java-redis-client---jedis)
5. [Basic Redis Operations in Java](#basic-redis-operations-in-java)
6. [Redis Bloom Filter](#redis-bloom-filter)
7. [Bloom Filter in Java with RedisBloom](#bloom-filter-in-java-with-redisbloom)
8. [Maven Dependencies](#maven-dependencies)
9. [Best Practices](#best-practices)

---

## Introduction to Redis

**Redis** (Remote Dictionary Server) is an open-source, in-memory data structure store that can be used as:

- **Database** - Persistent key-value store
- **Cache** - High-speed data caching layer
- **Message Broker** - Pub/Sub messaging system
- **Queue** - Task/Job queue management

### Key Features

| Feature               | Description                                                        |
| --------------------- | ------------------------------------------------------------------ |
| **In-Memory**         | Data stored in RAM for ultra-fast access (sub-millisecond latency) |
| **Persistence**       | Optional disk persistence (RDB snapshots, AOF logs)                |
| **Replication**       | Master-slave replication for high availability                     |
| **Clustering**        | Horizontal scaling with automatic partitioning                     |
| **Atomic Operations** | All operations are atomic, ensuring data consistency               |
| **Lua Scripting**     | Server-side scripting for complex operations                       |

### When to Use Redis?

- ✅ Session management
- ✅ Caching frequently accessed data
- ✅ Real-time analytics
- ✅ Leaderboards/Counting
- ✅ Rate limiting
- ✅ Pub/Sub messaging
- ✅ Geospatial data

---

## Setting Up Redis

### Option 1: Docker (Recommended)

```bash
# Start Redis server
docker run -d --name redis -p 6379:6379 redis:latest

# Start Redis with RedisBloom module (for Bloom Filters)
docker run -d --name redis-bloom -p 6379:6379 redis/redis-stack:latest
```

### Option 2: Local Installation (macOS)

```bash
brew install redis
brew services start redis
```

### Verify Installation

```bash
redis-cli ping
# Expected output: PONG
```

---

## Redis Data Types

### 1. Strings

The most basic type. Can store text, numbers, or binary data (max 512MB).

```
SET key "value"
GET key
INCR counter
APPEND key " more text"
```

### 2. Lists

Ordered collections of strings (linked list implementation).

```
LPUSH mylist "first"
RPUSH mylist "last"
LRANGE mylist 0 -1
LPOP mylist
```

### 3. Sets

Unordered collections of unique strings.

```
SADD myset "member1" "member2"
SMEMBERS myset
SISMEMBER myset "member1"
SINTER set1 set2
```

### 4. Hashes

Maps of field-value pairs (like Java HashMap).

```
HSET user:1 name "John" age "30"
HGET user:1 name
HGETALL user:1
```

### 5. Sorted Sets (ZSets)

Sets with a score for each member (ordered by score).

```
ZADD leaderboard 100 "player1" 200 "player2"
ZRANGE leaderboard 0 -1 WITHSCORES
ZRANK leaderboard "player1"
```

### 6. Streams

Append-only log data structure for messaging.

```
XADD mystream * field value
XREAD STREAMS mystream 0
```

---

## Java Redis Client - Jedis

**Jedis** is the most popular Java client for Redis. It's simple, lightweight, and fully compatible with Redis commands.

### Alternative Clients

| Client       | Description                                              |
| ------------ | -------------------------------------------------------- |
| **Jedis**    | Simple, synchronous, thread-safe with connection pooling |
| **Lettuce**  | Async/reactive, built on Netty, thread-safe by design    |
| **Redisson** | High-level API, distributed objects, Spring integration  |

---

## Maven Dependencies

Add these to your `pom.xml`:

```xml
<dependencies>
    <!-- Jedis - Redis Java Client -->
    <dependency>
        <groupId>redis.clients</groupId>
        <artifactId>jedis</artifactId>
        <version>5.1.0</version>
    </dependency>
</dependencies>
```

---

## Basic Redis Operations in Java

### Connection Setup

```java
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisConnectionExample {

    // Simple connection
    public void simpleConnection() {
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            System.out.println("Connected: " + jedis.ping()); // PONG
        }
    }

    // Connection Pool (Recommended for production)
    public JedisPool createPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(128);           // Max connections
        config.setMaxIdle(128);            // Max idle connections
        config.setMinIdle(16);             // Min idle connections
        config.setTestOnBorrow(true);      // Validate on borrow
        config.setTestWhileIdle(true);     // Validate while idle

        return new JedisPool(config, "localhost", 6379);
    }

    // Using connection pool
    public void usePool(JedisPool pool) {
        try (Jedis jedis = pool.getResource()) {
            jedis.set("key", "value");
            String value = jedis.get("key");
            System.out.println("Value: " + value);
        }
    }
}
```

### String Operations

```java
public class RedisStringOperations {

    public void stringOperations(Jedis jedis) {
        // SET and GET
        jedis.set("user:name", "John Doe");
        String name = jedis.get("user:name");
        System.out.println("Name: " + name);

        // SET with expiration (seconds)
        jedis.setex("session:123", 3600, "active");

        // SET with expiration (milliseconds)
        jedis.psetex("temp:data", 5000, "temporary");

        // SET only if NOT exists (NX)
        Long result = jedis.setnx("unique:key", "first-value");
        System.out.println("Set successful: " + (result == 1));

        // Increment/Decrement
        jedis.set("counter", "10");
        jedis.incr("counter");          // 11
        jedis.incrBy("counter", 5);     // 16
        jedis.decr("counter");          // 15
        jedis.decrBy("counter", 3);     // 12

        // Append
        jedis.append("user:name", " Jr.");

        // Get string length
        Long length = jedis.strlen("user:name");

        // Multiple SET/GET
        jedis.mset("key1", "val1", "key2", "val2", "key3", "val3");
        List<String> values = jedis.mget("key1", "key2", "key3");
    }
}
```

### Hash Operations

```java
public class RedisHashOperations {

    public void hashOperations(Jedis jedis) {
        // Set single field
        jedis.hset("user:100", "name", "Alice");
        jedis.hset("user:100", "email", "alice@example.com");

        // Set multiple fields
        Map<String, String> userMap = new HashMap<>();
        userMap.put("name", "Bob");
        userMap.put("age", "25");
        userMap.put("city", "New York");
        jedis.hset("user:101", userMap);

        // Get single field
        String name = jedis.hget("user:100", "name");

        // Get all fields
        Map<String, String> allFields = jedis.hgetAll("user:101");

        // Check if field exists
        boolean exists = jedis.hexists("user:100", "email");

        // Get all field names
        Set<String> fields = jedis.hkeys("user:100");

        // Get all values
        List<String> values = jedis.hvals("user:100");

        // Delete a field
        jedis.hdel("user:100", "email");

        // Increment numeric field
        jedis.hincrBy("user:101", "age", 1); // age becomes 26
    }
}
```

### List Operations

```java
public class RedisListOperations {

    public void listOperations(Jedis jedis) {
        // Push to left (head)
        jedis.lpush("queue", "first", "second", "third");

        // Push to right (tail)
        jedis.rpush("queue", "last");

        // Get range (0 to -1 means all)
        List<String> all = jedis.lrange("queue", 0, -1);

        // Pop from left
        String first = jedis.lpop("queue");

        // Pop from right
        String last = jedis.rpop("queue");

        // Blocking pop (with timeout in seconds)
        List<String> blocked = jedis.blpop(30, "queue");

        // Get list length
        Long length = jedis.llen("queue");

        // Get element at index
        String element = jedis.lindex("queue", 0);

        // Set element at index
        jedis.lset("queue", 0, "new-first");

        // Trim list (keep only range)
        jedis.ltrim("queue", 0, 99); // Keep first 100 elements
    }
}
```

### Set Operations

```java
public class RedisSetOperations {

    public void setOperations(Jedis jedis) {
        // Add members
        jedis.sadd("tags", "java", "redis", "programming");
        jedis.sadd("topics", "redis", "database", "caching");

        // Get all members
        Set<String> members = jedis.smembers("tags");

        // Check membership
        boolean isMember = jedis.sismember("tags", "java");

        // Get set size
        Long size = jedis.scard("tags");

        // Remove member
        jedis.srem("tags", "programming");

        // Random member
        String random = jedis.srandmember("tags");

        // Pop random member
        String popped = jedis.spop("tags");

        // Set operations
        Set<String> union = jedis.sunion("tags", "topics");
        Set<String> intersection = jedis.sinter("tags", "topics");
        Set<String> difference = jedis.sdiff("tags", "topics");
    }
}
```

### Sorted Set Operations

```java
public class RedisSortedSetOperations {

    public void sortedSetOperations(Jedis jedis) {
        // Add with scores
        jedis.zadd("leaderboard", 100, "player1");
        jedis.zadd("leaderboard", 250, "player2");
        jedis.zadd("leaderboard", 180, "player3");

        // Add multiple
        Map<String, Double> scores = new HashMap<>();
        scores.put("player4", 300.0);
        scores.put("player5", 150.0);
        jedis.zadd("leaderboard", scores);

        // Get range by rank (ascending)
        List<String> top3 = jedis.zrange("leaderboard", 0, 2);

        // Get range by rank (descending)
        List<String> top3Desc = jedis.zrevrange("leaderboard", 0, 2);

        // Get range with scores
        List<Tuple> withScores = jedis.zrangeWithScores("leaderboard", 0, -1);
        for (Tuple t : withScores) {
            System.out.println(t.getElement() + ": " + t.getScore());
        }

        // Get rank of member
        Long rank = jedis.zrank("leaderboard", "player1");
        Long revRank = jedis.zrevrank("leaderboard", "player1");

        // Get score
        Double score = jedis.zscore("leaderboard", "player1");

        // Increment score
        jedis.zincrby("leaderboard", 50, "player1");

        // Count members in score range
        Long count = jedis.zcount("leaderboard", 100, 200);

        // Get by score range
        List<String> range = jedis.zrangeByScore("leaderboard", 100, 200);
    }
}
```

### Key Operations & TTL

```java
public class RedisKeyOperations {

    public void keyOperations(Jedis jedis) {
        // Check if key exists
        boolean exists = jedis.exists("mykey");

        // Delete key(s)
        jedis.del("key1", "key2", "key3");

        // Set expiration (seconds)
        jedis.expire("mykey", 3600);

        // Set expiration (milliseconds)
        jedis.pexpire("mykey", 5000);

        // Set expiration at specific time
        jedis.expireAt("mykey", Instant.now().plusSeconds(3600).getEpochSecond());

        // Get TTL (Time To Live)
        Long ttl = jedis.ttl("mykey");      // seconds
        Long pttl = jedis.pttl("mykey");    // milliseconds

        // Remove expiration
        jedis.persist("mykey");

        // Rename key
        jedis.rename("oldkey", "newkey");

        // Get key type
        String type = jedis.type("mykey");

        // Find keys by pattern (use with caution in production!)
        Set<String> keys = jedis.keys("user:*");

        // SCAN (better for production)
        ScanParams params = new ScanParams().match("user:*").count(100);
        String cursor = "0";
        do {
            ScanResult<String> result = jedis.scan(cursor, params);
            cursor = result.getCursor();
            for (String key : result.getResult()) {
                System.out.println("Key: " + key);
            }
        } while (!cursor.equals("0"));
    }
}
```

---

## Redis Bloom Filter

### What is a Bloom Filter?

A **Bloom Filter** is a space-efficient probabilistic data structure used to test whether an element is a member of a set.

```
┌─────────────────────────────────────────────────────────────────┐
│                     BLOOM FILTER CONCEPT                        │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│   "Is element X in the set?"                                    │
│                                                                 │
│   ┌──────────────────┐                                          │
│   │   Bloom Filter   │                                          │
│   └────────┬─────────┘                                          │
│            │                                                    │
│            ▼                                                    │
│   ┌────────────────────────────────────────┐                    │
│   │  "Definitely NOT in set"  ─► 100% sure │                    │
│   │  "Possibly in set"        ─► May be!   │                    │
│   └────────────────────────────────────────┘                    │
│                                                                 │
│   ⚠️ FALSE POSITIVES possible                                   │
│   ✅ FALSE NEGATIVES impossible                                 │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

### Key Characteristics

| Aspect                 | Description                                              |
| ---------------------- | -------------------------------------------------------- |
| **Space Efficient**    | Uses much less memory than storing actual elements       |
| **Fast**               | O(k) time complexity, where k = number of hash functions |
| **False Positives**    | "Might be in set" can be wrong                           |
| **No False Negatives** | "Definitely not in set" is always correct                |
| **No Deletion**        | Standard Bloom filters don't support deletion            |

### Use Cases

- ✅ **Spam Filtering** - Quick check if email is spam
- ✅ **Database Queries** - Avoid expensive disk lookups
- ✅ **Cache Optimization** - Check before querying cache
- ✅ **Username Availability** - Quick check if username taken
- ✅ **Duplicate Detection** - Prevent processing duplicates
- ✅ **Web Crawlers** - Track visited URLs
- ✅ **Password Breach Check** - Check against known breached passwords

### How It Works

```
Step 1: INSERT "hello"
┌───────────────────────────────────────────────┐
│ Bit Array (initially all 0s):                 │
│ [0][0][0][0][0][0][0][0][0][0][0][0]          │
│                                               │
│ hash1("hello") = 2                            │
│ hash2("hello") = 5                            │
│ hash3("hello") = 9                            │
│                                               │
│ After insert:                                 │
│ [0][0][1][0][0][1][0][0][0][1][0][0]          │
│       ↑        ↑           ↑                  │
│       2        5           9                  │
└───────────────────────────────────────────────┘

Step 2: CHECK "hello"
┌───────────────────────────────────────────────┐
│ hash1("hello") = 2 → bit[2] = 1 ✓            │
│ hash2("hello") = 5 → bit[5] = 1 ✓            │
│ hash3("hello") = 9 → bit[9] = 1 ✓            │
│                                               │
│ All bits are 1 → "POSSIBLY in set"           │
└───────────────────────────────────────────────┘

Step 3: CHECK "world"
┌───────────────────────────────────────────────┐
│ hash1("world") = 3 → bit[3] = 0 ✗            │
│                                               │
│ At least one bit is 0 → "DEFINITELY NOT"     │
└───────────────────────────────────────────────┘
```

---

## Bloom Filter in Java with RedisBloom

### RedisBloom Module

**RedisBloom** is a Redis module that provides probabilistic data structures:

- **Bloom Filter** - Membership testing
- **Cuckoo Filter** - Bloom filter with deletion support
- **Count-Min Sketch** - Frequency estimation
- **Top-K** - Track top K elements

### Using Jedis Directly (Low-Level)

```java
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import java.util.Arrays;

public class BloomFilterWithJedis {

    private final JedisPool pool;

    public BloomFilterWithJedis(JedisPool pool) {
        this.pool = pool;
    }

    /**
     * Create a Bloom filter with specified error rate and capacity
     */
    public void createFilter(String filterName, double errorRate, long capacity) {
        try (Jedis jedis = pool.getResource()) {
            jedis.sendCommand(() -> "BF.RESERVE".getBytes(),
                filterName,
                String.valueOf(errorRate),
                String.valueOf(capacity));
        }
    }

    /**
     * Add an item to the Bloom filter
     */
    public boolean add(String filterName, String item) {
        try (Jedis jedis = pool.getResource()) {
            Object result = jedis.sendCommand(() -> "BF.ADD".getBytes(),
                filterName, item);
            return result != null && ((Long) result) == 1;
        }
    }

    /**
     * Check if an item might exist in the Bloom filter
     */
    public boolean exists(String filterName, String item) {
        try (Jedis jedis = pool.getResource()) {
            Object result = jedis.sendCommand(() -> "BF.EXISTS".getBytes(),
                filterName, item);
            return result != null && ((Long) result) == 1;
        }
    }

    /**
     * Add multiple items to the Bloom filter
     */
    public void addMultiple(String filterName, String... items) {
        try (Jedis jedis = pool.getResource()) {
            String[] args = new String[items.length + 1];
            args[0] = filterName;
            System.arraycopy(items, 0, args, 1, items.length);
            jedis.sendCommand(() -> "BF.MADD".getBytes(), args);
        }
    }

    /**
     * Check multiple items existence
     */
    public boolean[] existsMultiple(String filterName, String... items) {
        try (Jedis jedis = pool.getResource()) {
            String[] args = new String[items.length + 1];
            args[0] = filterName;
            System.arraycopy(items, 0, args, 1, items.length);

            @SuppressWarnings("unchecked")
            java.util.List<Long> results = (java.util.List<Long>)
                jedis.sendCommand(() -> "BF.MEXISTS".getBytes(), args);

            boolean[] exists = new boolean[results.size()];
            for (int i = 0; i < results.size(); i++) {
                exists[i] = results.get(i) == 1;
            }
            return exists;
        }
    }

    /**
     * Get Bloom filter info
     */
    public void getInfo(String filterName) {
        try (Jedis jedis = pool.getResource()) {
            Object info = jedis.sendCommand(() -> "BF.INFO".getBytes(), filterName);
            System.out.println("Filter Info: " + info);
        }
    }
}
```

### Practical Example: Username Availability Check

```java
import io.rebloom.client.Client;
import redis.clients.jedis.JedisPool;

public class UsernameChecker {

    private static final String FILTER_NAME = "registered_usernames";
    private static final double ERROR_RATE = 0.01; // 1% false positive rate
    private static final long CAPACITY = 1_000_000; // Expected 1 million users

    private final Client bloomClient;
    private final JedisPool jedisPool;

    public UsernameChecker(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
        this.bloomClient = new Client(jedisPool);
        initializeFilter();
    }

    private void initializeFilter() {
        try {
            bloomClient.createFilter(FILTER_NAME, CAPACITY, ERROR_RATE);
            System.out.println("Bloom filter created successfully");
        } catch (Exception e) {
            // Filter might already exist
            System.out.println("Bloom filter already exists");
        }
    }

    /**
     * Quick check if username might be taken (uses Bloom Filter)
     * @return true if username might be taken, false if definitely available
     */
    public boolean mightBeTaken(String username) {
        String normalized = username.toLowerCase().trim();
        return bloomClient.exists(FILTER_NAME, normalized);
    }

    /**
     * Register a new username
     */
    public RegistrationResult registerUsername(String username) {
        String normalized = username.toLowerCase().trim();

        // Step 1: Quick Bloom filter check
        if (mightBeTaken(normalized)) {
            // Step 2: Bloom filter says "maybe" - do expensive DB check
            if (checkDatabaseForUsername(normalized)) {
                return RegistrationResult.ALREADY_TAKEN;
            }
            // False positive from Bloom filter - username is actually available
        }

        // Step 3: Register in database
        if (registerInDatabase(normalized)) {
            // Step 4: Add to Bloom filter
            bloomClient.add(FILTER_NAME, normalized);
            return RegistrationResult.SUCCESS;
        }

        return RegistrationResult.ERROR;
    }

    private boolean checkDatabaseForUsername(String username) {
        // Simulate database check
        // In real application, query your database here
        return false;
    }

    private boolean registerInDatabase(String username) {
        // Simulate database registration
        // In real application, insert into your database here
        return true;
    }

    public enum RegistrationResult {
        SUCCESS,
        ALREADY_TAKEN,
        ERROR
    }
}
```

### Practical Example: URL Deduplication for Web Crawler

```java
import io.rebloom.client.Client;
import redis.clients.jedis.JedisPool;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Base64;

public class WebCrawlerDeduplicator {

    private static final String FILTER_NAME = "crawled_urls";
    private static final double ERROR_RATE = 0.001; // 0.1% false positive
    private static final long CAPACITY = 100_000_000; // 100 million URLs

    private final Client bloomClient;

    public WebCrawlerDeduplicator(JedisPool pool) {
        this.bloomClient = new Client(pool);
        initializeFilter();
    }

    private void initializeFilter() {
        try {
            bloomClient.createFilter(FILTER_NAME, CAPACITY, ERROR_RATE);
        } catch (Exception e) {
            // Filter exists
        }
    }

    /**
     * Check if URL should be crawled
     * @return true if URL should be crawled (not seen before)
     */
    public boolean shouldCrawl(String url) {
        String normalizedUrl = normalizeUrl(url);
        String urlHash = hashUrl(normalizedUrl);

        // If Bloom filter says "definitely not seen", we should crawl
        // If it says "maybe seen", we skip (accept false positive)
        return !bloomClient.exists(FILTER_NAME, urlHash);
    }

    /**
     * Mark URL as crawled
     */
    public void markAsCrawled(String url) {
        String normalizedUrl = normalizeUrl(url);
        String urlHash = hashUrl(normalizedUrl);
        bloomClient.add(FILTER_NAME, urlHash);
    }

    /**
     * Process a batch of URLs
     */
    public void processBatch(java.util.List<String> urls) {
        for (String url : urls) {
            if (shouldCrawl(url)) {
                // Crawl the URL
                crawlUrl(url);
                markAsCrawled(url);
            }
        }
    }

    private String normalizeUrl(String url) {
        try {
            URL parsed = new URL(url);
            return parsed.getProtocol() + "://" +
                   parsed.getHost().toLowerCase() +
                   parsed.getPath();
        } catch (Exception e) {
            return url.toLowerCase();
        }
    }

    private String hashUrl(String url) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(url.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception e) {
            return url;
        }
    }

    private void crawlUrl(String url) {
        // Implement actual crawling logic
        System.out.println("Crawling: " + url);
    }
}
```

### Practical Example: Spam Email Filter

```java
import io.rebloom.client.Client;
import redis.clients.jedis.JedisPool;

public class SpamFilter {

    private static final String SPAM_FILTER = "spam_signatures";
    private static final String SAFE_FILTER = "safe_signatures";

    private final Client bloomClient;

    public SpamFilter(JedisPool pool) {
        this.bloomClient = new Client(pool);
        initializeFilters();
    }

    private void initializeFilters() {
        try {
            bloomClient.createFilter(SPAM_FILTER, 10_000_000, 0.001);
            bloomClient.createFilter(SAFE_FILTER, 10_000_000, 0.01);
        } catch (Exception e) {
            // Filters exist
        }
    }

    /**
     * Train the filter with known spam
     */
    public void trainSpam(String emailContent) {
        String signature = extractSignature(emailContent);
        bloomClient.add(SPAM_FILTER, signature);
    }

    /**
     * Train the filter with known safe emails
     */
    public void trainSafe(String emailContent) {
        String signature = extractSignature(emailContent);
        bloomClient.add(SAFE_FILTER, signature);
    }

    /**
     * Check if email is likely spam
     */
    public SpamCheckResult checkEmail(String emailContent) {
        String signature = extractSignature(emailContent);

        boolean isKnownSafe = bloomClient.exists(SAFE_FILTER, signature);
        boolean isKnownSpam = bloomClient.exists(SPAM_FILTER, signature);

        if (isKnownSafe && !isKnownSpam) {
            return SpamCheckResult.SAFE;
        } else if (isKnownSpam && !isKnownSafe) {
            return SpamCheckResult.SPAM;
        } else if (isKnownSpam && isKnownSafe) {
            // Both filters triggered - needs further analysis
            return SpamCheckResult.NEEDS_REVIEW;
        } else {
            return SpamCheckResult.UNKNOWN;
        }
    }

    private String extractSignature(String content) {
        // Extract key features from email
        // In real implementation, use more sophisticated NLP
        return content.replaceAll("\\s+", " ")
                      .toLowerCase()
                      .substring(0, Math.min(content.length(), 500));
    }

    public enum SpamCheckResult {
        SAFE,
        SPAM,
        NEEDS_REVIEW,
        UNKNOWN
    }
}
```

### Cuckoo Filter (Alternative with Deletion Support)

```java
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class CuckooFilterExample {

    private final JedisPool pool;

    public CuckooFilterExample(JedisPool pool) {
        this.pool = pool;
    }

    /**
     * Create a Cuckoo filter
     */
    public void createFilter(String filterName, long capacity) {
        try (Jedis jedis = pool.getResource()) {
            jedis.sendCommand(() -> "CF.RESERVE".getBytes(),
                filterName, String.valueOf(capacity));
        }
    }

    /**
     * Add item to Cuckoo filter
     */
    public boolean add(String filterName, String item) {
        try (Jedis jedis = pool.getResource()) {
            Object result = jedis.sendCommand(() -> "CF.ADD".getBytes(),
                filterName, item);
            return ((Long) result) == 1;
        }
    }

    /**
     * Check if item exists
     */
    public boolean exists(String filterName, String item) {
        try (Jedis jedis = pool.getResource()) {
            Object result = jedis.sendCommand(() -> "CF.EXISTS".getBytes(),
                filterName, item);
            return ((Long) result) == 1;
        }
    }

    /**
     * Delete item from Cuckoo filter (not possible with Bloom filter!)
     */
    public boolean delete(String filterName, String item) {
        try (Jedis jedis = pool.getResource()) {
            Object result = jedis.sendCommand(() -> "CF.DEL".getBytes(),
                filterName, item);
            return ((Long) result) == 1;
        }
    }

    public void demonstrateDeletion() {
        String filterName = "session_tokens";

        createFilter(filterName, 10000);

        // Add a session token
        add(filterName, "token_abc123");
        System.out.println("Exists after add: " + exists(filterName, "token_abc123"));

        // User logs out - delete the token
        delete(filterName, "token_abc123");
        System.out.println("Exists after delete: " + exists(filterName, "token_abc123"));
    }
}
```

---

## Best Practices

### 1. Connection Management

```java
// ✅ DO: Use connection pooling
JedisPool pool = new JedisPool(config, "localhost", 6379);
try (Jedis jedis = pool.getResource()) {
    // Use jedis
}

// ❌ DON'T: Create new connections for each operation
Jedis jedis = new Jedis("localhost", 6379);
// ... use and forget to close
```

### 2. Key Naming Convention

```java
// ✅ DO: Use structured, namespaced keys
"user:1234:profile"
"session:abc123:data"
"cache:products:category:electronics"

// ❌ DON'T: Use flat, ambiguous keys
"user1234"
"abc123"
"products"
```

### 3. Bloom Filter Sizing

```java
// ✅ DO: Calculate appropriate size
// Formula: m = -n * ln(p) / (ln(2)^2)
// where n = expected elements, p = false positive rate

long capacity = 1_000_000;    // Expected elements
double errorRate = 0.01;       // 1% false positive rate
// This will use approximately 1.2 MB of memory

// ❌ DON'T: Undersize your filter
long tooSmall = 1000;         // Will cause high false positive rate
```

### 4. Error Handling

```java
// ✅ DO: Handle Redis connection errors gracefully
public Optional<String> getValue(JedisPool pool, String key) {
    try (Jedis jedis = pool.getResource()) {
        return Optional.ofNullable(jedis.get(key));
    } catch (JedisConnectionException e) {
        logger.error("Redis connection failed", e);
        return Optional.empty();
    }
}

// ❌ DON'T: Let exceptions propagate unchecked
public String getValue(JedisPool pool, String key) {
    return pool.getResource().get(key); // Resource leak if exception!
}
```

### 5. TTL for Cache Data

```java
// ✅ DO: Always set TTL for cache entries
jedis.setex("cache:user:123", 3600, userData); // 1 hour TTL

// ❌ DON'T: Cache without expiration (memory leak)
jedis.set("cache:user:123", userData);
```

### 6. Bloom Filter vs Cuckoo Filter Decision

| Use Case                 | Recommended                         |
| ------------------------ | ----------------------------------- |
| Read-heavy, no deletions | **Bloom Filter**                    |
| Need deletion support    | **Cuckoo Filter**                   |
| Very large datasets      | **Bloom Filter** (slightly smaller) |
| Count occurrences        | **Count-Min Sketch**                |

---

## Quick Reference Commands

### Redis CLI Bloom Filter Commands

```bash
# Create filter
BF.RESERVE myfilter 0.01 10000

# Add items
BF.ADD myfilter "item1"
BF.MADD myfilter "item1" "item2" "item3"

# Check existence
BF.EXISTS myfilter "item1"
BF.MEXISTS myfilter "item1" "item2"

# Get filter info
BF.INFO myfilter
```

### Redis CLI Cuckoo Filter Commands

```bash
# Create filter
CF.RESERVE myfilter 10000

# Add items
CF.ADD myfilter "item1"

# Check existence
CF.EXISTS myfilter "item1"

# Delete item
CF.DEL myfilter "item1"
```

---

## Summary

| Topic               | Key Takeaways                                           |
| ------------------- | ------------------------------------------------------- |
| **Redis**           | In-memory data store with multiple data structures      |
| **Jedis**           | Simple, synchronous Java client with connection pooling |
| **Bloom Filter**    | Space-efficient probabilistic set membership            |
| **False Positives** | Bloom filters can say "maybe" when it's "no"            |
| **False Negatives** | Never says "no" when it's actually "yes"                |
| **Cuckoo Filter**   | Like Bloom filter but supports deletion                 |
| **Best Practices**  | Pool connections, namespace keys, set TTLs              |

---

## Additional Resources

- [Redis Documentation](https://redis.io/docs/)
- [Jedis GitHub](https://github.com/redis/jedis)
- [RedisBloom Documentation](https://redis.io/docs/stack/bloom/)
- [Bloom Filter Calculator](https://hur.st/bloomfilter/)
