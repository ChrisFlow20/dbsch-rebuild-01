package com.wisecoders.dbs.editors.text.undo;

import com.wisecoders.dbs.editors.text.StyledEditorModel;

public abstract class Change {
  public final long e = System.currentTimeMillis();
  
  public abstract Change a(StyledEditorModel paramStyledEditorModel);
  
  public abstract Change b(StyledEditorModel paramStyledEditorModel);
}
