package com.wisecoders.dbs.cli.command.sql;

import groovy.text.SimpleTemplateEngine;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.mail.internet.InternetAddress;

class b extends HashMap {
  public final String a;
  
  public final int b;
  
  b(NewsletterSendCommand paramNewsletterSendCommand, Map<? extends K, ? extends V> paramMap) {
    putAll(paramMap);
    if (!containsKey("email"))
      throw new NullPointerException("dbschema_newsletter is missing email"); 
    if (!containsKey("userid"))
      throw new NullPointerException("dbschema_newsletter is missing userid"); 
    this.a = b("email");
    this.b = ((Number)get("userid")).intValue();
  }
  
  private String a(String paramString) {
    return (new SimpleTemplateEngine()).createTemplate(paramString).make(this).toString();
  }
  
  private String b(String paramString) {
    V v = get(paramString);
    return (v != null) ? String.valueOf(v) : null;
  }
  
  private InternetAddress a() {
    if (c(b("firstname")) && c(b("lastname")))
      return new InternetAddress(this.a, String.valueOf(get("firstname")) + " " + String.valueOf(get("firstname"))); 
    return new InternetAddress(this.a);
  }
  
  private boolean c(String paramString) {
    return (paramString != null && paramString.length() > 1 && paramString
      .substring(1).equals(paramString.substring(1).toLowerCase()) && paramString
      .substring(0, 1).toUpperCase().equals(paramString.substring(0, 1)));
  }
  
  private void a(int paramInt) {
    String str = this.a.toLowerCase();
    int i = str.indexOf("@");
    if (i > 0) {
      String str1 = this.a.substring(i + 1);
      if (this.c.i.containsKey(str1)) {
        Long long_ = (Long)this.c.i.get(str1);
        while (long_ != null && System.currentTimeMillis() - long_.longValue() < 86400000L / paramInt) {
          try {
            TimeUnit.SECONDS.sleep(1L);
          } catch (InterruptedException interruptedException) {}
        } 
      } 
      this.c.i.put(str1, Long.valueOf(System.currentTimeMillis()));
    } 
  }
}
