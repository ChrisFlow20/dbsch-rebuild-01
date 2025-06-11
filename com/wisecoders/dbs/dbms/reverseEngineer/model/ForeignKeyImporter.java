package com.wisecoders.dbs.dbms.reverseEngineer.model;

import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.ForeignKeyAction;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.Log;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ForeignKeyImporter {
  public static List a(Importer paramImporter, Schema paramSchema) {
    ArrayList<ForeignKeyEntry> arrayList = new ArrayList();
    if (paramImporter.c.allChildrenAreSelected(paramSchema.tables))
      try {
        DatabaseMetaData databaseMetaData = paramImporter.d.a();
        Log.a(paramSchema, "AllForeignKeys");
        paramImporter.a("Schema '" + String.valueOf(paramSchema) + "' foreign keys");
        ResultSet resultSet = databaseMetaData.getImportedKeys(paramSchema.getMDCatalog(), paramSchema.getMDSchema(), null);
        while (resultSet.next()) {
          String str1 = resultSet.getString(1), str2 = resultSet.getString(2);
          Schema schema = paramSchema.project.getSchemaUsingMetaDataCatalogAndSchemaName(str1, str2);
          if (schema == null) {
            Log.f("SYNC: importing Fks failed to find schema '" + ((str1 != null) ? str1 : str2) + "', therefore '" + String.valueOf(paramSchema) + "' will be used instead.");
            schema = paramSchema;
          } 
          Table table1 = (Table)paramSchema.tables.getByName(resultSet.getString(7));
          Table table2 = (Table)schema.tables.getByName(resultSet.getString(3));
          if (table2 == null) {
            Log.f("SYNC: importing Fks failed to find table " + String.valueOf(schema) + "." + resultSet.getString(3));
          } else if (paramImporter.c.isSelected(table2)) {
            Column column1 = table1.getColumnByNameOrPath(resultSet.getString(8));
            Column column2 = (Column)table2.columns.getByName(resultSet.getString(4));
            if (column1 == null && resultSet.getString(8) != null && !paramImporter.d.a.isMongo()) {
              Log.f("SYNC: importing Fks failed to find column " + String.valueOf(table1) + "." + resultSet.getString(8));
            } else if (column2 == null && resultSet.getString(4) != null) {
              Log.f("SYNC: importing Fks failed to find column " + String.valueOf(table2) + "." + resultSet.getString(4));
            } else {
              arrayList.add(new ForeignKeyEntry(table2, table1, resultSet.getString(12), column2, column1, ForeignKeyAction.decode(resultSet.getShort(11)), ForeignKeyAction.decode(resultSet.getShort(10)), resultSet.getInt(9)));
            } 
          } 
          Log.j();
        } 
        resultSet.close();
        paramImporter.b();
      } catch (Exception exception) {
        Log.b(exception);
        paramImporter.d.a(exception);
      }  
    return arrayList;
  }
  
  public static List b(Importer paramImporter, Schema paramSchema) {
    ArrayList<ForeignKeyEntry> arrayList = new ArrayList();
    try {
      DatabaseMetaData databaseMetaData = paramImporter.d.a();
      Log.a(paramSchema, "ForeignKeysByTable");
      for (Table table : paramSchema.tables) {
        if (paramImporter.c.isSelected(table)) {
          paramImporter.a(table.getSymbolicName() + " '" + table.getSymbolicName() + "' foreign keys");
          ResultSet resultSet = databaseMetaData.getImportedKeys(paramSchema.getMDCatalog(), paramSchema.getMDSchema(), table.getName());
          if (resultSet != null) {
            while (resultSet.next()) {
              String str1 = resultSet.getString(1), str2 = resultSet.getString(2);
              Schema schema = paramSchema.project.getSchemaUsingMetaDataCatalogAndSchemaName(str1, str2);
              if (schema == null) {
                Log.f("SYNC: importing Fks failed to find schema '" + ((str1 != null) ? str1 : str2) + "', therefore '" + String.valueOf(paramSchema) + "' will be used instead.");
                schema = paramSchema;
              } 
              Table table1 = (Table)schema.tables.getByName(resultSet.getString(3));
              if (table1 == null) {
                Log.f("SYNC: importing Fks failed to find table " + String.valueOf(schema) + "." + resultSet.getString(3));
              } else if (paramImporter.c.isSelected(table1)) {
                Column column1 = table.getColumnByNameOrPath(resultSet.getString(8));
                Column column2 = (Column)table1.columns.getByName(resultSet.getString(4));
                if (column1 == null && resultSet.getString(8) != null && !paramImporter.d.a.isMongo()) {
                  Log.f("SYNC: importing Fks failed to find column " + String.valueOf(table) + "." + resultSet.getString(8));
                } else if (column2 == null && resultSet.getString(4) != null) {
                  Log.f("SYNC: importing Fks failed to find column " + String.valueOf(table1) + "." + resultSet.getString(4));
                } else {
                  arrayList.add(new ForeignKeyEntry(table1, table, resultSet.getString(12), column2, column1, ForeignKeyAction.decode(resultSet.getShort(11)), ForeignKeyAction.decode(resultSet.getShort(10)), resultSet.getInt(9)));
                } 
              } 
              Log.j();
            } 
            resultSet.close();
          } 
          paramImporter.b();
        } 
      } 
    } catch (Exception exception) {
      Log.b(exception);
      paramImporter.d.a(exception);
    } 
    if (arrayList.isEmpty())
      try {
        DatabaseMetaData databaseMetaData = paramImporter.d.a();
        Log.a(paramSchema, "ForeignKeysByTableAsExportedKeys");
        for (Table table : paramSchema.tables) {
          if (paramImporter.c.isSelected(table)) {
            paramImporter.a(table.getSymbolicName() + " '" + table.getSymbolicName() + "' foreign keys");
            ResultSet resultSet = databaseMetaData.getExportedKeys(paramSchema.getMDCatalog(), paramSchema.getMDSchema(), table.getName());
            if (resultSet != null) {
              while (resultSet.next()) {
                String str1 = resultSet.getString(5), str2 = resultSet.getString(6);
                Schema schema = paramSchema.project.getSchemaUsingMetaDataCatalogAndSchemaName(str1, str2);
                if (schema == null) {
                  Log.f("SYNC: importing Fks failed to find schema " + ((str2 != null) ? str2 : str1) + ", therefore " + String.valueOf(paramSchema) + " will be used instead.");
                  schema = paramSchema;
                } 
                String str3 = resultSet.getString(7);
                Table table1 = (Table)schema.tables.getByName(str3);
                if (table1 == null) {
                  Log.f("SYNC: importing Fks failed to find table " + String.valueOf(schema) + ". " + str3);
                } else if (paramImporter.c.isSelected(table1)) {
                  Column column1 = (Column)table.columns.getByName(resultSet.getString(4));
                  Column column2 = table1.getColumnByNameOrPath(resultSet.getString(8));
                  if (column2 == null && resultSet.getString(8) != null) {
                    Log.f("SYNC: importing Fks failed to find column " + String.valueOf(table1) + "." + resultSet.getString(8));
                  } else if (column1 == null && resultSet.getString(4) != null) {
                    Log.f("SYNC: importing Fks failed to find column " + String.valueOf(table) + "." + resultSet.getString(4));
                  } else {
                    arrayList.add(new ForeignKeyEntry(table, table1, resultSet.getString(12), column1, column2, ForeignKeyAction.decode(resultSet.getShort(11)), ForeignKeyAction.decode(resultSet.getShort(10)), resultSet.getInt(9)));
                  } 
                } 
                Log.j();
              } 
              resultSet.close();
            } 
          } 
        } 
      } catch (Exception exception) {
        Log.b(exception);
        paramImporter.d.a(exception);
      }  
    return arrayList;
  }
  
  public static boolean a(List<Comparable> paramList) {
    Collections.sort(paramList);
    ArrayList<ForeignKey> arrayList = new ArrayList();
    ForeignKey foreignKey = null;
    int i = -1;
    for (ForeignKeyEntry foreignKeyEntry : paramList) {
      if (foreignKey == null || foreignKeyEntry.b != foreignKey.getEntity() || foreignKeyEntry.a != foreignKey.getTargetEntity() || !foreignKeyEntry.c.equals(foreignKey.getName()) || foreignKeyEntry.h <= i) {
        foreignKey = foreignKeyEntry.b.createRelation(foreignKeyEntry.c);
        foreignKey.setTargetEntity(foreignKeyEntry.a);
        foreignKey.setDeleteAction(foreignKeyEntry.f);
        foreignKey.setUpdateAction(foreignKeyEntry.g);
        arrayList.add(foreignKey);
      } 
      if (foreignKeyEntry.d != null)
        foreignKey.addColumns(foreignKeyEntry.e, foreignKeyEntry.d); 
      i = foreignKeyEntry.h;
    } 
    for (ForeignKey foreignKey1 : arrayList) {
      for (ForeignKey foreignKey2 : foreignKey1.getEntity().getRelations()) {
        if (foreignKey1 != foreignKey2 && foreignKey1.sameAs(foreignKey2, false) && foreignKey1.usesSameColumns(foreignKey2)) {
          foreignKey1.markForDeletion();
          foreignKey1.getEntity().refresh();
        } 
      } 
    } 
    paramList.clear();
    return !arrayList.isEmpty();
  }
}
