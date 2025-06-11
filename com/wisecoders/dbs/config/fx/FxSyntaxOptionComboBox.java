package com.wisecoders.dbs.config.fx;

import com.wisecoders.dbs.config.model.OptionsProperty;
import com.wisecoders.dbs.config.model.SyntaxOption;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.sys.StringUtil;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.control.ComboBox;

public class FxSyntaxOptionComboBox extends ComboBox {
  private OptionsProperty a;
  
  public FxSyntaxOptionComboBox(WorkspaceWindow paramWorkspaceWindow, String paramString, OptionsProperty paramOptionsProperty) {
    this(paramWorkspaceWindow, paramString);
    a(paramOptionsProperty);
  }
  
  public FxSyntaxOptionComboBox(WorkspaceWindow paramWorkspaceWindow, OptionsProperty paramOptionsProperty) {
    this(paramWorkspaceWindow, paramWorkspaceWindow.getWorkspace().m().getDbId());
    a(paramOptionsProperty);
  }
  
  public FxSyntaxOptionComboBox(WorkspaceWindow paramWorkspaceWindow, String paramString) {
    setEditable(true);
    setMaxWidth(Double.MAX_VALUE);
    setVisible(false);
    setOnShowing(paramEvent -> {
          if (this.a != null && this.a.f()) {
            SyntaxOption syntaxOption = this.a.h();
            syntaxOption.a((String)getValue());
            FxSyntaxOptionDialog fxSyntaxOptionDialog = new FxSyntaxOptionDialog(paramWorkspaceWindow, paramString, syntaxOption, this.a.a());
            fxSyntaxOptionDialog.showDialog().ifPresent(());
            Platform.runLater(this::hide);
          } 
        });
  }
  
  public void a(String paramString) {
    setValue(paramString);
    c();
  }
  
  public String a() {
    return (String)getValue();
  }
  
  public boolean b() {
    return StringUtil.isFilledTrim(getValue());
  }
  
  public void a(OptionsProperty paramOptionsProperty) {
    this.a = paramOptionsProperty;
    if (paramOptionsProperty != null && paramOptionsProperty.i() != null)
      setPromptText(paramOptionsProperty.i()); 
    c();
  }
  
  public void c() {
    setVisible((StringUtil.isFilledTrim(getValue()) || (this.a != null && this.a.f())));
  }
  
  public void d() {
    if (this.a != null && this.a.r() != null)
      a(this.a.r()); 
  }
  
  public void b(String paramString) {
    if (getPromptText() == null)
      setPromptText(paramString); 
  }
}
