package io.github.anotherme17.okhttp3util.manager;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.util.*;

/**
 * @author lirenhao
 * date: 2017/11/15 下午3:38
 */
public class CookieManager implements CookieJar {

    private final List<Cookie> cookieStore = new ArrayList<>();

    private boolean cookieEnable = true;

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cookieStore.addAll(cookies);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        if (!cookieEnable)
            return new ArrayList<>();
        return cookieStore;
    }

    public void setCookieEnable(boolean enable) {
        this.cookieEnable = enable;
    }

    public boolean getCookieEnable() {
        return cookieEnable;
    }

    public void restoreCookie(String cookieStr, String domain) {
        if (!cookieEnable)
            return;
        Map<String, String> cookieMap;
        if (cookieStr != null) {
            cookieMap = convertJsonToMap(cookieStr);
            for (Map.Entry<String, String> entry : cookieMap.entrySet()) {
                this.addCookie(entry.getKey(), entry.getValue(), domain, "/");
            }
        }
    }

    public static Map<String, String> convertJsonToMap(String cookieJson) {
        Map<String, String> map = new HashMap<>();
        if (cookieJson.contains(";")) {
            String[] pairs = cookieJson.split(";");
            for (String pair : pairs) {

                int index = pair.indexOf("=");

                String key = pair.substring(0, index).trim();
                String value = pair.substring(index + 1).trim();

                map.put(key, value);
            }
        }
        return map;
    }

    public void addCookie(String name, String value, String domain, String path) {
        if (name == null || name.isEmpty())
            return;
        for (Cookie cookie : this.cookieStore) {
            if (name.equals(cookie.name())) {
                return;
            }
        }

        Cookie newCookie = new Cookie.Builder()
                .name(name)
                .value(value)
                .domain(domain)
                .path(path)
                .expiresAt(System.currentTimeMillis() + 24 * 60 * 60 * 1000)
                .build();
        this.cookieStore.add(newCookie);
    }
}
