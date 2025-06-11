package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.sys.DoNotObfuscate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@DoNotObfuscate
public class JavaType {
  public static final ObservableList KNOWN = FXCollections.observableArrayList();
  
  public final int javaType;
  
  public final Precision precision;
  
  public final String name;
  
  static {
    KNOWN.add(new JavaType("ARRAY", 2003, Precision.NONE));
    KNOWN.add(new JavaType("BIGINT", -5, Precision.PRECISION));
    KNOWN.add(new JavaType("BINARY", -2, Precision.NONE));
    KNOWN.add(new JavaType("BIT", -7, Precision.NONE));
    KNOWN.add(new JavaType("BLOB", 2004, Precision.NONE));
    KNOWN.add(new JavaType("BOOLEAN", 16, Precision.NONE));
    KNOWN.add(new JavaType("CHAR", 1, Precision.LENGTH));
    KNOWN.add(new JavaType("CLOB", 2005, Precision.NONE));
    KNOWN.add(new JavaType("DATALINK", 70, Precision.NONE));
    KNOWN.add(new JavaType("DATE", 91, Precision.NONE));
    KNOWN.add(new JavaType("DECIMAL", 3, Precision.PRECISION));
    KNOWN.add(new JavaType("DISTINCT", 2001, Precision.NONE));
    KNOWN.add(new JavaType("DOUBLE", 8, Precision.DECIMAL));
    KNOWN.add(new JavaType("FLOAT", 6, Precision.DECIMAL));
    KNOWN.add(new JavaType("INTEGER", 4, Precision.PRECISION));
    KNOWN.add(new JavaType("JAVA_OBJECT", 2000, Precision.NONE));
    KNOWN.add(new JavaType("LONGVARBINARY", -4, Precision.LENGTH));
    KNOWN.add(new JavaType("LONGVARCHAR", -1, Precision.LENGTH));
    KNOWN.add(new JavaType("NULL", 0, Precision.NONE));
    KNOWN.add(new JavaType("NUMERIC", 2, Precision.DECIMAL));
    KNOWN.add(new JavaType("REAL", 7, Precision.DECIMAL));
    KNOWN.add(new JavaType("REF", 2006, Precision.NONE));
    KNOWN.add(new JavaType("SMALLINT", 5, Precision.PRECISION));
    KNOWN.add(new JavaType("STRUCT", 2002, Precision.NONE));
    KNOWN.add(new JavaType("TIME", 92, Precision.NONE));
    KNOWN.add(new JavaType("TIMESTAMP", 93, Precision.PRECISION));
    KNOWN.add(new JavaType("TINYINT", -6, Precision.NONE));
    KNOWN.add(new JavaType("VARBINARY", -3, Precision.NONE));
    KNOWN.add(new JavaType("VARCHAR", 12, Precision.LENGTH));
    KNOWN.add(new JavaType("OTHER", 1111, Precision.NONE));
  }
  
  private JavaType(String paramString, int paramInt, Precision paramPrecision) {
    this.name = paramString;
    this.javaType = paramInt;
    this.precision = paramPrecision;
  }
  
  public static JavaType getFromDataType(DataType paramDataType) {
    for (JavaType javaType1 : KNOWN) {
      if (javaType1.javaType == paramDataType.getJavaType())
        return javaType1; 
    } 
    JavaType javaType = new JavaType(paramDataType.getName(), paramDataType.getJavaType(), paramDataType.getPrecision());
    KNOWN.add(javaType);
    return javaType;
  }
  
  public String toString() {
    return this.name;
  }
}
