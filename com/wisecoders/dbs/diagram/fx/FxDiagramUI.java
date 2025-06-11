package com.wisecoders.dbs.diagram.fx;

import com.wisecoders.dbs.diagram.fx.notation.LineNotation;
import com.wisecoders.dbs.diagram.fx.notation.Notation;
import com.wisecoders.dbs.diagram.fx.print.FxPrintPreviewDialog;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Callout;
import com.wisecoders.dbs.diagram.model.CalloutPointer;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Depictable;
import com.wisecoders.dbs.diagram.model.Diagram;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Group;
import com.wisecoders.dbs.diagram.model.Line;
import com.wisecoders.dbs.diagram.model.LineTextType;
import com.wisecoders.dbs.diagram.model.PainterColumnPart;
import com.wisecoders.dbs.diagram.model.PainterStatus;
import com.wisecoders.dbs.diagram.model.Rect;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.diagram.model.RelationPosition;
import com.wisecoders.dbs.diagram.model.Shape;
import com.wisecoders.dbs.diagram.model.ShapeStyle;
import com.wisecoders.dbs.schema.AttributeSpec;
import com.wisecoders.dbs.schema.DataTypeFormat;
import com.wisecoders.dbs.sys.ColorUtil;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.FxUtil;
import java.util.Collection;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontSmoothingType;

public class FxDiagramUI {
  public static final int a = 0;
  
  public static final int b = 1;
  
  public static final int c = 2;
  
  private static final Image f = Rx.c("marker_pk.png");
  
  private static final Image g = Rx.c("marker_unq.png");
  
  private static final Image h = Rx.c("marker_idx.png");
  
  private static final Image i = Rx.c("marker_idx1_key.png");
  
  private static final Image j = Rx.c("marker_idx2_key.png");
  
  private static final Image k = Rx.c("marker_idx3_key.png");
  
  private static final Image l = Rx.c("marker_sort_asc.png");
  
  private static final Image m = Rx.c("marker_sort_desc.png");
  
  private static final Image[] n = new Image[] { Rx.c("todo0.png"), 
      Rx.c("todo1.png"), 
      Rx.c("todo2.png") };
  
  private static final Image o = Rx.c("marker_fk@2x.png");
  
  private static final Image p = Rx.c("marker_ref@2x.png");
  
  private static final Image q = Rx.c("marker_unlinked.png");
  
  private static final Image r = Rx.c("marker_mandatory.png");
  
  private static final Image s = Rx.c("marker_view.png");
  
  private static final Image t = Rx.c("marker_virtual.png");
  
  private static final Image u = Rx.c("marker_validation_rule.png");
  
  private static final Image v = Rx.c("marker_leaf.png");
  
  private static final Image w = Rx.c("marker_leaf_array.png");
  
  private static final Image x = Rx.c("marker_ticked.png");
  
  private static final Image y = Rx.c("marker_unticked.png");
  
  private static final double z = 1.5D;
  
  static final double d = 0.4D;
  
  private static final LineNotation A = new LineNotation();
  
  private boolean B = false;
  
  private boolean C = false;
  
  private final FxAbstractDiagramPane D;
  
  private final Diagram E;
  
  FxDiagramUI(FxAbstractDiagramPane paramFxAbstractDiagramPane) {
    this.D = paramFxAbstractDiagramPane;
    this.E = paramFxAbstractDiagramPane.a;
  }
  
