package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.sys.Log;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

public class HttpsUtils {
  private static CloseableHttpClient a;
  
  private static String b;
  
  static {
    a();
  }
  
  public static void a() {
    if (a == null)
      try {
        a = HttpClients.custom().setSSLHostnameVerifier((HostnameVerifier)NoopHostnameVerifier.INSTANCE).setSSLContext((new SSLContextBuilder()).loadTrustMaterial(null, (TrustStrategy)((paramArrayOfX509Certificate, paramString) -> true)).build()).build();
      } catch (KeyManagementException keyManagementException) {
        b = keyManagementException.getLocalizedMessage();
        Log.a("KeyManagementException in creating http client instance", keyManagementException);
      } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
        b = noSuchAlgorithmException.getLocalizedMessage();
        Log.a("NoSuchAlgorithmException in creating http client instance", noSuchAlgorithmException);
      } catch (KeyStoreException keyStoreException) {
        b = keyStoreException.getLocalizedMessage();
        Log.a("KeyStoreException in creating http client instance", keyStoreException);
      }  
  }
  
  public static CloseableHttpResponse a(HttpUriRequest paramHttpUriRequest) {
    a();
    if (a == null)
      throw new IOException("Cannot initialize HttpsUtils: " + b); 
    return a.execute(paramHttpUriRequest);
  }
}
