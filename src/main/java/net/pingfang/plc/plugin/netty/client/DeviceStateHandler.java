package net.pingfang.plc.plugin.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import net.pingfang.plc.aisle.State;
import net.pingfang.plc.plugin.netty.state.DeviceStateContext;

/**
 * @author 王超
 * @description TODO
 * @date 2022-04-21 11:18
 */
@Slf4j
public class DeviceStateHandler extends ChannelInboundHandlerAdapter {

	public DeviceStateContext context;

	public DeviceStateHandler(DeviceStateContext context) {
		this.context = context;
	}

	// 客户端链接上来的时候触发
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// 链接成功
		context.setState(State.CONNECTED);
	}

	// 有数据可读的时候触发
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		context.setState(State.HEART_BEAT);
		ctx.fireChannelRead(msg);
	}

	/**
	 * 空闲一段时间，就进行检查 (当前时间-上次上行数据的时间) 如果大于设定的超时时间 设备状态就就行一次 onTimeout
	 *
	 * @param ctx
	 * @param evt
	 * @throws Exception
	 */
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.WRITER_IDLE) {
				ctx.writeAndFlush(new byte[] { (byte) 0xFF });
			}
		} else {
			super.userEventTriggered(ctx, evt);// 不是IdleStateEvent事件，所以将它传递给下一个ChannelInboundHandler
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		context.setState(State.CLOSE);
		ctx.fireChannelRead(ctx);
	}

//异常的时候触发
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		context.setState(State.CLOSE);
		log.error(cause.getMessage());
		ctx.fireExceptionCaught(cause);
	}
}
