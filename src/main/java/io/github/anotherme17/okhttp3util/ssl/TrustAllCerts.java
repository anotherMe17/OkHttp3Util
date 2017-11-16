package io.github.anotherme17.okhttp3util.ssl;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * <p>TrustAllCerts class.</p>
 *
 * @author lirenhao
 * date: 2017/11/9 下午3:47
 * @version $Id: $Id
 */
public class TrustAllCerts implements X509TrustManager {
    /** {@inheritDoc} */
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    /** {@inheritDoc} */
    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    /** {@inheritDoc} */
    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
