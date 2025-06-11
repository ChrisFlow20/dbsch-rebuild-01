package com.wisecoders.dbs.project.convert.model;

import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.sync.engine.diffs.SimpleStatement;
import com.wisecoders.dbs.dbms.sync.engine.operations.K;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.sys.StringUtil;

public class DataTypeConverterReplacement extends ConverterReplacement {
  private final DataTypeConverter b;
  
  private DataType c;
  
  private int d = DataTypeConverter.d;
  
  private int e = DataTypeConverter.d;
  
  private String f;
  
  public DataTypeConverterReplacement(DataTypeConverter paramDataTypeConverter, String paramString) {
    super(paramString);
    this.b = paramDataTypeConverter;
  }
  
  public void a(AbstractUnit paramAbstractUnit) {
    Column column = (Column)paramAbstractUnit;
    column.setDataType(this.c);
    if (this.c.getPrecision().usesLength() && this.d != DataTypeConverter.d)
      column.setLength(this.d); 
    if (this.c.getPrecision().usesDecimal() && this.e != DataTypeConverter.d)
      column.setDecimal(this.e); 
    column.setTypeOptions(this.f);
  }
  
  public boolean a() {
    return (this.c != null);
  }
  
  public void a(Column paramColumn) {
    boolean bool = ConversionDictionary.a(paramColumn.getDbId()).a(paramColumn.getDataType());
    this.c = DbmsTypes.get(this.a).getSimilarType(this.b.e());
    if (this.c != null) {
      if (this.b.e().getPrecision().usesLength() && bool) {
        this.b.a(DataTypeConverter.c);
        this.b.b(DataTypeConverter.e);
      } 
      if (this.b.e().getPrecision().usesDecimal() && bool) {
        this.b.c(DataTypeConverter.c);
        this.b.d(DataTypeConverter.e);
      } 
      this.b.d(paramColumn.getTypeOptions());
    } 
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    if (this.c == null) {
      stringBuilder.append("???");
    } else {
      if (this.c.getPattern() == null) {
        stringBuilder.append(this.c.getName());
        switch (DataTypeConverterReplacement$1.a[this.c.getPrecision().ordinal()]) {
          case 1:
            stringBuilder.append("(");
            stringBuilder.append((this.d == DataTypeConverter.d) ? "..." : ("" + this.d));
            stringBuilder.append(",");
            stringBuilder.append((this.e == DataTypeConverter.d) ? "..." : ("" + this.e));
            stringBuilder.append(")");
            break;
          case 2:
          case 3:
            stringBuilder.append("(");
            stringBuilder.append((this.d == DataTypeConverter.d) ? "..." : ("" + this.d));
            stringBuilder.append(")");
            break;
        } 
      } else {
        SimpleStatement simpleStatement = (new SimpleStatement(this.c.getPattern())).set(K.ap, (this.d == DataTypeConverter.d) ? "<length>" : Integer.valueOf(this.d)).set(K.aq, (this.d == DataTypeConverter.d) ? "<length>" : ((this.d != DataType.UNSET) ? Integer.valueOf(this.d) : "")).set(K.ar, (this.e == DataTypeConverter.d) ? "<precision>" : Integer.valueOf(this.e)).set(K.G, this.f);
        String str = simpleStatement.toString();
        return str.endsWith(" )") ? (str.substring(0, str.length() - 2) + ")") : str;
      } 
      if (StringUtil.isFilledTrim(this.f))
        stringBuilder.append(" ").append(this.f); 
    } 
    return stringBuilder.toString();
  }
  
  public void a(DataType paramDataType) {
    this.c = paramDataType;
  }
  
  public DataType b() {
    return this.c;
  }
  
  public void a(int paramInt) {
    this.d = paramInt;
  }
  
  public void b(int paramInt) {
    this.e = paramInt;
  }
  
  public int c() {
    return this.d;
  }
  
  public int d() {
    return this.e;
  }
  
  public String e() {
    return this.f;
  }
  
  public void a(String paramString) {
    this.f = paramString;
  }
}
