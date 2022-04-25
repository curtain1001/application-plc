package net.pingfang.plc.plugin.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

/**
 * @author Bill Lau
 * @date 2017-12-04
 */
public class ChannelAttribute<T> {

	/**
	 * 属性键名称
	 */
	AttributeKey<T> attributeKey;

	public ChannelAttribute(String key) {
		this.attributeKey = AttributeKey.valueOf(key);
	}

	/**
	 * 设置属性
	 *
	 * @param ctx   通道上下文
	 * @param value 值
	 */
	public void setAttribute(ChannelHandlerContext ctx, T value) {
		Attribute<T> attribute = getAttribute(ctx);
		attribute.set(value);
	}

	/**
	 * 获取属性值
	 *
	 * @param ctx 通道上下文
	 * @return 属性值 有可能为 null
	 */
	public T getAttributeValue(ChannelHandlerContext ctx) {
		Attribute<T> attribute = getAttribute(ctx);
		T value = attribute.get();
		return value;
	}

	/**
	 * 永远不会返回 null
	 *
	 * @param ctx channel 上下
	 * @return ctx 关联的 channle 的 attribute
	 */
	private Attribute<T> getAttribute(ChannelHandlerContext ctx) {
		return ctx.channel().attr(attributeKey);
	}

}
