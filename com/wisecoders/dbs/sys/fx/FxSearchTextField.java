package com.wisecoders.dbs.sys.fx;

import com.wisecoders.dbs.sys.StringUtil;
import java.util.regex.PatternSyntaxException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import org.controlsfx.control.textfield.CustomTextField;

public class FxSearchTextField extends CustomTextField {
  public final ToggleButton a = a("Cc", "Case sensitive. Press to activate.");
  
  public final ToggleButton b = a(".*", "Regular Expression. Press to activate.");
  
  private ChangeListener c;
  
  public FxSearchTextField() {
    setRight((Node)(new HBox$()).a(new Node[] { (Node)this.a, (Node)this.b }));
    setPromptText("Type to filter");
    textProperty().addListener((paramObservableValue, paramString1, paramString2) -> a());
  }
  
  private ToggleButton a(String paramString1, String paramString2) {
    ToggleButton toggleButton = new ToggleButton(paramString1);
    toggleButton.setTooltip(new Tooltip(paramString2));
    toggleButton.getStyleClass().addAll((Object[])new String[] { "transparent-button", "font-bold", "text-gray" });
    toggleButton.selectedProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          if (paramBoolean2.booleanValue()) {
            paramToggleButton.getStyleClass().remove("text-gray");
          } else {
            paramToggleButton.getStyleClass().add("text-gray");
          } 
          a();
        });
    return toggleButton;
  }
  
  public void a() {
    if (this.c != null)
      this.c.changed(null, null, textProperty().getValue()); 
  }
  
  public void a(ChangeListener paramChangeListener) {
    this.c = paramChangeListener;
  }
  
  public boolean a(String paramString) {
    String str = textProperty().getValue();
    if (StringUtil.isEmpty(str))
      return true; 
    if (this.b.isSelected())
      try {
        return paramString.matches(str);
      } catch (PatternSyntaxException patternSyntaxException) {} 
    if (this.a.isSelected())
      return paramString.contains(str); 
    return paramString.toLowerCase().contains(str.toLowerCase());
  }
}
