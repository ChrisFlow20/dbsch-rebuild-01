package com.wisecoders.dbs.editors.text.undo;

import com.wisecoders.dbs.editors.text.StyledEditorModel;
import com.wisecoders.dbs.editors.text.TextLine;

public class RemoveLineChange extends Change {
  private final int a;
  
  private String b;
  
  public RemoveLineChange(int paramInt) {
    this.a = paramInt;
  }
  
  public Change a(StyledEditorModel paramStyledEditorModel) {
    this.b = paramStyledEditorModel.a(this.a).b();
    paramStyledEditorModel.d(this.a);
    paramStyledEditorModel.v();
    return this;
  }
  
  public Change b(StyledEditorModel paramStyledEditorModel) {
    paramStyledEditorModel.a(this.a, new TextLine(this.b));
    return this;
  }
}
