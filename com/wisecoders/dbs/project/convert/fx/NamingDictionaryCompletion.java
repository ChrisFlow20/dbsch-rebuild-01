package com.wisecoders.dbs.project.convert.fx;

import com.wisecoders.dbs.project.convert.model.NameConverter;
import com.wisecoders.dbs.project.convert.model.NamingDictionary;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class NamingDictionaryCompletion {
  public static void a(TextField paramTextField) {
    ContextMenu contextMenu = new ContextMenu();
    paramTextField.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> {
          if (paramTextField.isVisible() && paramTextField.getScene() != null) {
            String str = paramTextField.getText();
            if (str != null) {
              str = str.toLowerCase();
              int i;
              if ((i = str.lastIndexOf(" ")) > -1)
                str = str.substring(i + 1); 
              if ((i = str.lastIndexOf("_")) > -1)
                str = str.substring(i + 1); 
            } 
            paramContextMenu.getItems().clear();
            if (str == null || str.isEmpty()) {
              paramContextMenu.hide();
            } else {
              String str1 = str;
              NamingDictionary.c.d.stream().filter(()).limit(10L).forEach(());
              if (paramContextMenu.getItems().isEmpty()) {
                paramContextMenu.hide();
              } else if (!paramContextMenu.isShowing()) {
                paramContextMenu.show((Node)paramTextField, Side.BOTTOM, 0.0D, 0.0D);
              } 
            } 
          } 
        });
    paramTextField.focusedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> paramContextMenu.hide());
  }
  
  public static TextFlow a(String paramString1, String paramString2) {
    int i = paramString1.toLowerCase().indexOf(paramString2.toLowerCase());
    Text text1 = new Text(paramString1.substring(0, i));
    Text text2 = new Text(paramString1.substring(i + paramString2.length()));
    Text text3 = new Text(paramString1.substring(i, i + paramString2.length()));
    text3.getStyleClass().add("font-bold");
    return new TextFlow(new Node[] { (Node)text1, (Node)text3, (Node)text2 });
  }
}
