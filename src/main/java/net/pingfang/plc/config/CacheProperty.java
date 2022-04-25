package net.pingfang.plc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * @author 王超
 * @description TODO
 * @date 2022-04-22 0:40
 */
@Configuration
@ConfigurationProperties(prefix = "cache")
@Data
public class CacheProperty {

	private Boolean enable;
	private long duration;
	private int initialCapacity;
	private long maximumSize;
	private String type;
}
