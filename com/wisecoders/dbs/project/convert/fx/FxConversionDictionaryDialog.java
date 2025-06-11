package com.wisecoders.dbs.project.convert.fx;

import com.wisecoders.dbs.config.fx.FxSettingsDialog;
import com.wisecoders.dbs.config.fx.FxSettingsDialog$SelectTab;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.project.convert.model.ConversionDictionary;
import com.wisecoders.dbs.project.convert.model.ConverterReplacement;
import com.wisecoders.dbs.project.convert.model.DataTypeConverter;
import com.wisecoders.dbs.project.convert.model.DefaultValueConverter;
import com.wisecoders.dbs.project.convert.model.OptionsConverter;
import com.wisecoders.dbs.project.convert.store.ConversionDictionaryLoader;
import com.wisecoders.dbs.project.convert.store.ConversionDictionaryStore;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.UniqueArrayList;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.ChoiceDialogWithFilterableCombo;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.control.TreeTableView;
import javafx.stage.WindowEvent;

public class FxConversionDictionaryDialog extends ButtonDialog$ implements WorkspaceWindow {
  private final Project b;
  
  private final Workspace c;
  
  private final ConversionDictionary d;
  
  private final TreeTableView e = new TreeTableView();
  
  private final TreeItem f = new TreeItem("Data Types");
  
  private final TreeItem i = new TreeItem("Default Values");
  
  private final TreeItem j = new TreeItem("Table & Column Options");
  
  private final String k;
  
  private Project l;
  
  public static final String a = "convertToDbms";
  
  public FxConversionDictionaryDialog(WorkspaceWindow paramWorkspaceWindow, Project paramProject) {
    this(paramWorkspaceWindow, paramProject, (String)null);
  }
  
