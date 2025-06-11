package com.wisecoders.dbs.loader.model;

import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;

public class MetaLoaderNamingDictionary extends Project {
  public final Table a;
  
  public static final String b = "logicalName";
  
  public static final String c = "physicalName";
  
  public static final String[] d = new String[] { "logicalName", "physicalName" };
  
  public MetaLoaderNamingDictionary() {
    super("NamingDictionaryProject", "MySql");
    Schema schema = createSchema("meta");
    this.a = schema.createTable("meta");
    for (String str : d)
      this.a.createColumn(str, "string"); 
  }
}
