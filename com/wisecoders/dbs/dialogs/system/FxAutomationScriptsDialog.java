package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Language;
import com.wisecoders.dbs.sql.fx.FxSqlEditor;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.ContextMenu$;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.HBox$;
import com.wisecoders.dbs.sys.fx.HtmlLabel;
import com.wisecoders.dbs.sys.fx.VBox$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import java.io.File;
import java.io.IOException;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Modality;

public class FxAutomationScriptsDialog extends ButtonDialog$ {
  private final Workspace a;
  
  private final TreeView b = new TreeView();
  
  private final TabPane c = new TabPane();
  
  private TreeItem d;
  
  private final String e;
  
  private final TextField f;
  
  private String i;
  
  public FxAutomationScriptsDialog(WorkspaceWindow paramWorkspaceWindow) {
    this(paramWorkspaceWindow, (String)null);
  }
  
  public FxAutomationScriptsDialog(WorkspaceWindow paramWorkspaceWindow, String paramString) {
    super(paramWorkspaceWindow);
    this.a = paramWorkspaceWindow.getWorkspace();
    this.e = paramString;
    initModality(Modality.NONE);
    setGraphic(BootstrapIcons.filetype_java);
    this.rx.a("flagSelectedUserFolder", () -> 
        (this.b.getSelectionModel().getSelectedItem() != null && ((b)((TreeItem)this.b.getSelectionModel().getSelectedItem()).getValue()).a(Sys.h())));
    b b = new b();
    b.a.add(new b("Installed (Read-Only)", Sys.y.toFile(), true));
    b.a.add(new b("User Defined (Read-Write)", Sys.h().toFile(), false));
    FxAutomationScriptsDialog$ScriptTreeItem fxAutomationScriptsDialog$ScriptTreeItem = new FxAutomationScriptsDialog$ScriptTreeItem(this, b);
    fxAutomationScriptsDialog$ScriptTreeItem.setExpanded(true);
    this.b.setRoot(fxAutomationScriptsDialog$ScriptTreeItem);
    this.b.setShowRoot(false);
    this.b.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    this.b.setOnMouseClicked(paramMouseEvent -> {
          if (paramMouseEvent.getClickCount() == 2 && this.b.getSelectionModel().getSelectedItem() != null)
            a((b)((TreeItem)this.b.getSelectionModel().getSelectedItem()).getValue()); 
        });
    this.b.setCellFactory(paramTreeView -> new FxAutomationScriptsDialog$1(this));
    this.b.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramTreeItem1, paramTreeItem2) -> this.rx.b());
    ContextMenu$ contextMenu$ = (new ContextMenu$()).a(this.rx, new String[] { "createGroovyScript" });
    this.b.setContextMenu(contextMenu$);
    if (this.d != null) {
      this.b.getSelectionModel().select(this.d);
      a((b)this.d.getValue());
    } 
    this.f = this.rx.t("filterField");
    HBox$.setHgrow((Node)this.f, Priority.ALWAYS);
    this.f.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> a(this.f.getText()));
    setOnCloseRequest(paramDialogEvent -> {
          for (Tab tab : this.c.getTabs()) {
            Node node = tab.getContent();
            if (node instanceof FxSqlEditor) {
              FxSqlEditor fxSqlEditor = (FxSqlEditor)node;
              if (fxSqlEditor.h.getFile() != null && fxSqlEditor.h.getFile().toPath().startsWith(Sys.h()))
                fxSqlEditor.y(); 
            } 
          } 
        });
    HtmlLabel htmlLabel = this.rx.g("introduction");
    htmlLabel.setPadding(new Insets(15.0D, 15.0D, 15.0D, 15.0D));
    Tab tab = new Tab("Info", (Node)htmlLabel);
    this.c.getTabs().add(tab);
  }
  
  public void a(String paramString) {
    this.i = (paramString != null) ? paramString.toLowerCase().trim() : null;
    if (this.b.getRoot() instanceof FxAutomationScriptsDialog$ScriptTreeItem)
      ((FxAutomationScriptsDialog$ScriptTreeItem)this.b.getRoot()).b(); 
  }
  
  private void a(b paramb) {
    if (paramb != null && !paramb.b()) {
      for (Tab tab1 : this.c.getTabs()) {
        if (paramb.hasFile() && paramb.getFile().getName().equals(tab1.getText())) {
          this.c.getSelectionModel().select(tab1);
          return;
        } 
      } 
      FxSqlEditor fxSqlEditor = new FxSqlEditor(this.a, null, paramb);
      fxSqlEditor.a(Language.b);
      fxSqlEditor.i.e(!paramb.b);
      Tab tab = new Tab(paramb.toString(), (Node)fxSqlEditor);
      this.c.getTabs().add(tab);
      this.c.getSelectionModel().select(tab);
    } 
  }
  
  public Node createContentPane() {
    VBox$.setVgrow((Node)this.b, Priority.ALWAYS);
    VBox$ vBox$ = (new VBox$()).b(3).c(new Node[] { (Node)this.f, (Node)this.b });
    SplitPane splitPane = new SplitPane();
    Rx.b(this.c);
    splitPane.getItems().addAll((Object[])new Node[] { (Node)vBox$, (Node)this.c });
    Dialog$.setRegionPrefSize((Region)splitPane, 1250.0D, 650.0D);
    Platform.runLater(() -> paramSplitPane.setDividerPositions(new double[] { 0.15D }));
    return (Node)splitPane;
  }
  
  public void createButtons() {
    createCloseButton();
    Button button = createHelpButton("https://dbschema.com/javadoc/");
    button.setText("DbSchema API");
  }
  
  public boolean apply() {
    return true;
  }
  
  @Action(b = "flagSelectedUserFolder")
  public void createGroovyScript() {
    FxAutomationScriptsDialog$ScriptTreeItem fxAutomationScriptsDialog$ScriptTreeItem = (FxAutomationScriptsDialog$ScriptTreeItem)this.b.getSelectionModel().getSelectedItem();
    File file = FxFileChooser.a(getDialogScene(), "Create Groovy File", FileOperation.b, ((b)fxAutomationScriptsDialog$ScriptTreeItem.getValue()).getFile(), new FileType[] { FileType.s });
    if (file != null)
      try {
        if (file.createNewFile()) {
          fxAutomationScriptsDialog$ScriptTreeItem.a();
          FxAutomationScriptsDialog$ScriptTreeItem fxAutomationScriptsDialog$ScriptTreeItem1 = ((FxAutomationScriptsDialog$ScriptTreeItem)this.b.getRoot()).a(file);
          if (fxAutomationScriptsDialog$ScriptTreeItem1 != null) {
            this.b.getSelectionModel().select(fxAutomationScriptsDialog$ScriptTreeItem1);
            a((b)fxAutomationScriptsDialog$ScriptTreeItem1.getValue());
          } 
        } 
      } catch (IOException iOException) {
        this.rx.a(getDialogScene(), iOException);
      }  
  }
}
