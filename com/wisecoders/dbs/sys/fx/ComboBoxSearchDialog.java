package com.wisecoders.dbs.sys.fx;

import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import java.util.Comparator;
import java.util.Objects;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ComboBoxSearchDialog extends ButtonDialog$ {
  private final TextField a;
  
  private final ListView b = new ListView();
  
  private final ComboBox c;
  
  private Button d;
  
  public ComboBoxSearchDialog(WorkspaceWindow paramWorkspaceWindow, String paramString, ComboBox paramComboBox) {
    super(paramWorkspaceWindow);
    setDialogTitle(paramString);
    this.c = paramComboBox;
    this.a = this.rx.t("findField");
    FilteredList filteredList = new FilteredList(paramComboBox.getItems(), paramObject -> true);
    this.a.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> paramFilteredList.setPredicate(()));
    SortedList sortedList = new SortedList((ObservableList)filteredList);
    sortedList.comparatorProperty().set(Comparator.comparing(Object::toString));
    this.b.setItems((ObservableList)sortedList);
    this.b.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramObject1, paramObject2) -> {
          if (this.d != null)
            this.d.setDisable((paramObject2 == null)); 
        });
    Objects.requireNonNull(this.a);
    Platform.runLater(this.a::requestFocus);
    showDialog();
  }
  
  public Node createContentPane() {
    GridPane$ gridPane$ = (new GridPane$()).l().b(new int[] { -1, -2 });
    gridPane$.a((Node)this.a, "0,0,f,c");
    gridPane$.a((Node)this.b, "0,1,f,c");
    return (Node)gridPane$;
  }
  
  public void createButtons() {
    this.d = createOkButton();
    this.d.setDisable(true);
    createCloseButton();
  }
  
  public boolean apply() {
    if (this.b.getSelectionModel().getSelectedItem() != null)
      this.c.setValue(this.b.getSelectionModel().getSelectedItem()); 
    return true;
  }
}
