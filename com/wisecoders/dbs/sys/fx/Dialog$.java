package com.wisecoders.dbs.sys.fx;

import com.wisecoders.dbs.dbms.DbmsAdvices;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.dialogs.system.FxTechnicalSupportDialog;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.schema.ConnectivityTip;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.WebBrowserExternal;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import java.util.Objects;
import java.util.Optional;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Window;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.Notifications;

public abstract class Dialog$ extends Dialog {
  public final Rx rx;
  
  public final MaskerPane maskerPane = new MaskerPane();
  
  private Node a;
  
  private boolean b;
  
  public Dialog$(WorkspaceWindow paramWorkspaceWindow) {
    this(paramWorkspaceWindow, Modality.WINDOW_MODAL);
  }
  
  public Dialog$(WorkspaceWindow paramWorkspaceWindow, Modality paramModality) {
    this(paramModality);
    initOwner(paramWorkspaceWindow);
  }
  
  public Dialog$(Window paramWindow) {
    this(paramWindow, Modality.WINDOW_MODAL);
  }
  
  public Dialog$(Window paramWindow, Modality paramModality) {
    this(paramModality);
    if (paramWindow != null)
      initOwner(paramWindow); 
  }
  
  private boolean c = true;
  
  private boolean d;
  
  public void setDialogTitleAndHeader() {
    setDialogTitle(this.rx.a("dialog.title.text", new String[0]));
    setHeaderText(this.rx.a("dialog.header.text", new String[0]));
  }
  
  public Dialog$(Modality paramModality) {
    this.d = false;
    initModality(paramModality);
    this.maskerPane.setVisible(false);
    this.rx = new Rx(getClass(), this);
    setDialogTitleAndHeader();
    this.rx.a(this.maskerPane);
    setResizable(true);
    getDialogPane().addEventFilter(KeyEvent.KEY_PRESSED, paramKeyEvent -> {
          if ((paramKeyEvent.getCode() == KeyCode.ALT || paramKeyEvent.getCode() == KeyCode.ALT_GRAPH) && this.c && Pref.c("showAltKeyTip", 25)) {
            this.c = false;
            Notifications.create().owner(getDialogPane().getScene().getWindow()).hideAfter(Duration.seconds(5.0D)).text("Pressing the ALT key activated the HOTKEY mode.\nThe underlined letters on buttons are hotkeys.\nDisable the Hotkey mode by pressing the ALT key again.").position(Pos.TOP_RIGHT).show();
          } 
        });
  }
  
  public void setDialogTitle(String paramString) {
    if (paramString != null) {
      setTitle(paramString);
      if (!this.d) {
        Log.c("Dialog " + paramString);
        this.d = true;
      } 
    } 
  }
  
  public void requestFocusOn(Node paramNode) {
    Objects.requireNonNull(paramNode);
    Platform.runLater(paramNode::requestFocus);
  }
  
  public Button createCancelButton() {
    getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
    Button button = (Button)getDialogPane().lookupButton(ButtonType.CANCEL);
    String str = this.rx.H("cancel");
    if (str != null)
      button.setText(str); 
    button.addEventFilter(ActionEvent.ACTION, paramActionEvent -> {
          if (!cancel())
            paramActionEvent.consume(); 
        });
    return button;
  }
  
  public Button createCloseButton() {
    if (!getDialogPane().getButtonTypes().contains(ButtonType.CLOSE))
      getDialogPane().getButtonTypes().add(ButtonType.CLOSE); 
    Button button = (Button)getDialogPane().lookupButton(ButtonType.CLOSE);
    String str = this.rx.H("close");
    if (str != null)
      button.setText(str); 
    button.addEventFilter(ActionEvent.ACTION, paramActionEvent -> {
          if (!cancel())
            paramActionEvent.consume(); 
        });
    return button;
  }
  
  public Button createOkButton() {
    getDialogPane().getButtonTypes().add(ButtonType.OK);
    Button button = (Button)getDialogPane().lookupButton(ButtonType.OK);
    String str = this.rx.H("ok");
    if (str != null)
      button.setText(str); 
    button.addEventFilter(ActionEvent.ACTION, paramActionEvent -> {
          if (!apply())
            paramActionEvent.consume(); 
        });
    return button;
  }
  
