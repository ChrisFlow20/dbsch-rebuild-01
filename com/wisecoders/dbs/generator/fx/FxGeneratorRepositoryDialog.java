package com.wisecoders.dbs.generator.fx;

import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.generator.engine.plan.Category;
import com.wisecoders.dbs.generator.engine.plan.DefinedPattern;
import com.wisecoders.dbs.generator.engine.plan.Domain;
import com.wisecoders.dbs.generator.store.PatternStore;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.ContextMenu$;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.RowPane$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import java.io.File;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.layout.Region;

public class FxGeneratorRepositoryDialog extends Dialog$ implements WorkspaceWindow {
  private final TextField a = new TextField();
  
  private final e b = new e(this);
  
  private final TreeTableView c = new TreeTableView(this.b);
  
  private final Workspace d;
  
  private final TreeTableColumn e;
  
  private final TreeTableColumn f;
  
  private final TreeTableColumn i;
  
  private ContextMenu$ j;
  
  FxGeneratorRepositoryDialog(WorkspaceWindow paramWorkspaceWindow) {
    super(paramWorkspaceWindow);
    this.e = new TreeTableColumn("Category / Alias");
    this.f = new TreeTableColumn("Pattern");
    this.i = new TreeTableColumn("Description & Samples");
    this.d = paramWorkspaceWindow.getWorkspace();
    setGraphic(BootstrapIcons.dice_5_fill);
    d();
    this.rx.a("flagSelectedPattern", () -> (b() != null));
    this.rx.a("flagSelectedItem", () -> (a() != null));
    this.rx.a("flagSelectedCategory", () -> (c() != null));
    this.rx.b();
    setResultConverter(paramButtonType -> (paramButtonType != null && paramButtonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) ? b() : null);
  }
  
  private TreeItem a() {
    return (TreeItem)this.c.getSelectionModel().getSelectedItem();
  }
  
  private DefinedPattern b() {
    TreeItem treeItem = a();
    return (treeItem != null && treeItem.getValue() instanceof DefinedPattern) ? (DefinedPattern)treeItem.getValue() : null;
  }
  
  private Category c() {
    TreeItem treeItem = a();
    return (treeItem != null && treeItem.getValue() instanceof Category) ? (Category)treeItem.getValue() : null;
  }
  
  private void d() {
    this.c.setShowRoot(false);
    this.e.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue().getValue()));
    this.e.setCellFactory(paramTreeTableColumn -> new d(this));
    this.f.setCellValueFactory(paramCellDataFeatures -> {
          Object object = paramCellDataFeatures.getValue().getValue();
          if (object instanceof DefinedPattern) {
            DefinedPattern definedPattern = (DefinedPattern)object;
            return (ObservableValue)new SimpleStringProperty(definedPattern.e());
          } 
          return null;
        });
    this.f.setGraphic(Rx.a());
    this.f.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
    this.f.setOnEditCommit(paramCellEditEvent -> ((DefinedPattern)paramCellEditEvent.getRowValue().getValue()).c((String)paramCellEditEvent.getNewValue()));
    this.i.setCellValueFactory(paramCellDataFeatures -> {
          Object object = paramCellDataFeatures.getValue().getValue();
          if (object instanceof DefinedPattern) {
            DefinedPattern definedPattern = (DefinedPattern)object;
            return (ObservableValue)new SimpleStringProperty(definedPattern.c());
          } 
          return null;
        });
    this.c.getColumns().addAll((Object[])new TreeTableColumn[] { this.e, this.f, this.i });
    this.c.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
    setRegionPrefSize((Region)this.c, 1000.0D, 600.0D);
    this.c.setEditable(true);
    this.c.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramTreeItem1, paramTreeItem2) -> this.rx.b());
  }
  
  public Node createContentPane() {
    SplitMenuButton splitMenuButton1 = this.rx.g("add", false);
    splitMenuButton1.getItems().addAll(this.rx.e(new String[] { "addCategory", "addPattern" }));
    this.a.getStyleClass().add("search-field");
    this.a.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> this.b.a());
    SplitMenuButton splitMenuButton2 = this.rx.g("save", false);
    splitMenuButton2.getItems().addAll(this.rx.e(new String[] { "open", "save" }));
    return (Node)(new RowPane$()).a()
      .a((Node)this.a)
      .b((Node)this.c)
      .a((Node)(new FlowPane$()).a().a(new Node[] { (Node)this.rx.j("editPattern"), (Node)splitMenuButton1, (Node)this.rx
            
            .j("deletePattern"), (Node)this.rx
            .j("resetDefaults"), (Node)splitMenuButton2 }));
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
    createHelpButton("random-data-generator.html#repository");
  }
  
  @Action
  public void addCategory() {
    String str = showInputString("createCategoryDialog");
    if (StringUtil.isFilledTrim(str)) {
      Domain.a(str);
      this.b.b();
    } 
  }
  
  @Action(b = "flagSelectedCategory")
  public void addPattern() {
    (new FxCreatePatternDialog(this, c())).showDialog();
    this.b.b();
  }
  
  @Action
  public Task open() {
    File file = FxFileChooser.a(getDialogScene(), "Pattern file", FileOperation.a, new FileType[] { FileType.j });
    if (file != null)
      return new FxGeneratorRepositoryDialog$1(this, file); 
    return null;
  }
  
  @Action
  public Task save() {
    File file = FxFileChooser.a(getDialogScene(), "Pattern file", FileOperation.b, new FileType[] { FileType.j });
    if (file != null)
      return a(file); 
    return null;
  }
  
  private Task a(File paramFile) {
    return new FxGeneratorRepositoryDialog$2(this, paramFile);
  }
  
  @Action(b = "flagSelectedPattern")
  public void editPattern() {
    DefinedPattern definedPattern = b();
    if (definedPattern != null)
      (new FxGeneratorPatternDialog(this, null, definedPattern.e(), false)).showDialog().ifPresent(paramString -> {
            paramDefinedPattern.c(paramString);
            this.c.refresh();
          }); 
  }
  
  @Action(b = "flagSelectedItem")
  public void deletePattern() {
    DefinedPattern definedPattern = b();
    Category category = c();
    if (definedPattern != null) {
      if (this.rx.a(getDialogPane().getScene(), "Drop '" + definedPattern.b() + "' Pattern ?")) {
        definedPattern.a.a().remove(definedPattern);
        this.b.b();
      } 
    } else if (category != null) {
      if (category.a().isEmpty()) {
        Domain.a(category);
        this.b.b();
      } else {
        showInformation("Categories should be empty to be deleted.", new String[0]);
      } 
    } 
  }
  
  @Action
  public void resetDefaults() {
    if (this.rx.a(getDialogPane().getScene(), "Are you sure you want to reload default settings ?\nThis will overwrite the previous settings.")) {
      Domain.a();
      this.b.b();
    } 
  }
  
  public boolean apply() {
    this.rx.a(a(PatternStore.a));
    return true;
  }
  
  public Workspace getWorkspace() {
    return this.d;
  }
}
