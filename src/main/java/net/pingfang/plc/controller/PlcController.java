package net.pingfang.plc.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.pingfang.plc.domain.MessageReq;
import net.pingfang.plc.domain.PlcInfo;
import net.pingfang.plc.service.PlcService;
import net.pingfang.plc.plugin.netty.state.DeviceLog;
import net.pingfang.plc.utils.GenericResult;

/**
 * @author 王超
 * @description TODO
 * @date 2022-04-15 14:29
 */

@RestController
@RequestMapping("plc")
public class PlcController {
	final PlcService plcService;

	public PlcController(PlcService plcService) {
		this.plcService = plcService;
	}

	@PostMapping()
	public GenericResult<String, String> create(PlcInfo plcInfo) {
		plcService.create(plcInfo);
		return GenericResult.success(null, "创建成功");
	}

	@PutMapping("msg")
	public GenericResult<String, String> sendMsg(MessageReq req) {
		plcService.send(req);
		return GenericResult.success(null, "发送成功");
	}

	@PutMapping("logout/{aisle}")
	public GenericResult<String, String> logout(@PathVariable Long aisle) {
		plcService.logout(aisle);
		return GenericResult.success(null, "发送成功");
	}

	@GetMapping("log/{aisle}")
	public GenericResult<List<DeviceLog>, String> getLogs(@PathVariable Long aisle) {
		return GenericResult.success(plcService.getLogs(aisle), "获取成功");
	}

}
