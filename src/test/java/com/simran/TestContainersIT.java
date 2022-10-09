package com.simran;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import redis.clients.jedis.Jedis;

@Testcontainers
public class TestContainersIT
{
    public static final int REDIS_PORT = 6379;
    static final DockerImageName REDIS_IMAGE = DockerImageName.parse("redis:7.0.5");

    @Container
    static final GenericContainer<?> redis = new GenericContainer<>(REDIS_IMAGE).withExposedPorts(REDIS_PORT);

    @Test
    public void testRedisConnection()
    {
        try (final Jedis jedis = new Jedis(redis.getHost(), redis.getMappedPort(REDIS_PORT))) {
            jedis.connect();
            System.out.println("Connected to Redis");
        } catch (final Exception ex) {
            Assertions.fail();
        }
    }
}
