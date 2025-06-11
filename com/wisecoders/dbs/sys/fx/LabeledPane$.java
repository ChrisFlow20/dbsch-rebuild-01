package com.wisecoders.dbs.sys.fx;

import com.wisecoders.dbs.sys.StringUtil;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;

public class LabeledPane$ extends BorderPane {
  private final Label a = new Label();
  
  public final ToolBar b = new ToolBar();
  
  protected LabeledPane$() {
    this.b.getItems().setAll((Object[])new Node[] { (Node)this.a });
    this.b.getStyleClass().add("title-tool-bar");
    setTop((Node)this.b);
  }
  
  public LabeledPane$(String paramString) {
    this();
    a(paramString);
  }
  
  public LabeledPane$(String paramString, Node paramNode) {
    this();
    a(paramString);
    setCenter(paramNode);
  }
  
  public void a(String paramString) {
    if (!StringUtil.areEqual(paramString, this.a.getText()))
      this.a.setText(paramString); 
  }
}
