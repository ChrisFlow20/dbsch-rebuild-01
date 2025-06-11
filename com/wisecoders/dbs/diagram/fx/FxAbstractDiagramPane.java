package com.wisecoders.dbs.diagram.fx;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.diagram.fx.notation.Notation;
import com.wisecoders.dbs.diagram.model.AbstractDiagram;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.diagram.model.Callout;
import com.wisecoders.dbs.diagram.model.CalloutPointer;
import com.wisecoders.dbs.diagram.model.Depict;
import com.wisecoders.dbs.diagram.model.Depictable;
import com.wisecoders.dbs.diagram.model.Diagram;
import com.wisecoders.dbs.diagram.model.DiagramRouterTask;
import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Group;
import com.wisecoders.dbs.diagram.model.PainterColumnPart;
import com.wisecoders.dbs.diagram.model.PainterStatus;
import com.wisecoders.dbs.diagram.model.Point;
import com.wisecoders.dbs.diagram.model.Rect;
import com.wisecoders.dbs.diagram.model.Relation;
import com.wisecoders.dbs.diagram.model.Selection;
import com.wisecoders.dbs.diagram.model.Shape;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.project.fx.Theme;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.schema.Index;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.UniqueArrayList;
import com.wisecoders.dbs.sys.UniqueCopyOnWriteArrayList;
import com.wisecoders.dbs.sys.fx.Alert$;
import com.wisecoders.dbs.sys.fx.ContextMenu$;
import com.wisecoders.dbs.sys.fx.FxQuickMenu;
import com.wisecoders.dbs.sys.fx.FxUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.css.Styleable;
import javafx.css.StyleableProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.Side;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.PopupWindow;
import javax.imageio.ImageIO;

public abstract class FxAbstractDiagramPane extends VirtualCanvas {
  private final Rx an = new Rx(FxAbstractDiagramPane.class, this);
  
  public final Diagram a;
  
  private final Point ao = new Point();
  
  private final Point at = new Point();
  
  private final List au = new ArrayList();
  
  final Button b = new Button();
  
  public final Selection c = new Selection();
  
  public final Selection d = new Selection();
  
  final HashMap e = new HashMap<>();
  
  final Rect f = new Rect(-2.147483648E9D, -2.147483648E9D, 0.0D, 0.0D);
  
  private Unit av;
  
  Unit g;
  
  PainterColumnPart h;
  
  private Attribute aw;
  
  private boolean ax = false;
  
  private final boolean ay;
  
  private boolean az = false;
  
  private double aA = 1.0D;
  
  private double aB;
  
  private FxAbstractDiagramPane$Mode aC = FxAbstractDiagramPane$Mode.a;
  
  protected final ContextMenu$ i = new ContextMenu$();
  
  protected FxQuickMenu j;
  
  public final FxDiagramUI k;
  
  public Diagram d() {
    return this.a;
  }
  
  protected void e() {
    if (this.j != null && this.j.isShowing()) {
      this.j.hide();
      this.j = null;
    } 
    if (this.i.isShowing())
      this.i.hide(); 
  }
  
  private boolean O() {
    return (this.j == null || !this.j.isShowing());
  }
  
  private final Tooltip aD = new Tooltip();
  
  private final List aE;
  
  private final List aF;
  
  private DiagramRouterTask aG;
  
  private a aH;
  
  private int aI;
  
  static final int l = 10;
  
  static final int m = 200;
  
  int n;
  
  public double f() {
    return this.a.getWidth() * this.aA;
  }
  
  public double g() {
    return this.a.getHeight() * this.aA;
  }
  
  void a(Rect paramRect) {
    f(paramRect.a());
    e(paramRect.b());
  }
  
  Rect h() {
    return this.as;
  }
  
  protected Rect a(Unit paramUnit) {
    Rect rect = b(paramUnit);
    if (rect != null)
      rect.a(i(rect.a()), j(rect.b()), (int)(this.aA * rect.c()), (int)(this.aA * rect.d())); 
    return rect;
  }
  
  public Rect b(Unit paramUnit) {
    if (paramUnit instanceof Entity) {
      Entity entity = (Entity)paramUnit;
      Depict depict = this.a.getDepictFor(entity);
      if (depict != null)
        return new Rect(depict.getPosition().a(), depict.getPosition().b(), depict.getPosition().c(), depict.getPosition().d()); 
    } 
    if (paramUnit instanceof Index)
      paramUnit = ((Index)paramUnit).getColumns().get(0); 
    if (paramUnit instanceof Attribute) {
      Attribute attribute = (Attribute)paramUnit;
      Entity entity = attribute.getParentEntity();
      Depict depict = this.a.getDepictFor(entity);
      if (depict != null) {
        Rect rect = new Rect();
        int i = entity.getAttributes().indexOf(attribute);
        double d = ((Font)this.p.getValue()).getSize();
        rect.a(depict.getPosition().a(), (int)(depict.getPosition().b() + d + (Diagram.cell * (i + 1))), depict
            .getPosition().c(), Diagram.cell);
        return rect;
      } 
    } 
    return null;
  }
  
  private boolean d(MouseEvent paramMouseEvent) {
    return (paramMouseEvent.getButton() == MouseButton.SECONDARY || (Sys.k() && paramMouseEvent.isControlDown()));
  }
  
  private boolean e(MouseEvent paramMouseEvent) {
    return (paramMouseEvent.getButton() == MouseButton.PRIMARY);
  }
  
  void i() {
    Depict depict = this.a.getMostReferredDepict();
    if (depict != null)
      c(depict.getParentEntity()); 
  }
  
  public boolean c(Unit paramUnit) {
    return a(paramUnit, true);
  }
  
  public boolean a(Unit paramUnit, boolean paramBoolean) {
    Depictable depictable = null;
    if (paramUnit instanceof Depictable) {
      depictable = (Depictable)paramUnit;
    } else {
      Entity entity = paramUnit.getParentEntity();
      if (entity != null) {
        S();
        depictable = this.a.getDepictFor(entity);
      } 
    } 
    this.c.b();
    this.c.b(paramUnit);
    S();
    if (depictable == null || this.as.c(i(depictable.getPosition().a()), j(depictable.getPosition().b()), 
        (int)(depictable.getPosition().c() * this.aA), (int)(depictable.getPosition().d() * this.aA))) {
      j();
    } else {
      Rect rect = new Rect(i(depictable.getPosition().a() + depictable.getPosition().c() / 2.0D) - this.as.c() / 2.0D, j(depictable.getPosition().b()) - this.as.d() / 6.0D, this.as.c(), this.as.d());
      if (this.aH != null)
        this.aH.a(); 
      if (paramBoolean) {
        this.aH = new a(this, rect);
      } else {
        a(rect);
      } 
      return true;
    } 
    return false;
  }
  
