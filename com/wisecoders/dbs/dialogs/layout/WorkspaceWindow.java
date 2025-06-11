package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.sys.Rx;
import javafx.scene.Scene;

public interface WorkspaceWindow {
  Workspace getWorkspace();
  
  Scene getDialogScene();
  
  Rx getRx();
}
