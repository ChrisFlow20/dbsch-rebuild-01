package com.wisecoders.dbs.project.pdf;

import com.lowagie.text.Anchor;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import java.awt.Color;

class a extends PdfPTable {
  public static final Color a = new Color(1316377);
  
  public static final Color b = new Color(16777215);
  
  public static final Color c = new Color(2766918);
  
  public static final Color d = new Color(5658973);
  
  public static final Color e = new Color(21937);
  
  private static final Color g = new Color(16054266);
  
  private static final Color h = new Color(14805747);
  
  public static final Color f = new Color(1653069);
  
  private static final Color i = new Color(1048575);
  
  private static final Color j = new Color(13292254);
  
  private static final float k = 0.0F;
  
  private static final float l = 0.7F;
  
  private final PdfBaseFonts m;
  
  private int n = 0;
  
  a(PdfBaseFonts paramPdfBaseFonts, float[] paramArrayOffloat) {
    super(paramArrayOffloat);
    this.m = paramPdfBaseFonts;
    setWidthPercentage(100.0F);
    setSpacingAfter(8.0F);
    setSpacingBefore(8.0F);
    setLockedWidth(false);
  }
  
  void a(String paramString) {
    a(paramString, 1, false, false);
  }
  
  void b(String paramString) {
    a(paramString, 1, false, true);
  }
  
  void a(String paramString, int paramInt, boolean paramBoolean1, boolean paramBoolean2) {
    PdfPCell pdfPCell = new PdfPCell(new Phrase(paramString, this.m.a(9.0F, paramBoolean1 ? 1 : 0, paramBoolean1 ? c : (paramBoolean2 ? d : a))));
    pdfPCell.setBorderColor(i);
    pdfPCell.setBorderWidth(0.0F);
    pdfPCell.setColspan(paramInt);
    pdfPCell.setPaddingBottom(4.0F);
    if (this.n / this.relativeWidths.length % 2 == 0)
      pdfPCell.setBackgroundColor(g); 
    addCell(pdfPCell);
    this.n += paramInt;
  }
  
  void c(String paramString) {
    for (String str : paramString.split(",")) {
      PdfPCell pdfPCell = new PdfPCell(new Phrase(str, this.m.a(9.0F, 1, d)));
      pdfPCell.setBorderColor(i);
      pdfPCell.setBorderWidth(0.0F);
      pdfPCell.setPaddingBottom(4.0F);
      pdfPCell.setBackgroundColor(h);
      addCell(pdfPCell);
    } 
  }
  
  void a(String paramString1, String paramString2, int paramInt) {
    Anchor anchor = new Anchor(paramString1, this.m.a(11.0F, 1, b));
    anchor.setName(paramString2);
    PdfPCell pdfPCell = new PdfPCell((Phrase)anchor);
    pdfPCell.setBorderColor(i);
    pdfPCell.setBorderWidth(0.0F);
    pdfPCell.setBorderWidthBottom(0.7F);
    pdfPCell.setBorderColorBottom(j);
    pdfPCell.setBackgroundColor(f);
    pdfPCell.setColspan(paramInt);
    pdfPCell.setPaddingBottom(4.0F);
    addCell(pdfPCell);
    this.n += paramInt;
  }
}
