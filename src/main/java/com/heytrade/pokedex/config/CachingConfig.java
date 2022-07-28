package com.heytrade.pokedex.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CachingConfig {

    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager("evolutions", "pokemons");
        cacheManager.setCaffeine(caffeineCacheBuilder());
        return cacheManager;
    }

    private Caffeine<Object, Object> caffeineCacheBuilder() {
        return Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(100)
                .expireAfterAccess(2, TimeUnit.SECONDS)
                .recordStats();
    }
}
