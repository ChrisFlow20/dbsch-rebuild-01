package com.wisecoders.dbs.editors.text.undo;

import com.wisecoders.dbs.editors.text.StyledEditorModel;
import com.wisecoders.dbs.editors.text.TextLine;

public class InsertCharsChange extends Change {
  private final int a;
  
  private final int b;
  
  private final String c;
  
  public InsertCharsChange(int paramInt1, int paramInt2, String paramString) {
    this.a = paramInt1;
    this.b = paramInt2;
    this.c = paramString;
  }
  
  public Change a(StyledEditorModel paramStyledEditorModel) {
    TextLine textLine = paramStyledEditorModel.a(this.a);
    textLine.a(this.b, this.c);
    paramStyledEditorModel.v();
    return this;
  }
  
  public Change b(StyledEditorModel paramStyledEditorModel) {
    TextLine textLine = paramStyledEditorModel.a(this.a);
    textLine.a(this.b, this.c.length());
    return this;
  }
}
