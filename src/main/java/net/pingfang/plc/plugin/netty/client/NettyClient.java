package net.pingfang.plc.plugin.netty.client;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import net.pingfang.plc.aisle.Integration;
import net.pingfang.plc.aisle.Method;
import net.pingfang.plc.aisle.State;
import net.pingfang.plc.plugin.netty.client.ResponseHandlers.AbstractResultEventHandler;
import net.pingfang.plc.plugin.netty.client.codec.ByteConvertUtil;
import net.pingfang.plc.plugin.netty.client.codec.PLCDecoder;
import net.pingfang.plc.plugin.netty.client.codec.PLCEncoder;
import net.pingfang.plc.plugin.netty.state.DeviceStateContext;
import net.pingfang.plc.plugin.netty.state.DeviceLog;

/**
 * @author pancm
 * @Title: NettyClient
 * @Description: Netty客户端 心跳测试
 * @Version:1.0.0
 * @date 2017年10月8日
 */
@Slf4j
public class NettyClient implements Integration, Runnable {

	// todo 线程销毁问题

	private final String ip;
	private final int port;
	private final long aisleId;
	private final List<AbstractResultEventHandler> eventHandlerList;
	private final DeviceStateContext deviceStateContext;

	private int count = 0;
	private static final int retryCount = 5;

	public NettyClient(String ip, int port, long aisleId, List<AbstractResultEventHandler> eventHandlerList) {
		this.ip = ip;
		this.port = port;
		this.aisleId = aisleId;
		this.eventHandlerList = eventHandlerList;
		this.deviceStateContext = new DeviceStateContext(aisleId);
	}

	private ChannelFuture channelFuture;
	private EventLoopGroup group;

	@Override
	public void run() {
		connect();
	}

	public void connect() {
		group = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		try {
			bootstrap.group(group) //
					.channel(NioSocketChannel.class) //
					.option(ChannelOption.SO_KEEPALIVE, true) //
					.option(ChannelOption.TCP_NODELAY, true) //
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel sh) {
							ChannelPipeline pipeline = sh.pipeline();
							pipeline.addLast(new LoggingHandler(LogLevel.INFO));
							pipeline.addLast(new FixedLengthFrameDecoder(4));
							pipeline.addLast(new PLCEncoder());
							pipeline.addLast(new PLCDecoder());
							pipeline.addLast(new IdleStateHandler(0, 10, 0, TimeUnit.SECONDS)); // 十秒心跳
							// 超时检测
//							pipeline.addLast("readTimeoutHandler", new ReadTimeoutHandler(60, TimeUnit.SECONDS));
							// 心跳请求
//							pipeline.addLast("heartBeatRequestHandler", new HeartBeatRequestHandler());
							pipeline.addLast("DeviceStateHandler", new DeviceStateHandler(deviceStateContext));
							pipeline.addLast("ResultResponseHandler",
									new ResultResponseHandler(aisleId, eventHandlerList));
						}
					});
			channelFuture = bootstrap //
					.connect(ip, port)//
					.addListener((ChannelFutureListener) future -> {
						if (count < retryCount) {
							if (!future.isSuccess()) {
								future.channel().eventLoop().schedule(() -> {
									deviceStateContext.setState(State.RETRY_CONNECTED);
									log.info("正在重新连接服务端 目标 ip：{}， port：{}", ip, port);
									count++;
									connect();
								}, 5, TimeUnit.SECONDS);
							} else {
								count = 0;
								log.debug("消息发送成功");
							}
						}

					}).sync();
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			log.error("错误", e);
		}
	}

	@Override
	public void sendMsg(Object o) {
		if (channelFuture == null || channelFuture.channel() == null) {
			connect();
		}
		channelFuture.channel().writeAndFlush(ByteConvertUtil.hexToByteArray(String.valueOf(0)));
	}

	@Override
	public void close() throws InterruptedException {
		if (channelFuture != null && channelFuture.channel() != null) {
			channelFuture.channel().close();
		}
		if (group != null) {
			// 异步关闭 EventLoop
			Future<?> future = group.shutdownGracefully();
			// 等待关闭成功
			future.syncUninterruptibly();
			log.info("退出服务器成功");
		}
	}

	@Override
	public Method getMethod() {
		return Method.TCP;
	}

	@Override
	public State getStatus() {
		return deviceStateContext.getState();
	}

	@Override
	public List<DeviceLog> getLogs() {
		return deviceStateContext.getDeviceLogs();
	}
}
