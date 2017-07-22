package com.sea.ftp.server.impl.config.xml;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;

import com.sea.ftp.command.CommandFactory;
import com.sea.ftp.constants.Constants;
import com.sea.ftp.context.application.FTPServerContext;
import com.sea.ftp.exception.FTPServerException;
import com.sea.ftp.exception.FTPServerRuntimeException;
import com.sea.ftp.exception.illegal.IllegalConfigException;
import com.sea.ftp.message.i18n.LocalizedMessageResource;
import com.sea.ftp.message.i18n.MultilanguageMessageResource;
import com.sea.ftp.server.FTPServerFactory;
import com.sea.ftp.server.context.application.DefaultFTPServerContext;
import com.sea.ftp.server.impl.DefaultCommandFactory;
import com.sea.ftp.server.impl.DefaultFTPServer;
import com.sea.ftp.server.impl.DefaultFTPServerInitializer;
import com.sea.ftp.server.impl.config.xml.bean.Command;
import com.sea.ftp.server.impl.config.xml.bean.Commands;
import com.sea.ftp.server.impl.config.xml.bean.Listener;
import com.sea.ftp.server.impl.config.xml.bean.NativeFilesystem;
import com.sea.ftp.server.impl.config.xml.bean.SSLConfiguration;
import com.sea.ftp.server.impl.config.xml.bean.Server;
import com.sea.ftp.server.impl.config.xml.bean.ServerConfiguration;
import com.sea.ftp.user.UserManager;
import com.sea.ftp.user.factory.UserManagerFactory;
import com.sea.ftp.util.JAXBUtils;
import com.sea.ftp.util.StringUtils;

/**
 * 
 * XML配置文件的FTP服务器
 * 
 * @author sea
 */
public class XmlConfigFTPServer {
	public XmlConfigFTPServer() throws FTPServerException {
		this("/config/server.xml");
	}

	public XmlConfigFTPServer(String config) throws FTPServerException {
		loadXMLConfig(config);
	}

	/**
	 * 加载xml配置文件
	 * 
	 * @param config
	 *            xml配置文件
	 * @throws FTPServerException
	 */
	private void loadXMLConfig(String config) throws FTPServerException {
		File configFile = new File(config);
		// 判断配置文件地址是否为绝对路径，不是绝对路径时以当前目录为根目录
		if (!configFile.isAbsolute()) {
			configFile = new File(System.getProperty("user.dir"), config);
		}
		ServerConfiguration serverConfiguration = (ServerConfiguration) JAXBUtils.xmlToBean(configFile,
				ServerConfiguration.class);
		List<Server> servers = serverConfiguration.getServers();
		// 创建FTP服务器
		for (Server server : servers) {
			createFTPServer(server);
		}
	}

	/**
	 * 创建FTP服务器
	 * 
	 * @param server
	 *            FTP服务器配置
	 * @throws FTPServerException
	 */
	private void createFTPServer(Server server) throws FTPServerException {
		DefaultFTPServer ftpServer = new DefaultFTPServer();
		// 初始化监听器
		initListener(server, ftpServer);
		// 初始化管理器
		initManager(server, ftpServer);
		// 设置消息管理器
		initMessageManager(server, ftpServer);
		// 注册FTP服务器
		FTPServerFactory.registerFTPServer(server.getId(), ftpServer);
	}

	/**
	 * 初始化文件系统视图
	 * 
	 * @param server
	 * @param ftpServer
	 * @param serverContext
	 */
	private void initFileSystemView(Server server, DefaultFTPServer ftpServer, FTPServerContext serverContext) {
		NativeFilesystem fileSytemView = server.getNf();
		if (fileSytemView == null) {
			return;
		}
		serverContext.setAtrribute(Constants.KEY_FILE_NAME_IGNORE_CASE, fileSytemView.isCaseInsensitive());
	}

