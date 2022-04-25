package net.pingfang.plc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.pingfang.plc.aisle.Method;

/**
 * @author 王超
 * @description TODO
 * @date 2022-04-15 14:50
 */
@Data
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class PlcInfo {
	final Long aisleId;
	final String ip;
	final int port;
	final Method method;
}
