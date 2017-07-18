package com.sea.ftp.boot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.sea.ftp.exception.FTPServerException;
import com.sea.ftp.exception.illegal.IllegalArgumentException;
import com.sea.ftp.message.MessageCode;
import com.sea.ftp.message.MessageCode.MessageType;
import com.sea.ftp.message.i18n.LocalizedMessageResource;
import com.sea.ftp.server.FTPServer;
import com.sea.ftp.server.FTPServerFactory;
import com.sea.ftp.server.impl.DefaultFTPServer;
import com.sea.ftp.server.impl.config.xml.XmlConfigFTPServer;
import com.sea.ftp.util.StringUtils;

/**
 * 
 * FTP服务器启动入口（单独部署FTP服务器时）
 * 
 * @author sea
 */
public class BootStartup {
    private static final String CMD_HELP = "help";
    private static final String CMD_RESUME = "resume";
    private static final String CMD_SUSPENDED = "suspended";
    private static final String CMD_STOP = "stop";
    private static final String CMD_START = "start";
    private static int MODE_DEFAULT = 1;// 默认创建模式
    private static int MODE_CONFIG = 2;// 配置文件的创建模式
    private static int MODE_ARGS = 3;// 命令行的创建模式
    private static Logger logger = Logger.getLogger(BootStartup.class);
    private static MessageCode code = MessageCode.newMessageCode(MessageType.Info);
    private static LocalizedMessageResource lmr = LocalizedMessageResource.newInstance();
    private static StringBuilder helpDoc = new StringBuilder();

    /** 命令行参数 */
    private static String command = CMD_START;// 执行命令
    private static String host = null;// 绑定地址
    private static int port = 21;// 端口
    private static boolean ssl = false;// 使用使用SSL
    private static String configFile = "/config/server.xml";// 配置文件

