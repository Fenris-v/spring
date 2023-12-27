package org.example.lesson5.config;

import org.example.lesson5.config.properties.AppCacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableCaching
@EnableConfigurationProperties(AppCacheProperties.class)
public class CacheConfiguration {
    @Bean
    public CacheManager redisCacheManager(AppCacheProperties properties, LettuceConnectionFactory factory) {
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        Map<String, RedisCacheConfiguration> redisCacheConfiguration = new HashMap<>();
        properties.getCacheNames().forEach(name -> redisCacheConfiguration
                .put(name, RedisCacheConfiguration
                        .defaultCacheConfig()
                        .entryTtl(properties
                                          .getCaches()
                                          .get(name)
                                          .getExpiry())));

        return RedisCacheManager.builder(factory)
                                .cacheDefaults(configuration)
                                .withInitialCacheConfigurations(redisCacheConfiguration)
                                .build();
    }
}