  public Button createActionButton(String paramString) {
    return createActionButton(paramString, ButtonBar.ButtonData.OK_DONE);
  }
  
  public Button createActionButton(String paramString, ButtonBar.ButtonData paramButtonData) {
    ButtonType buttonType = new ButtonType(paramString, paramButtonData);
    getDialogPane().getButtonTypes().add(buttonType);
    return a(paramString, (Button)getDialogPane().lookupButton(buttonType));
  }
  
  public Button createActionButton(String paramString, ButtonType paramButtonType) {
    getDialogPane().getButtonTypes().add(paramButtonType);
    return a(paramString, (Button)getDialogPane().lookupButton(paramButtonType));
  }
  
  private Button a(String paramString, Button paramButton) {
    paramButton.setText(this.rx.b(paramString + ".text", new String[0]));
    this.rx.a(paramString, (ButtonBase)paramButton);
    paramButton.addEventFilter(ActionEvent.ACTION, paramActionEvent -> {
          EventHandler eventHandler = this.rx.a(paramString);
          eventHandler.handle((Event)paramActionEvent);
          paramActionEvent.consume();
        });
    return paramButton;
  }
  
  public void setInitFocusedNode(Node paramNode) {
    this.a = paramNode;
  }
  
  static final ButtonType g = new ButtonType("Help", ButtonBar.ButtonData.LEFT);
  
  static final ButtonType h = new ButtonType("Support Ticket", ButtonBar.ButtonData.LEFT);
  
  private String e;
  
  public Button createHelpButton(String paramString) {
    getDialogPane().getButtonTypes().add(g);
    Button button = (Button)getDialogPane().lookupButton(g);
    button.setGraphic((Node)BootstrapIcons.question_circle.glyph(new String[] { "glyph-green" }));
    String str = this.rx.H("help");
    if (str != null)
      button.setText(str); 
    button.addEventFilter(ActionEvent.ACTION, paramActionEvent -> {
          paramActionEvent.consume();
          if (paramString.startsWith("http")) {
            WebBrowserExternal.a(getDialogScene(), paramString);
          } else {
            WebBrowserExternal.a(getDialogScene(), paramString);
          } 
        });
    return button;
  }
  
  public Button createSupportButton(String paramString) {
    getDialogPane().getButtonTypes().add(h);
    Button button = (Button)getDialogPane().lookupButton(h);
    button.setGraphic((Node)BootstrapIcons.question_circle_fill.glyph(new String[] { "glyph-orange" }));
    button.addEventFilter(ActionEvent.ACTION, paramActionEvent -> {
          paramActionEvent.consume();
          (new FxTechnicalSupportDialog(getDialogScene().getWindow())).showDialog();
        });
    return button;
  }
  
  public Optional showDialog(WorkspaceWindow paramWorkspaceWindow) {
    if (paramWorkspaceWindow instanceof Dialog) {
      Dialog dialog = (Dialog)paramWorkspaceWindow;
      a(dialog.getDialogPane().getScene());
    } else if (paramWorkspaceWindow instanceof Scene) {
      a((Scene)paramWorkspaceWindow);
    } 
    return showDialog();
  }
  
  public Optional showDialog(Window paramWindow) {
    a(paramWindow.getScene());
    return showDialog();
  }
  
  public Optional showDialog(Node paramNode) {
    if (paramNode != null)
      a(paramNode.getScene()); 
    return showDialog();
  }
  
  public Optional showDialog() {
    if (this.b)
      return Optional.empty(); 
    Node node = createContentPane();
    if (node != null) {
      createButtons();
      StackPane stackPane = new StackPane();
      stackPane.getChildren().addAll((Object[])new Node[] { node, (Node)this.maskerPane });
      getDialogPane().setContent((Node)stackPane);
      getDialogPane().setMinHeight(-1.0D);
    } 
    try {
      if (this.a != null)
        Platform.runLater(() -> {
              this.a.requestFocus();
              Node node = this.a;
              if (node instanceof ComboBox) {
                ComboBox comboBox = (ComboBox)node;
                comboBox.getEditor().selectAll();
              } 
            }); 
      return showAndWait();
    } catch (Throwable throwable) {
      Log.b(throwable);
      return Optional.empty();
    } 
  }
  
