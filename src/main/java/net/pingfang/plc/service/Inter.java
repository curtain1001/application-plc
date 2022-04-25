package net.pingfang.plc.service;

import com.google.common.collect.Maps;
import java.util.Map;
import net.pingfang.plc.aisle.Integration;

/**
 * @author 王超
 * @description TODO
 * @date 2022-04-14 11:43
 */
public class Inter {
     static Map<String, Integration> MAP = Maps.newConcurrentMap();
}