  public FxAbstractDiagramPane(Diagram paramDiagram, boolean paramBoolean) {
    this.aE = new UniqueCopyOnWriteArrayList();
    this.aF = new UniqueCopyOnWriteArrayList();
    this.aI = 0;
    this.n = 0;
    this.o = aJ.createStyleableFontProperty((Styleable)this, "font", "font", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.o, Font.font(A(), FxUtil.b * 1.083333333333333D));
    this.p = aJ.createStyleableFontProperty((Styleable)this, "table-header-font", "table-header-font", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.p, Font.font(B(), FontWeight.BOLD, FxUtil.b * 1.125D));
    this.q = aJ.createStyleableFontProperty((Styleable)this, "data-type-font", "data-type-font", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.q, Font.font(A(), FxUtil.b));
    this.r = aJ.createStyleableFontProperty((Styleable)this, "callout-font", "callout-font", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.r, Font.font(A(), FxUtil.b * 1.083333333333333D));
    this.s = aJ.createStyleableColorProperty((Styleable)this, "print-preview-page-border", "print-preview-page-border", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.s, Color.web("#a79424"));
    this.t = aJ.createStyleableNumberProperty((Styleable)this, "table-header-type", "table-header-type", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.t, Integer.valueOf(2));
    this.u = aJ.createStyleableColorProperty((Styleable)this, "table-header-top", "table-header-top", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.u, Color.web("#75ae68"));
    this.v = aJ.createStyleableColorProperty((Styleable)this, "table-header-middle", "table-header-middle", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.v, Color.web("#ece8e2"));
    this.w = aJ.createStyleableColorProperty((Styleable)this, "table-header-bottom", "table-header-bottom", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.w, Color.web("#ffffff"));
    this.x = aJ.createStyleableColorProperty((Styleable)this, "table-header-foreground", "table-header-foreground", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.x, Color.web("#576b57"));
    this.y = aJ.createStyleableColorProperty((Styleable)this, "table-header-foreground-selected", "table-header-foreground-selected", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.y, Color.web("#ffffff"));
    this.z = aJ.createStyleableColorProperty((Styleable)this, "table-background", "table-background", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.z, Color.web("#f7f7f7"));
    this.A = aJ.createStyleableColorProperty((Styleable)this, "table-background-selected", "table-background-selected", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.A, Color.web("#4f6c85"));
    this.B = aJ.createStyleableColorProperty((Styleable)this, "table-background-perspective", "table-background-perspective", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.B, Color.web("#7d8ca6"));
    this.C = aJ.createStyleableBooleanProperty((Styleable)this, "table-rounded-corner", "table-rounded-corner", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.C, false);
    this.D = aJ.createStyleableColorProperty((Styleable)this, "table-border-color", "table-border-color", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.D, Color.web("#b39d9d"));
    this.E = aJ.createStyleableNumberProperty((Styleable)this, "table-border-width", "table-border-width", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.E, Double.valueOf(1.4D));
    this.F = aJ.createStyleableEffectProperty((Styleable)this, "table-shadow-effect", "table-shadow-effect", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.F, null);
    this.G = aJ.createStyleableEffectProperty((Styleable)this, "table-shadow-mouse-over-effect", "table-shadow-mouse-over-effect", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.G, null);
    this.H = aJ.createStyleableColorProperty((Styleable)this, "column-foreground", "column-foreground", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.H, Color.web("#1e2025"));
    this.I = aJ.createStyleableColorProperty((Styleable)this, "column-primary-key", "column-primary-key", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.I, Color.web("#4f3706"));
    this.J = aJ.createStyleableColorProperty((Styleable)this, "column-data-type", "column-data-type", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.J, Color.web("#9f9696"));
    this.K = aJ.createStyleableColorProperty((Styleable)this, "column-functional-foreground", "column-functional-foreground", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.K, Color.web("#660a96"));
    this.L = aJ.createStyleableEffectProperty((Styleable)this, "mouse-over-effect", "mouse-over-effect", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.L, (Effect)new DropShadow(1.0D, 1.0D, 1.0D, Color.web("#818181")));
    this.M = aJ.createStyleableEffectProperty((Styleable)this, "text-shadow", "text-shadow", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.M, (Effect)new Shadow(6.0D, Color.web("#596363")));
    this.N = aJ.createStyleableColorProperty((Styleable)this, "column-foreground-selected", "column-foreground-selected", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.N, Color.web("#141619"));
    this.O = aJ.createStyleableColorProperty((Styleable)this, "column-background-selected", "column-background-selected", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.O, Color.web("#e0e0e0"));
    this.P = aJ.createStyleableColorProperty((Styleable)this, "json-marker-foreground", "json-marker-foreground", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.P, Color.web("#0046a5"));
    this.Q = aJ.createStyleableColorProperty((Styleable)this, "foreign-key", "foreign-key", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.Q, Color.web("#888581"));
    this.R = aJ.createStyleableColorProperty((Styleable)this, "foreign-key-text", "foreign-key-text", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.R, Color.web("#a6a6a6"));
    this.S = aJ.createStyleableColorProperty((Styleable)this, "foreign-key-generated", "foreign-key-generated", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.S, Color.web("#b5a167"));
    this.T = aJ.createStyleableColorProperty((Styleable)this, "foreign-key-virtual", "foreign-key-virtual", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.T, Color.web("#71527b"));
    this.U = aJ.createStyleableColorProperty((Styleable)this, "foreign-key-mouse-over", "foreign-key-mouse-over", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.U, Color.web("#1151a9"));
    this.V = aJ.createStyleableColorProperty((Styleable)this, "foreign-key-selected", "foreign-key-selected", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.V, Color.web("#af2323"));
    this.W = aJ.createStyleableNumberProperty((Styleable)this, "foreign-key-line-width", "foreign-key-line-width", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.W, Double.valueOf(1.65D));
    this.X = aJ.createStyleableColorProperty((Styleable)this, "group-text", "group-text", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.X, Color.web("#a7b9ba"));
    this.Y = aJ.createStyleableColorProperty((Styleable)this, "group-background", "group-background", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.Y, Color.web("#fbf8ec"));
    this.Z = aJ.createStyleableColorProperty((Styleable)this, "group-border-color", "group-border-color", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.Z, Color.web("#cacab0"));
    this.aa = aJ.createStyleableColorProperty((Styleable)this, "group-selected-border-color", "group-selected-border-color", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.aa, Color.web("#1f7276"));
    this.ab = aJ.createStyleableNumberProperty((Styleable)this, "group-printTransparency-value", "group-printTransparency-value", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.ab, Double.valueOf(0.65D));
    this.ac = aJ.createStyleableNumberProperty((Styleable)this, "group-transparency-value", "group-transparency-value", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.ac, Double.valueOf(0.34D));
    this.ad = aJ.createStyleableNumberProperty((Styleable)this, "group-transparencySelected-value", "group-transparency-selected-value", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.ad, Double.valueOf(0.65D));
    this.ae = aJ.createStyleableColorProperty((Styleable)this, "callout-foreground", "callout-foreground", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.ae, Color.web("#373128"));
    this.af = aJ.createStyleablePaintProperty((Styleable)this, "callout-background", "callout-background", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.af, (Paint)new RadialGradient(280.0D, 0.3D, 0.2D, 0.3D, 0.9D, true, CycleMethod.NO_CYCLE, new Stop[] { new Stop(0.0D, Color.web("#fefefcdd")), new Stop(1.0D, Color.web("#f3f1dfdd")) }));
    this.ag = aJ.createStyleablePaintProperty((Styleable)this, "callout-selected-background", "callout-selected-background", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.ag, (Paint)Color.web("#408148"));
    this.ah = aJ.createStyleableColorProperty((Styleable)this, "callout-border-color", "callout-border-color", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.ah, Color.web("#d0cec3"));
    this.ai = aJ.createStyleableEffectProperty((Styleable)this, "callout-shadow-effect", "callout-shadow-effect", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.ai, null);
    this.aj = aJ.createStyleableColorProperty((Styleable)this, "shape-background1", "shape-background1", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.aj, Color.web("#fcfaf4"));
    this.ak = aJ.createStyleableColorProperty((Styleable)this, "shape-background2", "shape-background2", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.ak, Color.web("#f1e6c6"));
    this.al = aJ.createStyleableNumberProperty((Styleable)this, "line-width", "line-width", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.al, Double.valueOf(6.0D));
    this.am = aJ.createStyleableColorProperty((Styleable)this, "line-text", "line-text", paramFxAbstractDiagramPane -> paramFxAbstractDiagramPane.am, Color.web("#5e94c4"));
    this.a = paramDiagram;
    this.k = new FxDiagramUI(this);
    this.ay = paramBoolean;
    this.aD.setAutoHide(true);
    this.aD.setMaxWidth(800.0D);
    this.aD.setWrapText(true);
    this.aD.setHideOnEscape(true);
    this.aD.setAnchorLocation(PopupWindow.AnchorLocation.CONTENT_TOP_LEFT);
    getStyleClass().addAll((Object[])new String[] { "diagram" });
    getStyleClass().addAll((Object[])new String[] { "wallpaper" });
    AbstractDiagram.setCellSizeFromFontSize(((Font)this.o.getValue()).getSize());
    aK.setFont((Font)this.o.getValue());
    aM.setFont((Font)this.p.getValue());
    aN.setFont((Font)this.r.getValue());
    paramDiagram.refresh();
    j();
    this.ar.setOnScroll(paramScrollEvent -> {
          if (paramScrollEvent.isControlDown() || paramScrollEvent.isAltDown()) {
            double d = paramScrollEvent.getDeltaY();
            if (d != 0.0D)
              a(n() + ((paramScrollEvent.getDeltaY() > 0.0D) ? true : -1) * n() * 0.07D); 
          } else {
            c(-paramScrollEvent.getDeltaY());
            d(-paramScrollEvent.getDeltaX());
          } 
        });
    setFocusTraversable(true);
    setOnKeyPressed(paramKeyEvent -> {
          Attribute attribute;
          double d1 = 0.0D;
          double d2 = 0.0D;
          switch (FxAbstractDiagramPane$1.e[paramKeyEvent.getCode().ordinal()]) {
            case 1:
              d2 -= 10.0D;
              break;
            case 2:
              d2 += 10.0D;
              break;
            case 3:
              d1 += 10.0D;
              break;
            case 4:
              d1 -= 10.0D;
              break;
            case 5:
              q();
              this.aC = FxAbstractDiagramPane$Mode.a;
              this.e.clear();
              j();
              break;
            case 6:
              attribute = p();
              if (attribute != null) {
                if (s()) {
                  attribute.setTicked(!attribute.isTicked());
                } else {
                  this.c.b(p());
                } 
                j();
              } 
            case 7:
              a(1.0D);
              break;
            case 8:
              a(this.aA + 0.1D);
              break;
            case 9:
              a(this.aA - 0.1D);
              break;
            case 10:
              if (paramKeyEvent.isShortcutDown()) {
                this.c.b();
                for (Depict depict : (d()).depicts)
                  this.c.b(depict.getEntity()); 
                j();
              } 
              break;
            case 11:
              this.az = true;
              break;
          } 
          if (d1 != 0.0D || d2 != 0.0D)
            b(d1, d2); 
        });
    setOnKeyReleased(paramKeyEvent -> {
          if (paramKeyEvent.getCode() == KeyCode.SPACE)
            this.az = false; 
        });
    this.ar.setOnDragDetected(paramMouseEvent -> {
          Dragboard dragboard = this.ar.startDragAndDrop(new TransferMode[] { TransferMode.COPY });
          if (paramMouseEvent.isPrimaryButtonDown() && this.c.a.size() == 1 && this.c.a.get(0) instanceof Attribute) {
            if (Pref.c("createFkTip", 4))
              this.an.d(getScene(), "createFkDragDrop"); 
            dragboard.setDragView(Rx.c("cursor_create_fk.png"));
          } else {
            dragboard.setDragView(Rx.c("empty.png"));
          } 
          ClipboardContent clipboardContent = new ClipboardContent();
          FxDnd.a(this.c.a);
          clipboardContent.put(FxDnd.a, Integer.valueOf(1));
          dragboard.setContent((Map)clipboardContent);
          c(paramMouseEvent);
          paramMouseEvent.consume();
        });
    this.ar.setOnDragOver(paramDragEvent -> {
          if (paramDragEvent.getDragboard().hasContent(FxDnd.a)) {
            paramDragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            a(paramDragEvent);
            paramDragEvent.consume();
          } 
        });
    this.ar.setOnDragDropped(paramDragEvent -> {
          if (this.aC == FxAbstractDiagramPane$Mode.a && paramDragEvent.getDragboard().hasContent(FxDnd.a))
            a(new Point((int)(paramDragEvent.getX() + T()), (int)(paramDragEvent.getY() + U()))); 
          FxDnd.b.clear();
          paramDragEvent.setDropCompleted(true);
          paramDragEvent.consume();
          c(paramDragEvent.getX(), paramDragEvent.getY());
        });
    this.ar.setOnDragDone(paramDragEvent -> {
          this.e.clear();
          j();
        });
    this.ar.addEventFilter(MouseEvent.MOUSE_PRESSED, this::f);
    this.ar.addEventFilter(MouseEvent.MOUSE_CLICKED, this::g);
    this.ar.setOnMouseMoved(this::h);
    this.ar.addEventFilter(MouseEvent.MOUSE_EXITED, paramMouseEvent -> {
          a((String)null, paramMouseEvent);
          q();
          this.d.b();
          j();
        });
    this.ar.setCursor(Cursor.HAND);
  }
  
