package com.wisecoders.dbs.project.pdf;

import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfAction;
import com.lowagie.text.pdf.PdfAnnotation;
import com.lowagie.text.pdf.PdfBorderDictionary;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfName;
import com.lowagie.text.pdf.PdfShading;
import com.lowagie.text.pdf.PdfShadingPattern;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import com.wisecoders.dbs.diagram.fx.notation.Notation;
import com.wisecoders.dbs.diagram.model.Diagram;
import com.wisecoders.dbs.diagram.model.PathWriter;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import javafx.scene.paint.Color;

public class PdfCanvas {
  static final float a = 0.8F;
  
  private final PdfWriter D;
  
  private final PdfContentByte E;
  
  private final float F;
  
  final float b;
  
  final float c;
  
  final float d;
  
  final float e;
  
  final float f;
  
  final float g;
  
  final float h;
  
  final float i;
  
  final PdfTemplate j;
  
  final PdfTemplate k;
  
  final PdfTemplate l;
  
  final PdfTemplate m;
  
  final PdfTemplate n;
  
  final PdfTemplate o;
  
  final PdfTemplate p;
  
  final PdfTemplate q;
  
  final PdfTemplate r;
  
  final PdfTemplate s;
  
  final PdfTemplate t;
  
  final PdfTemplate u;
  
  final PdfTemplate v;
  
  final PdfTemplate w;
  
  final PdfTemplate x;
  
  final PdfTemplate y;
  
  final PdfTemplate z;
  
  final PdfTemplate A;
  
  final PdfTemplate B;
  
  private final PdfBaseFonts G;
  
  private PdfTemplate H;
  
  PdfCanvas(PdfWriter paramPdfWriter, Notation paramNotation, PdfBaseFonts paramPdfBaseFonts, float paramFloat) {
    this.C = new PdfCanvas$1(this);
    this.J = new Color(12950847);
    this.K = new Color(5996981);
    this.L = new Color(15979659);
    this.M = new Color(11097623);
    this.N = new Color(12481638);
    this.O = new Color(1802276);
    this.D = paramPdfWriter;
    this.E = paramPdfWriter.getDirectContent();
    this.F = paramFloat * 0.8F;
    this.G = paramPdfBaseFonts;
    this.E.transform(new AffineTransform(0.8F, 0.0F, 0.0F, 0.8F, 0.0F, this.F * 0.19999999F));
    this.b = Diagram.cell;
    this.c = this.b / 2.0F;
    this.d = this.b / 3.0F;
    this.e = this.b / 4.0F;
    this.f = this.b / 5.0F;
    this.h = this.b / 7.0F;
    this.g = 3.0F * this.b / 4.0F;
    this.i = this.b / 8.0F;
    this.j = this.E.createTemplate(this.b, this.b);
    this.k = this.E.createTemplate(this.b, this.b);
    this.l = this.E.createTemplate(this.b, this.b);
    this.m = this.E.createTemplate(this.b, this.b);
    this.n = this.E.createTemplate(this.b, this.b);
    this.o = this.E.createTemplate(this.b, this.b);
    this.u = this.E.createTemplate(this.b, this.b);
    this.p = this.E.createTemplate(this.b, this.b);
    this.q = this.E.createTemplate(this.b, this.b);
    this.r = this.E.createTemplate(this.b, this.b);
    this.s = this.E.createTemplate(this.b, this.b);
    this.t = this.E.createTemplate(this.b, this.b);
    this.x = this.E.createTemplate(this.b, this.b);
    this.y = this.E.createTemplate(this.b, this.b);
    this.z = this.E.createTemplate(this.b, this.b);
    this.A = this.E.createTemplate(this.b, this.b);
    this.B = this.E.createTemplate(this.b, this.b);
    this.v = this.E.createTemplate(this.b, this.b);
    this.w = this.E.createTemplate(this.b, this.b);
    a(paramNotation);
    this.E.setFontAndSize(paramPdfBaseFonts.a(), 12.0F);
  }
  
  public void a(float paramFloat) {
    this.E.setFontAndSize(this.G.a(), paramFloat);
  }
  
  public void a() {
    this.E.fill();
  }
  
  public void b() {
    this.E.fillStroke();
  }
  
  public void c() {
    this.E.stroke();
  }
  
  public void a(Color paramColor) {
    this.E.setColorFill(paramColor);
  }
  
