package net.pingfang.plc.plugin.netty.state;

/**
 * @author 王超
 * @description TODO
 * @date 2022-04-21 16:01
 */

import lombok.Data;
import lombok.ToString;
import net.pingfang.plc.aisle.State;

/**
 * 记录状态转换的历史
 */
@Data
@ToString
public class DeviceLog {
	private final State state;
	private final String currentTime;
	private final Object msg;

	public DeviceLog(State state, String currentTime) {
		this.state = state;
		this.currentTime = currentTime;
		this.msg = "";
	}

	public DeviceLog(State state, Object msg, String currentTime) {
		this.state = state;
		this.currentTime = currentTime;
		this.msg = msg;
	}
}
