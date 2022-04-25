//package net.pingfang.plugin.netty.client;
//
//import java.util.Date;
//
//import io.netty.channel.ChannelHandlerContext;
//import io.netty.channel.ChannelInboundHandlerAdapter;
//import io.netty.handler.timeout.IdleState;
//import io.netty.handler.timeout.IdleStateEvent;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class HeartBeatRequestHandler extends ChannelInboundHandlerAdapter {
//
//	@Override
//	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//		log.info("客户端循环心跳监测发送: " + format.format(new Date()));
//		if (evt instanceof IdleStateEvent) {
//			IdleStateEvent event = (IdleStateEvent) evt;
//			if (event.state() == IdleState.WRITER_IDLE) {
//				ctx.writeAndFlush(new byte[] { (byte) 0xFF });
//			}
//		} else {
//			super.userEventTriggered(ctx, evt);// 不是IdleStateEvent事件，所以将它传递给下一个ChannelInboundHandler
//		}
//	}
//}
