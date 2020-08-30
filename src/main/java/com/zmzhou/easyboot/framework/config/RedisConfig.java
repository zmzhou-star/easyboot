package com.zmzhou.easyboot.framework.config;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;

import lombok.extern.slf4j.Slf4j;

/**
 *  @title RedisConfig
 *  @Description 描述
 *  @author zmzhou
 *  @Date 2020/07/08 19:14
 */
@Slf4j
@EnableCaching
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

    /**
     *  设置 redis 数据默认过期时间，默认6小时
     *  设置@cacheable 序列化方式
     */
    @Bean
    public RedisCacheConfiguration redisCacheConfiguration(){
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();
        configuration = configuration.serializeValuesWith(RedisSerializationContext.SerializationPair
                .fromSerializer(fastJsonRedisSerializer)).entryTtl(Duration.ofHours(6));
        return configuration;
    }

    /**
     * 注册redisTemplate
     * @param redisConnectionFactory RedisConnectionFactory
     * @return RedisTemplate<String, Object>
     * @author zmzhou
     * @date 2020/08/26 16:47
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        //序列化
        FastJsonRedisSerializer<Object> fastJsonRedisSerializer = new FastJsonRedisSerializer<>(Object.class);
        // value值的序列化采用fastJsonRedisSerializer
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);
        // 全局开启AutoType，这里方便开发，使用全局的方式
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
        // key的序列化采用StringRedisSerializer
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
    /**
     * CacheErrorHandler
     * @return CacheErrorHandler
     * @author zmzhou
     * @date 2020/08/26 16:48
     */
    @Bean
    @Override
    public CacheErrorHandler errorHandler() {
        // 异常处理，当Redis发生异常时，打印日志，但是程序正常走
        log.info("初始化 -> [{}]", "Redis CacheErrorHandler");
        return new CacheErrorHandler() {
            @Override
            public void handleCacheGetError(RuntimeException e, Cache cache, Object key) {
                log.error("Redis occur handleCacheGetError：key -> [{}]", key, e);
            }
            @Override
            public void handleCachePutError(RuntimeException e, Cache cache, Object key, Object value) {
                log.error("Redis occur handleCachePutError：key -> [{}]；value -> [{}]", key, value, e);
            }
            @Override
            public void handleCacheEvictError(RuntimeException e, Cache cache, Object key) {
                log.error("Redis occur handleCacheEvictError：key -> [{}]", key, e);
            }
            @Override
            public void handleCacheClearError(RuntimeException e, Cache cache) {
                log.error("Redis occur handleCacheClearError：", e);
            }
        };
    }

    /**
     *  @title FastJsonRedisSerializer
     *  @Description Value 序列化
     *  @author zmzhou
     *  @Date 2020/07/09 10:15
     */
    static class FastJsonRedisSerializer<T> implements RedisSerializer<T> {
        private final Class<T> clazz;
        FastJsonRedisSerializer(Class<T> clazz) {
            super();
            this.clazz = clazz;
        }

        @Override
        public byte[] serialize(T t) {
            if (t == null) {
                return new byte[0];
            }
            return JSON.toJSONString(t, SerializerFeature.WriteClassName).getBytes(StandardCharsets.UTF_8);
        }

        @Override
        public T deserialize(byte[] bytes) {
            if (bytes == null || bytes.length <= 0) {
                return null;
            }
            String str = new String(bytes, StandardCharsets.UTF_8);
            return JSON.parseObject(str, clazz);
        }

    }

    /**
     *  @title StringRedisSerializer
     *  @Description 重写序列化器
     *  @author zmzhou
     *  @Date 2020/07/09 10:14
     */
    static class StringRedisSerializer implements RedisSerializer<Object> {

        private final Charset charset;

        StringRedisSerializer() {
            this(StandardCharsets.UTF_8);
        }

        private StringRedisSerializer(Charset charset) {
            this.charset = charset;
        }

        @Override
        public String deserialize(byte[] bytes) {
            return (bytes == null ? null : new String(bytes, charset));
        }

        @Override
        public byte[] serialize(Object object) {
            String string = JSON.toJSONString(object);
            if (StringUtils.isBlank(string)) {
                return null;
            }
            string = string.replace("\"", "");
            return string.getBytes(charset);
        }
    }
}
