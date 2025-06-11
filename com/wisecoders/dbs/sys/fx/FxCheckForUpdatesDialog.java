package com.wisecoders.dbs.sys.fx;

import com.install4j.api.update.UpdateSchedule;
import com.install4j.api.update.UpdateScheduleRegistry;
import com.install4j.runtime.installer.helper.apiimpl.UpdateScheduleRegistryImpl;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Log;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;

public class FxCheckForUpdatesDialog extends Dialog$ {
  private final ComboBox a = new ComboBox();
  
  public FxCheckForUpdatesDialog(WorkspaceWindow paramWorkspaceWindow) {
    super(paramWorkspaceWindow);
    this.a.getItems().addAll((Object[])UpdateSchedule.values());
    this.a.setValue(UpdateScheduleRegistry.getUpdateSchedule());
    if (this.a.getValue() == null)
      this.a.setValue(UpdateSchedule.DAILY); 
  }
  
  public Node createContentPane() {
    GridPane$ gridPane$ = (new GridPane$()).l();
    gridPane$.a((Node)this.rx.e("scheduleLabel"), "0,0,r,c");
    gridPane$.a((Node)this.a, "1,0,l,c");
    return (Node)gridPane$;
  }
  
  @Action
  public Task checkForUpdate() {
    return new CheckForUpdatesTask(getDialogScene());
  }
  
  public void createButtons() {
    createActionButton("checkForUpdate");
    createOkButton();
    createCancelButton();
  }
  
  public boolean apply() {
    Log.c("Set UpdateScheduleRegistry.setUpdateSchedule=" + String.valueOf(this.a.getValue()));
    UpdateScheduleRegistry.setUpdateSchedule((UpdateSchedule)this.a.getValue());
    Log.c("Check UpdateScheduleRegistry.getUpdateSchedule()=" + String.valueOf(UpdateScheduleRegistry.getUpdateSchedule()));
    Log.c("Check UpdateScheduleRegistryImpl.getApplicationId()=" + UpdateScheduleRegistryImpl.getApplicationId());
    return true;
  }
}