  public void a(GraphicsContext paramGraphicsContext, boolean paramBoolean, PaintMode paramPaintMode, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5) {
    if (!FxUtil.a())
      Thread.dumpStack(); 
    boolean bool1 = (paramPaintMode == PaintMode.a && (!paramBoolean || this.D.n() > 0.25D)) ? true : false;
    boolean bool2 = (paramPaintMode == PaintMode.a && (!paramBoolean || this.D.n() > 0.1D)) ? true : false;
    int i = (int)((Font)this.D.o.getValue()).getSize();
    paramGraphicsContext.setFont((Font)this.D.o.getValue());
    paramGraphicsContext.setFontSmoothingType(FontSmoothingType.LCD);
    paramGraphicsContext.setImageSmoothing(false);
    if (paramBoolean)
      paramGraphicsContext.clearRect(0.0D, 0.0D, paramDouble4, paramDouble5); 
    double d = 1.0D / paramDouble1;
    paramGraphicsContext.translate(-paramDouble2, -paramDouble3);
    paramGraphicsContext.scale(paramDouble1, paramDouble1);
    Rect rect = new Rect((int)(paramDouble2 * d), (int)(paramDouble3 * d), (int)(paramDouble4 * d), (int)(paramDouble5 * d));
    long l = System.currentTimeMillis();
    byte b = 0;
    for (Group group : this.E.groups) {
      boolean bool = this.D.c.a.contains(group);
      Rect rect1 = group.getPosition();
      if (rect1.b(rect)) {
        double d1 = Math.max(rect1.a() + 1.0D, rect.a() - Diagram.cell);
        double d2 = Math.max(rect1.b() + 1.0D, rect.b() - Diagram.cell);
        double d3 = Math.min(rect1.a() + rect1.c(), rect.a() + rect.c() + Diagram.cell) - d1;
        double d4 = Math.min(rect1.b() + rect1.d(), rect.b() + rect.d() + Diagram.cell) - d2;
        paramGraphicsContext.setGlobalAlpha(bool ? ((Number)this.D.ad.getValue()).doubleValue() : (
            paramBoolean ? ((Number)this.D.ac.getValue()).doubleValue() : (
            (Number)this.D.ab.getValue()).doubleValue()));
        paramGraphicsContext.setLineWidth(3.0D);
        paramGraphicsContext.setStroke((Paint)ColorUtil.a(bool ? (Color)this.D.aa.getValue() : (Color)this.D.Z.getValue(), group.getColor()));
        paramGraphicsContext.strokeRect(d1, d2, d3, d4);
        Color color = ColorUtil.a((Color)this.D.Y.getValue(), group.getColor());
        paramGraphicsContext.setFill((Paint)color);
        paramGraphicsContext.fillRect(d1, d2, d3, d4);
        paramGraphicsContext.setGlobalAlpha(1.0D);
        paramGraphicsContext.setFill((Paint)ColorUtil.a((Color)this.D.X.getValue(), group.getColor()));
        paramGraphicsContext.fillText(group.getName(), rect1.a() + i, a(rect1.b() + (int)(1.5D * i)));
        b++;
      } 
    } 
    if (!this.C && b > 0 && !this.B) {
      if ((System.currentTimeMillis() - l) / b > 200L)
        this.B = true; 
      this.C = true;
    } 
    if (this.E.getStatus().a() && this.D.b() && bool1) {
      Collection collection = (Collection)((this.E.getLineTextType() != LineTextType.d) ? this.E.relationPositions.keySet() : this.D.c.d);
      for (Relation relation : collection)
        a(paramGraphicsContext, rect, relation); 
    } 
    for (Group group : this.E.groups) {
      if (group.getPosition().b(rect)) {
        for (Depict depict : group.getDepicts()) {
          if (rect.b(depict.getPosition())) {
            if (bool1) {
              a(paramGraphicsContext, depict, depict.getPosition(), rect, paramBoolean, false);
              continue;
            } 
            a(paramGraphicsContext, depict.getPosition(), paramPaintMode);
          } 
        } 
        b++;
      } 
    } 
    for (Depict depict : this.E.depicts) {
      if (depict.isGroupFree() && rect.b(depict.getPosition())) {
        if (bool1) {
          a(paramGraphicsContext, depict, depict.getPosition(), rect, paramBoolean, false);
          continue;
        } 
        a(paramGraphicsContext, depict.getPosition(), paramPaintMode);
      } 
    } 
    for (Shape shape : this.E.shapes) {
      if (rect.b(shape.a.getPosition()))
        a(paramGraphicsContext, shape, shape.a.getPosition(), bool1); 
      for (Line line : shape.c)
        a(paramGraphicsContext, rect, line); 
    } 
    if (this.E.getStatus().a() && bool2)
      try {
        double d1 = Math.max(0.0D, rect.a() / Diagram.cell - 1.0D);
        double d2 = Math.max(0.0D, rect.b() / Diagram.cell - 1.0D);
        double d3 = Math.min(this.E.dim_X, (rect.a() + rect.c()) / Diagram.cell + 1.0D);
        double d4 = Math.min(this.E.dim_Y, (rect.b() + rect.d()) / Diagram.cell + 1.0D);
        paramGraphicsContext.setLineWidth(1.45D);
        paramGraphicsContext.setLineCap(StrokeLineCap.ROUND);
        Notation notation = this.D.C();
        notation.a(Diagram.cell);
        for (int j = (int)d2; j < d4; j++) {
          for (int k = (int)d1; k < d3; k++) {
            if (!this.E.getStatus().a())
              // Byte code: goto -> 1798 
            long l1 = this.E.links[k][j];
            long l2 = l1 & 0xF00000L;
            if (l2 != 0L) {
              int m = k * Diagram.cell, n = j * Diagram.cell;
              paramGraphicsContext.translate(m, a(n));
              a(paramGraphicsContext, l1, notation);
              int i1 = (int)(l1 >> 12L & 0x3L);
              int i2 = (int)(l1 >> 10L & 0x3L);
              if (l2 == 1048576L) {
                notation.a(paramGraphicsContext, i2, i1);
              } else if (l2 == 3145728L) {
                notation.a(paramGraphicsContext, i2, i1);
                for (byte b1 = 0; b1 < 4; b1++) {
                  if (b1 != i2 && b1 != Diagram.REVERSE_DIR[i1]) {
                    long l3 = this.E.getNeighbourPixel(k, j, b1);
                    if (l3 != 0L) {
                      a(paramGraphicsContext, l3, notation);
                      notation.a(paramGraphicsContext, b1, Diagram.REVERSE_DIR[i2]);
                    } 
                  } 
                } 
              } else if (l2 == 2097152L) {
                long l3 = this.E.getNeighbourPixel(k, j, 0);
                if (l3 == 0L)
                  l3 = this.E.getNeighbourPixel(k, j, 2); 
                a(paramGraphicsContext, l3, notation);
                notation.a_(paramGraphicsContext);
                l3 = this.E.getNeighbourPixel(k, j, 1);
                if (l3 == 0L)
                  l3 = this.E.getNeighbourPixel(k, j, 3); 
                a(paramGraphicsContext, l3, notation);
                notation.h(paramGraphicsContext);
              } else {
                Notation notation1 = ((l1 & 0x2000000000L) > 0L) ? A : notation;
                notation1.a(paramGraphicsContext, this.E.getRelationCardinality(l1), (l2 == 8388608L), ((l1 & 0x8000000L) > 0L), ((l1 & 0x10000000L) > 0L), i1);
              } 
              paramGraphicsContext.translate(-m, -a(n));
            } 
          } 
        } 
      } catch (Throwable throwable) {
        Log.h();
      }  
    paramGraphicsContext.setLineDashes(new double[] { 0.0D });
    for (Callout callout : this.E.callouts) {
      if (rect.b(callout.getPosition()))
        a(paramGraphicsContext, callout, callout.getPosition(), bool1); 
    } 
    for (Depictable depictable : this.D.e.keySet()) {
      Rect rect1 = (Rect)this.D.e.get(depictable);
      if (rect.b(rect1)) {
        if (depictable instanceof Depict) {
          Depict depict = (Depict)depictable;
          Entity entity = depict.getEntity();
          if (entity instanceof Shape) {
            Shape shape = (Shape)entity;
            a(paramGraphicsContext, shape, rect1, bool1);
            continue;
          } 
          if (bool1) {
            a(paramGraphicsContext, depict, rect1, rect, paramBoolean, true);
            continue;
          } 
          a(paramGraphicsContext, rect1, paramPaintMode);
          continue;
        } 
        if (depictable instanceof Callout)
          a(paramGraphicsContext, (Callout)depictable, rect1, bool1); 
      } 
    } 
    if (paramBoolean && this.D.f.a() != -2.147483648E9D) {
      paramGraphicsContext.setStroke((Paint)Color.RED);
      paramGraphicsContext.setLineWidth(1.0D);
      paramGraphicsContext.setLineDashes(new double[] { 3.0D });
      paramGraphicsContext.strokeRect(this.D.f.a(), this.D.f.b(), this.D.f.c(), this.D.f.d());
      paramGraphicsContext.setLineDashes(new double[] { 0.0D });
    } 
    paramGraphicsContext.scale(d, d);
    paramGraphicsContext.translate(paramDouble2, paramDouble3);
    if (paramBoolean) {
      if (this.E.isShowPageBorders()) {
        double d1 = FxPrintPreviewDialog.a() * paramDouble1 / 0.6D;
        double d2 = FxPrintPreviewDialog.b() * paramDouble1 / 0.6D - 10.0D;
        if (d1 > 0.0D && d2 > 0.0D) {
          paramGraphicsContext.setStroke((Paint)this.D.s.getValue());
          paramGraphicsContext.setLineDashes(new double[] { 1.0D, 4.0D });
          int j = (int)Math.ceil(rect.a() / d1);
          int k = (int)((rect.a() + rect.c()) / d1);
          int m = (int)Math.ceil(rect.b() / d2);
          int n = (int)((rect.b() + rect.d()) / d2);
          int i1;
          for (i1 = j; i1 <= k; i1++) {
            double d3 = i1 * d1 - rect.a() - 1.0D;
            paramGraphicsContext.strokeLine(d3, 0.0D, d3, rect.d() * paramDouble1);
          } 
          for (i1 = m; i1 <= n; i1++) {
            double d3 = i1 * d2 - rect.b() - 1.0D;
            paramGraphicsContext.strokeLine(0.0D, d3, rect.c() * paramDouble1, d3);
          } 
          paramGraphicsContext.setLineDashes(new double[0]);
        } 
      } 
      if (paramPaintMode == PaintMode.a && this.E.getStatus() == PainterStatus.b) {
        paramGraphicsContext.setFill((Paint)this.D.H.getValue());
        paramGraphicsContext.fillText("Reconnecting lines...", paramDouble4 - 150.0D, a(20.0D));
      } 
    } 
  }
  
