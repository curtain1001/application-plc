package net.pingfang.plc.constants;

import java.util.Map;

import org.apache.catalina.util.Introspection;

import com.google.common.collect.Maps;

/**
 * @author 王超
 * @description TODO
 * @date 2022-04-14 16:30
 */
public class PlcConstant {

	Map<Long, Introspection> introspections = Maps.newHashMap();

	public String PORT_0 = "0.0";
	public String PORT_1 = "0.1";
	public String PORT_2 = "0.2";
	public String PORT_3 = "0.3";
	public String PORT_4 = "0.4";
	public String PORT_5 = "0.5";
	public String PORT_6 = "0.6";
	public String PORT_7 = "0.7";

	public Map<String, Byte> PORT_MAPPING = Maps.newHashMap();
	{
		PORT_MAPPING.put(PORT_0, (byte) 0x01);
		PORT_MAPPING.put(PORT_1, (byte) 0x02);
		PORT_MAPPING.put(PORT_2, (byte) 0x04);
		PORT_MAPPING.put(PORT_3, (byte) 0x08);
		PORT_MAPPING.put(PORT_4, (byte) 0x10);
		PORT_MAPPING.put(PORT_5, (byte) 0x20);
		PORT_MAPPING.put(PORT_6, (byte) 0x40);
		PORT_MAPPING.put(PORT_7, (byte) 0x80);
	}

}
