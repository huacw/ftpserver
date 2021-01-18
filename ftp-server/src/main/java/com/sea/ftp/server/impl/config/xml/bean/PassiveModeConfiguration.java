package com.sea.ftp.server.impl.config.xml.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 被动模式配置
 *
 * @author sea
 * @Date 2018/8/17 16:34
 * @Version 1.0
 */
@XmlRootElement(name = "passive")
@XmlAccessorType(XmlAccessType.NONE)
public class PassiveModeConfiguration {
    @XmlAttribute(name = "ports")
    private String ports;
    @XmlAttribute(name = "address")
    private String address;
    @XmlAttribute(name = "external-address")
    private String externalAddress;

    public String getPorts() {
        return ports;
    }

    public void setPorts(String ports) {
        this.ports = ports;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExternalAddress() {
        return externalAddress;
    }

    public void setExternalAddress(String externalAddress) {
        this.externalAddress = externalAddress;
    }
}