  public void j() {
    if (this.aF != null && this.a.getStatus().a()) {
      this.aF.clear();
      this.aF.addAll(this.aE);
      this.aF.addAll(this.c.d);
      this.aF.addAll(this.d.d);
      for (Relation relation : this.aF) {
        boolean bool1 = this.c.d.contains(relation);
        boolean bool2 = this.d.d.contains(relation);
        this.a.highlightLine(relation, bool1, bool2);
      } 
      this.aF.clear();
      this.aE.clear();
      this.aE.addAll(this.c.d);
      this.aE.addAll(this.d.d);
    } 
    Q();
  }
  
  public synchronized void k() {
    m();
    this.a.refresh();
    this.aG = new DiagramRouterTask(this, this.a);
    j();
  }
  
  void l() {
    m();
    this.a.setStatus(PainterStatus.e);
  }
  
  void m() {
    if (this.aG != null)
      this.aG.cancel(); 
  }
  
  public double n() {
    return this.aA;
  }
  
  public void a(double paramDouble) {
    S();
    paramDouble = Math.max(0.001D, paramDouble);
    paramDouble = Math.min(100.0D, paramDouble);
    this.aA = paramDouble;
    S();
    j();
  }
  
  private Depict b(Point paramPoint) {
    for (int i = this.a.depicts.size() - 1; i > -1; i--) {
      Depict depict = this.a.depicts.get(i);
      if (depict.getPosition().a(paramPoint))
        return depict; 
    } 
    return null;
  }
  
