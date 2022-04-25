package net.pingfang.plc.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.pingfang.plc.aisle.Integration;
import net.pingfang.plc.domain.MessageReq;
import net.pingfang.plc.domain.PlcInfo;
import net.pingfang.plc.plugin.netty.client.NettyClient;
import net.pingfang.plc.plugin.netty.client.ResponseHandlers.AbstractResultEventHandler;
import net.pingfang.plc.plugin.netty.client.ResponseHandlers.impl.MsgPrintHandler;
import net.pingfang.plc.plugin.netty.state.DeviceLog;

/**
 * @author 王超
 * @description TODO
 * @date 2022-04-15 14:53
 *
 */
@AllArgsConstructor
@Service
@Slf4j
public class PlcService {
	static final Map<Long, Integration> PLCS = Maps.newConcurrentMap();

	public void create(PlcInfo plcInfo) {
		Integration integration = PLCS.get(plcInfo.getAisleId());
		if (integration != null) {
			throw new RuntimeException("当前通道已存在plc设备，请先注销当前设备再添加");
		}
		List<AbstractResultEventHandler> eventHandlerList = Lists.newLinkedList();
		eventHandlerList.add(new MsgPrintHandler());
		NettyClient client = new NettyClient(plcInfo.getIp(), plcInfo.getPort(), plcInfo.getAisleId(),
				eventHandlerList);
		Thread thread = new Thread(client);
		thread.setName("THREAD::AISLE_ID:" + plcInfo.getAisleId());
		thread.start();
		PLCS.put(plcInfo.getAisleId(), client);
	}

	public void send(MessageReq messageReq) {
		Integration integration = getInter(messageReq.getAisleId());
		integration.sendMsg(messageReq.getMsg());
	}

	public void logout(Long aisleId) {
		Integration integration = getInter(aisleId);
		try {
			integration.close();
		} catch (Exception e) {
			log.info("销毁异常:", e);
		}
		integration = null;
		PLCS.remove(aisleId);
	}

	public List<DeviceLog> getLogs(long aisleId) {
		Integration integration = getInter(aisleId);
		return integration.getLogs();
	}

	/**
	 * 获取通道设备
	 *
	 * @param aisleId
	 * @return
	 */
	public Integration getInter(Long aisleId) {
		Integration integration = PLCS.get(aisleId);
		if (integration == null) {
			throw new RuntimeException(String.format("%s通道不存在PLC设备", aisleId));
		}
		return integration;
	}
}
