package net.pingfang.plc.plugin.netty.client.ResponseHandlers;

import net.pingfang.plc.plugin.netty.client.domain.EventContext;

/**
 * @author 王超
 * @description TODO
 * @date 2022-04-20 21:36
 */
public interface AbstractResultEventHandler {

	void handler(EventContext context);

	default int getOrder() {
		return 1;
	}

}
