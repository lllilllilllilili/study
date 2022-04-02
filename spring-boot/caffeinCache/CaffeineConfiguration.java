package com.example.demo.caffeine;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/* 캐싱을 관리해주는 설정 부분이다. 만료, 캐시 사이즈, 리미트를 관리한다. */
@EnableCaching
@Configuration
public class CaffeineConfiguration {
    @Bean
    public Caffeine caffeineConfig() {
        return Caffeine.newBuilder()
                .expireAfterWrite(60, TimeUnit.MINUTES);
    }

    @Bean
    public CacheManager cacheManager(Caffeine caffeine) { //<-- 인자값을 따로 받는것도 이상하네
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
//        caffeineCacheManager.getCache("addresses3");
//        caffeineCacheManager.setCaffeine(caffeine);

        //caffeineCacheManager.setCaffeine(caffeine) 이렇게 들어와야 할것 같은데
        /*
        다른 코드에서는 아래 처럼 사용하드라.
          SimpleCacheManager cacheManager = new SimpleCacheManager();
    List<CaffeineCache> caches = Arrays.stream(CacheType.values())
        .map(cache -> new CaffeineCache(cache.getCacheName(), Caffeine.newBuilder().recordStats()
            .expireAfterWrite(cache.getExpiredAfterWrite(), TimeUnit.SECONDS)
            .maximumSize(cache.getMaximumSize())
            .build()
            )
        )
        .collect(Collectors.toList());
    cacheManager.setCaches(caches);   <-- 요기 주목
         */
        return caffeineCacheManager;
    }
}
