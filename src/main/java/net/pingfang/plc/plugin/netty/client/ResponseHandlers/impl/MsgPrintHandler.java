package net.pingfang.plc.plugin.netty.client.ResponseHandlers.impl;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;
import net.pingfang.plc.plugin.netty.client.ResponseHandlers.AbstractResultEventHandler;
import net.pingfang.plc.plugin.netty.client.domain.EventContext;

/**
 * @author 王超
 * @description 消息打印处理器（测试）
 * @date 2022-04-20 21:50
 */
@Slf4j
public class MsgPrintHandler implements AbstractResultEventHandler {
	@Override
	public void handler(EventContext context) {
		log.info(String.format("%s通道PLC收到消息：%s", context.getAisleId(), Arrays.toString(context.getMsg())));
	}

	@Override
	public int getOrder() {
		return AbstractResultEventHandler.super.getOrder();
	}
}