  public void a(Color paramColor) {
    this.E.setColorFill(c(paramColor));
  }
  
  public void b(Color paramColor) {
    this.E.setColorStroke(paramColor);
  }
  
  public void b(Color paramColor) {
    this.E.setColorStroke(c(paramColor));
  }
  
  PdfCanvas a(double paramDouble1, double paramDouble2) {
    this.E.moveTo((float)paramDouble1, this.F - (float)paramDouble2);
    return this;
  }
  
  PdfCanvas b(double paramDouble1, double paramDouble2) {
    this.E.lineTo((float)paramDouble1, this.F - (float)paramDouble2);
    return this;
  }
  
  PdfCanvas a(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    this.E.curveTo((float)paramDouble1, this.F - (float)paramDouble2, (float)paramDouble3, this.F - (float)paramDouble4);
    return this;
  }
  
  PdfCanvas a(double paramDouble1, double paramDouble2, double paramDouble3) {
    double d = paramDouble1 * Math.PI / 180.0D;
    this.E.transform(new AffineTransform(Math.cos(d), Math.sin(d), -Math.sin(d), Math.cos(d), paramDouble2, paramDouble3));
    return this;
  }
  
  PdfCanvas a(double paramDouble) {
    this.E.transform(AffineTransform.getRotateInstance(paramDouble * Math.PI / 180.0D));
    return this;
  }
  
  PdfCanvas c(double paramDouble1, double paramDouble2) {
    this.E.transform(AffineTransform.getTranslateInstance(paramDouble1, -paramDouble2));
    return this;
  }
  
  PdfCanvas a(double paramDouble1, double paramDouble2, String paramString) {
    if (paramString != null) {
      this.E.beginText();
      this.E.showTextAligned(0, paramString, (float)paramDouble1, this.F - (float)paramDouble2, 0.0F);
      this.E.endText();
      this.E.stroke();
    } 
    return this;
  }
  
  PdfCanvas a(double paramDouble1, double paramDouble2, String paramString, float paramFloat) {
    if (paramString != null) {
      this.E.beginText();
      this.E.showTextAligned(2, paramString, (float)paramDouble1, this.F - (float)paramDouble2, paramFloat);
      this.E.endText();
      this.E.stroke();
    } 
    return this;
  }
  
  void a(double paramDouble1, double paramDouble2, Color paramColor1, Color paramColor2) {
    PdfShading pdfShading = PdfShading.simpleAxial(this.D, 0.0F, this.F - (float)paramDouble1 * 0.8F, 0.0F, this.F - (float)paramDouble1 * 0.8F - (float)paramDouble2 * 0.8F, paramColor1, paramColor2);
    this.E.setShadingFill(new PdfShadingPattern(pdfShading));
  }
  
  void a(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5) {
    this.E.roundRectangle((float)paramDouble1, this.F - (float)paramDouble2 - (float)paramDouble4, (float)paramDouble3, (float)paramDouble4, (float)paramDouble5);
  }
  
  void b(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    this.E.ellipse((float)paramDouble1, this.F - (float)paramDouble2 - (float)paramDouble4, (float)paramDouble1 + (float)paramDouble3, this.F - (float)paramDouble2);
  }
  
  void c(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    float f = this.F - (float)paramDouble2 - (float)paramDouble4;
    this.E.moveTo((float)paramDouble1 + (float)paramDouble3 / 2.0F, f);
    this.E.lineTo((float)paramDouble1 + (float)paramDouble3, f + (float)paramDouble4 / 2.0F);
    this.E.lineTo((float)paramDouble1 + (float)paramDouble3 / 2.0F, f + (float)paramDouble4);
    this.E.lineTo((float)paramDouble1, f + (float)paramDouble4 / 2.0F);
    this.E.lineTo((float)paramDouble1 + (float)paramDouble3 / 2.0F, f);
    this.E.fillStroke();
  }
  
  void d(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    this.E.rectangle((float)paramDouble1, this.F - (float)paramDouble2 - (float)paramDouble4, (float)paramDouble3, (float)paramDouble4);
  }
  
  private PdfTemplate d() {
    if (this.H == null)
      this.H = this.D.getDirectContent().createTemplate(1.0F, 1.0F); 
    return this.H;
  }
  
