package net.pingfang.plc.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * @author 王超
 * @description TODO
 * @date 2022-04-20 17:37
 */
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class MessageReq {
	final Long aisleId;
	final String msg;

}
