package io.github.anotherme17.okhttp3util.util;


import java.util.ArrayList;
import java.util.List;

/**
 * <p>DeviceInfoUtil class.</p>
 *
 * @author zhangwt
 * @version $Id: $Id
 */
public class DeviceInfoUtil {

    private static List<String> list1 = new ArrayList<String>() {{
        add("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_4; en-us) AppleWebKit/528.4+ (KHTML, like Gecko) Version/4.0dp1 Safari/526.11.2");
        add("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_2; en-us) AppleWebKit/531.21.8 (KHTML, like Gecko) Version/4.0.4 Safari/531.21.10");
        add("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_8; zh-cn) AppleWebKit/533.18.1 (KHTML, like Gecko) Version/5.0.2 Safari/533.18.5");
        add("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_3; en-au) AppleWebKit/533.16 (KHTML, like Gecko) Version/5.0 Safari/533.16");
        add("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_5; en-US) AppleWebKit/534.13 (KHTML, like Gecko) Chrome/9.0.597.15 Safari/534.13");
        add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
    }};

    /**
     * <p>randomUserAgent.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public static String randomUserAgent() {
        return list1.get((int) (Math.random() * list1.size()));
    }

    public static String getVersion(String userAgent){
        return userAgent.replaceAll("Mozilla/","");
    }
}