  private Callout c(Point paramPoint) {
    Callout callout = null;
    for (Callout callout1 : this.a.callouts) {
      if (callout1.getPosition().a(paramPoint))
        callout = callout1; 
    } 
    return callout;
  }
  
  private Shape d(Point paramPoint) {
    Shape shape = null;
    for (Shape shape1 : this.a.shapes) {
      if (shape1.a.getPosition().a(paramPoint))
        shape = shape1; 
    } 
    return shape;
  }
  
  private Attribute a(Depict paramDepict, Point paramPoint) {
    if (paramDepict.getPosition().a(paramPoint)) {
      double d = paramDepict.getPosition().b();
      int i = paramDepict.getVisibleAttributes().size();
      if (paramPoint.b >= d + AbstractDiagram.cell && paramPoint.b <= d + ((i + 2) * AbstractDiagram.cell)) {
        int j = (int)((paramPoint.b - d) / AbstractDiagram.cell) - 2;
        if (j > -1 && j < paramDepict.getVisibleAttributes().size())
          return paramDepict.getVisibleAttributes().get(j); 
      } else {
        return null;
      } 
    } 
    return null;
  }
  
  private boolean b(Depict paramDepict, Point paramPoint) {
    if (paramDepict.getPosition().a(paramPoint) && !paramDepict.getHiddenAttributes().isEmpty()) {
      double d = paramDepict.getPosition().b();
      return (paramPoint.b >= d + AbstractDiagram.cell && paramPoint.b > d + ((paramDepict.getVisibleAttributes().size() + 2) * AbstractDiagram.cell));
    } 
    return false;
  }
  
  private Group e(Point paramPoint) {
    for (Group group : this.a.groups) {
      if (group.getPosition().a(paramPoint))
        return group; 
    } 
    return null;
  }
  
  protected Unit o() {
    return this.av;
  }
  
  Attribute p() {
    return this.aw;
  }
  
  private void c(Attribute paramAttribute) {
    this.aw = paramAttribute;
    if (paramAttribute != null)
      q(); 
  }
  
  protected void a(String paramString, double paramDouble1, double paramDouble2) {
    q();
    this.aD.setText(paramString);
    Point2D point2D = this.ar.localToScreen(paramDouble1, paramDouble2);
    this.aD.show((Node)this.ar, point2D.getX(), point2D.getY());
  }
  
  private void a(String paramString, MouseEvent paramMouseEvent) {
    if (StringUtil.isFilledTrim(paramString)) {
      if (!paramString.equals(this.aD.getText())) {
        this.aD.hide();
        this.aD.setText(paramString);
        this.aD.show((Node)this.ar, paramMouseEvent.getScreenX() + 10.0D, paramMouseEvent.getScreenY() - this.aD.getHeight() - 20.0D);
      } 
    } else {
      this.aD.hide();
    } 
  }
  
  protected void q() {
    this.aD.setText(null);
    this.aD.hide();
  }
  
  public void a(boolean paramBoolean) {
    this.ax = paramBoolean;
  }
  
  boolean r() {
    return this.ax;
  }
  
  boolean d(Unit paramUnit) {
    return this.d.a.contains(paramUnit);
  }
  
  boolean s() {
    return this.ay;
  }
  
  PainterColumnPart a(Attribute paramAttribute, Point paramPoint) {
    Depict depict = this.a.getDepictFor(paramAttribute.getParentEntity());
    if (depict != null && depict.getPosition().a(paramPoint)) {
      if (s()) {
        if (paramPoint.a < depict.getPosition().a() + AbstractDiagram.cell)
          return PainterColumnPart.b; 
        if (paramPoint.a < depict.getPosition().a() + (2 * AbstractDiagram.cell))
          return PainterColumnPart.c; 
      } else if (paramPoint.a < depict.getPosition().a() + AbstractDiagram.cell) {
        return PainterColumnPart.c;
      } 
      if (paramPoint.a > depict.getPosition().a() + depict.getPosition().c() - AbstractDiagram.cell)
        return PainterColumnPart.d; 
    } 
    return PainterColumnPart.a;
  }
  
  private Unit a(Point paramPoint, boolean paramBoolean) {
    Callout callout = c(paramPoint);
    if (callout != null) {
      this.a.bringToFront(callout);
      return callout;
    } 
    Shape shape = d(paramPoint);
    if (shape != null) {
      this.a.bringToFront(shape);
      return shape;
    } 
    Depict depict = b(paramPoint);
    if (depict == null) {
      List<Unit> list = this.a.findRelations(paramPoint.a, paramPoint.b, c());
      if (list.isEmpty())
        return e(paramPoint); 
      return (list.size() == 1) ? list.get(0) : null;
    } 
    if (paramBoolean)
      this.a.bringToFront(depict); 
    Attribute attribute = a(depict, paramPoint);
    return (attribute != null) ? attribute : depict.getParentEntity();
  }
  
