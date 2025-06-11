package com.wisecoders.dbs.project.html;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.model.AbstractTable;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.diagram.model.TreeSelection;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Constraint;
import com.wisecoders.dbs.schema.DataTypeFormat;
import com.wisecoders.dbs.schema.Expose;
import com.wisecoders.dbs.schema.ForeignKey;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.schema.IndexType;
import com.wisecoders.dbs.schema.Layout;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Sequence;
import com.wisecoders.dbs.schema.Sql;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.Trigger;
import com.wisecoders.dbs.schema.UserDataType;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sys.DoNotObfuscate;
import com.wisecoders.dbs.sys.EscapeChars;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.UniqueArrayList;
import java.io.File;
import java.io.PrintWriter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@DoNotObfuscate
public class HTML5Writer {
  private static final String a = "<a href='https://dbschema.com' class='text-secondary small'>&nbsp;&nbsp;&nbsp;Generated using DbSchema</a><br>";
  
  public static void generateHtml5(Expose paramExpose) {
    PrintWriter printWriter = new PrintWriter(paramExpose.file, StandardCharsets.UTF_8);
    String str = paramExpose.getString("title", "Documentation");
    a(paramExpose.getString("title"), paramExpose, printWriter);
    printWriter.printf("<body class='%s'>\n", new Object[] { paramExpose.getBoolean("darkTheme", false) ? "" : "bg-light" });
    boolean bool = (paramExpose.layouts.size() > 1) ? true : false;
    if (bool) {
      printWriter.println("<div class='container'>");
      a(str, paramExpose, printWriter);
      printWriter.println("\n\n\n<a name='legend'/>");
      printWriter.printf("<h3>%s</h3>\n", new Object[] { str });
      printWriter.println("<div class='card'><div class='card-body'><h5 class='card-title'>Layouts</h5><ul>");
      byte b1 = 1;
      for (Layout layout : paramExpose.layouts) {
        printWriter.printf("<li><a href='%s' class='lg'>%s</a><br>", new Object[] { "#layout" + b1, "" + b1 + ". " + b1 });
        b1++;
      } 
      printWriter.println("</ul>");
      printWriter.println("<a href='https://dbschema.com' class='text-secondary small'>&nbsp;&nbsp;&nbsp;Generated using DbSchema</a><br>");
      printWriter.printf("</div></div><br><br>", new Object[0]);
      printWriter.flush();
      if (paramExpose.is("TOC"))
        a(paramExpose, printWriter, true); 
      printWriter.println("</div>");
    } 
    byte b = 1;
    URI uRI = null;
    HashMap<Object, Object> hashMap1 = new HashMap<>();
    HashMap<Object, Object> hashMap2 = new HashMap<>();
    for (Layout layout : paramExpose.layouts) {
      printWriter.println("<div class='container-fluid' style='text-align: center;'>");
      printWriter.printf("\n\n\n<a name='layout" + b + "' style='position:sticky; top:0;'/>", new Object[0]);
      printWriter.printf("<h4>%s</h4>\n", new Object[] { layout.getName() });
      if (bool) {
        if (b > 1)
          printWriter.println("<a href='#layout" + b - 1 + "' class='small'>Prev</a> "); 
        printWriter.println("<a href='#legend' class='small'>Index</a> ");
        if (b < paramExpose.layouts.size())
          printWriter.println("<a href='#layout" + b + 1 + "' class='small'>Next</a>"); 
        b++;
        printWriter.println("<br>");
      } 
      if (paramExpose.is("image"))
        if (paramExpose.is("separateSVGFile")) {
          uRI = paramExpose.file.toURI().resolve(StringUtil.escapeForFileName(StringUtil.getFileNameWithoutExtension(paramExpose.file) + "/"));
          if (uRI == null && !(new File(uRI)).exists())
            (new File(uRI)).mkdir(); 
          URI uRI1 = uRI.resolve(StringUtil.escapeForFileName("./" + layout.getName() + ".svg"));
          PrintWriter printWriter1 = new PrintWriter(new File(uRI1), StandardCharsets.UTF_8);
          SvgWriter.a(paramExpose, layout.diagram, printWriter1, str);
          printWriter1.close();
          printWriter.println("<object data='" + uRI1.toASCIIString() + "' type='image/svg+xml'>\n    <embed src='" + uRI1
              .toASCIIString() + "' type='image/svg+xml'/>\n</object>\n");
        } else {
          printWriter.println("<div class='svgContainer'>");
          SvgWriter.a(paramExpose, layout.diagram, printWriter, str);
          printWriter.println("</div>");
          printWriter.println(StringUtil.readStringFromInputStream(HTML5Writer.class.getResourceAsStream("resources/html-zoom-panning.html")));
        }  
      printWriter.println("</div>");
      for (Depict depict : layout.diagram.depicts) {
        if (!hashMap2.containsKey(depict.getEntity())) {
          hashMap2.put(depict.getEntity(), depict);
          hashMap1.put(depict, layout);
        } 
      } 
    } 
    if (paramExpose.is("text")) {
      printWriter.println("<div class='container pt-2'>\n");
      if (!bool && paramExpose.is("TOC"))
        a(paramExpose, printWriter, false); 
      List list = sortDepicts(hashMap1.keySet());
      UniqueArrayList uniqueArrayList = new UniqueArrayList();
      for (Depict depict : list) {
        if (depict.entity instanceof Table) {
          if (!uniqueArrayList.contains(depict.entity.getSchema())) {
            a(printWriter, depict.getEntity().getSchema(), paramExpose);
            uniqueArrayList.add(depict.entity.getSchema());
          } 
          a(printWriter, (Table)depict.entity, depict, (Layout)hashMap1.get(depict), paramExpose);
        } 
        if (depict.entity instanceof View) {
          if (!uniqueArrayList.contains(depict.entity.getSchema())) {
            a(printWriter, depict.getEntity().getSchema(), paramExpose);
            uniqueArrayList.add(depict.entity.getSchema());
          } 
          a(printWriter, (View)depict.entity, depict, (Layout)hashMap1.get(depict), paramExpose);
        } 
      } 
      if (paramExpose.is("functions")) {
        ArrayList<Schema> arrayList = new ArrayList();
        for (Depict depict : list) {
          Schema schema = depict.getEntity().getSchema();
          if ((!schema.procedures.isEmpty() || !schema.functions.isEmpty()) && !arrayList.contains(schema)) {
            arrayList.add(schema);
            a(printWriter, "Functions", schema.functions, paramExpose);
            a(printWriter, "Procedures", schema.procedures, paramExpose);
          } 
        } 
      } 
      if (paramExpose.is("sequences")) {
        ArrayList<Schema> arrayList = new ArrayList();
        for (Depict depict : list) {
          Schema schema = depict.getEntity().getSchema();
          if (!schema.sequences.isEmpty() && !arrayList.contains(schema)) {
            arrayList.add(schema);
            a(printWriter, schema);
          } 
        } 
      } 
      if (paramExpose.is("user_data_type")) {
        ArrayList<Schema> arrayList = new ArrayList();
        for (Depict depict : list) {
          Schema schema = depict.getEntity().getSchema();
          if (!schema.userDataTypes.isEmpty() && !arrayList.contains(schema)) {
            arrayList.add(schema);
            b(printWriter, schema, paramExpose);
          } 
        } 
      } 
      printWriter.println("</div>");
    } 
    printWriter.println("<br><br></body></html>");
    printWriter.flush();
    printWriter.close();
  }
  
