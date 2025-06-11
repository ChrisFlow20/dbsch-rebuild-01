package com.wisecoders.dbs.editors.csv.fx;

import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.loader.tasks.LoaderPresetSeparators;
import com.wisecoders.dbs.project.history.History;
import com.wisecoders.dbs.project.history.HistoryFile;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.AutoCompleteComboBox;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.FxUtil$FileCell;
import com.wisecoders.dbs.sys.fx.GridPane$;
import java.io.File;
import java.nio.charset.Charset;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

public class CsvEditorFileChooser extends ButtonDialog$ {
  private final History a = new History("CSVEditor");
  
  private final ComboBox b = new ComboBox();
  
  private final ComboBox c = new ComboBox();
  
  private final ComboBox d = new ComboBox();
  
  private final ComboBox e = new ComboBox();
  
  private final ComboBox f = new ComboBox();
  
  private final CheckBox i;
  
  private final boolean j;
  
  public CsvEditorFileChooser(WorkspaceWindow paramWorkspaceWindow, char paramChar1, String paramString, char paramChar2, boolean paramBoolean) {
    this(paramWorkspaceWindow, false);
    this.d.setValue("" + paramChar1);
    this.e.setValue(paramString);
    this.f.setValue("" + paramChar2);
    this.i.setSelected(paramBoolean);
  }
  
  public CsvEditorFileChooser(WorkspaceWindow paramWorkspaceWindow, boolean paramBoolean) {
    super(paramWorkspaceWindow);
    this.j = paramBoolean;
    this.i = this.rx.h("firstRowIsHeaderCheck", true);
    this.rx.a("flagNext", () -> StringUtil.isFilledTrim(this.b.getValue()));
    for (Charset charset1 : Charset.availableCharsets().values())
      this.c.getItems().add(charset1); 
    Charset charset = Charset.defaultCharset();
    this.c.setValue(charset);
    new AutoCompleteComboBox(this.c);
    this.b.setEditable(true);
    for (HistoryFile historyFile : this.a.c())
      this.b.getItems().add(historyFile.b.getAbsolutePath()); 
    this.b.setMaxWidth(2.147483647E9D);
    this.b.setValue("");
    this.b.setOnAction(paramActionEvent -> {
          if (paramBoolean)
            h(); 
          this.rx.b();
        });
    this.c.setOnAction(paramActionEvent -> {
          if (paramBoolean && b() != null && this.c.getValue() != null)
            a(b(), (Charset)this.c.getValue()); 
        });
    this.b.setCellFactory(paramListView -> new FxUtil$FileCell());
    this.d.setEditable(false);
    this.e.setEditable(false);
    this.f.setEditable(false);
    for (String str : LoaderPresetSeparators.c)
      this.d.getItems().add(str); 
    this.d.setButtonCell(new a());
    this.d.setCellFactory(paramListView -> new a());
    this.e.getItems().addAll((Object[])LoaderPresetSeparators.d);
    this.e.setButtonCell(new a());
    this.e.setCellFactory(paramListView -> new a());
    for (String str : LoaderPresetSeparators.e)
      this.f.getItems().add(str); 
  }
  
  public Node createContentPane() {
    GridPane$ gridPane$ = (new GridPane$()).l().a(new int[] { -2, -1, -2 });
    gridPane$.a((Node)this.rx.e("fileLabel"), "0,0,r,c");
    gridPane$.a((Node)this.b, "1,0,f,c");
    gridPane$.a((Node)this.rx.j("chooseFile"), "2,0,l,c");
    gridPane$.a((Node)this.rx.e("charsetLabel"), "0,1,r,c");
    gridPane$.a((Node)this.c, "1,1,l,c");
    gridPane$.a((Node)this.rx.h("formatSeparator"), "0,2,2,2,f,c");
    gridPane$.a((Node)this.i, "1,3,l,c");
    gridPane$.a((Node)this.rx.e("delimiterLabel"), "0,4,r,c");
    gridPane$.a((Node)this.d, "1,4,l,c");
    gridPane$.a((Node)this.rx.e("recordSeparatorLabel"), "0,5,r,c");
    gridPane$.a((Node)this.e, "1,5,l,c");
    gridPane$.a((Node)this.rx.e("quoteLabel"), "0,6,r,c");
    gridPane$.a((Node)this.f, "1,6,l,c");
    return (Node)gridPane$;
  }
  
  @Action
  public void chooseFile() {
    File file = FxFileChooser.a(getDialogScene(), "Choose Excel, JSon, Csv or text file", 
        
        this.j ? FileOperation.a : FileOperation.b, new FileType[] { FileType.D });
    if (file != null)
      this.b.setValue(file.getAbsolutePath()); 
  }
  
  public void createButtons() {
    Button button = createOkButton();
    if (!this.j)
      button.setText("Save"); 
    createCancelButton();
    createHelpButton("data-importer.html");
  }
  
  public Charset a() {
    return (Charset)this.c.getValue();
  }
  
  public File b() {
    String str = (String)this.b.getValue();
    if (StringUtil.isFilledTrim(str)) {
      File file = new File(str);
      if (file.exists() || !this.j)
        return file; 
    } 
    return null;
  }
  
  public boolean c() {
    if (b() == null) {
      showError("Cannot find file '" + (String)this.b.getValue() + "'.");
      this.b.requestFocus();
      return false;
    } 
    this.a.a(null, b());
    this.a.a(b());
    return true;
  }
  
  public boolean apply() {
    return c();
  }
  
  private void h() {
    File file = b();
    if (file != null)
      this.rx.a(new CsvEditorFileChooser$1(this, file)); 
  }
  
  private void a(File paramFile, Charset paramCharset) {
    this.rx.a(new CsvEditorFileChooser$2(this, paramFile, paramCharset));
  }
  
  public char d() {
    return StringUtil.isFilled(this.f.getValue()) ? ((String)this.f.getValue()).charAt(0) : '\'';
  }
  
  public String e() {
    return (String)this.e.getValue();
  }
  
  public char f() {
    return StringUtil.isFilled(this.d.getValue()) ? ((String)this.d.getValue()).charAt(0) : ',';
  }
  
  public boolean g() {
    return this.i.isSelected();
  }
}