  void a(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, String paramString) {
    float f1 = (float)paramDouble1 * 0.8F;
    float f2 = this.F - (float)paramDouble2 * 0.8F;
    float f3 = (float)paramDouble1 * 0.8F + (float)paramDouble3 * 0.8F;
    float f4 = this.F - (float)paramDouble2 * 0.8F - (float)paramDouble4 * 0.8F + this.e;
    PdfAnnotation pdfAnnotation = PdfAnnotation.createStamp(this.D, new Rectangle(f1, f2, f3, f4), paramString, "#Comment");
    pdfAnnotation.setAppearance(PdfName.N, d());
    this.D.addAnnotation(pdfAnnotation);
  }
  
  private static final PdfBorderDictionary I = new PdfBorderDictionary(0.0F, 4);
  
  final PathWriter C;
  
  private final Color J;
  
  private final Color K;
  
  private final Color L;
  
  private final Color M;
  
  private final Color N;
  
  private final Color O;
  
  void b(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, String paramString) {
    float f1 = (float)paramDouble1 * 0.8F;
    float f2 = this.F - (float)paramDouble2 * 0.8F;
    float f3 = (float)paramDouble1 * 0.8F + (float)paramDouble3 * 0.8F;
    float f4 = this.F - (float)paramDouble2 * 0.8F - (float)paramDouble4 * 0.8F + this.e;
    PdfAnnotation pdfAnnotation = PdfAnnotation.createLink(this.D, new Rectangle(f1, f2, f3, f4), PdfAnnotation.HIGHLIGHT_NONE, new PdfAction(paramString));
    pdfAnnotation.setBorderStyle(I);
    this.D.addAnnotation(pdfAnnotation);
  }
  
  void a(PdfTemplate paramPdfTemplate, double paramDouble1, double paramDouble2, int paramInt) {
    switch (paramInt) {
      case 3:
        this.E.addTemplate(paramPdfTemplate, 1.0F, 0.0F, 0.0F, 1.0F, this.b * (float)paramDouble1, this.F - this.b * (float)paramDouble2 + this.g);
        break;
      case 0:
        this.E.addTemplate(paramPdfTemplate, 0.0F, -1.0F, 1.0F, 0.0F, this.b * (float)paramDouble1 - this.e, this.F - this.b * (float)paramDouble2);
        break;
      case 1:
        this.E.addTemplate(paramPdfTemplate, -1.0F, 0.0F, 0.0F, -1.0F, this.b * (float)paramDouble1 + this.b, this.F - this.b * (float)paramDouble2 - this.e);
        break;
      case 2:
        this.E.addTemplate(paramPdfTemplate, 0.0F, 1.0F, -1.0F, 0.0F, this.b * (float)paramDouble1 + this.g, this.F - this.b * (float)paramDouble2 + this.b);
        break;
    } 
  }
  
  void a(PdfTemplate paramPdfTemplate, double paramDouble1, double paramDouble2) {
    this.E.addTemplate(paramPdfTemplate, (float)paramDouble1, this.F - (float)paramDouble2);
  }
  
  void b(double paramDouble) {
    this.E.setLineWidth((float)paramDouble);
  }
  
  void a(boolean paramBoolean) {
    if (paramBoolean) {
      this.E.setLineDash(this.e, this.e);
    } else {
      this.E.setLineDash(0.0F);
    } 
  }
  
