package com.wisecoders.dbs.editors.csv.model;

import com.wisecoders.dbs.data.filter.FilterPattern;
import com.wisecoders.dbs.data.filter.Filterable;
import com.wisecoders.dbs.data.filter.Filters;
import com.wisecoders.dbs.data.groovy.Groovy;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import groovy.lang.Binding;
import groovy.lang.Script;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Callback;

public class CsvModel implements Filterable {
  public static final String a = "\007";
  
  public final List b = new ArrayList();
  
  public final ObservableList c = FXCollections.observableArrayList();
  
  public final ObservableList d = FXCollections.observableArrayList();
  
  private final HashMap e = new HashMap<>();
  
  private final HashMap f = new HashMap<>();
  
  private static final String[] g = new String[0];
  
  private final LinkedHashMap h = new LinkedHashMap<>();
  
  public String[] a(int paramInt) {
    return this.d.contains(Integer.valueOf(paramInt)) ? c(((Integer)this.d.get(paramInt)).intValue()) : g;
  }
  
  public String[] b(int paramInt) {
    byte[] arrayOfByte = (byte[])this.c.get(paramInt);
    return (new String(arrayOfByte)).split("\007", -1);
  }
  
  public String[] c(int paramInt) {
    if (this.h.containsKey(Integer.valueOf(paramInt)))
      return (String[])this.h.get(Integer.valueOf(paramInt)); 
    if (paramInt > -1 && paramInt < this.c.size()) {
      String[] arrayOfString = b(paramInt);
      if (this.h.size() > 300)
        this.h.remove(this.h.keySet().iterator().next()); 
      this.h.put(Integer.valueOf(paramInt), arrayOfString);
      return arrayOfString;
    } 
    return g;
  }
  
  public void a(int paramInt, String[] paramArrayOfString) {
    if (paramInt < this.c.size())
      this.c.set(paramInt, String.join("\007", (CharSequence[])paramArrayOfString).getBytes()); 
  }
  
  public CsvColumn b(String paramString) {
    for (byte b = 0; b < this.c.size(); b++) {
      String[] arrayOfString1 = b(b);
      String[] arrayOfString2 = new String[arrayOfString1.length + 1];
      System.arraycopy(arrayOfString1, 0, arrayOfString2, 0, arrayOfString1.length);
      arrayOfString2[arrayOfString2.length - 1] = paramString;
      a(b, arrayOfString2);
    } 
    this.h.clear();
    CsvColumn csvColumn = new CsvColumn("New Column", this.b.size());
    this.b.add(csvColumn);
    return csvColumn;
  }
  
  public void a(CsvColumn paramCsvColumn, Boolean paramBoolean) {
    this.f.clear();
    if (paramBoolean != null)
      this.f.put(paramCsvColumn, paramBoolean); 
  }
  
  public String a(CsvColumn paramCsvColumn, int paramInt) {
    if (paramInt < this.d.size())
      return b(paramCsvColumn, ((Integer)this.d.get(paramInt)).intValue()); 
    return null;
  }
  
  public String b(CsvColumn paramCsvColumn, int paramInt) {
    String[] arrayOfString = c(paramInt);
    if (arrayOfString != null && paramCsvColumn.a() < arrayOfString.length)
      return arrayOfString[paramCsvColumn.a()]; 
    return null;
  }
  
  private String e() {
    if (!this.e.isEmpty()) {
      StringBuilder stringBuilder = new StringBuilder();
      for (CsvColumn csvColumn : this.e.keySet()) {
        String str = (String)this.e.get(csvColumn);
        FilterPattern filterPattern = Filters.a(csvColumn, str);
        if (filterPattern != null) {
          if (stringBuilder.length() > 0)
            stringBuilder.append(" && "); 
          stringBuilder.append(filterPattern.a("Groovy", str, csvColumn.e()));
        } 
      } 
      return stringBuilder.toString();
    } 
    return null;
  }
  
  private CsvColumn f() {
    return this.f.isEmpty() ? null : this.f.keySet().stream().findFirst().get();
  }
  
  private Comparator b(List paramList) {
    CsvColumn csvColumn = f();
    if (csvColumn != null) {
      boolean bool = ((Boolean)this.f.get(csvColumn)).booleanValue() ? true : true;
      return (paramInteger1, paramInteger2) -> {
          Double double_1 = (Double)paramList.get(paramInteger1.intValue());
          Double double_2 = (Double)paramList.get(paramInteger2.intValue());
          return (double_1 == null) ? -1 : ((double_2 == null) ? 1 : ((double_1 instanceof Double && double_2 instanceof Double) ? (paramInt * Double.compare(((Double)double_1).doubleValue(), ((Double)double_2).doubleValue())) : (
            
            (double_1 instanceof Long && double_2 instanceof Long) ? (paramInt * Long.compare(((Long)double_1).longValue(), ((Long)double_2).longValue())) : (paramInt * StringUtil.compare(String.valueOf(double_1), String.valueOf(double_2))))));
        };
    } 
    return null;
  }
  
  private Script a(HashMap paramHashMap) {
    String str = e();
    if (str != null) {
      Log.c("Filter CSV: " + str);
      try {
        Binding binding = new Binding(paramHashMap);
        return Groovy.a.b(binding, str);
      } catch (Throwable throwable) {
        Log.b(throwable);
      } 
    } 
    return null;
  }
  
  public void a() {
    this.d.setAll(a((Callback)null));
  }
  
