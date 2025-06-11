package com.wisecoders.dbs.loader.model;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.loader.engine.LoaderConsumer;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.StringUtil;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LoaderMeta implements LoaderConsumer {
  public final ObservableList a = FXCollections.observableArrayList();
  
  public final ObservableList b = FXCollections.observableArrayList();
  
  private int e = 0;
  
  private int f = 0;
  
  public final String c;
  
  public final Table d;
  
  private boolean g;
  
  private SimpleDateFormat h;
  
  public LoaderMeta(Table paramTable, boolean paramBoolean) {
    this.d = paramTable;
    this.g = paramBoolean;
    this.c = paramTable.schema.project.getDbId();
  }
  
  public void consumeRecord(Map paramMap) {
    ArrayList arrayList = new ArrayList((Collection<?>)this.a);
    for (String str : paramMap.keySet()) {
      Object object = paramMap.get(str);
      LoaderColumn loaderColumn = a(str);
      loaderColumn.a(object, this.h);
      if (loaderColumn.d() == null && object != null)
        loaderColumn.b(StringUtil.cutOfWithDots(String.valueOf(object), 80)); 
      arrayList.remove(loaderColumn);
    } 
    for (LoaderColumn loaderColumn : arrayList)
      loaderColumn.a(false); 
  }
  
  public void a() {
    for (LoaderColumn loaderColumn : this.a)
      loaderColumn.b(); 
    this.b.clear();
    this.a.clear();
    this.e = 0;
    this.f = 0;
    if (this.g) {
      for (Column column : this.d.columns)
        column.markForDeletion(); 
      this.d.refresh();
    } 
  }
  
  public void b() {
    this.b.clear();
    this.e = 0;
    this.f = 0;
  }
  
  public void a(int paramInt) {
    this.f += paramInt;
  }
  
  public int c() {
    return this.f;
  }
  
  public int d() {
    return this.e;
  }
  
  private LoaderColumn a(String paramString) {
    for (LoaderColumn loaderColumn1 : this.a) {
      if (loaderColumn1.a.equals(paramString))
        return loaderColumn1; 
    } 
    LoaderColumn loaderColumn = new LoaderColumn(this.d, paramString);
    if (this.g) {
      String str = Dbms.get(this.d).toDefaultCases(paramString.trim());
      Column column = (Column)this.d.columns.getByName(str);
      if (column == null)
        column = this.d.createColumn(str, DbmsTypes.get(this.c).getDataType(12)); 
      loaderColumn.a(column);
    } else {
      loaderColumn.e();
    } 
    this.a.add(loaderColumn);
    i();
    return loaderColumn;
  }
  
  public void e() {
    for (LoaderColumn loaderColumn : this.a)
      loaderColumn.f(); 
  }
  
  public void a(SimpleDateFormat paramSimpleDateFormat) {
    this.h = paramSimpleDateFormat;
  }
  
  public SimpleDateFormat f() {
    return this.h;
  }
  
  public void a(boolean paramBoolean) {
    this.g = paramBoolean;
  }
  
  public boolean g() {
    return this.g;
  }
  
  public void h() {
    if (this.g)
      for (LoaderColumn loaderColumn : this.a)
        loaderColumn.a(this.c);  
  }
  
  public void a(int paramInt, String paramString, Map paramMap) {
    this.e++;
    LoaderError loaderError = null;
    for (LoaderError loaderError1 : this.b) {
      if (loaderError1.a(paramString))
        loaderError = loaderError1; 
    } 
    if (loaderError == null && this.b.size() < 1000)
      this.b.add(loaderError = new LoaderError(paramString)); 
    if (loaderError != null)
      loaderError.a(paramInt, paramMap); 
  }
  
  public void i() {
    for (LoaderColumn loaderColumn : this.a) {
      loaderColumn.b(false);
      for (LoaderColumn loaderColumn1 : this.a) {
        if (loaderColumn != loaderColumn1 && !loaderColumn.h() && !loaderColumn1.h() && loaderColumn.a() != null && loaderColumn.a() == loaderColumn1.a())
          loaderColumn.b(true); 
      } 
    } 
  }
  
  public String a(Map paramMap, String paramString) {
    Object object = null;
    for (LoaderColumn loaderColumn : this.a) {
      if (loaderColumn.a() != null && !loaderColumn.h() && paramString.equals(loaderColumn.a().getName()))
        object = paramMap.get(loaderColumn.a); 
    } 
    return (object != null) ? object.toString() : null;
  }
}
