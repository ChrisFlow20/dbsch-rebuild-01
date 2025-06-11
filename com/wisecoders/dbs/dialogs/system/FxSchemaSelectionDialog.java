package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.schema.Project;

public class FxSchemaSelectionDialog extends FxUnitSelectionDialog {
  public FxSchemaSelectionDialog(WorkspaceWindow paramWorkspaceWindow, String paramString1, Project paramProject, String paramString2) {
    super(paramWorkspaceWindow, paramString1, paramProject, paramString2);
    this.d.setShowRoot(false);
  }
  
  public boolean c(TreeUnit paramTreeUnit) {
    return !(paramTreeUnit instanceof com.wisecoders.dbs.diagram.model.AbstractTable);
  }
}
