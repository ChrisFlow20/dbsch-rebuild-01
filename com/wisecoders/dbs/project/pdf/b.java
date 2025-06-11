package com.wisecoders.dbs.project.pdf;

import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.diagram.fx.FxAbstractDiagramPane;
import com.wisecoders.dbs.diagram.fx.ToolTipFactory;
import com.wisecoders.dbs.diagram.fx.notation.Notation;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Callout;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Group;
import com.wisecoders.dbs.diagram.model.Line;
import com.wisecoders.dbs.diagram.model.LineTextType;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.diagram.model.RelationPosition;
import com.wisecoders.dbs.diagram.model.Shape;
import com.wisecoders.dbs.diagram.model.ShapeStyle;
import com.wisecoders.dbs.schema.DataTypeFormat;
import com.wisecoders.dbs.schema.Expose;
import com.wisecoders.dbs.schema.Layout;
import com.wisecoders.dbs.sys.ColorUtil;
import com.wisecoders.dbs.sys.Features;
import com.wisecoders.dbs.sys.License;
import com.wisecoders.dbs.sys.StringUtil;
import java.awt.Color;
import java.text.SimpleDateFormat;
import javafx.scene.paint.Color;

class b extends PdfCanvas {
  private static final Color F = new Color(7895157);
  
  private static final Color G = new Color(7750790);
  
  private static final Color H = new Color(2698029);
  
  public static final Color D = new Color(13158069);
  
  public static final float E = 40.0F;
  
