package io.github.anotherme17.okhttp3util.entity;

import com.google.common.base.Objects;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;

/**
 * @author lirenhao
 * date: 2017/11/15 下午6:09
 */
public class ProxyInfo {
    private String ipaddr;
    private int port;
    private String password;
    private String username;
    private String province;
    private String city;
    private boolean https;
    private String carrier;

    public String getProvince() {
        return this.province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public ProxyInfo() {
    }

    public ProxyInfo(String proxyStr) {
        URI uri = URI.create(proxyStr);
        this.https = "https".equals(uri.getScheme());
        this.ipaddr = uri.getHost();
        this.port = uri.getPort();
        String userInfo = uri.getUserInfo();
        if (userInfo != null) {
            String[] uandp = userInfo.split(":");
            this.username = uandp[0];
            this.password = uandp[1];
        }

    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isHttps() {
        return this.https;
    }

    public void setHttps(boolean isHttps) {
        this.https = isHttps;
    }

    public String getIpaddr() {
        return this.ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProxyString() {
        StringBuilder sb = new StringBuilder();
        if (this.https) {
            sb.append("https://");
        } else {
            sb.append("http://");
        }

        if (!StringUtils.isEmpty(this.username) && !StringUtils.isEmpty(this.password)) {
            sb.append(this.username).append(':').append(this.password).append('@').append(this.ipaddr).append(':').append(this.port);
        } else {
            sb.append(this.ipaddr).append(":").append(this.port);
        }

        return sb.toString();
    }

    public String getProxyIpString() {
        StringBuilder sb = new StringBuilder();
        if (this.https) {
            sb.append("https://");
        } else {
            sb.append("http://");
        }

        sb.append(this.ipaddr).append(":").append(this.port);
        return sb.toString();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            ProxyInfo that = (ProxyInfo) o;
            return Objects.equal(this.port, that.port) && Objects.equal(this.ipaddr, that.ipaddr);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{this.ipaddr, this.port});
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }
}
