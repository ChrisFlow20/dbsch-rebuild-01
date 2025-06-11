package com.wisecoders.dbs.data.fx;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wisecoders.dbs.data.model.result.Result;
import com.wisecoders.dbs.data.model.result.ResultMapRow;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.editors.text.StyledEditor;
import com.wisecoders.dbs.editors.text.StyledEditorHighlight;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.HBox$;
import com.wisecoders.dbs.sys.fx.VBox$;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class FxScriptResultPane extends Tab {
  public final FxAbstractSqlEditor a;
  
  protected String b;
  
  private final FxMessageCombo d = new FxScriptResultPane$1(this);
  
  private final Rx e = new Rx(FxScriptResultPane.class, this);
  
  private final StyledEditor f = new StyledEditor();
  
  public final Result c = new FxScriptResultPane$2(this);
  
  private int[] g;
  
  private boolean h;
  
  private String d() {
    this.g = new int[this.c.C()];
    byte b1;
    for (b1 = 0; b1 < this.c.C(); b1++)
      this.g[b1] = Math.max(this.g[b1], Math.min(16, this.c.k(b1).length())); 
    for (b1 = 0; b1 < this.c.A(); b1++) {
      for (byte b = 0; b < this.c.C(); b++) {
        Object object = this.c.a(b1, b);
        this.g[b] = Math.max(this.g[b], String.valueOf(object).length());
      } 
    } 
    StringBuilder stringBuilder = new StringBuilder("\n");
    byte b2;
    for (b2 = 0; b2 < this.c.C(); b2++) {
      stringBuilder.append(a(this.c.k(b2), this.g[b2], false));
      stringBuilder.append(' ');
    } 
    stringBuilder.append('\n');
    for (b2 = 0; b2 < this.c.C(); b2++) {
      stringBuilder.append("=".repeat(Math.max(0, this.g[b2])));
      stringBuilder.append(' ');
    } 
    stringBuilder.append('\n');
    return stringBuilder.toString();
  }
  
  private String e() {
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b = 0; b < this.c.A(); b++) {
      for (byte b1 = 0; b1 < this.c.C(); b1++) {
        stringBuilder.append(a(this.c.a(b, b1), this.g[b1], this.c.g(b1)));
        stringBuilder.append(' ');
      } 
      stringBuilder.append("\n");
    } 
    return stringBuilder.toString();
  }
  
  private String f() {
    StringBuilder stringBuilder = new StringBuilder();
    for (byte b = 0; b < this.c.A(); b++) {
      Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
      stringBuilder.append("\n")
        .append(gson.toJson(((ResultMapRow)this.c.e(b)).a));
    } 
    return stringBuilder.toString();
  }
  
  private void g() {
    if (this.c.A() > 0 && this.c.e(0) instanceof ResultMapRow) {
      this.f.d(f());
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      if (this.g == null)
        stringBuilder.append(d()); 
      stringBuilder.append(e());
      this.f.d(stringBuilder.toString());
    } 
    this.c.y();
  }
  
  void a() {
    this.c.q();
    g();
    this.g = null;
  }
  
  private static String a(Object paramObject, int paramInt, boolean paramBoolean) {
    String str = (paramObject == null || paramObject.toString() == null) ? "null" : paramObject.toString();
    if (str.length() > paramInt)
      str = str.substring(0, paramInt); 
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = str.length(); i < paramInt; i++)
      stringBuilder.append(" "); 
    return paramBoolean ? (String.valueOf(stringBuilder) + String.valueOf(stringBuilder)) : (str + str);
  }
  
  public void a(String paramString) {
    this.d.a(paramString);
  }
  
  public void a(String paramString, Throwable paramThrowable) {
    this.d.a(paramString, paramThrowable);
  }
  
  public void a(String paramString, StyledEditorHighlight paramStyledEditorHighlight) {
    this.f.a("\n\n" + paramString + "\n\n\n", paramStyledEditorHighlight);
  }
  
  public void b() {
    this.f.x();
  }
  
  public Result c() {
    return this.c;
  }
  
  @Action(b = "flagIdle")
  public void save() {
    File file = FxFileChooser.a(this.a.getWorkspace(), "Save result to file", FileOperation.b, new FileType[] { FileType.i });
    if (file != null)
      try {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
          fileOutputStream.write(this.f.t().getBytes(StandardCharsets.UTF_8));
          Pref.a("last_save_dir", file.getPath());
          fileOutputStream.close();
        } catch (Throwable throwable) {
          try {
            fileOutputStream.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          } 
          throw throwable;
        } 
      } catch (Throwable throwable) {
        a("Exception by saving to file: " + throwable.getLocalizedMessage(), throwable);
      }  
  }
  
  @Action(b = "flagIdle")
  public void print() {}
  
  FxScriptResultPane(FxAbstractSqlEditor paramFxAbstractSqlEditor, String paramString) {
    this.h = true;
    setText(paramString);
    this.a = paramFxAbstractSqlEditor;
    this.e.a("flagIdle", () -> this.h);
    this.c.i(Dbms.get(paramFxAbstractSqlEditor.d()).getSqlRecordsPerPage());
    HBox$ hBox$ = new HBox$();
    hBox$.getStyleClass().add("tool-bar");
    hBox$.getChildren().addAll((Object[])new Node[] { (Node)this.e.j("save"), (Node)this.d });
    this.d.setPrefWidth(2.147483647E9D);
    HBox.setHgrow((Node)this.d, Priority.SOMETIMES);
    VBox$ vBox$ = new VBox$();
    VBox.setVgrow((Node)this.f, Priority.ALWAYS);
    vBox$.getChildren().addAll((Object[])new Node[] { (Node)hBox$, (Node)this.f });
    setContent((Node)vBox$);
  }
  
  public void a(boolean paramBoolean) {
    this.h = paramBoolean;
    this.e.b();
  }
  
  public void b(String paramString) {
    this.f.d(paramString);
  }
  
  public void c(String paramString) {
    this.f.e(paramString);
  }
}
