package net.pingfang.plc.config;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * @author 王超
 * @description TODO
 * @date 2022-04-22 0:36
 */

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
//引入选择器
@Import(CaffeineCacheSelector.class)
public @interface EnableCaffeineCache {
	/**
	 * 默认开关
	 *
	 * @return
	 */
	boolean enable() default false;
}