  static final float[] e = new float[3];
  
  private void a(GraphicsContext paramGraphicsContext, long paramLong, Notation paramNotation) {
    Color color;
    boolean bool1 = ((paramNotation.a() && (paramLong & 0x800000L) == 0L && (paramLong & 0x10000000L) == 0L) || (paramLong & 0x4000000000L) > 0L) ? true : false;
    boolean bool2 = ((paramLong & 0x2000000000L) > 0L) ? true : false, bool3 = ((paramLong & 0x200000000L) != 0L) ? true : false, bool4 = ((paramLong & 0x100000000L) != 0L) ? true : false;
    if (bool2) {
      color = bool4 ? (Color)this.D.U.getValue() : (bool3 ? (Color)this.D.V.getValue() : Color.hsb((ColorUtil.a((int)((paramLong & 0xFFFFFF0000000000L) >> 40L), e)[0] * 360.0F), ((Color)this.D.ak
          .getValue()).getSaturation(), ((Color)this.D.ak
          .getValue()).getBrightness()));
      paramGraphicsContext.setLineWidth(((Number)this.D.al.getValue()).doubleValue());
      paramGraphicsContext.setLineDashes(new double[] { 0.0D });
    } else if (bool3) {
      paramGraphicsContext.setLineWidth(2.7D);
      color = (Color)this.D.V.getValue();
      if (bool1) {
        paramGraphicsContext.setLineDashes(new double[] { 3.0D, 5.0D });
      } else {
        paramGraphicsContext.setLineDashes(new double[] { 1.0D });
      } 
    } else if (bool4) {
      paramGraphicsContext.setLineWidth(2.5D);
      color = (Color)this.D.U.getValue();
      if (bool1) {
        paramGraphicsContext.setLineDashes(new double[] { 3.0D, 5.0D });
      } else {
        paramGraphicsContext.setLineDashes(new double[] { 1.0D });
      } 
    } else if ((paramLong & 0x4000000000L) > 0L) {
      paramGraphicsContext.setLineWidth(2.0D);
      color = (Color)this.D.S.getValue();
      if (bool1) {
        paramGraphicsContext.setLineDashes(new double[] { 3.0D, 5.0D });
      } else {
        paramGraphicsContext.setLineDashes(new double[] { 1.0D });
      } 
    } else if ((paramLong & 0x1000000000L) > 0L) {
      paramGraphicsContext.setLineWidth(2.0D);
      color = (Color)this.D.T.getValue();
      if (bool1) {
        paramGraphicsContext.setLineDashes(new double[] { 3.0D, 5.0D });
      } else {
        paramGraphicsContext.setLineDashes(new double[] { 1.0D });
      } 
    } else {
      paramGraphicsContext.setLineWidth(((Number)this.D.W.getValue()).doubleValue());
      color = (Color)this.D.Q.getValue();
      if (bool1) {
        paramGraphicsContext.setLineDashes(new double[] { 3.0D, 5.0D });
      } else {
        paramGraphicsContext.setLineDashes(new double[] { 0.0D });
      } 
    } 
    paramGraphicsContext.setFill((Paint)color);
    paramGraphicsContext.setStroke((Paint)color);
  }
  
