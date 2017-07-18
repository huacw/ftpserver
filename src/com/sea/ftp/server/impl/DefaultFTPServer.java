/*******************************************************************************
 * $Header$ $Revision$ $Date$
 *
 * ==============================================================================
 *
 * Copyright (c) 2005-2015 Tansun Technologies, Ltd. All rights reserved.
 * 
 * Created on 2015年9月28日
 *******************************************************************************/

package com.sea.ftp.server.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;

import com.sea.ftp.message.MessageCode;
import com.sea.ftp.message.MessageCode.MessageType;
import com.sea.ftp.message.MessageResource;
import com.sea.ftp.message.i18n.LocalizedMessageResource;
import com.sea.ftp.server.FTPServer;
import com.sea.ftp.util.StringUtils;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 * 默认的FTP服务器
 * 
 * @author sea
 * 
 */
public class DefaultFTPServer implements FTPServer {
    protected Logger logger = Logger.getLogger(getClass());
    private ChannelFuture future;
    private boolean started = false;
    private boolean suspend = false;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    protected int port = 21;// 端口
    protected int cmdListenerPort = 22;// 指令监听端口
    protected String host;// IP
    protected MessageCode code = MessageCode.newMessageCode(MessageType.Info);
    protected MessageResource lmr = LocalizedMessageResource.newInstance();
    private boolean SSL = false;

    protected DefaultFTPServer daemon = this;// 守护进程

    protected static String CMD_STOP = "stop";
    protected static String CMD_SUSPENDED = "suspended";
    protected static String CMD_RESUME = "resume";
    protected FTPServerCommandSender ftpServerCMDSender = new FTPServerCommandSender();
    protected DefaultFTPServerInitializer ftpServerInitializer;// 默认的FTP服务器初始化对象
    protected Charset charset;

    public DefaultFTPServer(int port, String host, boolean ssl) {
        super();
        this.port = port;
        this.host = host;
        this.SSL = ssl;
    }

    public DefaultFTPServer(int port, boolean ssl) {
        this(port, null, ssl);
    }

    public DefaultFTPServer(int port, String host) {
        this(port, host, false);
    }

    public DefaultFTPServer(int port) {
        this(port, false);
    }

    public DefaultFTPServer() {
        this(21, false);
    }

    /**
     * 
     * @return
     */
    @Override
    public boolean isStarted() {
        return started;
    }

    @Override
    public void start() {
        if (isStarted()) {
            return;
        }
        // 启动FTP服务器指令监听
        startShutdownListener();
        SslContext sslCtx = null;
        try {
            if (SSL) {
                SelfSignedCertificate ssc = new SelfSignedCertificate();
                sslCtx = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
            }
        } catch (Exception e) {
            code.setMsgKey("server.start.err");
            logger.error(lmr.getMessage(code, e.getMessage()), e);
            System.exit(1);
        }

        if (ftpServerInitializer == null) {
            ftpServerInitializer = new DefaultFTPServerInitializer(charset, sslCtx);
        } else {
            ftpServerInitializer.setSslCtx(sslCtx);
        }

        ServerBootstrap b = new ServerBootstrap();
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(ftpServerInitializer)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        try {
            if (StringUtils.isBlank(host)) {
                future = b.bind(port).sync();
            } else {
                future = b.bind(host, port).sync();
            }
            code.setMsgKey("server.started");
            logger.info(lmr.getMessage(code, String.valueOf(port)));
            started = true;
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            code.setMsgKey("server.start.err");
            logger.error(lmr.getMessage(code, e.getMessage()), e);
        }
    }

    /**
     * 启动FTP服务器指令监听
     */
    private void startShutdownListener() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    ServerSocket server = new ServerSocket();
                    // 绑定IP和端口
                    InetSocketAddress ipAddress = null;
                    if (StringUtils.isBlank(host)) {
                        ipAddress = new InetSocketAddress(cmdListenerPort);
                    } else {
                        ipAddress = new InetSocketAddress(host, cmdListenerPort);
                    }
                    server.bind(ipAddress);

                    Socket client = server.accept();
                    BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    String cmd = br.readLine();
                    if (CMD_STOP.equalsIgnoreCase(cmd)) {
                        daemon.stopServer();
                    } else if (CMD_RESUME.equalsIgnoreCase(cmd)) {
                        daemon.resumeServer();
                    } else if (CMD_SUSPENDED.equalsIgnoreCase(cmd)) {
                        daemon.suspendedServer();
                    } else {
                        logger.error("不支持的指令【" + cmd + "】");
                    }
                    server.close();
                } catch (Exception e) {
                }
            }
        }).start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            logger.error(e);
        }
    }

    /**
     * 
     * FTP服务器指定发送器
     * 
     * @author sea
     */
    protected class FTPServerCommandSender {

        /**
         * 发送指令
         * 
         * @param cmd 指令
         */
        public void sendCmd(String cmd) {
            try {
                Socket client = null;
                // 如果没有设置IP地址，发送给本地所有网卡
                if (StringUtils.isBlank(host)) {
                    client = new Socket("0.0.0.0", cmdListenerPort);
                } else {
                    client = new Socket(host, cmdListenerPort);
                }
                BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                bw.write(cmd);
                bw.flush();
                client.close();
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("FTP服务器指令发送失败");
            }
        }
    }

    /**
     * 关闭服务器
     */
    protected void stopServer() {
        if (!started) {
            return;
        }
        destroy();
        started = false;
    }

    /**
     * 销毁资源
     */
    private void destroy() {
        future.channel().close();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        bossGroup = null;
        workerGroup = null;
    }

    /**
     * 暂停服务器
     */
    protected void suspendedServer() {
        if (!started) {
            return;
        }
        if (suspend) {
            return;
        }
        destroy();
        suspend = true;
    }

    /**
     * 恢复服务器
     */
    protected void resumeServer() {
        if (!started) {
            return;
        }
        if (!suspend) {
            return;
        }
        start();
        suspend = false;
    }

    @Override
    public boolean isSuspended() {
        return suspend;
    }

    @Override
    public void stop() {
        ftpServerCMDSender.sendCmd(CMD_STOP);
    }

    @Override
    public void suspended() {
        ftpServerCMDSender.sendCmd(CMD_SUSPENDED);
    }

    @Override
    public void resume() {
        ftpServerCMDSender.sendCmd(CMD_RESUME);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getShutdownPort() {
        return cmdListenerPort;
    }

    public void setShutdownPort(int shutdownPort) {
        this.cmdListenerPort = shutdownPort;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public DefaultFTPServerInitializer getFtpServerInitializer() {
        return ftpServerInitializer;
    }

    public void setFtpServerInitializer(DefaultFTPServerInitializer ftpServerInitializer) {
        this.ftpServerInitializer = ftpServerInitializer;
    }

    public boolean isSSL() {
        return SSL;
    }

    public void setSSL(boolean sSL) {
        SSL = sSL;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }

}
