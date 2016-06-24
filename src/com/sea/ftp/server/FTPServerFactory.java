package com.sea.ftp.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sea.ftp.exception.FTPServerException;

/**
 * 
 * FTP服务器工厂
 *
 * @author sea 
 */
public class FTPServerFactory {
	/**
	 * 默认的FTP服务器名称
	 */
	public static final String DEFAULT_SERVER_NAME = "default_server";
	protected static final Map<String, FTPServer> registeredServers = new HashMap<String, FTPServer>();

	/**
	 * 获取指定名称的FTP服务器
	 * 
	 * @param serverName
	 *            服务器名称
	 * @return
	 */
	public synchronized static FTPServer getRegisterFTPServer(String serverName) {
		return registeredServers.get(serverName);
	}

	/**
	 * 注册FTP服务器
	 * 
	 * @param name
	 *            服务器名称
	 * @param server
	 *            服务器对象
	 * @throws FTPServerException
	 *             该名称的服务器已经注册过时抛出
	 */
	public synchronized static void registerFTPServer(String name,
			FTPServer server) throws FTPServerException {
		if (isRegisteredServer(name)) {
			throw new FTPServerException("");
		}
		registeredServers.put(name, server);
	}

	/**
	 * 取消指定名称服务器的注册
	 * 
	 * @param name
	 *            服务器名称
	 */
	public synchronized static void unregisterServer(String name) {
		registeredServers.remove(name);
	}

	/**
	 * 取消指定类型服务器的注册
	 * 
	 * @param clazz
	 *            服务器类型
	 */
	public synchronized static void unregisterServer(Class<?> clazz) {
		Set<String> keySet = registeredServers.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			FTPServer value = registeredServers.get(key);
			if (value.getClass() == clazz) {
				registeredServers.remove(key);
			}
		}
	}

	/**
	 * 取消所有服务器的注册
	 */
	public synchronized static void unregisterAllServers() {
		registeredServers.clear();
	}

	/**
	 * 判断指定名称的服务器是否已经注册过
	 * 
	 * @param name
	 *            服务器名称
	 * @return true-注册过，false-未注册
	 */
	public synchronized static boolean isRegisteredServer(String name) {
		return getRegisterFTPServer(name) == null ? false : true;
	}

	/**
	 * 获取已注册的所有FTP服务器
	 * 
	 * @return 已注册的所有FTP服务器
	 */
	public synchronized static List<FTPServer> getRegisteredServers() {
		List<FTPServer> servers = new ArrayList<FTPServer>();
		if (!registeredServers.isEmpty()) {
			servers.addAll(registeredServers.values());
		}
		return servers;
	}
}
