package com.wisecoders.dbs.diagram.fx;

import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.query.model.items.QueryColumn;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.DataTypeFormat;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.EscapeChars;
import com.wisecoders.dbs.sys.StringUtil;

public class ToolTipFactory {
  public static String a(AbstractUnit paramAbstractUnit) {
    if (paramAbstractUnit instanceof AbstractTable)
      return a((AbstractTable)paramAbstractUnit); 
    if (paramAbstractUnit instanceof Column)
      return b((Column)paramAbstractUnit); 
    if (paramAbstractUnit instanceof ForeignKey)
      return a((ForeignKey)paramAbstractUnit); 
    return paramAbstractUnit.getName();
  }
  
  public static String a(Attribute paramAttribute, boolean paramBoolean) {
    StringBuilder stringBuilder = new StringBuilder();
    boolean bool = true;
    for (Relation relation : paramAttribute.getEntity().getRelations()) {
      if (relation.getSourceAttributes().contains(paramAttribute)) {
        if (!bool)
          stringBuilder.append("\n"); 
        stringBuilder.append("↗ References ").append(paramBoolean ? relation.getTargetEntity().getNameWithSchemaName() : relation.getTargetEntity());
        stringBuilder.append(" ( ").append(ForeignKey.listAttributes(relation.getSourceAttributes(), " -> ", relation.getTargetAttributes())).append(" )");
        bool = false;
      } 
    } 
    for (Relation relation : paramAttribute.getEntity().getImportedRelations()) {
      if (relation.getTargetAttributes().contains(paramAttribute)) {
        if (!bool)
          stringBuilder.append("\n"); 
        stringBuilder.append("↙ Referred by ").append(paramBoolean ? relation.getEntity().getNameWithSchemaName() : relation.getEntity());
        stringBuilder.append(" ( ").append(ForeignKey.listAttributes(relation.getSourceAttributes(), " -> ", relation.getTargetAttributes())).append(" )");
        bool = false;
      } 
    } 
    return !stringBuilder.isEmpty() ? stringBuilder.toString() : null;
  }
  
