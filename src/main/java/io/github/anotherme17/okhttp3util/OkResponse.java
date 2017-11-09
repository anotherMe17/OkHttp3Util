package io.github.anotherme17.okhttp3util;

import com.alibaba.fastjson.JSON;
import okhttp3.ResponseBody;

import java.io.IOException;

/**
 * @author lirenhao
 * date: 2017/11/9 下午5:05
 */
public class OkResponse {

    private ResponseBody body;

    public OkResponse(ResponseBody body) {
        this.body = body;
    }

    public String readString() throws IOException {
        return this.body.string();
    }

    public <T> T readObject(Class<T> tClass) throws IOException {
        String s = this.body.string();
        return JSON.parseObject(s, tClass);
    }
}
