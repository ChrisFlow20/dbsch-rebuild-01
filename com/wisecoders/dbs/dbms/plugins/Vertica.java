package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.Dbms$ParamSource;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.JdbcUrlParam;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.MaterializedView;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sql.parser.DDLParser;
import com.wisecoders.dbs.sys.Log;
import java.sql.ResultSet;
import java.util.List;

public class Vertica extends Dbms {
  public Vertica() {
    super("Vertica");
  }
  
  public void createDatabase(Connector paramConnector, String paramString1, String paramString2) {
    paramConnector.setInstance(paramString1);
    Envoy envoy = paramConnector.startEnvoy("Create Database");
    if (envoy != null)
      envoy.close(); 
  }
  
  public void importMaterializedViews(Importer paramImporter, Schema paramSchema) {
    SelectStatement selectStatement = paramImporter.d.a("select p.projection_name, c.projection_column_name from projections p join projection_columns c on ( p.projection_id=c.projection_id ) where p.projection_schema=?", new Object[] { paramSchema


          
          .getName() });
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next() && !paramImporter.a()) {
        MaterializedView materializedView = (MaterializedView)paramSchema.materializedViews.getByName(resultSet.getString(1));
        if (materializedView == null)
          materializedView = paramSchema.createMaterializedView(resultSet.getString(1)); 
        materializedView.createColumn(resultSet.getString(2), DbmsTypes.get(paramImporter.d.e()).getOrCreateDataType(12, "Varchar"));
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
    for (View view : paramSchema.materializedViews) {
      SelectStatement selectStatement1 = paramImporter.d.a("select export_objects('',?,false)", new Object[] { view
            
            .ref() });
      try {
        ResultSet resultSet = selectStatement1.j();
        if (resultSet.next())
          view.setScript(resultSet.getString(1)); 
        if (selectStatement1 != null)
          selectStatement1.close(); 
      } catch (Throwable throwable) {
        if (selectStatement1 != null)
          try {
            selectStatement1.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          }  
        throw throwable;
      } 
    } 
  }
  
  public void importViewColumns(Importer paramImporter, Schema paramSchema) {
    for (View view : paramSchema.views) {
      SelectStatement selectStatement = paramImporter.d.a("SELECT column_name, data_type FROM v_catalog.view_columns WHERE table_schema=? and table_name =?", new Object[] { paramSchema
            .getName(), view.getName() });
      try {
        ResultSet resultSet = selectStatement.j();
        while (resultSet.next()) {
          Column column = (Column)view.columns.getByName(resultSet.getString(1));
          if (column == null)
            column = view.createColumn(resultSet.getString(1), DbmsTypes.get(this.dbId).getDataType(12)); 
          Dbms.setColumnTypeFromString(column, resultSet.getString(2));
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
  
  public Dbms$ParamSource getParamSource(JdbcUrlParam paramJdbcUrlParam) {
    return (paramJdbcUrlParam == JdbcUrlParam.a) ? Dbms$ParamSource.b : Dbms$ParamSource.c;
  }
  
  public List listParam(Envoy paramEnvoy, JdbcUrlParam paramJdbcUrlParam) {
    return a(paramEnvoy, paramEnvoy.a("select database_name from v_catalog.databases", new Object[0]));
  }
  
  public void importIndexes(Importer paramImporter, Schema paramSchema) {
    super.importIndexes(paramImporter, paramSchema);
  }
  
  public boolean reverseEngineerTablesUsingDDL(Importer paramImporter, DDLParser paramDDLParser, Schema paramSchema) {
    boolean bool = true;
    Log.a(paramSchema, "VerticaIndividualReverseEngineerUsingDDL");
    for (Table table : paramSchema.tables) {
      if (paramImporter.c.isSelected(table)) {
        paramImporter.a(table.getSymbolicName() + " '" + table.getSymbolicName() + "'");
        paramImporter.b();
        try {
          SelectStatement selectStatement = paramImporter.d.a("SELECT EXPORT_TABLES('', '" + table.ref() + "')", new Object[0]);
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
}
