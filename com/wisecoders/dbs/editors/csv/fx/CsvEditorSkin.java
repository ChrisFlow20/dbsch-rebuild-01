package com.wisecoders.dbs.editors.csv.fx;

import com.wisecoders.dbs.editors.csv.model.CsvColumn;
import com.wisecoders.dbs.editors.csv.model.CsvModel;
import com.wisecoders.dbs.sys.IntUtil;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;
import javafx.scene.Node;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;

public class CsvEditorSkin extends SkinBase {
  private final BorderPane d = new BorderPane();
  
  public final CsvEditorTableView a;
  
  private final CsvEditorFind e = new CsvEditorFind(this);
  
  private final CsvEditorFindReplace f = new CsvEditorFindReplace(this);
  
  private Pattern g;
  
  int b;
  
  int c;
  
  public CsvEditorSkin(CsvEditor paramCsvEditor) {
    super(paramCsvEditor);
    this.b = 0;
    this.c = 0;
    this.a = new CsvEditorTableView(paramCsvEditor);
    this.d.setCenter((Node)this.a);
    getChildren().addAll((Object[])new Node[] { (Node)this.d });
    new CsvEditorBehaviour(this);
  }
  
  protected void a(Pattern paramPattern) {
    this.g = paramPattern;
  }
  
  void a() {
    this.b = this.c = 0;
  }
  
  boolean a(boolean paramBoolean) {
    CsvModel csvModel = ((CsvEditor)getSkinnable()).b;
    this.b = IntUtil.a(this.b, 0, csvModel.d.size() - 1);
    int i = this.b;
    boolean bool = false;
    while (this.g != null && !bool) {
      String[] arrayOfString = csvModel.a(i);
      for (CsvColumn csvColumn : csvModel.b) {
        if (csvColumn.a() > this.c && csvColumn.a() < arrayOfString.length && this.g.matcher(arrayOfString[csvColumn.a()]).find()) {
          TableColumn tableColumn = this.a.a(csvColumn);
          if (tableColumn != null) {
            this.a.getSelectionModel().clearAndSelect(i, tableColumn);
            this.a.scrollTo(i);
            this.a.scrollToColumn(tableColumn);
            this.c = csvColumn.a();
            this.b = i;
            return true;
          } 
        } 
      } 
      if (i < csvModel.d.size() - 1) {
        i++;
        this.c = 0;
      } else if (paramBoolean) {
        i = 0;
        this.c = 0;
      } else {
        bool = true;
      } 
      if (i == this.b)
        bool = true; 
    } 
    return false;
  }
  
  public void a(String paramString) {
    for (TablePosition tablePosition : this.a.getSelectionModel().getSelectedCells()) {
      if (tablePosition.getTableColumn() instanceof CsvEditorTableColumn) {
        CsvColumn csvColumn = ((CsvEditorTableColumn)tablePosition.getTableColumn()).b();
        int i = ((CsvEditor)getSkinnable()).b.f(tablePosition.getRow());
        String str = ((CsvEditor)getSkinnable()).b.b(csvColumn, i);
        ((CsvEditor)getSkinnable()).b.a(i, csvColumn, this.g.matcher(str).replaceAll(paramString));
      } 
    } 
  }
  
  public void b() {
    this.d.setTop((Node)this.e);
  }
  
  public void c() {
    StringBuilder stringBuilder = new StringBuilder();
    ArrayList<? extends CharSequence> arrayList = new ArrayList();
    int i = -1;
    for (TablePosition tablePosition : this.a.getSelectionModel().getSelectedCells()) {
      if (tablePosition.getTableColumn() instanceof CsvEditorTableColumn) {
        CsvColumn csvColumn = ((CsvEditorTableColumn)tablePosition.getTableColumn()).b();
        if (i > -1 && i != tablePosition.getRow()) {
          stringBuilder.append(String.join("\007", arrayList)).append("\n");
          arrayList.clear();
        } 
        i = tablePosition.getRow();
        arrayList.add(((CsvEditor)getSkinnable()).b.a(csvColumn, tablePosition.getRow()));
      } 
    } 
    if (!arrayList.isEmpty())
      stringBuilder.append(String.join("\007", arrayList)).append("\n"); 
    ClipboardContent clipboardContent = new ClipboardContent();
    clipboardContent.putString(stringBuilder.toString());
    Clipboard.getSystemClipboard().setContent((Map)clipboardContent);
  }
  
  public void d() {
    if (!this.a.getSelectionModel().getSelectedCells().isEmpty()) {
      TablePosition tablePosition = (TablePosition)this.a.getSelectionModel().getSelectedCells().get(0);
      if (tablePosition.getTableColumn() instanceof CsvEditorTableColumn) {
        byte b = 0;
        for (String str : Clipboard.getSystemClipboard().getString().split("\n")) {
          byte b1 = 0;
          for (String str1 : str.split("\007")) {
            int i = this.a.getColumns().indexOf(tablePosition.getTableColumn()) + b1;
            if (i > -1 && i < this.a.getColumns().size()) {
              TableColumn tableColumn = (TableColumn)this.a.getColumns().get(i);
              if (tableColumn instanceof CsvEditorTableColumn) {
                if (tablePosition.getRow() + b >= ((CsvEditor)getSkinnable()).b.d.size())
                  ((CsvEditor)getSkinnable()).b.b(); 
                ((CsvEditor)getSkinnable()).b.a(((CsvEditor)
                    getSkinnable()).b.f(tablePosition.getRow() + b), ((CsvEditorTableColumn)tableColumn)
                    .b(), str1);
              } 
            } 
            b1++;
          } 
          b++;
        } 
        this.a.refresh();
      } 
    } 
  }
  
  public void e() {
    this.d.setTop((Node)this.f);
  }
  
  public void dispose() {
    super.dispose();
    getChildren().removeAll((Object[])new Node[0]);
  }
  
  public void f() {
    this.d.setTop(null);
  }
}
