package com.wisecoders.dbs.project.html;

import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Constraint;
import com.wisecoders.dbs.schema.DataTypeFormat;
import com.wisecoders.dbs.schema.Expose;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Function;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.Layout;
import com.wisecoders.dbs.schema.Procedure;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Sequence;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.Trigger;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sys.EscapeChars;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.UniqueArrayList;
import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MarkdownWriter {
  private static final String a = "&#128273; ";
  
  private static final String b = "&#128269; ";
  
  private static final String c = "&#128270; ";
  
  private static final String d = "&#11016; ";
  
  private static final String e = "&#11019; ";
  
  public static void a(Expose paramExpose) {
    PrintWriter printWriter = new PrintWriter(paramExpose.file, StandardCharsets.UTF_8);
    String str1 = paramExpose.getString("title", "Documentation");
    a(str1, paramExpose, printWriter);
    boolean bool = (paramExpose.layouts.size() > 1) ? true : false;
    if (bool) {
      printWriter.print("## Layouts\n\n");
      byte b = 1;
      for (Layout layout : paramExpose.layouts) {
        printWriter.printf("%d. [%s](%s)\n", new Object[] { Integer.valueOf(b), layout.getName(), a("# " + layout.getName()) });
        b++;
      } 
      printWriter.printf("\n", new Object[0]);
      printWriter.flush();
      if (paramExpose.is("TOC")) {
        a(paramExpose, printWriter, true);
        printWriter.print("\n");
      } 
    } 
    String str2 = null;
    byte b1 = 0;
    for (byte b2 = 0; b2 < paramExpose.layouts.size(); b2++) {
      Layout layout = paramExpose.layouts.get(b1);
      printWriter.printf("\n\n\n### %s\n", new Object[] { layout.getName() });
      if (bool) {
        if (str2 != null)
          printWriter.printf("[Prev](%s)\n", new Object[] { "# " + a(str2) }); 
        printWriter.print("[Index](#legend) ");
        if (b1 < paramExpose.layouts.size() - 1)
          printWriter.printf("[Next](%s) ", new Object[] { "# " + a(((Layout)paramExpose.layouts.get(b1 + 1)).getName()) }); 
        b1++;
        printWriter.print("\n");
        str2 = layout.getName();
      } 
      boolean bool1 = (paramExpose.layouts.size() > 1) ? true : false;
      if (paramExpose.is("image")) {
        String str = "./";
        if (bool1) {
          str = "./" + StringUtil.escapeForFileName(StringUtil.getFileNameWithoutExtension(paramExpose.file)) + "/";
          (new File(paramExpose.file.toURI().resolve(str))).mkdir();
        } 
        str = str + str;
        File file = new File(paramExpose.file.toURI().resolve(str));
        PrintWriter printWriter1 = new PrintWriter(file, StandardCharsets.UTF_8);
        SvgWriter.a(paramExpose, layout.diagram, printWriter1, str1);
        printWriter1.close();
        printWriter.print("![img](" + str + ")\n");
      } 
      printWriter.print("\n");
      if (paramExpose.is("text")) {
        printWriter.print("\n");
        if (!bool && paramExpose.is("TOC"))
          a(paramExpose, printWriter, false); 
        List list = SvgWriter.a(layout.diagram);
        for (Depict depict : list) {
          if (depict.entity instanceof Table)
            a(printWriter, (Table)depict.entity, paramExpose); 
        } 
        for (Depict depict : list) {
          if (depict.entity instanceof View)
            a(printWriter, paramExpose, (View)depict.entity, depict); 
        } 
        if (paramExpose.is("functions")) {
          ArrayList<Schema> arrayList = new ArrayList();
          for (Depict depict : list) {
            Schema schema = depict.getEntity().getSchema();
            if ((!schema.procedures.isEmpty() || !schema.functions.isEmpty()) && !arrayList.contains(schema)) {
              arrayList.add(schema);
              a(printWriter, schema);
            } 
          } 
        } 
        if (paramExpose.is("sequences")) {
          ArrayList<Schema> arrayList = new ArrayList();
          for (Depict depict : list) {
            Schema schema = depict.getEntity().getSchema();
            if (!schema.sequences.isEmpty() && !arrayList.contains(schema)) {
              arrayList.add(schema);
              a(printWriter, schema, paramExpose);
            } 
          } 
        } 
        printWriter.print("\n");
      } 
    } 
    printWriter.print("\n");
    printWriter.flush();
    printWriter.close();
  }
  
  private static String a(String paramString) {
    return (paramString != null) ? EscapeChars.forMD(paramString.toLowerCase()).replaceAll(" ", "%20") : "";
  }
  
  static void a(PrintWriter paramPrintWriter, Table paramTable, Expose paramExpose) {
    paramPrintWriter.printf("\n", new Object[0]);
    paramPrintWriter.printf("### %s %s \n", new Object[] { paramTable
          .getSymbolicName(), paramTable
          .getNameWithSchemaName() });
    if (StringUtil.isFilledTrim(paramTable.getComment()))
      paramPrintWriter.print(EscapeChars.forMD(paramTable.getComment()) + "\n\n"); 
    boolean bool = paramTable.columns.hasComments();
    paramPrintWriter.println(b("Idx,Name,Data Type" + (bool ? ",Description" : "")));
    for (Column column : paramTable.getAttributes())
      a(paramPrintWriter, column, bool); 
    paramPrintWriter.println("\n");
    if (paramExpose.is("indexes")) {
      boolean bool1 = paramTable.indexes.hasComments();
      if (!paramTable.getIndexes().isEmpty()) {
        paramPrintWriter.println("##### Indexes ");
        paramPrintWriter.println(b("Type,Name,On" + (bool1 ? ",Description" : "")));
        for (Index index : paramTable.getIndexes()) {
          paramPrintWriter.printf("| %s | %s | ON %s", new Object[] { (index.getType() == IndexType.PRIMARY_KEY) ? "&#128273; " : (
                (index.getType() == IndexType.UNIQUE_KEY || index.getType() == IndexType.UNIQUE_INDEX) ? 
                "&#128269; " : 
                "&#128270; "), 
                EscapeChars.forMD(index.getName()), 
                EscapeChars.forMD(Index.listAttributes(index.getColumns())) });
          if (bool1)
            paramPrintWriter.printf(" | %s ", new Object[] { EscapeChars.forMDWithNewLines(index.getCommentNotNull()) }); 
          paramPrintWriter.printf("|\n", new Object[0]);
        } 
        paramPrintWriter.printf("\n", new Object[0]);
      } 
    } 
    if (paramExpose.is("foreignKeys")) {
      boolean bool1 = paramTable.foreignKeys.hasComments();
      if (!paramTable.getRelations().isEmpty()) {
        paramPrintWriter.printf("##### %s\n", new Object[] { (paramTable.is(UnitProperty.f).booleanValue() || paramTable.is(UnitProperty.g).booleanValue()) ? "Relationships" : "Foreign Keys" });
        paramPrintWriter.println(b("Type,Name,On" + (bool1 ? ",Description" : "")));
        for (ForeignKey foreignKey : paramTable.getRelations()) {
          paramPrintWriter.printf("| %s | %s ", new Object[] { foreignKey.isVirtual() ? "Vir" : "", foreignKey.getName() });
          paramPrintWriter.printf("| ( ", new Object[0]);
          paramPrintWriter.printf(EscapeChars.forMD(Index.listAttributes(foreignKey.getSourceAttributes())), new Object[0]);
          paramPrintWriter.printf(" ) ref ", new Object[0]);
          paramPrintWriter.printf("[%s](#%s) (", new Object[] { EscapeChars.forMD(foreignKey.getTargetEntity().getNameWithSchemaName()), EscapeChars.forMD(foreignKey.getTargetEntity().getName()) });
          paramPrintWriter.printf(EscapeChars.forMD(Index.listAttributes(foreignKey.getTargetAttributes())), new Object[0]);
          paramPrintWriter.printf(") ", new Object[0]);
          if (bool1)
            paramPrintWriter.printf("| %s ", new Object[] { EscapeChars.forMDWithNewLines(foreignKey.getCommentNotNull()) }); 
          paramPrintWriter.printf("|\n", new Object[0]);
        } 
        paramPrintWriter.println("\n");
      } 
      boolean bool2 = paramTable.constraints.hasComments();
      if (!paramTable.constraints.isEmpty()) {
        paramPrintWriter.printf("##### %s\n", new Object[] { "Constraints" });
        paramPrintWriter.println(b("Name,Definition" + (bool1 ? ",Description" : "")));
        for (Constraint constraint : paramTable.constraints) {
          paramPrintWriter.printf("| %s | %s ", new Object[] { constraint.getName(), EscapeChars.forMD(constraint.getText()) });
          if (bool2)
            paramPrintWriter.printf("| %s ", new Object[] { EscapeChars.forMD(constraint.getCommentNotNull()) }); 
          paramPrintWriter.printf("|\n", new Object[0]);
        } 
        paramPrintWriter.println("\n");
      } 
      ArrayList<Trigger> arrayList = new ArrayList();
      for (Trigger trigger : paramTable.schema.triggers) {
        if (trigger.getOwningTable() == paramTable)
          arrayList.add(trigger); 
      } 
      if (!arrayList.isEmpty() && paramExpose.is("triggers")) {
        paramPrintWriter.printf("##### Triggers\n", new Object[0]);
        paramPrintWriter.println(b("Name,Definition"));
        for (Trigger trigger : arrayList) {
          paramPrintWriter.printf("### Trigger %s \n %s \n ```\n%s\n``` \n", new Object[] { trigger
                .getName(), 
                EscapeChars.forMD(trigger.getCommentNotNull()), 
                EscapeChars.forMD(trigger.getText()) });
        } 
      } 
      if (StringUtil.isFilledTrim(paramTable.getOptions())) {
        paramPrintWriter.printf("##### Options \n", new Object[0]);
        paramPrintWriter.printf("%s \n", new Object[] { EscapeChars.forMD(paramTable.getOptions()) });
      } 
    } 
    paramPrintWriter.printf("\n", new Object[0]);
  }
  
  private static String b(String paramString) {
    StringBuilder stringBuilder = new StringBuilder("|");
    for (String str : paramString.split(","))
      stringBuilder.append(str).append(" |"); 
    stringBuilder.append("\n|");
    for (String str : paramString.split(","))
      stringBuilder.append("---|"); 
    return stringBuilder.toString();
  }
  
  private static void a(PrintWriter paramPrintWriter, Column paramColumn, boolean paramBoolean) {
    StringBuilder stringBuilder = new StringBuilder();
    if (paramColumn.isMandatory())
      stringBuilder.append("* "); 
    if (paramColumn.hasMarker(1)) {
      stringBuilder.append("&#128273;  ");
    } else if (paramColumn.hasMarker(8)) {
      stringBuilder.append("&#128269; ");
    } else if (paramColumn.hasMarker(4)) {
      stringBuilder.append("&#128270; ");
    } 
    if (paramColumn.hasMarker(32)) {
      stringBuilder.append("&#11016; ");
    } else if (paramColumn.hasMarker(64)) {
      stringBuilder.append("&#11019; ");
    } 
    if (stringBuilder.length() == 0)
      stringBuilder.append(" "); 
    paramPrintWriter.printf("| %s", new Object[] { stringBuilder });
    paramPrintWriter.printf("| %s", new Object[] { EscapeChars.forMD(paramColumn.getNameWithPath()) });
    paramPrintWriter.printf("| %s ", new Object[] { EscapeChars.forMD(paramColumn.getTypeString(DataTypeFormat.b)) });
    if (paramColumn.isUnsigned())
      paramPrintWriter.printf("UNSIGNED ", new Object[0]); 
    if (paramColumn.isIdentity())
      paramPrintWriter.printf(paramColumn.getIdentity(), new Object[0]); 
    paramPrintWriter.printf(EscapeChars.forMD(paramColumn.getOptions() + " "), new Object[0]);
    if (paramColumn.hasDefaultValue())
      paramPrintWriter.printf("DEFAULT %s ", new Object[] { paramColumn.getDefaultValue() }); 
    if (paramBoolean)
      paramPrintWriter.printf("| %s ", new Object[] { EscapeChars.forMDWithNewLines(paramColumn.getCommentNotNull()) }); 
    paramPrintWriter.printf("|\n", new Object[0]);
    if (paramColumn.hasChildEntity())
      for (Column column : (paramColumn.getChildEntity()).columns)
        a(paramPrintWriter, column, paramBoolean);  
  }
  
  static void a(PrintWriter paramPrintWriter, Expose paramExpose, View paramView, Depict paramDepict) {
    paramPrintWriter.printf("\n\n", new Object[0]);
    paramPrintWriter.printf("## %s %s\n\n", new Object[] { paramView.getSymbolicName(), paramView.getName() });
    if (StringUtil.isFilledTrim(paramView.getComment()))
      paramPrintWriter.printf(EscapeChars.forMD(paramView.getComment()) + "\n\n", new Object[0]); 
    boolean bool = HTML5Writer.hasComments(paramView);
    if (paramExpose.is("view_query"))
      paramPrintWriter.printf("Query %s\n", new Object[] { paramView.getName() }); 
    paramPrintWriter.printf("```\n%s\n```\n\n", new Object[] { EscapeChars.forMD(paramView.getScript()) });
    paramPrintWriter.println(b("Idx,Name,Type" + (bool ? ",Description" : "")));
    for (Column column : paramView.getAttributes())
      a(paramPrintWriter, column, bool); 
  }
  
  static void a(PrintWriter paramPrintWriter, Schema paramSchema) {
    paramPrintWriter.printf("## %s %s\n", new Object[] { paramSchema.getSymbolicName(), paramSchema.getName() });
    if (!paramSchema.functions.isEmpty()) {
      paramPrintWriter.printf("### Functions \n", new Object[0]);
      for (Function function : paramSchema.functions) {
        paramPrintWriter.printf("%s \n```\n%s\n```\n", new Object[] { EscapeChars.forMD(function.getName()), EscapeChars.forMD(function.getCommentNotNull()) });
      } 
    } 
    if (!paramSchema.procedures.isEmpty()) {
      paramPrintWriter.printf("### Procedures\n", new Object[0]);
      for (Procedure procedure : paramSchema.procedures) {
        paramPrintWriter.printf("%s \n```\n%s\n```\n", new Object[] { EscapeChars.forMD(procedure.getName()), EscapeChars.forMD(procedure.getCommentNotNull()) });
      } 
      paramPrintWriter.print("\n\n");
    } 
  }
  
  static void a(PrintWriter paramPrintWriter, Schema paramSchema, Expose paramExpose) {
    paramPrintWriter.printf("## %s %s\n", new Object[] { paramSchema.getSymbolicName(), paramSchema.getName() });
    if (!paramSchema.sequences.isEmpty()) {
      paramPrintWriter.printf(b("Name,Description"), new Object[0]);
      for (Sequence sequence : paramSchema.sequences) {
        paramPrintWriter.printf("| %s | %s |", new Object[] { EscapeChars.forMD(sequence.getName()), EscapeChars.forMDWithNewLines(sequence.getCommentNotNull()) });
      } 
      paramPrintWriter.print("\n\n");
    } 
  }
  
  private static void a(Expose paramExpose, PrintWriter paramPrintWriter, boolean paramBoolean) {
    a(paramExpose, paramPrintWriter, paramBoolean, "Tables", Table.class);
    a(paramExpose, paramPrintWriter, paramBoolean, "Views", View.class);
  }
  
  private static void a(Expose paramExpose, PrintWriter paramPrintWriter, boolean paramBoolean, String paramString, Class paramClass) {
    UniqueArrayList uniqueArrayList = new UniqueArrayList();
    for (Layout layout : paramExpose.layouts)
      uniqueArrayList.addAll(layout.getEntities()); 
    uniqueArrayList.sort((paramEntity1, paramEntity2) -> paramEntity1.getNameWithSchemaName().compareToIgnoreCase(paramEntity2.getNameWithSchemaName()));
    boolean bool = false;
    byte b = 0;
    for (Entity entity : uniqueArrayList) {
      if (paramClass.isInstance(entity)) {
        if (!bool) {
          paramPrintWriter.printf("## %s\n\n", new Object[] { paramString });
          bool = true;
        } 
        a(paramExpose, paramPrintWriter, ++b, paramBoolean, entity);
      } 
    } 
    if (bool)
      paramPrintWriter.print("\n"); 
  }
  
  private static void a(Expose paramExpose, PrintWriter paramPrintWriter, int paramInt, boolean paramBoolean, Entity paramEntity) {
    paramPrintWriter.printf("%d. [%s](#%s) ", new Object[] { Integer.valueOf(paramInt), paramEntity.getNameWithSchemaName(), a(paramEntity.getSymbolicName() + " " + paramEntity.getSymbolicName()) });
    if (paramBoolean) {
      paramPrintWriter.printf(" ( ", new Object[0]);
      byte b = 1;
      for (Layout layout : paramExpose.layouts) {
        if (layout.containsDepictForEntity(paramEntity))
          paramPrintWriter.printf(" [%d](%d) ", new Object[] { Integer.valueOf(b), Integer.valueOf(b) }); 
        b++;
      } 
      paramPrintWriter.printf(")", new Object[0]);
    } 
    paramPrintWriter.printf("\n", new Object[0]);
  }
  
  private static void a(String paramString, Expose paramExpose, PrintWriter paramPrintWriter) {
    paramPrintWriter.printf("#%s\n", new Object[] { paramString });
    paramPrintWriter.printf("Generated using [DbSchema](https://dbschema.com)\n\n", new Object[0]);
  }
}
