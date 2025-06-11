package com.wisecoders.dbs.dbms.reverseEngineer.model;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.sync.engine.diffs.AlterStatement;
import com.wisecoders.dbs.dbms.sync.engine.operations.K;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.MaterializedView;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sys.EscapeChars;
import com.wisecoders.dbs.sys.InputStreamUtil;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.SqlUtil;
import com.wisecoders.dbs.sys.StringUtil;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class ViewImporter {
  private static boolean a = false;
  
  public void a(Importer paramImporter, Schema paramSchema, SelectStatement paramSelectStatement) {
    Log.a(paramSchema, "ExtractViews");
    ResultSet resultSet = paramSelectStatement.j();
    while (resultSet.next()) {
      String str = resultSet.getString(1);
      if (str != null) {
        View view = paramSchema.getView(str);
        if (view != null && paramImporter.c.isSelected(view)) {
          String str1;
          Object object = resultSet.getObject(2);
          if (object instanceof Clob) {
            str1 = InputStreamUtil.a(((Clob)object).getCharacterStream(), 100000);
          } else {
            str1 = String.valueOf(object);
          } 
          if (str1 != null) {
            String str2 = view.getScript();
            if (StringUtil.isFilledTrim(str2))
              str1 = str2 + "\n" + str2; 
            str1 = ImporterUtils.a(paramSchema, str, str1, "CREATE VIEW", (Dbms.get(paramSchema.getDbId())).defaultViewName.c_());
            view.setScript(str1);
            view.deduceVirtualFks();
          } 
        } 
      } 
      Log.j();
    } 
    resultSet.close();
  }
  
  public void a(Importer paramImporter, Schema paramSchema) {
    Log.a(paramSchema, "ExtractViewColumns");
    for (View view : paramSchema.views) {
      if (paramImporter.c.isSelected(view) && view.getAttributes().isEmpty()) {
        paramImporter.a("View '" + view.ref() + "' columns");
        a(paramImporter.d, view);
        paramImporter.b();
        Log.j();
      } 
    } 
  }
  
  public void b(Importer paramImporter, Schema paramSchema, SelectStatement paramSelectStatement) {
    Log.a(paramSchema, "ExtractMaterializedViews");
    ResultSet resultSet = paramSelectStatement.j();
    while (resultSet.next()) {
      String str = resultSet.getString(1);
      if (str != null) {
        String str1;
        MaterializedView materializedView = paramSchema.getOrCreateMaterializedView(str);
        Object object = resultSet.getObject(2);
        if (object instanceof Clob) {
          str1 = InputStreamUtil.a(((Clob)object).getCharacterStream(), 100000);
        } else {
          str1 = String.valueOf(object);
        } 
        if (str1 != null) {
          String str2 = materializedView.getScript();
          if (StringUtil.isFilledTrim(str2))
            str1 = str2 + "\n" + str2; 
          str1 = ImporterUtils.a(paramSchema, str, str1, "CREATE MATERIALIZED VIEW", (Dbms.get(paramSchema.getDbId())).defaultViewName.c_());
          materializedView.setScript(str1);
          materializedView.deduceVirtualFks();
        } 
      } 
      Log.j();
    } 
    resultSet.close();
    for (MaterializedView materializedView : paramSchema.materializedViews) {
      if (materializedView.getAttributes().isEmpty()) {
        paramImporter.a("Materialized View '" + materializedView.ref() + "'");
        a(paramImporter.d, materializedView);
        paramImporter.b();
        Log.j();
      } 
    } 
  }
  
  public static void a(Envoy paramEnvoy, View paramView) {
    String str = (paramView.getSchema()).project.getDbId();
    ArrayList arrayList = new ArrayList(paramView.columns);
    boolean bool = false;
    try {
      Dbms dbms = Dbms.get(str);
      Schema schema = paramView.getSchema();
      ResultSet resultSet = paramEnvoy.a().getColumns(schema.getMDCatalog(), schema.getMDSchema(), paramView.getName(), null);
      while (resultSet.next()) {
        Column column = paramView.getColumn(resultSet.getString(4));
        if (column == null) {
          column = paramView.createColumn(resultSet.getString(4), DbmsTypes.get(str).getOrCreateDataType(resultSet.getInt(5), resultSet.getString(6)));
        } else {
          column.setDataType(DbmsTypes.get(str).getOrCreateDataType(resultSet.getInt(5), resultSet.getString(6)));
          arrayList.remove(column);
        } 
        dbms.setImportedColumnType(column, resultSet.getInt(5), resultSet.getString(6), resultSet.getInt(7), resultSet.getInt(9), (String)null);
        column.setComment(SqlUtil.unescapeComment(resultSet.getString(12)));
        bool = true;
      } 
      resultSet.close();
    } catch (Exception exception) {
      Log.b(exception);
    } 
    if (!a && !bool) {
      long l = System.currentTimeMillis();
      Timeline timeline = new Timeline(new KeyFrame[] { new KeyFrame(Duration.millis(2000.0D), paramActionEvent -> {
                Log.f("SYNC: deduce table columns canceled");
                paramEnvoy.n();
              }new javafx.animation.KeyValue[0]) });
      timeline.setCycleCount(1);
      timeline.play();
      try {
        SelectStatement selectStatement = paramEnvoy.a("SELECT * FROM " + paramView.ref() + " WHERE 0=1 ", new Object[0]);
        try {
          ResultSet resultSet = selectStatement.j();
          ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
          int i = resultSetMetaData.getColumnCount();
          if (paramView.getAttributes().isEmpty()) {
            if (i > 0)
              paramView.getAttributes().clear(); 
            for (byte b = 1; b <= i; b++) {
              Column column = paramView.getColumn(resultSetMetaData.getColumnName(b).trim());
              if (column == null) {
                paramView.createColumn(resultSetMetaData.getColumnName(b).trim(), 
                    DbmsTypes.get(str).getOrCreateDataType(resultSetMetaData.getColumnType(b), resultSetMetaData.getColumnTypeName(b)));
              } else {
                column.setDataType(DbmsTypes.get(str).getOrCreateDataType(resultSetMetaData.getColumnType(b), resultSetMetaData.getColumnTypeName(b)));
                arrayList.remove(column);
              } 
              bool = true;
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
      } catch (Exception exception) {
        if (System.currentTimeMillis() > l + Sys.B.viewDeduceColumnsTimeoutMillis.a())
          a = true; 
        Log.b(exception);
      } finally {
        timeline.stop();
      } 
    } 
    if (bool) {
      for (Column column : arrayList)
        column.markForDeletion(); 
      paramView.refresh();
    } 
  }
  
  public static String a(View paramView) {
    String str = paramView.getScript();
    int i;
    if ((i = paramView.getScript().toLowerCase().indexOf("select")) > -1)
      str = paramView.getScript().substring(i); 
    int j;
    if ((j = paramView.getScript().toLowerCase().indexOf("as ")) > -1 && j < i)
      str = paramView.getScript().substring(j + "as ".length()); 
    return str;
  }
  
  public static void b(Envoy paramEnvoy, View paramView) {
    String str = (new AlterStatement(null, EscapeChars.forGroovy(a(paramView)))).set(K.a, Dbms.quote(paramView)).set(K.B, Dbms.quote(paramView)).set(K.A, paramView.ref()).set(K.C, paramView.ref()).set(K.D, paramView.ref()).set(K.l, paramView.schema.ref()).set(K.m, Dbms.quote(paramView.schema)).toString();
    SelectStatement selectStatement = paramEnvoy.a(str, new Object[0]);
    try {
      ResultSet resultSet = selectStatement.j();
      ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
      int i = resultSetMetaData.getColumnCount();
      if (i > 0)
        paramView.getAttributes().clear(); 
      for (byte b = 1; b <= i; b++)
        paramView.createColumn(resultSetMetaData.getColumnName(b), 
            DbmsTypes.get(paramView.schema.project.getDbId()).getOrCreateDataType(resultSetMetaData.getColumnType(b), resultSetMetaData.getColumnTypeName(b))); 
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
