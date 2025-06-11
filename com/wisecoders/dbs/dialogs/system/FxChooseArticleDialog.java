package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.FileUtils;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import java.io.File;
import java.util.Objects;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class FxChooseArticleDialog extends ButtonDialog$ {
  private final WorkspaceWindow a;
  
  private final ListView b = new ListView();
  
  private final File c;
  
  public FxChooseArticleDialog(WorkspaceWindow paramWorkspaceWindow) {
    super(paramWorkspaceWindow);
    this.a = paramWorkspaceWindow;
    this.c = Sys.e().toFile();
    this.rx.a("flagSelectedArticle", () -> (this.b.getSelectionModel().getSelectedItem() != null));
    this.b.setPlaceholder((Node)this.rx.e("articleListPlaceholder"));
    this.b.setCellFactory(paramListView -> new c());
    this.b.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramFile1, paramFile2) -> this.rx.b());
    this.b.setOnMouseClicked(paramMouseEvent -> {
          if (paramMouseEvent.getButton() == MouseButton.PRIMARY && paramMouseEvent.getClickCount() == 2 && this.b.getSelectionModel().getSelectedItem() != null)
            editArticle(); 
        });
    a();
    this.rx.b();
  }
  
  private void a() {
    this.b.getItems().clear();
    if (this.c.exists())
      for (File file : (File[])Objects.<File[]>requireNonNull(this.c.listFiles())) {
        if (file.isDirectory())
          this.b.getItems().add(file); 
      }  
  }
  
  public Node createContentPane() {
    return (Node)(new GridPane$()).l().a(new int[] { -2, -1 }).b(new int[] { -1, -2 }).a((Node)this.rx.e("articlesLabel"), "0,0,r,c")
      .a((Node)this.b, "1,0,f,f")
      .a((Node)(new FlowPane$()).a().a(this.rx.c(new String[] { "addArticle", "editArticle" }, )), "1,1,l,c");
  }
  
  public void createButtons() {
    createHelpButton("https://dbschema.com/publish.html");
    createCancelButton();
  }
  
  public boolean apply() {
    return true;
  }
  
  @Action
  public void addArticle() {
    String str = this.rx.b(getDialogScene(), "articleTitle");
    if (StringUtil.isFilledTrim(str)) {
      str = FileUtils.a(str);
      str = str.replaceAll("  ", " ").replaceAll(" ", "-");
      File file = new File(this.c.toURI().resolve(str.trim()));
      if (file.mkdir())
        (new FxArticleEditor(this.a, file)).showDialog(); 
    } 
  }
  
  @Action(b = "flagSelectedArticle")
  public void editArticle() {
    File file = (File)this.b.getSelectionModel().getSelectedItem();
    if (file != null) {
      hide();
      (new FxArticleEditor(this.a, file)).showDialog();
    } 
  }
}
