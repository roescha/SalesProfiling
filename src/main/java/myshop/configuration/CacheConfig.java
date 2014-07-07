package myshop.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.sf.ehcache.config.CacheConfiguration;

	@Configuration
	@EnableCaching
	public class CacheConfig implements CachingConfigurer {
		@Bean(destroyMethod="shutdown")
    public net.sf.ehcache.CacheManager ehCacheManager() {
        CacheConfiguration cacheConfiguration1 = new CacheConfiguration();
        cacheConfiguration1.setName("flow");
        cacheConfiguration1.setMemoryStoreEvictionPolicy("FIFO");
        cacheConfiguration1.setMaxEntriesLocalHeap(4);
        cacheConfiguration1.logging(true);

        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
        config.addCache(cacheConfiguration1);
        
        CacheConfiguration cacheConfiguration2 = new CacheConfiguration();
        cacheConfiguration2.setName("flows");
        cacheConfiguration2.setMemoryStoreEvictionPolicy("FIFO");
        cacheConfiguration2.setMaxEntriesLocalHeap(4);
        cacheConfiguration2.logging(true);
        
        config.addCache(cacheConfiguration2);
        
        CacheConfiguration cacheConfiguration3 = new CacheConfiguration();
        cacheConfiguration3.setName("aggregation");
        cacheConfiguration3.setMemoryStoreEvictionPolicy("FIFO");
        cacheConfiguration3.setMaxEntriesLocalHeap(4);
        cacheConfiguration3.logging(true);

        config.addCache(cacheConfiguration3);
        
        CacheConfiguration cacheConfiguration4 = new CacheConfiguration();
        cacheConfiguration4.setName("storelist");
        cacheConfiguration4.setMemoryStoreEvictionPolicy("FIFO");
        cacheConfiguration4.setMaxEntriesLocalHeap(2);
        cacheConfiguration4.logging(true);
        
        config.addCache(cacheConfiguration4);
        
        CacheConfiguration cacheConfiguration5 = new CacheConfiguration();
        cacheConfiguration5.setName("range");
        cacheConfiguration5.setMemoryStoreEvictionPolicy("LFU");
        cacheConfiguration5.setMaxBytesLocalHeap("65M");
        cacheConfiguration5.setTimeToLiveSeconds(600);
        cacheConfiguration5.logging(true);
        
        config.addCache(cacheConfiguration5);

        net.sf.ehcache.CacheManager manager = net.sf.ehcache.CacheManager.newInstance(config);
        
        return manager;
    }

    @Bean
    @Override
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManager());
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }
}