    /**
     * 启动默认FTP服务器
     * 
     * @param args
     * @throws FTPServerException
     */
    public static void main(String[] args) throws FTPServerException {
        // 验证命令行参数是否正确
        boolean isVaild = vaildateAndBuildFtpServers(args);

        if (!isVaild) {
            logger.error("命令行参数式不正确");
            return;
        }

        // 获取已注册的FTP服务器
        List<FTPServer> registeredServers = FTPServerFactory.getRegisteredServers();

        if (CMD_START.equalsIgnoreCase(command)) {
            code.setMsgKey("server.starting");
            logger.info(lmr.getMessage(code));
            for (final FTPServer server : registeredServers) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        server.start();
                    }
                }).start();
            }
        } else if (CMD_STOP.equalsIgnoreCase(command)) {
            code.setMsgKey("server.stoping");
            logger.info(lmr.getMessage(code));
            for (FTPServer server : registeredServers) {
                server.stop();
            }
        } else if (CMD_SUSPENDED.equalsIgnoreCase(command)) {
            for (FTPServer server : registeredServers) {
                server.suspended();
            }
        } else if (CMD_RESUME.equalsIgnoreCase(command)) {
            for (FTPServer server : registeredServers) {
                server.resume();
            }
        } else if (CMD_HELP.equalsIgnoreCase(command)) {
            usage();
        } else {
            code.setMsgKey("illegalcommand");
            logger.info(lmr.getMessage(code, command));
        }
    }

    /**
     * 验证命令行参数是否正确并构建FTP服务器
     * 
     * @param args 要验证的参数
     * @return true-验证通过，false-验证失败
     * @throws FTPServerException
     */
    private static boolean vaildateAndBuildFtpServers(String[] args) throws FTPServerException {
        if (args == null || args.length == 0) {
            command = CMD_START;
            buildFTPServer(MODE_DEFAULT);
            return true;
        }
        if (args.length == 1) {
            command = args[0];
            // 如果命令为start、stop、suspend、resume时，按默认配置文件解析
            if (CMD_START.equalsIgnoreCase(command) || CMD_STOP.equalsIgnoreCase(command) || CMD_SUSPENDED.equalsIgnoreCase(command)
                    || CMD_RESUME.equalsIgnoreCase(command)) {
                buildFTPServer(MODE_DEFAULT);
            }
            return true;
        }
        command = args[0];
        String secondArg = args[1];
        int length = args.length;
        if ("-default".equalsIgnoreCase(secondArg)) {// 使用-default参数，后面不再跟任何参数
            if (length != 2) {
                return false;
            }
            buildFTPServer(MODE_DEFAULT);
        } else if ("-config".equals(secondArg)) {// 使用-config参数，后面需指定配置文件
            if (length == 3) {
                configFile = args[2];
            } else if (length > 3) {
                return false;
            }
            buildFTPServer(MODE_CONFIG);
        } else {
            if (parseArgs(args, length)) {
                buildFTPServer(MODE_ARGS);
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 解析系统参数
     * 
     * @param args
     * @param length
     * @return
     */
    private static boolean parseArgs(String[] args, int length) {
        for (int i = 1; i < length; i++) {
            String arg = args[i];
            if ("-h".equalsIgnoreCase(arg)) {// 使用-h参数，后面需指定IP
                if (i <= length - 1) {
                    return false;
                }
                if (vaildIP(args[i + 1])) {
                    i++;
                } else {
                    return false;
                }
            } else if ("-p".equalsIgnoreCase(arg)) {// 使用-p参数，后面需指定端口
                if (i <= length - 1) {
                    return false;
                }
                try {
                    port = Integer.parseInt(args[i + 1]);
                    if (port < 0 || port > 65535) {// 端口范围0-65535
                        return false;
                    }
                    i++;
                } catch (NumberFormatException e) {
                    return false;
                }
            } else if ("-ssl".equalsIgnoreCase(arg)) {// 使用-ssl参数
                ssl = true;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 构建FTP服务器
     * 
     * @param mode
     * @throws FTPServerException
     */
    private static void buildFTPServer(int mode) throws FTPServerException {
        if (mode == MODE_DEFAULT || mode == MODE_CONFIG) {
            new XmlConfigFTPServer(configFile);
        } else if (mode == MODE_ARGS) {
            FTPServerFactory.registerFTPServer(FTPServerFactory.DEFAULT_SERVER_NAME, initServer(host, port));
        } else {
            code.setMsgKey("illegalargs");
            throw new IllegalArgumentException(lmr.getMessage(code, String.valueOf(mode)));
        }
    }

    /**
     * 验证IP格式
     * 
     * @param string
     * @return
     */
    private static boolean vaildIP(String arg) {
        if ("localhost".equalsIgnoreCase(arg)) {
            host = "127.0.0.1";
            return true;
        } else {
            // IP正则表达式
            String regex =
                    "^(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(arg);
            return matcher.find();
        }
    }

    /**
     * 初始化服务器
     * 
     * @param host 服务器地址
     * @param port 端口
     * @return 创建的FTP服务器
     */
    private static FTPServer initServer(String host, int port) {
        FTPServer server;
        if (StringUtils.isBlank(host)) {
            server = new DefaultFTPServer(port, ssl);
        } else {
            server = new DefaultFTPServer(port, host, ssl);
        }
        return server;
    }

    /**
     * Print the usage message.
     */
    protected static void usage() {
        if (StringUtils.isBlank(helpDoc.toString())) {
            readHelp();
        }
        System.out.println(helpDoc);
    }

    /**
     * 读取帮助文档
     */
    private static void readHelp() {
        BufferedReader br = null;
        String lang = Locale.getDefault().toString();
        String docFile = "com/sea/ftp/boot/usage";
        if (StringUtils.isBlank(lang)) {
            docFile += ".txt";
        } else {
            docFile += "_" + lang + ".txt";
        }
        try {
            br = new BufferedReader(new InputStreamReader(BootStartup.class.getClassLoader().getResourceAsStream(docFile), "UTF-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                helpDoc.append(line).append(System.getProperty(("line.separator")));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    br = null;
                }
            }
        }
    }
}
