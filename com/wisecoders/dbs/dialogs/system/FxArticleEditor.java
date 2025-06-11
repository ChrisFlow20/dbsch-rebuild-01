package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.editors.md.MDTool;
import com.wisecoders.dbs.editors.text.StyledEditor;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Language;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.FileUtils;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.TabPane$;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.web.WebView;
import javafx.stage.Screen;
import javafx.stage.WindowEvent;

public class FxArticleEditor extends ButtonDialog$ implements WorkspaceWindow {
  private static final String a = "article.md";
  
  private static final String b = "article.html";
  
  private final WorkspaceWindow c;
  
  private final StyledEditor d = new StyledEditor(Language.b, false);
  
  private final WebView e = new WebView();
  
  private final ListView f = new ListView();
  
  private final Path i;
  
  private final Path j;
  
  private final File k;
  
  private static final String l = "Preview";
  
  public FxArticleEditor(WorkspaceWindow paramWorkspaceWindow, File paramFile) {
    super(paramWorkspaceWindow);
    this.c = paramWorkspaceWindow;
    this.k = paramFile;
    this.i = paramFile.toPath().resolve("article.md");
    this.j = paramFile.toPath().resolve("article.html");
    this.rx.a("flagSelectedFile", () -> (this.f.getSelectionModel().getSelectedItem() != null));
    File file = this.i.toFile();
    if (file.exists())
      try {
        FileInputStream fileInputStream = new FileInputStream(file);
        try {
          this.d.b(StringUtil.readStringFromInputStream(fileInputStream));
          fileInputStream.close();
        } catch (Throwable throwable) {
          try {
            fileInputStream.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          } 
          throw throwable;
        } 
      } catch (IOException iOException) {
        Log.b(iOException);
        this.rx.a(getDialogScene(), iOException);
      }  
    this.f.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramFile1, paramFile2) -> this.rx.b());
    this.f.setCellFactory(paramListView -> new a());
    a();
    this.rx.b();
  }
  
  private void a() {
    this.f.getItems().clear();
    if (this.k.exists())
      for (File file : (File[])Objects.<File[]>requireNonNull(this.k.listFiles())) {
        if (!"article.md".equals(file.getName()) && !"article.html".equals(file.getName()))
          this.f.getItems().add(file); 
      }  
  }
  
  private void b() {
    try {
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.j.toFile()));
      try {
        bufferedWriter.write(MDTool.a(this.d.t()));
        this.e.getEngine().load(this.j.toString());
        bufferedWriter.close();
      } catch (Throwable throwable) {
        try {
          bufferedWriter.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
    } catch (Exception exception) {
      Log.b(exception);
      this.rx.a(getDialogScene(), exception);
    } 
  }
  
  public Node createContentPane() {
    GridPane$ gridPane$ = (new GridPane$()).l().a(new int[] { -2, -1 }).b(new int[] { -1, -2 }).a((Node)this.rx.e("filesLabel"), "0,0,l,c").a((Node)this.f, "1,0,f,f").a((Node)(new FlowPane$()).g().a(this.rx.c(new String[] { "uploadImage", "addScreenshot", "deleteImage" }, )), "1,1,l,c");
    TabPane$ tabPane$ = (new TabPane$()).a("Article (Markdown)", (Node)this.d).a("Images", (Node)gridPane$).a("Preview", (Node)this.e);
    tabPane$.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramTab1, paramTab2) -> {
          if ("Preview".equals(paramTab2.getText()))
            b(); 
        });
    return (Node)tabPane$;
  }
  
  public void createButtons() {
    createActionButton("saveEditor");
    createActionButton("publishArticle");
    createHelpButton("https://dbschema.com/publish.html#markdown");
    createCloseButton();
  }
  
  public boolean apply() {
    return true;
  }
  
  @Action
  public void publishArticle() {
    saveEditor();
    (new FxPublishArticleDialog(this, this.k)).showDialog();
  }
  
  @Action
  public void saveEditor() {
    try {
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.i.toFile()));
      try {
        bufferedWriter.write(this.d.t());
        bufferedWriter.close();
      } catch (Throwable throwable) {
        try {
          bufferedWriter.close();
        } catch (Throwable throwable1) {
          throwable.addSuppressed(throwable1);
        } 
        throw throwable;
      } 
    } catch (IOException iOException) {
      Log.b(iOException);
      this.rx.a(getDialogScene(), iOException);
    } 
  }
  
  @Action
  public void uploadImage() {
    File file = FxFileChooser.a(getDialogScene(), "Choose File", FileOperation.a, new FileType[] { FileType.D });
    if (file != null) {
      try {
        FileUtils.a(file, new File(this.k.toURI().resolve(file.getName())));
      } catch (IOException iOException) {
        Log.b(iOException);
        this.rx.a(getDialogScene(), iOException);
      } 
      a();
    } 
  }
  
  @Action(d = "flagSelectedFile")
  public void deleteImage() {
    if (this.rx.a(getDialogScene(), "confirmDeleteImage")) {
      FileUtils.b((File)this.f.getSelectionModel().getSelectedItem());
      this.rx.a(getDialogScene(), "imageDeleted", new String[0]);
    } 
  }
  
  @Action
  public void addScreenshot() {
    ArrayList<i> arrayList = new ArrayList();
    for (Iterator<Screen> iterator = Screen.getScreens().iterator(); iterator.hasNext(); ) {
      Screen screen = iterator.next();
      i i = new i(screen, this.i);
      i.setOnHiding(paramWindowEvent -> {
            a();
            for (i i1 : paramList) {
              if (i1 != parami)
                i1.hide(); 
            } 
          });
      arrayList.add(i);
      i.show();
    } 
  }
  
  public Workspace getWorkspace() {
    return this.c.getWorkspace();
  }
  
  @Action
  public void markdownCheatSheet() {
    this.rx.a(getDialogScene(), "markdownCheatSheet", new String[0]);
  }
}
