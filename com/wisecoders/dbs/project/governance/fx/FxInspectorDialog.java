package com.wisecoders.dbs.project.governance.fx;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.project.governance.model.InspectorRoot;
import com.wisecoders.dbs.project.governance.model.Mode;
import com.wisecoders.dbs.project.governance.store.InspectorStore;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.FileUtils;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Path;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

public class FxInspectorDialog extends Dialog$ implements WorkspaceWindow {
  private final WorkspaceWindow a;
  
  private final TabPane b = new TabPane();
  
  private final Tab c = new Tab();
  
  private final Mode d;
  
  public FxInspectorDialog(WorkspaceWindow paramWorkspaceWindow, Mode paramMode) {
    super(paramWorkspaceWindow);
    this.a = paramWorkspaceWindow;
    this.d = paramMode;
    this.rx.G(String.valueOf(paramMode));
    setDialogTitleAndHeader();
    this.rx.a("flagHasInspectors", () -> !InspectorRoot.d.isEmpty());
    for (InspectorRoot inspectorRoot : (paramMode == Mode.a) ? InspectorRoot.d : InspectorRoot.e)
      this.b.getTabs().add(new FxInspectorTab(this, inspectorRoot, paramMode)); 
    this.c.setGraphic((Node)BootstrapIcons.plus_circle_dotted.glyph(new String[0]));
    this.c.setContent((Node)new BorderPane((Node)this.rx.j("createInspector")));
    this.c.setClosable(false);
    this.b.getTabs().add(this.c);
    this.b.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramTab1, paramTab2) -> {
          if (paramTab2 == this.c)
            createInspector(); 
        });
    setRegionPrefSize((Region)this.b, 850.0D, 550.0D);
  }
  
  @Action
  public void createInspector() {
    String str = this.rx.b(getDialogScene(), "newInspectorName");
    if (StringUtil.isFilled(str))
      if (InspectorRoot.a(str, this.d) == null) {
        InspectorRoot inspectorRoot = InspectorRoot.b(str, this.d);
        inspectorRoot.a(this.a.getWorkspace().m().getDbId());
        FxInspectorTab fxInspectorTab = new FxInspectorTab(this, inspectorRoot, this.d);
        this.b.getTabs().add(Math.max(0, this.b.getTabs().size() - 1), fxInspectorTab);
        this.b.getSelectionModel().select(fxInspectorTab);
      } else {
        this.rx.c(getDialogScene(), "duplicateInspector");
      }  
    this.rx.b();
  }
  
  public Node createContentPane() {
    return (Node)this.b;
  }
  
  public void createButtons() {
    createOkButton();
    if (this.d == Mode.a)
      createActionButton("validate"); 
    createCancelButton();
    createHelpButton((this.d == Mode.b) ? "import-export-model.html" : "model-validation.html");
  }
  
  public boolean apply() {
    for (Tab tab : this.b.getTabs()) {
      if (tab instanceof FxInspectorTab)
        ((FxInspectorTab)tab).a(); 
    } 
    Path path = (this.d == Mode.a) ? Sys.b() : Sys.c();
    File[] arrayOfFile = path.toFile().listFiles();
    if (arrayOfFile != null)
      for (File file : arrayOfFile)
        FileUtils.b(file);  
    for (InspectorRoot inspectorRoot : (this.d == Mode.a) ? InspectorRoot.d : InspectorRoot.e) {
      try {
        FileWriter fileWriter = new FileWriter(path.resolve(inspectorRoot.o() + ".xml").toFile());
        try {
          new InspectorStore(inspectorRoot, fileWriter);
          fileWriter.close();
        } catch (Throwable throwable) {
          try {
            fileWriter.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          } 
          throw throwable;
        } 
      } catch (Exception exception) {
        Log.b(exception);
      } 
    } 
    return true;
  }
  
  @Action(b = "flagHasInspectors")
  public void validate() {
    StringWriter stringWriter = new StringWriter();
    try {
      for (InspectorRoot inspectorRoot : InspectorRoot.d)
        inspectorRoot.a(this.a.getWorkspace().m(), stringWriter); 
      (new FxValidationResultDialog(this, false, InspectorRoot.d)).showDialog();
    } catch (IOException|ClassNotFoundException iOException) {
      this.rx.a(this.a.getDialogScene(), iOException);
    } 
  }
  
  public Workspace getWorkspace() {
    return this.a.getWorkspace();
  }
}
