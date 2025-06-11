package com.wisecoders.dbs.project.convert.model;

import com.wisecoders.dbs.dbms.sync.engine.diffs.SimpleStatement;
import com.wisecoders.dbs.dbms.sync.engine.operations.K;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.sys.StringUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DataTypeConverter extends Converter {
  public static int c = Integer.MIN_VALUE;
  
  public static int d = -2147483647;
  
  public static int e = Integer.MAX_VALUE;
  
  public DataType f;
  
  private int h = c, i = e;
  
  private int j = c;
  
  private int k = e;
  
  private String l;
  
  public final ObservableList g = FXCollections.observableArrayList();
  
  public DataTypeConverter(ConversionDictionary paramConversionDictionary) {
    super(paramConversionDictionary);
  }
  
  public void a(DataType paramDataType) {
    this.f = paramDataType;
  }
  
  public ObservableList a() {
    return this.g;
  }
  
  public String b() {
    return "Data Type";
  }
  
  public DataTypeConverterReplacement b(String paramString) {
    DataTypeConverterReplacement dataTypeConverterReplacement = new DataTypeConverterReplacement(this, paramString);
    this.g.add(dataTypeConverterReplacement);
    return dataTypeConverterReplacement;
  }
  
  public boolean c(String paramString) {
    for (DataTypeConverterReplacement dataTypeConverterReplacement : this.g) {
      if (paramString.equals(dataTypeConverterReplacement.a))
        return true; 
    } 
    return false;
  }
  
  public boolean a(AbstractUnit paramAbstractUnit) {
    if (paramAbstractUnit instanceof Column) {
      Column column = (Column)paramAbstractUnit;
      if (this.f == null || this.f != column.getDataType())
        return false; 
      if (this.f.getPrecision().usesLength() && (g() > column.getLength() || h() < column.getLength()))
        return false; 
      if (this.f.getPrecision().usesDecimal() && (i() > column.getDecimal() || j() < column.getDecimal()))
        return false; 
      String str = StringUtil.isFilledTrim(column.getTypeOptions()) ? column.getTypeOptions().trim() : null;
      return StringUtil.areEqual(f(), str);
    } 
    return false;
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    if (this.f != null) {
      if (this.f.getPattern() == null) {
        stringBuilder.append(this.f.getName());
        switch (DataTypeConverter$1.a[this.f.getPrecision().ordinal()]) {
          case 1:
            if (this.h != c || this.i != e || this.j != c || this.k != e) {
              stringBuilder.append(" (");
              if (this.h == c && this.i == e) {
                stringBuilder.append("*");
              } else {
                stringBuilder.append((this.h != c) ? ("" + this.h) : "*");
                stringBuilder.append("-");
                stringBuilder.append((this.i != e) ? ("" + this.i) : "*");
              } 
              stringBuilder.append(",");
              if (this.j == c && this.k == e) {
                stringBuilder.append("*");
              } else {
                stringBuilder.append((this.j != c) ? ("" + this.j) : "*");
                stringBuilder.append("-");
                stringBuilder.append((this.k != e) ? ("" + this.k) : "*");
              } 
              stringBuilder.append(")");
            } 
            break;
          case 2:
          case 3:
            if (this.h != c || this.i != e) {
              stringBuilder.append("(");
              stringBuilder.append((this.h != c) ? ("" + this.h) : "*");
              stringBuilder.append("-");
              stringBuilder.append((this.i != e) ? ("" + this.i) : "*");
              stringBuilder.append(")");
            } 
            break;
        } 
      } else {
        String str1 = (this.h != c || this.i != e) ? ("" + this.h + "-" + this.h) : "*";
        String str2 = (this.j != c || this.k != e) ? ("" + this.j + "-" + this.j) : "*";
        SimpleStatement simpleStatement = (new SimpleStatement(this.f.getPattern())).set(K.ap, str1).set(K.aq, str1).set(K.ar, str2).set(K.G, this.l);
        String str3 = simpleStatement.toString();
        return str3.endsWith(" )") ? (str3.substring(0, str3.length() - 2) + ")") : str3;
      } 
      if (StringUtil.isFilledTrim(this.l))
        stringBuilder.append(" ").append(this.l); 
    } 
    return stringBuilder.toString();
  }
  
  public DataType e() {
    return this.f;
  }
  
  public void d(String paramString) {
    this.l = StringUtil.isFilledTrim(paramString) ? paramString.trim() : null;
  }
  
  public String f() {
    return this.l;
  }
  
  public void a(int paramInt) {
    this.h = paramInt;
  }
  
  public int g() {
    return this.h;
  }
  
  public void b(int paramInt) {
    this.i = paramInt;
  }
  
  public int h() {
    return this.i;
  }
  
  public void c(int paramInt) {
    this.j = paramInt;
  }
  
  public int i() {
    return this.j;
  }
  
  public void d(int paramInt) {
    this.k = paramInt;
  }
  
  public int j() {
    return this.k;
  }
}
