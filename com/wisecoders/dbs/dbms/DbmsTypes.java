package com.wisecoders.dbs.dbms;

import com.wisecoders.dbs.dbms.store.DataTypeLoader;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.DataTypeUtil;
import com.wisecoders.dbs.schema.JavaType;
import com.wisecoders.dbs.schema.Precision;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@DoNotObfuscate
public class DbmsTypes {
  public final String dbId;
  
  public static final String VERSION = "1.0";
  
  public final ObservableList dataTypes;
  
  private final LinkedHashMap a;
  
  private boolean b;
  
  public DbmsTypes(String paramString) {
    this.dataTypes = FXCollections.observableArrayList();
    this.a = new LinkedHashMap<>();
    this.b = true;
    this.dbId = paramString;
  }
  
  public final DataType getType(String paramString) {
    DataType dataType = (DataType)this.a.get(paramString.toLowerCase());
    if (dataType != null)
      return dataType; 
    for (DataType dataType1 : this.a.values()) {
      if (dataType1.getAliases() != null)
        for (String str1 : dataType1.getAliases().split(",")) {
          if (str1.equalsIgnoreCase(paramString))
            return dataType1; 
        }  
    } 
    for (JavaType javaType : JavaType.KNOWN) {
      if (javaType.name.equalsIgnoreCase(paramString))
        for (DataType dataType1 : this.dataTypes) {
          if (dataType1.getJavaType() == javaType.javaType)
            return dataType1; 
        }  
    } 
    String str = paramString.toLowerCase();
    if (str.startsWith("set") || str.startsWith("frozen") || str.startsWith("list") || str.startsWith("array"))
      return getOrCreateDataType(4999545, paramString); 
    if (str.startsWith("map"))
      return getOrCreateDataType(4999544, paramString); 
    return getDataType(12);
  }
  
  public final DataType getSimilarType(DataType paramDataType) {
    DataType dataType = (DataType)this.a.get(paramDataType.getName().toLowerCase());
    if (dataType != null)
      return dataType; 
    for (DataType dataType1 : this.a.values()) {
      if (dataType1.getAliases() != null)
        for (String str : dataType1.getAliases().split(",")) {
          if (str.equalsIgnoreCase(paramDataType.getName()))
            return dataType1; 
        }  
    } 
    for (DataType dataType1 : this.a.values()) {
      if (dataType1.getJavaType() == paramDataType.getJavaType() && dataType1.getPrecision() == paramDataType.getPrecision())
        return dataType1; 
    } 
    return null;
  }
  
  public void sortAlphabetical() {
    Collections.sort((List<?>)this.dataTypes, Comparator.comparing(AbstractUnit::getName));
  }
  
  public final DataType getOrCreateDataType(int paramInt, String paramString) {
    return getOrCreateDataType(paramInt, paramString, Precision.NONE);
  }
  
  public final DataType getOrCreateDataType(int paramInt, String paramString, Precision paramPrecision) {
    DataType dataType = null;
    if (StringUtil.isEmptyTrim(paramString)) {
      for (DataType dataType1 : this.dataTypes) {
        if (dataType1.getJavaType() == paramInt) {
          if (dataType == null) {
            dataType = dataType1;
            continue;
          } 
          if (dataType1.getPrecision() == paramPrecision)
            dataType = dataType1; 
        } 
      } 
      if (dataType == null)
        dataType = getDataType(12); 
    } else {
      dataType = (DataType)this.a.get(paramString.toLowerCase());
    } 
    if (dataType == null)
      for (DataType dataType1 : this.a.values()) {
        if (dataType1.getAliases() != null)
          for (String str : dataType1.getAliases().split(",")) {
            if (str.equalsIgnoreCase(paramString))
              return dataType1; 
          }  
      }  
    if (dataType == null) {
      dataType = createType(paramInt, paramString, paramPrecision);
      Log.d("DBMS: data type '" + paramString + "' javaType=" + paramInt + " precision=" + String.valueOf(paramPrecision) + " is new.");
    } 
    return dataType;
  }
  
