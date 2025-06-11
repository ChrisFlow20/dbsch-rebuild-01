package com.wisecoders.dbs.sys.fx;

import com.wisecoders.dbs.dialogs.system.FxTechnicalSupportDialog;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StackTraceHelper;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import java.util.Map;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Modality;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class Alert$ extends Alert {
  public Alert$() {
    super(Alert.AlertType.INFORMATION);
    getDialogPane().getStyleClass().add("dialog-large");
    setTitle("Information Dialog");
    setResizable(true);
    getDialogPane().getScene().getWindow().setOnCloseRequest(paramWindowEvent -> hide());
  }
  
  public Alert$(Alert.AlertType paramAlertType) {
    super(paramAlertType);
    getDialogPane().getStyleClass().add("dialog-large");
    getDialogPane().setMaxSize(900.0D, 1200.0D);
    setResizable(true);
    getDialogPane().getScene().getWindow().setOnCloseRequest(paramWindowEvent -> hide());
  }
  
  public Alert$ a(Scene paramScene) {
    if (paramScene != null && paramScene.getWindow() != null)
      initOwner(paramScene.getWindow()); 
    return this;
  }
  
  public Alert$ a() {
    initModality(Modality.APPLICATION_MODAL);
    return this;
  }
  
  public Alert$ a(String paramString) {
    setTitle(paramString);
    return this;
  }
  
  public Alert$ b(String paramString) {
    setHeaderText(paramString);
    return this;
  }
  
  public Alert$ a(ButtonType paramButtonType, String paramString) {
    if (!getButtonTypes().contains(paramButtonType))
      getButtonTypes().add(paramButtonType); 
    ((Button)getDialogPane().lookupButton(paramButtonType)).setText(paramString);
    return this;
  }
  
  public Alert$ a(String paramString, Callback paramCallback) {
    getDialogPane().getButtonTypes().add(Dialog$.g);
    Button button = (Button)getDialogPane().lookupButton(Dialog$.g);
    if (button != null) {
      button.setText(paramString);
      button.setOnAction(paramActionEvent -> paramCallback.call(null));
      button.setGraphic((Node)BootstrapIcons.question_circle_fill.glyph(new String[] { "glyph-blue" }));
    } 
    return this;
  }
  
  public void a(Scene paramScene, String paramString) {
    getDialogPane().getButtonTypes().add(Dialog$.h);
    Button button = (Button)getDialogPane().lookupButton(Dialog$.h);
    if (button != null) {
      button.setText("Report a Bug");
      button.setGraphic((Node)BootstrapIcons.headset.glyph(new String[] { "glyph-orange" }));
      button.setOnAction(paramActionEvent -> Platform.runLater(()));
      button.setGraphic((Node)BootstrapIcons.headset.glyph(new String[] { "glyph-red" }));
    } 
  }
  
  public Alert$ a(ButtonType paramButtonType) {
    if (!getButtonTypes().contains(paramButtonType))
      getButtonTypes().add(paramButtonType); 
    return this;
  }
  
  public Alert$ a(ButtonType... paramVarArgs) {
    getButtonTypes().setAll((Object[])paramVarArgs);
    return this;
  }
  
  public Alert$ b() {
    getButtonTypes().clear();
    return this;
  }
  
  public Alert$ c(String paramString) {
    return a(paramString, (String)null, false);
  }
  
  public Alert$ a(String paramString1, String paramString2, boolean paramBoolean) {
    if (paramString1 != null && paramString1.startsWith("<html>")) {
      HtmlLabel htmlLabel = new HtmlLabel(paramString1);
      htmlLabel.setPrefSize(650.0D, 400.0D);
      if (paramBoolean)
        htmlLabel.getStyleClass().add("font-large"); 
      BorderPane borderPane = new BorderPane((Node)htmlLabel);
      borderPane.setPrefWidth(550.0D);
      if (paramString2 != null) {
        ImageView imageView = Rx.q(paramString2);
        BorderPane.setMargin((Node)imageView, new Insets(10.0D, 10.0D, 10.0D, 0.0D));
        borderPane.setLeft((Node)imageView);
      } 
      borderPane.setRight((Node)(new Hyperlink$((Node)BootstrapIcons.copy.glyph(new String[] { "glyph-32" }, ), paramActionEvent -> {
              ClipboardContent clipboardContent = new ClipboardContent();
              clipboardContent.putString(paramHtmlLabel.b());
              Clipboard.getSystemClipboard().setContent((Map)clipboardContent);
            })).a("Clipboard Copy"));
      getDialogPane().setContent((Node)borderPane);
    } else {
      setContentText(paramString1);
    } 
    return this;
  }
  
  public ButtonType d(String paramString) {
    setAlertType(Alert.AlertType.CONFIRMATION);
    setContentText(paramString);
    getDialogPane().getButtonTypes().addAll((Object[])new ButtonType[] { ButtonType.YES, ButtonType.NO });
    showAndWait();
    return (ButtonType)getResult();
  }
  
  public ButtonType e(String paramString) {
    setAlertType(Alert.AlertType.CONFIRMATION);
    setContentText(paramString);
    getDialogPane().getButtonTypes().addAll((Object[])new ButtonType[] { ButtonType.YES, ButtonType.NO, ButtonType.CANCEL });
    showAndWait();
    return (ButtonType)getResult();
  }
  
  public Alert$ a(Throwable paramThrowable) {
    if (paramThrowable != null)
      a(StackTraceHelper.a(paramThrowable), false); 
    return this;
  }
  
  public Alert$ b(Throwable paramThrowable) {
    if (paramThrowable != null)
      a(StackTraceHelper.a(paramThrowable), true); 
    return this;
  }
  
  public Alert$ a(String paramString, boolean paramBoolean) {
    TextArea textArea = new TextArea(paramString);
    if (paramBoolean)
      textArea.setStyle("-fx-font: 1.1em \"" + FxUtil.a(new String[] { "Courier New", "Lucida Sans Typewriter", "Monaco", "Menlo", "Courier", "Consolas", "Monospaced", "Serif", "System" }) + "\""); 
    textArea.setEditable(false);
    textArea.setWrapText(true);
    textArea.setMaxWidth(Double.MAX_VALUE);
    textArea.setMaxHeight(Double.MAX_VALUE);
    GridPane.setVgrow((Node)textArea, Priority.ALWAYS);
    GridPane.setHgrow((Node)textArea, Priority.ALWAYS);
    Label label = new Label("Details:");
    GridPane gridPane = new GridPane();
    gridPane.setVgap(5.0D);
    gridPane.setMaxWidth(Double.MAX_VALUE);
    gridPane.add((Node)label, 0, 0);
    gridPane.add((Node)textArea, 0, 1);
    getDialogPane().setExpandableContent((Node)gridPane);
    return this;
  }
  
  public ButtonBar.ButtonData c() {
    Optional<ButtonType> optional = showAndWait();
    return optional.isPresent() ? ((ButtonType)optional.get()).getButtonData() : ButtonType.CANCEL.getButtonData();
  }
}