  private static void a(Expose paramExpose, PrintWriter paramPrintWriter, boolean paramBoolean) {
    paramPrintWriter.println("<div class='card'><div class='card-body'>");
    a(paramExpose, paramPrintWriter, paramBoolean, StringUtil.getPluralOf((paramExpose.getDbms()).tableAlias.c_()), Table.class);
    a(paramExpose, paramPrintWriter, paramBoolean, "Views", View.class);
    paramPrintWriter.printf("</div></div><br>", new Object[0]);
  }
  
  private static void a(Expose paramExpose, PrintWriter paramPrintWriter, boolean paramBoolean, String paramString, Class paramClass) {
    UniqueArrayList uniqueArrayList1 = new UniqueArrayList();
    for (Layout layout : paramExpose.layouts)
      uniqueArrayList1.addAll(layout.getEntities()); 
    uniqueArrayList1.sort((paramEntity1, paramEntity2) -> paramEntity1.getNameWithSchemaName().compareToIgnoreCase(paramEntity2.getNameWithSchemaName()));
    UniqueArrayList uniqueArrayList2 = new UniqueArrayList();
    for (Entity entity : uniqueArrayList1) {
      if (paramClass.isInstance(entity))
        uniqueArrayList2.add(entity.getSchema()); 
    } 
    boolean bool = false;
    for (Schema schema : uniqueArrayList2) {
      if (!bool) {
        paramPrintWriter.printf("<h5 class='card-title'>%s</h5>\n<ul>", new Object[] { paramString });
        bool = true;
      } 
      paramPrintWriter.printf("\n<li>%s <a href='#%s'/> %s</a>", new Object[] { (paramExpose.getDbms()).schemaAlias.c_(), EscapeChars.forURL(schema.getNameWithCatalog()), schema.getNameWithCatalog() });
      paramPrintWriter.println("<ul>");
      for (Entity entity : uniqueArrayList1) {
        if (paramClass.isInstance(entity) && (schema.tables.contains(entity) || schema.views.contains(entity))) {
          paramPrintWriter.printf("\n<li><a href='#%s'/>%s</a>", new Object[] { EscapeChars.forURL(entity.getNameWithSchemaName()), entity.getName() });
          if (paramBoolean) {
            paramPrintWriter.printf(" <small>[", new Object[0]);
            byte b = 1;
            for (Layout layout : paramExpose.layouts) {
              if (layout.containsDepictForEntity(entity))
                paramPrintWriter.printf(" <a href='#layout%d' title='%s' class='link-secondary'/>%s</a>", new Object[] { Integer.valueOf(b), EscapeChars.forHtmlNewLinesAsBR(layout.getName()), layout.getName() }); 
              b++;
            } 
            paramPrintWriter.printf(" ]</small> ", new Object[0]);
          } 
        } 
      } 
      paramPrintWriter.printf("\n</ul>", new Object[0]);
    } 
    if (bool)
      paramPrintWriter.println("</ul>"); 
  }
  
