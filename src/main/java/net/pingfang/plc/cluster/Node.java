package net.pingfang.plc.cluster;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 王超
 * @description TODO
 * @date 2022-04-21 9:59
 */
public class Node {
    private String domain;

    private String ip;
    private int count = 0;

    private Map<String, Object> data = new HashMap<>();

    public Node(String domain) {
        this.domain= domain;
        //this.ip=ip;
    }
    public <T> void put(String key,String value) {
        data.put(key,value);
        count++;
    }

    public void remove(String key){
        data.remove(key);
        count--;
    }

    public Object get(String key) {
        return data.get(key);
    }

    public int getCount() {
        return count;
    }

    public String getName() {
        return domain;
    }
}
