package com.wisecoders.dbs.editors.text;

import com.wisecoders.dbs.sys.fx.ControlBehaviour;
import java.util.function.Predicate;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

public class StyledEditorBehaviour extends ControlBehaviour {
  final StyledEditorSkin a;
  
  private final Predicate b;
  
  private final Predicate c;
  
  private StyledEditor a() {
    return (StyledEditor)this.a.getSkinnable();
  }
  
  private void b(KeyEvent paramKeyEvent) {
    (a()).K.b.a();
    this.a.q();
    this.a.i();
    paramKeyEvent.consume();
  }
  
  public StyledEditorBehaviour(StyledEditorSkin paramStyledEditorSkin) {
    this.b = (paramKeyEvent -> 
      
      ((!paramKeyEvent.isControlDown() && !paramKeyEvent.isMetaDown()) || (StyledEditor.b && !paramKeyEvent.isMetaDown() && paramKeyEvent.isAltDown())));
    this.c = (paramKeyEvent -> 
      (paramKeyEvent.getCode().isLetterKey() || paramKeyEvent.getCode().isDigitKey() || paramKeyEvent.getCode().isWhitespaceKey()));
    this.a = paramStyledEditorSkin;
    put((K)new KeyCodeCombination(KeyCode.C, new KeyCombination.Modifier[] { KeyCombination.SHORTCUT_DOWN }), (V)(paramKeyEvent -> {
          a().b();
          b(paramKeyEvent);
        }));
    put((K)new KeyCodeCombination(KeyCode.SPACE, new KeyCombination.Modifier[] { KeyCombination.CONTROL_DOWN }), (V)(paramKeyEvent -> {
          if (paramStyledEditorSkin.v() != null) {
            paramStyledEditorSkin.v().a((StyledEditor)paramStyledEditorSkin.getSkinnable(), paramStyledEditorSkin.c, StyledEditorCompletion$Mode.b, false);
            b(paramKeyEvent);
          } 
        }));
    put((K)new KeyCodeCombination(KeyCode.P, new KeyCombination.Modifier[] { KeyCombination.CONTROL_DOWN }), (V)(paramKeyEvent -> {
          if (paramStyledEditorSkin.v() != null) {
            paramStyledEditorSkin.v().a((StyledEditor)paramStyledEditorSkin.getSkinnable(), paramStyledEditorSkin.c, StyledEditorCompletion$Mode.c, false);
            b(paramKeyEvent);
          } 
        }));
    put((K)new KeyCodeCombination(KeyCode.A, new KeyCombination.Modifier[] { KeyCombination.SHORTCUT_DOWN }), (V)(paramKeyEvent -> {
          (a()).K.o();
          b(paramKeyEvent);
        }));
    put((K)new KeyCodeCombination(KeyCode.V, new KeyCombination.Modifier[] { KeyCombination.SHORTCUT_DOWN }), (V)(paramKeyEvent -> {
          a().c();
          b(paramKeyEvent);
        }));
    put((K)new KeyCodeCombination(KeyCode.X, new KeyCombination.Modifier[] { KeyCombination.SHORTCUT_DOWN }), (V)(paramKeyEvent -> {
          a().clipboardCut();
          b(paramKeyEvent);
        }));
    put((K)new KeyCodeCombination(KeyCode.D, new KeyCombination.Modifier[] { KeyCombination.SHORTCUT_DOWN }), (V)(paramKeyEvent -> (a()).K.r()));
    put((K)new KeyCodeCombination(KeyCode.Z, new KeyCombination.Modifier[] { KeyCombination.SHORTCUT_DOWN }), (V)(paramKeyEvent -> {
          (a()).K.p();
          paramStyledEditorSkin.g();
          b(paramKeyEvent);
        }));
    put((K)new KeyCodeCombination(KeyCode.BACK_SPACE, new KeyCombination.Modifier[] { KeyCombination.ALT_DOWN }), (V)(paramKeyEvent -> {
          (a()).K.p();
          paramStyledEditorSkin.g();
          b(paramKeyEvent);
        }));
    put((K)new KeyCodeCombination(KeyCode.Z, new KeyCombination.Modifier[] { KeyCombination.SHORTCUT_DOWN, KeyCodeCombination.SHIFT_DOWN }), (V)(paramKeyEvent -> {
          (a()).K.q();
          paramStyledEditorSkin.g();
          b(paramKeyEvent);
        }));
    put((K)new KeyCodeCombination(KeyCode.Y, new KeyCombination.Modifier[] { KeyCombination.SHORTCUT_DOWN }), (V)(paramKeyEvent -> {
          (a()).K.q();
          paramStyledEditorSkin.g();
          b(paramKeyEvent);
        }));
    put((K)new KeyCodeCombination(KeyCode.U, new KeyCombination.Modifier[] { KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN }), (V)(paramKeyEvent -> {
          a().toggleCase();
          b(paramKeyEvent);
        }));
    put((K)new KeyCodeCombination(KeyCode.UP, new KeyCombination.Modifier[] { KeyCombination.SHORTCUT_DOWN }), (V)(paramKeyEvent -> {
          paramStyledEditorSkin.a.c(-10.0D);
          b(paramKeyEvent);
        }));
    put((K)new KeyCodeCombination(KeyCode.DOWN, new KeyCombination.Modifier[] { KeyCombination.SHORTCUT_DOWN }), (V)(paramKeyEvent -> {
          paramStyledEditorSkin.a.c(10.0D);
          b(paramKeyEvent);
        }));
    put((K)new KeyCodeCombination(KeyCode.RIGHT, new KeyCombination.Modifier[] { KeyCombination.SHORTCUT_DOWN }), (V)(paramKeyEvent -> {
          a(false);
          b(paramKeyEvent);
        }));
    put((K)new KeyCodeCombination(KeyCode.RIGHT, new KeyCombination.Modifier[] { KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN }), (V)(paramKeyEvent -> {
          a(true);
          b(paramKeyEvent);
        }));
    put((K)new KeyCodeCombination(KeyCode.LEFT, new KeyCombination.Modifier[] { KeyCombination.SHORTCUT_DOWN }), (V)(paramKeyEvent -> {
          b(false);
          b(paramKeyEvent);
        }));
    put((K)new KeyCodeCombination(KeyCode.LEFT, new KeyCombination.Modifier[] { KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN }), (V)(paramKeyEvent -> {
          b(true);
          b(paramKeyEvent);
        }));
    put((K)new KeyCodeCombination(KeyCode.HOME, new KeyCombination.Modifier[] { KeyCombination.SHORTCUT_DOWN }), (V)(paramKeyEvent -> {
          e(false);
          b(paramKeyEvent);
        }));
    put((K)new KeyCodeCombination(KeyCode.END, new KeyCombination.Modifier[] { KeyCombination.SHORTCUT_DOWN }), (V)(paramKeyEvent -> {
          f(false);
          b(paramKeyEvent);
        }));
    put((K)new KeyCodeCombination(KeyCode.HOME, new KeyCombination.Modifier[] { KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN }), (V)(paramKeyEvent -> {
          e(true);
          b(paramKeyEvent);
        }));
    put((K)new KeyCodeCombination(KeyCode.END, new KeyCombination.Modifier[] { KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN }), (V)(paramKeyEvent -> {
          f(true);
          b(paramKeyEvent);
        }));
    put((K)new KeyCodeCombination(KeyCode.R, new KeyCombination.Modifier[] { KeyCombination.SHORTCUT_DOWN }), (V)(paramKeyEvent -> paramStyledEditorSkin.r()));
    put((K)new KeyCodeCombination(KeyCode.F, new KeyCombination.Modifier[] { KeyCombination.SHORTCUT_DOWN }), (V)(paramKeyEvent -> paramStyledEditorSkin.u()));
    put((K)new KeyCodeCombination(KeyCode.G, new KeyCombination.Modifier[] { KeyCombination.SHORTCUT_DOWN }), (V)(paramKeyEvent -> paramStyledEditorSkin.s()));
    put((K)new KeyCodeCombination(KeyCode.ESCAPE, new KeyCombination.Modifier[0]), (V)(paramKeyEvent -> ((StyledEditor)paramStyledEditorSkin.getSkinnable()).m()));
    put((K)new KeyCodeCombination(KeyCode.F3, new KeyCombination.Modifier[0]), (V)(paramKeyEvent -> ((StyledEditor)paramStyledEditorSkin.getSkinnable()).a(true, true, true, true)));
    put((K)new KeyCodeCombination(KeyCode.F3, new KeyCombination.Modifier[] { KeyCombination.SHIFT_DOWN }), (V)(paramKeyEvent -> ((StyledEditor)paramStyledEditorSkin.getSkinnable()).a(false, true, true, true)));
    ((StyledEditor)paramStyledEditorSkin.getSkinnable()).addEventHandler(KeyEvent.KEY_PRESSED, this::a);
    ((StyledEditor)paramStyledEditorSkin.getSkinnable()).addEventHandler(KeyEvent.KEY_TYPED, this::d);
    ((StyledEditor)paramStyledEditorSkin.getSkinnable()).addEventHandler(KeyEvent.KEY_PRESSED, this::c);
  }
  
