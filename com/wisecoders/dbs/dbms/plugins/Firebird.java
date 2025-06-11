package com.wisecoders.dbs.dbms.plugins;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.connect.model.envoy.SelectStatement;
import com.wisecoders.dbs.dbms.reverseEngineer.model.Importer;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.Function;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.Procedure;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.Trigger;
import com.wisecoders.dbs.schema.UserDataType;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Qx;
import com.wisecoders.dbs.sys.StringUtil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

public class Firebird extends Dbms {
  public Firebird() {
    super("Firebird");
  }
  
  private static final Qx a = new Qx(Firebird.class);
  
  private static final Pattern b = Pattern.compile("line[\\s:]*(\\d+)");
  
  private static final Pattern c = Pattern.compile("column[\\s:]*(\\d+)");
  
  public int getErrorPosition(SQLException paramSQLException, String paramString, Statement paramStatement, Envoy paramEnvoy) {
    String str = getMessageFromException(paramSQLException, paramEnvoy.a);
    return a(paramString, str, b, c, (Pattern)null, (Pattern)null);
  }
  
  public void importFinal(Schema paramSchema) {
    for (Trigger trigger : paramSchema.triggers) {
      String str = trigger.getText();
      if (StringUtil.isFilledTrim(str)) {
        if (!str.toLowerCase().startsWith("create"))
          str = "CREATE TRIGGER " + trigger.getName() + " " + str; 
        trigger.setText(str);
      } 
    } 
    for (Function function : paramSchema.functions) {
      String str = function.getText();
      if (StringUtil.isFilledTrim(str)) {
        if (!str.toLowerCase().startsWith("create"))
          str = "CREATE FUNCTION " + function.getName() + " " + str; 
        function.setText(str);
      } 
    } 
    for (Procedure procedure : paramSchema.procedures) {
      String str = procedure.getText();
      if (StringUtil.isFilledTrim(str)) {
        if (!str.toLowerCase().startsWith("create"))
          str = "CREATE PROCEDURE " + procedure.getName() + " " + str; 
        procedure.setText(str);
      } 
    } 
  }
  
  public void createDatabase(Connector paramConnector, String paramString1, String paramString2) {
    Envoy envoy = paramConnector.startEnvoy("Create Database");
    try {
      envoy.g("CREATE DATABASE '" + paramString1 + "'");
      if (envoy != null)
        envoy.close(); 
    } catch (Throwable throwable) {
      if (envoy != null)
        try {
          envoy.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
    paramConnector.setInstance(paramString1);
  }
  
  public void importUserDefinedTypes(Importer paramImporter, Schema paramSchema) {
    Log.a(paramSchema, "UserDefinedTypes");
    SelectStatement selectStatement = paramImporter.d.a(a.a("UserDefinedTypes"), new Object[0]);
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next()) {
        String str1 = resultSet.getString(1).trim();
        String str2 = resultSet.getString(2).trim();
        String str3 = resultSet.getString(3);
        String str4 = resultSet.getString(4);
        String str5 = resultSet.getString(5);
        String str6 = resultSet.getString(6);
        boolean bool = resultSet.getBoolean(7);
        String str7 = resultSet.getString(8);
        Log.d("Import domain " + str1);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE DOMAIN ${name} AS ");
        stringBuilder.append(str2).append(" ");
        if (str6 != null)
          stringBuilder.append(str6).append(" "); 
        if (str5 != null)
          stringBuilder.append(str5).append(" "); 
        if (str4 != null && !"NONE".equalsIgnoreCase(str4.trim()))
          stringBuilder.append("COLLATE ").append(str4.trim()).append(" "); 
        if (str3 != null && !"NONE".equalsIgnoreCase(str3.trim()))
          stringBuilder.append("CHARACHTER SET ").append(str3.trim()).append(" "); 
        UserDataType userDataType = paramSchema.createUserDataType(str1);
        userDataType.setScript(stringBuilder.toString());
        userDataType.setComment(str7);
        int i = str2.indexOf("(");
        if (i > -1)
          str2 = str2.substring(0, i); 
        DataType dataType = DbmsTypes.get(this.dbId).getType(str2);
        if (dataType != null)
          userDataType.setJavaType(dataType.getJavaType()); 
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
  
  public void importIndexes(Importer paramImporter, Schema paramSchema) {
    super.importIndexes(paramImporter, paramSchema);
    SelectStatement selectStatement = paramImporter.d.a(a.a("Indexes"), new Object[0]);
    try {
      ResultSet resultSet = selectStatement.j();
      while (resultSet.next()) {
        String str1 = resultSet.getString(1).trim();
        String str2 = resultSet.getString(2).trim();
        Table table = paramSchema.getTable(str1);
        if (table != null) {
          Index index = (Index)table.indexes.getByName(str2);
          if (index != null && index.getType() == IndexType.UNIQUE_INDEX)
            index.setType(IndexType.UNIQUE_KEY); 
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
  
  public void importProcedures(Importer paramImporter, Schema paramSchema) {
    for (Procedure procedure : paramSchema.procedures) {
      StringBuilder stringBuilder1 = new StringBuilder();
      StringBuilder stringBuilder2 = new StringBuilder();
      StringBuilder stringBuilder3 = new StringBuilder();
      SelectStatement selectStatement = paramImporter.d.a("SELECT trim(rdb$procedure_source) FROM rdb$procedures WHERE trim(rdb$procedure_name)=?", new Object[] { procedure
            .getName() });
      try {
        ResultSet resultSet = selectStatement.j();
        while (resultSet.next())
          stringBuilder1.append(resultSet.getString(1)); 
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
      selectStatement = paramImporter.d.a(a
          .a("ProcedureParameters"), new Object[] { procedure.getName(), Integer.valueOf(0) });
      try {
        ResultSet resultSet = selectStatement.j();
        while (resultSet.next()) {
          if (!stringBuilder2.isEmpty())
            stringBuilder2.append(", "); 
          stringBuilder2.append(resultSet.getString(1)).append(" ").append(resultSet.getString(2));
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
      selectStatement = paramImporter.d.a(a
          .a("ProcedureParameters"), new Object[] { procedure.getName(), Integer.valueOf(1) });
      try {
        ResultSet resultSet = selectStatement.j();
        while (resultSet.next()) {
          if (!stringBuilder3.isEmpty())
            stringBuilder3.append(", "); 
          stringBuilder3.append(resultSet.getString(1)).append(" ").append(resultSet.getString(2));
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
      if (!stringBuilder1.isEmpty()) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CREATE PROCEDURE ").append(procedure.getName());
        if (!stringBuilder2.isEmpty())
          stringBuilder.append("(").append(stringBuilder2).append(") "); 
        if (!stringBuilder3.isEmpty())
          stringBuilder.append("RETURNS (").append(stringBuilder3).append(") "); 
        stringBuilder.append("\nAS \n").append(stringBuilder1.toString().trim());
        procedure.setText(stringBuilder.toString());
      } 
    } 
  }
}