  public static List sortDepicts(Set paramSet) {
    TreeSelection treeSelection = new TreeSelection();
    for (Depict depict : paramSet)
      treeSelection.select(depict.getEntity()); 
    ArrayList<Depict> arrayList = new ArrayList();
    for (Depict depict : paramSet) {
      if (treeSelection.isSelected(depict.getEntity()))
        arrayList.add(depict); 
    } 
    arrayList.sort(Comparator.comparing(paramDepict -> paramDepict.getEntity().getNameWithSchemaName()));
    return arrayList;
  }
  
  static void a(PrintWriter paramPrintWriter, Schema paramSchema, Expose paramExpose) {
    paramPrintWriter.printf("<br><br>\n", new Object[0]);
    paramPrintWriter.printf("<div class='card'><div class='card-body'><a name='%s' style='cursor:pointer;'>", new Object[] { EscapeChars.forURL(paramSchema.getNameWithCatalog()) });
    paramPrintWriter.printf("<h5 class='card-title'>%s %s</h5>", new Object[] { (paramExpose.getDbms()).schemaAlias.c_(), paramSchema
          .getNameWithCatalog() });
    if (StringUtil.isFilledTrim(paramSchema.getComment()))
      paramPrintWriter.printf("<p class='text-muted mb-5'>%s</p>\n", new Object[] { a(paramSchema) }); 
    paramPrintWriter.printf("</div></div>\n\n", new Object[0]);
  }
  
