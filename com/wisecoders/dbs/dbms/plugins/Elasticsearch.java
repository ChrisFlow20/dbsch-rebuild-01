package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.Dbms$SchemaCatalogEntry;
import com.wisecoders.dbs.dbms.Dbms$TableCache;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.SqlUtil;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Elasticsearch extends Dbms {
  public static final String a = "Elasticsearch";
  
  public Elasticsearch() {
    super("Elasticsearch");
  }
  
  public void learnDataTypes(Envoy paramEnvoy) {}
  
  public List listSchemasAndCatalogs(Envoy paramEnvoy) {
    DatabaseMetaData databaseMetaData = paramEnvoy.a();
    ResultSet resultSet = databaseMetaData.getCatalogs();
    ArrayList<Dbms$SchemaCatalogEntry> arrayList = new ArrayList();
    if (resultSet != null) {
      while (resultSet.next())
        arrayList.add(new Dbms$SchemaCatalogEntry(resultSet.getString(1), null)); 
      resultSet.close();
    } 
    return arrayList;
  }
  
  public void importColumns(Importer paramImporter, Schema paramSchema) {
    Dbms$TableCache dbms$TableCache = new Dbms$TableCache(paramSchema);
    Log.a(paramSchema, "AllColumns");
    ResultSet resultSet = paramImporter.d.a().getColumns(paramSchema.getMDCatalog(), paramSchema.getMDSchema(), null, null);
    boolean bool = false;
    AbstractTable abstractTable = null;
    Dbms dbms = Dbms.get(paramImporter.b);
    while (resultSet.next() && !paramImporter.a()) {
      String str = resultSet.getString(3);
      AbstractTable abstractTable1 = dbms$TableCache.a(str);
      if (abstractTable1 != null) {
        String str1 = resultSet.getString(4);
        if (!str1.endsWith(".keyword")) {
          int i;
          if ((i = str1.indexOf(".")) > -1)
            abstractTable1.createColumn(str1.substring(0, i), DbmsTypes.get("Elasticsearch").getDataType(4999545)); 
          Column column = (Column)abstractTable1.getAttributes().getByName(str1);
          if (column == null)
            column = abstractTable1.createColumn(str1, DbmsTypes.get(paramImporter.b).getDataType(resultSet.getInt(5))); 
          dbms.setImportedColumnType(column, resultSet.getInt(5), resultSet.getString(6), resultSet.getInt(7), resultSet.getInt(9), resultSet.getString(13));
          column.setMandatory((0 == resultSet.getInt(11)));
          column.setComment(SqlUtil.unescapeComment(resultSet.getString(12)));
          Log.d("Import column " + String.valueOf(abstractTable1) + "." + str1 + " javaType:" + resultSet.getInt(5) + " type:" + resultSet.getString(6) + "(" + resultSet.getInt(7) + "," + resultSet.getInt(9) + ")");
          dbms.importColumnAdditions(column, resultSet);
          bool = true;
          if (abstractTable1 != abstractTable) {
            paramImporter.a(abstractTable1.getSymbolicName() + " '" + abstractTable1.getSymbolicName() + "' columns all");
            paramImporter.b();
            abstractTable = abstractTable1;
          } 
        } 
      } 
      Log.j();
    } 
    resultSet.close();
    if (!bool)
      super.importColumns(paramImporter, paramSchema); 
  }
}
