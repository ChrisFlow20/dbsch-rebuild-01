package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.diagram.model.TreeUnit;
import com.wisecoders.dbs.dialogs.system.FxUnitSelectionDialog;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Table;

public class FxGridTableSelectionDialog extends FxUnitSelectionDialog {
  public FxGridTableSelectionDialog(WorkspaceWindow paramWorkspaceWindow, String paramString1, Project paramProject, String paramString2) {
    super(paramWorkspaceWindow, paramString1, paramProject, paramString2);
    this.d.setShowRoot(false);
  }
  
  public boolean c(TreeUnit paramTreeUnit) {
    return !(paramTreeUnit instanceof com.wisecoders.dbs.diagram.model.AbstractTable);
  }
  
  public boolean d(TreeUnit paramTreeUnit) {
    return (paramTreeUnit instanceof Table || paramTreeUnit instanceof com.wisecoders.dbs.schema.Schema || (paramTreeUnit instanceof Folder && ((Folder)paramTreeUnit).childClass == Table.class));
  }
}
