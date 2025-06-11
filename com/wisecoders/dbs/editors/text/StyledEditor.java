package com.wisecoders.dbs.editors.text;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.editors.text.lexers.JavaLexer;
import com.wisecoders.dbs.editors.text.lexers.JsonLexer;
import com.wisecoders.dbs.editors.text.lexers.SqlLexer;
import com.wisecoders.dbs.editors.text.lexers.XmlLexer;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.schema.Language;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.Rx$ScreenResolution;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.FxUtil;
import com.wisecoders.dbs.sys.fx.ResourceCallback;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.css.Styleable;
import javafx.css.StyleableProperty;
import javafx.css.StyleablePropertyFactory;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class StyledEditor extends Control {
  static final boolean a = Sys.k();
  
  static final boolean b = Sys.i();
  
  static final boolean c = Sys.j();
  
  private boolean M = b;
  
  private Language N;
  
  private static final StyleablePropertyFactory O = new StyleablePropertyFactory(Control.getClassCssMetaData());
  
  final StyleableProperty d;
  
  final StyleableProperty e;
  
  protected final StyleableProperty f;
  
  protected final StyleableProperty g;
  
  protected final StyleableProperty h;
  
  protected final StyleableProperty i;
  
  protected final StyleableProperty j;
  
  protected final StyleableProperty k;
  
  protected final StyleableProperty l;
  
  protected final StyleableProperty m;
  
  protected final StyleableProperty n;
  
  protected final StyleableProperty o;
  
  final StyleableProperty p;
  
  protected final StyleableProperty q;
  
  final StyleableProperty r;
  
  final StyleableProperty s;
  
  final StyleableProperty t;
  
  final StyleableProperty u;
  
  final StyleableProperty v;
  
  protected final StyleableProperty w;
  
  protected final StyleableProperty x;
  
  protected final StyleableProperty y;
  
  protected final StyleableProperty z;
  
  protected final StyleableProperty A;
  
  protected final StyleableProperty B;
  
  protected final StyleableProperty C;
  
  protected final StyleableProperty D;
  
  protected final StyleableProperty E;
  
  protected final StyleableProperty F;
  
  protected final StyleableProperty G;
  
  protected final StyleableProperty H;
  
  protected final StyleableProperty I;
  
  public final StyledEditorSkin J;
  
  public final StyledEditorModel K;
  
  private Charset P;
  
  private boolean Q;
  
  public final Rx L;
  
  private Pattern R;
  
  private static final String S = "autoPopupCompletion";
  
  private boolean T;
  
  public StyledEditor(Language paramLanguage, boolean paramBoolean) {
    (new String[3])[0] = "Menlo";
    (new String[3])[1] = "Monospaced";
    (new String[3])[2] = "System";
    (new String[4])[0] = "Liberation Mono";
    (new String[4])[1] = "Consolas";
    (new String[4])[2] = "Monospaced";
    (new String[4])[3] = "System";
    (new String[3])[0] = "Roboto Mono";
    (new String[3])[1] = "Monospaced";
    (new String[3])[2] = "System";
    this.d = O.createStyleableFontProperty((Styleable)this, "font", "font", paramStyledEditor -> paramStyledEditor.d, Font.font(FxUtil.a(a ? new String[3] : (b ? new String[4] : new String[3])), FxUtil.b * ((
          b && Rx.f() == Rx$ScreenResolution.c) ? 1.125D : 1.083333333333333D)));
    this.e = O.createStyleableNumberProperty((Styleable)this, "line-space", "line-space", paramStyledEditor -> paramStyledEditor.e, Integer.valueOf((b && Rx.f() == Rx$ScreenResolution.c) ? 8 : 6));
    this.f = O.createStyleableNumberProperty((Styleable)this, "tab-size", "tab-size", paramStyledEditor -> paramStyledEditor.f, Integer.valueOf(4));
    this.g = O.createStyleableColorProperty((Styleable)this, "foreground", "foreground", paramStyledEditor -> paramStyledEditor.g, Color.web("0x2f3136"));
    this.h = O.createStyleableColorProperty((Styleable)this, "background", "background", paramStyledEditor -> paramStyledEditor.h, Color.web("0xfafbfc"));
    this.i = O.createStyleableColorProperty((Styleable)this, "caret-line-background", "caret-line-background", paramStyledEditor -> paramStyledEditor.i, Color.web("0xf8f8f1"));
    this.j = O.createStyleableColorProperty((Styleable)this, "find-background", "find-background", paramStyledEditor -> paramStyledEditor.j, Color.web("0xade398"));
    this.k = O.createStyleableColorProperty((Styleable)this, "word-background", "word-background", paramStyledEditor -> paramStyledEditor.k, Color.web("0xf0fbf0"));
    this.l = O.createStyleableColorProperty((Styleable)this, "success-foreground", "success-foreground", paramStyledEditor -> paramStyledEditor.l, Color.web("0x0c821b"));
    this.m = O.createStyleableColorProperty((Styleable)this, "error-foreground", "error-foreground", paramStyledEditor -> paramStyledEditor.m, Color.web("0xbf0b00"));
    this.n = O.createStyleableColorProperty((Styleable)this, "prompt-text-foreground", "prompt-text-foreground", paramStyledEditor -> paramStyledEditor.n, Color.web("0xafb3b5"));
    this.o = O.createStyleableBooleanProperty((Styleable)this, "selection-use-foreground", "selection-use-foreground", paramStyledEditor -> paramStyledEditor.o, false);
    this.p = O.createStyleableColorProperty((Styleable)this, "selection-foreground", "selection-foreground", paramStyledEditor -> paramStyledEditor.p, Color.web("0x111213"));
    this.q = O.createStyleableColorProperty((Styleable)this, "selection-error-foreground", "selection-error-foreground", paramStyledEditor -> paramStyledEditor.q, Color.web("0xff8d85"));
    this.r = O.createStyleableColorProperty((Styleable)this, "selection-background", "selection-background", paramStyledEditor -> paramStyledEditor.r, Color.web("0xdfe4eb"));
    this.s = O.createStyleableColorProperty((Styleable)this, "ruler-foreground", "ruler-foreground", paramStyledEditor -> paramStyledEditor.s, Color.web("0xaaaaaa"));
    this.t = O.createStyleableColorProperty((Styleable)this, "ruler-background", "ruler-background", paramStyledEditor -> paramStyledEditor.t, Color.web("0xf1f2f3"));
    this.u = O.createStyleableColorProperty((Styleable)this, "ruler-selection-foreground", "ruler-selection-foreground", paramStyledEditor -> paramStyledEditor.u, Color.web("0x777777"));
    this.v = O.createStyleableColorProperty((Styleable)this, "ruler-selection-background", "ruler-selection-background", paramStyledEditor -> paramStyledEditor.v, Color.web("0xeaeaea"));
    this.w = O.createStyleableColorProperty((Styleable)this, "keyword-foreground", "keyword-foreground", paramStyledEditor -> paramStyledEditor.w, Color.web("#05309d"));
    this.x = O.createStyleableBooleanProperty((Styleable)this, "keyword-bold", "keyword-bold", paramStyledEditor -> paramStyledEditor.x, true);
    this.y = O.createStyleableColorProperty((Styleable)this, "string-foreground", "string-foreground", paramStyledEditor -> paramStyledEditor.y, Color.web("#256b21"));
    this.z = O.createStyleableColorProperty((Styleable)this, "string2-foreground", "string2-foreground", paramStyledEditor -> paramStyledEditor.z, Color.web("#256b21"));
    this.A = O.createStyleableColorProperty((Styleable)this, "comment-foreground", "comment-foreground", paramStyledEditor -> paramStyledEditor.A, Color.web("#635d57"));
    this.B = O.createStyleableColorProperty((Styleable)this, "comment2-foreground", "comment2-foreground", paramStyledEditor -> paramStyledEditor.A, Color.web("#3a6e42"));
    this.C = O.createStyleableColorProperty((Styleable)this, "reg-exp-foreground", "reg-exp-foreground", paramStyledEditor -> paramStyledEditor.C, Color.web("#660e7a"));
    this.D = O.createStyleableColorProperty((Styleable)this, "identifier-foreground", "identifier-foreground", paramStyledEditor -> paramStyledEditor.D, Color.web("#10151b"));
    this.E = O.createStyleableColorProperty((Styleable)this, "delimiter-foreground", "delimiter-foreground", paramStyledEditor -> paramStyledEditor.E, Color.web("#112549"));
    this.F = O.createStyleableColorProperty((Styleable)this, "type-foreground", "type-foreground", paramStyledEditor -> paramStyledEditor.F, Color.web("#5e0077"));
    this.G = O.createStyleableColorProperty((Styleable)this, "number-foreground", "number-foreground", paramStyledEditor -> paramStyledEditor.G, Color.web("#904708"));
    this.H = O.createStyleableColorProperty((Styleable)this, "special-char-foreground", "special-char-foreground", paramStyledEditor -> paramStyledEditor.H, Color.web("#e5db85"));
    this.I = O.createStyleableColorProperty((Styleable)this, "hidden-char-foreground", "hidden-char-foreground", paramStyledEditor -> paramStyledEditor.I, Color.web("#dd916e"));
    this.J = new StyledEditorSkin(this);
    this.K = new StyledEditorModel();
    this.Q = false;
    this.L = new Rx(StyledEditor.class, this);
    this.T = Pref.b("autoPopupCompletion", true);
    getStyleClass().add("code-editor");
    setMinSize(150.0D, 60.0D);
    styleProperty().addListener((paramObservableValue, paramString1, paramString2) -> {
          this.J.a.S();
          this.J.j();
          this.J.q();
        });
    a((paramObservableValue, paramInteger1, paramInteger2) -> this.L.b());
    a(paramLanguage);
    getStyleClass().add("text-area");
    setFocusTraversable(true);
    a(paramBoolean);
    addEventFilter(MouseEvent.MOUSE_PRESSED, paramMouseEvent -> requestFocus());
    Objects.requireNonNull(this.K.e);
    this.L.a("flagCanUndo", this.K.e::getValue);
    Objects.requireNonNull(this.K.f);
    this.L.a("flagCanRedo", this.K.f::getValue);
    this.L.b();
  }
  
  public StyledEditor() {
    this(Language.a, false);
  }
  
  public StyledEditor(boolean paramBoolean) {
    this(Language.a, paramBoolean);
  }
  
  protected Skin createDefaultSkin() {
    return (Skin)this.J;
  }
  
  public static List a() {
    return O.getCssMetaData();
  }
  
  public List getControlCssMetaData() {
    return O.getCssMetaData();
  }
  
  public Task a(File paramFile, ResourceCallback paramResourceCallback) {
    this.J.q();
    this.J.a("Please wait...");
    return new StyledEditor$1(this, paramFile, paramResourceCallback);
  }
  
  public Task a(File paramFile) {
    return new StyledEditor$2(this, paramFile);
  }
  
  boolean b(File paramFile) {
    BufferedReader bufferedReader = new BufferedReader(new FileReader(paramFile));
    try {
      boolean bool1 = false, bool2 = false;
      int i;
      while (!bool2 && (i = bufferedReader.read()) != -1) {
        if (i == 10)
          bool2 = true; 
        if (i == 13) {
          bool1 = true;
          bool2 = true;
        } 
      } 
      boolean bool3 = bool1;
      bufferedReader.close();
      return bool3;
    } catch (Throwable throwable) {
      try {
        bufferedReader.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
  }
  
  void b() {
    if (isVisible() && getScene() != null) {
      Clipboard clipboard = Clipboard.getSystemClipboard();
      ClipboardContent clipboardContent = new ClipboardContent();
      if (this.K.m()) {
        clipboardContent.putString(this.K.n());
        clipboard.setContent((Map)clipboardContent);
        this.J.h();
      } 
    } 
  }
  
  void c() {
    Clipboard clipboard = Clipboard.getSystemClipboard();
    if (isVisible() && getScene() != null && clipboard.hasString()) {
      this.J.b();
      String str = clipboard.getString();
      str = str.replaceAll("[\\p{C}&&[^\n\\s]]", "");
      this.K.a(this.K.b, str);
      this.J.a.S();
      this.J.g();
    } 
  }
  
  @Action
  public void clipboardCut() {
    b();
    this.J.b();
  }
  
  @Action
  public void toggleCase() {
    String str1 = d();
    String str2 = str1.trim();
    Position position1 = new Position(this.K.k()), position2 = new Position(this.K.l());
    if (StringUtil.isFilledTrim(str2)) {
      boolean bool = (str2.toLowerCase().charAt(0) == str2.charAt(0)) ? true : false;
      a(bool ? str1.toUpperCase() : str1.toLowerCase());
      this.K.b(position1, position2);
    } 
  }
  
  public void a(String paramString) {
    this.J.b();
    this.K.a(this.K.b, paramString);
    this.J.a.S();
    this.J.g();
  }
  
  public void b(String paramString) {
    try {
      this.J.a();
      this.K.a(paramString);
      this.J.a.S();
      this.J.q();
    } catch (IOException iOException) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
      alert.initOwner(getScene().getWindow());
      alert.setTitle("Error");
      alert.setHeaderText("Error setting text");
      alert.setContentText(iOException.getLocalizedMessage());
      alert.showAndWait();
    } 
  }
  
  public String d() {
    return this.K.n();
  }
  
  public void a(int paramInt, String paramString) {
    this.K.a(paramInt, paramString);
    this.J.a.S();
    this.J.q();
  }
  
  public Scene e() {
    return getScene();
  }
  
  void a(Pattern paramPattern) {
    this.R = paramPattern;
    this.J.q();
  }
  
  public Pattern f() {
    return this.R;
  }
  
  boolean a(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4) {
    Position position = new Position();
    position.a(this.K.b);
    boolean bool = true;
    while (this.R != null && bool) {
      Matcher matcher = this.R.matcher(this.K.a(position).b());
      int i = -1, j = -1;
      while (matcher.find()) {
        if ((paramBoolean1 && matcher.start() >= position.getCharacter() && i == -1) || (!paramBoolean1 && matcher.end() < position.getCharacter())) {
          i = matcher.start();
          j = matcher.end();
        } 
      } 
      if (i > -1) {
        this.K.b(new Position(position.getLine(), i), new Position(position.getLine(), j));
        this.K.b.a(position.getLine(), paramBoolean2 ? j : i);
        if (paramBoolean4)
          this.J.g(); 
        return true;
      } 
      if (paramBoolean1) {
        if (position.getLine() + 1 < this.K.a.size()) {
          position.a(position.getLine() + 1, 0);
        } else if (paramBoolean3) {
          position.a(0, 0);
        } else {
          bool = false;
        } 
      } else if (position.getLine() - 1 >= 0) {
        position.a(position.getLine() - 1, 2147483647);
      } else if (paramBoolean3) {
        position.a(this.K.z(), 2147483647);
      } else {
        bool = false;
      } 
      if (position.getLine() == this.K.b.getLine())
        bool = false; 
    } 
    this.K.b(this.K.b, this.K.b);
    return false;
  }
  
  @Action
  public void paste() {
    if (!this.J.i.c() && !this.J.j.c() && !this.J.k.c())
      c(); 
  }
  
  @Action
  public void copy() {
    if (!this.J.i.b() && !this.J.j.b() && !this.J.k.b())
      b(); 
  }
  
  @Action
  public void cut() {
    if (!this.J.i.d() && !this.J.j.d() && !this.J.k.d())
      clipboardCut(); 
  }
  
  @Action
  public void find() {
    this.J.u();
  }
  
  @Action(b = "flagCanUndo")
  public void undo() {
    this.K.p();
    this.J.n();
  }
  
  @Action(b = "flagCanRedo")
  public void redo() {
    this.K.q();
    this.J.n();
  }
  
  @Action
  public void replace() {
    this.J.r();
  }
  
  @Action
  public void goToLine() {
    this.J.s();
  }
  
  public int a(int paramInt) {
    this.K.b.a(paramInt - 1, 0);
    this.J.g();
    return this.K.b.getLine() + 1;
  }
  
  public void a(boolean paramBoolean) {
    this.J.b(paramBoolean);
  }
  
  public void c(String paramString) {
    this.J.c(paramString);
  }
  
  public String a(Position paramPosition1, Position paramPosition2) {
    return this.K.a(paramPosition1, paramPosition2, false);
  }
  
  public Position g() {
    return this.K.k();
  }
  
  public Position h() {
    return this.K.l();
  }
  
  public StyledEditorModel$ModelPosition i() {
    return this.K.b;
  }
  
  public int j() {
    return this.K.a.size();
  }
  
  public String b(int paramInt) {
    return (paramInt > -1 && paramInt < this.K.a.size()) ? this.K.a(paramInt).b() : null;
  }
  
  public int c(int paramInt) {
    return (paramInt > -1 && paramInt < this.K.a.size()) ? this.K.a(paramInt).c() : 0;
  }
  
  public boolean k() {
    return this.K.m();
  }
  
  public void l() {
    this.K.o();
  }
  
  public void d(String paramString) {
    a(paramString, StyledEditorHighlight.a);
  }
  
  public void a(String paramString, StyledEditorHighlight paramStyledEditorHighlight) {
    this.K.a(paramString, paramStyledEditorHighlight);
    this.J.n();
  }
  
  public void e(String paramString) {
    this.J.b(paramString);
  }
  
  void m() {
    this.J.t();
  }
  
  public void b(boolean paramBoolean) {
    this.M = paramBoolean;
  }
  
  public boolean n() {
    return this.M;
  }
  
  public void c(boolean paramBoolean) {
    this.K.a(paramBoolean);
  }
  
  public boolean o() {
    return this.K.b();
  }
  
  public int p() {
    return this.K.a.size();
  }
  
  public boolean q() {
    if (this.K.a.size() < 10) {
      for (byte b = 0; b < this.K.a.size(); b++) {
        if (!((TextLine)this.K.a.get(b)).a())
          return false; 
      } 
      return true;
    } 
    return false;
  }
  
  public Charset r() {
    return (this.P != null) ? this.P : Charset.defaultCharset();
  }
  
  public boolean s() {
    return this.T;
  }
  
  public void d(boolean paramBoolean) {
    this.T = paramBoolean;
    Pref.a("autoPopupCompletion", paramBoolean);
  }
  
  public String t() {
    return this.K.j();
  }
  
  public void e(boolean paramBoolean) {
    this.K.c(paramBoolean);
  }
  
  public boolean u() {
    return this.K.u();
  }
  
  public void f(boolean paramBoolean) {
    this.Q = paramBoolean;
  }
  
  public void a(Language paramLanguage) {
    this.N = paramLanguage;
    switch (StyledEditor$3.a[paramLanguage.ordinal()]) {
      case 1:
      case 2:
        this.J.a(new JsonLexer());
        break;
      case 3:
        this.J.a(new JavaLexer());
        break;
      case 4:
        this.J.a(this.Q ? new JsonLexer() : new SqlLexer());
        break;
      case 5:
        this.J.a(new XmlLexer());
        break;
    } 
  }
  
  public Language v() {
    return this.N;
  }
  
  public void w() {
    this.K.v();
    this.J.q();
  }
  
  public void b(int paramInt, String paramString) {
    Position position = this.K.a(paramInt);
    a(position.getLine(), position.getCharacter(), position.getCharacter() + 1, paramString, true);
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3, String paramString, boolean paramBoolean) {
    this.K.a(paramInt1, paramInt2, paramInt3, paramString);
    if (paramBoolean) {
      this.K.b.a(paramInt1, paramInt2);
      this.J.g();
    } 
  }
  
  public void a(ChangeListener paramChangeListener) {
    this.K.d.add(paramChangeListener);
  }
  
  public void x() {
    this.K.a();
    this.J.o();
  }
}
