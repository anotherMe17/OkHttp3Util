package io.github.anotherme17.okhttp3util.ssl;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * <p>TrustAllHostnameVerifier class.</p>
 *
 * @author lirenhao
 * date: 2017/11/9 下午3:47
 * @version $Id: $Id
 */
public class TrustAllHostnameVerifier implements HostnameVerifier {
    /** {@inheritDoc} */
    @Override
    public boolean verify(String hostname, SSLSession session) {
        return true;
    }
}
