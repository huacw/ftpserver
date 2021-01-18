package com.sea.ftp.server.impl.config.xml.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 服务器监听器
 *
 * @author sea
 */
@XmlRootElement(name = "listener")
@XmlAccessorType(XmlAccessType.NONE)
public class Listener {
    @XmlAttribute(required = true)
    private String name;
    @XmlAttribute
    private String host;
    @XmlAttribute
    private int port = 21;
    @XmlAttribute(name = "shutdown-port")
    private int shutdownPort = 22;
    @XmlElement(name = "ssl")
    private SSLConfiguration ssl;
    @XmlAttribute
    private String charset;
    @XmlElement(name = "data-connection")
    private DataConnectionConfiguration dataConnectionConfiguration;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getShutdownPort() {
        return shutdownPort;
    }

    public void setShutdownPort(int shutdownPort) {
        this.shutdownPort = shutdownPort;
    }

    public SSLConfiguration getSsl() {
        return ssl;
    }

    public void setSsl(SSLConfiguration ssl) {
        this.ssl = ssl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public DataConnectionConfiguration getDataConnectionConfiguration() {
        return dataConnectionConfiguration;
    }

    public void setDataConnectionConfiguration(DataConnectionConfiguration dataConnectionConfiguration) {
        this.dataConnectionConfiguration = dataConnectionConfiguration;
    }
}
