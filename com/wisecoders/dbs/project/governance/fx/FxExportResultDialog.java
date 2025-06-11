package com.wisecoders.dbs.project.governance.fx;

import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.fx.Dialog$;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.Window;

public class FxExportResultDialog extends Dialog$ {
  final TextArea a = new TextArea();
  
  public FxExportResultDialog(Window paramWindow, String paramString) {
    super(paramWindow);
    this.a.setText(paramString);
    this.a.setPrefSize(800.0D, 600.0D);
    initOwner(paramWindow);
    setResizable(true);
    getDialogPane().getButtonTypes().addAll((Object[])new ButtonType[] { ButtonType.OK, ButtonType.CANCEL });
    getDialogPane().setContent((Node)this.a);
    Objects.requireNonNull(this.a);
    Platform.runLater(this.a::requestFocus);
    setResultConverter(paramButtonType -> (paramButtonType != null && paramButtonType.getButtonData() == ButtonBar.ButtonData.OK_DONE) ? (String)getResult() : null);
  }
  
  public Node createContentPane() {
    return null;
  }
  
  public void createButtons() {
    createCloseButton();
    createActionButton("saveToFile");
  }
  
  public boolean apply() {
    setResult(this.a.getText());
    return false;
  }
  
  @Action
  public void saveToFile() {
    File file = FxFileChooser.a(getDialogScene(), "Save File", FileOperation.b, new FileType[] { FileType.i, FileType.g, FileType.j });
    if (file != null)
      try {
        PrintWriter printWriter = new PrintWriter(file);
        try {
          printWriter.println(this.a.getText());
          printWriter.close();
        } catch (Throwable throwable) {
          try {
            printWriter.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          } 
          throw throwable;
        } 
      } catch (IOException iOException) {
        this.rx.a(getDialogScene(), iOException);
      }  
  }
}
