package net.pingfang.plc.plugin.netty.client;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import net.pingfang.plc.plugin.netty.client.ResponseHandlers.AbstractResultEventHandler;
import net.pingfang.plc.plugin.netty.client.domain.EventContext;

/**
 * @author 王超
 * @description TODO
 * @date 2022-04-15 11:31
 */
@Slf4j
public class ResultResponseHandler extends ChannelInboundHandlerAdapter {

	private final Long aisle;
	private List<AbstractResultEventHandler> eventHandlerList;
	private boolean flag = false;

	public ResultResponseHandler(Long aisle, List<AbstractResultEventHandler> eventHandlerList) {
		this.aisle = aisle;
		this.eventHandlerList = eventHandlerList;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		flag = true;
		eventHandlerList = eventHandlerList.stream().sorted(Comparator.comparing(AbstractResultEventHandler::getOrder))
				.collect(Collectors.toList());
		EventContext context = EventContext.builder() //
				.aisleId(aisle) //
				.msg((byte[]) msg) //
				.build(); //
		eventHandlerList.forEach(x -> x.handler(context));
		ReferenceCountUtil.release(msg);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
		if (!flag) {
			ctx.read();
		}
	}
}