  static void a(PrintWriter paramPrintWriter, Table paramTable, Depict paramDepict, Layout paramLayout, Expose paramExpose) {
    paramPrintWriter.printf("<br><br>\n", new Object[0]);
    paramPrintWriter.printf("<div class='card'><div class='card-body'><a name='%s' %s style='cursor:pointer;'>", new Object[] { EscapeChars.forURL(paramTable.getNameWithSchemaName()), 
          a(paramDepict) });
    paramPrintWriter.printf("<h5 class='card-title'>%s %s</h5>", new Object[] { paramTable
          .getSymbolicName(), 
          paramLayout.diagram.isShowSchemaName() ? paramTable.getNameWithSchemaName() : paramTable.getDisplayName(paramLayout.diagram) });
    paramPrintWriter.printf("</a>\n", new Object[0]);
    if (StringUtil.isFilledTrim(paramTable.getComment()) || paramTable.getUnitProperty(UnitProperty.e) != null)
      paramPrintWriter.printf("<p class=\"text-muted\">%s</p>\n", new Object[] { a(paramTable) }); 
    a(paramPrintWriter, paramTable, paramExpose);
    boolean bool = hasComments(paramTable);
    paramPrintWriter.printf("<table class='table table-sm small' style='table-layout: fixed; word-wrap: break-word;'>\n", new Object[0]);
    paramPrintWriter.printf("<thead>\n", new Object[0]);
    if (bool) {
      paramPrintWriter.printf("<tr><th style='width:5%%'>Idx</th><th style='width:20%%'>%s Name</th><th style='width:23%%'>Definition</th><th style='width:52%%'>Description</th></tr>\n", new Object[] { (paramExpose.getDbms()).columnAlias.c_() });
    } else {
      paramPrintWriter.printf("<tr><th style='width:5%%'>Idx</th><th style='width:40%%'>%s Name</th><th>Data Type</th></tr>\n", new Object[] { (paramExpose.getDbms()).columnAlias.c_() });
    } 
    paramPrintWriter.printf("</thead>\n", new Object[0]);
    paramPrintWriter.printf("<tbody>\n", new Object[0]);
    for (Column column : paramTable.getAttributes()) {
      if (paramExpose.getBoolean("allColumn", true) || paramDepict.getVisibleAttributes().contains(column))
        a(paramPrintWriter, paramTable, column, bool); 
    } 
    byte b = bool ? 4 : 3;
    if (paramExpose.is("indexes")) {
      if (!paramTable.getIndexes().isEmpty())
        paramPrintWriter.printf("<tr><th colspan='%d'>Indexes</th></tr>\n", new Object[] { Integer.valueOf(b) }); 
      for (Index index : paramTable.getIndexes()) {
        paramPrintWriter.printf("\t<tr>", new Object[0]);
        paramPrintWriter.printf("\t\t<td><svg width='14' height='14'><use xlink:href='%s'/></svg></td><td>%s</td>\n", new Object[] { (index.getType() == IndexType.PRIMARY_KEY) ? "#pk" : (
              (index.getType() == IndexType.UNIQUE_KEY || index.getType() == IndexType.UNIQUE_INDEX) ? "#unq" : "#idx"), 
              EscapeChars.forHtml(index.getName()) });
        paramPrintWriter.printf("\t\t<td>%s ON ", new Object[] { Dbms.get(index.getDbId()).getIndexTypeName(index.getType()) });
        paramPrintWriter.printf(EscapeChars.forHtml(Index.listAttributes(index.getColumns())), new Object[0]);
        paramPrintWriter.printf("</td>\n", new Object[0]);
        if (bool)
          paramPrintWriter.printf("\t\t<td> %s </td>\n", new Object[] { a(index) }); 
        paramPrintWriter.printf("\t</tr>\n", new Object[0]);
      } 
    } 
    if (paramExpose.is("foreignKeys")) {
      if (!paramTable.getRelations().isEmpty())
        paramPrintWriter.printf("<tr><th colspan='%d'>%s</th></tr>\n", new Object[] { Integer.valueOf(b), (paramExpose.getDbms()).fkAlias.c_() }); 
      for (ForeignKey foreignKey : paramTable.getRelations())
        a(paramPrintWriter, paramLayout, bool, foreignKey); 
      if (!paramTable.getImportedRelations().isEmpty())
        paramPrintWriter.printf("<tr><th colspan='%d'>%s</th></tr>\n", new Object[] { Integer.valueOf(b), "Referring " + (paramExpose.getDbms()).fkAlias.c_() }); 
      for (Relation relation : paramTable.getImportedRelations())
        a(paramPrintWriter, paramLayout, bool, relation); 
      if (!paramTable.constraints.isEmpty())
        paramPrintWriter.printf("<tr><th colspan='%d'>Constraints</th></tr>\n", new Object[] { Integer.valueOf(b) }); 
      for (Constraint constraint : paramTable.constraints) {
        paramPrintWriter.printf("\t<tr>\n", new Object[0]);
        paramPrintWriter.printf("\t\t<td>&nbsp;</td><td> %s </td><td> %s </td>", new Object[] { constraint.getName(), EscapeChars.forHtml(constraint.getText()) });
        if (bool)
          paramPrintWriter.printf("<td> %s</td>\n", new Object[] { EscapeChars.forHtml(constraint.getCommentNotNull()) }); 
        paramPrintWriter.printf("\t</tr>\n", new Object[0]);
      } 
      ArrayList<Trigger> arrayList = new ArrayList();
      for (Trigger trigger : paramTable.schema.triggers) {
        if (trigger.getOwningTable() == paramTable)
          arrayList.add(trigger); 
      } 
      if (!arrayList.isEmpty())
        paramPrintWriter.printf("<tr><th colspan='%d'>Triggers</th></tr>\n", new Object[] { Integer.valueOf(b) }); 
      if (paramExpose.is("triggers"))
        for (Trigger trigger : arrayList) {
          paramPrintWriter.printf("\t<tr>\n", new Object[0]);
          boolean bool1 = (trigger.getComment() != null) ? true : false;
          paramPrintWriter.printf("\t\t<td>&nbsp;</td><td> %s </td><td colspan='%d'> %s </td>", new Object[] { trigger
                .getName(), 
                Integer.valueOf((b == 4 && !bool1) ? 2 : 1), 
                paramExpose.is("sql_code") ? ("<pre>" + EscapeChars.forHtml(trigger.getText()) + "</pre>") : "" });
          if (bool && bool1)
            paramPrintWriter.printf("<td> %s</td>\n", new Object[] { a(trigger) }); 
          paramPrintWriter.printf("\t</tr>\n", new Object[0]);
        }  
      if (StringUtil.isFilledTrim(paramTable.getOptions())) {
        paramPrintWriter.printf("<tr><th colspan='%d'>Options</th></tr>\n", new Object[] { Integer.valueOf(b) });
        paramPrintWriter.printf("<tr><td colspan='%d'>%s</td></tr>\n", new Object[] { Integer.valueOf(b), EscapeChars.forHtmlNewLinesAsBR(paramTable.getOptions()) });
      } 
    } 
    paramPrintWriter.printf("</tbody>\n", new Object[0]);
    paramPrintWriter.printf("</table></div></div>\n\n", new Object[0]);
  }
  
