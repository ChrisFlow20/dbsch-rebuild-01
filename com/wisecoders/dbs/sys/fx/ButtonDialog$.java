package com.wisecoders.dbs.sys.fx;

import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Window;

public abstract class ButtonDialog$ extends Dialog$ {
  public ButtonDialog$(WorkspaceWindow paramWorkspaceWindow) {
    super(paramWorkspaceWindow);
  }
  
  public ButtonDialog$(WorkspaceWindow paramWorkspaceWindow, Modality paramModality) {
    super(paramWorkspaceWindow, paramModality);
  }
  
  public ButtonDialog$(Window paramWindow) {
    super(paramWindow);
  }
  
  public ButtonDialog$(Window paramWindow, Modality paramModality) {
    super(paramWindow, paramModality);
  }
  
  public boolean showDialogIsResultOkDone() {
    showDialog();
    return (getResult() != null && ((ButtonType)getResult()).getButtonData() == ButtonBar.ButtonData.OK_DONE);
  }
}
