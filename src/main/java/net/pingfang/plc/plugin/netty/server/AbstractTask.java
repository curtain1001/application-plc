/**
 * 创建日期: 2017/10/19
 * 创建作者：helloworldyu
 * 文件名称：AbstractTask.java
 * 功能:
 */
package net.pingfang.plc.plugin.netty.server;

import io.netty.channel.ChannelHandlerContext;

/**
 * 功能:
 *
 * 创建作者：helloworldyu 文件名称：AbstractTask.java 创建日期: 2017/10/19
 */
public abstract class AbstractTask implements Runnable {

	protected ChannelHandlerContext ctx = null;
	protected Object msg = null;

	public AbstractTask(ChannelHandlerContext ctx, Object msg) {
		this.ctx = ctx;
		this.msg = msg;
	}

	@Override
	public abstract void run();
}
