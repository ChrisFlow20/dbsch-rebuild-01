package com.wisecoders.dbs.dbms.reverseEngineer.model;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.schema.AttributeSpec;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.TreeMap;

public class IndexImporter {
  private final Importer a;
  
  private final Schema b;
  
  private String c = null;
  
  private Table d = null;
  
  private IndexType e = IndexType.NORMAL;
  
  private String f;
  
  private final TreeMap g = new TreeMap<>();
  
  private final HashMap h = new HashMap<>();
  
  public IndexImporter(Importer paramImporter, Schema paramSchema) {
    this.a = paramImporter;
    this.b = paramSchema;
  }
  
  public boolean a() {
    Log.a(this.b, "IndexesAllTables");
    DatabaseMetaData databaseMetaData = this.a.d.a();
    boolean bool1 = false, bool2 = false;
    if (this.a.c.allChildrenAreSelected(this.b.tables)) {
      if (this.a.a())
        return false; 
      this.a.a(this.b.getSymbolicName() + " '" + this.b.getSymbolicName() + "' indexes");
      try {
        ResultSet resultSet = databaseMetaData.getPrimaryKeys(this.b.getMDCatalog(), this.b.getMDSchema(), null);
        while (resultSet.next() && !this.a.a()) {
          Table table = this.b.getTable(resultSet.getString(3));
          if (table != null) {
            Column column = table.getColumnByNameOrPath(resultSet.getString(4));
            if (column == null) {
              column = table.createColumn(resultSet.getString(4), DbmsTypes.get(this.a.b).getDataType(12));
              column.setSpec(AttributeSpec.functional);
            } 
            column.setMandatory(true);
            a(false, table, resultSet.getString(6), IndexType.PRIMARY_KEY, null, column, resultSet.getShort(5), null);
            bool1 = true;
          } 
        } 
        c();
        resultSet.close();
      } catch (Exception exception) {
        Log.b(exception);
        bool2 = true;
      } 
      try {
        ResultSet resultSet = databaseMetaData.getIndexInfo(this.b.getMDCatalog(), this.b.getMDSchema(), null, false, true);
        int i = resultSet.getMetaData().getColumnCount();
        while (resultSet.next() && !this.a.a()) {
          Table table = this.b.getTable(resultSet.getString(3));
          if (table != null && resultSet.getString(9) != null) {
            Column column = table.getColumnByNameOrPath(resultSet.getString(9));
            if (column == null) {
              column = table.createColumn(resultSet.getString(9), DbmsTypes.get(this.a.b).getDataType(12));
              column.setSpec(AttributeSpec.functional);
            } 
            StringBuilder stringBuilder = new StringBuilder();
            if (1 == resultSet.getShort(7))
              stringBuilder.append("clustered"); 
            if (i >= 13 && StringUtil.isFilledTrim(resultSet.getString(13))) {
              if (!stringBuilder.isEmpty())
                stringBuilder.append(" "); 
              stringBuilder.append("WHERE ").append(resultSet.getString(13));
            } 
            IndexType indexType = resultSet.getBoolean(4) ? IndexType.NORMAL : ((Dbms.get(this.a.d.e())).uniqueIndexCreate.j() ? IndexType.UNIQUE_INDEX : IndexType.UNIQUE_KEY);
            a(true, table, resultSet
                
                .getString(6), indexType, stringBuilder
                
                .toString(), column, resultSet
                
                .getShort(8), 
                (i >= 9) ? resultSet.getString(10) : null);
            bool1 = true;
          } 
          Log.j();
        } 
        c();
        resultSet.close();
      } catch (Exception exception) {
        Log.b(exception);
        bool2 = true;
      } 
      for (Table table : this.b.tables) {
        for (Index index : table.indexes) {
          for (Index index1 : table.indexes) {
            if (index1 != index && Index.attributesAreEqual(index.getColumns(), index1.getColumns()) && (index.getType()).category == (index1.getType()).category && !index.isMarkedForDeletion() && !index1.isMarkedForDeletion()) {
              Index index2 = (index.getType() == IndexType.PRIMARY_KEY) ? index1 : index;
              Log.d("Import remove duplicate index '" + String.valueOf(index) + "' and keep '" + String.valueOf(index1) + "'");
              index2.markForDeletion();
            } 
          } 
        } 
        table.presetColumnOrder();
        table.refresh();
      } 
      this.a.b();
    } 
    return (bool1 && !bool2);
  }
  
