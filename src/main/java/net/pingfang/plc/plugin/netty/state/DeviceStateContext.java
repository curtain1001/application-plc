package net.pingfang.plc.plugin.netty.state;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.pingfang.plc.aisle.State;
import net.pingfang.plc.utils.DateUtils;

/**
 * describe:设备状态切换类
 *
 * @author helloworldyu
 * @data 2018/3/27
 */
@Data
@Builder
@AllArgsConstructor
public class DeviceStateContext {

	List<DeviceLog> deviceLogs = new ArrayList<>();

	/**
	 * 设备的 deviceId
	 */
	private long deviceId;

	/**
	 * 设备的上次更新时间
	 */
	private String lastUpdateTime;

	/**
	 * 设备当前状态
	 */
	private State state;

	public DeviceStateContext(long deviceId) {
		this.deviceId = deviceId;
		this.lastUpdateTime = DateUtils.getCurrentTime();
		setState(State.init, lastUpdateTime);
	}

	public void setState(State state) {
		if (this.state == state) {
			return;
		}
		this.state = state;
		// 把每次切换的状态加入到历史状态中
		deviceLogs.add(new DeviceLog(state, DateUtils.getCurrentTime()));
	}

	public void setState(State state, String currentTime) {
		this.state = state;
		// 把每次切换的状态加入到历史状态中
		deviceLogs.add(new DeviceLog(state, currentTime));
	}

	public void pushLog(Object msg) {
		deviceLogs.add(new DeviceLog(state, msg, DateUtils.getCurrentTime()));
	}

}
