package com.wisecoders.dbs.config.model;

import com.wisecoders.dbs.data.groovy.Groovy;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.StringUtil;
import groovy.lang.Binding;
import groovy.sql.Sql;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProviderProperty extends StringProperty {
  private String g;
  
  private String h;
  
  private final Map i = new HashMap<>();
  
  ProviderProperty(Property paramProperty, String paramString) {
    super(paramProperty, paramString);
  }
  
  public void a(Properties paramProperties, boolean paramBoolean) {
    String str = paramProperties.getProperty(l());
    if (str != null) {
      a(str);
      if (paramBoolean)
        this.h = this.g; 
    } 
  }
  
  public void b(Properties paramProperties, boolean paramBoolean) {
    if (paramBoolean || !e())
      if (this.i.isEmpty()) {
        paramProperties.remove(l());
      } else {
        paramProperties.put(l(), c_());
      }  
  }
  
  public void a(Object paramObject) {
    if (paramObject != null) {
      this.g = String.valueOf(paramObject);
      f(this.g);
    } 
  }
  
  public Object c() {
    return c_();
  }
  
  public String toString() {
    return c_();
  }
  
  public String c_() {
    StringBuilder stringBuilder = new StringBuilder();
    for (String str : this.i.keySet())
      stringBuilder.append(str).append(":").append(this.i.get(str)).append("\n\n"); 
    return stringBuilder.toString();
  }
  
  private static final Pattern j = Pattern.compile("^([a-zA-Z.]+):.*");
  
  private void f(String paramString) {
    String[] arrayOfString = paramString.split("\\r?\\n");
    StringBuilder stringBuilder = new StringBuilder();
    String str = null;
    for (String str1 : arrayOfString) {
      if (StringUtil.isEmptyTrim(str1)) {
        a(str, stringBuilder.toString());
        stringBuilder.delete(0, stringBuilder.length());
        str = null;
      } else if (str == null) {
        Matcher matcher = j.matcher(str1);
        if (matcher.matches()) {
          str = matcher.group(1);
          stringBuilder.append(str1.substring(str.length() + 1)).append("\n");
        } else {
          str = "Unknown";
          stringBuilder.append(str1).append("\n");
        } 
      } else {
        stringBuilder.append(str1).append("\n");
      } 
    } 
    a(str, stringBuilder.toString());
  }
  
  private void a(String paramString1, String paramString2) {
    if (paramString1 != null) {
      b b = b.b;
      int i = paramString1.indexOf(".");
      if (i > 0) {
        if (paramString1.endsWith(".sql"))
          b = b.a; 
        if (paramString1.endsWith(".offline.groovy"))
          b = b.c; 
        if (paramString1.endsWith(".groovy"))
          b = b.b; 
        paramString1 = paramString1.substring(0, i);
      } 
      this.i.put(paramString1, new a(b, paramString2));
    } 
  }
  
  public boolean c(String paramString) {
    return this.i.containsKey(paramString);
  }
  
  public boolean d(String paramString) {
    return (this.i.containsKey(paramString) && ((a)this.i.get(paramString)).a != b.c);
  }
  
  public List a(String paramString, Connector paramConnector) {
    a a = (a)this.i.get(paramString);
    ArrayList<String> arrayList = new ArrayList();
    if (a != null && a.b != null)
      if (a.a == b.a) {
        if (paramConnector != null) {
          Envoy envoy = paramConnector.startEnvoy("Choose Option");
          try {
            SelectStatement selectStatement = envoy.a(a.b, new Object[0]);
            try {
              ResultSet resultSet = selectStatement.j();
              if (resultSet.getMetaData().getColumnCount() > 0)
                while (resultSet.next())
                  arrayList.add(resultSet.getString(1));  
              if (selectStatement != null)
                selectStatement.close(); 
            } catch (Throwable throwable) {
              if (selectStatement != null)
                try {
                  selectStatement.close();
                } catch (Throwable throwable1) {
                  throwable.addSuppressed(throwable1);
                }  
              throw throwable;
            } 
            if (envoy != null)
              envoy.close(); 
          } catch (Throwable throwable) {
            if (envoy != null)
              try {
                envoy.close();
              } catch (Throwable throwable1) {
                throwable.addSuppressed(throwable1);
              }  
            throw throwable;
          } 
        } 
      } else {
        Binding binding = new Binding();
        Envoy envoy = null;
        if (paramConnector != null) {
          envoy = paramConnector.startEnvoy("Choose Option");
          Sql sql = new Sql(envoy.c());
          binding.setVariable("sql", sql);
        } 
        try {
          return (List)Groovy.a.a(binding, a.b);
        } catch (Throwable throwable) {
          if (envoy != null)
            envoy.a(throwable); 
          throw throwable;
        } finally {
          if (envoy != null)
            envoy.close(); 
        } 
      }  
    return arrayList;
  }
  
  public boolean e() {
    return StringUtil.areEqual(this.h, this.g);
  }
}
