package com.wisecoders.dbs.sql.generator;

import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.DataType;
import com.wisecoders.dbs.schema.Table;
import java.util.ArrayList;
import java.util.List;

public class JsonScriptGenerator implements ScriptGenerator {
  private final List a = new ArrayList();
  
  public JsonScriptGenerator(List paramList) {
    this.a.addAll(paramList);
  }
  
  private Table b() {
    for (Unit unit : this.a) {
      if (unit.getEntity() instanceof Table)
        return (Table)unit.getEntity(); 
    } 
    return null;
  }
  
  public String a() {
    Table table = b();
    if (table != null)
      return table.ref() + ".find()"; 
    return "";
  }
  
  public String a(StatementType paramStatementType) {
    Table table = b();
    String str = "// REPLACE <database> and <collection> WITH AN EXISTING DATABASE AND COLLECTION\n";
    if (table == null) {
      switch (JsonScriptGenerator$1.a[paramStatementType.ordinal()]) {
        case 1:
          return "// REPLACE <database> and <collection> WITH AN EXISTING DATABASE AND COLLECTION\n<database>.<collection>.insert({...});";
        case 2:
          return "// REPLACE <database> and <collection> WITH AN EXISTING DATABASE AND COLLECTION\n<database>.<collection>.update({_id:...,{...}} )";
        case 3:
          return "// REPLACE <database> and <collection> WITH AN EXISTING DATABASE AND COLLECTION\n<database>.<collection>.delete({_id:...})";
      } 
      return "// REPLACE <database> and <collection> WITH AN EXISTING DATABASE AND COLLECTION\n<database>.<collection>.find()";
    } 
    switch (JsonScriptGenerator$1.a[paramStatementType.ordinal()]) {
      case 1:
        return table.ref() + ".insert( " + table.ref() + "\n );";
      case 2:
        return table.ref() + ".update( {_id:..., {...} } )";
      case 3:
        return table.ref() + ".delete( {_id:...} )";
    } 
    return table.ref() + ".find()";
  }
  
  public static String a(Entity paramEntity) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("{");
    a(paramEntity, 1, stringBuilder, false);
    stringBuilder.append("\n}");
    return stringBuilder.toString();
  }
  
  public static void a(Entity paramEntity, int paramInt, StringBuilder paramStringBuilder, boolean paramBoolean) {
    boolean bool = true;
    if (paramBoolean)
      paramStringBuilder.append('\n').append(a(paramInt++)).append("{"); 
    for (Attribute attribute : paramEntity.getAttributes()) {
      if (attribute instanceof Column) {
        Column column = (Column)attribute;
        if (!"_id".equals(attribute.getName())) {
          if (bool) {
            bool = false;
          } else {
            paramStringBuilder.append(",");
          } 
          paramStringBuilder.append('\n').append(a(paramInt));
          paramStringBuilder.append('"').append(attribute.getName()).append("\" : ");
          DataType dataType = attribute.getDataType();
          if (dataType.isNumeric()) {
            paramStringBuilder.append("0");
            continue;
          } 
          if (dataType.isBoolean()) {
            paramStringBuilder.append(column.hasDefaultValue() ? column.getDefaultValue() : "true");
            continue;
          } 
          if (dataType.isArray()) {
            paramStringBuilder.append("[\"\",...]");
            continue;
          } 
          if (dataType.isDate()) {
            paramStringBuilder.append("\"2016-01-01T00:00:00.000Z\"");
            continue;
          } 
          if (dataType.isJsonList()) {
            paramStringBuilder.append("[");
            if (column.hasChildEntity()) {
              a(column.getChildEntity(), paramInt + 1, paramStringBuilder, true);
              paramStringBuilder.append('\n').append(a(paramInt));
            } else if ("array[string]".equals(dataType.getName())) {
              paramStringBuilder.append("\"\",\"\"...");
            } else {
              paramStringBuilder.append("1,2...");
            } 
            paramStringBuilder.append("]");
            continue;
          } 
          if (dataType.isJsonMap() && column.hasChildEntity()) {
            paramStringBuilder.append("{");
            a(column.getChildEntity(), paramInt + 1, paramStringBuilder, false);
            paramStringBuilder.append('\n').append(a(paramInt));
            paramStringBuilder.append("}");
            continue;
          } 
          paramStringBuilder.append("\"\"");
        } 
      } 
    } 
    if (paramBoolean)
      paramStringBuilder.append('\n').append(a(--paramInt)).append("}"); 
  }
  
  private static String a(int paramInt) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("  ".repeat(Math.max(0, paramInt)));
    return stringBuilder.toString();
  }
}
