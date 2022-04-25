///**
// * 创建日期: 2017/10/17
// * 创建作者：helloworldyu
// * 文件名称：TcpServerHandler.java
// * 功能:
// */
//package net.pingfang.plc.plugin.netty.server;
//
//import java.util.concurrent.ThreadPoolExecutor;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.SimpleChannelInboundHandler;
//import net.pingfang.AppContext;
//
///**
// * 功能:tcp数据处理层
// *
// * 创建作者：helloworldyu 文件名称：TcpServerHandler.java 创建日期: 2017/10/17
// */
//public class TcpServerHandler extends SimpleChannelInboundHandler<String> {
//	private static final Logger logger = LoggerFactory.getLogger(TcpServerHandler.class);
//
//	// 有数据可读的时候触发
//	@Override
//	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
//		// 处理消息
//		// 合法
//		ThreadPoolExecutor threadPool = (ThreadPoolExecutor) AppContext.getBean("threadPool");
//		TestTask testTask = new TestTask(ctx, msg);
//
//		threadPool.execute(testTask);
//	}
//
//	@Override
//	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//		logger.debug("有超时事件发生:{}", ctx.channel().remoteAddress().toString());
//	}
//
//	// 异常的时候触发
//	@Override
//	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//		logger.error("{}:异常,栈信息:", ctx.channel().remoteAddress().toString(), cause);
//	}
//}
