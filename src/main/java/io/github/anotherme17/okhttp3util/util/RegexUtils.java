package io.github.anotherme17.okhttp3util.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lirenhao
 * date: 2017/10/24 上午9:44
 */
public class RegexUtils {

    public static String group(String str, String regex,String defaultStr) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find())
            return matcher.group();
        return defaultStr;
    }
}
