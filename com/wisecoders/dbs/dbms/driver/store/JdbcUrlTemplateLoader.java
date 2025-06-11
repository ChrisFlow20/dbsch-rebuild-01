package com.wisecoders.dbs.dbms.driver.store;

import com.wisecoders.dbs.dbms.driver.model.DriverManager;
import com.wisecoders.dbs.dbms.driver.model.JdbcUrlTemplate;
import com.wisecoders.dbs.project.store.AbstractContentHandler;
import com.wisecoders.dbs.sys.Log;
import java.io.FileInputStream;
import java.io.InputStream;

public class JdbcUrlTemplateLoader extends AbstractContentHandler {
  private DriverManager a;
  
  public JdbcUrlTemplateLoader() {
    try {
      if (DriverManager.a.exists()) {
        FileInputStream fileInputStream = new FileInputStream(DriverManager.a);
        try {
          parse(fileInputStream);
          fileInputStream.close();
        } catch (Throwable throwable) {
          try {
            fileInputStream.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          } 
          throw throwable;
        } 
      } else {
        InputStream inputStream = JdbcUrlTemplateLoader.class.getResourceAsStream("/dbms/drivers.xml");
        try {
          parse(inputStream);
          if (inputStream != null)
            inputStream.close(); 
        } catch (Throwable throwable) {
          if (inputStream != null)
            try {
              inputStream.close();
            } catch (Throwable throwable1) {
              throwable.addSuppressed(throwable1);
            }  
          throw throwable;
        } 
      } 
    } catch (Exception exception) {
      Log.c(exception);
    } 
  }
  
  private final StoreKey[] b = new StoreKey[10];
  
  protected void a(String paramString, int paramInt) {
    StoreKey storeKey = StoreKey.valueOf(paramString);
    this.b[paramInt] = storeKey;
    a((paramInt > 0) ? this.b[paramInt - 1] : null, storeKey);
  }
  
  private void a(StoreKey paramStoreKey1, StoreKey paramStoreKey2) {
    if (paramStoreKey1 != null)
      switch (JdbcUrlTemplateLoader$1.a[paramStoreKey1.ordinal()]) {
        case 1:
          if (paramStoreKey2 == StoreKey.database) {
            this.a = DriverManager.a(get(StoreKey.name));
            this.a.b(get(StoreKey.local_description));
            this.a.c(get(StoreKey.remote_description));
            this.a.d(get(StoreKey.cloud_description));
            this.a.e(get(StoreKey.inherited_dbms));
            this.a.a(getBoolean(StoreKey.authenticate_manual_url, true));
          } 
          break;
        case 2:
          if (paramStoreKey2 == StoreKey.driver) {
            String str = get(StoreKey.url);
            if (str == null) {
              str = "";
            } else {
              str = str.replaceAll("\\{", "<").replaceAll("\\}", ">");
            } 
            JdbcUrlTemplate jdbcUrlTemplate = this.a.a(get(StoreKey.cls), str, 
                
                get(StoreKey.inst_label), 
                get(StoreKey.user_label), 
                get(StoreKey.param), 
                get(StoreKey.param2), 
                get(StoreKey.param3), 
                get(StoreKey.param4), 
                get(StoreKey.param5), 
                get(StoreKey.website), 
                get(StoreKey.description), 
                getBoolean(StoreKey.encode_password, true));
            jdbcUrlTemplate.a(getBoolean(StoreKey.authenticate, true));
            jdbcUrlTemplate.c(getBoolean(StoreKey.share_connection, false));
            jdbcUrlTemplate.c(get(StoreKey.hide_extensions));
            jdbcUrlTemplate.d(getBoolean(StoreKey.skip_port_if_default));
            jdbcUrlTemplate.b(getBoolean(StoreKey.linux_compatible, true));
            jdbcUrlTemplate.f(getBoolean(StoreKey.defo, false));
            jdbcUrlTemplate.d(get(StoreKey.user_prompt_text));
          } 
          break;
      }  
  }
}
