package com.sea.ftp.command.impl;

import com.sea.ftp.command.AbstractCommand;
import com.sea.ftp.command.CommandContext;
import com.sea.ftp.command.code.FtpReply;
import com.sea.ftp.constants.Constants;
import com.sea.ftp.context.session.FTPServerSession;
import com.sea.ftp.exception.FTPServerException;
import com.sea.ftp.exception.io.FTPIOException;
import com.sea.ftp.filesystem.nativefs.impl.NativeFileSystemView;
import com.sea.ftp.server.context.session.DefaultFTPServerSession;
import com.sea.ftp.user.User;
import com.sea.ftp.util.CiphertextUtils;

/**
 * 
 * （Password，密码）命令
 * 
 * 
 * @author sea
 */
public class PASS extends AbstractCommand {

	@Override
	public void done(CommandContext context) throws FTPIOException {
		int errPwdCount = context.getErrPwdCount();
		if (errPwdCount >= 3) {
			write(context, FtpReply.REPLY_331_USER_NAME_OKAY_NEED_PASSWORD);
		}
		User user = getLoginUser(context);
		if (user == null) {
			write(context, FtpReply.REPLY_332_NEED_ACCOUNT_FOR_LOGIN);
			return;
		}
		// 匿名用户不校验密码
		if (user.isAnonymousUser()) {
			write(context, FtpReply.REPLY_230_USER_LOGGED_IN);
			return;
		}
		String[] args = getCmdArgs(context);
		String dealPassword = CiphertextUtils.passAlgorithmsCiphering(args == null || args[0] == null ? "" : args[0],
				user.getEncryptedStrategy());
		if (user.getPassword().equals(dealPassword)) {
			try {
				boolean caseInsensitive = (boolean) context.getServerContext()
						.getAtrribute(Constants.KEY_FILE_NAME_IGNORE_CASE);
				NativeFileSystemView fileSystemView = new NativeFileSystemView(user, caseInsensitive);
				FTPServerSession ftpServerSession = context.getFtpServerSession();
				ftpServerSession.setSystemFileView(fileSystemView);
				ftpServerSession.setAtrribute(DefaultFTPServerSession.KEY_USER_CURRENT_PATH, "/");
				write(context, FtpReply.REPLY_230_USER_LOGGED_IN);
			} catch (FTPServerException e) {
				write(context, FtpReply.REPLY_551_REQUESTED_ACTION_ABORTED_PAGE_TYPE_UNKNOWN);
			}
		} else {
			context.addErrPwdCount();
			logger.error(String.format("第%d次密码错误", errPwdCount));
			write(context, FtpReply.REPLY_331_USER_NAME_OKAY_NEED_PASSWORD);
		}
	}
}
