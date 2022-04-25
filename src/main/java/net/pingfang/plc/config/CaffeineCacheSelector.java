package net.pingfang.plc.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import lombok.extern.slf4j.Slf4j;

/**
 * @author 王超
 * @description TODO
 * @date 2022-04-22 0:37
 */
@Slf4j
public class CaffeineCacheSelector implements ImportSelector {

	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		Class<?> annotationType = EnableCaffeineCache.class;
		AnnotationAttributes attributes = AnnotationAttributes
				.fromMap(importingClassMetadata.getAnnotationAttributes(annotationType.getName(), false));
		Boolean enable;
		if (attributes.getBoolean("enable"))
			enable = true;
		else
			enable = false;
		if (enable) {
			return new String[] { CacheAutoConfiguration.class.getName() };
		}
		return new String[0];
	}

	@Configuration
	// 扫描ioc 的bean 可以扫描第三方包
	@ComponentScan("net.pingfang.plc.*")
	public class CacheAutoConfiguration {
//		@Bean
//		public Cache<String, Object> creatCaffeineCache(CacheProperty cacheProperty) {
//			log.info("plc插件已经启动了 ------------------");
//			return Caffeine.newBuilder()
//					// 设置最后一次写入或访问后经过固定时间过期
//					.expireAfterWrite(cacheProperty.getDuration(), TimeUnit.valueOf(cacheProperty.getType()))
//					// 初始化缓存空间大小
//					.initialCapacity(cacheProperty.getInitialCapacity())
//					// 最大缓存数
//					.maximumSize(cacheProperty.getMaximumSize())
//					// 打开value的弱引用
//					.weakValues()
//					// 打开key的弱引用
//					.weakKeys().build();
//		}
	}
}
