package com.wisecoders.dbs.project.html;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.diagram.fx.FxAbstractDiagramPane;
import com.wisecoders.dbs.diagram.fx.ToolTipFactory;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Callout;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Diagram;
import com.wisecoders.dbs.diagram.model.Group;
import com.wisecoders.dbs.diagram.model.LineTextType;
import com.wisecoders.dbs.diagram.model.Rect;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.diagram.model.RelationPosition;
import com.wisecoders.dbs.diagram.model.Shape;
import com.wisecoders.dbs.diagram.model.ShapeStyle;
import com.wisecoders.dbs.diagram.model.TreeSelection;
import com.wisecoders.dbs.project.fx.Theme;
import com.wisecoders.dbs.schema.DataTypeFormat;
import com.wisecoders.dbs.schema.Expose;
import com.wisecoders.dbs.schema.RelationCardinality;
import com.wisecoders.dbs.schema.RelationType;
import com.wisecoders.dbs.sys.ColorUtil;
import com.wisecoders.dbs.sys.EscapeChars;
import com.wisecoders.dbs.sys.License;
import com.wisecoders.dbs.sys.StringUtil;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import javafx.scene.paint.Color;

public class SvgWriter {
  private static final int a = 110;
  
  public static void a(Expose paramExpose, Diagram paramDiagram, PrintWriter paramPrintWriter, String paramString) {
    int i = Diagram.cell;
    int j = i / 2, k = i / 4, m = i / 5, n = 3 * i / 4;
    char c = 'Ũ';
    double d = Math.max(paramDiagram.getWidth(), 400.0D);
    paramPrintWriter.printf("<svg xmlns='http://www.w3.org/2000/svg' xmlns:xlink='http://www.w3.org/1999/xlink' ", new Object[0]);
    paramPrintWriter.printf("  width='%.0f' height='%.0f' viewBox='0 0 %.0f %.0f' >\n", new Object[] { Double.valueOf(d), Double.valueOf(paramDiagram.getHeight() + 110.0D), Double.valueOf(d), Double.valueOf(paramDiagram.getHeight() + 110.0D) });
    paramPrintWriter.printf("  <script type='text/ecmascript'>\n", new Object[0]);
    paramPrintWriter.printf("  <![CDATA[\n", new Object[0]);
    paramPrintWriter.printf("  function hghl(el) { for ( var i in el ){ var elem = document.getElementById(el[i]); if ( elem != null ) elem.setAttribute('class','highlight');  }  }\n", new Object[0]);
    paramPrintWriter.printf("  function uhghl(el) { for ( var i in el ){ var elem = document.getElementById(el[i]); if ( elem != null ) elem.setAttribute('class','unhighlight'); }  } \n", new Object[0]);
    paramPrintWriter.printf("  ]]>\n", new Object[0]);
    paramPrintWriter.printf("  </script> \n\n", new Object[0]);
    String str1 = StringUtil.readStringFromInputStream(HTML5Writer.class.getResourceAsStream(paramExpose.is("darkTheme") ? "resources/svg-theme-dark.svg" : "resources/svg-theme-light.svg"));
    str1 = str1.replaceAll("_TABLE_HEADER_FONT_NAME_", paramExpose.getString("tableHeaderFontName", "Candara"));
    str1 = str1.replaceAll("_TABLE_HEADER_FONT_SIZE_", paramExpose.getString("tableHeaderFontSize", "13"));
    str1 = str1.replaceAll("_TABLE_HEADER_FONT_WEIGHT_", paramExpose.getString("tableHeaderFontWeight", "bold"));
    str1 = str1.replaceAll("_DIAGRAM_FONT_NAME_", paramExpose.getString("fontName", "Candara"));
    str1 = str1.replaceAll("_DIAGRAM_FONT_SIZE_", paramExpose.getString("fontSize", "13"));
    str1 = str1.replaceAll("_DATA_TYPE_FONT_SIZE_", paramExpose.getString("dataTypeFontSize", "12"));
    str1 = str1.replaceAll("_CALLOUT_FONT_NAME_", paramExpose.getString("calloutFontName", "Candara"));
    str1 = str1.replaceAll("_CALLOUT_FONT_SIZE_", paramExpose.getString("calloutFontSize", "14"));
    paramPrintWriter.print(str1);
    paramPrintWriter.printf("\n\n<defs>", new Object[0]);
    for (RelationCardinality relationCardinality : RelationCardinality.values())
      paramExpose.getNotation().a(paramPrintWriter, relationCardinality); 
    ArrayList<Color> arrayList1 = new ArrayList();
    for (Depict depict : paramDiagram.depicts) {
      if (!arrayList1.contains(depict.getColor())) {
        arrayList1.add(depict.getColor());
        if (paramExpose.is("darkTheme")) {
          paramPrintWriter.printf("  <linearGradient id='tbg_%s' x1='0%%' y1='0%%' x2='100%%' y2='0%%' >\n", new Object[] { ColorUtil.b(depict.getColor()) });
          paramPrintWriter.printf("     <stop offset='0%%' stop-color='#%s' /> \n", new Object[] { ColorUtil.b(Color.web("#2e3136"), depict.getColor()) });
          paramPrintWriter.printf("     <stop offset='40%%' stop-color='#%s' /> \n", new Object[] { ColorUtil.b(Color.web("#2b2d31"), depict.getColor()) });
          paramPrintWriter.printf("     <stop offset='100%%' stop-color='#%s' /> \n", new Object[] { ColorUtil.b(Color.web("#27292c"), depict.getColor()) });
          paramPrintWriter.printf("  </linearGradient>\n", new Object[0]);
          continue;
        } 
        if (Theme.a() == Theme.c) {
          paramPrintWriter.printf("  <linearGradient id='tbg_%s' x1='0%%' y1='0%%' x2='100%%' y2='0%%' >\n", new Object[] { ColorUtil.b(depict.getColor()) });
          paramPrintWriter.printf("     <stop offset='0%%' stop-color='#%s' /> \n", new Object[] { ColorUtil.b(Color.web("#edf2f6"), depict.getColor()) });
          paramPrintWriter.printf("     <stop offset='100%%' stop-color='#%s' /> \n", new Object[] { ColorUtil.b(Color.web("#ffffff"), depict.getColor()) });
          paramPrintWriter.printf("  </linearGradient>\n", new Object[0]);
          continue;
        } 
        paramPrintWriter.printf("  <linearGradient id='tbg_%s' x1='0%%' y1='0%%' x2='0%%' y2='100%%' >\n", new Object[] { ColorUtil.b(depict.getColor()) });
        paramPrintWriter.printf("     <stop offset='0%%' stop-color='#%s' /> \n", new Object[] { ColorUtil.b(Color.web("#fcfbf4"), depict.getColor()) });
        paramPrintWriter.printf("     <stop offset='75%%' stop-color='#%s' /> \n", new Object[] { ColorUtil.b(Color.web("#f2edd9"), depict.getColor()) });
        paramPrintWriter.printf("     <stop offset='100%%' stop-color='#%s' /> \n", new Object[] { ColorUtil.b(Color.web("#dbd8cb"), depict.getColor()) });
        paramPrintWriter.printf("  </linearGradient>\n", new Object[0]);
      } 
    } 
    paramPrintWriter.printf("</defs>\n\n", new Object[0]);
    paramPrintWriter.flush();
    paramPrintWriter.printf("<!-- == Shape Colors == -->\n", new Object[0]);
    ArrayList<Color> arrayList2 = new ArrayList();
    for (Shape shape : paramDiagram.shapes) {
      if (!arrayList2.contains(shape.c())) {
        arrayList2.add(shape.c());
        paramPrintWriter.printf("  <radialGradient id='shape%s' cx='25%%' cy='20%%' r='80%%' fx='10%%' fy='10%%'>\n", new Object[] { Integer.valueOf(ColorUtil.a(shape.c())) });
        if (paramExpose.is("darkTheme")) {
          paramPrintWriter.printf("    <stop offset='0%%' stop-color='#%s' />\n", new Object[] { ColorUtil.b(Color.web("#527da4"), shape.c()) });
          paramPrintWriter.printf("    <stop offset='100%%' stop-color='#%s' />\n", new Object[] { ColorUtil.b(Color.web("#224a6c"), shape.c()) });
        } else {
          paramPrintWriter.printf("    <stop offset='0%%' stop-color='#%s' />\n", new Object[] { ColorUtil.b(Color.web("#fbf9e7"), shape.c()) });
          paramPrintWriter.printf("    <stop offset='100%%' stop-color='#%s' />\n", new Object[] { ColorUtil.b(Color.web("#f7e5b8"), shape.c()) });
        } 
        paramPrintWriter.printf("  </radialGradient>\n", new Object[0]);
      } 
    } 
    paramPrintWriter.printf("<!-- == Desktop == -->\n", new Object[0]);
    paramPrintWriter.printf("<rect x='0' y='0' width='%.0f' height='%.0f' style='fill:url(#layoutBgA); stroke:%s; stroke-width:.7px; cursor:move;' />\n", new Object[] { Double.valueOf(d), Double.valueOf(paramDiagram.getHeight() + 110.0D), paramExpose.is("darkTheme") ? "#444444" : "#ccc" });
    if ((License.a().g()).i)
      paramPrintWriter.printf("<rect x='1' y='1' width='%.0f' height='%.0f' rx='%d' ry='%d' style='fill:url(#layoutBgEval);' />\n", new Object[] { Double.valueOf(d - 2.0D), Double.valueOf(paramDiagram.getHeight() + 110.0D - 2.0D), Integer.valueOf(j), Integer.valueOf(j) }); 
    paramPrintWriter.println();
    byte b = 14;
    paramPrintWriter.printf("<!-- == Legend == -->\n", new Object[0]);
    paramPrintWriter.printf("<g transform='translate(%d,%d)'>\n", new Object[] { Integer.valueOf(10), Integer.valueOf(10) });
    String str2 = Sys.B.copyright.c_().replaceAll("DATE", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    int i1 = Math.max(FxAbstractDiagramPane.a(paramString), FxAbstractDiagramPane.a(str2));
    boolean bool = Sys.B.logo.b() ? (70 + 14 * ((paramExpose.project.getCommentTags() != null) ? paramExpose.project.getCommentTags().size() : 0)) : false;
    if (Sys.B.logo.b()) {
      int i2 = Math.max(3 + i1, 360);
      paramPrintWriter.printf("  <rect x='10' y='10' width='%d' height='%d' rx='7' ry='7' class='legend'/> \n", new Object[] { Integer.valueOf(i2), Integer.valueOf(bool) });
      paramPrintWriter.printf("  <use x='%d' y='28' xlink:href='#bulb' width='24' height='24'><title>Read column and table comments by hovering the columns and tables.\nZoom with CTRL+whell mouse button, pan by dragging the diagram with the right-mouse button</title></use>\n", new Object[] { Integer.valueOf(i2 - 11) });
      paramPrintWriter.printf("  <use id='logo' x='20' y='40' xlink:href='#logo'><title>DbSchema Database Designer </title></use>", new Object[0]);
      paramPrintWriter.printf("  <text x='20' y='30' class='legendTitle' >%s</text> \n", new Object[] { EscapeChars.titleForHtml(paramString) });
      byte b1 = 47;
      if (paramExpose.project.getCommentTags() != null)
        for (String str3 : paramExpose.project.getCommentTags().keySet()) {
          String str4 = str3 + " : " + str3;
          paramPrintWriter.printf("  <text x='57' y='%d' class='legendSubTitle' >%s</text> \n", new Object[] { Integer.valueOf(b1), EscapeChars.forHtml(str4) });
          b1 += 14;
        }  
      paramPrintWriter.printf("  <text x='57' y='%d' class='legendSubTitle'><tspan>Interactive docs: mouse-over comments, zoom and pan</tspan></text>\n", new Object[] { Integer.valueOf(b1) });
      b1 += 14;
      paramPrintWriter.printf("  <a xlink:href='%s'><text x='57' y='%d' class='legendSubTitle'><tspan>%s<title>DbSchema is an universal database designer.\nDbSchema has a free Community Edition.</title></tspan></text></a> \n", new Object[] { EscapeChars.forURL(Sys.B.copyrightHref.c_()), Integer.valueOf(b1), EscapeChars.titleForHtml(str2) });
    } 
    paramPrintWriter.printf("</g>\n\n", new Object[0]);
    paramPrintWriter.printf("<g transform='translate(%d,%d)'>\n", new Object[] { Integer.valueOf(0), Integer.valueOf(110) });
    for (Group group : paramDiagram.groups) {
      paramPrintWriter.printf("<!-- == Group '%s' == -->\n", new Object[] { group.getName() });
      paramPrintWriter.printf("<rect class='grp' style='fill:#%s;stroke:#%s' x='%.0f' y='%.0f' width='%.0f' height='%.0f' />\n", new Object[] { ColorUtil.b(Color.web(paramExpose.is("darkTheme") ? "#282f37" : "#fffdf2"), group.getColor()), 
            ColorUtil.b(Color.web(paramExpose.is("darkTheme") ? "#636872" : "#cacab0"), group.getBorderColor()), 
            Double.valueOf(group.getPosition().a()), Double.valueOf(group.getPosition().b()), Double.valueOf(group.getPosition().c()), Double.valueOf(group.getPosition().d()) });
      paramPrintWriter.printf("<text x='%.0f' y='%.0f' style='fill:#%s;'>%s</text>\n", new Object[] { Double.valueOf(group.getPosition().a() + n), Double.valueOf(group.getPosition().b() + i), ColorUtil.b(Color.web(paramExpose.is("darkTheme") ? "#6c4c4c" : "#acac66"), group.getBorderColor()), EscapeChars.forHtml(group.getName()) });
      paramPrintWriter.flush();
    } 
    Rect rect = new Rect();
    SvgWriter$1 svgWriter$1 = new SvgWriter$1(paramPrintWriter);
    for (Depict depict : paramDiagram.depicts) {
      rect.a(depict.getPosition());
      for (Relation relation : depict.getEntity().getRelations()) {
        if (paramDiagram.relationIsDrawn(relation)) {
          paramPrintWriter.printf("<!-- == Fk '%s' == -->\n", new Object[] { b(relation) });
          paramPrintWriter.printf("<path id='%s' %s transform='translate(%d,%d)' class='unhighlight' ", new Object[] { b(relation), a(relation), Integer.valueOf(j), Integer.valueOf(0) });
          paramPrintWriter.printf("d='", new Object[0]);
          paramDiagram.vectorizeLine(relation, svgWriter$1, false);
          paramPrintWriter.printf("' >\n\t<title>%s</title>\n</path>\n", new Object[] { ToolTipFactory.c(relation) });
          boolean bool1 = (relation.getRelationType() == RelationType.c) ? true : false;
          paramPrintWriter.printf("<path transform='translate(%d,%d)' marker-start='url(#%s)' %s ", new Object[] { Integer.valueOf(j), Integer.valueOf(0), paramDiagram
                .getRelationCardinality(relation, false), 
                paramDiagram.hasEndTerminator(relation) ? ("marker-end='url(#" + String.valueOf(paramDiagram.getTargetTerminatorCardinality(paramExpose.getNotation(), relation)) + ")'") : "" });
          boolean bool2 = (paramExpose.getNotation().a() && !relation.hasFlag(1)) ? true : false;
          boolean bool3 = relation.isVirtual();
          paramPrintWriter.printf(" %s ", new Object[] { (bool2 || bool3) ? ("class='" + (bool2 ? "dotted " : "") + (bool3 ? "virtual" : "") + "'") : "" });
          paramPrintWriter.printf(" d='", new Object[0]);
          paramDiagram.vectorizeLine(relation, svgWriter$1, false);
          paramPrintWriter.printf("' ><title>%s</title></path>\n", new Object[] { ToolTipFactory.c(relation) });
        } 
        if (paramDiagram.getLineTextType() != LineTextType.d) {
          RelationPosition relationPosition = (RelationPosition)paramDiagram.relationPositions.get(relation);
          if (relationPosition != null)
            paramPrintWriter.printf("<text x='%d' y='%d' transform='rotate(%d %d,%d)' class='relName'>%s</text>\n", new Object[] { Integer.valueOf(relationPosition.e()), Integer.valueOf(relationPosition.f() - j), 
                  Integer.valueOf(relationPosition.b()), Integer.valueOf(relationPosition.e()), Integer.valueOf(relationPosition.f() - j), EscapeChars.forHtml(relationPosition.h) }); 
        } 
      } 
    } 
    for (Shape shape : paramDiagram.shapes) {
      rect.a(shape.a.getPosition());
      for (Relation relation : ((Shape)shape.a.getEntity()).getRelations()) {
        if (paramDiagram.relationIsDrawn(relation)) {
          paramPrintWriter.printf("<!-- == Line '%s' == -->\n", new Object[] { b(relation) });
          paramPrintWriter.printf("<path transform='translate(%d,%d)' class='line' ", new Object[] { Integer.valueOf(j), Integer.valueOf(0) });
          paramPrintWriter.printf(" style='stroke:#%s;' ", new Object[] { ColorUtil.b(Color.web("#f7e5b8"), shape.c()) });
          paramPrintWriter.printf(" d='", new Object[0]);
          paramDiagram.vectorizeLine(relation, svgWriter$1, true);
          paramPrintWriter.printf("' ><title>%s</title></path>\n", new Object[] { ToolTipFactory.c(relation) });
        } 
        if (paramDiagram.getLineTextType() != LineTextType.d) {
          RelationPosition relationPosition = (RelationPosition)paramDiagram.relationPositions.get(relation);
          if (relationPosition != null)
            paramPrintWriter.printf("<text x='%d' y='%d' transform='rotate(%d %d,%d)' class='relName'>%s</text>\n", new Object[] { Integer.valueOf(relationPosition.e()), Integer.valueOf(relationPosition.f() - j), 
                  Integer.valueOf(relationPosition.b()), Integer.valueOf(relationPosition.e()), Integer.valueOf(relationPosition.f() - j), EscapeChars.forHtml(relationPosition.h) }); 
        } 
      } 
    } 
    for (Depict depict : paramDiagram.depicts) {
      rect.a(depict.getPosition());
      paramPrintWriter.printf("<!-- == Table '%s' == -->\n", new Object[] { depict.getEntity().getName() });
      double d1 = rect.a(), d2 = rect.b() - j, d3 = rect.c(), d4 = (i + n), d5 = k;
      if (Theme.a() == Theme.c || paramExpose.is("darkTheme")) {
        paramPrintWriter.printf("<rect id='depict_%s' class='entity' x='%.0f' y='%.0f' width='%.0f' height='%.0f' rx='%d' ry='%d' %s/>\n", new Object[] { EscapeChars.forHtml(depict.getEntity().getNameWithSchemaName()), 
              Double.valueOf(rect.a()), Double.valueOf(rect.b() - j), 
              Double.valueOf(rect.c()), 
              Double.valueOf(rect.d()), 
              Integer.valueOf(k), Integer.valueOf(k), "style='fill:url(#tbg_" + 
              ColorUtil.b(depict.getColor()) + ");stroke:#" + ColorUtil.b(Color.web("#bec8d5"), depict.getColor()) + ";'" });
        paramPrintWriter.printf(Locale.US, "<line class='delim' style='stroke:%s;' x1='%.0f' y1='%.0f' x2='%.0f' y2='%.0f'/>\n", new Object[] { "777777", 
              
              Double.valueOf(d1), Double.valueOf(d2 + d4), Double.valueOf(d1 + d3), Double.valueOf(d2 + d4) });
      } else if (Theme.a() == Theme.b) {
        paramPrintWriter.printf("<rect id='depict_%s' class='entity' style='stroke:none;' x='%.0f' y='%.0f' width='%.0f' height='%.0f' rx='%d' ry='%d' />\n", new Object[] { EscapeChars.forHtml(depict.getEntity().getNameWithSchemaName()), 
              Double.valueOf(rect.a()), Double.valueOf(rect.b() - j), 
              Double.valueOf(rect.c()), 
              Double.valueOf(rect.d()), 
              Integer.valueOf(k), Integer.valueOf(k) });
        paramPrintWriter.printf(Locale.US, "<path d='M %.0f %.0f L %.0f %.0f Q %.0f %.0f %.0f %.0f L %.0f %.0f Q %.0f %.0f %.0f %.0f L %.0f %.0f L%.0f %.0f ' style='fill:url(#tbg_%s);stroke-width:0;' />\n", new Object[] { 
              Double.valueOf(d1), Double.valueOf(d2 + d4), 
              Double.valueOf(d1), Double.valueOf(d2 + d5), 
              Double.valueOf(d1), Double.valueOf(d2), Double.valueOf(d1 + d5), Double.valueOf(d2), 
              Double.valueOf(d1 + d3 - d5), Double.valueOf(d2), 
              Double.valueOf(d1 + d3), Double.valueOf(d2), Double.valueOf(d1 + d3), Double.valueOf(d2 + d5), 
              Double.valueOf(d1 + d3), Double.valueOf(d2 + d4), 
              Double.valueOf(d1), Double.valueOf(d2 + d4), ColorUtil.b(depict.getColor()) });
        paramPrintWriter.printf("<rect class='entity' x='%.0f' y='%.0f' width='%.0f' height='%.0f' rx='%d' ry='%d' %s/>\n", new Object[] { Double.valueOf(rect.a()), Double.valueOf(rect.b() - j), 
              Double.valueOf(rect.c()), 
              Double.valueOf(rect.d()), 
              Integer.valueOf(k), Integer.valueOf(k), "style='fill:none;stroke:#" + 
              ColorUtil.b(Color.web(paramExpose.is("darkTheme") ? "#2c3035" : "#e1ebf2"), depict.getColor()) + "'" });
      } else {
        String str = ColorUtil.b(Color.web("#768eb9"), depict.getColor());
        paramPrintWriter.printf("<rect id='depict_%s' class='entity' style='stroke:#%s' x='%.0f' y='%.0f' width='%.0f' height='%.0f' />\n", new Object[] { EscapeChars.forHtml(depict.getEntity().getNameWithSchemaName()), str, 
              
              Double.valueOf(rect.a()), Double.valueOf(rect.b() - j), 
              Double.valueOf(rect.c()), 
              Double.valueOf(rect.d()) });
        paramPrintWriter.printf("<rect x='%.0f' y='%.0f' width='%.0f' height='%.0f' style='stroke-width:0; fill:#%s'/>\n", new Object[] { Double.valueOf(d1), Double.valueOf(d2), Double.valueOf(d3), Double.valueOf(d4), 
              ColorUtil.b(Color.web(paramExpose.is("darkTheme") ? "#202325" : "#f4f1ed"), depict.getColor()) });
        paramPrintWriter.printf(Locale.US, "<line class='topLine' style='stroke:#%s;' stroke-linecap='round' x1='%.0f' y1='%.0f' x2='%.0f' y2='%.0f'/>\n", new Object[] { ColorUtil.b(Color.web(paramExpose.is("darkTheme") ? "#589d48" : "#75ae68"), depict.getColor()), 
              Double.valueOf(d1), Double.valueOf(d2), Double.valueOf(d1 + d3), Double.valueOf(d2) });
      } 
      if (depict.entity.isView())
        paramPrintWriter.printf("<use id='view' x='%.0f' y='%.0f' xlink:href='#view'/>\n", new Object[] { Double.valueOf(rect.a() + rect.c() - i), Double.valueOf(rect.b() - j) }); 
      int i2 = depict.getNameWidth(paramDiagram.isShowSchemaName());
      paramPrintWriter.printf("<a xlink:href='#%s'>", new Object[] { EscapeChars.forURL(depict.getEntity().getNameWithSchemaName()) });
      paramPrintWriter.printf("<text x='%.0f' y='%.0f' class='tableHeader'>%s</text>", new Object[] { Double.valueOf(rect.a() + (rect.c() - i2) / 2.0D - k), 
            Double.valueOf(rect.b() + j + m), 
            EscapeChars.forHtml(paramDiagram.isShowSchemaName() ? depict.entity.getNameWithSchemaName() : depict.getEntity().getDisplayName(paramDiagram)) });
      paramPrintWriter.printf("<title>%s</title>", new Object[] { ToolTipFactory.b(depict.entity) });
      paramPrintWriter.printf("</a>\n", new Object[0]);
      byte b1 = 0;
      for (Attribute attribute : depict.getVisibleAttributes()) {
        paramPrintWriter.printf("  ", new Object[0]);
        String str3 = null;
        if (attribute.isMandatory())
          paramPrintWriter.printf("<use id='nn' x='%.0f' y='%.0f' xlink:href='#nn'/>", new Object[] { Double.valueOf(rect.a() + 2.0D), Double.valueOf(rect.b() + 1.0D + (i * (b1 + 1)) + n) }); 
        if (attribute.hasMarker(1)) {
          str3 = "pk";
        } else if (attribute.hasMarker(8)) {
          str3 = "unq";
        } else if (attribute.hasMarker(4)) {
          str3 = "idx";
        } else if (attribute.hasMarker(65536)) {
          str3 = "idx1";
        } else if (attribute.hasMarker(131072)) {
          str3 = "idx2";
        } 
        if (str3 != null) {
          paramPrintWriter.printf("<use id='%s' x='%.0f' y='%.0f' xlink:href='#%s'>", new Object[] { str3, Double.valueOf(rect.a() + 2.0D), Double.valueOf(rect.b() + (i * (b1 + 1)) + n), str3 });
          paramPrintWriter.printf("<title>%s</title>", new Object[] { ToolTipFactory.f(attribute) });
          paramPrintWriter.printf("</use>", new Object[0]);
        } 
        if (attribute.getToDoFlag() != -1)
          paramPrintWriter.printf("<use id='%s' x='%.0f' y='%.0f' xlink:href='#%s'/>", new Object[] { str3, Double.valueOf(rect.a() + rect.c() - i), Double.valueOf(rect.b() + (i * (b1 + 1)) + n), "flag" + attribute.getToDoFlag() }); 
        paramPrintWriter.printf("<a xlink:href='#%s'>", new Object[] { EscapeChars.forURL(depict.getEntity().getEntity().getNameWithSchemaName() + "." + depict.getEntity().getEntity().getNameWithSchemaName()) });
        paramPrintWriter.printf("<text id='%s' x='%.0f' y='%.0f'%s%s>%s</text>", new Object[] { EscapeChars.forHtml(depict.getEntity().getNameWithSchemaName() + "." + depict.getEntity().getNameWithSchemaName()), 
              Double.valueOf(rect.a() + i + m), 
              Double.valueOf(rect.b() + (i * (b1 + 2)) + j - 1.0D), 
              a(attribute), 
              attribute.hasMarker(1) ? " class='colPk'" : "", 
              EscapeChars.forHtml(attribute.getDisplayName(paramDiagram)) });
        paramPrintWriter.printf("<title>%s</title>", new Object[] { ToolTipFactory.c(attribute) });
        paramPrintWriter.printf("</a>\n", new Object[0]);
        if (paramDiagram.isShowDataType()) {
          String str = StringUtil.cutOfWithDots(attribute.getTypeString(DataTypeFormat.b), 24);
          paramPrintWriter.printf("<text x='%.0f' y='%.0f' class='dataType' style='fill:#%s;'>%s</text>", new Object[] { Double.valueOf(rect.a() + rect.c() - i), Double.valueOf(rect.b() + (i * (b1 + 2)) + j), 
                ColorUtil.b(Color.web(paramExpose.is("darkTheme") ? "#8f8585" : "#9f9696"), depict.getColor()), 
                EscapeChars.forHtml(str) });
        } 
        String str4 = null;
        if (attribute.hasMarker(32)) {
          str4 = "fk";
        } else if (attribute.hasMarker(64)) {
          str4 = "ref";
        } 
        if (str4 != null) {
          paramPrintWriter.printf("<a xlink:href='#%s'>", new Object[] { EscapeChars.forURL(depict.getEntity().getEntity().getNameWithSchemaName() + "." + depict.getEntity().getEntity().getNameWithSchemaName()) });
          paramPrintWriter.printf("<use id='%s' x='%.0f' y='%.0f' xlink:href='#%s'/>", new Object[] { str4, Double.valueOf(rect.a() + rect.c() - n), Double.valueOf(rect.b() + (i * (b1 + 1)) + n), str4 });
          paramPrintWriter.printf("<title>%s</title>", new Object[] { ToolTipFactory.b(attribute, paramDiagram.isShowSchemaName()) });
          paramPrintWriter.printf("</a>\n", new Object[0]);
        } else if (!paramDiagram.isShowDataType()) {
          String str = Attribute.a(attribute);
          if (str != null)
            paramPrintWriter.printf("<text x='%.0f' y='%.0f' text-anchor='end' class='colType'>%s</text>", new Object[] { Double.valueOf(rect.a() + rect.c() - m), Double.valueOf(rect.b() + (i * (b1 + 2)) + k), 
                  EscapeChars.forHtml(str) }); 
        } 
        b1++;
      } 
      if (depict.hasHiddenAttributes())
        paramPrintWriter.printf("<text x='%.0f' y='%.0f'>--more--</text>\n", new Object[] { Double.valueOf(rect.a() + i), Double.valueOf(rect.b() + rect.d() - n) }); 
      paramPrintWriter.printf("\n", new Object[0]);
      paramPrintWriter.flush();
    } 
    for (Callout callout : paramDiagram.callouts) {
      rect.a(callout.getPosition());
      paramPrintWriter.printf("<!-- == Callout == -->\n", new Object[0]);
      paramPrintWriter.printf("<rect class='callout' x='%.0f' y='%.0f' width='%.0f' height='%.0f' rx='%d' ry='%d'  />\n", new Object[] { Double.valueOf(rect.a()), Double.valueOf(rect.b() - i), Double.valueOf(rect.c() + i), Double.valueOf(rect.d() + i), Integer.valueOf(i), Integer.valueOf(i) });
      paramPrintWriter.printf("<text x='%.0f' y='%.0f' class='callout'>\n", new Object[] { Double.valueOf(rect.a() + i + j), Double.valueOf(rect.b() - i) });
      double d1 = paramExpose.getInt("calloutFontSize", 14) * Callout.d;
      double d2 = d1;
      for (String str3 : callout.d()) {
        if (str3.trim().isEmpty()) {
          d2 += d1;
        } else {
          paramPrintWriter.printf(Locale.US, "    <tspan x='%f' dx='%d' dy='%.0f'>%s</tspan>\n", new Object[] { Double.valueOf(rect.a() + k), Integer.valueOf(j), Double.valueOf(d2), 
                
                EscapeChars.forHtml(str3) });
          d2 = d1;
        } 
      } 
      paramPrintWriter.printf("</text>\n", new Object[0]);
      double d3 = -1.0D, d4 = -1.0D;
      String str = null;
      switch (SvgWriter$2.a[callout.a().ordinal()]) {
        case 1:
          str = "calloutArrowUp";
          d3 = rect.a() + rect.c() - (2 * i);
          d4 = rect.b() - (2 * i);
          break;
        case 2:
          str = "calloutArrowUp";
          d3 = rect.a() + i;
          d4 = rect.b() - (2 * i);
          break;
        case 3:
          str = "calloutArrowDown";
          d3 = rect.a() + rect.c() - (2 * i);
          d4 = rect.b() + rect.d();
          break;
        case 4:
          str = "calloutArrowDown";
          d3 = rect.a() + i;
          d4 = rect.b() + rect.d();
          break;
      } 
      if (str != null)
        paramPrintWriter.printf("<use x='%.0f' y='%.0f' xlink:href='#%s'/>\n\n", new Object[] { Double.valueOf(d3), Double.valueOf(d4), str }); 
    } 
    for (Shape shape : paramDiagram.shapes) {
      double d2;
      rect.a(shape.a.getPosition());
      paramPrintWriter.printf("<!-- == Shape == -->\n", new Object[0]);
      String str = "style='fill:url(#shape" + ColorUtil.a(shape.c()) + "); stroke-width:0;filter:url(#shadow);' ";
      switch (SvgWriter$2.b[shape.b().ordinal()]) {
        case 1:
          paramPrintWriter.printf("<rect %s class='shape' x='%.0f' y='%.0f' width='%.0f' height='%.0f' rx='%d' ry='%d' />\n", new Object[] { str, Double.valueOf(rect.a()), Double.valueOf(rect.b() - i), Double.valueOf(rect.c() + i), Double.valueOf(rect.d() + i), Integer.valueOf(i), Integer.valueOf(i) });
          break;
        case 2:
          paramPrintWriter.printf("<ellipse %s class='shape'  cx='%.0f' cy='%.0f' rx='%.0f' ry='%.0f />", new Object[] { str, Double.valueOf(rect.a() + rect.c() / 2.0D), Double.valueOf(rect.b() + rect.d() / 2.0D), Double.valueOf(rect.c() / 2.0D), Double.valueOf(rect.d() / 2.0D) });
          break;
        case 3:
          d1 = rect.c() / 2.0D;
          d2 = rect.d() / 2.0D;
          paramPrintWriter.printf("<path %s class='shape' d='M %.0f,%.0f L %.0f,%.0f L %.0f,%.0f L %.0f,%.0f L %.0f,%.0f' />", new Object[] { 
                str, 
                
                Double.valueOf(rect.a() + d1), Double.valueOf(rect.b()), 
                Double.valueOf(rect.a() + rect.c()), Double.valueOf(rect.b() + d2), 
                Double.valueOf(rect.a() + d1), Double.valueOf(rect.b() + rect.d()), 
                Double.valueOf(rect.a()), Double.valueOf(rect.b() + d2), 
                Double.valueOf(rect.a() + d1), 
                Double.valueOf(rect.b()) });
          break;
      } 
      double d1 = paramExpose.getInt("calloutFontSize", 14) * Callout.d;
      int i2 = (int)((shape.a.getPosition().d() - ((shape.a()).length * i)) / 2.0D);
      paramPrintWriter.printf("<text x='%.0f' y='%.0f' class='callout'>\n", new Object[] { Double.valueOf(rect.a() + j), Double.valueOf(rect.b() + i2 - i) });
      double d3 = d1;
      for (String str3 : shape.a()) {
        int i3 = FxAbstractDiagramPane.a(str3);
        if (str3.trim().isEmpty()) {
          d3 += d1;
        } else if (shape.b() == ShapeStyle.a) {
          paramPrintWriter.printf(Locale.US, "    <tspan x='%.0f' dx='%d' dy='%.0f'>%s</tspan>\n", new Object[] { Double.valueOf(rect.a() + k), Integer.valueOf(j), Double.valueOf(d3), 
                
                EscapeChars.forHtml(str3) });
          d3 = d1;
        } else {
          paramPrintWriter.printf(Locale.US, "    <tspan x='%d' dx='%d' dy='%.0f'>%s</tspan>\n", new Object[] { Integer.valueOf((int)(rect.a() + (rect.c() - i3) / 2.0D)), Integer.valueOf(j), Double.valueOf(d3), 
                
                EscapeChars.forHtml(str3) });
          d3 = d1;
        } 
      } 
      paramPrintWriter.printf("</text>\n", new Object[0]);
    } 
    paramPrintWriter.printf("</g>", new Object[0]);
    paramPrintWriter.printf("</svg>", new Object[0]);
  }
  
  private static String a(Attribute paramAttribute) {
    StringBuilder stringBuilder = new StringBuilder();
    for (Relation relation : paramAttribute.getEntity().getRelations()) {
      if (relation.getSourceAttributes().contains(paramAttribute)) {
        if (!stringBuilder.isEmpty())
          stringBuilder.append(","); 
        stringBuilder.append("'").append(b(relation)).append("'");
        for (Attribute attribute : relation.getTargetAttributes()) {
          if (attribute != null) {
            if (!stringBuilder.isEmpty())
              stringBuilder.append(","); 
            stringBuilder.append("'").append(attribute.getEntity().getNameWithSchemaName()).append(".").append(attribute.getName()).append("'");
          } 
        } 
      } 
    } 
    for (Relation relation : paramAttribute.getEntity().getImportedRelations()) {
      if (relation.getTargetAttributes().contains(paramAttribute)) {
        if (!stringBuilder.isEmpty())
          stringBuilder.append(","); 
        stringBuilder.append("'").append(b(relation)).append("'");
        for (Attribute attribute : relation.getSourceAttributes()) {
          if (attribute != null) {
            if (!stringBuilder.isEmpty())
              stringBuilder.append(","); 
            stringBuilder.append("'").append(attribute.getEntity().getNameWithSchemaName()).append(".").append(attribute.getName()).append("'");
          } 
        } 
      } 
    } 
    return !stringBuilder.isEmpty() ? (" onmouseover=\"hghl([" + String.valueOf(stringBuilder) + "])\" onmouseout=\"uhghl([" + String.valueOf(stringBuilder) + "])\"") : "";
  }
  
  private static String a(Relation paramRelation) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("'").append(b(paramRelation)).append("'");
    for (Attribute attribute : paramRelation.getSourceAttributes()) {
      if (attribute != null) {
        if (!stringBuilder.isEmpty())
          stringBuilder.append(","); 
        stringBuilder.append("'").append(attribute.getEntity().getNameWithSchemaName()).append(".").append(attribute.getName()).append("'");
      } 
    } 
    for (Attribute attribute : paramRelation.getTargetAttributes()) {
      if (attribute != null) {
        if (!stringBuilder.isEmpty())
          stringBuilder.append(","); 
        stringBuilder.append("'").append(attribute.getEntity().getNameWithSchemaName()).append(".").append(attribute.getName()).append("'");
      } 
    } 
    return !stringBuilder.isEmpty() ? (" onmouseover=\"hghl([" + String.valueOf(stringBuilder) + "])\" onmouseout=\"uhghl([" + String.valueOf(stringBuilder) + "])\"") : "";
  }
  
  private static String b(Relation paramRelation) {
    return paramRelation.getEntity().getName() + "_" + paramRelation.getEntity().getName();
  }
  
  public static List a(Diagram paramDiagram) {
    TreeSelection treeSelection = new TreeSelection();
    for (Depict depict : paramDiagram.depicts)
      treeSelection.select(depict.getEntity()); 
    ArrayList<Depict> arrayList = new ArrayList();
    for (Depict depict : paramDiagram.depicts) {
      if (treeSelection.isSelected(depict.getEntity()))
        arrayList.add(depict); 
    } 
    arrayList.sort(Comparator.comparing(Depict::getName));
    return arrayList;
  }
}
