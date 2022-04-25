/**
 * 创建日期: 2017/10/18
 * 创建作者：helloworldyu
 * 文件名称：NettyTcpServer.java
 * 功能:
 */
package net.pingfang.plc.plugin.netty.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLoggerFactory;

/**
 * 功能:
 *
 * 创建作者：helloworldyu 文件名称：NettyTcpServer.java 创建日期: 2017/10/18
 */
public class NettyTcpServer implements IServer {
	private static final Logger logger = LoggerFactory.getLogger(NettyTcpServer.class);

	static {
		InternalLoggerFactory.setDefaultFactory(Slf4JLoggerFactory.INSTANCE);
	}

	private int port = 8080;
	private ChannelInitializer<SocketChannel> channelInitializer;

	// 接收请求的 nio 池
	private EventLoopGroup bossGroup = new NioEventLoopGroup();
	// 接收数据的 nio 池
	private EventLoopGroup workerGroup = new NioEventLoopGroup();

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public ChannelInitializer<SocketChannel> getChannelInitializer() {
		return channelInitializer;
	}

	public void setChannelInitializer(ChannelInitializer<SocketChannel> channelInitializer) {
		this.channelInitializer = channelInitializer;
	}

	@Override
	public void start() {
		ServerBootstrap b = new ServerBootstrap();
		// 指定接收链接的 NioEventLoop,和接收数据的 NioEventLoop
		b.group(bossGroup, workerGroup);
		// 指定server使用的 channel
		b.channel(NioServerSocketChannel.class);
		// 初始化处理请求的编解码，处理响应类等
		b.childHandler(channelInitializer);
		b.option(ChannelOption.SO_BACKLOG, 1024);
		b.option(ChannelOption.SO_REUSEADDR, true);
		try {
			// 服务器绑定端口监听
			b.bind(port).sync();
			logger.info("启动服务器成功,port={}", port);
		} catch (InterruptedException e) {
			// 错误日志
			logger.error("启动服务器报错:", e);
		}
	}

	@Override
	public void stop() {
		// 异步关闭 EventLoop
		Future<?> future = bossGroup.shutdownGracefully();
		Future<?> future1 = workerGroup.shutdownGracefully();

		// 等待关闭成功
		future.syncUninterruptibly();
		future1.syncUninterruptibly();
		logger.info("退出服务器成功");
	}

}
