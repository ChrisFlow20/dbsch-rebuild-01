package com.wisecoders.dbs.loader.model;

import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;

public class MetaLoaderProject extends Project {
  public final Table a;
  
  public static final String b = "schemaName";
  
  public static final String c = "catalogName";
  
  public static final String d = "schemaOptions";
  
  public static final String e = "schemaComment";
  
  public static final String f = "tableName";
  
  public static final String g = "tableType";
  
  public static final String h = "tableOptions";
  
  public static final String i = "tableComment";
  
  public static final String j = "columnName";
  
  public static final String k = "columnType";
  
  public static final String l = "columnLength";
  
  public static final String m = "columnDecimal";
  
  public static final String n = "columnDefault";
  
  public static final String o = "columnMandatory";
  
  public static final String p = "columnNullable";
  
  public static final String q = "columnComment";
  
  public static final String r = "columnOptions";
  
  public static final String[] s = new String[] { 
      "schemaName", "catalogName", "schemaOptions", "schemaComment", "tableName", "tableType", "tableOptions", "tableComment", "columnName", "columnType", 
      "columnLength", "columnDecimal", "columnDefault", "columnMandatory", "columnNullable", "columnComment", "columnOptions" };
  
  public MetaLoaderProject(String paramString) {
    super("MetaLoaderProject", paramString);
    this.a = a(this);
  }
  
  public static Table a(Project paramProject) {
    Schema schema = paramProject.createSchema("meta");
    Table table = schema.createTable("meta");
    for (String str : s)
      table.createColumn(str, "string"); 
    return table;
  }
}
