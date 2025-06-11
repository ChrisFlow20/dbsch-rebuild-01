package com.wisecoders.dbs.tips.tips;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.project.fx.Theme;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.SRx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.UniqueArrayList;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Tips {
  protected final List a = new ArrayList();
  
  private String b = "tips";
  
  public abstract String getName();
  
  public abstract String getDialogTitle();
  
  public abstract boolean goToFirst();
  
  public abstract boolean cycleTips();
  
  protected void a(Class paramClass) {
    Properties properties = new Properties();
    this.b = paramClass.getSimpleName();
    try {
      InputStream inputStream = paramClass.getResourceAsStream("resources/" + this.b + ".properties");
      try {
        SRx.a(properties, inputStream);
        if (Sys.l() != null) {
          InputStream inputStream1 = paramClass.getResourceAsStream("resources/" + this.b + "_" + Sys.l() + ".properties");
          try {
            SRx.a(properties, inputStream1);
            if (inputStream1 != null)
              inputStream1.close(); 
          } catch (Throwable throwable) {
            if (inputStream1 != null)
              try {
                inputStream1.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              }  
            throw throwable;
          } 
        } 
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
    } catch (Throwable throwable) {
      Log.b(throwable);
    } 
    byte b = 0;
    String str;
    while ((str = properties.getProperty("" + ++b + ".text")) != null)
      this.a.add(str); 
  }
  
  public String getTip() {
    return this.a.get(getIndex());
  }
  
  public String getIndexString() {
    return "" + getIndex() + 1 + " of " + getIndex() + 1;
  }
  
  public int getTipsCount() {
    return this.a.size();
  }
  
  private static final Pattern c = Pattern.compile("src='\\{PATH}([^']*)'");
  
  private static final String d = "TIPS:";
  
  public static String formatTip(String paramString, boolean paramBoolean) {
    if (paramString != null) {
      if (paramString.startsWith("<html>"))
        paramString = paramString.substring("<html>".length()); 
      String str = "";
      try {
        str = str + str;
      } catch (IOException iOException) {}
      StringBuilder stringBuilder = new StringBuilder();
      Matcher matcher = c.matcher(paramString);
      while (matcher.find()) {
        String str1 = a(matcher.group(1));
        matcher.appendReplacement(stringBuilder, Matcher.quoteReplacement(str1));
      } 
      matcher.appendTail(stringBuilder);
      return str + str;
    } 
    return null;
  }
  
  private static String a(String paramString) {
    URL uRL = Tips.class.getResource(paramString);
    if ((Theme.a()).j) {
      URL uRL1 = Tips.class.getResource(paramString.replaceFirst("\\.png", Matcher.quoteReplacement("-dark.png")));
      if (uRL1 != null)
        uRL = uRL1; 
    } 
    String str = String.valueOf(uRL);
    return " src='" + str + "' " + StringUtil.sizeString(uRL);
  }
  
  public void incrementPage(int paramInt) {
    setIndex(getIndex() + paramInt);
  }
  
  public void setIndex(int paramInt) {
    if (paramInt > this.a.size() - 2)
      Pref.a(this.b + "_reachedEnd", true); 
    if (paramInt > this.a.size() - 1)
      paramInt %= this.a.size(); 
    if (paramInt < 0)
      paramInt = this.a.size() - 1; 
    Pref.a(this.b, paramInt);
  }
  
  public int getIndex() {
    return Pref.b(this.b, 0) % this.a.size();
  }
  
  public void resetIndex() {
    Pref.b(this.b);
  }
  
  public boolean hasReachedEnd() {
    return Pref.b(this.b + "_reachedEnd", false);
  }
  
  public boolean isShowTipsDialogOnStartup() {
    return (Pref.b("TIPS:" + this.b, true) && (Sys.B.alwaysShowUsageTips
      .b() || Pref.c("quickTour" + this.b, 12)));
  }
  
  public void setShowTipsDialogOnStartup(boolean paramBoolean) {
    Pref.a("TIPS:" + this.b, paramBoolean);
  }
  
  private static boolean e = true;
  
  public static void showBalloonTips(boolean paramBoolean) {
    e = paramBoolean;
  }
  
  private static final List f = new UniqueArrayList();
  
  public static boolean shouldShowTip(String paramString) {
    boolean bool = (e && !f.contains(paramString) && (Pref.c(paramString, 7) || Sys.B.alwaysShowUsageTips.b())) ? true : false;
    f.add(paramString);
    return bool;
  }
}