  private void a(GraphicsContext paramGraphicsContext, Depict paramDepict, Rect paramRect1, Rect paramRect2, boolean paramBoolean1, boolean paramBoolean2) {
    int i = (int)((Font)this.D.o.getValue()).getSize(), j = (int)(i * 1.2D);
    int k = Diagram.cell / 2, m = Diagram.cell / 3, n = (int)(Diagram.cell * 0.375D), i1 = Diagram.cell / 4, i2 = Diagram.cell / 5, i3 = Diagram.cell / 7, i4 = (int)(0.55D * Diagram.cell);
    int i5 = (int)(Diagram.cell * 1.5D);
    int i6 = (int)(1.35D * Diagram.cell);
    boolean bool1 = this.D.d.a.contains(paramDepict.getEntity());
    boolean bool2 = this.D.c.a.contains(paramDepict.getEntity());
    boolean bool3 = this.D.s();
    int i7 = paramDepict.getNameWidth(this.D.a.isShowSchemaName());
    int i8 = (int)((paramRect1.c() - i7) / 2.0D), i9 = i;
    byte b = ((Boolean)this.D.C.getValue()).booleanValue() ? k : 0;
    double d1 = paramRect2.b() - paramRect1.b() - b, d2 = paramRect2.b() + paramRect2.d() - paramRect1.b() + b;
    double d3 = Math.max(Diagram.headerY, d1), d4 = Math.min(paramRect1.d(), d2) - Math.max(Diagram.headerY, d1);
    paramGraphicsContext.translate(paramRect1.a(), paramRect1.b());
    Color color1 = ColorUtil.a((Color)this.D.D.getValue(), paramDepict.getColor());
    if (((Number)this.D.E.getValue()).doubleValue() > 0.0D) {
      paramGraphicsContext.setStroke((Paint)color1);
      paramGraphicsContext.setLineWidth(((Number)this.D.E.getValue()).doubleValue());
      if (!paramBoolean2)
        paramGraphicsContext.setEffect((bool1 || bool2) ? (Effect)this.D.G.getValue() : (Effect)this.D.F.getValue()); 
      paramGraphicsContext.strokeRoundRect(0.0D, d3, paramRect1.c(), d4, b, b);
      paramGraphicsContext.setEffect(null);
      paramGraphicsContext.setLineWidth(1.0D);
    } 
    boolean bool4 = paramRect2.d(paramRect1.b() + Diagram.headerY, i5);
    Color color2 = (Color)this.D.z.getValue();
    if (paramBoolean2)
      paramGraphicsContext.setGlobalAlpha(0.6D); 
    int i10 = ((Number)this.D.t.getValue()).intValue();
    if (paramDepict.getEntity().getEntity() instanceof com.wisecoders.dbs.schema.UserDataType)
      i10 = 1; 
    switch (i10) {
      case 1:
        if (bool2) {
          paramGraphicsContext.setFill((Paint)ColorUtil.a((Color)this.D.A.getValue(), paramDepict.getColor()));
        } else {
          paramGraphicsContext.setFill((Paint)new LinearGradient(0.0D, 0.0D, 1.0D, 0.0D, true, CycleMethod.REPEAT, new Stop[] { new Stop(0.0D, 
                    
                    ColorUtil.a((Color)this.D.u.getValue(), paramDepict.getColor())), new Stop(0.35D, 
                    ColorUtil.a((Color)this.D.v.getValue(), paramDepict.getColor())), new Stop(1.0D, 
                    ColorUtil.a((Color)this.D.w.getValue(), paramDepict.getColor())) }));
        } 
        paramGraphicsContext.fillRoundRect(0.0D, Diagram.headerY, paramRect1.c(), paramRect1.d() - Diagram.headerY, b, b);
        break;
      case 2:
        paramGraphicsContext.setFill((Paint)color2);
        paramGraphicsContext.fillRoundRect(0.0D, d3, paramRect1.c(), d4, b, b);
        if (bool2) {
          paramGraphicsContext.setFill((Paint)ColorUtil.a(bool2 ? (Color)this.D.A.getValue() : (Color)this.D.v.getValue(), paramDepict.getColor()));
        } else {
          paramGraphicsContext.setFill((Paint)new LinearGradient(0.0D, 0.0D, 1.0D, 0.0D, true, CycleMethod.REPEAT, new Stop[] { new Stop(0.0D, 
                    
                    ColorUtil.a((Color)this.D.v.getValue(), paramDepict.getColor())), new Stop(1.0D, 
                    ColorUtil.a((Color)this.D.w.getValue(), paramDepict.getColor())) }));
        } 
        paramGraphicsContext.fillRoundRect(0.0D, Diagram.headerY, paramRect1.c(), i5, b, b);
        paramGraphicsContext.setFill(null);
        if (!bool2) {
          paramGraphicsContext.setLineWidth(2.0D);
          paramGraphicsContext.setStroke((Paint)new LinearGradient(0.0D, 0.0D, 1.0D, 0.0D, true, CycleMethod.REPEAT, new Stop[] { new Stop(0.0D, 
                    
                    ColorUtil.a((Color)this.D.u.getValue(), paramDepict.getColor())), new Stop(1.0D, 
                    ColorUtil.a((Color)this.D.v.getValue(), paramDepict.getColor())) }));
          paramGraphicsContext.strokeLine(0.0D, d3, paramRect1.c(), d3);
          paramGraphicsContext.setLineWidth(1.0D);
        } 
        break;
      default:
        paramGraphicsContext.setFill((Paint)color2);
        paramGraphicsContext.fillRoundRect(0.0D, d3, paramRect1.c(), d4, b, b);
        if (bool2) {
          paramGraphicsContext.setFill((Paint)ColorUtil.a((Color)this.D.A.getValue(), paramDepict.getColor()));
        } else {
          paramGraphicsContext.setFill((Paint)new LinearGradient(0.0D, 0.0D, 0.0D, 1.0D, true, CycleMethod.REPEAT, new Stop[] { new Stop(0.0D, 
                    
                    ColorUtil.a((Color)this.D.u.getValue(), paramDepict.getColor())), new Stop(0.75D, 
                    ColorUtil.a((Color)this.D.v.getValue(), paramDepict.getColor())), new Stop(1.0D, 
                    ColorUtil.a((Color)this.D.w.getValue(), paramDepict.getColor())) }));
        } 
        if (bool4) {
          paramGraphicsContext.fillRect(0.0D, (Diagram.headerY + b), paramRect1.c(), (i5 - b));
          paramGraphicsContext.fillRoundRect(0.0D, Diagram.headerY, paramRect1.c(), i5, b, b);
          paramGraphicsContext.setFill(null);
        } 
        break;
    } 
    paramGraphicsContext.setGlobalAlpha(1.0D);
    if (bool4) {
      if (paramDepict.getEntity().isView()) {
        paramGraphicsContext.drawImage(s, paramRect1.c() - k, -i2, j, j);
      } else if (this.E.json && !paramDepict.getEntity().isChildEntity() && !paramDepict.getEntity().isVirtual()) {
        paramGraphicsContext.drawImage(u, paramRect1.c() - (2 * k), -i2, j, j);
      } else if (paramDepict.getEntity().isChildEntity()) {
        paramGraphicsContext.drawImage(paramDepict.getEntity().isChildEntityArray() ? w : v, paramRect1.c() - k, -i2, j, j);
      } 
      if (paramDepict.getEntity().isVirtual())
        paramGraphicsContext.drawImage(t, -i2, -i2, j, j); 
      Color color = bool2 ? (Color)this.D.y.getValue() : ColorUtil.a((Color)this.D.x.getValue(), paramDepict.getColor());
      String str = this.D.a.isShowSchemaName() ? paramDepict.getEntity().getNameWithSchemaName() : paramDepict.getEntity().getDisplayName(this.E);
      if (!bool2 && bool1)
        paramGraphicsContext.setEffect((Effect)this.D.L.getValue()); 
      paramGraphicsContext.setFill((Paint)color);
      paramGraphicsContext.setFont((Font)this.D.p.getValue());
      paramGraphicsContext.fillText(str, i8, a((i9 + n)));
    } 
    paramGraphicsContext.setFont((Font)this.D.o.getValue());
    paramGraphicsContext.translate(-paramRect1.a(), -paramRect1.b());
    paramGraphicsContext.setEffect(null);
    Color color3 = ColorUtil.a((Color)this.D.J.getValue(), paramDepict.getColor());
    double d5 = i;
    int i11 = -1;
    boolean bool5 = true;
    paramGraphicsContext.setFill((Paint)this.D.H.getValue());
    for (Attribute attribute : paramDepict.getVisibleAttributes()) {
      if (paramRect2.d(paramRect1.b() + (int)d5 + Diagram.cell, Diagram.cell)) {
        paramGraphicsContext.translate(paramRect1.a(), paramRect1.b() + (int)(d5 + Diagram.cell));
        boolean bool = this.D.c.a.contains(attribute);
        boolean bool6 = (paramBoolean1 && this.D.d.a.contains(attribute) && this.D.h == PainterColumnPart.a) ? true : false;
        if (bool) {
          paramGraphicsContext.setFill((Paint)this.D.O.getValue());
          paramGraphicsContext.fillRect(0.0D, i1, paramRect1.c(), Diagram.cell);
        } 
        paramGraphicsContext.setFill((Paint)color2);
        if (this.D.r() && this.D.p() == attribute)
          i11 = (int)(paramRect1.b() + d5 + Diagram.cell + i1 - 1.0D); 
        if (bool3) {
          if (paramBoolean1 && bool5 && !attribute.isSelectable()) {
            paramGraphicsContext.setStroke((Paint)Color.LIGHTGRAY);
            paramGraphicsContext.strokeLine(0.0D, i1, paramRect1.c(), i1);
          } 
          bool5 = attribute.isSelectable();
          if (attribute.isSelectable())
            paramGraphicsContext.drawImage(attribute.isTicked() ? x : y, i1, m, i, i); 
        } else {
          Image image1;
          boolean bool7 = attribute.hasMarker(16);
          if (attribute.hasMarker(1)) {
            image1 = f;
          } else if (attribute.hasMarker(8)) {
            image1 = g;
          } else if (attribute.hasMarker(4)) {
            image1 = h;
          } else if (attribute.hasMarker(65536)) {
            image1 = i;
          } else if (attribute.hasMarker(131072)) {
            image1 = j;
          } else if (attribute.hasMarker(262144)) {
            image1 = k;
          } else {
            image1 = null;
          } 
          if (image1 != null) {
            boolean bool8 = (attribute == this.D.g && this.D.h == PainterColumnPart.c) ? true : false;
            if (bool8)
              paramGraphicsContext.setEffect((Effect)this.D.L.getValue()); 
            paramGraphicsContext.drawImage(image1, i2, m, bool7 ? j : i, bool7 ? j : i);
            if (attribute.hasMarker(1048576)) {
              paramGraphicsContext.drawImage(l, i2, k, j, j);
            } else if (attribute.hasMarker(2097152)) {
              paramGraphicsContext.drawImage(m, i2, k, j, j);
            } 
            paramGraphicsContext.setEffect(null);
          } 
          if (attribute.isMandatory())
            paramGraphicsContext.drawImage(r, i3, k, i1, i1); 
        } 
        Image image = null;
        if (attribute.hasMarker(32)) {
          image = o;
        } else if (attribute.hasMarker(64) || attribute.hasMarker(32768) || attribute.hasMarker(16384)) {
          image = p;
        } 
        if (attribute == this.D.g && this.D.h == PainterColumnPart.d)
          paramGraphicsContext.setEffect((Effect)this.D.L.getValue()); 
        if (image != null) {
          paramGraphicsContext.drawImage(image, paramRect1.c() - i, m, i, i);
          if (this.D.a.hasMarker(attribute, 4))
            paramGraphicsContext.drawImage(q, paramRect1.c() - i, m, i, i); 
        } else if (!this.D.a.isShowDataType()) {
          paramGraphicsContext.setFill(bool ? (Paint)this.D.y.getValue() : (Paint)color3);
          if (attribute.hasMarker(128)) {
            paramGraphicsContext.fillText("#", paramRect1.c() - i4, a(Diagram.cell));
          } else if (attribute.hasMarker(2048)) {
            paramGraphicsContext.fillText("d", paramRect1.c() - i4, a(Diagram.cell));
          } else if (attribute.hasMarker(512)) {
            paramGraphicsContext.fillText("t", paramRect1.c() - i4, a(Diagram.cell));
          } else if (attribute.hasMarker(8192)) {
            paramGraphicsContext.fillText("[]", paramRect1.c() - i4, a(Diagram.cell));
          } else if (attribute.hasMarker(1024)) {
            paramGraphicsContext.fillText("c", paramRect1.c() - i4, a(Diagram.cell));
          } else if (attribute.hasMarker(256)) {
            paramGraphicsContext.fillText("b", paramRect1.c() - i4, a(Diagram.cell));
          } else if (attribute.hasMarker(4096)) {
            paramGraphicsContext.fillText("~", paramRect1.c() - i4, a(Diagram.cell));
          } 
        } 
        paramGraphicsContext.setEffect(null);
        if (attribute.getToDoFlag() != -1) {
          int i12 = FxAbstractDiagramPane.a(attribute.getName());
          paramGraphicsContext.drawImage(n[Math.min(n.length - 1, attribute.getToDoFlag())], (i6 + i12), i2, (i + i1), (i + i1));
        } 
        Color color = bool ? (Color)this.D.N.getValue() : (bool6 ? (Color)this.D.H.getValue() : (attribute.hasMarker(1) ? (Color)this.D.I.getValue() : ((attribute.getSpec() == AttributeSpec.functional) ? (Color)this.D.K.getValue() : (Color)this.D.H.getValue())));
        paramGraphicsContext.setFill((Paint)color);
        paramGraphicsContext.setStroke((Paint)color);
        paramGraphicsContext.setEffect(bool6 ? (Effect)this.D.L.getValue() : null);
        paramGraphicsContext.fillText(attribute.getDisplayName(this.E), i6, a(Diagram.cell));
        paramGraphicsContext.setEffect(null);
        if (this.D.a.isShowDataType()) {
          paramGraphicsContext.setFill(bool ? (Paint)this.D.y.getValue() : (Paint)color3);
          paramGraphicsContext.setFont((Font)this.D.q.getValue());
          String str = StringUtil.cutOfWithDots(attribute.getTypeString(DataTypeFormat.b), 24);
          paramGraphicsContext.fillText(str, paramRect1.c() - FxAbstractDiagramPane.b(str) - i - i3, a(Diagram.cell));
          paramGraphicsContext.setFont((Font)this.D.o.getValue());
        } 
        paramGraphicsContext.translate(-paramRect1.a(), -paramRect1.b() - (int)(d5 + Diagram.cell));
      } 
      d5 += Diagram.cell;
    } 
    paramGraphicsContext.setEffect(null);
    if (paramBoolean1 && i11 > -1) {
      paramGraphicsContext.setLineDashes(new double[] { 2.0D, 2.0D });
      paramGraphicsContext.setFill((Paint)this.D.J.getValue());
      paramGraphicsContext.strokeRect(paramRect1.a(), i11, paramRect1.c() - 1.0D, (Diagram.cell + i1));
      paramGraphicsContext.setLineDashes(new double[] { 0.0D });
    } 
    paramGraphicsContext.translate(paramRect1.a(), paramRect1.b());
    if (paramDepict.hasHiddenAttributes()) {
      paramGraphicsContext.setFill((Paint)this.D.J.getValue());
      paramGraphicsContext.fillText("-- more --", i6, a(paramRect1.d() - Diagram.headerY));
    } 
    paramGraphicsContext.translate(-paramRect1.a(), -paramRect1.b());
    paramGraphicsContext.setGlobalAlpha(1.0D);
  }
  
