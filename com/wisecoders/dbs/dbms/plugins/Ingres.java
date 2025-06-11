package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import java.sql.ResultSet;

public class Ingres extends Dbms {
  public Ingres() {
    super("Ingres");
  }
  
  public void setImportedColumnType(Column paramColumn, int paramInt1, String paramString1, int paramInt2, int paramInt3, String paramString2) {
    if ("INTEGER".equalsIgnoreCase(paramString1) && paramInt2 == 6)
      paramString1 = "smallint"; 
    super.setImportedColumnType(paramColumn, paramInt1, paramString1, paramInt2, paramInt3, paramString2);
  }
  
  public void importSchemaAdditions(Importer paramImporter, Schema paramSchema) {
    SelectStatement selectStatement = paramImporter.d.a(",SELECT object_name, long_remark FROM iidb_comments WHERE object_owner=? ", new Object[] { paramSchema.getName() });
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next()) {
        Table table = (Table)paramSchema.tables.getByName(resultSet.getString(1).trim());
        if (table != null)
          table.setComment(resultSet.getString(2)); 
      } 
      resultSet.close();
      if (selectStatement != null)
        selectStatement.close(); 
    } catch (Throwable throwable) {
      if (selectStatement != null)
        try {
          selectStatement.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
    selectStatement = paramImporter.d.a("SELECT object_name, subobject_name, long_remark FROM iidb_subcomments WHERE object_owner=? ", new Object[] { paramSchema.getName() });
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next()) {
        Table table = (Table)paramSchema.tables.getByName(resultSet.getString(1).trim());
        if (table != null) {
          String str = resultSet.getString(2);
          if (str != null) {
            Column column = (Column)table.columns.getByName(str.trim());
            column.setComment(resultSet.getString(3));
          } 
        } 
      } 
      resultSet.close();
      if (selectStatement != null)
        selectStatement.close(); 
    } catch (Throwable throwable) {
      if (selectStatement != null)
        try {
          selectStatement.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
  }
}