  private void c(KeyEvent paramKeyEvent) {
    if (paramKeyEvent.getCode() == KeyCode.TAB) {
      d(paramKeyEvent);
      paramKeyEvent.consume();
    } 
    if (paramKeyEvent.isShortcutDown())
      return; 
    switch (StyledEditorBehaviour$1.a[paramKeyEvent.getCode().ordinal()]) {
      case 1:
        c(false);
        break;
      case 2:
        d(false);
        break;
      case 3:
        if (paramKeyEvent.isControlDown()) {
          this.a.a.c(-10.0D);
          return;
        } 
        (a()).K.b.b(-1, 0);
        break;
      case 4:
        if (this.a.v() != null && this.a.c.isShowing())
          this.a.c.requestFocus(); 
        if (paramKeyEvent.isControlDown()) {
          this.a.a.c(10.0D);
          return;
        } 
        (a()).K.b.b(1, 0);
        break;
      case 5:
        (a()).K.b.a(false);
        break;
      case 6:
        (a()).K.b.a(true);
        break;
      case 7:
        (a()).K.b.b(-this.a.d(), 0);
        this.a.g();
        break;
      case 8:
        (a()).K.b.b(this.a.d(), 0);
        this.a.g();
        break;
      case 9:
        if ((a()).K.u()) {
          this.a.b();
          (a()).K.f();
          this.a.a.S();
          break;
        } 
        b();
        break;
      case 10:
        (a()).K.b((a()).K.b, (a()).K.b);
        break;
      case 11:
        if ((a()).K.u() && !this.a.b()) {
          (a()).K.g();
          this.a.a((a()).K.b.getLine());
        } 
        break;
      case 12:
        if ((a()).K.u() && !this.a.b()) {
          (a()).K.h();
          this.a.a((a()).K.b.getLine());
        } 
        break;
      default:
        return;
    } 
    (a()).K.b.a();
    if (paramKeyEvent.isShiftDown() && paramKeyEvent.getCode() != KeyCode.DELETE && paramKeyEvent.getCode() != KeyCode.BACK_SPACE) {
      (a()).K.c.a((a()).K.b);
    } else {
      (a()).K.b((a()).K.b, (a()).K.b);
    } 
    this.a.f();
    this.a.q();
    this.a.i();
    paramKeyEvent.consume();
  }
  
