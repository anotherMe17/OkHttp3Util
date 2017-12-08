package io.github.anotherme17.okhttp3util;

import io.github.anotherme17.okhttp3util.interceptor.HeaderInterceptor;
import io.github.anotherme17.okhttp3util.interceptor.HttpLoggingInterceptor;
import io.github.anotherme17.okhttp3util.manager.CookieManager;
import io.github.anotherme17.okhttp3util.ssl.TrustAllCerts;
import io.github.anotherme17.okhttp3util.ssl.TrustAllHostnameVerifier;
import okhttp3.*;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.net.Proxy;
import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * <p>OkClient class.</p>
 *
 * @author lirenhao
 * date: 2017/11/9 下午2:38
 * @version $Id: $Id
 */
public class OkClient {

    private int connect_time_out = 10 * 60;
    private int write_time_out = 10 * 60;
    private int read_time_out = 10 * 60;

    /**
     * Constant <code>FORM_JSON</code>
     */
    public static final MediaType FORM_JSON = MediaType.parse("application/json;charset=utf-8");
    /**
     * Constant <code>FORM_URLENCODED</code>
     */
    public static final MediaType FORM_URLENCODED = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");

    private OkHttpClient mClient = null;

    private CookieManager cookieManager = new CookieManager();

    private HeaderInterceptor headerInterceptor;

    /**
     * <p>Constructor for OkClient.</p>
     * <p>Log Level None</p>
     * <p>Proxy Null</p>
     */
    public OkClient() {
        this(HttpLoggingInterceptor.Level.NONE, null);
    }

    /**
     * <p>Constructor for OkClient.</p>
     *
     * @param level a {@link io.github.anotherme17.okhttp3util.interceptor.HttpLoggingInterceptor.Level} object.
     * @param proxy a {@link java.net.Proxy} object.
     */
    public OkClient(HttpLoggingInterceptor.Level level, Proxy proxy) {
        this(level, proxy, null);
    }

    public OkClient(HttpLoggingInterceptor.Level level, Proxy proxy, String userAgent) {
        //日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(level);

        //默认头部
        if (StringUtils.isEmpty(userAgent))
            headerInterceptor = new HeaderInterceptor();
        else
            headerInterceptor = new HeaderInterceptor(userAgent);

        //默认信任任何证书
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .sslSocketFactory(createSSLSocketFactory())
                .hostnameVerifier(new TrustAllHostnameVerifier())
                .cookieJar(cookieManager)
                .protocols(Arrays.asList(Protocol.HTTP_2, Protocol.SPDY_3, Protocol.HTTP_1_1))
                .readTimeout(read_time_out, TimeUnit.SECONDS)
                .writeTimeout(write_time_out, TimeUnit.SECONDS)
                .connectTimeout(connect_time_out, TimeUnit.SECONDS)
                .addInterceptor(headerInterceptor)
                .addInterceptor(loggingInterceptor);
        if (proxy != null) {
            builder.proxy(proxy);
        }
        mClient = builder.build();
    }

    /**
     * <p>builder.</p>
     *
     * @return a {@link io.github.anotherme17.okhttp3util.OkRequest} object.
     */
    public OkRequest builder() {
        return new OkRequest(mClient);
    }

    /**
     * <p>getClient.</p>
     *
     * @return a {@link okhttp3.OkHttpClient} object.
     */
    public OkHttpClient getClient() {
        return mClient;
    }

    public void restoreCookie(String cookieStr, String domain) {
        cookieManager.restoreCookie(cookieStr, domain);
    }

    public void setCookieEnable(boolean enable) {
        cookieManager.setCookieEnable(enable);
    }

    public boolean getCookieEnable() {
        return cookieManager.getCookieEnable();
    }

    public void addStaticHeads(String key, String value) {
        headerInterceptor.setStaticHeads(key, value);
    }

    public void addStaticHeads(Map<String, String> heads) {
        headerInterceptor.setStaticHeads(heads);
    }

    public int getConnect_time_out() {
        return connect_time_out;
    }

    public void setConnect_time_out(int connect_time_out) {
        this.connect_time_out = connect_time_out;
    }

    public int getWrite_time_out() {
        return write_time_out;
    }

    public void setWrite_time_out(int write_time_out) {
        this.write_time_out = write_time_out;
    }

    public int getRead_time_out() {
        return read_time_out;
    }

    public void setRead_time_out(int read_time_out) {
        this.read_time_out = read_time_out;
    }

    private SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ssfFactory;
    }
}
