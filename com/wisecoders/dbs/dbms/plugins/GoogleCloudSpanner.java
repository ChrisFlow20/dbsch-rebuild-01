package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.Log;
import java.sql.ResultSet;

public class GoogleCloudSpanner extends Dbms {
  public static final String a = "GoogleCloudSpanner";
  
  public GoogleCloudSpanner() {
    super("GoogleCloudSpanner");
  }
  
  public GoogleCloudSpanner(String paramString) {
    super(paramString);
  }
  
  public void importSchemaAdditions(Importer paramImporter, Schema paramSchema) {
    SelectStatement selectStatement = paramImporter.d.a("SELECT table_name, parent_table_name, on_delete_action, table_type, spanner_state, interleave_type, row_deletion_policy_expression FROM information_schema.tables WHERE table_schema=?", new Object[] { paramSchema
          
          .getName() });
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next()) {
        Table table = paramSchema.getTable(resultSet.getString(1));
        if (table != null) {
          StringBuilder stringBuilder = new StringBuilder();
          if (resultSet.getString(6) != null) {
            stringBuilder.append("INTERLEAVE ").append(resultSet.getString(6)).append(" ");
            if (resultSet.getString(2) != null)
              stringBuilder.append(resultSet.getString(2)).append(" "); 
          } 
          if (resultSet.getString(3) != null)
            stringBuilder.append("ON DELETE ").append(resultSet.getString(3)); 
          table.setOptions(stringBuilder.toString());
        } 
      } 
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
    selectStatement = paramImporter.d.a("SELECT table_name, column_name, column_default, is_generated, generation_expression, is_stored FROM information_schema.columns WHERE table_schema=?", new Object[] { paramSchema
          .getName() });
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next()) {
        Table table = paramSchema.getTable(resultSet.getString(1));
        if (table != null && 
          resultSet.getString(2) != null) {
          Column column = table.getColumnByNameOrPath(resultSet.getString(2));
          if (column != null) {
            if (resultSet.getString(3) != null)
              column.setDefaultValue(resultSet.getString(3)); 
            if ("ALWAYS".equalsIgnoreCase(resultSet.getString(4)))
              column.setOptions("AS ( " + resultSet.getString(5) + ") " + ("YES".equalsIgnoreCase(resultSet.getString(6)) ? "STORED" : "")); 
          } 
        } 
      } 
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
  
  public void importIndexes(Importer paramImporter, Schema paramSchema) {
    Log.a(paramSchema, "Indexes");
    try {
      SelectStatement selectStatement = paramImporter.d.a("SELECT c.table_name, c.column_name, c.index_name, c.index_type, c.column_name, c.ordinal_position, c.column_ordering, i.is_unique \nFROM information_schema.indexes i JOIN information_schema.index_columns c ON ( i.table_schema=c.table_schema AND i.table_name=c.table_name AND i.index_name=c.index_name ) \nWHERE i.table_schema=? AND i.spanner_is_managed=false ORDER BY c.table_name, c.index_name, IFNULL(c.ordinal_position,9999)", new Object[] { paramSchema



            
            .getName() });
      try {
        ResultSet resultSet = selectStatement.j();
        while (resultSet.next()) {
          String str1 = resultSet.getString(1);
          String str2 = resultSet.getString(2);
          Table table = paramSchema.getTable(str1);
          if (table != null) {
            Index index = (Index)table.indexes.getByName(resultSet.getString(3));
            if (index == null) {
              index = table.createIndex(resultSet.getString(3));
              switch (resultSet.getString(4)) {
                case "PRIMARY_KEY":
                  index.setType(IndexType.PRIMARY_KEY);
                  break;
                case "INDEX":
                  index.setType(resultSet.getBoolean(8) ? IndexType.UNIQUE_INDEX : IndexType.NORMAL);
                  break;
              } 
            } 
            Column column = table.getColumnByNameOrPath(str2);
            if (column != null) {
              index.columns.add(column);
              if (resultSet.getString(6) == null)
                index.setColumnOptions(column, "STORING"); 
            } 
          } 
          Log.j();
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
    } catch (Exception exception) {
      Log.b(exception);
      super.importIndexes(paramImporter, paramSchema);
    } 
  }
}
