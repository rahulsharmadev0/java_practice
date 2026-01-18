package com.example;

import java.util.Map;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.HSetExParams;

/**
 * Main application entry point.
 */
public class App {

    static void sleep(long millis) throws Exception {
        Thread.sleep(millis);
        print("====" + millis + "millis" + "====");
    }

    static record Sleep(long millis) {
        static Sleep ms(long ms) {
            return new Sleep(ms);
        }

        void run() throws Exception {
            System.out.println("====" + millis + "millis" + "====");
            Thread.sleep(millis);
        }
    }

    static void print(Object obj) {
        System.out.println(obj);
    }

    static <T> void printAll(T... objects) throws Exception {
        for (T object : objects)
            if (object instanceof Sleep)
                ((Sleep) object).run();
            else if (object instanceof Runnable)
                (new Thread((Runnable) object)).start();
            else
                System.out.println(object);
    }

    public static void main(String[] args) throws Exception {
        // Single Connecation
        Jedis jedis = new Jedis("localhost", 6379);

        // setGetAndWithExpiry(jedis);

        // incrementDecrement(jedis);

        // stringOperations(jedis);

        // hmapOperations(jedis);

        // listOperation(jedis);

        jedis.close();
    }

    private static void listOperation(Jedis jedis) throws Exception {
        printAll(
                // add from head/head
                jedis.lpush("cities", "delhi", "goa", "punjab"),

                // add from tail/right
                jedis.rpush("cities", "rohine", "raja vihar"),

                jedis.llen("cities"),

                jedis.lindex("cities", 0),
                jedis.lindex("cities", 1),

                jedis.lrange("cities", 0, -1),
                jedis.lrange("cities", 0, 2),

                jedis.lpop("cities"), // remove 1 from left
                jedis.lpop("cities", 2), // remove 2 from left
                jedis.rpop("cities"), // remove 1 from right
                jedis.rpop("cities", 2), // remove 2 from right

                jedis.del("list"),

                jedis.lrange("list", 0, -1),

                (Runnable) () -> print(new Jedis("localhost", 6379).blpop(3, "list")),

                Sleep.ms(100),
                jedis.lpush("list", "12", "2"),
                jedis.lrange("list", 0, -1),

                Sleep.ms(3000),
                jedis.lrange("list", 0, -1));
    }

    private static void hmapOperations(Jedis jedis) throws Exception {
        printAll(
                jedis.del("student:s1"), // exist 1, else 0
                jedis.hset("student:s1", Map.of("name", "rahul", "age", "23", "city", "delhi")), // Length of map
                jedis.hset("student:s1", Map.of("rollno", "12", "gender", "male")), // 2
                jedis.hget("student:s1", "name"), // rahul
                jedis.hgetAll("student:s1"), // complete map
                jedis.hkeys("student:s1"), // Get all keys only
                jedis.hvals("student:s1"), // Get all values only
                jedis.hexists("student:s1", "name"), // true
                jedis.hexists("student:s1", "location"), // false
                jedis.hincrBy("student:s1", "age", 2), // 25 (Increment)
                jedis.hincrBy("student:s1", "age", -2) // 23 (Decrement)
        );

        sleep(500);

        for (int i = 0; i < 10; i++)
            jedis.hrandfield("student:s1");

        sleep(500);

        printAll(
                jedis.hsetex("student:s1", HSetExParams.hSetExParams().ex(2), Map.of("amount", "120")),
                Sleep.ms(1000),
                jedis.hget("student:s1", "amount"), // 120
                Sleep.ms(2000),
                jedis.hget("student:s1", "amount"), // null
                jedis.hgetAll("student:s1"), // all key-value pair except `amount`
                jedis.hexpire("student:s1", 1, "gender", "rollno"),
                jedis.hgetAll("student:s1"),
                jedis.hgetAll("student:s1"),
                Sleep.ms(1000),
                jedis.hgetAll("student:s1"));
    }

    private static void stringOperations(Jedis jedis) throws Exception {
        printAll(
                jedis.set("name", "Rahul"), // OK
                jedis.append("name", " Sharma"), // Length
                jedis.get("name"), // Rahul Sharma
                jedis.strlen("name") // Length of value
        );
    }

    static void setGetAndWithExpiry(Jedis jedis) throws Exception {
        printAll(
                jedis.set("rahul", "rahul@gmail.com"), // Ok
                jedis.set("rahul", "rahul2@gmail.com"), // override and return OK
                jedis.setnx("rahul", "rahul3@gmail.com"), // not-override and return 0
                jedis.get("rahul"), // return latest value

                Sleep.ms(500),

                // set with expiry (sec)
                jedis.setex("raju", 3, "raju@gmail.com"),

                Sleep.ms(1000), // wait for 1s
                jedis.get("raju"), // raju@gmail.com

                Sleep.ms(2000), // wait for 2s
                jedis.get("raju"), // null

                jedis.mset("key1", "value1", "key2", "value2"), // Ok
                jedis.mget("key1", "key2") // Ok
        );
    }

    static void incrementDecrement(Jedis jedis) throws Exception {
        printAll(
                jedis.set("counter", "0"), // OK
                jedis.incr("counter"), // 1
                jedis.incr("counter"), // 2
                jedis.incrBy("counter", 5), // 7

                Sleep.ms(500),

                jedis.decrBy("counter", 5), // 2
                jedis.decr("counter"), // 1
                jedis.decr("counter"), // 0
                jedis.decr("counter"), // -1

                Sleep.ms(500),

                jedis.incrByFloat("counter", 0.5), // -0.5 [value convert int float]

                // Once a key becomes float → it's forever a float (unless reset).

                Sleep.ms(500),

                jedis.decr("counter") // Exception since value is not an integer
        );
    }

}