  private double g(double paramDouble) {
    return paramDouble / this.aA;
  }
  
  private double h(double paramDouble) {
    return paramDouble / this.aA;
  }
  
  private int i(double paramDouble) {
    return (int)(paramDouble * this.aA);
  }
  
  private int j(double paramDouble) {
    return (int)(paramDouble * this.aA);
  }
  
  protected void a(Unit paramUnit, MouseEvent paramMouseEvent, boolean paramBoolean1, boolean paramBoolean2) {
    Entity entity = (paramUnit != null) ? paramUnit.getParentEntity() : null;
    Attribute attribute = (paramUnit instanceof Attribute) ? (Attribute)paramUnit : null;
    Rect rect = a(paramUnit);
    if (rect != null)
      this.at.a(rect.a() + rect.c(), rect.b()); 
    if (entity != null) {
      List list = a(entity, attribute, paramBoolean1, paramBoolean2);
      this.i.setAutoHide(true);
      this.i.a(true);
      this.i.getItems().setAll(list);
      if (list.isEmpty())
        this.i.getItems().add(new MenuItem(this.an.H("joinFkEmpty"))); 
      if (paramMouseEvent == null) {
        this.i.show((Node)this, Side.TOP, this.at.a - T(), this.at.b - U());
      } else {
        this.i.show((Node)this, paramMouseEvent.getScreenX(), paramMouseEvent.getScreenY());
      } 
    } 
  }
  
  public int t() {
    return this.a.dim_X * AbstractDiagram.cell;
  }
  
  public int u() {
    return this.a.dim_Y * AbstractDiagram.cell;
  }
  
  private void a(double paramDouble1, double paramDouble2, boolean paramBoolean) {
    int i = (int)g(paramDouble1 + T()), j = (int)h(paramDouble2 + U());
    this.ao.a(i, j);
    if (paramBoolean)
      this.at.a(i, j); 
  }
  
  public Point v() {
    S();
    if (!this.as.f(i(this.at.a), j(this.at.b)))
      this.at.a(g(this.as.i()), h(this.as.j())); 
    return this.at;
  }
  
  protected void a(double paramDouble1, double paramDouble2) {
    this.at.c(paramDouble1, paramDouble2);
  }
  
  public void a(Unit paramUnit, String paramString) {
    Rect rect = a(paramUnit);
    if (rect != null && paramString != null) {
      Callout callout = this.a.createCallout(paramString, new Point(rect.a(), rect.b() - 20.0D));
      callout.a(true);
      callout.a(CalloutPointer.a);
      callout.refresh();
      if (paramUnit instanceof com.wisecoders.dbs.diagram.model.AbstractTable) {
        callout.translate(rect.c() / 2.0D, -20.0D);
      } else {
        callout.translate(-callout.getPosition().c(), 0.0D);
        callout.a(CalloutPointer.b);
      } 
      this.au.add(callout);
      k();
      c(paramUnit);
    } 
  }
  
  public boolean w() {
    return !this.au.isEmpty();
  }
  
  public void x() {
    for (Callout callout : this.au)
      callout.markForDeletion(); 
    this.au.clear();
    k();
  }
  
  public void y() {
    for (Callout callout : this.au)
      callout.markForDeletion(); 
    this.a.refresh();
  }
  
  protected void a(FileType paramFileType) {
    File file = FxFileChooser.a(getScene(), "Choose file", FileOperation.b, new FileType[] { paramFileType });
    if (file != null)
      try {
        String str = paramFileType.F[0];
        getScene().getStylesheets().removeAll((Object[])(Theme.a()).h);
        char c = '΄';
        int i = Math.min(900, t()), j = Math.min(900, u());
        Canvas canvas = new Canvas(i, j);
        BufferedImage bufferedImage = null;
        Graphics graphics = null;
        int k;
        for (k = 0; k < t(); k += i) {
          int m;
          for (m = 0; m < u(); m += j) {
            int n = Math.min(900, t() - k);
            int i1 = Math.min(900, u() - m);
            GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
            this.k.a(graphicsContext, true, PaintMode.a, 1.0D, k, m, n, i1);
            SnapshotParameters snapshotParameters = new SnapshotParameters();
            snapshotParameters.setViewport(new Rectangle2D(0.0D, 0.0D, n, i1));
            WritableImage writableImage = canvas.snapshot(snapshotParameters, null);
            BufferedImage bufferedImage1 = SwingFXUtils.fromFXImage((Image)writableImage, null);
            if (graphics == null) {
              bufferedImage = new BufferedImage(t(), u(), "jpg".equalsIgnoreCase(str) ? 1 : bufferedImage1.getType());
              graphics = bufferedImage.getGraphics();
            } 
            graphics.drawImage(bufferedImage1, k, m, null);
          } 
        } 
        if (graphics != null) {
          graphics.dispose();
          ImageIO.write(bufferedImage, str, file);
        } 
        getScene().getStylesheets().addAll((Object[])(Theme.a()).h);
      } catch (Throwable throwable) {
        (new Alert$(Alert.AlertType.ERROR)).a(getScene()).c(throwable.getLocalizedMessage());
      }  
  }
  
  public void a(GraphicsContext paramGraphicsContext) {
    this.k.a(paramGraphicsContext, true, PaintMode.b, n(), 0.0D, 0.0D, f(), g());
  }
  
  public void z() {
    long l = System.currentTimeMillis();
    GraphicsContext graphicsContext = this.ar.a();
    if (graphicsContext != null) {
      this.k.a(graphicsContext, true, PaintMode.a, n(), this.as.a(), this.as.b(), this.as.c(), this.as.d());
      if (System.currentTimeMillis() - l > 200L) {
        this.n++;
      } else {
        this.n = 0;
      } 
      if (this.n > 10 && !getStyleClass().contains("slowDiagram"))
        getStyleClass().add("slowDiagram"); 
    } 
  }
  
  private static final StyleablePropertyFactory aJ = new StyleablePropertyFactory(getClassCssMetaData());
  
  public final StyleableProperty o;
  
  public final StyleableProperty p;
  
  public final StyleableProperty q;
  
  public final StyleableProperty r;
  
  StyleableProperty s;
  
  public final StyleableProperty t;
  
  final StyleableProperty u;
  
  final StyleableProperty v;
  
  final StyleableProperty w;
  
  StyleableProperty x;
  
  StyleableProperty y;
  
  StyleableProperty z;
  
  StyleableProperty A;
  
  StyleableProperty B;
  
  public final StyleableProperty C;
  
  final StyleableProperty D;
  
  final StyleableProperty E;
  
  final StyleableProperty F;
  
  final StyleableProperty G;
  
  StyleableProperty H;
  
  StyleableProperty I;
  
  StyleableProperty J;
  
  StyleableProperty K;
  
