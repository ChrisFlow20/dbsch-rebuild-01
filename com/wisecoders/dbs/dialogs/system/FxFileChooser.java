package com.wisecoders.dbs.dialogs.system;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.sys.Log;
import java.io.File;
import javafx.scene.Scene;
import javafx.stage.FileChooser;

public abstract class FxFileChooser {
  private static final String a = "FILECHOOSER:workdir";
  
  public static File a(Scene paramScene, String paramString, FileOperation paramFileOperation, FileType... paramVarArgs) {
    return a(paramScene, paramString, paramFileOperation, (File)null, paramVarArgs);
  }
  
  public static File a(Scene paramScene, String paramString1, String paramString2, FileOperation paramFileOperation, FileType... paramVarArgs) {
    if (paramString2 == null)
      paramString2 = Pref.c("FILECHOOSER:workdir", Sys.d.toString()); 
    return a(paramScene, paramString1, paramFileOperation, new File(paramString2), paramVarArgs);
  }
  
  public static File a(Scene paramScene, String paramString, FileOperation paramFileOperation, File paramFile, FileType... paramVarArgs) {
    FileChooser fileChooser = new FileChooser();
    if (paramFile != null && paramFile.exists()) {
      if (paramFile.isFile())
        paramFile = paramFile.getParentFile(); 
      fileChooser.setInitialDirectory(paramFile);
    } 
    boolean bool = true;
    for (FileType fileType : paramVarArgs) {
      FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(fileType.E, fileType.a());
      fileChooser.getExtensionFilters().add(extensionFilter);
      if (bool) {
        fileChooser.setSelectedExtensionFilter(extensionFilter);
        bool = false;
      } 
    } 
    fileChooser.setTitle(paramString);
    File file = (paramFileOperation == FileOperation.a) ? fileChooser.showOpenDialog(paramScene.getWindow()) : fileChooser.showSaveDialog(paramScene.getWindow());
    if (file != null) {
      if (paramFileOperation == FileOperation.b)
        if (!FileType.b(file)) {
          String str = (paramVarArgs[0]).F[0];
          if (!"*".equals(str))
            file = new File(String.valueOf(file.getAbsoluteFile()) + "." + String.valueOf(file.getAbsoluteFile())); 
        }  
      try {
        Pref.a("FILECHOOSER:workdir", String.valueOf(file.getParent()));
      } catch (Exception exception) {
        Log.b(exception);
      } 
    } 
    return file;
  }
}