  private static double a(double paramDouble) {
    return paramDouble + 0.05D;
  }
  
  private void a(GraphicsContext paramGraphicsContext, Rect paramRect, Relation paramRelation) {
    RelationPosition relationPosition = (RelationPosition)this.D.a.relationPositions.get(paramRelation);
    if (relationPosition != null && paramRect.b(relationPosition.e(), relationPosition.f(), relationPosition.c(), relationPosition.d())) {
      paramGraphicsContext.translate(relationPosition.e(), relationPosition.f());
      paramGraphicsContext.rotate(relationPosition.b());
      boolean bool1 = this.D.d(paramRelation);
      boolean bool2 = this.D.c.a.contains(paramRelation);
      boolean bool3 = this.D.c();
      int i = (int)FxAbstractDiagramPane.E().getSize();
      if (bool3) {
        paramGraphicsContext.setStroke((Paint)this.D.ah.getValue());
        paramGraphicsContext.setEffect((Effect)this.D.M.getValue());
        paramGraphicsContext.strokeRoundRect((-i + 2), -i, (relationPosition.c() + i), (i + 3), 2.0D, 2.0D);
        paramGraphicsContext.setEffect(null);
        paramGraphicsContext.setFill(bool1 ? (Paint)this.D.ag.getValue() : (Paint)this.D.af.getValue());
        paramGraphicsContext.fillRoundRect((-i + 2), -i, (relationPosition.c() + i), (i + 3), 2.0D, 2.0D);
        paramGraphicsContext.setFill(bool1 ? (Paint)this.D.y.getValue() : (Paint)this.D.H.getValue());
        paramGraphicsContext.strokeLine(-7.0D, (-i + 6), -5.0D, (-i + 8));
        paramGraphicsContext.strokeLine(-5.0D, (-i + 8), -3.0D, (-i + 6));
      } 
      paramGraphicsContext.setFill(bool3 ? (Paint)this.D.ae.getValue() : (
          bool2 ? (Paint)this.D.V.getValue() : (
          bool1 ? (Paint)this.D.U.getValue() : (
          
          (paramRelation instanceof Line) ? 
          (Paint)ColorUtil.a((Color)this.D.am.getValue(), ((Line)paramRelation).a.c()) : 
          (Paint)this.D.R.getValue()))));
      paramGraphicsContext.fillText(relationPosition.h, 0.0D, a(0.0D));
      paramGraphicsContext.rotate(-relationPosition.b());
      paramGraphicsContext.translate(-relationPosition.e(), -relationPosition.f());
    } 
  }
  
