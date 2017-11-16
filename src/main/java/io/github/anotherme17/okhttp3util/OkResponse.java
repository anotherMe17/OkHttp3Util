package io.github.anotherme17.okhttp3util;

import com.alibaba.fastjson.JSON;
import okhttp3.ResponseBody;

import java.io.IOException;

/**
 * <p>OkResponse class.</p>
 *
 * @author lirenhao
 * date: 2017/11/9 下午5:05
 * @version $Id: $Id
 */
public class OkResponse {

    private ResponseBody body;

    /**
     * <p>Constructor for OkResponse.</p>
     *
     * @param body a {@link okhttp3.ResponseBody} object.
     */
    public OkResponse(ResponseBody body) {
        this.body = body;
    }

    /**
     * <p>readString.</p>
     *
     * @return a {@link java.lang.String} object.
     * @throws java.io.IOException if any.
     */
    public String readString() throws IOException {
        return this.body.string();
    }

    /**
     * <p>readObject.</p>
     *
     * @param tClass a {@link java.lang.Class} object.
     * @param <T> a T object.
     * @return a T object.
     * @throws java.io.IOException if any.
     */
    public <T> T readObject(Class<T> tClass) throws IOException {
        String s = this.body.string();
        return JSON.parseObject(s, tClass);
    }
}