  public final ObservableList getDataTypes() {
    if (this.dataTypes.isEmpty()) {
      createType(4, "INTEGER", Precision.NONE);
      createType(2, "NUMERIC", Precision.DECIMAL);
      createType(12, "VARCHAR", Precision.LENGTH);
      createType(91, "DATE", Precision.NONE);
      createType(93, "TIMESTAMP", Precision.NONE);
      this.b = true;
    } 
    return this.dataTypes;
  }
  
  public final DataType createType(int paramInt, String paramString, Precision paramPrecision) {
    return createType(paramInt, paramString, null, paramPrecision);
  }
  
  public final DataType createType(int paramInt, String paramString1, String paramString2, Precision paramPrecision) {
    if (paramString1 == null)
      throw new NullPointerException("DataTypeName cannot be null"); 
    DataType dataType = (DataType)this.a.get(paramString1.toLowerCase());
    if (dataType == null) {
      dataType = new DataType(this.dbId, paramString1);
      dataType.setJavaType(paramInt);
      dataType.setPrecision(paramPrecision);
      dataType.setAliases(paramString2);
      this.a.put(paramString1.toLowerCase(), dataType);
      if (paramString2 != null)
        this.a.put(paramString2.toLowerCase(), dataType); 
      this.dataTypes.add(dataType);
      this.b = false;
    } 
    return dataType;
  }
  
  public DataType getDataType(int paramInt) {
    DataType dataType = a(paramInt);
    if (paramInt == 8 && (dataType = a(2)) != null)
      return dataType; 
    if (paramInt == 8 && (dataType = a(3)) != null)
      return dataType; 
    if (paramInt == 16 && (dataType = a(-7)) != null)
      return dataType; 
    if (paramInt == 16 && (dataType = a(-6)) != null)
      return dataType; 
    if (paramInt == 16 && (dataType = a(5)) != null)
      return dataType; 
    return (dataType != null) ? dataType : createType(paramInt, DataTypeUtil.getTypeName(paramInt), Precision.NONE);
  }
  
  private DataType a(int paramInt) {
    DataType dataType = null;
    for (DataType dataType1 : this.dataTypes) {
      boolean bool = ((paramInt == 12 && ("varchar".equalsIgnoreCase(dataType1.getName()) || "varchar2".equalsIgnoreCase(dataType1.getName()))) || (paramInt == 4 && ("int".equalsIgnoreCase(dataType1.getName()) || "integer".equalsIgnoreCase(dataType1.getName()))) || (paramInt == 8 && ("decimal".equalsIgnoreCase(dataType1.getName()) || "numeric".equalsIgnoreCase(dataType1.getName()))) || (paramInt == 91 && "date".equalsIgnoreCase(dataType1.getName()))) ? true : false;
      if (dataType1.getJavaType() == paramInt && (dataType == null || bool))
        dataType = dataType1; 
    } 
    return dataType;
  }
  
  public void deleteDataType(DataType paramDataType) {
    this.a.remove(String.valueOf(paramDataType).toLowerCase());
    this.dataTypes.remove(paramDataType);
  }
  
  public boolean isLearnDataTypes() {
    return this.b;
  }
  
  private static final Map c = new LinkedHashMap<>();
  
  public static DbmsTypes get(String paramString) {
    if (StringUtil.isEmptyTrim(paramString))
      throw new NullPointerException("Have asked for Rdb with empty name."); 
    DbmsTypes dbmsTypes = (DbmsTypes)c.get(paramString);
    if (dbmsTypes == null) {
      dbmsTypes = new DbmsTypes(paramString);
      new DataTypeLoader(dbmsTypes);
      c.put(paramString, dbmsTypes);
    } 
    return dbmsTypes;
  }
}
