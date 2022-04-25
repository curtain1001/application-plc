package net.pingfang.plc.plugin.netty.client.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author 王超
 * @description TODO
 * @date 2022-04-14 16:24
 */
public class PLCEncoder extends MessageToByteEncoder<byte[]> {
	@Override
	protected void encode(ChannelHandlerContext ctx, byte[] msg, ByteBuf out) {
		ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
		byteBuf.writeByte(0xFE);
		byteBuf.writeBytes(msg);
		byteBuf.writeByte(0xFF);
		out.writeBytes(byteBuf);
		ctx.flush();
	}
}
