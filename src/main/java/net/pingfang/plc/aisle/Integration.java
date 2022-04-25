package net.pingfang.plc.aisle;

import java.util.List;

import net.pingfang.plc.plugin.netty.state.DeviceLog;

/**
 * @author 王超
 * @description TODO
 * @date 2022-04-12 11:51
 */
public interface Integration {

	void connect();

	void sendMsg(Object o);

	void close() throws InterruptedException;

	Method getMethod();

	State getStatus();

	List<DeviceLog> getLogs();

}
