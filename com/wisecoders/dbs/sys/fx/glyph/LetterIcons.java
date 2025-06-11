package com.wisecoders.dbs.sys.fx.glyph;

import com.wisecoders.dbs.sys.StringUtil;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;

public class LetterIcons implements Glyph {
  private final char c;
  
  public static final String b = "letter-";
  
  public LetterIcons(char paramChar) {
    this.c = paramChar;
  }
  
  public Label glyph(String... paramVarArgs) {
    Label label = new Label("" + this.c);
    label.prefWidthProperty().bind((ObservableValue)label.prefHeightProperty());
    label.getStyleClass().addAll((Object[])new String[] { "glyph", "glyph-letter" });
    for (String str : paramVarArgs) {
      if (StringUtil.isFilledTrim(str))
        label.getStyleClass().addAll((Object[])str.split(" ")); 
    } 
    return label;
  }
  
  public String getId() {
    return "letter-" + this.c;
  }
  
  public String getHtmlTag() {
    return "<i class=\"ch ch-" + this.c + "\"></i>";
  }
}
