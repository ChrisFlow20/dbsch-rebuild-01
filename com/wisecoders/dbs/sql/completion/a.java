package com.wisecoders.dbs.sql.completion;

import com.wisecoders.dbs.editors.text.StyledEditor;
import com.wisecoders.dbs.editors.text.StyledEditorCompletionMenu;
import com.wisecoders.dbs.sys.fx.HBox$;
import com.wisecoders.dbs.sys.fx.glyph.LetterIcons;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import org.eclipse.lsp4j.CompletionItem;

class a extends CustomMenuItem {
  a(StyledEditor paramStyledEditor, StyledEditorCompletionMenu paramStyledEditorCompletionMenu, String paramString, CompletionItem paramCompletionItem) {
    setHideOnClick(true);
    HBox$ hBox$ = new HBox$();
    setContent((Node)hBox$);
    String str1 = paramCompletionItem.getLabel();
    int i = -1;
    for (int j = str1.length(); i == -1 && j > 0; j--) {
      if (paramString.toLowerCase().endsWith(str1.substring(0, j).toLowerCase()))
        i = j; 
    } 
    String str2 = null, str3 = null;
    if (i == -1) {
      str3 = str1;
    } else if (i == str1.length()) {
      str2 = str1;
    } else {
      str2 = str1.substring(0, i);
      str3 = str1.substring(i);
    } 
    Label label1 = new Label(str2, (Node)(new LetterIcons(paramCompletionItem.getKind().toString().toUpperCase().charAt(0))).glyph(new String[0]));
    label1.getStyleClass().addAll((Object[])new String[] { "font-bold", "text-blue" });
    Label label2 = new Label(str3);
    label2.getStyleClass().addAll((Object[])new String[] { "font-bold", "text-dark-gray" });
    Label label3 = new Label(paramCompletionItem.getDetail());
    label3.getStyleClass().add("text-gray");
    hBox$.getChildren().addAll((Object[])new Node[] { (Node)label1, (Node)label2, (Node)new Label(" "), (Node)label3 });
    String str4 = str3;
    setOnAction(paramActionEvent -> {
          paramStyledEditor.K.a(paramString, 0);
          paramStyledEditor.J.q();
          paramStyledEditorCompletionMenu.getItems().clear();
          paramStyledEditorCompletionMenu.hide();
        });
  }
}
