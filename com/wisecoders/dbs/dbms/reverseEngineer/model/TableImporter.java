package com.wisecoders.dbs.dbms.reverseEngineer.model;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Constraint;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.SqlUtil;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class TableImporter {
  public boolean a(Importer paramImporter, Schema paramSchema) {
    HashMap<Object, Object> hashMap = new HashMap<>();
    for (AbstractTable abstractTable1 : paramSchema.getEntities())
      hashMap.put(abstractTable1.getName(), abstractTable1); 
    Log.a(paramSchema, "AllColumns");
    ResultSet resultSet = paramImporter.d.a().getColumns(paramSchema.getMDCatalog(), paramSchema.getMDSchema(), null, null);
    boolean bool = false;
    AbstractTable abstractTable = null;
    Dbms dbms = Dbms.get(paramImporter.b);
    while (resultSet.next() && !paramImporter.a()) {
      String str = resultSet.getString(3);
      AbstractTable abstractTable1 = (AbstractTable)hashMap.get(str);
      if (abstractTable1 == null && str != null && str.contains(".")) {
        abstractTable1 = paramSchema.createTable(str);
        paramImporter.c.select(abstractTable1);
        hashMap.put(str, abstractTable1);
      } 
      if (abstractTable1 != null) {
        String str1 = resultSet.getString(4);
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
      Log.j();
    } 
    resultSet.close();
    return bool;
  }
  
  public void b(Importer paramImporter, Schema paramSchema) {
    if (!paramImporter.a()) {
      Dbms dbms = Dbms.get(paramImporter.b);
      Log.a(paramSchema, "ColumnsByTable");
      for (Table table : paramSchema.tables) {
        if (paramImporter.c.isSelected(table)) {
          paramImporter.a(table.getSymbolicName() + " '" + table.getSymbolicName() + "' ");
          ResultSet resultSet = paramImporter.d.a().getColumns(paramSchema.getMDCatalog(), paramSchema.getMDSchema(), table.getName(), null);
          while (resultSet.next() && !paramImporter.a()) {
            String str1 = resultSet.getString(3);
            AbstractTable abstractTable = table;
            if (str1 != null && str1.contains(".") && !abstractTable.getName().equalsIgnoreCase(str1)) {
              abstractTable = (AbstractTable)paramSchema.tables.getByName(str1);
              if (abstractTable == null) {
                abstractTable = paramSchema.createTable(str1);
                paramImporter.c.select(abstractTable);
              } 
            } 
            String str2 = resultSet.getString(4);
            Column column = (Column)abstractTable.getAttributes().getByName(str2);
            if (column == null)
              column = abstractTable.createColumn(str2, DbmsTypes.get(paramImporter.b).getDataType(resultSet.getInt(5))); 
            dbms.setImportedColumnType(column, resultSet.getInt(5), resultSet.getString(6), resultSet.getInt(7), resultSet.getInt(9), resultSet.getString(13));
            column.setComment(SqlUtil.unescapeComment(resultSet.getString(12)));
            column.setMandatory((0 == resultSet.getInt(11)));
            dbms.importColumnAdditions(column, resultSet);
            Log.j();
          } 
          resultSet.close();
          if (table.columns.isEmpty() && !paramImporter.d.a.isMongo())
            a(paramImporter.d, table); 
          paramImporter.b();
        } 
      } 
    } 
  }
  
  private void a(Envoy paramEnvoy, Table paramTable) {
    String str = paramEnvoy.e();
    Log.a(paramTable.getSchema(), "DeduceColumns");
    Timeline timeline = new Timeline(new KeyFrame[] { new KeyFrame(Duration.millis(2000.0D), paramActionEvent -> {
              Log.f("SYNC: deduce table columns canceled");
              paramEnvoy.n();
            }new javafx.animation.KeyValue[0]) });
    timeline.setCycleCount(1);
    timeline.play();
    try {
      SelectStatement selectStatement = paramEnvoy.a("SELECT * FROM " + paramTable.ref() + " WHERE 0=1 ", new Object[0]);
      try {
        ResultSet resultSet = selectStatement.j();
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        for (byte b = 1; b <= resultSetMetaData.getColumnCount(); b++) {
          String str1 = resultSetMetaData.getColumnName(b);
          Column column = (Column)paramTable.columns.getByName(str1);
          if (column == null)
            column = paramTable.createColumn(str1, DbmsTypes.get(str).getOrCreateDataType(resultSetMetaData.getColumnType(b), resultSetMetaData.getColumnTypeName(b))); 
          int i = -1, j = -1;
          try {
            i = resultSetMetaData.getPrecision(b);
            j = resultSetMetaData.getScale(b);
          } catch (Exception exception) {}
          Dbms.get(str).setImportedColumnType(column, resultSetMetaData.getColumnType(b), resultSetMetaData.getColumnTypeName(b), i, j, (String)null);
          column.setMandatory((0 == resultSetMetaData.isNullable(b)));
          b++;
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
      Log.a("Error by deduce columns reverse engineer", exception);
    } finally {
      timeline.stop();
    } 
  }
  
  public static void a(Importer paramImporter, Schema paramSchema, SelectStatement paramSelectStatement) {
    Log.a(paramSchema, "ConstraintsUsingQuery");
    byte b = 0;
    ResultSet resultSet = paramSelectStatement.j();
    Table table = null;
    while (resultSet.next()) {
      String str = resultSet.getString(1);
      Table table1 = paramSchema.getTable(str);
      if (table1 != null) {
        String str1 = resultSet.getString(2);
        String str2 = resultSet.getString(3);
        String str3 = (resultSet.getMetaData().getColumnCount() > 3) ? resultSet.getString(4) : null;
        if (str2 != null && !str2.isEmpty() && !str2.toLowerCase().endsWith("is not null")) {
          if ((Dbms.get(paramImporter.b)).loadConstraintRemoveBrackets.b() && str2.startsWith("(") && str2.endsWith(")"))
            str2 = str2.substring(1, str2.length() - 1); 
          if (str1 == null || str1.isEmpty())
            str1 = "cons_" + paramSchema.getName() + "_" + b; 
          Constraint constraint = table1.createConstraint(str1);
          constraint.setText(str2);
          constraint.setComment(str3);
          b++;
          if (table != table1) {
            paramImporter.a(table1.getSymbolicName() + " '" + table1.getSymbolicName() + "' constraints");
            paramImporter.b();
            table = table1;
          } 
        } 
      } 
      Log.j();
    } 
    resultSet.close();
  }
  
  public static void b(Importer paramImporter, Schema paramSchema, SelectStatement paramSelectStatement) {
    Log.a(paramSchema, "TableRowCount");
    ResultSet resultSet = paramSelectStatement.j();
    while (resultSet.next()) {
      String str = resultSet.getString(1);
      Table table = paramSchema.getTable(str);
      if (table != null) {
        long l = resultSet.getLong(2);
        table.setRowCount(l);
      } 
      Log.j();
    } 
    resultSet.close();
  }
  
  public static void c(Importer paramImporter, Schema paramSchema, SelectStatement paramSelectStatement) {
    Log.a(paramSchema, "SchemaComments");
    ResultSet resultSet = paramSelectStatement.j();
    while (resultSet.next()) {
      String str = resultSet.getString(1);
      if (str != null)
        paramSchema.setComment(str); 
      Log.j();
    } 
    resultSet.close();
  }
}
