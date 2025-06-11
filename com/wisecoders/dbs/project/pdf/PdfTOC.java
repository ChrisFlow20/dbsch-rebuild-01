package com.wisecoders.dbs.project.pdf;

import com.lowagie.text.Anchor;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.draw.DottedLineSeparator;
import com.lowagie.text.pdf.draw.DrawInterface;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.schema.Layout;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PdfTOC {
  private final PdfBaseFonts a;
  
  private final String b;
  
  private final Map c = new LinkedHashMap<>();
  
  private final Map d = new LinkedHashMap<>();
  
  private final Map e = new LinkedHashMap<>();
  
  PdfTOC(PdfBaseFonts paramPdfBaseFonts, String paramString) {
    this.a = paramPdfBaseFonts;
    this.b = paramString;
  }
  
  void a(Layout paramLayout, int paramInt) {
    this.c.put(paramLayout, Integer.valueOf(paramInt));
  }
  
  void a(Entity paramEntity, int paramInt) {
    if (!this.d.containsKey(paramEntity))
      this.d.put(paramEntity, Integer.valueOf(paramInt)); 
  }
  
  void b(Entity paramEntity, int paramInt) {
    if (!this.e.containsKey(paramEntity))
      this.e.put(paramEntity, Integer.valueOf(paramInt)); 
  }
  
  public void a(Document paramDocument, List paramList) {
    a(paramDocument);
    a(paramDocument, "Layouts", this.c);
    a(paramDocument, "Tables", paramList, this.d);
    a(paramDocument, "Views", paramList, this.e);
  }
  
  private void a(Document paramDocument, String paramString, Map paramMap) {
    if (!paramMap.isEmpty()) {
      Paragraph paragraph = new Paragraph(paramString, this.a.a(13.0F, 0, a.c));
      paragraph.setSpacingBefore(12.0F);
      paramDocument.add((Element)paragraph);
      Chunk chunk = new Chunk((DrawInterface)new DottedLineSeparator());
      byte b = 1;
      for (Layout layout : paramMap.keySet()) {
        Chunk chunk1 = new Chunk("" + b++ + ". " + b++);
        chunk1.setLocalGoto(layout.getName());
        Paragraph paragraph1 = new Paragraph(chunk1);
        paragraph1.setFont(this.a.a(12.0F, 0, a.a));
        paragraph1.add((Element)chunk);
        paragraph1.add(String.valueOf(paramMap.get(layout)));
        paramDocument.add((Element)paragraph1);
      } 
    } 
  }
  
  private void a(Document paramDocument, String paramString, List paramList, Map paramMap) {
    if (!paramMap.isEmpty()) {
      Paragraph paragraph = new Paragraph(paramString, this.a.a(13.0F, 0, a.c));
      paragraph.setSpacingBefore(12.0F);
      paramDocument.add((Element)paragraph);
      Chunk chunk = new Chunk((DrawInterface)new DottedLineSeparator());
      for (Entity entity : paramMap.keySet()) {
        Paragraph paragraph1 = new Paragraph();
        paragraph1.setFont(this.a.a(12.0F, 0, a.c));
        Chunk chunk1 = new Chunk(entity.getNameWithSchemaName());
        chunk1.setFont(this.a.a(12.0F, 0, a.e));
        chunk1.setLocalGoto(entity.getNameWithSchemaName());
        paragraph1.add((Element)chunk1);
        paragraph1.add((Element)new Chunk(" [ "));
        byte b = 1;
        for (Layout layout : paramList) {
          if (layout.containsDepictForEntity(entity)) {
            Chunk chunk2 = new Chunk("" + b + " ");
            chunk2.setFont(this.a.a(12.0F, 0, a.e));
            chunk2.setLocalGoto(layout.getName());
            paragraph1.add((Element)chunk2);
          } 
          b++;
        } 
        paragraph1.add((Element)new Chunk("]"));
        paragraph1.setFont(this.a.a(12.0F, 0, a.a));
        paragraph1.add((Element)chunk);
        paragraph1.add(String.valueOf(paramMap.get(entity)));
        paramDocument.add((Element)paragraph1);
      } 
    } 
  }
  
  private void a(Document paramDocument) {
    PdfPTable pdfPTable = new PdfPTable(new float[] { 100.0F });
    pdfPTable.setWidthPercentage(100.0F);
    pdfPTable.setSpacingAfter(10.0F);
    pdfPTable.setSpacingBefore(10.0F);
    pdfPTable.setLockedWidth(false);
    PdfPCell pdfPCell1 = new PdfPCell(new Phrase(this.b, this.a.a(14.0F, 1, a.b)));
    pdfPCell1.setBorderColor(a.f);
    pdfPCell1.setBackgroundColor(a.f);
    pdfPCell1.setColspan(2);
    pdfPCell1.setPadding(12.0F);
    pdfPCell1.setPaddingBottom(4.0F);
    pdfPTable.addCell(pdfPCell1);
    Anchor anchor = new Anchor((new SimpleDateFormat("d-MM-yyyy")).format(Long.valueOf(System.currentTimeMillis())) + " " + (new SimpleDateFormat("d-MM-yyyy")).format(Long.valueOf(System.currentTimeMillis())), this.a.a(10.0F, 0, a.b));
    anchor.setReference("https://dbschema.com");
    PdfPCell pdfPCell2 = new PdfPCell((Phrase)anchor);
    pdfPCell2.setHorizontalAlignment(2);
    pdfPCell2.setBorderColor(a.f);
    pdfPCell2.setBackgroundColor(a.f);
    pdfPCell2.setPadding(8.0F);
    pdfPTable.addCell(pdfPCell2);
    paramDocument.add((Element)pdfPTable);
  }
}
