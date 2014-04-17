package com.jerrylin.myhouse.util;

import java.io.UnsupportedEncodingException;

import org.springside.modules.security.utils.Digests;
import org.springside.modules.utils.Encodes;

public class UrlGenerator {
	
	private static final String ENCODE_CHARSET = "ISO_8859_1";
	private static final String digestKeyStr = "3go8&$8*3*3h0k(2)2";
	private static byte[] digestKey;
	static {
		try {
			digestKey = digestKeyStr.getBytes("ISO_8859_1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public static String getImageUrl(Long userId, Long houseId, Long imageId) {
		StringBuilder sb = new StringBuilder();
		sb.append("/");
		sb.append(userId);
		sb.append("/");
		sb.append(houseId);
		sb.append("/");
		
		try {
			byte[] bytes = Digests.sha1(String.valueOf(imageId).getBytes(ENCODE_CHARSET), digestKey);
			String encodeStr = Encodes.encodeUrlSafeBase64(bytes);
			sb.append(encodeStr);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	
	/**
	 * 获取房屋的打包下载地址<br>
	 * eg: /download/v-EngpklDSkwGOGKuNZDDYhKU5M/2_2.zip
	 * 
	 * @param userId
	 * @param houseId
	 * @return
	 */
	public static String getHouseUrl(Long userId, Long houseId) {
		StringBuilder sb = new StringBuilder();
		sb.append(userId);
		sb.append("_");
		sb.append(houseId);
		
		String main = sb.toString();
		try {
			byte[] bytes = Digests.sha1(main.getBytes(ENCODE_CHARSET), digestKey);
			String encodeStr = Encodes.encodeUrlSafeBase64(bytes);
			sb.insert(0, "/download/" + encodeStr + "/");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		sb.append(".zip");
		return sb.toString();
	}

}
