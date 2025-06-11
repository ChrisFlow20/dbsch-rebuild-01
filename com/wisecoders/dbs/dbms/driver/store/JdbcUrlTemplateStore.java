package com.wisecoders.dbs.dbms.driver.store;

import com.wisecoders.dbs.dbms.driver.model.DriverManager;
import com.wisecoders.dbs.dbms.driver.model.JdbcUrlTemplate;
import com.wisecoders.dbs.project.store.XMLWriter;
import java.io.Writer;

public class JdbcUrlTemplateStore {
  public static final int a = 1;
  
  public JdbcUrlTemplateStore(Writer paramWriter) {
    XMLWriter xMLWriter = new XMLWriter(paramWriter);
    xMLWriter.a("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
    xMLWriter.a(StoreKey.drivers).e(StoreKey.version, Integer.valueOf(1));
    for (DriverManager driverManager : DriverManager.a()) {
      xMLWriter.a(StoreKey.database)
        .e(StoreKey.name, driverManager.b)
        .e(StoreKey.local_description, driverManager.l())
        .e(StoreKey.remote_description, driverManager.m())
        .e(StoreKey.cloud_description, driverManager.n())
        .e(StoreKey.inherited_dbms, driverManager.o())
        .e(StoreKey.authenticate_manual_url, Boolean.valueOf(driverManager.p()));
      for (JdbcUrlTemplate jdbcUrlTemplate : driverManager.f()) {
        if (driverManager.b.equals(jdbcUrlTemplate.p)) {
          String str = jdbcUrlTemplate.c();
          if (str != null)
            str = str.replaceAll("<", "{").replaceAll(">", "}"); 
          xMLWriter.a(StoreKey.driver)
            .e(StoreKey.cls, String.join(";", (CharSequence[])jdbcUrlTemplate.q))
            .e(StoreKey.url, str)
            .a(StoreKey.defo, jdbcUrlTemplate.t())
            .e(StoreKey.inst_label, jdbcUrlTemplate.f())
            .e(StoreKey.user_label, jdbcUrlTemplate.g())
            .e(StoreKey.param, jdbcUrlTemplate.k())
            .e(StoreKey.param2, jdbcUrlTemplate.l())
            .e(StoreKey.param3, jdbcUrlTemplate.m())
            .e(StoreKey.param4, jdbcUrlTemplate.n())
            .e(StoreKey.param5, jdbcUrlTemplate.o())
            .e(StoreKey.description, jdbcUrlTemplate.h())
            .e(StoreKey.hide_extensions, jdbcUrlTemplate.p())
            .e(StoreKey.skip_port_if_default, Boolean.valueOf(jdbcUrlTemplate.s()));
          if (!jdbcUrlTemplate.d())
            xMLWriter.e(StoreKey.linux_compatible, Boolean.valueOf(false)); 
          if (!jdbcUrlTemplate.a())
            xMLWriter.e(StoreKey.authenticate, Boolean.valueOf(false)); 
          xMLWriter.b(StoreKey.driver);
        } 
      } 
      xMLWriter.b(StoreKey.database);
    } 
    xMLWriter.b(StoreKey.drivers);
    xMLWriter.close();
  }
}
