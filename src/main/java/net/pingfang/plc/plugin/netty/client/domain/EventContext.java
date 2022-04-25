package net.pingfang.plc.plugin.netty.client.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @author 王超
 * @description TODO
 * @date 2022-04-20 21:37
 */
@Data
@Builder(toBuilder = true)
public class EventContext {
	final Long aisleId;
	final byte[] msg;

}