  public List a(Callback paramCallback) {
    HashMap<Object, Object> hashMap = new HashMap<>();
    ArrayList<Object> arrayList = new ArrayList(this.d.size());
    Comparator<? super Integer> comparator = b(arrayList);
    ArrayList<Integer> arrayList1 = new ArrayList();
    Script script = a(hashMap);
    CsvColumn csvColumn = f();
    for (byte b = 0; b < this.c.size(); b++) {
      hashMap.clear();
      if (csvColumn != null) {
        String[] arrayOfString = c(b);
        arrayList.add((csvColumn.a() < arrayOfString.length) ? csvColumn.c(arrayOfString[csvColumn.a()]) : "");
      } 
      if (script == null) {
        arrayList1.add(Integer.valueOf(b));
      } else {
        for (CsvColumn csvColumn1 : this.e.keySet()) {
          String[] arrayOfString = c(b);
          if (csvColumn1.a() < arrayOfString.length)
            hashMap.put(csvColumn1.e(), csvColumn1.c(arrayOfString[csvColumn1.a()])); 
        } 
        try {
          Object object = script.run();
          if (object instanceof Boolean && ((Boolean)object).booleanValue())
            arrayList1.add(Integer.valueOf(b)); 
        } catch (Throwable throwable) {
          Log.b(throwable);
        } finally {
          hashMap.clear();
        } 
      } 
      if (paramCallback != null && b % 1000 == 0)
        paramCallback.call(Double.valueOf(b * 1.0D / this.c.size())); 
    } 
    this.h.clear();
    if (comparator != null)
      arrayList1.sort(comparator); 
    return arrayList1;
  }
  
  public void a(CsvColumn paramCsvColumn, String paramString) {
    for (Iterator<Integer> iterator = this.d.iterator(); iterator.hasNext(); ) {
      int i = ((Integer)iterator.next()).intValue();
      String[] arrayOfString = b(i);
      if (paramCsvColumn.a() < arrayOfString.length)
        arrayOfString[paramCsvColumn.a()] = paramString; 
      a(i, arrayOfString);
    } 
    paramCsvColumn.a(paramString);
    this.h.clear();
  }
  
  public void a(int paramInt, CsvColumn paramCsvColumn, String paramString) {
    if (paramInt < this.c.size()) {
      String[] arrayOfString = b(paramInt);
      if (paramCsvColumn.a() < arrayOfString.length)
        arrayOfString[paramCsvColumn.a()] = paramString; 
      a(paramInt, arrayOfString);
      paramCsvColumn.a(paramString);
      this.h.clear();
    } 
  }
  
  public void d(int paramInt) {
    this.c.remove(paramInt);
    this.h.clear();
    ArrayList<Integer> arrayList = new ArrayList();
    for (Integer integer : this.d) {
      if (integer.intValue() != paramInt)
        arrayList.add(Integer.valueOf((integer.intValue() > paramInt) ? (integer.intValue() - 1) : integer.intValue())); 
    } 
    this.d.setAll(arrayList);
  }
  
  public void a(int paramInt1, int paramInt2, byte[] paramArrayOfbyte) {
    this.c.add(paramInt2, paramArrayOfbyte);
    ArrayList<Integer> arrayList = new ArrayList();
    for (Integer integer : this.d)
      arrayList.add(Integer.valueOf((integer.intValue() >= paramInt2) ? (integer.intValue() + 1) : integer.intValue())); 
    arrayList.add(paramInt1, Integer.valueOf(paramInt2));
    this.d.setAll(arrayList);
    this.h.clear();
  }
  
  public void b() {
    e(this.d.size() - 1);
  }
  
  public void e(int paramInt) {
    String[] arrayOfString = new String[this.b.size()];
    Arrays.fill((Object[])arrayOfString, "");
    this.c.add(String.join("\007", (CharSequence[])arrayOfString).getBytes());
    this.d.add(paramInt, Integer.valueOf(this.c.size() - 1));
    this.h.clear();
  }
  
  public int f(int paramInt) {
    return ((Integer)this.d.get(paramInt)).intValue();
  }
  
  public int g(int paramInt) {
    return this.d.indexOf(Integer.valueOf(paramInt));
  }
  
  public void a(List paramList) {
    this.c.setAll(paramList);
    this.h.clear();
    a();
  }
  
  public void c() {
    this.c.clear();
    this.b.clear();
    this.f.clear();
    this.e.clear();
    this.d.clear();
  }
  
  public void h(int paramInt) {
    for (int i = this.b.size(); i < paramInt; i++)
      this.b.add(new CsvColumn("C_" + i, i)); 
  }
  
  public Object c(Attribute paramAttribute, String paramString) {
    this.e.put((CsvColumn)paramAttribute, paramString);
    return null;
  }
  
  public String c(Attribute paramAttribute) {
    return (paramAttribute != null) ? (String)this.e.get(paramAttribute) : null;
  }
  
  public void b(Attribute paramAttribute, String paramString) {
    this.e.remove(paramAttribute);
  }
  
  public void d() {
    this.e.clear();
  }
  
  public void a(CsvColumn paramCsvColumn) {
    this.e.remove(paramCsvColumn);
  }
  
  public void a(Attribute paramAttribute, boolean paramBoolean) {
    this.f.put((CsvColumn)paramAttribute, Boolean.valueOf(paramBoolean));
  }
  
  public void b(Attribute paramAttribute) {
    this.f.remove(paramAttribute);
  }
  
  public int a(Attribute paramAttribute) {
    return this.f.containsKey(paramAttribute) ? (((Boolean)this.f.get(paramAttribute)).booleanValue() ? 1 : -1) : 0;
  }
  
  public String a(String paramString) {
    return paramString;
  }
  
  public void a(int paramInt1, int paramInt2) {
    Collections.swap((List<?>)this.c, ((Integer)this.d.get(paramInt1)).intValue(), ((Integer)this.d.get(paramInt2)).intValue());
    this.h.clear();
  }
}
