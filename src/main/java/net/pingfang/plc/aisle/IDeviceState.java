package net.pingfang.plc.aisle;

/**
 * @author 王超
 * @description 设备各种状态下的行为总和
 * @date 2022-04-21 10:29
 */
public interface IDeviceState {

	/**
	 * 设备初始化
	 *
	 * @param connectedTime 建立链接的时间
	 * @param describe      描述在什么时候进行的此动作
	 */
	void onInit(long connectedTime, String describe);

	/**
	 * 设备新建立链接
	 *
	 * @param connectedTime 建立链接的时间
	 * @param describe      描述在什么时候进行的此动作
	 */
	void onConnect(long connectedTime, String describe);

	/**
	 * 断开链接
	 *
	 * @param describe 描述在什么时候进行的此动作
	 */
	void onDisconnect(String describe);

	/**
	 * 只要有数据上报，都属于心跳
	 *
	 * @param lastUpdateTime 最新更新时间
	 * @param describe       描述在什么时候进行的此动作
	 */
	void onHeartbeat(long lastUpdateTime, String describe);

	/**
	 * 进入超时
	 *
	 * @param describe
	 */
	void onTimeout(String describe);

	/**
	 * 返回当前状态的名字
	 */
	String getStateName();
}
