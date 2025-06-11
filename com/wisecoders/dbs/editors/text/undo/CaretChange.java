package com.wisecoders.dbs.editors.text.undo;

import com.wisecoders.dbs.editors.text.StyledEditorModel;

public class CaretChange extends Change {
  public final int a;
  
  public final int b;
  
  public int c;
  
  public int d;
  
  public CaretChange(int paramInt1, int paramInt2) {
    this.a = paramInt1;
    this.b = paramInt2;
  }
  
  public Change a(StyledEditorModel paramStyledEditorModel) {
    this.c = paramStyledEditorModel.s().getLine();
    this.d = paramStyledEditorModel.s().getCharacter();
    paramStyledEditorModel.b(this.a, this.b);
    return this;
  }
  
  public Change b(StyledEditorModel paramStyledEditorModel) {
    paramStyledEditorModel.b(this.c, this.d);
    return this;
  }
}