  final StyleableProperty L;
  
  final StyleableProperty M;
  
  final StyleableProperty N;
  
  final StyleableProperty O;
  
  StyleableProperty P;
  
  public final StyleableProperty Q;
  
  public final StyleableProperty R;
  
  StyleableProperty S;
  
  StyleableProperty T;
  
  StyleableProperty U;
  
  StyleableProperty V;
  
  StyleableProperty W;
  
  StyleableProperty X;
  
  StyleableProperty Y;
  
  StyleableProperty Z;
  
  StyleableProperty aa;
  
  StyleableProperty ab;
  
  StyleableProperty ac;
  
  StyleableProperty ad;
  
  StyleableProperty ae;
  
  StyleableProperty af;
  
  StyleableProperty ag;
  
  StyleableProperty ah;
  
  final StyleableProperty ai;
  
  public StyleableProperty aj;
  
  public StyleableProperty ak;
  
  StyleableProperty al;
  
  public final StyleableProperty am;
  
  public List getCssMetaData() {
    return aJ.getCssMetaData();
  }
  
  public static String A() {
    return FxUtil.a(new String[] { "Segoe UI", "Trebuchet MS", "Candara", "Verdana", "Roboto", "System" });
  }
  
  public static String B() {
    return FxUtil.a(new String[] { "Roboto Condensed", "Segoe UI", "Trebuchet MS", "Candara", "Verdana", "Roboto", "System" });
  }
  
  private static final Text aK = new Text();
  
  private static final Text aL = new Text();
  
  private static final Text aM = new Text();
  
  private static final Text aN = new Text();
  
  private static final int aO = 30;
  
  public static synchronized int a(String paramString) {
    aK.setText(paramString);
    double d = aK.getBoundsInLocal().getWidth();
    return (int)d;
  }
  
  public static synchronized int b(String paramString) {
    aL.setText(paramString);
    double d = aL.getBoundsInLocal().getWidth();
    return (int)d;
  }
  
  public static Font E() {
    return aK.getFont();
  }
  
  public static synchronized int c(String paramString) {
    aM.setText(paramString);
    double d = aM.getBoundsInLocal().getWidth();
    return (int)d;
  }
  
  public static synchronized int d(String paramString) {
    aN.setText(paramString);
    double d = aN.getBoundsInLocal().getWidth();
    return (int)d;
  }
  
  public static Font F() {
    return aN.getFont();
  }
  
  void c(MouseEvent paramMouseEvent) {
    if (this.av instanceof Attribute && this.aA < 0.5D)
      this.av = this.av.getParentEntity(); 
    if (this.av instanceof Entity || this.av instanceof Callout)
      this.c.b(this.av); 
    if (d(paramMouseEvent) || paramMouseEvent.getButton() == MouseButton.MIDDLE || this.az) {
      this.aC = FxAbstractDiagramPane$Mode.f;
    } else if (this.aC == FxAbstractDiagramPane$Mode.b || (this.av instanceof Shape && paramMouseEvent.isShortcutDown())) {
      this.aC = FxAbstractDiagramPane$Mode.b;
    } else if (this.c.b.contains(this.av) || this.c.c.contains(this.av) || this.av instanceof Group) {
      this.e.clear();
      ArrayList<Group> arrayList = new ArrayList();
      for (Unit unit : this.c.a) {
        if (unit instanceof Group) {
          Group group = (Group)unit;
          for (Depict depict1 : group.getDepicts())
            this.e.put(depict1, new Rect(depict1.getPosition())); 
          arrayList.add(group);
        } 
        if (unit instanceof Callout) {
          Callout callout = (Callout)unit;
          this.e.put(callout, new Rect(callout.getPosition()));
          continue;
        } 
        if (unit instanceof Shape) {
          Shape shape = (Shape)unit;
          this.e.put(shape.a, new Rect(shape.a.getPosition()));
          continue;
        } 
        Depict depict = this.a.getDepictFor(unit);
        if (depict == null)
          depict = this.a.getDepictFor(unit.getParentEntity()); 
        if (depict != null)
          this.e.put(depict, new Rect(depict.getPosition())); 
      } 
      arrayList.addAll(this.e.keySet());
      for (Depictable depictable : arrayList) {
        for (Callout callout : this.a.callouts) {
          if (callout.b == depictable && !this.e.containsKey(callout))
            this.e.put(callout, new Rect(callout.getPosition())); 
        } 
      } 
      this.aC = FxAbstractDiagramPane$Mode.e;
    } else if (this.av instanceof Attribute) {
      this.aC = FxAbstractDiagramPane$Mode.d;
    } else if (paramMouseEvent.isAltDown()) {
      this.aB = paramMouseEvent.getY();
      this.aC = FxAbstractDiagramPane$Mode.h;
    } else {
      this.aC = FxAbstractDiagramPane$Mode.g;
    } 
  }
  
  void a(DragEvent paramDragEvent) {
    double d2;
    Depict depict;
    double d4, d5;
    Rect rect;
    a(paramDragEvent.getX(), paramDragEvent.getY(), false);
    S();
    switch (FxAbstractDiagramPane$1.a[this.aC.ordinal()]) {
      case 1:
        a(Math.max(0.05D, 1.0D + (this.aB - paramDragEvent.getY()) / 20.0D));
        break;
      case 2:
        b((this.at.a - this.ao.a) * this.aA, (this.at.b - this.ao.b) * this.aA);
        break;
      case 3:
        d2 = Math.min(this.at.a, this.ao.a);
        d3 = Math.max(this.at.a, this.ao.a);
        d4 = Math.min(this.at.b, this.ao.b);
        d5 = Math.max(this.at.b, this.ao.b);
        this.f.a(d2, d4, d3 - d2, d5 - d4);
        this.c.b();
        rect = new Rect();
        for (Depict depict1 : this.a.depicts) {
          rect.a(depict1.getPosition());
          rect.d((int)(((Font)this.o.getValue()).getSize() * 2.0D));
          if (this.f.d(rect))
            this.c.b(depict1.getParentEntity()); 
        } 
        for (Callout callout : this.a.callouts) {
          if (this.f.d(callout.getPosition()))
            this.c.b(callout); 
        } 
        for (Shape shape : this.a.shapes) {
          if (this.f.d(shape.a.getPosition()))
            this.c.b(shape); 
        } 
        j();
        break;
      case 4:
        for (Depictable depictable : this.e.keySet())
          ((Rect)this.e.get(depictable)).c(this.ao.a - this.at.a, this.ao.b - this.at.b); 
        this.at.a(this.ao);
        S();
        j();
        break;
      case 5:
        depict = b(this.ao);
        if (depict == null || (!(this.av instanceof Attribute) && this.av.getParentEntity() != depict.getParentEntity()))
          this.aC = FxAbstractDiagramPane$Mode.c; 
        break;
      case 6:
      case 7:
        this.d.b();
        depict = b(this.ao);
        if (depict != null) {
          Attribute attribute = a(depict, this.ao);
          if (attribute != null && attribute != this.av) {
            this.g = attribute;
            this.d.b(attribute);
            this.d.b(this.av);
          } 
        } 
        j();
        break;
    } 
    double d1 = paramDragEvent.getX() + T();
    double d3 = paramDragEvent.getY() + U();
    if (this.av != null) {
      byte b1 = 0, b2 = 0;
      byte b = 10;
      if (d1 < this.as.a() + 30.0D) {
        b1 = -10;
      } else if (d3 < this.as.b() + 30.0D) {
        b2 = -10;
      } else if (d1 > this.as.a() + this.as.c() - 30.0D) {
        b1 += 10;
      } else if (d3 > this.as.b() + this.as.d() - 30.0D) {
        b2 += 10;
      } 
      if (b1 != 0 || b2 != 0) {
        this.as.c(b1, b2);
        a(this.as);
      } 
    } 
  }
  