  public void b() {
    Log.a(this.b, "IndexesByTable");
    DatabaseMetaData databaseMetaData = this.a.d.a();
    for (Table table : this.b.tables) {
      if (this.a.c.isSelected(table)) {
        if (this.a.a())
          return; 
        this.a.a(table.getSymbolicName() + " '" + table.getSymbolicName() + "' indexes");
        try {
          ResultSet resultSet = databaseMetaData.getPrimaryKeys(this.b.getMDCatalog(), this.b.getMDSchema(), table.getName());
          while (resultSet.next() && !this.a.a()) {
            Column column = table.getColumnByNameOrPath(resultSet.getString(4));
            if (column == null) {
              column = table.createColumn(resultSet.getString(4), DbmsTypes.get(this.a.b).getDataType(12));
              column.setSpec(AttributeSpec.functional);
            } 
            column.setMandatory(true);
            a(false, table, resultSet.getString(6), IndexType.PRIMARY_KEY, null, column, resultSet.getShort(5), null);
          } 
          c();
          resultSet.close();
        } catch (Exception exception) {
          Log.b(exception);
        } 
        try {
          ResultSet resultSet = databaseMetaData.getIndexInfo(this.b.getMDCatalog(), this.b.getMDSchema(), table.getName(), false, true);
          int i = resultSet.getMetaData().getColumnCount();
          while (resultSet.next() && !this.a.a()) {
            if (resultSet.getString(9) != null) {
              Column column = table.getColumnByNameOrPath(resultSet.getString(9));
              if (column == null) {
                column = table.createColumn(resultSet.getString(9), DbmsTypes.get(this.a.b).getDataType(12));
                column.setSpec(AttributeSpec.functional);
              } 
              StringBuilder stringBuilder = new StringBuilder();
              if (1 == resultSet.getShort(7))
                stringBuilder.append("clustered"); 
              if (i >= 13 && StringUtil.isFilledTrim(resultSet.getString(13))) {
                if (!stringBuilder.isEmpty())
                  stringBuilder.append(" "); 
                stringBuilder.append("WHERE ").append(resultSet.getString(13));
              } 
              IndexType indexType = resultSet.getBoolean(4) ? IndexType.NORMAL : ((Dbms.get(this.a.d.e())).uniqueIndexCreate.j() ? IndexType.UNIQUE_INDEX : IndexType.UNIQUE_KEY);
              a(true, table, resultSet
                  
                  .getString(6), indexType, stringBuilder
                  
                  .toString(), column, resultSet
                  
                  .getShort(8), 
                  (i >= 9) ? resultSet.getString(10) : null);
            } 
            Log.j();
          } 
          c();
          resultSet.close();
        } catch (Exception exception) {
          Log.b(exception);
        } 
        table.removeDuplicateIndexes();
        table.presetColumnOrder();
        table.refresh();
        this.a.b();
      } 
    } 
  }
  
  private void a(boolean paramBoolean, Table paramTable, String paramString1, IndexType paramIndexType, String paramString2, Column paramColumn, short paramShort, String paramString3) {
    Log.d("Import " + String.valueOf(paramIndexType) + " Index " + paramString1 + " on " + String.valueOf(paramTable) + " (" + paramShort + ": " + String.valueOf(paramColumn) + ") ");
    if ((paramBoolean && this.d != null && this.d != paramTable) || 
      
      !StringUtil.stringsAreEqual(this.c, paramString1) || this.g
      .get(Short.valueOf(paramShort)) != null)
      c(); 
    this.d = paramTable;
    this.c = paramString1;
    this.e = paramIndexType;
    this.f = paramString2;
    this.g.put(Short.valueOf(paramShort), paramColumn);
    if (paramString3 != null && 
      "D".equals(paramString3))
      this.h.put(paramColumn, "DESC"); 
  }
  
  private String a(Table paramTable, IndexType paramIndexType, String paramString) {
    switch (IndexImporter$1.a[paramIndexType.ordinal()]) {
      case 1:
      case 2:
      
      case 3:
      
      default:
        break;
    } 
    String str2 = "idx_" + paramTable.getName();
    String str1 = str2 = Dbms.get(paramString).toDefaultCases(str2);
    byte b = 0;
    while (true) {
      boolean bool = false;
      for (Table table : (paramTable.getSchema()).tables) {
        for (Index index : table.indexes) {
          if (str1.equalsIgnoreCase(index.getName()))
            bool = true; 
        } 
      } 
      if (bool)
        str1 = str2 + "_" + str2; 
      if (!bool)
        return str1; 
    } 
  }
  
  private void c() {
    if (this.d != null && !this.g.isEmpty()) {
      if (this.c == null || this.c.length() == 0 || "Primary".equalsIgnoreCase(this.c))
        this.c = a(this.d, this.e, this.d.schema.project.getDbId()); 
      Index index = this.d.createIndex(this.c);
      index.setType(this.e);
      index.setOptions(this.f);
      for (Short short_ : this.g.keySet()) {
        Column column = (Column)this.g.get(short_);
        index.addColumn(column);
        index.setColumnOptions(column, (String)this.h.get(column));
      } 
    } 
    this.d = null;
    this.c = null;
    this.g.clear();
    this.h.clear();
  }
}