  public static String a(Entity paramEntity) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramEntity.getName());
    if (paramEntity instanceof Table) {
      Table table = (Table)paramEntity;
      if (table.getRowCount() > -1L)
        stringBuilder.append(" - ").append(table.getRowCount()).append(" rows."); 
    } 
    if (paramEntity.getComment() != null)
      stringBuilder.append(a()).append("\n ").append(paramEntity.getComment()); 
    return stringBuilder.toString();
  }
  
  public static String b(Entity paramEntity) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(EscapeChars.titleForHtml(paramEntity.getSymbolicName())).append(" ")
      .append(EscapeChars.titleForHtml(paramEntity.getNameWithSchemaName()));
    if (paramEntity.getComment() != null)
      stringBuilder.append(b())
        .append(a(EscapeChars.titleForHtml(paramEntity.getComment()))); 
    return stringBuilder.toString();
  }
  
  public static String c(Entity paramEntity) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramEntity.getSymbolicName()).append(" ").append(paramEntity.getNameWithSchemaName());
    if (paramEntity.getComment() != null)
      stringBuilder.append("\n").append(paramEntity.getComment()); 
    return stringBuilder.toString();
  }
  
  public static String a(Attribute paramAttribute) {
    StringBuilder stringBuilder = new StringBuilder();
    if (paramAttribute instanceof Column) {
      Column column = (Column)paramAttribute;
      stringBuilder.append(column.getName()).append(" \n");
      stringBuilder.append(paramAttribute.getTypeString(DataTypeFormat.b));
      if (column.isMandatory())
        stringBuilder.append(" not null"); 
      if (column.hasDefaultValue())
        stringBuilder.append(" default ").append(column.getDefaultValue()); 
      stringBuilder.append(" ");
    } 
    if (paramAttribute.getComment() != null)
      stringBuilder.append(" \n").append(paramAttribute.getComment()); 
    stringBuilder.append("\n");
    return stringBuilder.toString();
  }
  
  public static String b(Attribute paramAttribute) {
    StringBuilder stringBuilder = new StringBuilder();
    if (paramAttribute instanceof QueryColumn)
      paramAttribute = ((QueryColumn)paramAttribute).x; 
    if (paramAttribute instanceof Column) {
      Column column = (Column)paramAttribute;
      stringBuilder.append(column.getName()).append(" ").append(paramAttribute.getTypeString(DataTypeFormat.b));
      if (column.isMandatory())
        stringBuilder.append(" not null"); 
      if (column.hasDefaultValue())
        stringBuilder.append(" default ").append(column.getDefaultValue()); 
      stringBuilder.append(" ");
      for (Relation relation : column.getEntity().getRelations()) {
        if (relation.getSourceAttributes().contains(paramAttribute))
          stringBuilder.append("\n ↗ ").append(relation.getEntity()).append("( ").append(StringUtil.join(relation.getTargetAttributes(), ", ")).append(" )"); 
      } 
      for (Relation relation : column.getEntity().getImportedRelations()) {
        if (relation.getTargetAttributes().contains(paramAttribute))
          stringBuilder.append("\n ↙ ").append(relation.getEntity()).append("( ").append(StringUtil.join(relation.getTargetAttributes(), ", ")).append(" )"); 
      } 
    } else {
      stringBuilder.append(paramAttribute.getName());
    } 
    if (paramAttribute.getComment() != null)
      stringBuilder.append(a()).append("\n ").append(paramAttribute.getComment()); 
    return (stringBuilder.length() > 0) ? stringBuilder.toString() : null;
  }
  
  public static String c(Attribute paramAttribute) {
    StringBuilder stringBuilder = new StringBuilder();
    if (paramAttribute instanceof Column) {
      Column column = (Column)paramAttribute;
      stringBuilder.append("&#10697;  ").append(EscapeChars.titleForHtml(column.getName())).append("\n     ");
      if (column.isMandatory())
        stringBuilder.append("* "); 
      stringBuilder.append(EscapeChars.titleForHtml(paramAttribute.getTypeString(DataTypeFormat.b).toLowerCase()));
      if (column.hasDefaultValue())
        stringBuilder.append(" default ").append(EscapeChars.titleForHtml(column.getDefaultValue())); 
      for (Relation relation : column.getEntity().getRelations()) {
        if (relation.getSourceAttributes().contains(paramAttribute))
          stringBuilder.append("\n &#8599; ").append(relation.getEntity()).append("( ").append(StringUtil.join(relation.getTargetAttributes(), ", ")).append(" )"); 
      } 
      for (Relation relation : column.getEntity().getImportedRelations()) {
        if (relation.getTargetAttributes().contains(paramAttribute))
          stringBuilder.append("\n &#8601; ").append(relation.getEntity()).append("( ").append(StringUtil.join(relation.getTargetAttributes(), ", ")).append(" )"); 
      } 
    } 
    if (paramAttribute.getComment() != null) {
      stringBuilder.append(b());
      stringBuilder.append(a(EscapeChars.titleForHtml(paramAttribute.getComment())));
    } 
    return stringBuilder.toString();
  }
  
  public static String d(Attribute paramAttribute) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramAttribute.getTypeString(DataTypeFormat.b));
    if (paramAttribute.isMandatory())
      stringBuilder.append(" not null"); 
    if (paramAttribute instanceof Column) {
      Column column = (Column)paramAttribute;
      if (column.hasDefaultValue())
        stringBuilder.append(" default ").append(column.getDefaultValue()); 
    } 
    return stringBuilder.toString();
  }
  
  public static String b(Attribute paramAttribute, boolean paramBoolean) {
    StringBuilder stringBuilder = new StringBuilder();
    for (Relation relation : paramAttribute.getEntity().getRelations()) {
      if (relation.getSourceAttributes().contains(paramAttribute)) {
        if (stringBuilder.length() > 0)
          stringBuilder.append("\n"); 
        stringBuilder.append("References ").append(paramBoolean ? relation.getTargetEntity().getNameWithSchemaName() : relation.getTargetEntity());
        stringBuilder.append(" ( ").append(ForeignKey.listAttributes(relation.getSourceAttributes(), " -> ", relation.getTargetAttributes())).append(" ) ");
      } 
    } 
    for (Relation relation : paramAttribute.getEntity().getImportedRelations()) {
      if (relation.getTargetAttributes().contains(paramAttribute)) {
        if (stringBuilder.length() > 0)
          stringBuilder.append("\n"); 
        stringBuilder.append("Referred by ").append(paramBoolean ? relation.getEntity().getNameWithSchemaName() : relation.getEntity());
        stringBuilder.append(" ( ").append(ForeignKey.listAttributes(relation.getSourceAttributes(), " -> ", relation.getTargetAttributes())).append(" ) ");
      } 
    } 
    return "&#x1F517;  " + EscapeChars.titleForHtml(stringBuilder.toString());
  }
  
  public static String a(Relation paramRelation) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(paramRelation.getEntity()).append(" references ").append(paramRelation.getTargetEntity()).append(" ( ");
    stringBuilder.append(ForeignKey.listAttributes(paramRelation.getSourceAttributes(), " -> ", paramRelation.getTargetAttributes())).append(" )");
    return (stringBuilder.length() > 0) ? stringBuilder.toString() : null;
  }
  
  public static String b(Relation paramRelation) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("'").append(paramRelation.getName()).append("' ").append(paramRelation.getEntity().getSymbolicName().toLowerCase()).append(" '");
    stringBuilder.append(paramRelation.getEntity()).append("' references '").append(paramRelation.getTargetEntity()).append("' ( ");
    stringBuilder.append(ForeignKey.listAttributes(paramRelation.getSourceAttributes(), " -> ", paramRelation.getTargetAttributes())).append(" )");
    return stringBuilder.toString();
  }
  
  public static String c(Relation paramRelation) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("&#x1F517;  Foreign Key ").append(EscapeChars.titleForHtml(paramRelation.getName())).append("\n");
    stringBuilder.append(paramRelation.getEntity()).append(" ref ").append(EscapeChars.titleForHtml(String.valueOf(paramRelation.getTargetEntity()))).append(" ( ");
    stringBuilder.append(EscapeChars.titleForHtml(ForeignKey.listAttributes(paramRelation.getSourceAttributes(), " -> ", paramRelation.getTargetAttributes()))).append(" )");
    if (paramRelation.getComment() != null)
      stringBuilder.append(b())
        .append(EscapeChars.titleForHtml(paramRelation.getComment())); 
    return stringBuilder.toString();
  }
  
  static String e(Attribute paramAttribute) {
    StringBuilder stringBuilder = new StringBuilder();
    boolean bool = true;
    if (paramAttribute.getEntity() instanceof Table)
      for (Index index : ((Table)paramAttribute.getEntity()).getIndexes()) {
        if (index.getColumns().contains(paramAttribute)) {
          if (!bool)
            stringBuilder.append("\n"); 
          switch (ToolTipFactory$1.a[index.getType().ordinal()]) {
            case 1:
              stringBuilder.append(" Primary Key ");
              break;
            case 2:
            case 3:
              stringBuilder.append(" Unique Index ");
              break;
            case 4:
              stringBuilder.append(" Index ");
              break;
          } 
          stringBuilder.append(" ( ").append(Index.listAttributes(index.getColumns())).append(" ) ");
          if (index.getComment() != null)
            stringBuilder.append(a()).append("\n").append(index.getComment()); 
          bool = false;
        } 
      }  
    return stringBuilder.toString();
  }
  
  public static String f(Attribute paramAttribute) {
    StringBuilder stringBuilder = new StringBuilder();
    if (paramAttribute.getEntity() instanceof Table)
      for (Index index : ((Table)paramAttribute.getEntity()).getIndexes()) {
        if (index.getColumns().contains(paramAttribute)) {
          switch (ToolTipFactory$1.a[index.getType().ordinal()]) {
            case 1:
              stringBuilder.append("&#x1F511;  Pk ");
              break;
            case 2:
            case 3:
              stringBuilder.append("&#x1F50D;  Unq ");
              break;
            default:
              stringBuilder.append("&#x1F50D;  ");
              break;
          } 
          stringBuilder.append(EscapeChars.titleForHtml(index.getName())).append(" ( ").append(EscapeChars.titleForHtml(Index.listAttributes(index.getColumns()))).append(" ) ");
          if (index.getComment() != null)
            stringBuilder.append(b()).append(EscapeChars.titleForHtml(index.getComment())); 
        } 
      }  
    return stringBuilder.toString();
  }
  
  public static String a() {
    return "\n " + "―".repeat(12);
  }
  
  private static String b() {
    return "\n " + "⎯".repeat(12) + "\n";
  }
  
  private static final String a = " ".repeat(6);
  
  private static String a(String paramString) {
    StringBuilder stringBuilder = new StringBuilder();
    boolean bool = false;
    for (String str : paramString.split("\\r?\\n")) {
      if (bool)
        stringBuilder.append("\n"); 
      stringBuilder.append(a).append(str);
      bool = true;
    } 
    return stringBuilder.toString();
  }
}
