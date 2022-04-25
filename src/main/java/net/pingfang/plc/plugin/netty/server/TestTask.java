/**
 * 创建日期: 2017/10/19
 * 创建作者：helloworldyu
 * 文件名称：TestTask.java
 * 功能:
 */
package net.pingfang.plc.plugin.netty.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

/**
 * 功能:
 *
 * 创建作者：helloworldyu 文件名称：TestTask.java 创建日期: 2017/10/19
 */
public class TestTask extends AbstractTask {

	public TestTask(ChannelHandlerContext ctx, Object msg) {
		super(ctx, msg);
	}

	@Override
	public void run() {
		ChannelFuture future = this.ctx.writeAndFlush(this.msg);
		future.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {

			}
		});
	}
}
