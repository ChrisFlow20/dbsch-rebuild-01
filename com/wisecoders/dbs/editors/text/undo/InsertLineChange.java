package com.wisecoders.dbs.editors.text.undo;

import com.wisecoders.dbs.editors.text.StyledEditorHighlight;
import com.wisecoders.dbs.editors.text.StyledEditorModel;
import com.wisecoders.dbs.editors.text.TextLine;

public class InsertLineChange extends Change {
  private final int a;
  
  private final String b;
  
  private final StyledEditorHighlight c;
  
  public InsertLineChange(int paramInt, String paramString, StyledEditorHighlight paramStyledEditorHighlight) {
    this.a = paramInt;
    this.b = paramString;
    this.c = paramStyledEditorHighlight;
  }
  
  public Change a(StyledEditorModel paramStyledEditorModel) {
    TextLine textLine = new TextLine(this.b);
    switch (InsertLineChange$1.a[this.c.ordinal()]) {
      case 1:
        textLine.b(true);
        break;
      case 2:
        textLine.c(true);
        break;
    } 
    paramStyledEditorModel.a(this.a, textLine);
    paramStyledEditorModel.v();
    return this;
  }
  
  public Change b(StyledEditorModel paramStyledEditorModel) {
    paramStyledEditorModel.d(this.a);
    return this;
  }
}
