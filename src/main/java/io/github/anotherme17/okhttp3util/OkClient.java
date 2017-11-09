package io.github.anotherme17.okhttp3util;

import io.github.anotherme17.okhttp3util.interceptor.HeaderInterceptor;
import io.github.anotherme17.okhttp3util.interceptor.HttpLoggingInterceptor;
import io.github.anotherme17.okhttp3util.ssl.TrustAllCerts;
import io.github.anotherme17.okhttp3util.ssl.TrustAllHostnameVerifier;
import okhttp3.*;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import java.net.Proxy;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author lirenhao
 * date: 2017/11/9 下午2:38
 */
public class OkClient {

    public static final MediaType FORM_JSON = MediaType.parse("application/json;charset=utf-8");
    public static final MediaType FORM_URLENCODED = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");

    private OkHttpClient mClient = null;

    public OkClient() {
        this(HttpLoggingInterceptor.Level.BODY, null);
    }

    public OkClient(HttpLoggingInterceptor.Level level, Proxy proxy) {
        //日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(level);

        //默认信任任何证书
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .sslSocketFactory(createSSLSocketFactory())
                .hostnameVerifier(new TrustAllHostnameVerifier())
                .cookieJar(new CookieJar() {
                    private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url.host(), cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url.host());
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(loggingInterceptor);
        if (proxy != null) {
            builder.proxy(proxy);
        }
        mClient = builder.build();
    }

    public OkRequest builder() {
        return new OkRequest(mClient);
    }

    public OkHttpClient getClient() {
        return mClient;
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