  private void c(double paramDouble1, double paramDouble2) {
    Unit unit2;
    a(paramDouble1, paramDouble2, true);
    Unit unit1 = a(this.ao, false);
    switch (FxAbstractDiagramPane$1.a[this.aC.ordinal()]) {
      case 4:
        for (Depictable depictable : this.e.keySet()) {
          Rect rect = (Rect)this.e.get(depictable);
          if (depictable instanceof Depict)
            for (Depict depict : this.a.depicts) {
              if (depict != depictable && rect.d(depict.getPosition())) {
                if (depict.getPosition().a() <= rect.a()) {
                  this.a.moveUndoable(depict, rect.a() - depict.getPosition().c(), depict.getPosition().b());
                  continue;
                } 
                this.a.moveUndoable(depict, rect.a() + rect.c(), depict.getPosition().b());
              } 
            }  
          this.a.moveUndoable(depictable, rect.a(), rect.b());
        } 
        this.e.clear();
        this.aC = FxAbstractDiagramPane$Mode.a;
        k();
        S();
        D();
        break;
      case 5:
        if (unit1 instanceof Attribute && this.av instanceof Attribute && this.av != unit1) {
          for (Attribute attribute : this.c.e)
            a(unit1.getParentEntity(), attribute, (Attribute)unit1); 
          k();
        } 
        S();
        break;
      case 7:
        unit2 = this.av;
        if (unit2 instanceof Attribute) {
          Attribute attribute = (Attribute)unit2;
          if (unit1 != this.av)
            if (unit1 instanceof Attribute) {
              a(unit1, attribute);
            } else if (unit1 instanceof Entity) {
              if (this.c.e.size() > 1 && this.c.e.contains(this.av)) {
                a((Entity)unit1, this.c.e);
              } else {
                a((Entity)unit1, attribute);
              } 
            }  
        } 
        S();
        break;
      case 6:
        if (this.av != null && unit1 != null)
          a(this.av, unit1); 
        break;
    } 
    this.f.a(-2.147483648E9D, -2.147483648E9D);
    this.aC = FxAbstractDiagramPane$Mode.a;
  }
  
  private void f(MouseEvent paramMouseEvent) {
    e();
    q();
    c((Attribute)null);
    requestFocus();
    a(paramMouseEvent.getX(), paramMouseEvent.getY(), true);
    this.f.a(-2.147483648E9D, -2.147483648E9D);
    this.av = a(this.ao, true);
    if (this.av == null) {
      this.c.b();
      this.d.b();
    } else if (d(paramMouseEvent)) {
      if (!this.c.a.contains(this.av)) {
        this.c.b();
        this.c.b(this.av);
      } 
    } else if (this.c.a.contains(this.av)) {
      if (paramMouseEvent.isShiftDown() && !paramMouseEvent.isControlDown())
        this.c.a(this.av); 
    } else {
      if ((!paramMouseEvent.isShiftDown() && !paramMouseEvent.isControlDown()) || (paramMouseEvent.isShiftDown() && paramMouseEvent.isControlDown()))
        this.c.b(); 
      this.c.b(this.av);
    } 
    if (this.aC != FxAbstractDiagramPane$Mode.b)
      this.aC = FxAbstractDiagramPane$Mode.a; 
    j();
  }
  
  private void g(MouseEvent paramMouseEvent) {
    a(paramMouseEvent.getX(), paramMouseEvent.getY(), true);
    if (d(paramMouseEvent)) {
      a(paramMouseEvent);
    } else {
      Unit unit = this.av;
      if (unit instanceof Attribute) {
        Attribute attribute = (Attribute)unit;
        c(attribute);
        if (e(paramMouseEvent)) {
          PainterColumnPart painterColumnPart = a(attribute, this.ao);
          switch (FxAbstractDiagramPane$1.b[painterColumnPart.ordinal()]) {
            case 1:
              attribute.setTicked(!attribute.isTicked());
              j();
              a();
              break;
            case 2:
              if (paramMouseEvent.getClickCount() == 2) {
                a(attribute, paramMouseEvent);
                break;
              } 
              if (this.av instanceof Entity) {
                b(paramMouseEvent);
                break;
              } 
              a(attribute, paramMouseEvent, PainterColumnPart.a);
              break;
            case 3:
              a(attribute, paramMouseEvent, PainterColumnPart.d);
              break;
            case 4:
              a(attribute, paramMouseEvent, PainterColumnPart.c);
              break;
          } 
        } 
      } else if (this.av instanceof Entity || this.av instanceof Callout || this.av instanceof Relation || this.av instanceof Group) {
        if (paramMouseEvent.getClickCount() == 2) {
          a(this.av, paramMouseEvent);
        } else if (this.av instanceof Entity || this.av instanceof Callout || this.av instanceof Relation) {
          b(paramMouseEvent);
        } 
      } else if (this.av == null && !d(paramMouseEvent) && !paramMouseEvent.isDragDetect()) {
        this.c.b();
      } 
    } 
    S();
    this.aC = FxAbstractDiagramPane$Mode.a;
  }
  
