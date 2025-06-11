package com.wisecoders.dbs.editors.text;

import com.wisecoders.dbs.editors.text.undo.CaretChange;
import com.wisecoders.dbs.editors.text.undo.Change;
import com.wisecoders.dbs.editors.text.undo.DeleteCharChange;
import com.wisecoders.dbs.editors.text.undo.InsertCharsChange;
import com.wisecoders.dbs.editors.text.undo.InsertLineChange;
import com.wisecoders.dbs.editors.text.undo.RemoveLineChange;
import com.wisecoders.dbs.editors.text.undo.SetTextChange;
import com.wisecoders.dbs.sys.StringUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;

public class StyledEditorModel {
  final List a = new ArrayList();
  
  public final StyledEditorModel$ModelPosition b = new StyledEditorModel$ModelPosition(this);
  
  private final Position g = new StyledEditorModel$ModelPosition(this);
  
  final Position c = new StyledEditorModel$ModelPosition(this);
  
  private boolean h = false;
  
  private final Map i = new HashMap<>();
  
  private boolean j = true;
  
  private boolean k = false;
  
  private int l = 4;
  
  private boolean m = false;
  
  final List d = new ArrayList();
  
  private long n = 0L;
  
  private final List o;
  
  private final List p;
  
  public final SimpleBooleanProperty e;
  
  public final SimpleBooleanProperty f;
  
  private boolean q;
  
  private static final int r = 128;
  
  TextLine a(Position paramPosition) {
    return this.a.get(paramPosition.getLine());
  }
  
  public TextLine a(int paramInt) {
    return this.a.get(paramInt);
  }
  
  void a() {
    this.i.clear();
    this.a.clear();
    this.a.add(new TextLine(""));
    this.o.clear();
    this.p.clear();
    this.b.a(0, 0);
  }
  
  void a(boolean paramBoolean) {
    this.m = paramBoolean;
  }
  
  public boolean b() {
    return this.m;
  }
  
  void b(int paramInt) {
    this.l = paramInt;
  }
  
  Reader c(int paramInt) {
    return new StyledEditorModel$CodeModelReader(this, paramInt, 2147483647);
  }
  
  Reader a(int paramInt1, int paramInt2) {
    return new StyledEditorModel$CodeModelReader(this, paramInt1, paramInt2);
  }
  
  boolean c() {
    return (this.a.size() == 1 && ((TextLine)this.a.get(0)).a());
  }
  
  private void A() {
    this.n = System.currentTimeMillis();
  }
  
