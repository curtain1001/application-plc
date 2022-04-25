package net.pingfang.plc.plugin.netty.server;

public interface IServer {
	/**
	 * 启动服务器
	 */
	void start();

	/**
	 * 停止服务器
	 */
	void stop();
}
