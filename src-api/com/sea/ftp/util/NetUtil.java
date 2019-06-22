package com.sea.ftp.util;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * 网络转换工具类
 * 
 * @author sea
 *
 */
public class NetUtil {
	private NetUtil() {
	}

	/**
	 * ip地址转换为ftp返回的ip格式
	 * 
	 * @param ipAddress
	 * @return
	 */
	public static String toFtpIpResult(String ipAddress) {
		if (StringUtils.isBlank(ipAddress)) {
			return "";
		}
		return ipAddress.replaceAll("\\.", ",");
	}

	/**
	 * 端口号转换为ftp返回的端口格式
	 * 
	 * @param port
	 * @return
	 */
	public static String toFtpPortResult(int port) {
		if (port < 0 || port > 65535) {
			throw new RuntimeException("非法的端口号");
		}
		String binaryStr = Integer.toBinaryString(port);
		StringBuilder strResult = new StringBuilder();
		for (int i = 0; i < 16 - binaryStr.length(); i++) {
			strResult.append("0");
		}
		strResult.append(binaryStr);
		return new StringBuilder().append(Integer.parseInt(strResult.subSequence(0, 8).toString(), 2)).append(",")
				.append(Integer.parseInt(strResult.subSequence(8, strResult.length()).toString(), 2)).toString();
	}

	/**
	 * 转换为ftp的网络地址格式
	 * 
	 * @param ipAddress
	 * @param port
	 * @return
	 */
	public String toNetResult(String ipAddress, int port) {
		return new StringBuilder().append(toFtpIpResult(ipAddress)).append(",").append(toFtpPortResult(port))
				.toString();
	}

	public static InetSocketAddress parseFtpNetAddress(String address) {
		if (StringUtils.isBlank(address)) {
			return null;
		}
		String[] segments = address.split(",");
		if (segments.length != 6) {
			throw new RuntimeException("非法的网络格式");
		}
		StringBuilder port = new StringBuilder();
		List<String> ips = new ArrayList<>();
		for (int i = 0; i < segments.length; i++) {
			if (i < 4) {
				ips.add(segments[i]);
			} else {
				String binaryStr = Integer.toBinaryString(Integer.parseInt(segments[i]));
				StringBuilder builder = new StringBuilder();
				for (int j = 0; j < 8 - binaryStr.length(); j++) {
					builder.append("0");
				}
				builder.append(binaryStr);
				port.append(builder);
			}

		}

		return new InetSocketAddress(String.join(".", ips), Integer.parseInt(port.toString(), 2));
	}

}