  void a(File paramFile, Charset paramCharset) {
    this.a.clear();
    this.o.clear();
    this.p.clear();
    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(paramFile), paramCharset));
    try {
      String str;
      while ((str = bufferedReader.readLine()) != null)
        this.a.add(new TextLine(str)); 
      bufferedReader.close();
      if (this.a.isEmpty())
        this.a.add(new TextLine("")); 
      A();
      bufferedReader.close();
    } catch (Throwable throwable) {
      try {
        bufferedReader.close();
      } catch (Throwable throwable1) {
        throwable.addSuppressed(throwable1);
      } 
      throw throwable;
    } 
  }
  
  void a(String paramString) {
    this.a.clear();
    this.o.clear();
    this.p.clear();
    if (paramString != null) {
      BufferedReader bufferedReader = new BufferedReader(new StringReader(paramString));
      String str;
      while ((str = bufferedReader.readLine()) != null)
        this.a.add(new TextLine(str)); 
      bufferedReader.close();
    } 
    if (this.a.isEmpty())
      this.a.add(new TextLine("")); 
    A();
  }
  
  void d() {
    for (int i = k().getLine(); i <= l().getLine(); i++) {
      if (i == l().getLine()) {
        if (l().getCharacter() == 0)
          break; 
        this.c.setCharacter(this.c.getCharacter() + 1);
      } 
      TextLine textLine = a(i);
      b(i, "\t" + textLine.b());
      if (this.b.getLine() == i)
        this.b.setCharacter(this.b.getCharacter() + 1); 
    } 
    A();
  }
  
  void e() {
    for (int i = k().getLine(); i <= l().getLine() && (
      i != l().getLine() || l().getCharacter() != 0); i++) {
      TextLine textLine = a(i);
      String str = textLine.b();
      if (str.startsWith("\t")) {
        str = str.substring(1);
        if (i == l().getLine())
          l().setCharacter(l().getCharacter() - 1); 
        if (this.b.getLine() == i)
          this.b.setCharacter(this.b.getCharacter() - 1); 
      } else {
        for (byte b = 0; b < this.l; b++) {
          if (str.startsWith(" "))
            str = str.substring(1); 
          if (i == l().getLine())
            l().setCharacter(l().getCharacter() - 1); 
          if (this.b.getLine() == i)
            this.b.setCharacter(this.b.getCharacter() - 1); 
        } 
      } 
      b(i, str);
    } 
    A();
  }
  
  void b(String paramString) {
    a(this.b.getLine(), this.b.getCharacter(), paramString);
    f(this.b.getLine(), this.b.getCharacter() + paramString.length());
    A();
  }
  
  void f() {
    TextLine textLine = a(this.b);
    a(this.b.getLine() + 1, textLine.c(this.b.getCharacter()), StyledEditorHighlight.a);
    b(this.b.getLine(), textLine.b(this.b.getCharacter()));
    f(this.b.getLine() + 1, 0);
    A();
  }
  
  void g() {
    TextLine textLine = a(this.b);
    if (this.b.getCharacter() > 0) {
      e(this.b.getLine(), this.b.getCharacter() - 1);
      f(this.b.getLine(), this.b.getCharacter() - 1);
    } else if (this.b.getLine() > 0) {
      TextLine textLine1 = this.a.get(this.b.getLine() - 1);
      int i = textLine1.b().length();
      b(this.b.getLine() - 1, textLine1.b() + textLine1.b());
      e(this.b.getLine());
      f(this.b.getLine() - 1, i);
    } 
    A();
  }
  
  void h() {
    TextLine textLine = a(this.b);
    if (this.b.getCharacter() < textLine.b().length()) {
      e(this.b.getLine(), this.b.getCharacter());
    } else if (this.b.getLine() + 1 < this.a.size()) {
      TextLine textLine1 = this.a.get(this.b.getLine() + 1);
      b(this.b.getLine(), textLine.b() + textLine.b());
      e(this.b.getLine() + 1);
    } 
    A();
  }
  
  boolean i() {
    if (this.j && m()) {
      a(k(), l());
      l().a(k());
      return true;
    } 
    return false;
  }
  
  void a(Position paramPosition1, Position paramPosition2) {
    TextLine textLine1 = this.a.get(paramPosition1.getLine());
    TextLine textLine2 = this.a.get(paramPosition2.getLine());
    if (paramPosition1.getLine() == paramPosition2.getLine() && paramPosition1.getCharacter() < paramPosition2.getCharacter()) {
      b(paramPosition1.getLine(), textLine1.b(paramPosition1.getCharacter()) + textLine1.b(paramPosition1.getCharacter()));
    } else if (paramPosition1.getLine() < paramPosition2.getLine()) {
      b(paramPosition1.getLine(), textLine1.b(paramPosition1.getCharacter()) + textLine1.b(paramPosition1.getCharacter()));
      for (int i = paramPosition1.getLine() + 1; i <= paramPosition2.getLine(); i++)
        e(paramPosition1.getLine() + 1); 
    } 
    f(paramPosition1.getLine(), paramPosition1.getCharacter());
    A();
  }
  
  void a(Position paramPosition, String paramString) {
    a(paramPosition, paramString, StyledEditorHighlight.a);
  }
  
  void a(Position paramPosition, String paramString, StyledEditorHighlight paramStyledEditorHighlight) {
    if (paramString != null) {
      TextLine textLine = this.a.get(paramPosition.getLine());
      String[] arrayOfString = StringUtil.splitTextInLines(paramString);
      String str1 = textLine.b(paramPosition.getCharacter());
      String str2 = textLine.c(paramPosition.getCharacter());
      if (arrayOfString.length == 1) {
        b(paramPosition.getLine(), str1 + str1 + arrayOfString[0]);
        f(paramPosition.getLine(), str1.length() + arrayOfString[arrayOfString.length - 1].length());
      } else if (arrayOfString.length > 1) {
        b(paramPosition.getLine(), str1 + str1);
        for (byte b = 1; b < arrayOfString.length - 1; b++)
          a(paramPosition.getLine() + b, arrayOfString[b], paramStyledEditorHighlight); 
        a(paramPosition.getLine() + arrayOfString.length - 1, arrayOfString[arrayOfString.length - 1] + arrayOfString[arrayOfString.length - 1], paramStyledEditorHighlight);
        f(paramPosition.getLine() + arrayOfString.length - 1, arrayOfString[arrayOfString.length - 1].length());
      } 
      A();
    } 
  }
  
  void a(String paramString, StyledEditorHighlight paramStyledEditorHighlight) {
    int i = z();
    Position position = new Position(i, a(i).c());
    a(position, paramString, paramStyledEditorHighlight);
  }
  
  void a(int paramInt, String paramString) {
    if (paramInt > -1 && paramInt < this.a.size())
      b(paramInt, paramString); 
  }
  
  String j() {
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b = 0; b < this.a.size(); b++) {
      if (b > 0)
        stringBuilder.append("\n"); 
      TextLine textLine = this.a.get(b);
      stringBuilder.append(textLine.b());
    } 
    return stringBuilder.toString();
  }
  
  String a(Position paramPosition1, Position paramPosition2, boolean paramBoolean) {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = paramPosition1.getLine(); i <= paramPosition2.getLine() && i < this.a.size(); i++) {
      if (i > paramPosition1.getLine())
        stringBuilder.append("\n"); 
      TextLine textLine = this.a.get(i);
      int j = textLine.b().length();
      if (paramBoolean) {
        int k = d(paramPosition1.getCharacter(), j);
        int m = d(paramPosition2.getCharacter(), j);
        stringBuilder.append(textLine.b(), k, m);
      } else {
        int k = d((i == paramPosition1.getLine()) ? paramPosition1.getCharacter() : 0, j);
        int m = d((i == paramPosition2.getLine()) ? paramPosition2.getCharacter() : j, j);
        stringBuilder.append(textLine.b(), k, m);
      } 
    } 
    return stringBuilder.toString();
  }
  
  private int d(int paramInt1, int paramInt2) {
    return Math.max(0, Math.min(paramInt1, paramInt2));
  }
  
  Position k() {
    this.g.a();
    this.c.a();
    return (this.g.getLine() < this.c.getLine() || (this.g.getLine() == this.c.getLine() && this.g.getCharacter() < this.c.getCharacter())) ? this.g : this.c;
  }
  
  Position l() {
    this.g.a();
    this.c.a();
    return (this.g.getLine() < this.c.getLine() || (this.g.getLine() == this.c.getLine() && this.g.getCharacter() < this.c.getCharacter())) ? this.c : this.g;
  }
  
  boolean b(Position paramPosition) {
    Position position1 = k();
    Position position2 = l();
    if (this.h)
      return (paramPosition.getLine() >= position1.getLine() && paramPosition.getLine() <= position2.getLine() && paramPosition
        .getCharacter() >= position1.getCharacter() && paramPosition.getCharacter() <= position2.getCharacter()); 
    return ((position1.getLine() < paramPosition.getLine() && paramPosition.getLine() < position2.getLine()) || (position1
      .getLine() == paramPosition.getLine() && position1.getLine() < position2.getLine() && position1.getCharacter() <= paramPosition.getCharacter()) || (position2
      .getLine() == paramPosition.getLine() && position1.getLine() < position2.getLine() && paramPosition.getCharacter() < position2.getCharacter()) || (position2
      .getLine() == position1.getLine() && paramPosition.getLine() == position2.getLine() && position1.getCharacter() <= paramPosition.getCharacter() && paramPosition.getCharacter() < position2.getCharacter()));
  }
  
  boolean m() {
    Position position1 = k();
    Position position2 = l();
    return ((position1.getLine() == position2.getLine() && position1.getCharacter() < position2.getCharacter()) || position1.getLine() < position2.getLine());
  }
  
  String n() {
    return a(k(), l(), this.h);
  }
  
  public void o() {
    this.h = false;
    this.g.a(0, 0);
    this.c.a(this.a.size() - 1, ((TextLine)this.a.get(this.a.size() - 1)).b().length());
  }
  
  public void a(int paramInt, TextLine paramTextLine) {
    this.a.add(paramInt, paramTextLine);
  }
  
  public void d(int paramInt) {
    this.a.remove(paramInt);
  }
  
  public void b(int paramInt1, int paramInt2) {
    this.b.a(paramInt1, paramInt2);
  }
  
  public StyledEditorModel() {
    this.o = new ArrayList();
    this.p = new ArrayList();
    this.e = new SimpleBooleanProperty(false);
    this.f = new SimpleBooleanProperty(false);
    this.q = false;
    this.a.clear();
    this.a.add(new TextLine(""));
  }
  
  public void b(boolean paramBoolean) {
    this.q = paramBoolean;
  }
  
  private void b(int paramInt, String paramString) {
    if (this.q) {
      a(paramInt).a(paramString);
    } else {
      Change change = (new SetTextChange(paramInt, paramString)).a(this);
      if (this.j)
        this.o.add(change); 
      B();
    } 
  }
  
  private void a(int paramInt1, int paramInt2, String paramString) {
    Change change = (new InsertCharsChange(paramInt1, paramInt2, paramString)).a(this);
    if (this.j)
      this.o.add(change); 
    B();
  }
  
  private void e(int paramInt1, int paramInt2) {
    Change change = (new DeleteCharChange(paramInt1, paramInt2)).a(this);
    if (this.j)
      this.o.add(change); 
    B();
  }
  
  private void a(int paramInt, String paramString, StyledEditorHighlight paramStyledEditorHighlight) {
    Change change = (new InsertLineChange(paramInt, paramString, paramStyledEditorHighlight)).a(this);
    if (this.j)
      this.o.add(change); 
    B();
  }
  
  private void e(int paramInt) {
    if (this.q) {
      d(paramInt);
    } else {
      Change change = (new RemoveLineChange(paramInt)).a(this);
      if (this.j)
        this.o.add(change); 
      B();
    } 
  }
  
  private void f(int paramInt1, int paramInt2) {
    if (this.q) {
      b(paramInt1, paramInt2);
    } else {
      Change change = (new CaretChange(paramInt1, paramInt2)).a(this);
      if (this.j)
        this.o.add(change); 
    } 
  }
  
  public void p() {
    if (!this.o.isEmpty()) {
      Change change1 = this.o.get(this.o.size() - 1);
      if (change1 instanceof CaretChange) {
        CaretChange caretChange = (CaretChange)change1;
        if (caretChange.a != this.b.getLine() || caretChange.b != this.b.getCharacter()) {
          this.b.a(caretChange.a, caretChange.b);
          return;
        } 
      } 
      long l = change1.e;
      Change change2;
      while (!this.o.isEmpty() && (change2 = this.o.get(this.o.size() - 1)).e > l - 500L) {
        change2.b(this);
        this.o.remove(change2);
        this.p.add(change2);
      } 
      this.b.a();
      B();
    } 
  }
  
  public void q() {
    if (!this.p.isEmpty()) {
      Change change1 = this.p.get(this.p.size() - 1);
      if (change1 instanceof CaretChange) {
        CaretChange caretChange = (CaretChange)change1;
        if (caretChange.c != this.b.getLine() || caretChange.d != this.b.getCharacter()) {
          this.b.a(caretChange.c, caretChange.d);
          return;
        } 
      } 
      long l = change1.e;
      Change change2;
      while (!this.p.isEmpty() && (change2 = this.p.get(this.p.size() - 1)).e < l + 500L) {
        change2.a(this);
        this.p.remove(change2);
        this.o.add(change2);
      } 
      this.b.a();
      B();
    } 
  }
  
  public void b(Position paramPosition1, Position paramPosition2) {
    this.h = false;
    this.g.a(paramPosition1);
    this.c.a(paramPosition2);
    this.b.a(paramPosition1);
  }
  
  public void a(String paramString, int paramInt) {
    this.h = false;
    this.g.a(this.b.getLine(), this.b.getCharacter() - paramInt);
    this.c.a(this.b.getLine(), this.b.getCharacter());
    a(this.g, this.c);
    a(this.g, paramString);
    this.g.a(this.c);
  }
  
  void c(String paramString) {
    if (m()) {
      a(this.g, this.c);
      a(this.g, paramString);
      this.h = false;
    } 
  }
  
  public void r() {
    a(this.b.getLine(), a(this.b.getLine()).b(), StyledEditorHighlight.a);
  }
  
  public Position s() {
    return this.b;
  }
  
  boolean t() {
    return this.k;
  }
  
  public void c(boolean paramBoolean) {
    this.j = paramBoolean;
  }
  
  public boolean u() {
    return this.j;
  }
  
  public int c(Position paramPosition1, Position paramPosition2) {
    int i = -paramPosition1.getCharacter();
    for (int j = paramPosition1.getLine(); j < paramPosition2.getLine(); j++) {
      i += ((TextLine)this.a.get(j)).c();
      i++;
    } 
    i += paramPosition2.getCharacter();
    return i;
  }
  
  Position a(long paramLong) {
    Position position = new Position();
    if (m())
      position.a(k()); 
    long l = paramLong;
    while (position.getLine() < z() && l > (((TextLine)this.a.get(position.getLine())).c() + 1)) {
      l -= (((TextLine)this.a.get(position.getLine())).c() + 1);
      position.b(1, 0);
    } 
    position.b(0, Math.min(((TextLine)this.a.get(position.getLine())).c() - 1, (int)l));
    return position;
  }
  
  public void v() {
    this.i.clear();
  }
  
  private void B() {
    this.m = true;
    w();
    for (ChangeListener changeListener : this.d)
      changeListener.changed(null, Integer.valueOf(0), Integer.valueOf(0)); 
  }
  
  void w() {
    this.e.set((this.o.size() > 0));
    this.f.set((this.p.size() > 0));
    if (this.o.size() > 128)
      this.o.remove(0); 
    if (this.p.size() > 128)
      this.p.remove(0); 
  }
  
  void c(Position paramPosition) {
    paramPosition.setLine(Math.max(0, Math.min(paramPosition.getLine(), z())));
    if (this.a.isEmpty()) {
      paramPosition.setCharacter(0);
    } else {
      paramPosition.setCharacter(Math.max(0, Math.min(paramPosition.getCharacter(), ((TextLine)this.a.get(paramPosition.getLine())).c())));
    } 
  }
  
  public int x() {
    return this.l;
  }
  
  public void d(boolean paramBoolean) {
    this.h = paramBoolean;
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString) {
    for (int i = paramInt1; i <= paramInt3 && i < this.a.size(); i++) {
      TextLine textLine = a(i);
      boolean bool = (i == paramInt1) ? paramInt2 : false;
      int j = (i == paramInt3) ? paramInt4 : textLine.c();
      a(i, bool, j, paramString);
    } 
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3, String paramString) {
    b b = (b)this.i.get(Integer.valueOf(paramInt1));
    if (paramInt1 < this.a.size())
      while (true) {
        if (b != null && b.a() == paramInt2) {
          b.a(paramString);
          return;
        } 
        boolean bool = (b != null && b.d() != null) ? true : false;
        if (bool)
          b = b.d(); 
        if (!bool) {
          b b1 = new b(paramInt2, paramInt3, paramString);
          if (b == null) {
            this.i.put(Integer.valueOf(paramInt1), b1);
            break;
          } 
          b.a(b1);
          break;
        } 
      }  
  }
  
  public boolean c(int paramInt1, int paramInt2) {
    b b = (b)this.i.get(Integer.valueOf(paramInt1));
    if (b != null)
      do {
        if (paramInt2 > b.a() && paramInt2 <= b.b())
          return true; 
      } while ((b = b.d()) != null); 
    return false;
  }
  
  public String d(Position paramPosition) {
    b b = (b)this.i.get(Integer.valueOf(paramPosition.getLine()));
    while (b != null) {
      if (b.a(paramPosition))
        return b.c(); 
      b = b.d();
    } 
    return null;
  }
  
  public long y() {
    return this.n;
  }
  
  public int z() {
    return this.a.size() - 1;
  }
}
