package net.pingfang.plc.plugin.netty.client.codec;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

/**
 * @author 王超
 * @description TODO
 * @date 2022-04-14 16:24
 */
public class PLCDecoder extends MessageToMessageDecoder<ByteBuf> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
		msg.skipBytes(1);
		byte[] bytes = new byte[2];
		bytes[0] = msg.readByte();
		bytes[1] = msg.readByte();
		out.add(bytes);
	}
}
