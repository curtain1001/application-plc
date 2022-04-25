package net.pingfang.plc.plugin.netty.client.codec;

/**
 * @author 王超
 * @description TODO
 * @date 2022-03-30 21:53
 */
public class ByteConvertUtil {
	public static String bytesToHexString(byte[] bArr) {
		StringBuffer sb = new StringBuffer(bArr.length);
		String sTmp;

		for (int i = 0; i < bArr.length; i++) {
			sTmp = Integer.toHexString(0xFF & bArr[i]);
			if (sTmp.length() < 2) {
				sb.append(0);
			}
			sb.append(sTmp.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * hex字符串转byte数组
	 *
	 * @param inHex 待转换的Hex字符串
	 * @return 转换后的byte数组结果
	 */
	public static byte[] hexToByteArray(String inHex) {
		int hexlen = inHex.length();
		byte[] result;
		if (hexlen % 2 == 1) {
			// 奇数
			hexlen++;
			result = new byte[(hexlen / 2)];
			inHex = "0" + inHex;
		} else {
			// 偶数
			result = new byte[(hexlen / 2)];
		}
		int j = 0;
		for (int i = 0; i < hexlen; i += 2) {
			result[j] = (byte) Integer.parseInt(inHex.substring(i, i + 2), 16);
			j++;
		}
		return result;
	}

	public static void main(String[] args) {
		byte[] bytes = new byte[1];
		bytes[0] = (byte) 0xFF;
		System.out.println(bytesToHexString(bytes));
	}

}
