package com.wisecoders.dbs.project.convert.fx;

import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.project.history.History;
import com.wisecoders.dbs.project.history.HistoryFile;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.RowPane$;
import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class FxConvertImportSchemaDialog extends ButtonDialog$ {
  private final Workspace a;
  
  private final History b = new History("SchemaImporter");
  
  private final ComboBox c = new ComboBox();
  
  private final ComboBox d = new ComboBox();
  
  private final TableView e = new TableView();
  
  private final ObservableList f = FXCollections.observableArrayList();
  
  private final List i = new ArrayList();
  
  public FxConvertImportSchemaDialog(WorkspaceWindow paramWorkspaceWindow) {
    super(paramWorkspaceWindow);
    this.a = paramWorkspaceWindow.getWorkspace();
    this.c.setEditable(true);
    this.c.setMaxWidth(2.147483647E9D);
    this.c.setOnAction(paramActionEvent -> {
          a(Paths.get((String)this.c.getValue(), new String[0]).toFile());
          this.rx.b();
        });
    this.e.setPrefSize(900.0D, 500.0D);
    this.e.setItems(this.f);
    this.d.getItems().addAll((Object[])a.values());
    for (HistoryFile historyFile : this.b.c())
      this.c.getItems().add(historyFile.b.getAbsolutePath()); 
  }
  
  public Node createContentPane() {
    return (Node)(new RowPane$()).g()
      .a(new Node[] { (Node)this.rx.e("chooseFileLabel"), (Node)this.c, (Node)this.rx.j("chooseFile") }).a(new Node[] { (Node)this.rx.e("formatLabel"), (Node)this.d }).b((Node)this.e);
  }
  
  public void createButtons() {
    createOkButton();
  }
  
  public boolean apply() {
    if (this.i.isEmpty() || this.f.isEmpty()) {
      this.rx.c(getDialogScene(), "selectValidFile.text");
      return false;
    } 
    if (this.d.getValue() == null) {
      this.rx.c(getDialogScene(), "selectFormat.text");
      return false;
    } 
    return true;
  }
  
  private void a() {
    Project project = new Project("Convert", "MySql");
  }
  
  @Action
  public void chooseFile() {
    File file = FxFileChooser.a(this.a, "Choose File", FileOperation.a, new FileType[] { FileType.D });
    if (file != null) {
      this.e.getColumns().clear();
      this.f.clear();
      this.i.clear();
      this.b.a(null, file);
      this.b.a(file);
      this.c.getItems().add(file.getAbsolutePath());
      this.c.setValue(file.getAbsolutePath());
      a(file);
    } 
  }
  
  private void a(File paramFile) {
    if (paramFile != null && paramFile.exists() && paramFile.isFile())
      try {
        if (paramFile.getName().toLowerCase().endsWith(".xlsx"))
          b(paramFile); 
        b();
      } catch (Exception exception) {
        this.rx.a(this.a, exception);
      }  
  }
  
  private void b() {
    this.e.getColumns().clear();
    for (Iterator<String> iterator = this.i.iterator(); iterator.hasNext(); ) {
      String str = iterator.next();
      TableColumn tableColumn = new TableColumn(str);
      tableColumn.setCellValueFactory(paramCellDataFeatures -> new SimpleStringProperty(String.valueOf(((b)paramCellDataFeatures.getValue()).get(paramString))));
      this.e.getColumns().add(tableColumn);
    } 
  }
  
  private void b(File paramFile) {
    Workbook workbook = WorkbookFactory.create(paramFile);
    Sheet sheet = workbook.getSheetAt(0);
    boolean bool = true;
    for (Row row : sheet) {
      if (bool) {
        for (Cell cell : row) {
          String str = cell.getStringCellValue();
          if (StringUtil.isEmptyTrim(str))
            str = "" + cell.getColumnIndex(); 
          this.i.add(str);
        } 
      } else {
        b b = new b(this);
        this.f.add(b);
        for (Cell cell : row) {
          int i = cell.getColumnIndex();
          if (cell.getCellType() != CellType.BLANK) {
            String str1;
            String str2;
            if (i < this.i.size()) {
              str1 = this.i.get(i);
            } else {
              str1 = "" + i;
              this.i.add(str1);
            } 
            switch (FxConvertImportSchemaDialog$1.a[cell.getCellType().ordinal()]) {
              case 1:
                b.put((K)str1, (V)Double.valueOf(cell.getNumericCellValue()));
              case 2:
                str2 = cell.getStringCellValue();
                if (!"null".equals(str2))
                  b.put((K)str1, (V)str2); 
              case 3:
                b.put((K)str1, (V)Boolean.valueOf(cell.getBooleanCellValue()));
            } 
          } 
        } 
      } 
      bool = false;
    } 
  }
}
