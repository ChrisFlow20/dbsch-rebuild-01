package com.wisecoders.dbs.project.convert.fx;

import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.loader.fx.FxLoaderFileDialog;
import com.wisecoders.dbs.loader.model.LoaderMode;
import com.wisecoders.dbs.loader.model.MetaLoaderNamingDictionary;
import com.wisecoders.dbs.project.convert.model.NameConverter;
import com.wisecoders.dbs.project.convert.model.NamingDictionary;
import com.wisecoders.dbs.project.convert.model.NamingDictionary$Separator;
import com.wisecoders.dbs.project.convert.store.NamingDictionaryLoader;
import com.wisecoders.dbs.project.convert.store.NamingDictionaryStore;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.FxEditableStringTableCell;
import com.wisecoders.dbs.sys.fx.FxSearchTextField;
import com.wisecoders.dbs.sys.fx.RowPane$;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

public class FxNamingDictionaryDialog extends Dialog$ implements WorkspaceWindow {
  private final WorkspaceWindow c;
  
  private final TableView d = new TableView();
  
  private final ComboBox e = new ComboBox();
  
  private final ComboBox f = new ComboBox();
  
  private final CheckBox i;
  
  private final NamingDictionary j;
  
  final TableColumn a;
  
  final FxSearchTextField b = new FxSearchTextField();
  
  public FxNamingDictionaryDialog(WorkspaceWindow paramWorkspaceWindow, NamingDictionary paramNamingDictionary) {
    super(paramWorkspaceWindow);
    this.c = paramWorkspaceWindow;
    this.j = paramNamingDictionary;
    this.rx.a("flagSelectedItem", () -> (this.d.getSelectionModel().getSelectedItem() != null));
    this.i = this.rx.h("convertCasesCheckBox", paramNamingDictionary.d());
    this.a = new TableColumn("Logical");
    this.a.setGraphic(Rx.a());
    this.a.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(((NameConverter)paramCellDataFeatures.getValue()).a()));
    this.a.setEditable(true);
    this.a.setCellFactory(paramTableColumn -> new FxEditableStringTableCell());
    this.a.setOnEditCommit(paramCellEditEvent -> {
          if (paramCellEditEvent.getRowValue() != null)
            ((NameConverter)paramCellEditEvent.getRowValue()).a((String)paramCellEditEvent.getNewValue()); 
        });
    TableColumn tableColumn = new TableColumn("Physical");
    tableColumn.setGraphic(Rx.a());
    tableColumn.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(((NameConverter)paramCellDataFeatures.getValue()).b()));
    tableColumn.setEditable(true);
    tableColumn.setCellFactory(paramTableColumn -> new FxNamingDictionaryDialog$1(this));
    tableColumn.setOnEditCommit(paramCellEditEvent -> ((NameConverter)paramCellEditEvent.getRowValue()).b((String)paramCellEditEvent.getNewValue()));
    this.d.getColumns().addAll((Object[])new TableColumn[] { this.a, tableColumn });
    this.d.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    this.d.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramNameConverter1, paramNameConverter2) -> this.rx.b());
    this.d.setEditable(true);
    this.d.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    FilteredList filteredList = new FilteredList(paramNamingDictionary.d, paramNameConverter -> true);
    this.b.a((paramObservableValue, paramString1, paramString2) -> paramFilteredList.setPredicate(()));
    this.d.setItems((ObservableList)filteredList);
    this.e.getItems().addAll((Object[])NamingDictionary$Separator.values());
    this.e.setValue(paramNamingDictionary.b());
    this.f.getItems().addAll((Object[])NamingDictionary$Separator.values());
    this.f.setValue(paramNamingDictionary.a());
  }
  
  @Action
  public void addItem() {
    this.j.d.add(new NameConverter());
    this.d.scrollTo(this.d.getItems().size() - 1);
    this.d.edit(this.d.getItems().size() - 1, this.a);
  }
  
  @Action(b = "flagSelectedItem")
  public void dropItem() {
    this.j.d.removeAll((Collection)this.d.getSelectionModel().getSelectedItems());
  }
  
  @Action
  public void openDictionary() {
    File file = FxFileChooser.a(getDialogScene(), "Choose XML Dictionary File", FileOperation.a, new FileType[] { FileType.j });
    if (file != null)
      new NamingDictionaryLoader(NamingDictionary.c, file); 
  }
  
  @Action
  public void saveDictionary() {
    File file = FxFileChooser.a(getDialogScene(), "Choose XML Dictionary File", FileOperation.b, new FileType[] { FileType.j });
    if (file != null)
      try {
        NamingDictionaryStore.a(NamingDictionary.c, file);
      } catch (IOException iOException) {
        this.rx.a(getDialogScene(), iOException);
      }  
  }
  
  public Node createContentPane() {
    setRegionPrefSize((Region)this.d, 400.0D, 400.0D);
    SplitMenuButton splitMenuButton = this.rx.g("openDictionary", true);
    splitMenuButton.getItems().addAll(this.rx.e(new String[] { "openDictionary", "loadFromCSV" }));
    ArrayList<SplitMenuButton> arrayList = new ArrayList(this.rx.c(new String[] { "addItem", "dropItem" }));
    arrayList.add(splitMenuButton);
    arrayList.addAll(this.rx.c(new String[] { "saveDictionary" }));
    return (Node)(new RowPane$()).g()
      .a((Node)this.rx.h("separators"))
      .a(new Node[] { (Node)this.rx.e("logicalSeparatorLabel"), (Node)this.e, (Node)this.rx.e("physicalSeparatorLabel"), (Node)this.f, (Node)this.i }).a((Node)this.b)
      .b((Node)this.d)
      .a(arrayList);
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
  
  public boolean apply() {
    this.j.d.removeIf(NameConverter::c);
    this.j.b((NamingDictionary$Separator)this.e.getValue());
    this.j.a((NamingDictionary$Separator)this.f.getValue());
    this.j.a(this.i.isSelected());
    try {
      NamingDictionaryStore.a(this.j, NamingDictionary.c());
    } catch (IOException iOException) {
      this.rx.a(getDialogScene(), iOException);
    } 
    return true;
  }
  
  @Action
  public void loadFromCSV() {
    MetaLoaderNamingDictionary metaLoaderNamingDictionary = new MetaLoaderNamingDictionary();
    new FxLoaderFileDialog(this.c, metaLoaderNamingDictionary.a, LoaderMode.c);
  }
  
  public Workspace getWorkspace() {
    return this.c.getWorkspace();
  }
}
