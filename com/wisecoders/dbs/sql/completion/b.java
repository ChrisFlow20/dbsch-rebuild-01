package com.wisecoders.dbs.sql.completion;

import com.wisecoders.dbs.sys.fx.Dialog$;
import javafx.scene.Node;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

class b extends CustomMenuItem {
  b(SqlCompletionSuggestion paramSqlCompletionSuggestion) {
    setHideOnClick(true);
    BorderPane borderPane = new BorderPane();
    Dialog$.setRegionPrefWidth((Region)borderPane, 500.0D);
    setContent((Node)borderPane);
    Label label1 = new Label(paramSqlCompletionSuggestion.toString());
    if (paramSqlCompletionSuggestion.c() != null)
      label1.setGraphic((Node)paramSqlCompletionSuggestion.c().getSymbolicGlyph().glyph(new String[] { "font-small", "glyph-blue" })); 
    Label label2 = new Label(paramSqlCompletionSuggestion.a());
    label1.getStyleClass().addAll((Object[])new String[] { "font-large" });
    label2.getStyleClass().add("text-gray");
    borderPane.setLeft((Node)label1);
    borderPane.setRight((Node)label2);
    if (paramSqlCompletionSuggestion.b() != null) {
      Label label = new Label(paramSqlCompletionSuggestion.b());
      label.getStyleClass().add("text-gray");
      borderPane.setBottom((Node)label);
    } 
  }
}
