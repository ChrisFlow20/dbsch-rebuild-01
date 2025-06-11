package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.Dbms$ParamSource;
import com.wisecoders.dbs.dbms.Dbms$SchemaCatalogEntry;
import com.wisecoders.dbs.dbms.JdbcUrlParam;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.reverseEngineer.model.StructureImporter;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVFiles extends Dbms {
  public static final String a = "CSVFiles";
  
  public CSVFiles() {
    super("CSVFiles");
  }
  
  public Dbms$ParamSource getParamSource(JdbcUrlParam paramJdbcUrlParam) {
    return (paramJdbcUrlParam == JdbcUrlParam.b || paramJdbcUrlParam == JdbcUrlParam.c || paramJdbcUrlParam == JdbcUrlParam.d) ? Dbms$ParamSource.a : Dbms$ParamSource.c;
  }
  
  public List listParam(Envoy paramEnvoy, JdbcUrlParam paramJdbcUrlParam) {
    if (paramJdbcUrlParam == JdbcUrlParam.b)
      return Arrays.asList(new String[] { 
            "DEFAULT", "EXCEL", "MYSQL", "RFC4180", "INFORMIX_UNLOAD", "INFORMIX_UNLOAD_CSV", "ORACLE", "MONGODB_CSV", "MONGODB_TSV", "POSTGRESQL_CSV", 
            "POSTGRESQL_TEXT", "TDF", "SEMICOLON" }); 
    if (paramJdbcUrlParam == JdbcUrlParam.c) {
      ArrayList<String> arrayList = new ArrayList();
      arrayList.add("UTF-8");
      arrayList.add("ISO 8859-1");
      arrayList.addAll(Charset.availableCharsets().keySet());
      return arrayList;
    } 
    if (paramJdbcUrlParam == JdbcUrlParam.d)
      return Arrays.asList(new String[] { "true", "false" }); 
    return null;
  }
  
  public List listSchemasAndCatalogs(Envoy paramEnvoy) {
    List list = super.listSchemasAndCatalogs(paramEnvoy);
    list.removeIf(paramDbms$SchemaCatalogEntry -> "INFORMATION_SCHEMA".equals(paramDbms$SchemaCatalogEntry.a));
    return list;
  }
  
  public void listTableAndViewsNames(StructureImporter paramStructureImporter, Schema paramSchema) {
    super.listTableAndViewsNames(paramStructureImporter, paramSchema);
    for (Table table : paramSchema.tables) {
      if (table.getName().toLowerCase().startsWith("dbs_meta_"))
        table.markForDeletion(); 
    } 
    paramSchema.refresh();
  }
}
