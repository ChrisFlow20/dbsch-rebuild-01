package com.wisecoders.dbs.editors.text.undo;

import com.wisecoders.dbs.editors.text.StyledEditorModel;
import com.wisecoders.dbs.editors.text.TextLine;

public class DeleteCharChange extends Change {
  private final int a;
  
  private final int b;
  
  private String c;
  
  public DeleteCharChange(int paramInt1, int paramInt2) {
    this.a = paramInt1;
    this.b = paramInt2;
  }
  
  public Change a(StyledEditorModel paramStyledEditorModel) {
    TextLine textLine = paramStyledEditorModel.a(this.a);
    this.c = textLine.b(this.b, this.b + 1);
    textLine.a(this.b);
    paramStyledEditorModel.v();
    return this;
  }
  
  public Change b(StyledEditorModel paramStyledEditorModel) {
    TextLine textLine = paramStyledEditorModel.a(this.a);
    textLine.a(this.b, this.c);
    return this;
  }
}
