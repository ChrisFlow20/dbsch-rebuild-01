package com.wisecoders.dbs.loader.model;

import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.DataTypeUtil;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;

public class LoaderColumn {
  public final String a;
  
  private final Table b;
  
  private Column c;
  
  private boolean d;
  
  private String e;
  
  private String f;
  
  private boolean g = false;
  
  private final LinkedHashMap h = new LinkedHashMap<>();
  
  private int i = Integer.MAX_VALUE;
  
  private int j = 0;
  
  private int k = 0;
  
  private boolean l = true;
  
  public LoaderColumn(Table paramTable, String paramString) {
    this.b = paramTable;
    this.a = paramString;
  }
  
  public Column a() {
    return this.c;
  }
  
  public void a(Column paramColumn) {
    this.c = paramColumn;
  }
  
  void b() {
    this.h.clear();
    this.f = null;
    this.l = true;
    this.i = Integer.MAX_VALUE;
    this.j = 0;
    this.k = 0;
  }
  
  public String c() {
    if (this.c != null) {
      StringBuilder stringBuilder = new StringBuilder();
      if (this.g)
        stringBuilder.append("Column used multiple times. "); 
      if (this.c.getDataType().isDate()) {
        byte b1 = 0, b2 = 0;
        for (Integer integer : this.h.keySet()) {
          if (DataTypeUtil.isDate(integer.intValue())) {
            b1++;
            continue;
          } 
          b2++;
        } 
        if (b2 > 0)
          stringBuilder.append((b1 > 0) ? ("Found wrong date: " + this.f) : "Cannot convert to date. "); 
      } 
      if (this.c.getDataType().getPrecision().usesLength() && this.c.getLength() > 0 && this.c.getLength() < this.j)
        stringBuilder.append("Value larger than specified column size. "); 
      if (this.c.isMandatory() && !this.l)
        stringBuilder.append("Column is mandatory, some values are empty. "); 
      return stringBuilder.toString();
    } 
    return null;
  }
  
  public Object a(String paramString, SimpleDateFormat paramSimpleDateFormat) {
    if (StringUtil.isFilledTrim(paramString))
      if (this.c.getDataType().isNumeric()) {
        try {
          return Integer.valueOf(Integer.parseInt(paramString));
        } catch (NumberFormatException numberFormatException) {
          try {
            return Long.valueOf(Long.parseLong(paramString));
          } catch (NumberFormatException numberFormatException1) {
            try {
              return Double.valueOf(Double.parseDouble(paramString));
            } catch (NumberFormatException numberFormatException2) {}
          } 
        } 
      } else if (this.c.getDataType().isTime() && paramSimpleDateFormat != null) {
        try {
          paramSimpleDateFormat.setLenient(false);
          return new Time(paramSimpleDateFormat.parse(paramString).getTime());
        } catch (ParseException parseException) {}
      } else if (this.c.getDataType().isDate() && paramSimpleDateFormat != null) {
        try {
          paramSimpleDateFormat.setLenient(false);
          return paramSimpleDateFormat.parse(paramString);
        } catch (ParseException parseException) {}
      } else if (this.c.getDataType().isBoolean()) {
        return Boolean.valueOf(("true".equalsIgnoreCase(paramString) || "1".equalsIgnoreCase(paramString) || "yes".equalsIgnoreCase(paramString)));
      }  
    return paramString;
  }
  
  public void a(Object paramObject, SimpleDateFormat paramSimpleDateFormat) {
    if (StringUtil.isFilledTrim(paramObject)) {
      int i = b(paramObject, paramSimpleDateFormat);
      if (this.h.containsKey(Integer.valueOf(91)) && i != 91)
        this.f = paramObject.toString(); 
      this.h.put(Integer.valueOf(i), Integer.valueOf(this.h.containsKey(Integer.valueOf(i)) ? (((Integer)this.h.get(Integer.valueOf(i))).intValue() + 1) : 1));
      String str = paramObject.toString();
      this.i = Math.min(this.i, str.length());
      this.j = Math.max(this.j, str.length());
      int j;
      if ((j = str.indexOf(".")) > -1)
        this.k = Math.max(this.k, str.length() - j - 1); 
    } else {
      this.l = false;
    } 
  }
  
  public void a(String paramString) {
    if (this.h.size() == 2 && this.h.containsKey(Integer.valueOf(8)))
      this.h.remove(Integer.valueOf(4)); 
    if (this.h.size() == 1) {
      int i = ((Integer)this.h.keySet().iterator().next()).intValue();
      this.c.setDataType(DbmsTypes.get(paramString).getDataType(i));
    } 
    switch (LoaderColumn$1.a[this.c.getDataType().getPrecision().ordinal()]) {
      case 1:
        this.c.setLength((int)(this.j * 1.3F));
        break;
      case 2:
      case 3:
        this.c.setLength((int)(this.j * 1.3F));
        this.c.setDecimal(this.k);
        break;
    } 
    this.c.setMandatory(false);
  }
  
  public String toString() {
    return this.a;
  }
  
  public static int b(Object paramObject, SimpleDateFormat paramSimpleDateFormat) {
    if (paramObject != null) {
      if (paramObject instanceof Integer || paramObject instanceof Long)
        return 4; 
      if (paramObject instanceof java.util.Date)
        return 91; 
      if (paramObject instanceof Boolean)
        return 16; 
      if (paramObject instanceof Number)
        return 2; 
      if (StringUtil.isFilledTrim(paramObject)) {
        String str = paramObject.toString().trim();
        if ("true".equalsIgnoreCase(str) || "false"
          .equalsIgnoreCase(str) || "yes"
          .equalsIgnoreCase(str) || "no"
          .equalsIgnoreCase(str))
          return 16; 
        if (paramSimpleDateFormat != null)
          try {
            paramSimpleDateFormat.setLenient(false);
            paramSimpleDateFormat.parse(str);
            return 91;
          } catch (ParseException|NumberFormatException parseException) {
            Log.h();
          }  
        try {
          Integer.parseInt(str);
          return 4;
        } catch (NumberFormatException numberFormatException) {
          Log.h();
          try {
            Double.parseDouble(str);
            return 8;
          } catch (NumberFormatException numberFormatException1) {
            Log.h();
          } 
        } 
      } 
    } 
    return 12;
  }
  
  public void b(String paramString) {
    this.e = paramString;
  }
  
  public String d() {
    return this.e;
  }
  
  private String i() {
    return "LD-" + this.b.getName() + "." + StringUtil.cutOfNoDots(this.a, 40);
  }
  
  public void e() {
    Column column = (Column)this.b.columns.getByName(Pref.c(i(), this.a));
    if (column != null)
      a(column); 
  }
  
  public void f() {
    if (this.c == null || this.a.equalsIgnoreCase(this.c.getName())) {
      Pref.b(i());
    } else {
      Pref.a(i(), this.c.getName());
    } 
  }
  
  public void a(boolean paramBoolean) {
    this.l = paramBoolean;
  }
  
  public void b(boolean paramBoolean) {
    this.g = paramBoolean;
  }
  
  public boolean g() {
    return this.g;
  }
  
  public void c(boolean paramBoolean) {
    this.d = paramBoolean;
  }
  
  public boolean h() {
    return this.d;
  }
}