  public FxConversionDictionaryDialog(WorkspaceWindow paramWorkspaceWindow, Project paramProject, String paramString) {
    super(paramWorkspaceWindow);
    this.b = paramProject;
    this.k = paramString;
    this.c = paramWorkspaceWindow.getWorkspace();
    this.d = ConversionDictionary.a(paramProject.getDbId());
    this.rx.a("flagSelectedRule", () -> (this.e.getSelectionModel().getSelectedItem() != null && ((TreeItem)this.e.getSelectionModel().getSelectedItem()).getValue() instanceof com.wisecoders.dbs.project.convert.model.Converter));
    for (String str : c())
      this.d.b(paramProject, str); 
    TreeItem treeItem = new TreeItem("Converters");
    treeItem.getChildren().addAll((Object[])new TreeItem[] { this.f, this.i, this.j });
    treeItem.setExpanded(true);
    this.f.setExpanded(true);
    this.i.setExpanded(true);
    this.j.setExpanded(true);
    d();
    this.e.setRoot(treeItem);
    this.e.setShowRoot(false);
    this.e.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    this.e.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramTreeItem1, paramTreeItem2) -> this.rx.b());
    b();
    this.rx.b();
  }
  
  private void b() {
    this.f.getChildren().clear();
    this.i.getChildren().clear();
    this.j.getChildren().clear();
    for (DataTypeConverter dataTypeConverter : this.d.c)
      this.f.getChildren().add(new TreeItem(dataTypeConverter)); 
    for (DefaultValueConverter defaultValueConverter : this.d.d)
      this.i.getChildren().add(new TreeItem(defaultValueConverter)); 
    for (OptionsConverter optionsConverter : this.d.e)
      this.j.getChildren().add(new TreeItem(optionsConverter)); 
    for (Iterator<String> iterator = c().iterator(); iterator.hasNext(); ) {
      String str = iterator.next();
      if (!a(str)) {
        TreeTableColumn treeTableColumn = new TreeTableColumn(str);
        treeTableColumn.setGraphic(Rx.a());
        treeTableColumn.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue().getValue()));
        treeTableColumn.setCellFactory(paramTreeTableColumn2 -> new FxConversionDictionaryDialog$1(this, paramTreeTableColumn1));
        this.e.getColumns().add(treeTableColumn);
      } 
    } 
  }
  
  private boolean a(String paramString) {
    for (TreeTableColumn treeTableColumn : this.e.getColumns()) {
      if (treeTableColumn.getText().equals(paramString))
        return true; 
    } 
    return false;
  }
  
  private List c() {
    UniqueArrayList uniqueArrayList = new UniqueArrayList();
    if (this.k == null) {
      for (DataTypeConverter dataTypeConverter : this.d.c) {
        for (ConverterReplacement converterReplacement : dataTypeConverter.g)
          uniqueArrayList.add(converterReplacement.a); 
      } 
      for (DefaultValueConverter defaultValueConverter : this.d.d) {
        for (ConverterReplacement converterReplacement : defaultValueConverter.f)
          uniqueArrayList.add(converterReplacement.a); 
      } 
      for (OptionsConverter optionsConverter : this.d.e) {
        for (ConverterReplacement converterReplacement : optionsConverter.c)
          uniqueArrayList.add(converterReplacement.a); 
      } 
    } else {
      uniqueArrayList.add(this.k);
    } 
    Collections.sort(uniqueArrayList);
    return uniqueArrayList;
  }
  
  public Workspace getWorkspace() {
    return this.c;
  }
  
  private void d() {
    TreeTableColumn treeTableColumn = new TreeTableColumn(this.b.getDbId());
    treeTableColumn.setGraphic(Rx.a());
    treeTableColumn.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue().getValue()));
    treeTableColumn.setCellFactory(paramTreeTableColumn -> new FxConversionDictionaryDialog$2(this));
    treeTableColumn.setEditable(false);
    this.e.getColumns().add(treeTableColumn);
    ContextMenu contextMenu = new ContextMenu();
    contextMenu.getItems().add(new Menu("Empty"));
    treeTableColumn.setContextMenu(contextMenu);
    contextMenu.setOnShowing(paramWindowEvent -> {
          paramContextMenu.getItems().clear();
          TreeItem treeItem = (TreeItem)this.e.getSelectionModel().getSelectedItem();
          if (treeItem != null)
            if (treeItem == this.f) {
              paramContextMenu.getItems().add(this.rx.A("addDataTypeConverter"));
            } else if (treeItem == this.i) {
              paramContextMenu.getItems().add(this.rx.A("addDefaultValueConverter"));
            } else if (treeItem == this.j) {
              paramContextMenu.getItems().add(this.rx.A("addOptionConverter"));
            } else if (treeItem.getValue() instanceof com.wisecoders.dbs.project.convert.model.Converter) {
              paramContextMenu.getItems().add(this.rx.A("editSelectedConvertor"));
            }  
        });
    this.e.setRowFactory(paramTreeTableView -> new FxConversionDictionaryDialog$3(this, paramContextMenu));
    this.e.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
    this.e.setEditable(true);
  }
  
  @Action
  public void editSelectedConvertor() {
    a(((TreeItem)this.e.getSelectionModel().getSelectedItem()).getValue());
  }
  
  private void a(Object paramObject) {
    if (paramObject instanceof DataTypeConverter) {
      DataTypeConverter dataTypeConverter = (DataTypeConverter)paramObject;
      (new FxDataTypeConverterDialog(this, dataTypeConverter)).showDialog();
    } else if (paramObject instanceof DefaultValueConverter) {
      DefaultValueConverter defaultValueConverter = (DefaultValueConverter)paramObject;
      (new FxDefaultValueConverterDialog(this.c, defaultValueConverter)).showDialog();
    } else if (paramObject instanceof OptionsConverter) {
      OptionsConverter optionsConverter = (OptionsConverter)paramObject;
      (new FxOptionsConverterDialog(this.c, optionsConverter)).showDialog();
    } 
    this.d.a(this.b, c());
    b();
    this.e.refresh();
  }
  
  public Node createContentPane() {
    GridPane$ gridPane$ = (new GridPane$()).l().a(new int[] { -1 }).b(new int[] { -2, -1, -2 });
    if (this.k == null)
      gridPane$.a((Node)this.rx.j("includeDbms"), "0,0,l,c"); 
    gridPane$.a((Node)this.e, "0,1,f,f");
    gridPane$.a((Node)(new FlowPane$()).a().a(this.rx.c(new String[] { "addDataTypeConverter", "addDefaultValueConverter", "addOptionConverter", "deleteConverter", "openDictionary", "saveDictionary", "groovyScript" }, )), "0,2,l,c");
    this.e.setPrefSize(700.0D, 500.0D);
    return (Node)gridPane$;
  }
  
  public void createButtons() {
    if (this.k == null) {
      createOkButton();
    } else {
      createActionButton("convert");
    } 
    createCancelButton();
  }
  
  @Action
  public void addDataTypeConverter() {
    (new FxDataTypeConverterDialog(this.c, this.d)).showDialog();
    this.d.a(this.b, c());
    b();
  }
  
  @Action
  public void addDefaultValueConverter() {
    (new FxDefaultValueConverterDialog(this.c, this.d)).showDialog();
    this.d.a(this.b, c());
    b();
  }
  
  @Action
  public void addOptionConverter() {
    (new FxOptionsConverterDialog(this.c, this.d)).showDialog();
    this.d.a(this.b, c());
    b();
  }
  
  @Action
  public String includeDbms() {
    List list = Dbms.getKnownDbmsExclude(new String[0]);
    list.removeAll(c());
    list.remove(this.b.getDbId());
    ChoiceDialogWithFilterableCombo choiceDialogWithFilterableCombo = new ChoiceDialogWithFilterableCombo((getDialogScene() != null) ? getDialogScene() : this.c, e(), list);
    this.rx.a("chooseDBMSDialog", choiceDialogWithFilterableCombo);
    Optional<String> optional = choiceDialogWithFilterableCombo.showAndWait();
    if (optional.isPresent()) {
      String str = optional.get();
      b(str);
      return str;
    } 
    return null;
  }
  
  private void b(String paramString) {
    if (paramString != null) {
      this.d.b(this.b, paramString);
      b();
    } 
  }
  
  private String e() {
    return Pref.c("convertToDbms", (String)null);
  }
  
  @Action(b = "flagSelectedRule")
  public void deleteConverter() {
    for (TreeItem treeItem : this.e.getSelectionModel().getSelectedItems()) {
      Object object = treeItem.getValue();
      if (object instanceof DataTypeConverter)
        this.d.c.remove(object); 
      if (object instanceof DefaultValueConverter)
        this.d.d.remove(object); 
    } 
    b();
  }
  
  @Action
  public void convert() {
    apply();
    if (this.k != null)
      if (this.d.a(this.b, this.k)) {
        Pref.a("convertToDbms", this.k);
        this.l = this.b.convert(this.k);
        close();
        this.c.u();
      } else {
        showError("Not all " + this.k + " data types are specified. Please edit.");
      }  
  }
  
  public static Project a(WorkspaceWindow paramWorkspaceWindow, Project paramProject) {
    ChoiceDialogWithFilterableCombo choiceDialogWithFilterableCombo = new ChoiceDialogWithFilterableCombo(paramWorkspaceWindow.getWorkspace(), Pref.c("convertToDbms", (String)null), Dbms.getKnownDbmsExclude(new String[] { paramProject.getDbId() }));
    (new Rx(FxConversionDictionaryDialog.class, null)).a("chooseDBMSDialog", choiceDialogWithFilterableCombo);
    return choiceDialogWithFilterableCombo.showAndWait().map(paramString -> a(paramWorkspaceWindow, paramProject, paramString)).orElse(null);
  }
  
  public static Project a(WorkspaceWindow paramWorkspaceWindow, Project paramProject, String paramString) {
    ConversionDictionary conversionDictionary = ConversionDictionary.a(paramProject.getDbId());
    if (conversionDictionary.a(paramProject, paramString)) {
      Pref.a("convertToDbms", paramString);
      return paramProject.convert(paramString);
    } 
    FxConversionDictionaryDialog fxConversionDictionaryDialog = new FxConversionDictionaryDialog(paramWorkspaceWindow, paramProject, paramString);
    fxConversionDictionaryDialog.showDialog();
    return fxConversionDictionaryDialog.a();
  }
  
  public boolean apply() {
    try {
      File file = Sys.a().resolve(StringUtil.escapeForFileName(this.d.b)).toFile();
      if (!file.exists())
        file.mkdirs(); 
      ConversionDictionaryStore.a(this.d, this.d.d());
    } catch (IOException iOException) {
      this.rx.a(getDialogScene(), iOException);
    } 
    return true;
  }
  
  public Project a() {
    return this.l;
  }
  
  @Action
  public void openDictionary() {
    File file = FxFileChooser.a(getDialogScene(), "Choose XML Dictionary File", FileOperation.a, new FileType[] { FileType.j });
    if (file != null)
      new ConversionDictionaryLoader(ConversionDictionary.a(this.b.getDbId()), file); 
  }
  
  @Action
  public void saveDictionary() {
    File file = FxFileChooser.a(getDialogScene(), "Choose XML Dictionary File", FileOperation.b, new FileType[] { FileType.j });
    if (file != null)
      try {
        ConversionDictionaryStore.a(ConversionDictionary.a(this.b.getDbId()), file);
      } catch (IOException iOException) {
        this.rx.a(getDialogScene(), iOException);
      }  
  }
  
  @Action
  public void groovyScript() {
    (new FxSettingsDialog(getDialogScene(), FxSettingsDialog$SelectTab.a, Sys.B.conversionGroovyScript)).showDialog();
  }
}