  public boolean cancel() {
    return true;
  }
  
  private void a(Scene paramScene) {
    setX(paramScene.getX() - getWidth() / 2.0D);
    setY(paramScene.getY() - getHeight() / 2.0D);
  }
  
  protected void c(String paramString) {
    Alert$ alert$ = (new Alert$()).c(this.rx.H(paramString + ".Alert")).a(getDialogPane().getScene());
    String str = this.rx.a(paramString + ".Alert.title", new String[0]);
    if (str != null)
      alert$.a(str); 
    alert$.showAndWait();
  }
  
  public void showNotificationPane(String paramString) {
    String str = this.rx.H(paramString);
    if (getDialogScene().getWindow() != null && getDialogScene().getWindow().isFocused() && !StringUtil.areEqual(paramString, this.e)) {
      this.e = paramString;
      Notifications.create().owner(getDialogPane().getScene().getWindow())
        .text((str == null) ? paramString : str)
        .position(Pos.BOTTOM_LEFT)
        .show();
    } 
  }
  
  public Scene getDialogScene() {
    return getDialogPane().getScene();
  }
  
  public void showErrorAndAdvice(String paramString, Throwable paramThrowable) {
    this.rx.b(getDialogScene(), DbmsAdvices.a(paramString).a(paramThrowable, (ConnectivityTip)null), paramThrowable);
  }
  
  public void showError(String paramString) {
    this.rx.c(getDialogScene(), paramString);
  }
  
  public void showError(String paramString, Throwable paramThrowable) {
    this.rx.b(getDialogScene(), paramString, paramThrowable);
  }
  
  public void showError(String paramString1, Throwable paramThrowable, String paramString2, Callback paramCallback) {
    this.rx.a(getDialogScene(), paramString1, paramThrowable, paramString2, paramCallback);
  }
  
  public void showInformation(String paramString, String... paramVarArgs) {
    this.rx.a(getDialogScene(), paramString, paramVarArgs);
  }
  
  public String showInputString(String paramString) {
    return this.rx.b(getDialogScene(), paramString);
  }
  
  public String showInputString(String paramString1, String paramString2) {
    return this.rx.b(getDialogScene(), paramString1, paramString2);
  }
  
  public Alert$ showOptionsDialog(String paramString) {
    return this.rx.b(getDialogScene(), paramString, new String[0]);
  }
  
  public static void setRegionPrefHeight(Region paramRegion, double paramDouble) {
    paramRegion.setPrefHeight(paramDouble * Rx.h());
  }
  
  public static void setRegionPrefWidth(Region paramRegion, double paramDouble) {
    paramRegion.setPrefWidth(paramDouble * Rx.h());
  }
  
  public static void setRegionPrefSize(Region paramRegion, double paramDouble1, double paramDouble2) {
    paramRegion.setPrefSize(paramDouble1 * Rx.h(), paramDouble2 * Rx.h());
  }
  
  public void initOwner(WorkspaceWindow paramWorkspaceWindow) {
    if (paramWorkspaceWindow instanceof Dialog) {
      Dialog dialog = (Dialog)paramWorkspaceWindow;
      if (dialog.getDialogPane() != null && dialog.getDialogPane().getScene() != null) {
        initOwner(dialog.getDialogPane().getScene().getWindow());
        return;
      } 
    } 
    if (paramWorkspaceWindow instanceof Scene)
      initOwner(((Scene)paramWorkspaceWindow).getWindow()); 
  }
  
  public static void setManagedVisible(Node... paramVarArgs) {
    for (Node node : paramVarArgs)
      node.managedProperty().bind((ObservableValue)node.visibleProperty()); 
  }
  
  public void skipShowingDialog() {
    this.b = true;
  }
  
  public void setGraphic(BootstrapIcons paramBootstrapIcons) {
    Label label = paramBootstrapIcons.glyph(new String[0]);
    label.getStyleClass().add("glyph-white");
    label.setStyle("-fx-font-size: 3em;");
    setGraphic((Node)label);
  }
  
  public Rx getRx() {
    return this.rx;
  }
  
  public abstract Node createContentPane();
  
  public abstract void createButtons();
  
  public abstract boolean apply();
}
