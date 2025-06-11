package com.wisecoders.dbs.sys;

import com.wisecoders.dbs.dialogs.system.HttpsUtils;
import com.wisecoders.dbs.sys.fx.Alert$;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javax.swing.SwingUtilities;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;

public class WebBrowserExternal {
  private static final String a = "https://dbschema.com/documentation/";
  
  public static void a(Scene paramScene, String paramString) {
    a(paramScene, "https://dbschema.com/documentation/" + paramString);
  }
  
  public static void a(Scene paramScene, Object paramObject) {
    try {
      URI uRI = (paramObject instanceof URI) ? (URI)paramObject : ((paramObject instanceof URL) ? ((URL)paramObject).toURI() : new URI(paramObject.toString()));
      Log.c("Browse to " + String.valueOf(uRI));
      SwingUtilities.invokeLater(() -> {
            try {
              Desktop.getDesktop().browse(paramURI);
            } catch (IOException iOException) {
              Log.b(iOException);
            } 
          });
    } catch (UnsupportedOperationException unsupportedOperationException) {
      (new Alert$(Alert.AlertType.ERROR)).a(paramScene).a("Error").b("Please open in Web browser '" + String.valueOf(paramObject) + "'.").a(unsupportedOperationException).show();
    } catch (Throwable throwable) {
      (new Alert$(Alert.AlertType.ERROR)).a(paramScene).a("Error").b("Error opening web browser.").a(throwable).show();
    } 
  }
  
  public static void a(Scene paramScene, String paramString1, String paramString2) {
    try {
      if (a("https://dbschema.com/documentation/" + paramString1)) {
        a(paramScene, "https://dbschema.com/documentation/" + paramString1);
      } else {
        a(paramScene, "https://dbschema.com/documentation/" + paramString2);
      } 
    } catch (Exception exception) {
      Log.b(exception);
    } 
  }
  
  public static boolean a(String paramString) {
    CloseableHttpResponse closeableHttpResponse = HttpsUtils.a((HttpUriRequest)new HttpGet(paramString));
    try {
      boolean bool1 = (closeableHttpResponse.getStatusLine().getStatusCode() == 200) ? true : false;
      EntityUtils.consume(closeableHttpResponse.getEntity());
      boolean bool2 = bool1;
      if (closeableHttpResponse != null)
        closeableHttpResponse.close(); 
      return bool2;
    } catch (Throwable throwable) {
      if (closeableHttpResponse != null)
        try {
          closeableHttpResponse.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
  }
}
