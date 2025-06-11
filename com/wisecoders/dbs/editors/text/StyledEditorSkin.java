package com.wisecoders.dbs.editors.text;

import com.wisecoders.dbs.diagram.fx.VirtualCanvas;
import com.wisecoders.dbs.sys.DelayedTimeline;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.ContextMenu$;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.SkinBase;
import javafx.scene.control.Tooltip;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class StyledEditorSkin extends SkinBase {
  protected final VirtualCanvas a = new StyledEditorSkin$1(this);
  
  private static final int l = 5;
  
  private static final int m = 3000;
  
  private Pattern n;
  
  private DefaultLexer o;
  
  private boolean p = false;
  
  private c q;
  
  private double r;
  
  private double s;
  
  private boolean t = false;
  
  private String u;
  
  public final DelayedTimeline b = new DelayedTimeline(Duration.seconds(0.49D));
  
  private long v = 0L;
  
  private long w = 0L;
  
  private boolean x = true;
  
  private final Position y = new Position();
  
  private final Tooltip z = new Tooltip();
  
  public final StyledEditorCompletionMenu c = new StyledEditorCompletionMenu(this);
  
  protected StyledEditorCompletion d;
  
  private double A;
  
  private int B;
  
  private static final int C = 30;
  
  private int D;
  
  private double E;
  
  private double F;
  
  private double G;
  
  private int H;
  
  private Color I;
  
  private boolean J;
  
  private boolean K;
  
  private TokenType L;
  
  public static final double e = 0.65D;
  
  public static final double f = 0.65D;
  
  public static final double g = -1.0D;
  
  public static final double h = 2.0D;
  
  private double M;
  
  private final HashMap N;
  
  private final Text O;
  
  private static final char P = '�';
  
  private static final double Q = 0.1D;
  
  private Font R;
  
  private Font S;
  
  private final StringBuilder T;
  
  private boolean U;
  
  protected final StyledEditorFind i;
  
  protected final a j;
  
  protected final StyledEditorGoToLine k;
  
  private final ContextMenu$ V;
  
  public StyledEditorSkin(StyledEditor paramStyledEditor) {
    super(paramStyledEditor);
    this.A = 0.0D;
    this.B = -1;
    this.D = -1;
    this.M = 12.0D;
    this.N = new HashMap<>();
    this.O = new Text();
    this.T = new StringBuilder();
    this.U = false;
    this.i = new StyledEditorFind((StyledEditor)getSkinnable());
    this.j = new a((StyledEditor)getSkinnable());
    this.k = new StyledEditorGoToLine((StyledEditor)getSkinnable());
    this.V = new ContextMenu$();
    new StyledEditorBehaviour(this);
    Platform.runLater(this::j);
    double d = ((Font)paramStyledEditor.d.getValue()).getSize() + ((Number)paramStyledEditor.e.getValue()).intValue();
    this.a.ap.setUnitIncrement(d);
    this.a.aq.setUnitIncrement(d);
    this.a.aq.setBlockIncrement(7.0D * d);
    this.b.a(() -> {
        
        });
    this.b.a(() -> {
          p();
          long l = System.currentTimeMillis();
          if (l - this.v > 250L && paramStyledEditor.isFocused() && paramStyledEditor.isVisible() && paramStyledEditor.getScene() != null) {
            this.x = !this.x;
            this.v = l;
            z();
            A().w();
          } 
          if (this.d != null && paramStyledEditor.isVisible() && System.currentTimeMillis() > A().y() + 1000L && A().y() > this.w) {
            this.d.a((StyledEditor)getSkinnable(), this.c, StyledEditorCompletion$Mode.a, true);
            this.w = System.currentTimeMillis();
          } 
          if (paramStyledEditor.getScene() != null) {
            this.t = true;
          } else if (this.t) {
            w();
          } 
        });
    ((StyledEditor)getSkinnable()).focusedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          q();
          i();
        });
    ((StyledEditor)getSkinnable()).visibleProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          if (paramBoolean2.booleanValue())
            x(); 
        });
    this.a.ar.addEventFilter(MouseEvent.MOUSE_PRESSED, paramMouseEvent -> {
          a(paramMouseEvent.getX(), paramMouseEvent.getY(), (A()).b, true);
          this.r = paramMouseEvent.getX();
          this.s = paramMouseEvent.getY();
          (A()).b.a();
          if (MouseButton.PRIMARY.equals(paramMouseEvent.getButton())) {
            A().d(paramMouseEvent.isAltDown());
            if (paramMouseEvent.getClickCount() == 3) {
              TextLine textLine = (A()).a.get((A()).b.getLine());
              Position position1 = new Position((A()).b.getLine(), 0);
              Position position2 = new Position((A()).b.getLine(), textLine.c());
              A().b(position1, position2);
            } else if (paramMouseEvent.getClickCount() == 2 && !A().m()) {
              TextLine textLine = (A()).a.get((A()).b.getLine());
              Position position1 = new Position((A()).b.getLine(), textLine.h((A()).b.getCharacter()));
              Position position2 = new Position((A()).b.getLine(), textLine.g((A()).b.getCharacter()));
              A().b(position1, position2);
              (A()).b.a(position2);
            } else if (paramMouseEvent.isShiftDown() || paramMouseEvent.isAltDown()) {
              (A()).c.a((A()).b);
            } else {
              A().b((A()).b, (A()).b);
            } 
          } 
          q();
          i();
        });
    this.a.ar.addEventFilter(MouseEvent.MOUSE_RELEASED, paramMouseEvent -> this.a.ar.setCursor(Cursor.TEXT));
    this.a.ar.addEventFilter(MouseEvent.MOUSE_DRAGGED, paramMouseEvent -> {
          a(paramMouseEvent.getX(), paramMouseEvent.getY(), (A()).c, true);
          if (paramMouseEvent.isSecondaryButtonDown()) {
            this.a.ar.setCursor(Cursor.HAND);
            this.a.d(this.r - paramMouseEvent.getX());
            this.a.c(this.s - paramMouseEvent.getY());
            this.r = paramMouseEvent.getX();
            this.s = paramMouseEvent.getY();
          } else {
            double d = 15.0D;
            if (paramMouseEvent.getY() > this.a.getHeight() - this.a.getHeight() / 10.0D)
              this.a.c(30.0D); 
            if (paramMouseEvent.getY() < this.a.getHeight() / 10.0D)
              this.a.c(-30.0D); 
            if (paramMouseEvent.getX() > this.a.getWidth() - this.a.getWidth() / 10.0D)
              this.a.d(15.0D); 
            if (paramMouseEvent.getX() < this.a.getWidth() / 10.0D)
              this.a.d(-15.0D); 
            (A()).c.a();
            (A()).b.a((A()).c);
            A().d(paramMouseEvent.isAltDown());
          } 
          q();
        });
    this.a.addEventFilter(MouseEvent.MOUSE_PRESSED, paramMouseEvent -> {
          if (paramMouseEvent.isSecondaryButtonDown()) {
            this.V.a(((StyledEditor)getSkinnable()).L, new String[] { "copy", "paste", "cut", "separator", "find", "replace", "goToLine", "separator", "undo", "redo" });
            this.V.show((Node)this.a, paramMouseEvent.getScreenX(), paramMouseEvent.getScreenY());
            paramMouseEvent.consume();
          } else {
            this.V.hide();
          } 
        });
    this.a.ar.setOnDragOver(paramDragEvent -> {
          if (paramDragEvent.getGestureSource() != this.a && paramDragEvent.getDragboard().hasString()) {
            paramDragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            paramDragEvent.consume();
          } 
        });
    this.a.ar.setOnDragDropped(paramDragEvent -> {
          if (paramDragEvent.getDragboard().hasString()) {
            Position position = new Position();
            a(paramDragEvent.getX(), paramDragEvent.getY(), position, true);
            A().a(position, paramDragEvent.getDragboard().getString());
            q();
          } 
          paramDragEvent.setDropCompleted(true);
          paramDragEvent.consume();
        });
    this.a.ar.setOnMouseMoved(paramMouseEvent -> {
          a(paramMouseEvent.getX(), paramMouseEvent.getY(), this.y, false);
          String str = A().d(this.y);
          if (str != null) {
            if (!this.z.isShowing() || !StringUtil.areEqual(this.z.getText(), str)) {
              this.z.setText(str);
              this.z.setAutoHide(true);
              this.z.show((Node)this.a.ar, paramMouseEvent.getScreenX() + 10.0D, paramMouseEvent.getScreenY() - this.z.getHeight() - 20.0D);
            } 
          } else if (this.z.isShowing()) {
            this.z.hide();
          } 
        });
    this.a.ar.setCursor(Cursor.TEXT);
    getChildren().add(this.a);
    q();
  }
  
  private void w() {
    this.b.a();
    A().a();
  }
  
  void a() {
    A().a();
    this.A = 0.0D;
    this.a.S();
  }
  
  boolean b() {
    if (A().i()) {
      this.a.S();
      q();
      return true;
    } 
    return false;
  }
  
  public void a(DefaultLexer paramDefaultLexer) {
    this.o = paramDefaultLexer;
    q();
  }
  
  void a(int paramInt) {
    if (paramInt > -1 && paramInt < (A()).a.size())
      this.a.b(g(paramInt)); 
  }
  
  int c() {
    return Math.max(0, Math.min(A().z(), (int)(this.a.U() / (((Font)((StyledEditor)getSkinnable()).d.getValue()).getSize() + ((Number)((StyledEditor)getSkinnable()).e.getValue()).intValue()))));
  }
  
  int d() {
    return (int)Math.ceil(this.a.getHeight() / (((Font)((StyledEditor)getSkinnable()).d.getValue()).getSize() + ((Number)((StyledEditor)getSkinnable()).e.getValue()).intValue()));
  }
  
  public int e() {
    return (A()).a.size();
  }
  
  void f() {
    double d1 = ((Font)((StyledEditor)getSkinnable()).d.getValue()).getSize();
    double d2 = ((Number)((StyledEditor)getSkinnable()).e.getValue()).doubleValue();
    if ((A()).b.getLine() < c() + 3) {
      this.a.c(-d1 - d2);
    } else if ((A()).b.getLine() > c() + d() - 3) {
      this.a.c(d1 + d2);
    } 
    double d3 = a((A()).b);
    if (d3 - this.a.T() > this.a.getWidth() - 20.0D) {
      this.a.d(d1);
    } else if (d3 - this.a.T() < 20.0D) {
      double d = Math.min(d3 - d1, this.a.T() - d1);
      this.a.d(d - this.a.T());
    } 
  }
  
  public void g() {
    double d1 = ((Font)((StyledEditor)getSkinnable()).d.getValue()).getSize();
    double d2 = ((Number)((StyledEditor)getSkinnable()).e.getValue()).doubleValue();
    if ((A()).b.getLine() < c() + 3 || (A()).b.getLine() > c() + d() - 3)
      this.a.e(((A()).b.getLine() - 5) * (d1 + d2)); 
    double d3 = a((A()).b);
    if (d3 - this.a.T() > this.a.getWidth() - 20.0D) {
      this.a.f(d3 - this.a.getWidth() + 120.0D);
    } else if (d3 - this.a.T() < 20.0D) {
      double d = Math.min(d3 - d1, this.a.T() - d1);
      this.a.f(d);
    } 
    q();
    i();
  }
  
  private void a(double paramDouble1, double paramDouble2, Position paramPosition, boolean paramBoolean) {
    paramPosition.setLine(Math.max(0, Math.min(A().z(), (int)((this.a.U() + paramDouble2) / (((Font)((StyledEditor)getSkinnable()).d.getValue()).getSize() + ((Number)((StyledEditor)getSkinnable()).e.getValue()).intValue())))));
    double d = 5.0D;
    A().c(paramPosition);
    paramPosition.setCharacter(0);
    TextLine textLine = (A()).a.get(paramPosition.getLine());
    int i = 0, j = 0;
    boolean bool = true;
    while (bool) {
      boolean bool1 = (i < textLine.c()) ? textLine.e(i) : true;
      int k = Character.charCount(bool1);
      i += k;
      double d1 = a(bool1, j);
      if (d + d1 < paramDouble1 + this.a.T() + b(32) / 2.0D) {
        paramPosition.setCharacter(paramPosition.getCharacter() + 1);
      } else {
        bool = false;
      } 
      if (paramBoolean && i >= textLine.c())
        bool = false; 
      j += (9 == bool1) ? c(j) : k;
      d += d1;
    } 
  }
  
  private int c(int paramInt) {
    return A().x() - paramInt % A().x();
  }
  
  public void a(String paramString) {
    GraphicsContext graphicsContext = this.a.ar.a();
    if (graphicsContext != null) {
      graphicsContext.setFontSmoothingType(FontSmoothingType.LCD);
      graphicsContext.setImageSmoothing(false);
      graphicsContext.setFill((Paint)((StyledEditor)getSkinnable()).h.getValue());
      graphicsContext.fillRect(0.0D, 0.0D, this.a.getWidth(), this.a.getHeight());
      graphicsContext.setFill((Paint)((StyledEditor)getSkinnable()).n.getValue());
      graphicsContext.fillText(paramString, (this.a.getWidth() + 1.0D) / 2.0D, d((int)((this.a.getHeight() + 1.0D) / 2.0D)));
    } 
  }
  
  private void x() {
    double d1 = ((Font)((StyledEditor)getSkinnable()).d.getValue()).getSize();
    double d2 = ((Number)((StyledEditor)getSkinnable()).e.getValue()).intValue();
    int i = c();
    Position position = new Position();
    this.E = -1.0D;
    this.F = -1.0D;
    GraphicsContext graphicsContext = this.a.ar.a();
    if (graphicsContext == null)
      return; 
    graphicsContext.setFontSmoothingType(FontSmoothingType.LCD);
    graphicsContext.setImageSmoothing(false);
    graphicsContext.setFill((Paint)((StyledEditor)getSkinnable()).h.getValue());
    graphicsContext.fillRect(0.0D, 0.0D, this.a.getWidth(), this.a.getHeight());
    b(graphicsContext);
    Token token = null;
    int j = -1;
    if (this.o != null) {
      this.n = null;
      if (!A().m()) {
        String str = this.o.b(A(), (A()).b);
        this.n = (str != null) ? Pattern.compile(Pattern.quote(str)) : null;
      } 
      this.o.a(A(), i);
    } 
    double d3 = 0.0D;
    this.E = -1.0D;
    this.F = -1.0D;
    Pattern pattern = ((StyledEditor)getSkinnable()).f();
    for (int k = i; k < i + d() && k < (A()).a.size(); k++) {
      position.setLine(k);
      int m = (int)((position.getLine() + 1) * (d1 + d2) - this.a.U());
      boolean bool = (position.getLine() == (A()).b.getLine()) ? true : false;
      double d = 5.0D;
      position.setCharacter(0);
      TextLine textLine = (A()).a.get(position.getLine());
      int n = textLine.c();
      Matcher matcher1 = null, matcher2 = null;
      int i1 = -1, i2 = -1, i3 = -1, i4 = -1;
      if (pattern != null && this.a.getTop() != null) {
        matcher1 = pattern.matcher(textLine.b());
        if (matcher1.find()) {
          i1 = matcher1.start();
          i2 = matcher1.end();
        } 
      } 
      if (this.n != null && n < 3000) {
        matcher2 = this.n.matcher(textLine.b());
        if (matcher2.find()) {
          i3 = matcher2.start();
          i4 = matcher2.end();
        } 
      } 
      int i5 = 0, i6 = 0;
      while (i5 < n) {
        int i7 = textLine.e(i5);
        int i8 = Character.charCount(i7);
        i5 += i8;
        boolean bool1 = A().b(position);
        boolean bool2 = A().c(position.getLine(), i5);
        double d4 = a(i7, i6);
        i6 += (9 == i7) ? c(i6) : i8;
        if (this.o != null && n < 3000) {
          token = this.o.h();
          if (token != null && j != token.b)
            j = token.b; 
        } 
        if (d >= this.a.T() && d - this.a.T() < this.a.getWidth()) {
          Color color = (Color)((StyledEditor)getSkinnable()).h.getValue();
          if (bool1) {
            color = (Color)((StyledEditor)getSkinnable()).r.getValue();
          } else if (i1 < i5 && i5 <= i2) {
            color = (Color)((StyledEditor)getSkinnable()).j.getValue();
          } else if (bool) {
            color = (Color)((StyledEditor)getSkinnable()).i.getValue();
          } else if (i3 < i5 && i5 <= i4) {
            color = (Color)((StyledEditor)getSkinnable()).k.getValue();
          } 
          a(graphicsContext, d - Math.round(this.a.T()), m, i7, d4 + 0.65D, d2, (token != null) ? token.a : null, bool1, bool2, color, textLine.e(), textLine.f());
          if (bool && position.getCharacter() == (A()).b.getCharacter()) {
            this.E = d - this.a.T();
            this.F = m;
            this.G = d4;
            this.H = i7;
            this.L = (token != null) ? token.a : null;
            this.I = color;
            this.J = bool2;
            this.K = bool1;
          } 
        } 
        d += d4;
        position.setCharacter(position.getCharacter() + 1);
        if (this.o != null) {
          this.o.g();
          if (i5 == textLine.c() - 1)
            textLine.a(this.o.i()); 
        } 
        if (matcher1 != null && i1 > -1 && i2 < position.getCharacter())
          if (matcher1.find()) {
            i1 = matcher1.start();
            i2 = matcher1.end();
          } else {
            i1 = i2 = -1;
          }  
        if (matcher2 != null && i3 > -1 && i4 < position.getCharacter())
          if (matcher2.find()) {
            i3 = matcher2.start();
            i4 = matcher2.end();
          } else {
            i3 = i4 = -1;
          }  
        d3 = Math.max(d3, d);
      } 
      if (this.o != null)
        this.o.g(); 
      if (bool) {
        graphicsContext.setFill((Paint)((StyledEditor)getSkinnable()).i.getValue());
        graphicsContext.fillRect(d - this.a.T(), m - d1, this.a.getWidth() - d + this.a.T(), d1 + d2);
      } 
      if (this.p)
        a(graphicsContext, d - this.a.T(), m, 10, b(32) + 0.65D, d2, (TokenType)null, false, false, bool ? (Color)((StyledEditor)getSkinnable()).i.getValue() : (Color)((StyledEditor)getSkinnable()).h.getValue(), false, false); 
      if (A().c(position.getLine(), i5 + 1))
        a(graphicsContext, d - this.a.T(), m, 32, b(32) + 0.65D, d2, (token != null) ? token.a : null, false, true, (Color)null, textLine.e(), textLine.f()); 
      if (bool && position.getCharacter() == (A()).b.getCharacter()) {
        this.E = d - this.a.T();
        this.F = m;
        this.H = 10;
        this.G = b(32);
        this.I = (Color)((StyledEditor)getSkinnable()).i.getValue();
        this.J = false;
        this.K = false;
        this.L = null;
      } 
    } 
    a(graphicsContext);
    this.A = Math.max(this.A, d3 + 30.0D);
    if (this.a.ap.getMax() < this.A)
      this.a.R(); 
    if (this.q != null) {
      this.q.a();
      y();
    } 
  }
  
  private void y() {
    int i = Math.min(3, (int)(Math.log10(e()) + 1.0D));
    if (this.B != i) {
      this.q.setPrefWidth(8.0D + i * ((Font)((StyledEditor)getSkinnable()).d.getValue()).getSize());
      this.a.requestLayout();
      this.B = i;
    } 
  }
  
  private void a(GraphicsContext paramGraphicsContext) {
    if (A().c() && this.u != null) {
      paramGraphicsContext.setFont((Font)((StyledEditor)getSkinnable()).d.getValue());
      paramGraphicsContext.setFill((Paint)((StyledEditor)getSkinnable()).n.getValue());
      paramGraphicsContext.setStroke((Paint)((StyledEditor)getSkinnable()).n.getValue());
      paramGraphicsContext.fillText(this.u, 12.0D, d((int)(((Font)((StyledEditor)getSkinnable()).d.getValue()).getSize() + ((Number)((StyledEditor)getSkinnable()).e.getValue()).intValue())));
    } 
  }
  
  private void b(GraphicsContext paramGraphicsContext) {
    if (this.D > -1) {
      paramGraphicsContext.setStroke((Paint)((StyledEditor)getSkinnable()).s.getValue());
      double d1 = (30 - this.D) * this.a.getWidth() / 30.0D, d2 = (30 - this.D) * this.a.getHeight() / 30.0D;
      paramGraphicsContext.strokeRect(d1, d2, this.a.getWidth() - 2.0D * d1, this.a.getHeight() - 2.0D * d2);
      this.D--;
    } 
  }
  
  void h() {
    if (this.D == -1) {
      this.D = 30;
      Timeline timeline = new Timeline(new KeyFrame[] { new KeyFrame(Duration.millis(20.0D), paramActionEvent -> x(), new javafx.animation.KeyValue[0]) });
      timeline.setCycleCount(31);
      timeline.play();
    } 
  }
  
  private synchronized void z() {
    GraphicsContext graphicsContext = this.a.ar.a();
    if (graphicsContext != null && this.E > 0.0D && this.F > 0.0D) {
      a(graphicsContext, this.E, (int)this.F, this.H, this.G - 0.65D, ((Number)((StyledEditor)getSkinnable()).e.getValue()).intValue(), this.L, this.K, this.J, this.I, false, false);
      if (this.x) {
        graphicsContext.setStroke((Paint)((StyledEditor)getSkinnable()).g.getValue());
        if (A().t()) {
          graphicsContext.setFill((Paint)Color.RED);
          graphicsContext.fillRect(this.E, this.F - ((Font)((StyledEditor)getSkinnable()).d.getValue()).getSize(), this.M, 0.0D);
        } else {
          graphicsContext.setLineWidth(1.8D);
          graphicsContext.setLineCap(StrokeLineCap.ROUND);
          graphicsContext.strokeLine(this.E + 0.65D + 1.0D, this.F + 2.0D - ((Font)((StyledEditor)getSkinnable()).d.getValue()).getSize(), this.E + 0.65D + 1.0D, this.F + ((Number)((StyledEditor)getSkinnable()).e.getValue()).intValue() - 4.0D);
        } 
      } 
    } 
  }
  
  private void a(GraphicsContext paramGraphicsContext, double paramDouble1, int paramInt1, int paramInt2, double paramDouble2, double paramDouble3, TokenType paramTokenType, boolean paramBoolean1, boolean paramBoolean2, Color paramColor, boolean paramBoolean3, boolean paramBoolean4) {
    Color color;
    if (paramColor != null) {
      paramGraphicsContext.setFill((Paint)paramColor);
      paramGraphicsContext.fillRect(paramDouble1 + 0.65D, paramInt1 - ((Font)((StyledEditor)getSkinnable()).d.getValue()).getSize() - 0.65D, paramDouble2, ((Font)((StyledEditor)getSkinnable()).d.getValue()).getSize() + paramDouble3 - 0.65D);
    } 
    if (paramBoolean2) {
      paramGraphicsContext.setStroke(paramBoolean1 ? (Paint)((StyledEditor)getSkinnable()).q.getValue() : (Paint)((StyledEditor)getSkinnable()).m.getValue());
      paramGraphicsContext.setLineWidth(1.0D);
      boolean bool = true;
      double d;
      for (d = 0.0D; d < paramDouble2; d += 2.0D) {
        paramGraphicsContext.strokeLine(paramDouble1 + d, paramInt1 + -1.0D + paramDouble3 + (bool ? -2.0D : 0.0D) - 0.65D, paramDouble1 + d + 2.0D, paramInt1 + -1.0D + paramDouble3 + (bool ? 0.0D : -2.0D) - 0.65D);
        bool = !bool ? true : false;
      } 
    } 
    if (32 == paramInt2 || 9 == paramInt2 || 10 == paramInt2) {
      if (this.p && !paramBoolean1) {
        paramGraphicsContext.setFill((Paint)((StyledEditor)getSkinnable()).H.getValue());
        switch (paramInt2) {
          case 32:
            paramGraphicsContext.fillText("·", paramDouble1, d(paramInt1));
            break;
          case 10:
            paramGraphicsContext.fillText("¶", paramDouble1, d(paramInt1));
            break;
          case 9:
            paramGraphicsContext.fillText("»", paramDouble1, d(paramInt1));
            break;
        } 
      } 
    } else if (f(paramInt2)) {
      paramGraphicsContext.setFill((Paint)((StyledEditor)getSkinnable()).I.getValue());
      paramGraphicsContext.fillText("�", paramDouble1, d(paramInt1));
    } 
    paramGraphicsContext.setFont((Font)((StyledEditor)getSkinnable()).d.getValue());
    if (paramBoolean1 && ((Boolean)((StyledEditor)getSkinnable()).o.getValue()).booleanValue()) {
      color = (Color)((StyledEditor)getSkinnable()).p.getValue();
    } else if (paramBoolean3) {
      color = (Color)((StyledEditor)getSkinnable()).l.getValue();
      paramGraphicsContext.setFont(this.S);
    } else if (paramBoolean4) {
      color = (Color)((StyledEditor)getSkinnable()).m.getValue();
      paramGraphicsContext.setFont(this.S);
    } else if (paramTokenType == null) {
      color = (Color)((StyledEditor)getSkinnable()).g.getValue();
    } else {
      switch (StyledEditorSkin$2.a[paramTokenType.ordinal()]) {
        case 1:
        case 2:
          color = (Color)((StyledEditor)getSkinnable()).w.getValue();
          paramGraphicsContext.setFont(((Boolean)((StyledEditor)getSkinnable()).x.getValue()).booleanValue() ? this.S : (Font)((StyledEditor)getSkinnable()).d.getValue());
          break;
        case 3:
          paramGraphicsContext.setFont(this.R);
          color = (Color)((StyledEditor)getSkinnable()).A.getValue();
          break;
        case 4:
          paramGraphicsContext.setFont(this.R);
          color = (Color)((StyledEditor)getSkinnable()).B.getValue();
          break;
        case 5:
          color = (Color)((StyledEditor)getSkinnable()).y.getValue();
          break;
        case 6:
          color = (Color)((StyledEditor)getSkinnable()).z.getValue();
          break;
        case 7:
        case 8:
        case 9:
          color = (Color)((StyledEditor)getSkinnable()).F.getValue();
          break;
        case 10:
        case 11:
          color = (Color)((StyledEditor)getSkinnable()).C.getValue();
          break;
        case 12:
          color = (Color)((StyledEditor)getSkinnable()).G.getValue();
          break;
        case 13:
          color = (Color)((StyledEditor)getSkinnable()).E.getValue();
          break;
        case 14:
          color = (Color)((StyledEditor)getSkinnable()).D.getValue();
          break;
        default:
          color = (Color)((StyledEditor)getSkinnable()).g.getValue();
          break;
      } 
    } 
    if (paramBoolean2) {
      color = paramBoolean1 ? (Color)((StyledEditor)getSkinnable()).q.getValue() : (Color)((StyledEditor)getSkinnable()).m.getValue();
      paramGraphicsContext.setFont(this.S);
    } 
    paramGraphicsContext.setFill((Paint)color);
    paramGraphicsContext.fillText(Character.toString(paramInt2), paramDouble1, d(paramInt1));
  }
  
  private static double d(int paramInt) {
    return paramInt;
  }
  
  void i() {
    this.v = System.currentTimeMillis();
    this.x = true;
    z();
  }
  
  private double e(int paramInt) {
    this.O.setFont((Font)((StyledEditor)getSkinnable()).d.getValue());
    this.O.setText(Character.toString(paramInt));
    this.O.applyCss();
    return this.O.getBoundsInLocal().getWidth();
  }
  
  double b(int paramInt) {
    if (this.N.containsKey(Integer.valueOf(paramInt)))
      return ((Double)this.N.get(Integer.valueOf(paramInt))).doubleValue(); 
    double d = e(paramInt);
    this.N.put(Integer.valueOf(paramInt), Double.valueOf(d));
    return d;
  }
  
  private double a(int paramInt1, int paramInt2) {
    if (9 == paramInt1)
      return c(paramInt2) * b(32); 
    double d = b(paramInt1);
    return (13 != paramInt1 && d < 0.1D) ? b(65533) : d;
  }
  
  private boolean f(int paramInt) {
    return (13 != paramInt && 9 != paramInt && b(paramInt) < 0.1D);
  }
  
  private double a(Position paramPosition) {
    TextLine textLine = A().a(paramPosition.getLine());
    double d = 0.0D;
    int i = 0, j = 0;
    while (i < paramPosition.getCharacter() && i < textLine.c()) {
      int k = textLine.e(i);
      int m = Character.charCount(k);
      i += m;
      d += a(k, j);
      j += (9 == k) ? c(j) : m;
    } 
    return d;
  }
  
  private double g(int paramInt) {
    TextLine textLine = A().a(paramInt);
    double d = 0.0D;
    int i = 0, j = 0;
    while (i < textLine.c()) {
      int k = textLine.e(i);
      int m = Character.charCount(k);
      i += m;
      d += a(k, j);
      j += (9 == k) ? c(j) : m;
    } 
    return d;
  }
  
  protected void j() {
    this.N.clear();
    this.S = Font.font(((Font)((StyledEditor)getSkinnable()).d.getValue()).getFamily(), FontWeight.SEMI_BOLD, ((Font)((StyledEditor)getSkinnable()).d.getValue()).getSize());
    this.R = Font.font(((Font)((StyledEditor)getSkinnable()).d.getValue()).getFamily(), FontPosture.ITALIC, ((Font)((StyledEditor)getSkinnable()).d.getValue()).getSize());
    A().b(((Number)((StyledEditor)getSkinnable()).f.getValue()).intValue());
    this.M = b(97);
  }
  
  public double k() {
    return this.E;
  }
  
  public double l() {
    return this.F + ((Font)((StyledEditor)getSkinnable()).d.getValue()).getSize() + ((Number)((StyledEditor)getSkinnable()).e.getValue()).intValue();
  }
  
  public void a(boolean paramBoolean) {
    this.p = paramBoolean;
    q();
  }
  
  public boolean m() {
    return this.p;
  }
  
  public void n() {
    this.a.S();
    g();
    q();
  }
  
  public void a(Position paramPosition1, Position paramPosition2) {
    A().b(paramPosition1, paramPosition2);
    q();
  }
  
  public void b(Position paramPosition1, Position paramPosition2) {
    A().b(paramPosition1, paramPosition2);
  }
  
  protected void o() {
    this.A = 0.0D;
    this.a.S();
    q();
  }
  
  void b(String paramString) {
    this.T.append(paramString);
  }
  
  protected void p() {
    if (!this.T.isEmpty()) {
      ((StyledEditor)getSkinnable()).a(this.T.toString(), StyledEditorHighlight.a);
      this.T.delete(0, this.T.length());
    } 
  }
  
  public void c(String paramString) {
    this.u = paramString;
  }
  
  public final synchronized void q() {
    if (((StyledEditor)getSkinnable()).isVisible() && ((StyledEditor)getSkinnable()).getScene() != null)
      if (Platform.isFxApplicationThread()) {
        this.U = false;
        x();
      } else {
        if (!this.U)
          Platform.runLater(() -> {
                this.U = false;
                x();
              }); 
        this.U = true;
      }  
  }
  
  private StyledEditorModel A() {
    return ((StyledEditor)getSkinnable()).K;
  }
  
  public void dispose() {
    super.dispose();
    getChildren().removeAll((Object[])new Node[0]);
  }
  
  protected void layoutChildren(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    super.layoutChildren(paramDouble1, paramDouble2, paramDouble3, paramDouble4);
    q();
  }
  
  public void r() {
    this.a.setTop((Node)this.j.a(A().n()));
    this.j.a();
  }
  
  public void s() {
    this.a.setTop((Node)this.k);
    this.k.a();
  }
  
  public void b(boolean paramBoolean) {
    if (paramBoolean) {
      if (this.a.getLeft() == null) {
        c c1 = new c(this);
        this.a.setLeft((Node)c1);
        a(c1);
      } 
    } else {
      this.a.setLeft(null);
      a((c)null);
    } 
  }
  
  void a(c paramc) {
    this.q = paramc;
  }
  
  void t() {
    this.a.setTop(null);
    this.a.requestFocus();
  }
  
  public void u() {
    this.a.setTop((Node)this.i.a(A().n()));
    this.i.a();
  }
  
  public void a(StyledEditorCompletion paramStyledEditorCompletion) {
    this.d = paramStyledEditorCompletion;
    this.w = 0L;
  }
  
  public StyledEditorCompletion v() {
    return this.d;
  }
}
