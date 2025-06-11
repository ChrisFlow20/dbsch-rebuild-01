package com.wisecoders.dbs.schema;

import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.UniqueArrayList;
import java.util.HashMap;
import java.util.List;

@DoNotObfuscate
public class SchemaMapping {
  private final UniqueArrayList a = new UniqueArrayList();
  
  private final HashMap b = new HashMap<>();
  
  public void setRemoteSchemas(List paramList) {
    this.a.clear();
    for (Schema schema : paramList)
      this.a.add(schema.getNameWithCatalog()); 
  }
  
  public void clearRemoteSchemas() {
    this.a.clear();
  }
  
  public void addRemoteSchema(String paramString) {
    this.a.add(paramString);
  }
  
  public boolean needEdit(Project paramProject) {
    for (Schema schema : paramProject.schemas) {
      String str = schema.getNameWithCatalog();
      if (!schema.isVirtual() && !this.b.containsKey(str) && !this.a.contains(str))
        return true; 
    } 
    return false;
  }
  
  public void setMapping(Schema paramSchema, String paramString) {
    for (String str : this.b.keySet()) {
      if (paramString.equals(this.b.get(str)))
        this.b.remove(str); 
    } 
    if (paramString != null)
      this.b.put(paramSchema.getNameWithCatalog(), paramString); 
  }
  
  public boolean localSchemaHasCustomMapping(Schema paramSchema) {
    return this.b.containsKey(paramSchema.getNameWithCatalog());
  }
  
  public String getRemoteSchemaName(Schema paramSchema) {
    if (this.b.containsKey(paramSchema.getNameWithCatalog()))
      return (String)this.b.get(paramSchema.getNameWithCatalog()); 
    if (this.a.contains(paramSchema.getNameWithCatalog()))
      return paramSchema.getNameWithCatalog(); 
    return null;
  }
  
  public Schema getLocalSchema(Project paramProject, String paramString) {
    for (Schema schema : paramProject.schemas) {
      if (paramString.equalsIgnoreCase((String)this.b.get(schema.getNameWithCatalog())))
        return schema; 
    } 
    return paramProject.getSchema(paramString);
  }
  
  public boolean isRemoteSchemaMapped(Project paramProject, Schema paramSchema) {
    return (paramProject.getSchema(paramSchema.getCatalogName(), paramSchema.getName()) != null || this.b
      .containsValue(paramSchema.getNameWithCatalog()));
  }
  
  public List getRemoteSchemaNames() {
    return this.a;
  }
  
  public void loadFromString(String paramString) {
    if (StringUtil.isFilledTrim(paramString)) {
      this.b.clear();
      this.a.clear();
      for (String str : paramString.split(";")) {
        String[] arrayOfString = str.split(":");
        if (arrayOfString.length == 2) {
          String str1 = arrayOfString[0];
          String str2 = arrayOfString[1];
          if (StringUtil.isFilledTrim(str1) && StringUtil.isFilledTrim(str2)) {
            this.a.add(str2);
            this.b.put(str1, str2);
          } 
        } 
      } 
    } 
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    for (String str : this.b.keySet()) {
      if (!stringBuilder.isEmpty())
        stringBuilder.append(';'); 
      stringBuilder.append(str).append(':').append((String)this.b.get(str));
    } 
    return stringBuilder.toString();
  }
}
