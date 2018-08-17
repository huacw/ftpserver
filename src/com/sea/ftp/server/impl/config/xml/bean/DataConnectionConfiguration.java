package com.sea.ftp.server.impl.config.xml.bean;

import javax.xml.bind.annotation.*;

/**
 * 数据连接配置
 *
 * @author sea
 * @Date 2018/8/17 16:28
 * @Version 1.0
 */
@XmlRootElement(name = "data-connection")
@XmlAccessorType(XmlAccessType.NONE)
public class DataConnectionConfiguration {
    @XmlAttribute(name = "idle-timeout")
    private int idleTimeout;
    @XmlAttribute(name = "ssl")
    private SSLConfiguration ssl;
    @XmlElement(name = "active")
    private ActiveModeConfiguration activeModeConfiguration;
    @XmlElement(name = "passive")
    private PassiveModeConfiguration passiveModeConfiguration;

    public int getIdleTimeout() {
        return idleTimeout;
    }

    public void setIdleTimeout(int idleTimeout) {
        this.idleTimeout = idleTimeout;
    }

    public SSLConfiguration getSsl() {
        return ssl;
    }

    public void setSsl(SSLConfiguration ssl) {
        this.ssl = ssl;
    }

    public ActiveModeConfiguration getActiveModeConfiguration() {
        return activeModeConfiguration;
    }

    public void setActiveModeConfiguration(ActiveModeConfiguration activeModeConfiguration) {
        this.activeModeConfiguration = activeModeConfiguration;
    }

    public PassiveModeConfiguration getPassiveModeConfiguration() {
        return passiveModeConfiguration;
    }

    public void setPassiveModeConfiguration(PassiveModeConfiguration passiveModeConfiguration) {
        this.passiveModeConfiguration = passiveModeConfiguration;
    }
}