  private void d(KeyEvent paramKeyEvent) {
    if (!(a()).K.u()) {
      b();
      return;
    } 
    if (paramKeyEvent.getCode() == KeyCode.TAB) {
      if ((a()).K.m()) {
        if (paramKeyEvent.isShiftDown()) {
          (a()).K.e();
        } else {
          (a()).K.d();
        } 
      } else {
        a(" ".repeat((a()).K.x()));
      } 
    } else if (this.b.and(paramKeyEvent -> b(paramKeyEvent.getCharacter())).and(paramKeyEvent -> !Character.isSupplementaryCodePoint(paramKeyEvent.getCode().getCode())).test(paramKeyEvent)) {
      a(paramKeyEvent.getCharacter());
    } 
    this.a.f();
    this.a.q();
    this.a.i();
    paramKeyEvent.consume();
  }
  
  private void b() {
    (a()).L.d(a().getScene(), "readOnlyNotification");
  }
  
  private void a(String paramString) {
    this.a.c.hide();
    this.a.b();
    (a()).K.b(paramString);
    (a()).K.b((a()).K.b, (a()).K.b);
    this.a.a((a()).K.b.getLine());
  }
  
  private void a(boolean paramBoolean) {
    int i = ((TextLine)(a()).K.a.get((a()).K.b.getLine())).g((a()).K.b.getCharacter());
    (a()).K.b.a((a()).K.b.getLine(), i);
    if (paramBoolean) {
      (a()).K.c.a((a()).K.b);
    } else {
      (a()).K.b((a()).K.b, (a()).K.b);
    } 
    this.a.g();
  }
  
  private void b(boolean paramBoolean) {
    (a()).K.b.a((a()).K.b.getLine(), ((TextLine)(a()).K.a.get((a()).K.b.getLine())).h((a()).K.b.getCharacter()));
    if (paramBoolean) {
      (a()).K.c.a((a()).K.b);
    } else {
      (a()).K.b((a()).K.b, (a()).K.b);
    } 
    this.a.g();
  }
  
  private static boolean b(String paramString) {
    int i = paramString.length();
    for (byte b = 0; b < i; b++) {
      if (Character.isISOControl(paramString.charAt(b)))
        return false; 
    } 
    return true;
  }
  
  private void c(boolean paramBoolean) {
    if (paramBoolean)
      (a()).K.b((a()).K.b, new Position((a()).K.b.getLine(), 0)); 
    (a()).K.b.a((a()).K.b.getLine(), 0);
    this.a.g();
  }
  
  private void d(boolean paramBoolean) {
    if (paramBoolean)
      (a()).K.b((a()).K.b, new Position((a()).K.b.getLine(), 2147483647)); 
    (a()).K.b.a((a()).K.b.getLine(), 2147483647);
    this.a.g();
  }
  
  private void e(boolean paramBoolean) {
    if (paramBoolean)
      (a()).K.b((a()).K.b, new Position(0, 0)); 
    (a()).K.b.a(0, 0);
    this.a.g();
  }
  
  private void f(boolean paramBoolean) {
    if (paramBoolean)
      (a()).K.b((a()).K.b, new Position((a()).K.z(), 2147483647)); 
    (a()).K.b.a((a()).K.z(), 2147483647);
    this.a.g();
  }
}
