package com.wisecoders.dbs.config.fx;

import com.wisecoders.dbs.config.model.BooleanProperty;
import com.wisecoders.dbs.config.model.DatePatternProperty;
import com.wisecoders.dbs.config.model.FileProperty;
import com.wisecoders.dbs.config.model.IntProperty;
import com.wisecoders.dbs.config.model.LOVProperty;
import com.wisecoders.dbs.config.model.ListProperty;
import com.wisecoders.dbs.config.model.Property;
import com.wisecoders.dbs.config.model.StringProperty;
import com.wisecoders.dbs.config.model.TextProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeTableCell;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import org.controlsfx.control.ToggleSwitch;

class b extends TreeTableCell {
  b(FxConfigurationPanel paramFxConfigurationPanel) {
    setEditable(true);
    setAlignment(Pos.CENTER_RIGHT);
  }
  
  protected void a(Property paramProperty, boolean paramBoolean) {
    super.updateItem(paramProperty, paramBoolean);
    setText(null);
    setGraphic(null);
    getStyleClass().removeAll((Object[])new String[] { "font-bold", "edited-cell" });
    if (paramProperty != null && !paramBoolean) {
      if (!paramProperty.e())
        getStyleClass().add("edited-cell"); 
      if (paramProperty instanceof BooleanProperty) {
        BooleanProperty booleanProperty = (BooleanProperty)paramProperty;
        ToggleSwitch toggleSwitch = new ToggleSwitch();
        toggleSwitch.setSelected(booleanProperty.b());
        toggleSwitch.setDisable(booleanProperty.f());
        toggleSwitch.selectedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
              paramBooleanProperty.a(paramToggleSwitch.isSelected());
              if (paramProperty.o() != null)
                paramProperty.o().call(null); 
            });
        setGraphic((Node)toggleSwitch);
      } else if (paramProperty instanceof com.wisecoders.dbs.config.model.FolderProperty) {
        getStyleClass().add("font-bold");
      } else if (paramProperty instanceof ListProperty) {
        ListProperty listProperty = (ListProperty)paramProperty;
        ComboBox comboBox = new ComboBox();
        comboBox.getItems().addAll((Object[])listProperty.b);
        comboBox.setValue(listProperty.f());
        comboBox.setOnAction(paramActionEvent -> paramListProperty.a(paramComboBox.getSelectionModel().getSelectedIndex()));
        setGraphic((Node)comboBox);
      } else if (paramProperty.c() != null) {
        setText(String.valueOf(paramProperty.c()));
      } 
    } 
  }
  
  public void startEdit() {
    if (!isEmpty()) {
      super.startEdit();
      if (!isEditing())
        return; 
      Object object = getItem();
      if (object instanceof TextProperty) {
        TextProperty textProperty = (TextProperty)object;
        object = new FxTextCellEditor(this.a.getScene().getWindow(), textProperty.c_());
        if (object.showDialogIsResultOkDone()) {
          textProperty.a(object.a());
          commitEdit(textProperty);
        } else {
          cancelEdit();
        } 
      } else {
        object = getItem();
        if (object instanceof StringProperty) {
          StringProperty stringProperty = (StringProperty)object;
          object = new FxStringCellEditor(this.a.getScene().getWindow(), stringProperty.c_());
          if (object.showDialogIsResultOkDone()) {
            stringProperty.a(object.a());
            commitEdit(stringProperty);
          } else {
            cancelEdit();
          } 
        } else {
          object = getItem();
          if (object instanceof LOVProperty) {
            LOVProperty lOVProperty = (LOVProperty)object;
            object = new FxStringCellEditor(this.a.getScene().getWindow(), lOVProperty.h());
            if (object.showDialogIsResultOkDone()) {
              lOVProperty.a(object.a());
              commitEdit(lOVProperty);
            } else {
              cancelEdit();
            } 
          } else {
            object = getItem();
            if (object instanceof DatePatternProperty) {
              DatePatternProperty datePatternProperty = (DatePatternProperty)object;
              object = new FxStringCellEditor(this.a.getScene().getWindow(), datePatternProperty.a());
              if (object.showDialogIsResultOkDone()) {
                datePatternProperty.a(object.a());
                commitEdit(datePatternProperty);
              } else {
                cancelEdit();
              } 
            } else {
              object = getItem();
              if (object instanceof IntProperty) {
                IntProperty intProperty = (IntProperty)object;
                object = new FxStringCellEditor(this.a.getScene().getWindow(), "" + intProperty.a());
                if (object.showDialogIsResultOkDone()) {
                  try {
                    intProperty.a(Integer.parseInt(object.a()));
                  } catch (NumberFormatException numberFormatException) {
                    this.a.c.a(this.a.getScene(), numberFormatException);
                  } 
                  commitEdit(intProperty);
                } else {
                  cancelEdit();
                } 
              } else {
                object = getItem();
                if (object instanceof FileProperty) {
                  FileProperty fileProperty = (FileProperty)object;
                  if (fileProperty.i()) {
                    DirectoryChooser directoryChooser = new DirectoryChooser();
                    if (fileProperty.f() != null)
                      directoryChooser.setInitialDirectory(fileProperty.f()); 
                    directoryChooser.setTitle("Choose Folder");
                    object = directoryChooser.showDialog(this.a.getScene().getWindow());
                  } else {
                    FileChooser fileChooser = new FileChooser();
                    if (fileProperty.f() != null) {
                      fileChooser.setInitialDirectory(fileProperty.f().getParentFile());
                      fileChooser.setInitialFileName(fileProperty.f().getName());
                      if (fileProperty.j() != null)
                        fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("File Type", fileProperty.j())); 
                    } 
                    fileChooser.setTitle("Choose File");
                    object = fileChooser.showSaveDialog(this.a.getScene().getWindow());
                  } 
                  if (object != null) {
                    fileProperty.a(object);
                    commitEdit(fileProperty);
                  } else if (this.a.c.a(this.a.getScene(), "Set file to <null> ?", "Delete")) {
                    fileProperty.a((Object)null);
                    commitEdit(fileProperty);
                  } else {
                    cancelEdit();
                  } 
                } 
              } 
            } 
          } 
        } 
      } 
      if (((Property)getItem()).o() != null)
        ((Property)getItem()).o().call(null); 
      this.a.a.refresh();
    } 
  }
}
