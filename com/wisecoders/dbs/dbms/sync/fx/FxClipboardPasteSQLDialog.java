package com.wisecoders.dbs.dbms.sync.fx;

import com.wisecoders.dbs.dbms.sync.engine.diffs.AbstractDiff;
import com.wisecoders.dbs.dbms.sync.engine.nodes.SyncPair;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.editors.text.StyledEditor;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.SchemaMapping;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.schema.View;
import com.wisecoders.dbs.sql.parser.DDLParser;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.VBox$;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class FxClipboardPasteSQLDialog extends Dialog$ implements WorkspaceWindow {
  private final Workspace a;
  
  private final StyledEditor b = new StyledEditor();
  
  private final CheckBox c = this.rx.v("virtualCheckBox");
  
  public FxClipboardPasteSQLDialog(WorkspaceWindow paramWorkspaceWindow) {
    super(paramWorkspaceWindow);
    this.a = paramWorkspaceWindow.getWorkspace();
  }
  
  public Node createContentPane() {
    VBox.setVgrow((Node)this.b, Priority.ALWAYS);
    this.b.b(Clipboard.getSystemClipboard().getString());
    VBox$ vBox$ = (new VBox$()).k().c(new Node[] { (Node)this.b, (Node)this.c });
    vBox$.setPrefSize(600.0D, 500.0D);
    return (Node)vBox$;
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
  
  public boolean apply() {
    if (StringUtil.isFilledTrim(this.b.t())) {
      Project project = new Project("Clipboard", this.a.m().getDbId());
      try {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
          PrintStream printStream = new PrintStream(byteArrayOutputStream);
          try {
            (new DDLParser(project, printStream)).splitAndParse(this.b.t());
            if (project.schemas.isEmpty()) {
              this.rx.c(getDialogScene(), "emptyClipboardProject");
            } else {
              SyncPair syncPair = new SyncPair(this.a.m(), new SchemaMapping(), project);
              syncPair.filter(paramAbstractDiff -> (paramAbstractDiff.pair.right == null));
              (new FxSynchronizationDialog(this.a, syncPair, false, "sqlScript", "Clipboard")).showDialog();
              if (this.c.isSelected())
                for (Schema schema : project.schemas) {
                  for (Table table1 : schema.tables) {
                    Table table2 = (Table)this.a.m().getSimilarEntity(table1);
                    if (table2 != null)
                      table2.setVirtual(true); 
                  } 
                  for (View view1 : schema.views) {
                    View view2 = (View)this.a.m().getSimilarEntity(view1);
                    if (view2 != null)
                      view2.setVirtual(true); 
                  } 
                }  
            } 
            printStream.close();
          } catch (Throwable throwable) {
            try {
              printStream.close();
            } catch (Throwable throwable1) {
              throwable.addSuppressed(throwable1);
            } 
            throw throwable;
          } 
          byteArrayOutputStream.close();
        } catch (Throwable throwable) {
          try {
            byteArrayOutputStream.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          } 
          throw throwable;
        } 
      } catch (IOException iOException) {}
    } 
    return true;
  }
  
  public Workspace getWorkspace() {
    return this.a;
  }
}
