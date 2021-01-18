package com.sea.ftp.server.impl;

import java.util.HashMap;
import java.util.Map;

import com.sea.ftp.command.Command;
import com.sea.ftp.command.CommandFactory;
import com.sea.ftp.command.impl.ABOT;
import com.sea.ftp.command.impl.ACCT;
import com.sea.ftp.command.impl.ALLO;
import com.sea.ftp.command.impl.APPE;
import com.sea.ftp.command.impl.CDUP;
import com.sea.ftp.command.impl.CWD;
import com.sea.ftp.command.impl.DELE;
import com.sea.ftp.command.impl.HELP;
import com.sea.ftp.command.impl.LIST;
import com.sea.ftp.command.impl.MKD;
import com.sea.ftp.command.impl.MODE;
import com.sea.ftp.command.impl.NLST;
import com.sea.ftp.command.impl.NOOP;
import com.sea.ftp.command.impl.OPTS;
import com.sea.ftp.command.impl.PASS;
import com.sea.ftp.command.impl.PASV;
import com.sea.ftp.command.impl.PORT;
import com.sea.ftp.command.impl.PWD;
import com.sea.ftp.command.impl.QUIT;
import com.sea.ftp.command.impl.REIN;
import com.sea.ftp.command.impl.REST;
import com.sea.ftp.command.impl.RETR;
import com.sea.ftp.command.impl.RMD;
import com.sea.ftp.command.impl.RNFR;
import com.sea.ftp.command.impl.RNTO;
import com.sea.ftp.command.impl.SITE;
import com.sea.ftp.command.impl.SMNT;
import com.sea.ftp.command.impl.STAT;
import com.sea.ftp.command.impl.STOR;
import com.sea.ftp.command.impl.STOU;
import com.sea.ftp.command.impl.STRU;
import com.sea.ftp.command.impl.SYST;
import com.sea.ftp.command.impl.TYPE;
import com.sea.ftp.command.impl.USER;
import com.sea.ftp.exception.illegal.IllegalCommandException;
import com.sea.ftp.message.MessageCode;
import com.sea.ftp.message.MessageCode.MessageType;
import com.sea.ftp.message.i18n.LocalizedMessageResource;
import com.sea.ftp.util.StringUtils;

/**
 * 
 * 默认的命令工厂
 * 
 * @author sea
 */
public class DefaultCommandFactory implements CommandFactory {
	private static Map<String, Command> registeredCmds = new HashMap<String, Command>();
	private Map<String, Command> customCmds = new HashMap<String, Command>();
	private MessageCode errorCode = MessageCode.newMessageCode(MessageType.Error);
	private static LocalizedMessageResource lmr = LocalizedMessageResource.newInstance();

	// 初始化FTP命令
	static {
		registeredCmds.put("ABOT", new ABOT());
		registeredCmds.put("ACCT", new ACCT());
		registeredCmds.put("ALLO", new ALLO());
		registeredCmds.put("APPE", new APPE());
		registeredCmds.put("CDUP", new CDUP());
		registeredCmds.put("CWD", new CWD());
		registeredCmds.put("DELE", new DELE());
		registeredCmds.put("HELP", new HELP());
		registeredCmds.put("LIST", new LIST());
		registeredCmds.put("MKD", new MKD());
		registeredCmds.put("MODE", new MODE());
		registeredCmds.put("NLST", new NLST());
		registeredCmds.put("NOOP", new NOOP());
		registeredCmds.put("PASS", new PASS());
		registeredCmds.put("PASV", new PASV());
		registeredCmds.put("PORT", new PORT());
		registeredCmds.put("PWD", new PWD());
		registeredCmds.put("QUIT", new QUIT());
		registeredCmds.put("EXIT", new QUIT());
		registeredCmds.put("REIN", new REIN());
		registeredCmds.put("REST", new REST());
		registeredCmds.put("RETR", new RETR());
		registeredCmds.put("RMD", new RMD());
		registeredCmds.put("RNFR", new RNFR());
		registeredCmds.put("RNTO", new RNTO());
		registeredCmds.put("SITE", new SITE());
		registeredCmds.put("SMNT", new SMNT());
		registeredCmds.put("STAT", new STAT());
		registeredCmds.put("STOR", new STOR());
		registeredCmds.put("STOU", new STOU());
		registeredCmds.put("STRU", new STRU());
		registeredCmds.put("SYST", new SYST());
		registeredCmds.put("TYPE", new TYPE());
		registeredCmds.put("USER", new USER());
		registeredCmds.put("OPTS", new OPTS());
	}

	public DefaultCommandFactory() {

	}

	public DefaultCommandFactory(Map<String, Command> customCmds) {
		this.customCmds = customCmds;
	}

	/**
	 * 获取命令对象
	 */
	@Override
	public Command getCommand(String cmdName) {
		if (StringUtils.isBlank(cmdName)) {
			errorCode.setMsgKey("illegalcommand");
			throw new IllegalCommandException(lmr.getMessage(errorCode, cmdName));
		}
		String upperCaseCmdName = cmdName.toUpperCase();
		// 从用户扩展命令列表中查找
		Command command = customCmds.get(upperCaseCmdName);
		// 从系统默认注册命令列表中查找
		if (command == null) {
			command = registeredCmds.get(upperCaseCmdName);
		}
		// 如果命令为空，则表明命令为非法命令
		if (command == null) {
			errorCode.setMsgKey("illegalcommand");
			throw new IllegalCommandException(lmr.getMessage(errorCode, cmdName));
		}
		return command;
	}

	@Override
	public void registerCommand(String cmdName, Command cmd) {
		registerCommand(cmdName, cmd, false);
	}

	@Override
	public void registerCommand(String cmdName, Command cmd, boolean isDefault) {
		if (isDefault) {
			registeredCmds.put(cmdName, cmd);
		} else {
			customCmds.put(cmdName, cmd);
		}
	}
}