  private void a(GraphicsContext paramGraphicsContext, Rect paramRect, PaintMode paramPaintMode) {
    int i = (int)(Diagram.cell * 1.5D);
    paramGraphicsContext.translate(paramRect.a(), paramRect.b());
    paramGraphicsContext.setFill((paramPaintMode == PaintMode.b) ? (Paint)this.D.B.getValue() : (Paint)this.D.z.getValue());
    paramGraphicsContext.fillRect(0.0D, i, paramRect.c(), paramRect.d() - i);
    paramGraphicsContext.setStroke((Paint)this.D.D.getValue());
    paramGraphicsContext.strokeRect(0.0D, Diagram.headerY, paramRect.c(), paramRect.d() - Diagram.headerY);
    paramGraphicsContext.translate(-paramRect.a(), -paramRect.b());
  }
  
  private void a(GraphicsContext paramGraphicsContext, Callout paramCallout, Rect paramRect, boolean paramBoolean) {
    String[] arrayOfString = paramCallout.d();
    int i = (int)((Font)this.D.r.getValue()).getSize();
    int j = i / 2;
    int k = i / 4;
    if (arrayOfString != null && paramRect != null) {
      paramGraphicsContext.setFont((Font)this.D.r.getValue());
      double d1 = paramRect.a() - i;
      double d2 = paramRect.b() - j;
      double d3 = paramRect.c() + (2 * i);
      double d4 = paramRect.d() + (2 * j);
      boolean bool1 = this.D.c.a.contains(paramCallout);
      boolean bool2 = this.D.d.a.contains(paramCallout);
      paramGraphicsContext.translate(d1, d2);
      paramGraphicsContext.beginPath();
      paramGraphicsContext.moveTo(0.0D, d4 - i);
      paramGraphicsContext.arcTo(0.0D, 0.0D, i, 0.0D, i);
      if (paramCallout.a() == CalloutPointer.c) {
        paramGraphicsContext.arcTo((2 * i), 0.0D, (2 * i), -i, i);
        paramGraphicsContext.lineTo((2 * i + i), 0.0D);
      } else if (paramCallout.a() == CalloutPointer.d) {
        paramGraphicsContext.lineTo(d3 - (2 * i) - i, 0.0D);
        paramGraphicsContext.lineTo(d3 - (2 * i), -i);
        paramGraphicsContext.arcTo(d3 - (2 * i), 0.0D, d3 - i, 0.0D, i);
      } 
      paramGraphicsContext.lineTo(d3 - i, 0.0D);
      paramGraphicsContext.arcTo(d3, 0.0D, d3, i, i);
      paramGraphicsContext.lineTo(d3, d4 - i);
      paramGraphicsContext.arcTo(d3, d4, d3 - i, d4, i);
      if (paramCallout.a() == CalloutPointer.b) {
        paramGraphicsContext.arcTo(d3 - (2 * i), d4, d3 - (2 * i), d4 + i, i);
        paramGraphicsContext.lineTo(d3 - (2 * i) - i, d4);
      } else if (paramCallout.a() == CalloutPointer.a) {
        paramGraphicsContext.lineTo((2 * i + i), d4);
        paramGraphicsContext.lineTo((2 * i), d4 + i);
        paramGraphicsContext.arcTo((2 * i), d4, i, d4, i);
      } 
      paramGraphicsContext.lineTo(i, d4);
      paramGraphicsContext.arcTo(0.0D, d4, 0.0D, d4 - i, i);
      paramGraphicsContext.closePath();
      paramGraphicsContext.setStroke((Paint)this.D.ah.getValue());
      paramGraphicsContext.setLineWidth(1.0D);
      paramGraphicsContext.setEffect((Effect)this.D.ai.getValue());
      paramGraphicsContext.stroke();
      paramGraphicsContext.setEffect(null);
      paramGraphicsContext.setFill(bool1 ? (Paint)this.D.ag.getValue() : (Paint)this.D.af.getValue());
      paramGraphicsContext.fill();
      Color color = bool1 ? (Color)this.D.y.getValue() : (Color)this.D.ae.getValue();
      if (!bool1 && bool2)
        paramGraphicsContext.setEffect((Effect)this.D.L.getValue()); 
      double d5 = (i + j);
      if (paramBoolean)
        for (String str : arrayOfString) {
          paramGraphicsContext.setFill((Paint)color);
          paramGraphicsContext.fillText(str, a(i), a((int)d5));
          d5 += i * Callout.d;
        }  
      paramGraphicsContext.setFont((Font)this.D.o.getValue());
      paramGraphicsContext.setEffect(null);
      paramGraphicsContext.translate(-d1, -d2);
    } 
  }
  
