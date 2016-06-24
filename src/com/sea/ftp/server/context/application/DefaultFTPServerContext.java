package com.sea.ftp.server.context.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.sea.ftp.command.CommandFactory;
import com.sea.ftp.context.AbstractContext;
import com.sea.ftp.context.application.FTPServerContext;
import com.sea.ftp.context.session.FTPServerSession;
import com.sea.ftp.server.impl.DefaultCommandFactory;
import com.sea.ftp.user.UserManager;
import com.sea.ftp.user.impl.DefaultUserManager;
import com.sea.ftp.util.StringUtils;

/**
 * 默认的FTP服务器上下文
 * 
 * @author sea
 * 
 */
public class DefaultFTPServerContext extends AbstractContext implements
		FTPServerContext {
	/** 服务器session键值前缀 */
	public static final String FTP_SERVER_SESSION = "ftp.server.session";
	/** 服务器用户管理键值 */
	public static final String FTP_SERVER_USER_MANAGER = "ftp.server.usermanager";
	/** 服务器命令工厂键值 */
	public static final String FTP_SERVER_COMMAND_FACTORY = "ftp.server.commandfactory";
	/** 服务器命令工厂键值 */
	public static final String FTP_SERVER_MESSAGE_FACTORY = "ftp.server.messagefactory";

	/**
	 * 操作类型
	 * 
	 * @author sea
	 * 
	 */
	private enum OperationType {
		get, delete;
	}

	public DefaultFTPServerContext() {
		addCommandFactory(new DefaultCommandFactory());
		addUserManager(new DefaultUserManager());
	}

	public DefaultFTPServerContext(CommandFactory cm, UserManager um) {
		addCommandFactory(cm);
		addUserManager(um);
	}

	@Override
	public void addFTPServerSession(FTPServerSession session) {
		addFTPServerSession("", session);
	}

	@Override
	public void addFTPServerSession(String name, FTPServerSession session) {
		attributes.put(createSessionName(name), session);
	}

	@Override
	public FTPServerSession getFTPServerSession(String name) {
		Object val = attributes.get(createSessionName(name));
		if (val instanceof FTPServerSession) {
			return (FTPServerSession) val;
		}
		return null;
	}

	/**
	 * 创建Session名称
	 * 
	 * @param name
	 *            自定义名称
	 * @return 完整的Session名称
	 */
	private String createSessionName(String name) {
		return FTP_SERVER_SESSION
				+ (StringUtils.isBlank(name) ? "" : ("-" + name));
	}

	@Override
	public FTPServerSession removeFTPServerSession(String name) {
		Object val = attributes.remove(createSessionName(name));
		if (val instanceof FTPServerSession) {
			return (FTPServerSession) val;
		}
		attributes.put(name, val);
		return null;
	}

	@Override
	public List<FTPServerSession> getFTPServerSessions() {
		String sessionPrefix = createSessionName("");
		Set<String> keySet = attributes.keySet();
		List<FTPServerSession> sessions = new ArrayList<FTPServerSession>();
		for (String key : keySet) {
			if (key.startsWith(sessionPrefix)) {
				sessions.add((FTPServerSession) attributes.get(key));
			}
		}
		return dealAllSession(OperationType.get);
	}

	@Override
	public List<FTPServerSession> removeAllFTPServerSessions() {
		return dealAllSession(OperationType.delete);
	}

	/**
	 * 出来所有FTP服务会话的方法
	 * 
	 * @param type
	 *            处理类型
	 * @return 所有的FTP服务器会话
	 */
	private List<FTPServerSession> dealAllSession(OperationType type) {
		String sessionPrefix = createSessionName("");
		Set<String> keySet = attributes.keySet();
		List<FTPServerSession> sessions = new ArrayList<FTPServerSession>();
		for (String key : keySet) {
			if (key.startsWith(sessionPrefix)) {
				FTPServerSession session = null;
				if (type == OperationType.delete) {
					session = (FTPServerSession) attributes.remove(key);
				} else if (type == OperationType.get) {
					session = (FTPServerSession) attributes.get(key);
				}
				if (session == null) {
					continue;
				}
				sessions.add(session);
			}
		}
		return sessions;
	}

	@Override
	public void addUserManager(UserManager um) {
		attributes.put(FTP_SERVER_USER_MANAGER, um);
	}

	@Override
	public void removeUserManager() {
		attributes.remove(FTP_SERVER_USER_MANAGER);
	}

	@Override
	public void addCommandFactory(CommandFactory cm) {
		attributes.put(FTP_SERVER_COMMAND_FACTORY, cm);
	}

	@Override
	public void removeCommandFactory() {
		attributes.remove(FTP_SERVER_COMMAND_FACTORY);
	}

	@Override
	public UserManager getUserManager() {
		Object um = attributes.get(FTP_SERVER_USER_MANAGER);
		if (um instanceof UserManager) {
			return (UserManager) um;
		}
		return null;
	}

	@Override
	public CommandFactory getCommandFactory() {
		Object cm = attributes.get(FTP_SERVER_COMMAND_FACTORY);
		if (cm instanceof CommandFactory) {
			return (CommandFactory) cm;
		}
		return null;
	}

}
