package io.github.anotherme17.okhttp3util.interceptor;

import io.github.anotherme17.okhttp3util.util.DeviceInfoUtil;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author lirenhao
 * date: 2017/11/9 下午6:03
 */
public class HeaderInterceptor implements Interceptor {

    private String mDevice = "";

    public HeaderInterceptor() {
        this.mDevice = DeviceInfoUtil.randomUserAgent();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept", "*/*")
                .addHeader("User-Agent", mDevice)
                .build();
        return chain.proceed(request);
    }
}