  private void a(GraphicsContext paramGraphicsContext, Shape paramShape, Rect paramRect, boolean paramBoolean) {
    int i = (int)((Font)this.D.r.getValue()).getSize();
    paramGraphicsContext.setFont((Font)this.D.r.getValue());
    double d1 = paramRect.a();
    double d2 = paramRect.b();
    double d3 = paramRect.c();
    double d4 = paramRect.d();
    boolean bool1 = this.D.c.a.contains(paramShape);
    boolean bool2 = this.D.d.a.contains(paramShape);
    paramGraphicsContext.translate(d1, d2);
    paramGraphicsContext.setStroke((Paint)this.D.ah.getValue());
    paramGraphicsContext.setEffect((Effect)this.D.ai.getValue());
    paramGraphicsContext.setLineWidth(1.0D);
    if (bool1) {
      paramGraphicsContext.setFill((Paint)this.D.A.getValue());
    } else {
      paramGraphicsContext.setFill((Paint)new RadialGradient(50.0D, 0.8D, 0.2D, 0.1D, 0.9D, true, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, 





                
                ColorUtil.a((Color)this.D.aj.getValue(), paramShape.c())), new Stop(1.0D, 
                ColorUtil.a((Color)this.D.ak.getValue(), paramShape.c())) }));
    } 
    double d5 = d3 / 2.0D, d6 = d4 / 2.0D;
    switch (FxDiagramUI$1.a[paramShape.b().ordinal()]) {
      case 1:
        paramGraphicsContext.strokeOval(0.0D, 0.0D, d3, d4);
        paramGraphicsContext.setEffect(null);
        paramGraphicsContext.fillOval(0.0D, 0.0D, d3, d4);
        break;
      case 2:
        paramGraphicsContext.beginPath();
        paramGraphicsContext.moveTo(d5, 0.0D);
        paramGraphicsContext.lineTo(d3, d6);
        paramGraphicsContext.lineTo(d5, d4);
        paramGraphicsContext.lineTo(0.0D, d6);
        paramGraphicsContext.lineTo(d5, 0.0D);
        paramGraphicsContext.closePath();
        paramGraphicsContext.stroke();
        paramGraphicsContext.setEffect(null);
        paramGraphicsContext.fill();
        break;
      case 3:
        paramGraphicsContext.strokeRoundRect(0.0D, 0.0D, d3, d4, i, i);
        paramGraphicsContext.setEffect(null);
        paramGraphicsContext.fillRoundRect(0.0D, 0.0D, d3, d4, i, i);
        break;
    } 
    paramGraphicsContext.translate(-d1, -d2);
    Color color = bool1 ? (Color)this.D.y.getValue() : (Color)this.D.ae.getValue();
    if (!bool1 && bool2)
      paramGraphicsContext.setEffect((Effect)this.D.L.getValue()); 
    paramGraphicsContext.setFont((Font)this.D.o.getValue());
    String[] arrayOfString = paramShape.a();
    if (paramBoolean && arrayOfString != null) {
      double d = i + (paramRect.d() - (arrayOfString.length * i) * Callout.d) / 2.0D;
      for (String str : arrayOfString) {
        paramGraphicsContext.setFill((Paint)color);
        int j = FxAbstractDiagramPane.a(str);
        if (paramShape.b() == ShapeStyle.a) {
          paramGraphicsContext.fillText(str, paramRect.a() + i, a(paramRect.b() + (int)d));
        } else {
          paramGraphicsContext.fillText(str, paramRect.a() + (d3 - j) / 2.0D, a(paramRect.b() + (int)d + (int)(0.5D * Diagram.cell)));
        } 
        d += i * Callout.d;
      } 
    } 
    paramGraphicsContext.setEffect(null);
  }
}