  private static void a(PrintWriter paramPrintWriter, Layout paramLayout, boolean paramBoolean, Relation paramRelation) {
    paramPrintWriter.printf("\t<tr>\n", new Object[0]);
    paramPrintWriter.printf("\t\t<td><svg width='14' height='14'><use xlink:href='#ref'/></svg>", new Object[0]);
    if (paramRelation.isVirtual())
      paramPrintWriter.printf("<svg width='14' height='14'><use xlink:href='#virtual'/></svg>", new Object[0]); 
    paramPrintWriter.printf("</td><td>%s</td>\n", new Object[] { paramRelation.getName() });
    String str1 = EscapeChars.forHtml(Index.listAttributes(paramRelation.getSourceAttributes()));
    String str2 = EscapeChars.forHtml(Index.listAttributes(paramRelation.getTargetAttributes()));
    paramPrintWriter.printf("\t\t<td > %s &#8601; ", new Object[] { str2 });
    Depict depict = paramLayout.diagram.getDepictFor(paramRelation.getEntity());
    paramPrintWriter.printf("<a href='#%s' %s", new Object[] { EscapeChars.forHtml(paramRelation.getEntity().getNameWithSchemaName()), (depict != null) ? a(depict) : "" });
    paramPrintWriter.printf(">&#10063; %s</a>", new Object[] { EscapeChars.forHtml(paramLayout.diagram.isShowSchemaName() ? paramRelation.getEntity().getNameWithSchemaName() : paramRelation.getEntity().getName()) });
    if (!str1.equals(str2))
      paramPrintWriter.printf("(%s)", new Object[] { str1 }); 
    paramPrintWriter.printf(" </td>\n", new Object[0]);
    if (paramBoolean)
      paramPrintWriter.printf("\t\t<td> %s </td>\n", new Object[] { EscapeChars.forHtmlNewLinesAsBR((paramRelation.getComment() != null) ? paramRelation.getComment() : "") }); 
    paramPrintWriter.printf("\t</tr>\n", new Object[0]);
  }
  
  private static void a(PrintWriter paramPrintWriter, Layout paramLayout, boolean paramBoolean, ForeignKey paramForeignKey) {
    paramPrintWriter.printf("\t<tr>\n", new Object[0]);
    paramPrintWriter.printf("\t\t<td><svg width='14' height='14'><use xlink:href='#fk'/></svg>", new Object[0]);
    if (paramForeignKey.isVirtual())
      paramPrintWriter.printf("<svg width='14' height='14'><use xlink:href='#virtual'/></svg>", new Object[0]); 
    paramPrintWriter.printf("</td><td>%s</td>\n", new Object[] { paramForeignKey.getName() });
    String str1 = EscapeChars.forHtml(Index.listAttributes(paramForeignKey.getSourceAttributes()));
    String str2 = EscapeChars.forHtml(Index.listAttributes(paramForeignKey.getTargetAttributes()));
    paramPrintWriter.printf("\t\t<td > %s &#8599; ", new Object[] { str1 });
    Depict depict = paramLayout.diagram.getDepictFor(paramForeignKey.getTargetEntity());
    paramPrintWriter.printf("<a href='#%s' %s", new Object[] { EscapeChars.forHtml(paramForeignKey.getTargetEntity().getNameWithSchemaName()), (depict != null) ? a(depict) : "" });
    paramPrintWriter.printf(">&#10063; %s</a>", new Object[] { EscapeChars.forHtml(paramLayout.diagram.isShowSchemaName() ? paramForeignKey.getTargetEntity().getNameWithSchemaName() : paramForeignKey.getTargetEntity().getName()) });
    if (!str1.equals(str2))
      paramPrintWriter.printf("(%s)", new Object[] { str2 }); 
    paramPrintWriter.printf(" </td>\n", new Object[0]);
    if (paramBoolean)
      paramPrintWriter.printf("\t\t<td> %s </td>\n", new Object[] { a(paramForeignKey) }); 
    paramPrintWriter.printf("\t</tr>\n", new Object[0]);
  }
  