  private void h(MouseEvent paramMouseEvent) {
    a(paramMouseEvent.getX(), paramMouseEvent.getY(), false);
    this.e.clear();
    this.d.b();
    for (Relation relation : this.c.d) {
      this.d.a(relation.getTargetAttributes());
      this.d.a(relation.getSourceAttributes());
    } 
    this.g = null;
    this.h = PainterColumnPart.a;
    Callout callout = c(this.ao);
    Shape shape = d(this.ao);
    if (callout != null) {
      this.d.b(callout);
      this.d.b(callout.c);
    } else if (shape != null) {
      this.d.b(shape);
    } else {
      Depict depict;
      if ((depict = b(this.ao)) == null) {
        a((String)null, paramMouseEvent);
        if (this.aA > 0.4D) {
          for (Relation relation : this.a.findRelations(this.ao.a, this.ao.b, c())) {
            this.d.b(relation);
            this.d.a(relation.getTargetAttributes());
            this.d.a(relation.getSourceAttributes());
          } 
          if (this.d.d.size() == 1 && 
            O())
            a(ToolTipFactory.a(this.d.d.get(0)), paramMouseEvent); 
        } 
      } else {
        Attribute attribute = a(depict, this.ao);
        if (attribute == null) {
          this.d.b(depict.getParentEntity());
          this.g = depict.getParentEntity();
          if (O())
            a(b(depict, this.ao) ? "Double-click the table header to choose the visible columns" : ToolTipFactory.a(depict.getParentEntity()), paramMouseEvent); 
        } else {
          this.d.b(attribute);
          this.g = attribute;
          this.h = a(attribute, this.ao);
          for (Relation relation : attribute.getParentEntity().getRelations()) {
            if (relation.getSourceAttributes().contains(attribute)) {
              this.d.b(relation);
              if (!relation.isAutoReference())
                this.d.a(relation.getTargetAttributes()); 
            } 
          } 
          for (Relation relation : attribute.getParentEntity().getImportedRelations()) {
            if (relation.getTargetAttributes().contains(attribute)) {
              this.d.b(relation);
              if (!relation.isAutoReference())
                this.d.a(relation.getSourceAttributes()); 
            } 
          } 
          if (O()) {
            String str;
            switch (FxAbstractDiagramPane$1.b[this.h.ordinal()]) {
              case 4:
                a(ToolTipFactory.e(attribute), paramMouseEvent);
                break;
              case 2:
                a(ToolTipFactory.b(attribute), paramMouseEvent);
                break;
              case 3:
                str = (this.a.hasMarker(attribute, 4) ? "Foreign key line needs more space between tables to be drawn.\n" : "") + (this.a.hasMarker(attribute, 4) ? "Foreign key line needs more space between tables to be drawn.\n" : "");
                a(str, paramMouseEvent);
                break;
            } 
          } 
        } 
      } 
    } 
    j();
  }
  
  public void b(boolean paramBoolean) {
    if (paramBoolean) {
      this.b.setVisible(false);
    } else {
      this.b.setGraphic((Node)BootstrapIcons.exclamation_triangle_fill.glyph(new String[] { "glyph-orange" }));
      this.b.setOnAction(paramActionEvent -> this.an.d(getScene(), this.a.isJoinedRouting() ? "routingFailedJoined" : "routingFailed"));
      this.b.setVisible(true);
    } 
  }
  
  public void G() {
    this.a.undo();
    k();
  }
  
  public void H() {
    this.a.redo();
    k();
  }
  
  public List I() {
    UniqueArrayList uniqueArrayList = new UniqueArrayList();
    for (Unit unit : this.c.b)
      uniqueArrayList.add(this.a.getDepictFor(unit)); 
    return uniqueArrayList;
  }
  
  public void a(Pos paramPos) {
    double d1 = Double.MAX_VALUE, d2 = Double.MIN_VALUE, d3 = Double.MAX_VALUE, d4 = Double.MIN_VALUE;
    double d5 = Double.MAX_VALUE, d6 = Double.MIN_VALUE, d7 = Double.MAX_VALUE, d8 = Double.MIN_VALUE;
    for (Depict depict : I()) {
      d1 = Math.min(d1, depict.getPosition().a());
      d2 = Math.max(d2, depict.getPosition().a());
      d5 = Math.min(d5, depict.getPosition().a() + depict.getPosition().c());
      d6 = Math.max(d6, depict.getPosition().a() + depict.getPosition().c());
      d3 = Math.min(d3, depict.getPosition().b());
      d4 = Math.max(d4, depict.getPosition().b());
      d7 = Math.min(d7, depict.getPosition().b() + depict.getPosition().d());
      d8 = Math.max(d8, depict.getPosition().b() + depict.getPosition().d());
    } 
    for (Depict depict : I()) {
      switch (FxAbstractDiagramPane$1.c[paramPos.getHpos().ordinal()]) {
        case 1:
          depict.setLocation(d1, depict.getPosition().b());
          break;
        case 2:
          depict.setLocation(d5 - depict.getPosition().c(), depict.getPosition().b());
          break;
      } 
      switch (FxAbstractDiagramPane$1.d[paramPos.getVpos().ordinal()]) {
        case 1:
          depict.setLocation(depict.getPosition().a(), d3);
        case 2:
          depict.setLocation(depict.getPosition().a(), d7 - depict.getPosition().d());
      } 
    } 
    k();
  }
  
  public boolean J() {
    return this.a.canUndo.getValue().booleanValue();
  }
  
  public boolean K() {
    return this.a.canRedo.getValue().booleanValue();
  }
  
  public boolean L() {
    return (this.aC == FxAbstractDiagramPane$Mode.b);
  }
  
  public void M() {
    this.aC = FxAbstractDiagramPane$Mode.b;
  }
  
  public void N() {
    ArrayList arrayList = new ArrayList(this.c.b);
    UniqueArrayList uniqueArrayList = new UniqueArrayList();
    for (Entity entity : arrayList) {
      for (Relation relation : entity.getImportedRelations())
        uniqueArrayList.add(relation.getEntity()); 
      for (Relation relation : entity.getRelations())
        uniqueArrayList.add(relation.getTargetEntity()); 
    } 
    this.c.a(uniqueArrayList);
    j();
  }
  
  public abstract void a();
  
  public abstract void a(Attribute paramAttribute, MouseEvent paramMouseEvent, PainterColumnPart paramPainterColumnPart);
  
  public abstract void a(Unit paramUnit, MouseEvent paramMouseEvent);
  
  public abstract void a(Unit paramUnit1, Unit paramUnit2);
  
  public abstract void a(Entity paramEntity, Attribute paramAttribute);
  
  public abstract void a(Entity paramEntity, List paramList);
  
  public abstract void a(MouseEvent paramMouseEvent);
  
  public abstract void b(MouseEvent paramMouseEvent);
  
  public abstract boolean a(Attribute paramAttribute);
  
  public abstract boolean b();
  
  public abstract boolean c();
  
  public abstract String b(Attribute paramAttribute);
  
  public abstract List a(Entity paramEntity, Attribute paramAttribute, boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract void a(Entity paramEntity, Attribute paramAttribute1, Attribute paramAttribute2);
  
  public abstract Notation C();
  
  public abstract void D();
  
  public abstract void a(Point paramPoint);
}
