package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.sync.engine.nodes.EditorSyncRoot;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.FxUnitEditor;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.SchemaMapping;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

public abstract class FxDbDialog extends ButtonDialog$ implements FxUnitEditor, WorkspaceWindow {
  protected final Workspace b;
  
  private final List a = new ArrayList();
  
  public final boolean doImplement;
  
  private final SimpleBooleanProperty c;
  
  public FxDbDialog(WorkspaceWindow paramWorkspaceWindow, boolean paramBoolean) {
    super(paramWorkspaceWindow.getWorkspace());
    this.c = new SimpleBooleanProperty(false);
    this.b = paramWorkspaceWindow.getWorkspace();
    this.doImplement = paramBoolean;
    initOwner(paramWorkspaceWindow);
  }
  
  public FxDbDialog(WorkspaceWindow paramWorkspaceWindow, boolean paramBoolean, Modality paramModality) {
    super(paramWorkspaceWindow.getWorkspace());
    this.c = new SimpleBooleanProperty(false);
    this.b = paramWorkspaceWindow.getWorkspace();
    initModality(paramModality);
    this.doImplement = paramBoolean;
    initOwner(paramWorkspaceWindow);
  }
  
  public EditorSyncRoot addSyncOperation(AbstractUnit paramAbstractUnit1, AbstractUnit paramAbstractUnit2, AbstractUnit paramAbstractUnit3) {
    return addSyncOperation(paramAbstractUnit1, paramAbstractUnit2, paramAbstractUnit3, false);
  }
  
  public EditorSyncRoot addSyncOperation(AbstractUnit paramAbstractUnit1, AbstractUnit paramAbstractUnit2, AbstractUnit paramAbstractUnit3, boolean paramBoolean) {
    if (this.doImplement) {
      SchemaMapping schemaMapping = this.b.r() ? (this.b.s()).mapping : null;
      EditorSyncRoot editorSyncRoot = new EditorSyncRoot(paramAbstractUnit1, paramAbstractUnit2, paramAbstractUnit3, true, schemaMapping);
      this.a.add(editorSyncRoot);
      editorSyncRoot.a(paramBoolean);
      return editorSyncRoot;
    } 
    return null;
  }
  
  public abstract void applyChanges();
  
  public Button createOkButton() {
    Button button = super.createOkButton();
    button.disableProperty().bind((ObservableValue)this.c);
    return button;
  }
  
  public final boolean apply() {
    if (validate()) {
      this.c.set(true);
      this.a.clear();
      applyChanges();
      int i = 0;
      for (EditorSyncRoot editorSyncRoot : this.a)
        i += editorSyncRoot.getDiffCount(); 
      if (i == 0) {
        saveSucceeded();
        setResult(ButtonType.OK);
        close();
        this.c.set(false);
      } else {
        if (this.doImplement && this.b.t() && !Sys.B.readOnly.b()) {
          this.rx.a(new d(this));
          return false;
        } 
        for (EditorSyncRoot editorSyncRoot : this.a)
          editorSyncRoot.a(this.b.o()); 
        saveSucceeded();
        setResult(ButtonType.OK);
        close();
        this.b.d(true);
        this.b.u();
        this.c.set(false);
      } 
      return true;
    } 
    return false;
  }
  
  public abstract void saveSucceeded();
  
  public abstract boolean validate();
  
  public Workspace getWorkspace() {
    return this.b;
  }
}