  private static String a(Depict paramDepict) {
    return String.format(" onclick=\"window.scrollTo(%.0f, %.0f);var nodes = document.getElementsByClassName('palpable');for (var i = 0; i < nodes.length; i++) { nodes[i].classList.remove('palpable');}; document.getElementById('depict_%s').classList.add('palpable'); return false;\"", new Object[] { Double.valueOf(Math.max(60.0D, paramDepict.getPosition().a() - 200.0D)), 
          Double.valueOf(paramDepict.getPosition().b()), paramDepict
          .getEntity().getNameWithSchemaName() });
  }
  
  private static void a(PrintWriter paramPrintWriter, Entity paramEntity, Expose paramExpose) {
    if (paramExpose.layouts.size() > 1) {
      paramPrintWriter.printf(" <small>[", new Object[0]);
      byte b = 1;
      for (Layout layout : paramExpose.layouts) {
        if (layout.containsDepictForEntity(paramEntity))
          paramPrintWriter.printf(" <a href='#layout%d'/>%s</a>", new Object[] { Integer.valueOf(b), layout.getName() }); 
        b++;
      } 
      paramPrintWriter.printf(" ]</small> ", new Object[0]);
    } 
  }
  
  private static void a(PrintWriter paramPrintWriter, AbstractTable paramAbstractTable, Column paramColumn, boolean paramBoolean) {
    paramPrintWriter.printf("\t<tr>\n", new Object[0]);
    StringBuilder stringBuilder = new StringBuilder();
    if (paramColumn.isMandatory())
      stringBuilder.append("<span class='text-danger'>*</span>"); 
    if (paramColumn.hasMarker(1)) {
      stringBuilder.append("<svg width='14' height='14'><use xlink:href='#pk'/></svg>");
    } else if (paramColumn.hasMarker(8)) {
      stringBuilder.append("<svg width='14' height='14'><use xlink:href='#unq'/></svg>");
    } else if (paramColumn.hasMarker(4)) {
      stringBuilder.append("<svg width='14' height='14'><use xlink:href='#idx'/></svg>");
    } 
    if (paramColumn.hasMarker(32)) {
      stringBuilder.append("<svg width='14' height='14'><use xlink:href='#fk'/></svg>");
    } else if (paramColumn.hasMarker(64)) {
      stringBuilder.append("<svg width='14' height='14'><use xlink:href='#ref'/></svg>");
    } 
    if (stringBuilder.isEmpty())
      stringBuilder.append("&nbsp;"); 
    paramPrintWriter.printf("\t\t<td>%s</td>\n", new Object[] { stringBuilder });
    paramPrintWriter.printf("\t\t<td><a name='%s'>%s</a></td>\n", new Object[] { EscapeChars.forURL(paramAbstractTable.getNameWithSchemaName() + "." + paramAbstractTable.getNameWithSchemaName()), EscapeChars.forHtml((paramColumn.getParentColumn() != null) ? paramColumn.getNameWithPath() : paramColumn.getName()) });
    paramPrintWriter.printf("\t\t<td class='dataType'> %s ", new Object[] { EscapeChars.forHtml(paramColumn.getTypeString(DataTypeFormat.b)) });
    if (paramColumn.isUnsigned())
      paramPrintWriter.printf("UNSIGNED ", new Object[0]); 
    if (paramColumn.isIdentity())
      paramPrintWriter.printf(paramColumn.getIdentity(), new Object[0]); 
    paramPrintWriter.printf(EscapeChars.forHtml(paramColumn.getOptions() + " "), new Object[0]);
    if (paramColumn.hasDefaultValue())
      paramPrintWriter.printf("DEFAULT %s ", new Object[] { paramColumn.getDefaultValue() }); 
    paramPrintWriter.printf("</td>\n", new Object[0]);
    if (paramBoolean)
      paramPrintWriter.printf("\t\t<td> %s </td>\n", new Object[] { a(paramColumn) }); 
    paramPrintWriter.printf("\t</tr>\n", new Object[0]);
    if (paramColumn.hasChildEntity())
      for (Column column : (paramColumn.getChildEntity()).columns)
        a(paramPrintWriter, paramAbstractTable, column, paramBoolean);  
  }
  
