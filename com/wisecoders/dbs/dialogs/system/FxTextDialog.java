package com.wisecoders.dbs.dialogs.system;

import java.util.Objects;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextArea;
import javafx.stage.Window;

public class FxTextDialog extends Dialog {
  public FxTextDialog(Window paramWindow, String paramString1, String paramString2) {
    TextArea textArea = new TextArea(paramString2);
    textArea.setPrefSize(800.0D, 600.0D);
    setTitle(paramString1);
    initOwner(paramWindow);
    setResizable(true);
    getDialogPane().getButtonTypes().addAll((Object[])new ButtonType[] { ButtonType.OK, ButtonType.CANCEL });
    getDialogPane().setContent((Node)textArea);
    Objects.requireNonNull(textArea);
    Platform.runLater(textArea::requestFocus);
    setResultConverter(paramButtonType -> (paramButtonType == ButtonType.OK) ? paramTextArea.getText() : null);
  }
}
