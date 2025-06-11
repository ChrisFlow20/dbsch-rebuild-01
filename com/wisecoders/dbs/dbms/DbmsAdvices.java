package com.wisecoders.dbs.dbms;

import com.wisecoders.dbs.dbms.store.AdvicesLoader;
import com.wisecoders.dbs.schema.ConnectivityTip;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.EscapeChars;
import com.wisecoders.dbs.sys.StringUtil;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DbmsAdvices {
  public String a;
  
  private final List b;
  
  public DbmsAdvices(String paramString) {
    this.b = new ArrayList();
    this.a = paramString;
  }
  
  public void a(Advice paramAdvice) {
    this.b.add(paramAdvice);
  }
  
  public String a(String paramString, Connector paramConnector) {
    for (Advice advice : this.b) {
      String str = advice.a(paramConnector, paramString);
      if (str != null)
        return str; 
    } 
    return null;
  }
  
  private static final Map c = new LinkedHashMap<>();
  
  public static DbmsAdvices a(String paramString) {
    if (StringUtil.isEmptyTrim(paramString))
      throw new NullPointerException("Missing name when searching for a DBMS."); 
    DbmsAdvices dbmsAdvices = (DbmsAdvices)c.get(paramString);
    if (dbmsAdvices == null) {
      dbmsAdvices = new DbmsAdvices(paramString);
      new AdvicesLoader(dbmsAdvices);
      c.put(paramString, dbmsAdvices);
    } 
    return dbmsAdvices;
  }
  
  public String a(Throwable paramThrowable, String paramString1, String paramString2, Connector paramConnector, ConnectivityTip paramConnectivityTip) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("<html><h2>").append((paramString1 != null) ? paramString1 : "Database Error").append("</h2>");
    if (paramString2 != null)
      stringBuilder.append(paramString2); 
    if (paramThrowable != null) {
      String str1 = Dbms.get(this.a).getMessageFromException(paramThrowable, paramConnector);
      stringBuilder.append(EscapeChars.forHtml(str1).replaceAll("\n", "<br>\n"));
      String str2 = a(this.a).a(str1 + "\n" + str1, paramConnector);
      if (str2 != null || paramConnectivityTip != null) {
        stringBuilder.append("\n<h3>DbSchema Advice</h3>");
        if (str2 != null) {
          stringBuilder.append(str2);
        } else {
          stringBuilder.append("<br>").append(paramConnectivityTip.d);
        } 
      } 
    } 
    return stringBuilder.toString();
  }
  
  public String a(Throwable paramThrowable, Connector paramConnector) {
    StringBuilder stringBuilder = new StringBuilder();
    if (paramThrowable != null) {
      String str1 = Dbms.get(this.a).getMessageFromException(paramThrowable, paramConnector);
      stringBuilder.append(str1);
      String str2 = a(this.a).a(str1, paramConnector);
      if (str2 != null)
        stringBuilder.append(StringUtil.toPlainText(str2)); 
    } 
    return stringBuilder.toString();
  }
  
  public String a(Throwable paramThrowable, ConnectivityTip paramConnectivityTip) {
    return a(paramThrowable, null, null, null, paramConnectivityTip);
  }
}