  static void a(PrintWriter paramPrintWriter, View paramView, Depict paramDepict, Layout paramLayout, Expose paramExpose) {
    paramPrintWriter.printf("<br><br>\n", new Object[0]);
    paramPrintWriter.printf("<div class='card'><div class='card-body'><a name='%s' %s style='cursor:pointer;'><h5>%s %s</h5></a>\n", new Object[] { EscapeChars.forURL(paramView.getNameWithSchemaName()), 
          a(paramDepict), paramView
          .getSymbolicName(), paramView.getName() });
    if (StringUtil.isFilledTrim(paramView.getComment()))
      paramPrintWriter.printf("<p class='text-muted'>%s</p>\n", new Object[] { a(paramView) }); 
    a(paramPrintWriter, paramView, paramExpose);
    boolean bool = hasComments(paramView);
    paramPrintWriter.printf("<table class='table table-sm small' style='table-layout: fixed; word-wrap: break-word;'>\n", new Object[0]);
    byte b = bool ? 4 : 3;
    if (paramExpose.is("view_query")) {
      paramPrintWriter.printf("<thead>\n", new Object[0]);
      paramPrintWriter.printf("\t<tr>\n", new Object[0]);
      paramPrintWriter.printf("\t\t<th colspan='%d'><a name='%s'>Query</a></th></tr>\n", new Object[] { Integer.valueOf(b), paramView.getName() });
      paramPrintWriter.printf("</thead>\n", new Object[0]);
      paramPrintWriter.printf("<tbody>\n", new Object[0]);
      paramPrintWriter.printf("\t<tr><td colspan='%d'><pre style='white-space: pre-wrap;word-wrap: break-word;'>%s</pre></td></tr>\n", new Object[] { Integer.valueOf(b), EscapeChars.forHtml(paramView.getScript()) });
    } 
    if (bool) {
      paramPrintWriter.printf("<tr><th style='width:5%%'>Idx</th><th style='width:20%%'>%s Name</th><th style='width:23%%'>Data Type</th><th style='width:52%%'>Description</th></tr>\n", new Object[] { (paramExpose.getDbms()).columnAlias.c_() });
    } else {
      paramPrintWriter.printf("<tr><th style='width:10%%'>Idx</th><th style='width:30%%'>%s Name</th><th>Data Type</th></tr>\n", new Object[] { (paramExpose.getDbms()).columnAlias.c_() });
    } 
    for (Column column : paramView.getAttributes())
      a(paramPrintWriter, paramView, column, bool); 
    if (paramExpose.is("foreignKeys")) {
      if (!paramView.getRelations().isEmpty())
        paramPrintWriter.printf("<tr><th colspan='%d'>%s</th></tr>\n", new Object[] { Integer.valueOf(b), (paramView.is(UnitProperty.f).booleanValue() || paramView.is(UnitProperty.g).booleanValue()) ? "Relationships" : "Foreign Keys" }); 
      for (ForeignKey foreignKey : paramView.getRelations())
        a(paramPrintWriter, paramLayout, bool, foreignKey); 
      if (!paramView.getImportedRelations().isEmpty())
        paramPrintWriter.printf("<tr><th colspan='%d'>%s</th></tr>\n", new Object[] { Integer.valueOf(b), (paramView.is(UnitProperty.f).booleanValue() || paramView.is(UnitProperty.g).booleanValue()) ? "Referring Relationships" : "Referring Foreign Keys" }); 
      for (Relation relation : paramView.getImportedRelations())
        a(paramPrintWriter, paramLayout, bool, relation); 
    } 
    paramPrintWriter.printf("</tbody>\n", new Object[0]);
    paramPrintWriter.printf("</table></div></div>\n", new Object[0]);
  }
  
  static void a(PrintWriter paramPrintWriter, String paramString, Folder paramFolder, Expose paramExpose) {
    paramPrintWriter.printf("<br><br>\n", new Object[0]);
    if (!paramFolder.isEmpty()) {
      paramPrintWriter.printf("<div class='card'><div class='card-body'><h5> %s </h5>\n", new Object[] { paramString });
      paramPrintWriter.printf("<table class='table-sm'>\n", new Object[0]);
      for (Sql sql : paramFolder) {
        paramPrintWriter.printf("\t<tr><td> %s </td><td> %s </td></tr>", new Object[] { sql
              .ref(), 
              a(sql) });
        if (paramExpose.is("sql_code"))
          paramPrintWriter.printf("<tr><td> %s</td></tr>\n", new Object[] { (sql.getText() != null) ? ("<pre>" + EscapeChars.forHtml(sql.getText()) + "</pre>") : "" }); 
      } 
      paramPrintWriter.printf("</table></div></div>\n", new Object[0]);
    } 
  }
  
  static void a(PrintWriter paramPrintWriter, Schema paramSchema) {
    paramPrintWriter.printf("<br><br>\n", new Object[0]);
    paramPrintWriter.printf("<div class='card'><div class='card-body'><h5>%s %s</h5>\n", new Object[] { paramSchema
          .getSymbolicName(), paramSchema.getName() });
    paramPrintWriter.printf("<table class='table-sm'>\n", new Object[0]);
    if (!paramSchema.sequences.isEmpty()) {
      paramPrintWriter.printf("\t<tr>\n", new Object[0]);
      paramPrintWriter.printf("\t\t<th>Sequences</th><th>Description</th></tr>\n", new Object[0]);
      for (Sequence sequence : paramSchema.sequences) {
        paramPrintWriter.printf("\t<tr><td>%s</td><td>%s</td></tr>\n", new Object[] { EscapeChars.forHtml(sequence.getName()), a(sequence) });
      } 
    } 
    paramPrintWriter.printf("</table></div></div>\n", new Object[0]);
  }
  
