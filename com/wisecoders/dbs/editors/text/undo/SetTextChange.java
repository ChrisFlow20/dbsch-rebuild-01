package com.wisecoders.dbs.editors.text.undo;

import com.wisecoders.dbs.editors.text.StyledEditorModel;
import com.wisecoders.dbs.editors.text.TextLine;

public class SetTextChange extends Change {
  private final int a;
  
  private final String b;
  
  private String c;
  
  public SetTextChange(int paramInt, String paramString) {
    this.a = paramInt;
    this.b = paramString;
  }
  
  public Change a(StyledEditorModel paramStyledEditorModel) {
    TextLine textLine = paramStyledEditorModel.a(this.a);
    this.c = textLine.b();
    textLine.a(this.b);
    paramStyledEditorModel.v();
    return this;
  }
  
  public Change b(StyledEditorModel paramStyledEditorModel) {
    TextLine textLine = paramStyledEditorModel.a(this.a);
    textLine.a(this.c);
    return this;
  }
}