  b(PdfWriter paramPdfWriter, PdfBaseFonts paramPdfBaseFonts, Expose paramExpose, Layout paramLayout) {
    super(paramPdfWriter, paramExpose.getNotation(), paramPdfBaseFonts, (float)paramLayout.diagram.getHeight() + 40.0F);
    float f = 2.0F * this.b;
    a(H);
    a(f, f, paramLayout.getName());
    String str = (new SimpleDateFormat("d-MM-yyyy")).format(Long.valueOf(System.currentTimeMillis())) + " " + (new SimpleDateFormat("d-MM-yyyy")).format(Long.valueOf(System.currentTimeMillis()));
    a(((float)paramLayout.diagram.getWidth() - a(str) - f), f, str);
    b(((float)paramLayout.diagram.getWidth() - a(str) - f), (f - 12.0F), (a(str) - f), 12.0D, "https://dbschema.com");
    b(H);
    a(f, (f + 3.0F));
    b(((float)paramLayout.diagram.getWidth() - f), (f + 3.0F));
    c();
    if (License.a().g() != Features.a) {
      a(32.0F);
      for (char c = ''; c < paramLayout.diagram.getWidth(); c += 'ɘ') {
        for (char c1 = 'ú'; c1 < paramLayout.diagram.getHeight(); c1 += 'Ɛ') {
          a(new Color(15133163));
          c((c + c1 % 300) + paramLayout.diagram.getWidth() / 2.0D, (c1 - 300));
          a(45.0D);
          a(0.0D, 0.0D, "DbSchema Trial");
          a(-45.0D);
          c((-c - c1 % 300) - paramLayout.diagram.getWidth() / 2.0D, (-c1 + 300));
        } 
      } 
      a(paramExpose.getInt("fontSize", 13));
    } 
    c(0.0D, 40.0D);
    for (Group group : paramLayout.diagram.groups) {
      a(ColorUtil.a(Color.web("#fbfaf6"), group.getColor()));
      d(group.getPosition().a(), group.getPosition().b(), group.getPosition().c(), group.getPosition().d());
      a();
      Color color = c(ColorUtil.a(Color.web("#a9a98c"), group.getColor()));
      b(color);
      d(group.getPosition().a(), group.getPosition().b(), group.getPosition().c(), group.getPosition().d());
      c();
      a(c(ColorUtil.a(Color.web("#9ec5c7"), group.getColor())));
      a(group.getPosition().a() + this.g, group.getPosition().b() + this.b, group.getName());
    } 
    for (Depict depict : paramLayout.diagram.depicts) {
      b(F);
      a(F);
      for (Relation relation : depict.getEntity().getRelations()) {
        if (paramLayout.diagram.relationIsDrawn(relation)) {
          boolean bool = (!relation.hasFlag(1) && paramExpose.getNotation().a()) ? true : false;
          boolean bool1 = relation.isVirtual();
          b(1.25D);
          a(bool);
          a(bool1 ? G : F);
          b(bool1 ? G : F);
          paramLayout.diagram.vectorizeLine(relation, this.C, false);
          c();
          a(false);
          RelationPosition relationPosition = (RelationPosition)paramLayout.diagram.relationPositions.get(relation);
          PdfTemplate pdfTemplate = null;
          if (paramExpose.getNotation() instanceof com.wisecoders.dbs.diagram.fx.notation.IEWithArrowsNotation) {
            pdfTemplate = this.j;
          } else {
            switch (b$1.a[paramLayout.diagram.getTargetTerminatorCardinality(paramExpose.getNotation(), relation).ordinal()]) {
              default:
                throw new IncompatibleClassChangeError();
              case 1:
              case 2:
              
              case 3:
              
              case 4:
              
              case 5:
              
              case 6:
                break;
            } 
            pdfTemplate = this.m;
          } 
          if (pdfTemplate != null)
            a(pdfTemplate, relationPosition.d, relationPosition.e, Notation.a(relationPosition.f)); 
          pdfTemplate = null;
          switch (b$1.a[paramLayout.diagram.getRelationCardinality(relation, false).ordinal()]) {
            case 6:
            
            case 5:
            
            case 3:
            
            case 4:
            
            case 2:
            
            default:
              break;
          } 
          pdfTemplate = pdfTemplate;
          if (pdfTemplate != null)
            a(pdfTemplate, relationPosition.a, relationPosition.b, relationPosition.c); 
          if (paramLayout.diagram.getLineTextType() != LineTextType.d)
            a(relationPosition); 
        } 
      } 
    } 
    for (Shape shape : paramLayout.diagram.shapes) {
      for (Line line : shape.c) {
        b(ColorUtil.a(Color.web("#f7e5b8"), shape.c()));
        b(10.0D);
        paramLayout.diagram.vectorizeLine(line, this.C, true);
        c();
        a(false);
        RelationPosition relationPosition = (RelationPosition)paramLayout.diagram.relationPositions.get(line);
        if (paramLayout.diagram.getLineTextType() != LineTextType.d)
          a(relationPosition); 
      } 
    } 
    for (Depict depict : paramLayout.diagram.depicts) {
      a(Color.WHITE);
      a(depict.getPosition().a(), depict.getPosition().b(), depict
          .getPosition().c(), depict.getPosition().d(), this.i);
      a();
      b(1.0D);
      b(ColorUtil.a(Color.web("#adadad"), depict.getColor()));
      a(depict.getPosition().a(), depict.getPosition().b(), depict
          .getPosition().c(), depict.getPosition().d(), this.i);
      c();
      a(c(ColorUtil.a(Color.web("#f7f1ea"), depict.getColor())));
      a(depict.getPosition().a(), depict.getPosition().b(), 
          (float)depict.getPosition().c(), (this.b + this.c), this.i);
      a();
      b(1.5D);
      b(c(ColorUtil.a(Color.web("#ab7e41"), depict.getColor())));
      a(depict.getPosition().a(), depict.getPosition().b());
      b(depict.getPosition().a() + depict.getPosition().c(), depict.getPosition().b());
      c();
      d(depict.getPosition().a(), depict.getPosition().b() + this.c, 
          (float)depict.getPosition().c(), this.i);
      a();
      a(a.a);
      int i = depict.getNameWidth(paramLayout.diagram.isShowSchemaName());
      float f1 = (int)(depict.getPosition().a() + (depict.getPosition().c() - i) / 2.0D - this.e);
      double d = depict.getPosition().b() + this.b;
      a(f1, d, paramLayout.diagram.isShowSchemaName() ? depict.entity.getNameWithSchemaName() : depict.getEntity().getName());
      if (Sys.B.commentsAsMouseOver.b() && paramExpose.is("imageTooltips"))
        a(f1, d + f - this.f, i, (this.b + this.f), ToolTipFactory.c(depict.getEntity())); 
      b(f1, d + f, i, this.b, "#" + depict.getEntity().getNameWithSchemaName());
      byte b1 = 0;
      for (Attribute attribute : depict.getVisibleAttributes()) {
        double d1 = depict.getPosition().a() + this.b + this.f;
        double d2 = depict.getPosition().b() + (this.b * (b1 + 2)) + this.c - 1.0D;
        a(a.a);
        PdfTemplate pdfTemplate = null;
        if (attribute.hasMarker(1)) {
          pdfTemplate = this.p;
        } else if (attribute.hasMarker(4)) {
          pdfTemplate = this.q;
        } else if (attribute.hasMarker(8)) {
          pdfTemplate = this.r;
        } else if (attribute.hasMarker(65536)) {
          pdfTemplate = this.s;
        } else if (attribute.hasMarker(131072)) {
          pdfTemplate = this.t;
        } 
        if (pdfTemplate != null) {
          b(0.5D);
          a(pdfTemplate, depict.getPosition().a() + this.f, d2 + this.f);
        } 
        if (attribute.isMandatory()) {
          b(0.5D);
          a(this.u, depict.getPosition().a(), d2 + this.f);
        } 
        b(1.0D);
        a(a.a);
        a(d1, d2, attribute.getName());
        if (Sys.B.commentsAsMouseOver.b() && paramExpose.is("imageTooltips"))
          a(d1, d2 + f - this.f, a(attribute.getName()), (this.b + this.f), ToolTipFactory.a(attribute)); 
        a(c(ColorUtil.a(Color.web("#566676"), depict.getColor())));
        if (paramLayout.diagram.isShowDataType())
          a(depict.getPosition().a() + depict.getPosition().c() - this.c, d2, StringUtil.cutOfWithDots(attribute.getTypeString(DataTypeFormat.b), 24), 0.0F); 
        switch (attribute.getToDoFlag()) {
          case 0:
            a(this.z, depict.getPosition().a() + a(attribute.getName()) + this.b + this.f, d2 + this.f);
            break;
          case 1:
            a(this.A, depict.getPosition().a() + a(attribute.getName()) + this.b + this.f, d2 + this.f);
            break;
          case 2:
            a(this.B, depict.getPosition().a() + a(attribute.getName()) + this.b + this.f, d2 + this.f);
            break;
        } 
        if (attribute.hasMarker(32)) {
          a(this.y, depict.getPosition().a() + depict.getPosition().c() - this.c - this.f, d2 + this.f);
        } else if (attribute.hasMarker(64)) {
          a(this.x, depict.getPosition().a() + depict.getPosition().c() - this.c - this.f, d2 + this.f);
        } else if (!paramLayout.diagram.isShowDataType()) {
          String str1 = Attribute.a(attribute);
          if (str1 != null)
            a(depict.getPosition().a() + depict.getPosition().c() - this.c, d2, str1); 
        } 
        b1++;
      } 
    } 
    for (Callout callout : paramLayout.diagram.callouts) {
      a(callout.getPosition().b() - this.c + 40.0D, callout.getPosition().d() + this.b, Color.WHITE, new Color(16053474));
      b(D);
      switch (b$1.b[callout.a().ordinal()]) {
        case 1:
          a(this.w, callout.getPosition().a() + callout.getPosition().c() - this.b - this.c, callout.getPosition().b() - this.c);
          break;
        case 2:
          a(this.w, callout.getPosition().a() + this.b, callout.getPosition().b() - this.c);
          break;
        case 3:
          a(this.v, callout.getPosition().a() + callout.getPosition().c() - this.b - this.c, callout.getPosition().b() + callout.getPosition().d() + this.b + this.c);
          break;
        case 4:
          a(this.v, callout.getPosition().a() + this.b, callout.getPosition().b() + callout.getPosition().d() + this.b + this.c);
          break;
      } 
      a(callout.getPosition().a() - this.c, callout.getPosition().b() - this.c, callout.getPosition().c() + this.b, callout.getPosition().d() + this.b, this.b);
      b();
      byte b1 = 0;
      a(a.a);
      a(paramExpose.getInt("calloutFontSize", 13));
      for (String str1 : callout.d()) {
        a(callout.getPosition().a() + this.e, callout.getPosition().b() + this.c + (float)((b1 * paramExpose.getInt("calloutFontSize", 13)) * Callout.d), (str1.trim().length() == 0) ? "" : str1);
        b1++;
      } 
    } 
    for (Shape shape : paramLayout.diagram.shapes) {
      a(shape.a.getPosition().b() - this.c + 40.0D, shape.a.getPosition().d() + this.b, Color.WHITE, new Color(16776372));
      b(ColorUtil.a(Color.web("#6a8dac"), shape.c()));
      a(ColorUtil.a(Color.web("#a4ccef"), shape.c()));
      switch (b$1.c[shape.b().ordinal()]) {
        case 1:
          a(shape.a.getPosition().a() - this.c, shape.a.getPosition().b() - this.c, shape.a.getPosition().c() + this.b, shape.a.getPosition().d() + this.b, this.b);
          break;
        case 2:
          b(shape.a.getPosition().a() - this.c, shape.a.getPosition().b() - this.c, shape.a.getPosition().c() + this.b, shape.a.getPosition().d() + this.b);
          break;
        case 3:
          c(shape.a.getPosition().a() - this.c, shape.a.getPosition().b() - this.c, shape.a.getPosition().c() + this.b, shape.a.getPosition().d() + this.b);
          break;
      } 
      b();
      byte b1 = 0;
      a(a.a);
      a(paramExpose.getInt("calloutFontSize", 14));
      double d = paramExpose.getInt("calloutFontSize", 14) * Callout.d;
      int i = (int)((shape.a.getPosition().d() - ((shape.a()).length * this.b)) / 2.0D);
      for (String str1 : shape.a()) {
        String str2 = (str1.trim().length() == 0) ? "" : str1;
        int j = FxAbstractDiagramPane.a(str1);
        if (shape.b() == ShapeStyle.a) {
          a(shape.a.getPosition().a() + this.e, shape.a.getPosition().b() + this.c + (float)((b1 * paramExpose.getInt("calloutFontSize", 14)) * Callout.d), str2);
        } else {
          a(shape.a.getPosition().a() + (shape.a.getPosition().c() - j) / 2.0D + this.e, shape.a.getPosition().b() + this.c + i + (float)(b1 * d), str2);
        } 
        b1++;
      } 
    } 
    a(paramExpose.getInt("fontSize", 13));
  }
  
  private void a(RelationPosition paramRelationPosition) {
    switch (paramRelationPosition.c) {
      case 0:
        a((paramRelationPosition.a * this.b + this.c), (paramRelationPosition.b * this.b + this.c), paramRelationPosition.h, 270.0F);
        break;
      case 1:
        a((paramRelationPosition.a * this.b + this.c), (paramRelationPosition.b * this.b), paramRelationPosition.h);
        break;
      case 2:
        a((paramRelationPosition.a * this.b), (paramRelationPosition.b * this.b + this.c), paramRelationPosition.h, 90.0F);
        break;
      case 3:
        a((paramRelationPosition.a * this.b + this.c), (paramRelationPosition.b * this.b), paramRelationPosition.h, 0.0F);
        break;
    } 
  }
}