  static void b(PrintWriter paramPrintWriter, Schema paramSchema, Expose paramExpose) {
    paramPrintWriter.printf("<br><br>\n", new Object[0]);
    paramPrintWriter.printf("<div class='card'><div class='card-body'><h5>%s %s</h5>\n", new Object[] { paramSchema
          .getSymbolicName(), paramSchema.getName() });
    paramPrintWriter.printf("<table class='table-sm'>\n", new Object[0]);
    if (!paramSchema.userDataTypes.isEmpty()) {
      paramPrintWriter.printf("\t<tr>\n", new Object[0]);
      paramPrintWriter.printf("\t\t<th>User Data Types</th><th>Description</th></tr>\n", new Object[0]);
      for (UserDataType userDataType : paramSchema.userDataTypes) {
        paramPrintWriter.printf("\t<tr><td>%s</td><td>%s</td></tr>\n", new Object[] { EscapeChars.forHtml(userDataType.getName()), a(userDataType) });
        if (paramExpose.is("sql_code"))
          paramPrintWriter.printf("<tr><td> %s</td></tr>\n", new Object[] { (userDataType.getScript() != null) ? ("<pre>" + EscapeChars.forHtml(userDataType.getScript()) + "</pre>") : "" }); 
      } 
    } 
    paramPrintWriter.printf("</table></div></div>\n", new Object[0]);
  }
  
  public static boolean hasComments(TreeUnit paramTreeUnit) {
    if (StringUtil.isFilledTrim(paramTreeUnit.getComment()) || paramTreeUnit.getUnitProperty(UnitProperty.e) != null)
      return true; 
    for (byte b = 0; b < paramTreeUnit.getChildrenCount(); b++) {
      if (hasComments(paramTreeUnit.getChildAt(b)))
        return true; 
    } 
    return false;
  }
  
  private static void a(String paramString, Expose paramExpose, PrintWriter paramPrintWriter) {
    paramPrintWriter.printf("<?xml version='1.0' encoding='UTF-8' standalone='yes'?>\n", new Object[0]);
    paramPrintWriter.printf("<!DOCTYPE html>\n", new Object[0]);
    paramPrintWriter.printf("<html lang='en-us' xmlns='http://www.w3.org/1999/xhtml' %s >\n", new Object[] { paramExpose.is("darkTheme") ? "data-bs-theme='dark'" : "" });
    paramPrintWriter.printf("<head>\n", new Object[0]);
    paramPrintWriter.printf("<title>%s</title>\n", new Object[] { EscapeChars.forHtml(paramString) });
    paramPrintWriter.printf("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'/>\n", new Object[0]);
    paramPrintWriter.printf("<meta name='viewport' content='width=device-width, initial-scale=1, shrink-to-fit=no' />\n", new Object[0]);
    paramPrintWriter.printf("<link rel='shortcut icon' href='https://dbschema.com/img/favicons/favicon.ico'>\n", new Object[0]);
    paramPrintWriter.printf("<style>\n", new Object[0]);
    paramPrintWriter.println(StringUtil.readStringFromInputStream(HTML5Writer.class.getResourceAsStream("resources/bootstrap.min.css")));
    paramPrintWriter.println(StringUtil.readStringFromInputStream(HTML5Writer.class.getResourceAsStream(paramExpose.is("darkTheme") ? "resources/bootstrap-theme-dark.css" : "resources/bootstrap-theme-light.css")));
    paramPrintWriter.println("</style>\n");
    paramPrintWriter.printf("</head>\n\n", new Object[0]);
  }
  
  static String a(AbstractUnit paramAbstractUnit) {
    StringBuilder stringBuilder = new StringBuilder();
    if (paramAbstractUnit.getComment() != null)
      stringBuilder.append(EscapeChars.forHtmlNewLinesAsBR(paramAbstractUnit.getComment())); 
    Map map = paramAbstractUnit.getCommentTags();
    if (map != null) {
      stringBuilder.append("<br>Tags: ");
      boolean bool = false;
      for (String str : map.keySet()) {
        if (bool)
          stringBuilder.append(", "); 
        stringBuilder.append(EscapeChars.forHtmlNewLinesAsBR(str)).append(" = ").append(EscapeChars.forHtmlNewLinesAsBR((String)map.get(str)));
        bool = true;
      } 
    } 
    return stringBuilder.toString();
  }
}
