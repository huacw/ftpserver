package com.sea.ftp.server.impl.config.xml.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 主动模式配置
 *
 * @author sea
 * @Date 2018/8/17 16:34
 * @Version 1.0
 */
@XmlRootElement(name = "active")
@XmlAccessorType(XmlAccessType.NONE)
public class ActiveModeConfiguration {
    @XmlAttribute(name = "enabled")
    private boolean enabled;
    @XmlAttribute(name = "local-address")
    private String localAddress;
    @XmlAttribute(name = "local-port")
    private int localPort = 20;
    @XmlAttribute(name = "ip-check")
    private boolean ipCheck;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getLocalAddress() {
        return localAddress;
    }

    public void setLocalAddress(String localAddress) {
        this.localAddress = localAddress;
    }

    public int getLocalPort() {
        return localPort;
    }

    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }

    public boolean isIpCheck() {
        return ipCheck;
    }

    public void setIpCheck(boolean ipCheck) {
        this.ipCheck = ipCheck;
    }
}
