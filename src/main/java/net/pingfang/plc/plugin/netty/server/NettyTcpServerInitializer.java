///**
// * 创建日期: 2017/10/18
// * 创建作者：helloworldyu
// * 文件名称：NettyTcpServerInitializer.java
// * 功能:
// */
//package net.pingfang.plc.plugin.netty.server;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import io.netty.channel.ChannelInboundHandlerAdapter;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelOutboundHandlerAdapter;
//import io.netty.channel.ChannelPipeline;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.handler.logging.LogLevel;
//import io.netty.handler.logging.LoggingHandler;
//import io.netty.handler.timeout.IdleStateHandler;
//
///**
// * 功能:
// *
// * 创建作者：helloworldyu 文件名称：NettyTcpServerInitializer.java 创建日期: 2017/10/18
// */
//public class NettyTcpServerInitializer extends ChannelInitializer<SocketChannel> {
//
//	private static final Logger logger = LoggerFactory.getLogger(NettyTcpServerInitializer.class);
//	private static LogLevel logLevel = LogLevel.DEBUG;
//
//	public NettyTcpServerInitializer(LogLevel logLevel) {
//		NettyTcpServerInitializer.logLevel = logLevel;
//	}
//
////	EventExecutorGroup eventExecutor = new DefaultEventExecutorGroup(10);
//
//	@Override
//	protected void initChannel(SocketChannel ch) throws Exception {
//		ChannelPipeline pipeline = ch.pipeline();
//
//		IdleStateHandler idleStateHandler = (IdleStateHandler) AppContext.getBean("idleStateHandler");
//		ChannelInboundHandlerAdapter decoder = (ChannelInboundHandlerAdapter) AppContext.getBean("decoder");
//		ChannelOutboundHandlerAdapter encoder = (ChannelOutboundHandlerAdapter) AppContext.getBean("encoder");
//		ChannelInboundHandlerAdapter serverHandler = (ChannelInboundHandlerAdapter) AppContext.getBean("serverHandler");
//
//		// 设置日志
//		pipeline.addLast("logging", new LoggingHandler(logLevel));
//		// 检测僵尸链接,触发 userEventTriggered 事件
//		pipeline.addLast(idleStateHandler);
//
//		// 字符串编码和解码
//		pipeline.addLast("decoder", decoder);
//		pipeline.addLast("encoder", encoder);
//		// 自己的逻辑Handler
//		pipeline.addLast("handler", serverHandler);
//
//		logger.debug("\n" //
//				+ "=============================================\n" //
//				+ "   新客户端channle初始化.\n" //
//				+ "   decoder:{}  \n" //
//				+ "   encoder:{}  \n" //
//				+ "   handler:{}\n" //
//				+ "=============================================", //
//				decoder.getClass().getTypeName(), encoder.getClass().getTypeName(),
//				serverHandler.getClass().getTypeName());
//	}
//}
