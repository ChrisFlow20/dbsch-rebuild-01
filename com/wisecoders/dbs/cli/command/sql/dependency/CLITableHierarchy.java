package com.wisecoders.dbs.cli.command.sql.dependency;

import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.SqlUtil;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.UniqueArrayList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CLITableHierarchy {
  public final Table a;
  
  final Index b;
  
  private final List c = new ArrayList();
  
  private final List d = new ArrayList();
  
  private final HashMap e = new HashMap<>();
  
  private final List f = new ArrayList();
  
  private final HashMap g = new HashMap<>();
  
  private final HashMap h = new HashMap<>();
  
  private long a(ForeignKey paramForeignKey) {
    return this.h.containsKey(paramForeignKey) ? ((Long)this.h.get(paramForeignKey)).longValue() : 0L;
  }
  
  public CLITableHierarchy(Table paramTable) {
    this.a = paramTable;
    this.b = paramTable.getPrimaryKeyOrUniqueIndex();
    if (this.b == null)
      throw new NullPointerException("Table " + String.valueOf(paramTable) + " has not Primary Key or Unique index"); 
    a(paramTable.schema);
    j();
  }
  
  private void a(Schema paramSchema) {
    boolean bool;
    List list = StringUtil.parseLOV("");
    for (String str : list) {
      Pattern pattern = Pattern.compile(str, 2);
      for (Table table : paramSchema.tables) {
        Matcher matcher = pattern.matcher(table.getName());
        if (table != this.a && matcher.matches() && matcher.groupCount() > 0)
          this.g.put(table, matcher.group(1)); 
      } 
    } 
    do {
      bool = false;
      ForeignKey foreignKey = null;
      for (Table table : paramSchema.tables) {
        if (!this.c.contains(table) && !this.g.containsKey(table) && this.a != table) {
          ForeignKey foreignKey1 = c(table);
          if (foreignKey1 != null && (foreignKey == null || a(foreignKey1) < a(foreignKey)))
            foreignKey = foreignKey1; 
        } 
      } 
      if (foreignKey == null)
        continue; 
      this.c.add((Table)foreignKey.getEntity());
      this.d.add(foreignKey);
      bool = true;
    } while (bool);
    this.f.addAll(paramSchema.tables);
    this.f.removeAll(this.c);
    this.f.removeAll(this.g.keySet());
    this.f.remove(this.a);
  }
  
  private ForeignKey c(Table paramTable) {
    ForeignKey foreignKey = null;
    Long long_ = Long.valueOf(Long.MAX_VALUE);
    for (ForeignKey foreignKey1 : paramTable.foreignKeys) {
      if (SqlUtil.columnsAreMandatory(foreignKey1.getSourceAttributes()) && (this.c.contains(foreignKey1.getTargetEntity()) || this.a == foreignKey1.getTargetEntity())) {
        Index index = ((Table)foreignKey1.getTargetEntity()).getPrimaryKeyOrUniqueIndex();
        if (index != null && Index.attributesAreEqual(index.columns, foreignKey1.getTargetAttributes())) {
          ForeignKey foreignKey2 = a((Table)foreignKey1.getTargetEntity());
          long l = ((foreignKey2 != null) ? a(foreignKey2) : 0L) + paramTable.getRowCount();
          if (l < long_.longValue()) {
            foreignKey = foreignKey1;
            long_ = Long.valueOf(l);
          } 
        } 
      } 
    } 
    if (foreignKey != null) {
      this.h.put(foreignKey, long_);
      return foreignKey;
    } 
    return null;
  }
  
  public ForeignKey a(Table paramTable) {
    if (this.c.contains(paramTable))
      return this.d.get(this.c.indexOf(paramTable)); 
    return null;
  }
  
  private void j() {
    for (ForeignKey foreignKey : this.d) {
      List<Table> list = (List)this.e.get(foreignKey.getTargetEntity());
      if (list == null) {
        list = new ArrayList();
        this.e.put((Table)foreignKey.getTargetEntity(), list);
      } 
      list.add((Table)foreignKey.getEntity());
    } 
  }
  
  public List a() {
    return this.c;
  }
  
  public List b() {
    UniqueArrayList uniqueArrayList = new UniqueArrayList();
    uniqueArrayList.addAll(this.c);
    uniqueArrayList.addAll(this.g.keySet());
    uniqueArrayList.addAll(this.f);
    Collections.sort(uniqueArrayList, i);
    return uniqueArrayList;
  }
  
  public Map c() {
    return this.g;
  }
  
  public List d() {
    return this.f;
  }
  
  public List e() {
    return this.d;
  }
  
  public HashMap f() {
    return this.e;
  }
  
  public CLITablePath b(Table paramTable) {
    return new CLITablePath(this, paramTable);
  }
  
  public List g() {
    return this.d;
  }
  
  private static final Comparator i = new CLITableHierarchy$1();
  
  public String h() {
    return a(this.a, this.e);
  }
  
  private String a(Table paramTable, HashMap paramHashMap) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("<li><a href='#")
      .append(paramTable.getName())
      .append("'>")
      .append(paramTable.getName())
      .append("</a>\n");
    List list = (List)paramHashMap.get(paramTable);
    if (list != null) {
      stringBuilder.append("<ul>");
      for (Table table : list)
        stringBuilder.append(a(table, paramHashMap)); 
      stringBuilder.append("</ul>\n\n");
    } 
    return stringBuilder.toString();
  }
  
  public String i() {
    return a(this.a, this.e, 0);
  }
  
  private String a(Table paramTable, HashMap paramHashMap, int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b = 0; b < paramInt; b++)
      stringBuilder.append("  "); 
    stringBuilder.append(paramTable.getName()).append("\n");
    List list = (List)paramHashMap.get(paramTable);
    if (list != null) {
      for (Table table : list)
        stringBuilder.append(a(table, paramHashMap, paramInt + 1)); 
      stringBuilder.append("\n");
    } 
    return stringBuilder.toString();
  }
}
