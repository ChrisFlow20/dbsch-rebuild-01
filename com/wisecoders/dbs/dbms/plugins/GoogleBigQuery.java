package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.dbms.sync.model.SyncFilter;
import com.wisecoders.dbs.schema.AttributeSpec;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.GroovyMethod;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sql.parser.DDLParser;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.SqlUtil;
import java.sql.ResultSet;
import java.util.List;

@DoNotObfuscate
public class GoogleBigQuery extends Dbms {
  public static final String NAME = "GoogleBigQuery";
  
  private static final String a = "CLUSTER_KEY";
  
  public GoogleBigQuery() {
    super("GoogleBigQuery");
  }
  
  public List listSchemasAndCatalogs(Envoy paramEnvoy) {
    return super.listSchemasAndCatalogs(paramEnvoy);
  }
  
  @GroovyMethod
  public String getClusteringOrder(Table paramTable) {
    Index index = paramTable.getPrimaryKey();
    if (index != null) {
      StringBuilder stringBuilder = new StringBuilder();
      for (Column column : index.columns) {
        if (index.hasColumnOption(column, "CLUSTER_KEY")) {
          if (!stringBuilder.isEmpty())
            stringBuilder.append(","); 
          stringBuilder.append(Dbms.quote(column));
        } 
      } 
      if (!stringBuilder.isEmpty())
        return "CLUSTER BY " + String.valueOf(stringBuilder); 
    } 
    return null;
  }
  
  public void importIndexes(Importer paramImporter, Schema paramSchema) {
    super.importIndexes(paramImporter, paramSchema);
    Log.a(paramSchema, "PartitionAndClusterKeys");
    SelectStatement selectStatement = paramImporter.d.a("SELECT table_name, column_name, is_partitioning_column, clustering_ordinal_position FROM \n`" + 
        String.valueOf(paramSchema.getSchema()) + "`.INFORMATION_SCHEMA.COLUMNS order by table_name, clustering_ordinal_position", new Object[0]);
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next()) {
        String str1 = resultSet.getString(1);
        String str2 = resultSet.getString(2);
        Table table = paramSchema.getTable(str1);
        if (table != null) {
          Column column = table.getColumnByNameOrPath(str2);
          if (column != null) {
            if ("YES".equalsIgnoreCase(resultSet.getString(3))) {
              Index index = table.createIndex("PartitionKey");
              index.setType(IndexType.PARTITION);
              if ("timestamp".equalsIgnoreCase(column.getDataType().getName())) {
                Column column1 = table.createColumn("DATE(" + str2 + ")", "date");
                column1.setSpec(AttributeSpec.functional);
              } else {
                index.addColumn(column);
              } 
            } 
            if (resultSet.getInt(4) > 0) {
              Index index = table.getIndexByType(IndexType.SORT);
              if (index == null) {
                index = table.createIndex("ClusterKey");
                index.setType(IndexType.SORT);
              } 
              index.addColumn(column);
            } 
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
  }
  
  public SyncFilter getSynchronizationFilter() {
    return new GoogleBigQuery$1(this);
  }
  
  public void importViewColumns(Importer paramImporter, Schema paramSchema) {
    for (View view : paramSchema.views) {
      SelectStatement selectStatement = paramImporter.d.a("SELECT column_name, data_type FROM " + paramSchema.ref() + ".INFORMATION_SCHEMA.COLUMNS WHERE table_schema=? and table_name =?", new Object[] { paramSchema
            .getName(), view.getName() });
      try {
        ResultSet resultSet = selectStatement.j();
        while (resultSet.next()) {
          Column column = (Column)view.columns.getByName(resultSet.getString(1));
          if (column == null)
            column = view.createColumn(resultSet.getString(1), DbmsTypes.get(this.dbId).getDataType(12)); 
          Dbms.setColumnTypeFromString(column, resultSet.getString(2));
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
  }
  
  public boolean reverseEngineerTablesUsingDDL(Importer paramImporter, DDLParser paramDDLParser, Schema paramSchema) {
    boolean bool = true;
    Log.a(paramSchema, "GBQIndividualReverseEngineerUsingDDL");
    for (Table table : paramSchema.tables) {
      if (paramImporter.c.isSelected(table)) {
        paramImporter.a(table.getSymbolicName() + " '" + table.getSymbolicName() + "'");
        paramImporter.b();
        try {
          SelectStatement selectStatement = paramImporter.d.a("SELECT ddl FROM " + paramSchema.ref() + ".INFORMATION_SCHEMA.TABLES WHERE table_name=?", new Object[] { table.getName() });
          try {
            String str = selectStatement.b(1);
            paramDDLParser.splitAndParse(str);
            Log.j();
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
          bool = false;
        } 
      } 
    } 
    return bool;
  }
  
  @GroovyMethod
  public static String printColumn(Column paramColumn, int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    if (paramInt > 0) {
      stringBuilder.append("\n");
      stringBuilder.append("\t".repeat(paramInt + 1));
    } 
    String str = paramColumn.getTypeString();
    if (paramColumn.getChildEntity() != null) {
      StringBuilder stringBuilder1 = new StringBuilder();
      boolean bool = false;
      for (Column column : (paramColumn.getChildEntity()).columns) {
        if (bool)
          stringBuilder1.append(","); 
        bool = true;
        stringBuilder1.append(printColumn(column, paramInt + 1));
      } 
      str = str.replaceFirst("\\?", stringBuilder1.toString());
    } 
    stringBuilder.append(paramColumn.getName()).append(" ").append(str);
    if (paramInt == 0 && paramColumn.isMandatory())
      stringBuilder.append(" NOT NULL"); 
    if (paramColumn.getComment() != null)
      stringBuilder.append(" OPTIONS( description='").append(SqlUtil.escapeCommentQuotes(paramColumn.getComment())).append("' ) "); 
    return stringBuilder.toString();
  }
}
