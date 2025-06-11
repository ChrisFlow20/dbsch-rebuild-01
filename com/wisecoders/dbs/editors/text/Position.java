package com.wisecoders.dbs.editors.text;

import org.eclipse.lsp4j.Position;

public class Position extends Position {
  public Position() {}
  
  public Position(int paramInt1, int paramInt2) {
    setLine(paramInt1);
    setCharacter(paramInt2);
  }
  
  public Position(Position paramPosition) {
    setLine(paramPosition.getLine());
    setCharacter(paramPosition.getCharacter());
  }
  
  public void a(Position paramPosition) {
    setLine(paramPosition.getLine());
    setCharacter(paramPosition.getCharacter());
    a();
  }
  
  public void a(int paramInt1, int paramInt2) {
    setLine(paramInt1);
    setCharacter(paramInt2);
    a();
  }
  
  public void b(int paramInt1, int paramInt2) {
    setLine(getLine() + paramInt1);
    setCharacter(getCharacter() + paramInt2);
    a();
  }
  
  public void a() {}
  
  public boolean b(Position paramPosition) {
    return (getLine() > paramPosition.getLine() || (getLine() == paramPosition.getLine() && getCharacter() > paramPosition.getCharacter()));
  }
  
  public boolean c(Position paramPosition) {
    return (getLine() > paramPosition.getLine() || (getLine() == paramPosition.getLine() && getCharacter() >= paramPosition.getCharacter()));
  }
  
  public String toString() {
    return "" + getLine() + 1 + ":" + getLine() + 1;
  }
  
  public boolean d(Position paramPosition) {
    return (getLine() == paramPosition.getLine() && getCharacter() == paramPosition.getCharacter());
  }
}
