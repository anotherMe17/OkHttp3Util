package io.github.anotherme17.okhttp3util.interceptor;

import io.github.anotherme17.okhttp3util.util.DeviceInfoUtil;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>HeaderInterceptor class.</p>
 *
 * @author lirenhao
 * date: 2017/11/9 下午6:03
 * @version $Id: $Id
 */
public class HeaderInterceptor implements Interceptor {

    private Map<String, String> staticHeads = new HashMap<>();

    /**
     * <p>Constructor for HeaderInterceptor.</p>
     */
    public HeaderInterceptor() {
        this(DeviceInfoUtil.randomUserAgent());
    }

    public HeaderInterceptor(String device) {
        staticHeads.put("User-Agent", device);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        if (staticHeads.size() > 0) {
            Request.Builder builder = request.newBuilder();
            for (Map.Entry<String, String> entry : staticHeads.entrySet()) {
                builder.addHeader(entry.getKey(), entry.getValue());
            }

        }
        return chain.proceed(request);
    }

    public void setStaticHeads(String key, String value) {
        staticHeads.put(key, value);
    }

    public void setStaticHeads(Map<String, String> heads) {
        staticHeads.putAll(heads);
    }
}
