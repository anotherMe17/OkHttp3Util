package io.github.anotherme17.okhttp3util;

import com.alibaba.fastjson.JSON;
import okhttp3.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

import static io.github.anotherme17.okhttp3util.OkClient.FORM_JSON;
import static io.github.anotherme17.okhttp3util.OkClient.FORM_URLENCODED;

/**
 * <p>OkRequest class.</p>
 *
 * @author lirenhao
 * date: 2017/11/9 下午5:03
 * @version $Id: $Id
 */
public class OkRequest {
    private OkHttpClient engine;
    private Request.Builder mBuilder = null;
    private String mUrl = "";
    private StringBuilder mQuires = new StringBuilder();

    /**
     * <p>Constructor for OkRequest.</p>
     *
     * @param engine a {@link okhttp3.OkHttpClient} object.
     */
    public OkRequest(OkHttpClient engine) {
        this.engine = engine;
        mBuilder = new Request.Builder();
    }

    /**
     * <p>url.</p>
     *
     * @param url a {@link java.lang.String} object.
     * @return a {@link io.github.anotherme17.okhttp3util.OkRequest} object.
     */
    public OkRequest url(String url) {
        this.mUrl = url;
        return this;
    }

    /**
     * <p>quires.</p>
     *
     * @param quires a {@link java.util.Map} object.
     * @return a {@link io.github.anotherme17.okhttp3util.OkRequest} object.
     */
    public OkRequest quires(Map<String, String> quires) {
        if (quires == null || quires.isEmpty())
            return this;
        boolean first = mQuires.length() <= 0;
        for (Map.Entry<String, String> entry : quires.entrySet()) {
            if (first) {
                first = false;
                mQuires.append("?");
            } else {
                mQuires.append("&");
            }
            mQuires.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue()));
        }
        return this;
    }

    /**
     * <p>header.</p>
     *
     * @param name  a {@link java.lang.String} object.
     * @param value a {@link java.lang.String} object.
     * @return a {@link io.github.anotherme17.okhttp3util.OkRequest} object.
     */
    public OkRequest header(String name, String value) {
        mBuilder.addHeader(name, value);
        return this;
    }

    /**
     * <p>headers.</p>
     *
     * @param headers a {@link java.util.Map} object.
     * @return a {@link io.github.anotherme17.okhttp3util.OkRequest} object.
     */
    public OkRequest headers(Map<String, String> headers) {
        if (headers == null || headers.isEmpty())
            return this;
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            mBuilder.header(entry.getKey(), entry.getValue());
        }
        return this;
    }

    public OkRequest headers(Headers headers) {
        mBuilder.headers(headers);
        return this;
    }

    private String generateUrl() {
        String tmp = mUrl;
        if (mQuires.length() > 0) {
            tmp += mQuires.toString();
        }
        return tmp;
    }

    /**
     * <p>get.</p>
     *
     * @return a {@link io.github.anotherme17.okhttp3util.OkResponse} object.
     * @throws java.io.IOException if any.
     */
    public OkResponse get() throws IOException {
        Request request = mBuilder
                .url(generateUrl())
                .get()
                .build();
        Response response = engine.newCall(request).execute();
        if (!response.isSuccessful()) {
            System.out.println(response.body().string());
            throw new IllegalStateException("请求失败");
        }
        return new OkResponse(response.body());
    }

    private RequestBody getBodyByMediaType(MediaType mediaType, Map<String, String> params) {
        String content = "";
        if (FORM_JSON.equals(mediaType)) {
            content = JSON.toJSONString(params);
            return RequestBody.create(FORM_JSON, content);
        } else if (FORM_URLENCODED.equals(mediaType)) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                content += entry.getKey() + "=" + entry.getValue();
            }
            return RequestBody.create(FORM_URLENCODED, content);
        }
        return null;
    }

    /**
     * <p>post.</p>
     *
     * @param mediaType a {@link okhttp3.MediaType} object.
     * @param params    a {@link java.util.Map} object.
     * @return a {@link io.github.anotherme17.okhttp3util.OkResponse} object.
     * @throws java.io.IOException if any.
     */
    public OkResponse post(MediaType mediaType, Map<String, String> params) throws IOException {

        Request request = mBuilder
                .url(generateUrl())
                .post(getBodyByMediaType(mediaType, params))
                .build();
        Response response = engine.newCall(request).execute();
        if (!response.isSuccessful()) {
            System.out.println(response.body().string());
            throw new IllegalStateException("请求失败");
        }
        return new OkResponse(response.body());
    }
}
