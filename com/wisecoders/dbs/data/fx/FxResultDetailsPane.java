package com.wisecoders.dbs.data.fx;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wisecoders.dbs.data.model.result.Result;
import com.wisecoders.dbs.data.model.result.ResultColumn;
import com.wisecoders.dbs.data.model.result.ResultRow;
import com.wisecoders.dbs.editors.text.StyledEditor;
import com.wisecoders.dbs.schema.Language;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.FxUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class FxResultDetailsPane extends TabPane {
  private final FxResultTreeView a = new FxResultTreeView(null);
  
  private final StyledEditor b = new StyledEditor(Language.d, true);
  
  private final Gson c;
  
  private final Rx d = new Rx(FxResultDetailsPane.class, this);
  
  public FxResultDetailsPane() {
    this.c = (new GsonBuilder()).setPrettyPrinting().registerTypeHierarchyAdapter(byte[].class, new c()).create();
    FxUtil.a((Control)this.a, this.d);
    Tab tab1 = new Tab("Tree", (Node)this.a);
    tab1.setGraphic((Node)BootstrapIcons.diagram_2.glyph(new String[0]));
    getTabs().add(tab1);
    Tab tab2 = new Tab("JSON", (Node)this.b);
    tab2.setGraphic((Node)BootstrapIcons.filetype_json.glyph(new String[0]));
    getTabs().add(tab2);
  }
  
  public void a() {
    this.a.setRoot(null);
    this.b.b((String)null);
  }
  
  public void a(Result paramResult, ResultRow paramResultRow) {
    this.a.a(paramResult, paramResultRow);
    HashMap<Object, Object> hashMap = new HashMap<>();
    for (ResultColumn resultColumn : paramResult.f) {
      Object object = paramResultRow.a(resultColumn);
      if (resultColumn != null)
        hashMap.put(resultColumn.a, object); 
    } 
    try {
      this.b.b(this.c.toJson(hashMap));
    } catch (Throwable throwable) {
      this.b.b(this.d.H("jsonLoadError") + "\n" + this.d.H("jsonLoadError") + "\n" + String.valueOf(throwable));
    } 
    hashMap.clear();
  }
  
  public void a(Object paramObject) {
    this.b.b((paramObject != null) ? String.valueOf(paramObject) : "");
  }
}
