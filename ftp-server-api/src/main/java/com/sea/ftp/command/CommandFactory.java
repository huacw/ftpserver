package com.sea.ftp.command;

/**
 * 
 * 命令工厂
 *
 * @author sea 
 */
public interface CommandFactory {
	/**
	 * 根据命令名称获取命令对象
	 * 
	 * @param cmdName
	 *            命令名称
	 * @return 命令对象
	 */
	public Command getCommand(String cmdName);

	/**
	 * 注册命令
	 * 
	 * @param cmdName
	 *            命令名称
	 * @param cmd
	 *            命令对象
	 */
	public void registerCommand(String cmdName, Command cmd);

	/**
	 * 注册命令
	 * 
	 * @param cmdName
	 *            命令名称
	 * @param cmd
	 *            命令对象
	 * @param isDefault
	 *            是否为默认命令，true-默认命令，false-非默认命令
	 */
	public void registerCommand(String cmdName, Command cmd, boolean isDefault);
}
