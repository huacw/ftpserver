package com.sea.ftp.command;

import org.apache.log4j.Logger;

import com.sea.ftp.command.code.FtpReply;
import com.sea.ftp.command.impl.LocalizedFTPReply;
import com.sea.ftp.constants.Constants;
import com.sea.ftp.exception.FTPServerRuntimeException;
import com.sea.ftp.exception.illegal.IllegalAuthorityException;
import com.sea.ftp.exception.io.FTPIOException;
import com.sea.ftp.server.context.request.DefaultFTPServerRequest;
import com.sea.ftp.user.User;

import io.netty.channel.ChannelHandlerContext;

/**
 * 
 * 抽象命令
 * 
 * @author sea
 */
public abstract class AbstractCommand implements Command {
    // 日志记录对象
    protected Logger logger = Logger.getLogger(getClass());
    // 国际化响应消息
    protected static FtpReply reply = new LocalizedFTPReply();

    /**
     * 返回响应信息
     * 
     * @param context 命令上下文
     * @param code 响应码
     * @param args
     */
    protected void write(CommandContext context, int code, String... args) {
        ChannelHandlerContext ctx =
                (ChannelHandlerContext) context.getResponse().getAtrribute(Constants.KEY_SESSION_STREAM);
        if (logger.isInfoEnabled()) {
            logger.info(reply.getMessage(code, args));
        }
        ctx.writeAndFlush(reply.getMessage(code, args) + "\n");
    }

    /**
     * FTP命令执行处理
     */
    @Override
    public void execute(CommandContext context) throws FTPIOException {
        try {
            before(context);
        } catch (Exception e) {
            logger.error(e);
            return;
        }
        try {
            done(context);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        } finally {
            after(context);
        }
    }

    /**
     * 命令执行前处理方法
     * 
     * @param context
     */
    protected void before(CommandContext context) {
        checkAuth(context);
    }

    /**
     * 检查用户权限
     * 
     * @param context
     */
    private void checkAuth(CommandContext context) {
        String cmdName = this.getClass().getSimpleName();
        if ("USER".equals(cmdName) || "PASS".equals(cmdName)) {
            return;
        }
        User loginUser = (User) context.getFtpServerSession().getAtrribute(Constants.KEY_LOGIN_USER);
        if (loginUser == null) {
            write(context, FtpReply.REPLY_332_NEED_ACCOUNT_FOR_LOGIN);
            throw new FTPServerRuntimeException(reply.getMessage(FtpReply.REPLY_332_NEED_ACCOUNT_FOR_LOGIN));
        }
        if (!hasAuth(context)) {
            throw new IllegalAuthorityException();
        }
    }

    /**
     * 用户是否拥有权限(子类根据需要重写)
     * 
     * @param context
     * @return
     */
    protected boolean hasAuth(CommandContext context) {
        return true;
    }

    /**
     * 具体的命令执行
     * 
     * @param context
     */
    protected abstract void done(CommandContext context) throws FTPIOException;

    /**
     * 命令执行后的处理方法
     * 
     * @param context
     */
    protected void after(CommandContext context) {

    }

    /**
     * 获取命令参数
     * 
     * @param context
     * @return
     */
    protected String[] getCmdArgs(CommandContext context) {
        String[] args = (String[]) context.getRequest().getAtrribute(DefaultFTPServerRequest.FTP_REQUEST_CMD_ARGS);
        return args;
    }

    protected User getLoginUser(CommandContext context) {
        Object user = context.getFtpServerSession().getAtrribute(Constants.KEY_LOGIN_USER);
        if (user == null) {
            return null;
        }
        if (user instanceof User) {
            return (User) user;
        }
        return null;
    }
}