  private void a(Notation paramNotation) {
    if (paramNotation instanceof com.wisecoders.dbs.diagram.fx.notation.BarkerNotation) {
      e();
    } else {
      c(this.k);
      f();
    } 
    c(this.j);
    b(this.j);
    this.u.setLineWidth(1.0F);
    this.u.setLineCap(1);
    this.u.setLineJoin(1);
    this.u.setColorStroke(this.N);
    this.u.moveTo(2.0F, 11.0F);
    this.u.lineTo(4.0F, 9.0F);
    this.u.stroke();
    this.u.moveTo(2.0F, 9.0F);
    this.u.lineTo(4.0F, 11.0F);
    this.u.stroke();
    this.p.setLineWidth(0.7F);
    this.p.setLineCap(1);
    this.p.setLineJoin(1);
    this.p.setColorStroke(this.J);
    this.p.setColorFill(this.L);
    this.p.circle(7.7F, 7.7F, 4.1F);
    this.p.fillStroke();
    this.p.moveTo(4.7F, 6.4F);
    this.p.lineTo(0.0F, 2.5F);
    this.p.lineTo(0.0F, 0.4F);
    this.p.curveTo(0.0F, 0.0F, 0.4F, 0.0F);
    this.p.lineTo(3.3F, 0.0F);
    this.p.lineTo(3.6F, 0.3F);
    this.p.lineTo(3.6F, 1.5F);
    this.p.lineTo(4.9F, 1.4F);
    this.p.lineTo(5.2F, 1.8F);
    this.p.lineTo(5.3F, 3.0F);
    this.p.lineTo(6.3F, 3.0F);
    this.p.lineTo(7.0F, 3.7F);
    this.p.fillStroke();
    this.p.setLineWidth(1.0F);
    this.q.setLineCap(1);
    this.q.setLineJoin(1);
    this.q.setColorStroke(this.M);
    this.q.setLineWidth(3.0F);
    this.q.moveTo(0.7F, 0.7F);
    this.q.lineTo(4.5F, 4.5F);
    this.q.stroke();
    this.q.setLineWidth(1.5F);
    this.q.setColorStroke(this.J);
    this.q.circle(7.7F, 7.7F, 4.1F);
    this.q.stroke();
    this.q.setLineWidth(1.0F);
    this.r.setLineCap(1);
    this.r.setLineJoin(1);
    this.r.setColorStroke(this.M);
    this.r.moveTo(7.7F, 5.8F);
    this.r.lineTo(7.7F, 9.5F);
    this.r.lineTo(7.0F, 8.8F);
    this.r.stroke();
    this.r.moveTo(7.0F, 5.8F);
    this.r.lineTo(8.4F, 5.8F);
    this.r.stroke();
    this.r.setLineWidth(3.0F);
    this.r.moveTo(0.7F, 0.7F);
    this.r.lineTo(4.5F, 4.5F);
    this.r.stroke();
    this.r.setLineWidth(1.5F);
    this.r.setColorStroke(this.J);
    this.r.circle(7.7F, 7.7F, 4.1F);
    this.r.stroke();
    this.r.setLineWidth(1.0F);
    this.s.setLineCap(1);
    this.s.setLineJoin(1);
    this.s.setColorFill(new Color(15393915));
    this.s.setColorStroke(new Color(12296253));
    this.s.rectangle(5.0F, 5.0F, 3.0F, 3.0F);
    this.s.fillStroke();
    this.s.rectangle(1.0F, 1.0F, 3.0F, 3.0F);
    this.s.fillStroke();
    this.s.rectangle(5.0F, 1.0F, 3.0F, 3.0F);
    this.s.fillStroke();
    this.t.setLineCap(1);
    this.t.setLineJoin(1);
    this.t.setColorStroke(new Color(5086143));
    this.t.setColorFill(new Color(9556977));
    this.t.rectangle(5.0F, 5.0F, 4.0F, 3.0F);
    this.t.fillStroke();
    this.t.rectangle(2.0F, 1.0F, 7.0F, 3.0F);
    this.t.fillStroke();
    this.x.setLineWidth(1.0F);
    this.x.setLineCap(1);
    this.x.setLineJoin(1);
    this.x.setColorStroke(this.J);
    this.x.setColorFill(this.J);
    this.x.moveTo(8.0F, 1.0F);
    this.x.lineTo(8.0F, 6.7F);
    this.x.curveTo(8.0F, 8.4F, 6.1F, 8.7F);
    this.x.lineTo(2.1F, 8.7F);
    this.x.stroke();
    this.x.moveTo(4.0F, 11.2F);
    this.x.lineTo(0.0F, 8.6F);
    this.x.lineTo(4.0F, 6.4F);
    this.x.fill();
    this.x.setLineWidth(1.0F);
    this.x.setLineWidth(1.0F);
    this.y.setLineWidth(1.0F);
    this.y.setLineCap(1);
    this.y.setLineJoin(1);
    this.y.setColorStroke(this.K);
    this.y.setColorFill(this.K);
    this.y.moveTo(1.1F, 1.0F);
    this.y.lineTo(1.0F, 6.7F);
    this.y.curveTo(1.0F, 8.4F, 2.9F, 8.7F);
    this.y.lineTo(7.9F, 8.7F);
    this.y.stroke();
    this.y.moveTo(5.0F, 11.2F);
    this.y.lineTo(9.0F, 8.6F);
    this.y.lineTo(5.0F, 6.4F);
    this.y.fill();
    this.y.setLineWidth(1.0F);
    a(this.z, this.K);
    a(this.A, this.N);
    a(this.B, this.O);
    this.v.moveTo(this.c, 0.0F);
    this.v.lineTo(this.b, this.b);
    this.v.lineTo(0.0F, this.b);
    this.v.fill();
    this.w.moveTo(this.c, this.b);
    this.w.lineTo(this.b, 0.0F);
    this.w.lineTo(0.0F, 0.0F);
    this.w.fill();
  }
  
