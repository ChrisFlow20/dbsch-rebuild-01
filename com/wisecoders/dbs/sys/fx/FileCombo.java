package com.wisecoders.dbs.sys.fx;

import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.project.history.History;
import com.wisecoders.dbs.project.history.HistoryFile;
import com.wisecoders.dbs.project.store.Pref;
import java.io.File;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;

public class FileCombo extends ComboBox {
  private static final String b = "Choose...";
  
  public final History a;
  
  private final String c;
  
  private final List d;
  
  public FileCombo(String paramString, FileType... paramVarArgs) {
    this.c = paramString;
    this.a = new History(paramString);
    this.d = Arrays.<FileType>stream(paramVarArgs).toList();
    setEditable(true);
    getItems().add("Choose...");
    for (HistoryFile historyFile : this.a.c())
      getItems().add(historyFile.b.getAbsolutePath()); 
    setOnAction(paramActionEvent -> {
          String str = (String)getSelectionModel().getSelectedItem();
          if ("Choose...".equals(str))
            a(); 
        });
  }
  
  private void a() {
    File file1 = Paths.get(Pref.c(this.c, System.getProperty("user.home")), new String[0]).toFile();
    File file2 = FxFileChooser.a(getScene(), "Input File", FileOperation.a, file1, (FileType[])this.d.toArray((Object[])new FileType[0]));
    if (file2 != null) {
      getItems().add(file2.getPath());
      setValue(file2.getPath());
      this.a.a(null, file2);
    } 
  }
}
