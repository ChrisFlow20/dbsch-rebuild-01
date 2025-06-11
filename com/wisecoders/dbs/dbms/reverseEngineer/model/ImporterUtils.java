package com.wisecoders.dbs.dbms.reverseEngineer.model;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.schema.Constraint;
import com.wisecoders.dbs.schema.Function;
import com.wisecoders.dbs.schema.Procedure;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Sequence;
import com.wisecoders.dbs.schema.Sql;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.Table$TableType;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.SqlUtil;
import com.wisecoders.dbs.sys.StringUtil;
import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ImporterUtils {
  public static void a(StructureImporter paramStructureImporter, Schema paramSchema) {
    Log.a(paramSchema, "ListTableNames");
    switch (paramSchema.project.getDbId()) {
      case "AmazonAthena":
        (new String[2])[0] = "TABLE";
        (new String[2])[1] = "VIEW";
      case "DuckDB":
        (new String[2])[0] = "BASE TABLE";
        (new String[2])[1] = "VIEW";
      case "PostgreSQL":
        (new String[7])[0] = "TABLE";
        (new String[7])[1] = "VIEW";
        (new String[7])[2] = "MATERIALIZED VIEW";
        (new String[7])[3] = "SYSTEM TABLE";
        (new String[7])[4] = "GLOBAL TEMPORARY";
        (new String[7])[5] = "LOCAL TEMPORARY";
        (new String[7])[6] = "PARTITIONED TABLE";
      case "Sqlite":
        (new String[2])[0] = "TABLE";
        (new String[2])[1] = "VIEW";
      default:
        (new String[5])[0] = "TABLE";
        (new String[5])[1] = "VIEW";
        (new String[5])[2] = "SYSTEM TABLE";
        (new String[5])[3] = "GLOBAL TEMPORARY";
        (new String[5])[4] = "LOCAL TEMPORARY";
        break;
    } 
    String[] arrayOfString = new String[5];
    ResultSet resultSet = paramStructureImporter.c.a().getTables(paramSchema.getMDCatalog(), paramSchema.getMDSchema(), null, arrayOfString);
    while (resultSet.next()) {
      String str = resultSet.getString(3);
      if (str != null && !str.startsWith("BIN$"))
        if ("VIEW".equalsIgnoreCase(resultSet.getString(4))) {
          View view = paramSchema.createView(str);
          view.setComment(SqlUtil.unescapeComment(resultSet.getString(5)));
        } else {
          Table table = paramSchema.createTable(str);
          table.setType(Table$TableType.a(resultSet.getString(4)));
          table.setComment(SqlUtil.unescapeComment(resultSet.getString(5)));
          paramStructureImporter.a("List Tables ... " + paramSchema.tables.size());
        }  
      Log.j();
    } 
    resultSet.close();
    paramStructureImporter.a();
  }
  
  public static void b(StructureImporter paramStructureImporter, Schema paramSchema) {
    Log.a(paramSchema, "ListProceduresUsingDriver");
    ResultSet resultSet = paramStructureImporter.c.a().getProcedures(paramSchema.getMDCatalog(), paramSchema.getMDSchema(), null);
    if (resultSet != null) {
      while (resultSet.next()) {
        String str = resultSet.getString(3);
        if (StringUtil.isFilledTrim(str)) {
          if (str.indexOf(';') > 0)
            str = str.substring(0, str.indexOf(';')); 
          Procedure procedure = paramSchema.createProcedure(str);
          procedure.setComment(a(resultSet, 7));
          procedure.setUnitProperty(UnitProperty.d, a(resultSet, 9));
          paramStructureImporter.a("List Procedures ... " + paramSchema.procedures.size());
        } 
        Log.j();
      } 
      resultSet.close();
    } 
  }
  
  public static void a(Schema paramSchema, ResultSet paramResultSet) {
    Log.a(paramSchema, "ListProceduresUsingQuery");
    while (paramResultSet.next()) {
      String str = paramResultSet.getString(1);
      if (StringUtil.isFilledTrim(str)) {
        Procedure procedure = paramSchema.createProcedure(str);
        if (paramResultSet.getMetaData().getColumnCount() > 1)
          procedure.setUnitProperty(UnitProperty.d, paramResultSet.getString(2)); 
      } 
      Log.j();
    } 
    paramResultSet.close();
  }
  
  public static void a(Importer paramImporter, Schema paramSchema) {
    boolean bool = false;
    if (paramImporter.c.allChildrenAreSelected(paramSchema.procedures) && !paramSchema.procedures.isEmpty()) {
      ResultSet resultSet = paramImporter.d.a().getProcedureColumns(paramSchema.getMDCatalog(), paramSchema.getMDSchema(), null, null);
      Log.a(paramSchema, "BulkProcedureParameters");
      paramImporter.a("Bulk Procedure Parameters");
      if (resultSet != null) {
        byte b = 0;
        while (resultSet.next()) {
          String str = a(a(resultSet, new int[] { 20, 3 }));
          int i = a(resultSet, 18, b++);
          for (Procedure procedure : paramSchema.procedures) {
            if (StringUtil.areEqual(str, StringUtil.firstNotEmpty(new String[] { procedure.getString(UnitProperty.d), procedure.getName() }))) {
              if (resultSet.getShort(5) == 5) {
                procedure.addResultParameter(resultSet.getString(4), resultSet.getInt(6), resultSet.getString(7), i);
              } else {
                procedure.addInputParameter(resultSet.getString(4), resultSet.getInt(6), resultSet.getString(7), resultSet.getShort(5), i);
              } 
              bool = true;
            } 
          } 
          Log.j();
        } 
        resultSet.close();
      } 
    } 
    if (bool) {
      for (Procedure procedure : paramSchema.procedures)
        procedure.orderParametersByPosition(); 
    } else {
      for (Procedure procedure : paramSchema.procedures) {
        if (paramImporter.c.isSelected(procedure)) {
          Log.a(paramSchema, "IndividualProcedureParameters");
          paramImporter.a("Procedure '" + procedure.ref() + "' parameters");
          ResultSet resultSet = paramImporter.d.a().getProcedureColumns(paramSchema.getMDCatalog(), paramSchema.getMDSchema(), procedure.getName(), null);
          if (resultSet != null) {
            byte b = 0;
            while (resultSet.next()) {
              String str = a(a(resultSet, new int[] { 20, 3 }));
              int i = a(resultSet, 18, b++);
              if (StringUtil.areEqual(str, StringUtil.firstNotEmpty(new String[] { procedure.getString(UnitProperty.d), procedure.getName() })))
                if (resultSet.getShort(5) == 5) {
                  procedure.addResultParameter(resultSet.getString(4), resultSet.getInt(6), resultSet.getString(7), i);
                } else {
                  procedure.addInputParameter(resultSet.getString(4), resultSet.getInt(6), resultSet.getString(7), resultSet.getShort(5), i);
                }  
              Log.j();
            } 
            resultSet.close();
          } 
          procedure.orderParametersByPosition();
        } 
      } 
    } 
    paramImporter.b();
  }
  
  private static String a(String paramString) {
    if (paramString != null && paramString.indexOf(";") > 0)
      return paramString.substring(0, paramString.indexOf(";")); 
    return paramString;
  }
  
  public static void c(StructureImporter paramStructureImporter, Schema paramSchema) {
    Log.a(paramSchema, "ListFunctions");
    ResultSet resultSet = paramStructureImporter.c.a().getFunctions(paramSchema.getMDCatalog(), paramSchema.getMDSchema(), null);
    if (resultSet != null) {
      while (resultSet.next()) {
        String str = resultSet.getString(3);
        if (StringUtil.isFilledTrim(str)) {
          if (str.indexOf(';') > 0)
            str = str.substring(0, str.indexOf(';')); 
          Function function = paramSchema.createFunction(str);
          function.setComment(a(resultSet, 7));
          String str1 = a(resultSet, 6);
          if ("-1".equals(str1))
            str1 = null; 
          function.setUnitProperty(UnitProperty.d, str1);
          paramStructureImporter.a("Function " + str);
        } 
        Log.j();
      } 
      resultSet.close();
    } 
  }
  
  public static void b(Importer paramImporter, Schema paramSchema) {
    boolean bool = false;
    if (paramImporter.c.allChildrenAreSelected(paramSchema.functions) && !paramSchema.functions.isEmpty()) {
      Log.a(paramSchema, "BulkFunctionParameters");
      paramImporter.a("Bulk Function Parameters.");
      ResultSet resultSet = paramImporter.d.a().getFunctionColumns(paramSchema.getMDCatalog(), paramSchema.getMDSchema(), null, null);
      if (resultSet != null) {
        byte b = 0;
        while (resultSet.next()) {
          String str = a(a(resultSet, new int[] { 17, 3 }));
          int i = a(resultSet, 15, b++);
          for (Function function : paramSchema.functions) {
            if (StringUtil.areEqual(str, StringUtil.firstNotEmpty(new String[] { function.getString(UnitProperty.d), function.getName() }))) {
              if (resultSet.getShort(5) == 5) {
                function.addResultParameter(resultSet.getString(4), resultSet.getInt(6), resultSet.getString(7), i);
              } else {
                function.addInputParameter(resultSet.getString(4), resultSet.getInt(6), resultSet.getString(7), resultSet.getShort(5), i);
              } 
              bool = true;
            } 
          } 
          Log.j();
        } 
        resultSet.close();
      } 
    } 
    if (bool) {
      for (Function function : paramSchema.functions)
        function.orderParametersByPosition(); 
    } else {
      Log.a(paramSchema, "IndividualFunctionParameters");
      for (Function function : paramSchema.functions) {
        if (paramImporter.c.isSelected(function)) {
          paramImporter.a("Function '" + function.ref() + "' parameters");
          ResultSet resultSet = paramImporter.d.a().getFunctionColumns(paramSchema.getMDCatalog(), paramSchema.getMDSchema(), function.getName(), null);
          if (resultSet != null) {
            byte b = 0;
            while (resultSet.next()) {
              String str = a(a(resultSet, new int[] { 17, 3 }));
              int i = a(resultSet, 15, b++);
              if (StringUtil.areEqual(str, StringUtil.firstNotEmpty(new String[] { function.getString(UnitProperty.d), function.getName() }))) {
                if (resultSet.getShort(5) == 5) {
                  function.addResultParameter(resultSet.getString(4), resultSet.getInt(6), resultSet.getString(7), i);
                  continue;
                } 
                function.addInputParameter(resultSet.getString(4), resultSet.getInt(6), resultSet.getString(7), resultSet.getShort(5), i);
              } 
            } 
            resultSet.close();
            function.orderParametersByPosition();
            Log.j();
          } 
        } 
      } 
    } 
    paramImporter.b();
  }
  
  public static void b(Schema paramSchema, ResultSet paramResultSet) {
    Log.a(paramSchema, "ListFunctionsUsingQuery");
    while (paramResultSet.next()) {
      String str = paramResultSet.getString(1);
      if (StringUtil.isFilledTrim(str)) {
        Function function = paramSchema.createFunction(str);
        if (paramResultSet.getMetaData().getColumnCount() > 1)
          function.setUnitProperty(UnitProperty.d, paramResultSet.getString(2)); 
      } 
      Log.j();
    } 
    paramResultSet.close();
  }
  
  public static void a(StructureImporter paramStructureImporter, Schema paramSchema, ResultSet paramResultSet) {
    Log.a(paramSchema, "ListTriggersUsingQuery");
    int i = paramResultSet.getMetaData().getColumnCount();
    while (paramResultSet.next()) {
      String str = paramResultSet.getString(1);
      if (StringUtil.isFilledTrim(str)) {
        String str1 = (i > 1) ? paramResultSet.getString(2) : null;
        Table table = (str1 != null) ? paramSchema.getTable(str1) : null;
        paramSchema.createTrigger(str, table);
        paramStructureImporter.a("List Triggers ... " + paramSchema.triggers.size());
      } 
      Log.j();
    } 
    paramResultSet.close();
  }
  
  public static void b(StructureImporter paramStructureImporter, Schema paramSchema, ResultSet paramResultSet) {
    Log.a(paramSchema, "ListRulesUsingQuery");
    int i = paramResultSet.getMetaData().getColumnCount();
    while (paramResultSet.next()) {
      String str = paramResultSet.getString(1);
      if (StringUtil.isFilledTrim(str)) {
        String str1 = (i > 1) ? paramResultSet.getString(2) : null;
        Table table = (str1 != null) ? paramSchema.getTable(str1) : null;
        paramSchema.createRule(str, table);
        paramStructureImporter.a("List Rules ... " + paramSchema.rules.size());
      } 
      Log.j();
    } 
    paramResultSet.close();
  }
  
  public static void c(StructureImporter paramStructureImporter, Schema paramSchema, ResultSet paramResultSet) {
    Log.a(paramSchema, "LoadSequencesUsingQuery");
    while (paramResultSet.next()) {
      String str = paramResultSet.getString(1);
      if (StringUtil.isFilledTrim(str)) {
        Sequence sequence = paramSchema.createSequence(str);
        if (paramResultSet.getMetaData().getColumnCount() > 1)
          sequence.setOptions(paramResultSet.getString(2)); 
        if (paramResultSet.getMetaData().getColumnCount() > 2)
          sequence.setComment(paramResultSet.getString(3)); 
        paramStructureImporter.a("List Sequences ... " + paramSchema.sequences.size());
      } 
      Log.j();
    } 
    paramResultSet.close();
  }
  
  public static void a(Sql paramSql, ResultSet paramResultSet) {
    String str = "";
    while (paramResultSet.next()) {
      switch (paramResultSet.getMetaData().getColumnType(1)) {
        case -3:
        case -2:
        case 2005:
        case 2011:
        
        default:
          break;
      } 
      String str1 = paramResultSet.getString(1);
      if (str1 != null) {
        str1 = str1.replaceAll("\n\r", "\n");
        str1 = str1.replaceAll("\n\n", "\n");
        if ("\n".equals(str1))
          str1 = ""; 
        if (StringUtil.isFilledTrim(str))
          str = str + str; 
        str = str + str;
      } 
      if (paramResultSet.getMetaData().getColumnCount() >= 2) {
        String str2 = paramResultSet.getString(2);
        if (StringUtil.isFilled(str2))
          paramSql.setComment(str2); 
      } 
    } 
    paramResultSet.close();
    paramSql.setText(str);
  }
  
  public static String a(ResultSet paramResultSet, int... paramVarArgs) {
    for (int i : paramVarArgs) {
      if (paramResultSet.getMetaData().getColumnCount() >= i && StringUtil.isFilledTrim(paramResultSet.getString(i)))
        return paramResultSet.getString(i); 
    } 
    return null;
  }
  
  public static String a(ResultSet paramResultSet, int paramInt) {
    return (paramResultSet.getMetaData().getColumnCount() >= paramInt) ? paramResultSet.getString(paramInt) : null;
  }
  
  public static int a(ResultSet paramResultSet, int paramInt1, int paramInt2) {
    return (paramResultSet.getMetaData().getColumnCount() >= paramInt1) ? paramResultSet.getInt(paramInt1) : paramInt2;
  }
  
  public static void a(Schema paramSchema) {
    for (Table table : paramSchema.tables) {
      for (Constraint constraint : table.constraints) {
        if (constraint.getText() != null && constraint.getText().toLowerCase().startsWith("check ")) {
          constraint.setText(constraint.getText().substring("check ".length()));
          if (constraint.getText().startsWith("(") && constraint.getText().endsWith(")"))
            constraint.setText(constraint.getText().substring(1, constraint.getText().length() - 1).trim()); 
        } 
      } 
    } 
  }
  
  public static String a(Schema paramSchema, String paramString1, String paramString2, String paramString3, String paramString4) {
    if (paramString2 != null) {
      paramString2 = paramString2.trim();
      Dbms dbms = Dbms.get(paramSchema.getDbId());
      String str = paramString2.trim().toUpperCase();
      if (str.startsWith("SELECT") || str.startsWith("WITH")) {
        paramString2 = paramString3 + " " + paramString3 + " AS " + paramString4;
      } else if (paramString2.toUpperCase().startsWith("CREATE")) {
        paramString2 = a(paramString2, dbms.quoteAlways(paramSchema.ref()) + "." + dbms.quoteAlways(paramSchema.ref()), paramString4);
        paramString2 = a(paramString2, dbms.quoteAlways(paramSchema.ref()) + "." + dbms.quoteAlways(paramSchema.ref()), paramString4);
        paramString2 = a(paramString2, paramSchema.ref() + "." + paramSchema.ref(), paramString4);
        paramString2 = a(paramString2, dbms.quoteAlways(paramSchema.getName()) + "." + dbms.quoteAlways(paramSchema.getName()), paramString4);
        paramString2 = a(paramString2, dbms.quoteAlways(paramSchema.getName()) + "." + dbms.quoteAlways(paramSchema.getName()), paramString4);
        paramString2 = a(paramString2, paramSchema.getName() + "." + paramSchema.getName(), paramString4);
        paramString2 = a(paramString2, paramString1, paramString4);
      } 
      if (paramString2.endsWith(";"))
        paramString2 = paramString2.substring(0, paramString2.length() - 1); 
    } 
    return paramString2;
  }
  
  private static String a(String paramString1, String paramString2, String paramString3) {
    if (StringUtil.isFilledTrim(paramString2))
      return paramString1.replaceAll("\\b" + Pattern.quote(paramString2) + "\\b", Matcher.quoteReplacement(paramString3)); 
    return paramString1;
  }
}