  private static void a(PdfTemplate paramPdfTemplate, Color paramColor) {
    paramPdfTemplate.setLineWidth(0.5F);
    paramPdfTemplate.setLineCap(1);
    paramPdfTemplate.setLineJoin(1);
    paramPdfTemplate.setColorStroke(Color.DARK_GRAY);
    paramPdfTemplate.setColorFill(paramColor);
    paramPdfTemplate.moveTo(3.0F, 1.0F);
    paramPdfTemplate.lineTo(3.0F, 11.0F);
    paramPdfTemplate.stroke();
    paramPdfTemplate.moveTo(4.0F, 11.0F);
    paramPdfTemplate.lineTo(10.0F, 8.0F);
    paramPdfTemplate.lineTo(4.0F, 5.0F);
    paramPdfTemplate.fill();
  }
  
  private void e() {
    c(this.j);
    e(this.l);
    c(this.n);
    c(this.k);
    e(this.m);
    a(this.m);
    c(this.o);
    a(this.o);
  }
  
  private void f() {
    this.l.moveTo(this.c, this.c);
    this.l.lineTo(this.b, this.c);
    this.l.stroke();
    this.l.circle(this.e, this.c, this.f);
    this.l.stroke();
    this.l.moveTo(this.c, this.e);
    this.l.lineTo(this.c, this.g);
    this.l.stroke();
    this.m.moveTo(this.c, this.c);
    this.m.lineTo(this.b, this.c);
    this.m.stroke();
    this.m.circle(this.e, this.c, this.f);
    this.m.stroke();
    this.m.moveTo(this.b, this.e);
    this.m.lineTo(this.c, this.c);
    this.m.lineTo(this.b, this.g);
    this.m.stroke();
    c(this.n);
    this.n.moveTo(0.0F, this.c);
    this.n.lineTo(this.b, this.c);
    this.n.stroke();
    this.n.moveTo(this.c, this.e);
    this.n.lineTo(this.c, this.g);
    this.n.stroke();
    this.o.moveTo(0.0F, this.c);
    this.o.lineTo(this.b, this.c);
    this.o.stroke();
    a(this.o);
    this.o.moveTo(this.c, this.e);
    this.o.lineTo(this.c, this.g);
    this.o.stroke();
  }
  
  private void a(PdfTemplate paramPdfTemplate) {
    paramPdfTemplate.moveTo(this.b, this.e);
    paramPdfTemplate.lineTo(this.c, this.c);
    paramPdfTemplate.lineTo(this.b, this.g);
    paramPdfTemplate.stroke();
  }
  
  private void b(PdfTemplate paramPdfTemplate) {
    paramPdfTemplate.moveTo(this.b, this.c);
    paramPdfTemplate.lineTo(this.c, this.e);
    paramPdfTemplate.lineTo(this.c, this.g);
    paramPdfTemplate.fillStroke();
  }
  
  private void c(PdfTemplate paramPdfTemplate) {
    paramPdfTemplate.moveTo(0.0F, this.c);
    paramPdfTemplate.lineTo(this.b, this.c);
    paramPdfTemplate.stroke();
  }
  
  private void d(PdfTemplate paramPdfTemplate) {
    paramPdfTemplate.moveTo(this.c, 0.0F);
    paramPdfTemplate.lineTo(this.c, this.b);
    paramPdfTemplate.stroke();
  }
  
  private void e(PdfTemplate paramPdfTemplate) {
    paramPdfTemplate.moveTo(this.e, this.c);
    paramPdfTemplate.lineTo(this.b, this.c);
    paramPdfTemplate.stroke();
  }
  
  public float a(String paramString) {
    return this.E.getEffectiveStringWidth(paramString, false);
  }
  
  public static Color c(Color paramColor) {
    return new Color((float)paramColor.getRed(), (float)paramColor.getGreen(), (float)paramColor.getBlue(), 1.0F);
  }
}
