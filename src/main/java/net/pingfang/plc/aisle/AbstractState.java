package net.pingfang.plc.aisle;

import net.pingfang.plc.plugin.netty.state.DeviceStateContext;

/**
 * @author 王超
 * @description TODO
 * @date 2022-04-21 11:09
 */
public abstract class AbstractState implements IDeviceState {
	protected DeviceStateContext stateCtx;

	public AbstractState(DeviceStateContext stateCtx) {
		this.stateCtx = stateCtx;
	}

	@Override
	public void onInit(long connectedTime, String describe) {

	}

	@Override
	public void onConnect(long connectedTime, String describe) {
		throw new IllegalStateException(getStateName() + " 此状态不应该进行链接动作");
	}

	@Override
	public void onDisconnect(String describe) {
		throw new IllegalStateException(getStateName() + " 此状态不应该进行断开链接动作");
	}

	@Override
	public void onHeartbeat(long lastUpdateTime, String describe) {
		throw new IllegalStateException(getStateName() + " 此状态不应该进行心跳动作");
	}

	@Override
	public void onTimeout(String describe) {
		throw new IllegalStateException(getStateName() + " 此状态不应该进行进入超时动作");
	}
}
