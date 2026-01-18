package com.example;

import java.util.Map.Entry;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.RedisClient;
import redis.clients.jedis.bloom.BFReserveParams;

/*
RedisBloom Bloom Filter
  =>  chain of immutable Bloom filters
  =>  auto-growing
  =>  append-only
  =>  capacity is elastic
 */
public class BloomFilterDemo {

    public static void main(String[] args) throws Exception {
        RedisClient redis = RedisClient.create("redis://localhost:6379");

        redis.del("filter:2");
        System.out.println(redis.bfReserve("filter:2", 0.001, 100_000)); // Ok

        System.out.println(redis.bfInfo("filter:2"));

        // Add items in bloom filter
        for (int i = 0; i < 100_0000; i++)
            redis.bfAdd("filter:2", "item" + i);

        System.out.println(redis.bfInfo("filter:2"));

        long i = -1;
        while (i != 0) {
            Entry<Long, byte[]> backup = redis.bfScanDump("filter:2", i);
            i = backup.getKey();
            System.out.println("i: " + i);
            System.out.println("backup: " + backup);
        }
        System.out.println(redis.bfInfo("filter:2"));
    }

}
