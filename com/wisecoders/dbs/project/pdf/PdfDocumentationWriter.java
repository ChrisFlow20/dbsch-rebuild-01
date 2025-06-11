package com.wisecoders.dbs.project.pdf;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.project.html.HTML5Writer;
import com.wisecoders.dbs.project.html.SvgWriter;
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
import com.wisecoders.dbs.sys.StringUtil;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PdfDocumentationWriter {
  private final Document a;
  
  private final PdfBaseFonts b;
  
  public PdfDocumentationWriter(Expose paramExpose) {
    switch (paramExpose.getString("pageSize", "A0")) {
      case "A0":
      
      case "A1":
      
      case "A2":
      
      case "A3":
      
      case "A4":
      
      case "A5":
      
      case "B0":
      
      case "B1":
      
      case "B2":
      
      case "B3":
      
      case "Legal":
      
      case "Executive":
      
      case "Tabloid":
      
      default:
        break;
    } 
    Rectangle rectangle = PageSize.LETTER;
    if (paramExpose.getBoolean("landscape", false))
      rectangle = rectangle.rotate(); 
    this.a = new Document(rectangle, 30.0F, 20.0F, 20.0F, 20.0F);
    this.b = new PdfBaseFonts(paramExpose);
    PdfWriter pdfWriter = PdfWriter.getInstance(this.a, new FileOutputStream(paramExpose.file));
    try {
      pdfWriter.setLinearPageMode();
      this.a.open();
      String str = paramExpose.getString("title", "Documentation");
      PdfTOC pdfTOC = new PdfTOC(this.b, str);
      for (Layout layout : paramExpose.layouts) {
        if (paramExpose.is("image")) {
          this.a.setPageSize(new Rectangle(0.0F, 0.0F, (float)layout.diagram.getWidth() * 0.8F, ((float)layout.diagram.getHeight() + 40.0F) * 0.8F));
          this.a.newPage();
          pdfTOC.a(layout, pdfWriter.getPageNumber());
          new b(pdfWriter, this.b, paramExpose, layout);
        } 
        if (paramExpose.is("text")) {
          this.a.setPageSize(rectangle);
          this.a.newPage();
          Chunk chunk = new Chunk(layout.getName());
          chunk.setFont(this.b.a(12.0F, 0, a.a));
          chunk.setLocalDestination(layout.getName());
          Paragraph paragraph = new Paragraph();
          paragraph.setSpacingAfter(5.0F);
          paragraph.add((Element)chunk);
          this.a.add((Element)paragraph);
          List list = SvgWriter.a(layout.diagram);
          for (Depict depict : list) {
            if (depict.entity instanceof Table) {
              pdfTOC.a(depict.getEntity(), pdfWriter.getPageNumber());
              a(depict, layout.diagram.isShowSchemaName(), paramExpose);
            } 
          } 
          for (Depict depict : list) {
            if (depict.entity instanceof View) {
              pdfTOC.b(depict.getEntity(), pdfWriter.getPageNumber());
              a((View)depict.entity, layout.diagram.isShowSchemaName(), paramExpose);
            } 
          } 
          if (paramExpose.is("functions")) {
            ArrayList<Schema> arrayList = new ArrayList();
            for (Depict depict : list) {
              Schema schema = depict.getEntity().getSchema();
              if ((!schema.procedures.isEmpty() || !schema.functions.isEmpty()) && !arrayList.contains(schema)) {
                arrayList.add(schema);
                a(schema);
              } 
            } 
          } 
          if (paramExpose.is("sequences")) {
            ArrayList<Schema> arrayList = new ArrayList();
            for (Depict depict : list) {
              Schema schema = depict.getEntity().getSchema();
              if (!schema.sequences.isEmpty() && !arrayList.contains(schema)) {
                arrayList.add(schema);
                b(schema);
              } 
            } 
          } 
        } 
      } 
      if (paramExpose.is("TOC")) {
        this.a.newPage();
        int i = pdfWriter.getPageNumber() - 1;
        pdfTOC.a(this.a, paramExpose.layouts);
        this.a.newPage();
        int j = pdfWriter.getPageNumber() - 1;
        int k = j - i;
        int[] arrayOfInt = new int[j];
        for (byte b = 0; b < j; b++)
          arrayOfInt[b] = (b < k) ? (i + b + 1) : (b - k + 1); 
        pdfWriter.reorderPages(arrayOfInt);
      } 
      this.a.close();
      if (pdfWriter != null)
        pdfWriter.close(); 
    } catch (Throwable throwable) {
      if (pdfWriter != null)
        try {
          pdfWriter.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        }  
      throw throwable;
    } 
  }
  
  private void a(Depict paramDepict, boolean paramBoolean, Expose paramExpose) {
    Table table = (Table)paramDepict.getEntity();
    boolean bool = HTML5Writer.hasComments(table);
    (new float[4])[0] = 5.0F;
    (new float[4])[1] = 25.0F;
    (new float[4])[2] = 20.0F;
    (new float[4])[3] = 50.0F;
    (new float[3])[0] = 7.0F;
    (new float[3])[1] = 45.0F;
    (new float[3])[2] = 48.0F;
    a a = new a(this.b, bool ? new float[4] : new float[3]);
    String str = table.getSymbolicName() + " " + table.getSymbolicName();
    byte b = bool ? 4 : 3;
    a.a(str, table.getNameWithSchemaName(), b);
    a.c("Idx,Name,Data Type" + (bool ? ",Description" : ""));
    if (table.getComment() != null)
      a.a(a(table), b, false, false); 
    a.setHeaderRows(1);
    for (Column column : table.columns) {
      if (paramExpose.getBoolean("allColumn", true) || paramDepict.getVisibleAttributes().contains(column))
        a(a, column, bool); 
    } 
    if (paramExpose.is("indexes")) {
      if (!table.getIndexes().isEmpty()) {
        a.a("Indexes", b, true, false);
        a.c("Type,Name,On" + (bool ? ",Description" : ""));
      } 
      for (Index index : table.getIndexes()) {
        a.a((index.getType() == IndexType.PRIMARY_KEY) ? "Pk" : (
            (index.getType() == IndexType.UNIQUE_KEY || index.getType() == IndexType.UNIQUE_INDEX) ? "Unq" : ""));
        a.a(index.getName());
        a.a(Index.listAttributes(index.getColumns()), 1, false, true);
        if (bool)
          a.a(a(index)); 
      } 
    } 
    if (paramExpose.is("foreignKeys")) {
      if (!table.getRelations().isEmpty()) {
        a.a((table.is(UnitProperty.f).booleanValue() || table.is(UnitProperty.g).booleanValue()) ? "Relationships" : "Foreign Keys", b, true, false);
        a.c("Type,Name,On" + (bool ? ",Description" : ""));
      } 
      for (ForeignKey foreignKey : table.getRelations()) {
        a.a(foreignKey.isVirtual() ? "Vir" : "");
        a.a(foreignKey.getName() + " ( " + foreignKey.getName() + " ) ref " + 
            Index.listAttributes(foreignKey.getSourceAttributes()) + " ( " + foreignKey
            .getTargetEntity().getName() + " )", 
            2, false, false);
        if (bool)
          a.a(a(foreignKey)); 
      } 
      if (!table.constraints.isEmpty()) {
        a.a("Constraints", b, true, false);
        a.c(",Name,Definition" + (bool ? ",Description" : ""));
      } 
      for (Constraint constraint : table.constraints) {
        a.a("");
        a.a(constraint.getName());
        a.a(constraint.getText());
        if (bool)
          a.a(a(constraint)); 
      } 
      ArrayList<Trigger> arrayList = new ArrayList();
      for (Trigger trigger : table.schema.triggers) {
        if (trigger.getOwningTable() == table)
          arrayList.add(trigger); 
      } 
      if (!arrayList.isEmpty()) {
        a.a("Triggers", b, true, false);
        a.c(",Name,Definition" + (bool ? ",Description" : ""));
      } 
      for (Trigger trigger : arrayList) {
        a.a("");
        a.a(trigger.getName());
        a.a("");
        if (bool)
          a.a(a(trigger)); 
        if (paramExpose.is("triggers"))
          a.a(trigger.getText(), b, false, false); 
      } 
      if (StringUtil.isFilledTrim(table.getOptions())) {
        a.a("Options", b, true, false);
        a.a(table.getOptions(), b, false, false);
      } 
    } 
    this.a.add((Element)a);
  }
  
  private void a(a parama, Column paramColumn, boolean paramBoolean) {
    StringBuilder stringBuilder1 = new StringBuilder();
    if (paramColumn.isMandatory())
      stringBuilder1.append("* "); 
    if (paramColumn.hasMarker(1)) {
      stringBuilder1.append("Pk");
    } else if (paramColumn.hasMarker(8)) {
      stringBuilder1.append("Unq");
    } else if (paramColumn.hasMarker(4)) {
      stringBuilder1.append("Idx");
    } 
    parama.a(stringBuilder1.toString());
    parama.a(paramColumn.getNameWithPath());
    StringBuilder stringBuilder2 = new StringBuilder(paramColumn.getTypeString(DataTypeFormat.b));
    if (paramColumn.isUnsigned())
      stringBuilder2.append(" UNSIGNED"); 
    if (paramColumn.isIdentity())
      stringBuilder2.append(" ").append(paramColumn.getIdentity()); 
    if (StringUtil.isFilled(paramColumn.getOptions()))
      stringBuilder2.append(" ").append(paramColumn.getOptions()); 
    if (paramColumn.hasDefaultValue())
      stringBuilder2.append(" DEFAULT ").append(paramColumn.getDefaultValue()); 
    parama.a(stringBuilder2.toString(), 1, false, true);
    if (paramBoolean)
      parama.a(a(paramColumn)); 
    if (paramColumn.hasChildEntity())
      for (Column column : (paramColumn.getChildEntity()).columns)
        a(parama, column, paramBoolean);  
  }
  
  private void a(View paramView, boolean paramBoolean, Expose paramExpose) {
    boolean bool = HTML5Writer.hasComments(paramView);
    a a = new a(this.b, new float[] { 100.0F });
    String str = "View " + (paramBoolean ? paramView.getNameWithSchemaName() : paramView.getName());
    a.a(str, paramView.getNameWithSchemaName(), 1);
    a.setHeaderRows(1);
    if (bool)
      a.a(a(paramView)); 
    if (paramView.getScript() != null && paramExpose.is("view_query"))
      a.a(paramView.getScript()); 
    this.a.add((Element)a);
  }
  
  private void a(Schema paramSchema) {
    a a = new a(this.b, new float[] { 30.0F, 70.0F });
    a.a(paramSchema.getSymbolicName() + " " + paramSchema.getSymbolicName(), paramSchema.getName(), 4);
    a.setHeaderRows(1);
    if (!paramSchema.functions.isEmpty()) {
      a.a("Functions", 2, true, false);
      for (Function function : paramSchema.functions) {
        a.a(function.getName());
        a.a(a(function));
      } 
    } 
    if (!paramSchema.procedures.isEmpty()) {
      a.a("Procedures", 2, true, false);
      for (Procedure procedure : paramSchema.procedures) {
        a.a(procedure.getName());
        a.a(a(procedure));
      } 
    } 
    this.a.add((Element)a);
  }
  
  private void b(Schema paramSchema) {
    a a = new a(this.b, new float[] { 30.0F, 70.0F });
    a.a(paramSchema.getSymbolicName() + " " + paramSchema.getSymbolicName(), paramSchema.getName(), 4);
    a.setHeaderRows(1);
    if (!paramSchema.sequences.isEmpty()) {
      a.a("Sequences", 2, true, false);
      for (Sequence sequence : paramSchema.sequences) {
        a.a(sequence.getName());
        a.a(a(sequence));
      } 
    } 
    this.a.add((Element)a);
  }
  
  static String a(AbstractUnit paramAbstractUnit) {
    StringBuilder stringBuilder = new StringBuilder();
    if (paramAbstractUnit.getComment() != null)
      stringBuilder.append(paramAbstractUnit.getComment()); 
    Map map = paramAbstractUnit.getCommentTags();
    if (map != null) {
      if (stringBuilder.length() > 0)
        stringBuilder.append("\n"); 
      stringBuilder.append("Tags: ");
      boolean bool = false;
      for (String str : map.keySet()) {
        if (bool)
          stringBuilder.append(", "); 
        stringBuilder.append(str).append(" = ").append((String)map.get(str));
        bool = true;
      } 
    } 
    return stringBuilder.toString();
  }
}