	/**
	 * 初始化消息管理器
	 * 
	 * @param server
	 *            FTP服务器配置
	 * @param ftpServer
	 *            FTP服务器
	 */
	private void initMessageManager(Server server, DefaultFTPServer ftpServer) {
		if (server.getMessage() == null) {
			ftpServer.getFtpServerInitializer().getServerHandler()
					.setMessageResource(LocalizedMessageResource.newInstance());
			return;
		}
		String lang = server.getMessage().getLanguages();
		if (StringUtils.isBlank(lang)) {
			ftpServer.getFtpServerInitializer().getServerHandler()
					.setMessageResource(LocalizedMessageResource.newInstance());
			return;
		}
		MultilanguageMessageResource mr = new MultilanguageMessageResource();
		mr.setDefaultLanguage(lang);
		ftpServer.getFtpServerInitializer().getServerHandler().setMessageResource(mr);
	}

	/**
	 * 初始化管理器
	 * 
	 * @param server
	 *            FTP服务器配置
	 * @param ftpServer
	 *            FTP服务器
	 */
	private void initManager(Server server, DefaultFTPServer ftpServer) {
		DefaultFTPServerInitializer ftpServerInitializer = new DefaultFTPServerInitializer(ftpServer.getCharset());
		DefaultFTPServerContext serverContext = new DefaultFTPServerContext(initCommandFactory(server.getCommands()),
				initUserManager(server.getUm(), server.getNf() == null ? false : server.getNf().isCreateWorkdir()));
		ftpServerInitializer.getServerHandler().setServerContext(serverContext);
		ftpServer.setFtpServerInitializer(ftpServerInitializer);
		initFileSystemView(server, ftpServer, serverContext);
	}

	/**
	 * 初始化命令工厂
	 * 
	 * @param commands
	 */
	private CommandFactory initCommandFactory(Commands commands) {
		CommandFactory commandFactory = new DefaultCommandFactory();
		if (commands == null) {
			return commandFactory;
		}
		List<Command> cmds = commands.getCommands();
		if (cmds == null || cmds.isEmpty()) {
			return commandFactory;
		}
		Boolean useDefault = commands.isUseDefault();
		useDefault = useDefault == null ? false : useDefault;
		for (Command command : cmds) {
			Object cmd = null;
			try {
				cmd = Class.forName(command.getClazz()).newInstance();
			} catch (Exception e) {
				throw new FTPServerRuntimeException(e);
			}
			if (!(cmd instanceof com.sea.ftp.command.Command)) {
				throw new IllegalConfigException("illegalcommand.ex", null, command.getCmdName());
			}
			commandFactory.registerCommand(command.getCmdName(), (com.sea.ftp.command.Command) cmd,
					command.isUseDefault() != null ? command.isUseDefault() : useDefault);
		}
		return commandFactory;
	}

	/**
	 * 初始化用户管理
	 * 
	 * @param um
	 * @param canCreateHome
	 */
	private UserManager initUserManager(com.sea.ftp.server.impl.config.xml.bean.UserManager um, boolean canCreateHome) {
		if (um == null) {
			throw new IllegalConfigException("usermanager.config.error");
		}
		// 创建用户管理类
		return new UserManagerFactory().createUserManager(um, canCreateHome);
	}

	/**
	 * 初始化监听器
	 * 
	 * @param server
	 *            FTP服务器配置
	 * @param ftpServer
	 *            FTP服务器
	 */
	private void initListener(Server server, DefaultFTPServer ftpServer) {
		Listener listener = server.getListener();
		ftpServer.setHost(listener.getHost());
		ftpServer.setPort(listener.getPort());
		ftpServer.setShutdownPort(listener.getShutdownPort());
		// 设置默认编码
		ftpServer.setCharset(StringUtils.isBlank(listener.getCharset()) ? Charset.defaultCharset()
				: Charset.forName(listener.getCharset()));
		SSLConfiguration ssl = listener.getSsl();
		if (ssl != null) {
			ftpServer.setSSL(ssl.isEnable());
		}
	}
}
