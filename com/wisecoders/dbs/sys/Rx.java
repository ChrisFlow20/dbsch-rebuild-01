package com.wisecoders.dbs.sys;

import com.wisecoders.dbs.config.model.LetterCase;
import com.wisecoders.dbs.dbms.DbmsAdvices;
import com.wisecoders.dbs.dialogs.system.Snapshot;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.schema.ConnectivityTip;
import com.wisecoders.dbs.sys.fx.Alert$;
import com.wisecoders.dbs.sys.fx.FxUtil;
import com.wisecoders.dbs.sys.fx.HtmlLabel;
import com.wisecoders.dbs.sys.fx.Menu$;
import com.wisecoders.dbs.sys.fx.NumericTextField$;
import com.wisecoders.dbs.sys.fx.ProgressBarWithText;
import com.wisecoders.dbs.sys.fx.ResourcesFlagCallback;
import com.wisecoders.dbs.sys.fx.Separator$;
import com.wisecoders.dbs.sys.fx.VBox$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Control;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.RadioButton;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.MaskerPane;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.ToggleSwitch;

public class Rx extends SRx {
  public static final String a = "e4se";
  
  public static final String b = "-fx-background-insets: 0;";
  
  public Rx(Class paramClass, Object paramObject) {
    super(paramClass, paramObject);
  }
  
  public Node a(Node paramNode, String paramString) {
    VBox$ vBox$ = (new VBox$()).b(5);
    Label label = new Label(b(paramString, new String[0]));
    label.setStyle("-fx-font-weight:bold;");
    vBox$.getChildren().addAll((Object[])new Node[] { (Node)label, paramNode });
    VBox.setVgrow(paramNode, Priority.ALWAYS);
    vBox$.setPadding(new Insets(10.0D, 0.0D, 5.0D, 0.0D));
    return (Node)vBox$;
  }
  
  public EventHandler a(String paramString) {
    try {
      Method method = I(paramString);
      Action action = method.<Action>getAnnotation(Action.class);
      if (action == null)
        throw new InvalidParameterException("Method '" + paramString + "' is missing the com.wisecoders.dbs.sys.Action annotation"); 
      return paramActionEvent -> {
          try {
            Log.c(" > " + paramString);
            Object object = paramMethod.invoke(this.g, new Object[0]);
            if (object instanceof Task)
              a((Task)object); 
          } catch (InvocationTargetException invocationTargetException) {
            Log.a("Error handling method '" + paramString + "' in class " + this.f.getName() + ": ", invocationTargetException.getTargetException());
            a(invocationTargetException.getTargetException());
          } catch (Throwable throwable) {
            Log.a("Error handling method '" + paramString + "' in class " + this.f.getName() + ": ", throwable);
            Log.f(StackTraceHelper.a(throwable));
            a((throwable.getCause() != null) ? throwable.getCause() : throwable);
          } 
        };
    } catch (NoSuchMethodException noSuchMethodException) {
      Log.b(noSuchMethodException);
      return null;
    } 
  }
  
  private Method I(String paramString) {
    try {
      return this.f.getDeclaredMethod(paramString, new Class[0]);
    } catch (NoSuchMethodException noSuchMethodException) {
      Class clazz = this.f;
      while ((clazz = clazz.getSuperclass()) != null) {
        try {
          return clazz.getDeclaredMethod(paramString, new Class[0]);
        } catch (NoSuchMethodException noSuchMethodException1) {}
      } 
      throw noSuchMethodException;
    } 
  }
  
  private void a(Throwable paramThrowable) {
    if (this.g instanceof Dialog) {
      a(((Dialog)this.g).getDialogPane().getScene(), paramThrowable);
    } else if (this.g instanceof Scene) {
      a((Scene)this.g, paramThrowable);
    } else if (this.g instanceof Region) {
      a(((Region)this.g).getScene(), paramThrowable);
    } 
  }
  
  public void a(Class paramClass) {
    for (Future future : j) {
      if (!future.isDone() && !future.isCancelled() && paramClass.isAssignableFrom(future.getClass()))
        future.cancel(true); 
    } 
  }
  
  public static final ExecutorService c = Executors.newFixedThreadPool(5);
  
  private static final List j = new ArrayList();
  
  public void a(Task paramTask) {
    a(paramTask, true);
  }
  
  public void a(Task paramTask, boolean paramBoolean) {
    j.removeIf(Future::isDone);
    if (paramTask != null) {
      if (this.n != null && paramBoolean) {
        this.n.textProperty().bind((ObservableValue)paramTask.messageProperty());
        this.n.progressProperty().bind((ObservableValue)paramTask.progressProperty());
        if (this.n.getProgressNode() instanceof Region)
          ((Region)this.n.getProgressNode()).setMinSize(40.0D, 40.0D); 
        Pane pane = (Pane)this.n.lookup(".masker-center");
        if (pane != null) {
          Button button = null;
          for (Node node : pane.getChildren()) {
            if (node instanceof Button)
              button = (Button)node; 
          } 
          if (button == null) {
            button = new Button("Cancel");
            pane.getChildren().add(button);
          } 
          button.setOnAction(paramActionEvent -> paramTask.cancel(true));
        } 
      } 
      if (this.m != null) {
        this.m.b.progressProperty().bind((ObservableValue)paramTask.progressProperty());
        this.m.c.textProperty().bind((ObservableValue)Bindings.format("%1.80s", new Object[] { paramTask.messageProperty() }));
        this.m.a.setOnAction(paramActionEvent -> paramTask.cancel(true));
      } 
      paramTask.stateProperty().addListener((paramObservableValue, paramState1, paramState2) -> {
            switch (Rx$1.c[paramTask.getState().ordinal()]) {
              case 1:
              case 2:
              case 3:
                if (this.n != null && paramBoolean)
                  this.n.setVisible(true); 
                if (this.m != null)
                  this.m.setVisible(true); 
                break;
              default:
                if (this.n != null) {
                  this.n.setVisible(false);
                  this.n.textProperty().unbind();
                  this.n.progressProperty().unbind();
                  Pane pane = (Pane)this.n.lookup(".masker-center");
                  if (pane != null) {
                    Button button = null;
                    for (Node node : pane.getChildren()) {
                      if (node instanceof Button)
                        button = (Button)node; 
                    } 
                    if (button != null)
                      pane.getChildren().remove(button); 
                  } 
                } 
                if (this.m != null) {
                  this.m.setVisible(false);
                  this.m.b.progressProperty().unbind();
                  this.m.c.textProperty().unbind();
                  this.m.a.setOnAction(null);
                } 
                break;
            } 
            b();
          });
      j.add(c.submit((Runnable)paramTask));
    } 
  }
  
  public boolean b(Class paramClass) {
    for (Future future : j) {
      if (!future.isDone() && !future.isCancelled() && paramClass.isAssignableFrom(future.getClass()))
        return true; 
    } 
    return false;
  }
  
  public boolean b(String paramString) {
    return (this.h.containsKey(paramString) && ((Rx$ResourcesBooleanProperty)this.h.get(paramString)).get());
  }
  
  private void a(Object paramObject, String paramString) {
    try {
      Method method = I(paramString);
      Pro pro = method.<Pro>getAnnotation(Pro.class);
      if (pro != null)
        if (paramObject instanceof MenuItem) {
          MenuItem menuItem = (MenuItem)paramObject;
          menuItem.getStyleClass().add("e4se");
        } else if (paramObject instanceof Node) {
          Node node = (Node)paramObject;
          node.getStyleClass().add("e4se");
        }  
      Action action = method.<Action>getAnnotation(Action.class);
      if (action == null)
        throw new InvalidParameterException("Method '" + paramString + "' is missing the com.wisecoders.dbs.sys.Action annotation"); 
      String str1 = action.b();
      String str2 = action.c();
      String str3 = action.d();
      if (StringUtil.isFilledTrim(action.b())) {
        Rx$ResourcesBooleanProperty rx$ResourcesBooleanProperty = (Rx$ResourcesBooleanProperty)this.h.get(str1);
        if (rx$ResourcesBooleanProperty == null)
          throw new NullPointerException("Flag not found '" + str1 + "'"); 
        if (paramObject instanceof MenuItem) {
          MenuItem menuItem = (MenuItem)paramObject;
          menuItem.disableProperty().bind((ObservableValue)rx$ResourcesBooleanProperty.not());
        } else if (paramObject instanceof Node) {
          Node node = (Node)paramObject;
          node.disableProperty().bind((ObservableValue)rx$ResourcesBooleanProperty.not());
        } 
      } else if (StringUtil.isFilledTrim(action.c())) {
        Rx$ResourcesBooleanProperty rx$ResourcesBooleanProperty = (Rx$ResourcesBooleanProperty)this.h.get(str2);
        if (rx$ResourcesBooleanProperty == null)
          throw new NullPointerException("Flag not found '" + str2 + "'"); 
        if (pro != null && License.a().f()) {
          if (paramObject instanceof MenuItem) {
            MenuItem menuItem = (MenuItem)paramObject;
            menuItem.setDisable(true);
          } else if (paramObject instanceof Node) {
            Node node = (Node)paramObject;
            node.setDisable(true);
          } 
        } else if (paramObject instanceof MenuItem) {
          MenuItem menuItem = (MenuItem)paramObject;
          menuItem.disableProperty().bind((ObservableValue)rx$ResourcesBooleanProperty);
        } else if (paramObject instanceof Node) {
          Node node = (Node)paramObject;
          node.disableProperty().bind((ObservableValue)rx$ResourcesBooleanProperty);
        } 
      } 
      if (StringUtil.isFilledTrim(str3)) {
        Rx$ResourcesBooleanProperty rx$ResourcesBooleanProperty = (Rx$ResourcesBooleanProperty)this.h.get(str3);
        if (rx$ResourcesBooleanProperty == null)
          throw new NullPointerException("Flag not found '" + str3 + "'"); 
        if (paramObject instanceof CheckMenuItem) {
          CheckMenuItem checkMenuItem = (CheckMenuItem)paramObject;
          checkMenuItem.selectedProperty().bindBidirectional((Property)rx$ResourcesBooleanProperty);
        } else if (paramObject instanceof RadioMenuItem) {
          RadioMenuItem radioMenuItem = (RadioMenuItem)paramObject;
          radioMenuItem.selectedProperty().bindBidirectional((Property)rx$ResourcesBooleanProperty);
        } else if (paramObject instanceof ToggleButton) {
          ToggleButton toggleButton = (ToggleButton)paramObject;
          toggleButton.selectedProperty().bindBidirectional((Property)rx$ResourcesBooleanProperty);
        } 
      } 
    } catch (NoSuchMethodException noSuchMethodException) {
      noSuchMethodException.printStackTrace();
    } 
  }
  
  public static final HashMap d = new HashMap<>();
  
  private ResourcesFlagCallback k;
  
  private static final String l = "  ";
  
  private ProgressBarWithText m;
  
  private MaskerPane n;
  
  public static Image c(String paramString) {
    if (d.containsKey(paramString))
      return (Image)d.get(paramString); 
    Image image = new Image("/icons/" + paramString);
    d.put(paramString, image);
    return image;
  }
  
  public static Image d(String paramString) {
    return (Thread.currentThread().getContextClassLoader().getResource("icons/" + paramString) != null) ? c(paramString) : null;
  }
  
  public static Node a() {
    return (Node)BootstrapIcons.pencil.glyph(new String[] { "glyph-orange" });
  }
  
  public void b() {
    if (this.k != null)
      this.k.evaluate(); 
    for (String str : this.h.keySet())
      ((Rx$ResourcesBooleanProperty)this.h.get(str)).a(); 
  }
  
  public void a(ResourcesFlagCallback paramResourcesFlagCallback) {
    this.k = paramResourcesFlagCallback;
  }
  
  public BooleanProperty a(String paramString, ResourcesFlagCallback paramResourcesFlagCallback) {
    if (this.h.containsKey(paramString))
      throw new NullPointerException("Event '" + paramString + "' already defined."); 
    Rx$ResourcesBooleanProperty rx$ResourcesBooleanProperty = new Rx$ResourcesBooleanProperty(paramResourcesFlagCallback);
    this.h.put(paramString, rx$ResourcesBooleanProperty);
    b();
    return (BooleanProperty)rx$ResourcesBooleanProperty;
  }
  
  public Label a(String paramString, Node paramNode) {
    Label label = e(paramString);
    label.setLabelFor(paramNode);
    label.setMnemonicParsing(true);
    return label;
  }
  
  public Label e(String paramString) {
    String str = H(paramString);
    Label label = new Label(str);
    label.setMnemonicParsing(true);
    label.setGraphic(p(paramString));
    b((Node)label, paramString);
    b((Control)label, paramString);
    return label;
  }
  
  public Tab b(String paramString, Node paramNode) {
    Tab tab = new Tab(a(paramString + ".text", new String[0]), paramNode);
    tab.setGraphic(p(paramString));
    return tab;
  }
  
  public HBox f(String paramString) {
    Label label = new Label(H(paramString));
    label.setGraphic(p(paramString));
    b((Node)label, paramString);
    b((Control)label, paramString);
    HBox hBox = new HBox();
    hBox.getChildren().addAll((Object[])new Node[] { (Node)j(), (Node)label, (Node)j() });
    hBox.setAlignment(Pos.CENTER);
    return hBox;
  }
  
  private Separator j() {
    Separator separator = new Separator();
    HBox.setHgrow((Node)separator, Priority.ALWAYS);
    return separator;
  }
  
  public HtmlLabel g(String paramString) {
    String str = H(paramString);
    HtmlLabel htmlLabel = new HtmlLabel(str);
    b((Node)htmlLabel, paramString);
    b(htmlLabel, paramString);
    return htmlLabel;
  }
  
  public Separator$ h(String paramString) {
    return new Separator$(H(paramString));
  }
  
  public Text i(String paramString) {
    String str = H(paramString);
    Text text = new Text(str);
    b((Node)text, paramString);
    return text;
  }
  
  public Button j(String paramString) {
    return k(paramString, true);
  }
  
  public Button k(String paramString) {
    return k(paramString, false);
  }
  
  public ToggleButton l(String paramString) {
    ToggleButton toggleButton = n(paramString);
    toggleButton.setText(null);
    return toggleButton;
  }
  
  public ToggleButton m(String paramString) {
    ToggleButton toggleButton = n(paramString);
    toggleButton.setText(null);
    return toggleButton;
  }
  
  public List a(String... paramVarArgs) {
    ArrayList<Button> arrayList = new ArrayList();
    for (String str : paramVarArgs)
      arrayList.add(k(str)); 
    return arrayList;
  }
  
  public ToggleButton a(String paramString, boolean paramBoolean) {
    ToggleButton toggleButton = new ToggleButton(K(paramString));
    toggleButton.setSelected(paramBoolean);
    toggleButton.setGraphic(p(paramString));
    toggleButton.setMnemonicParsing(true);
    c((Control)toggleButton, paramString);
    b((Node)toggleButton, paramString);
    return toggleButton;
  }
  
  public ToggleButton n(String paramString) {
    return b(paramString, false);
  }
  
  public ToggleButton b(String paramString, boolean paramBoolean) {
    ToggleButton toggleButton = new ToggleButton(K(paramString));
    toggleButton.setSelected(paramBoolean);
    a(paramString, (ButtonBase)toggleButton);
    return toggleButton;
  }
  
  private Button k(String paramString, boolean paramBoolean) {
    Button button = paramBoolean ? new Button(K(paramString)) : new Button();
    a(paramString, (ButtonBase)button);
    return button;
  }
  
  public List b(String... paramVarArgs) {
    ArrayList<Hyperlink> arrayList = new ArrayList();
    for (String str : paramVarArgs)
      arrayList.add(o(str)); 
    return arrayList;
  }
  
  public Hyperlink o(String paramString) {
    Hyperlink hyperlink = new Hyperlink(K(paramString));
    a(paramString, (ButtonBase)hyperlink);
    b((Control)hyperlink, paramString);
    return hyperlink;
  }
  
  private void b(Control paramControl, String paramString) {
    String str = a(paramString + ".tooltip", new String[0]);
    a(paramControl, str);
    if (a(paramString + ".mandatory", new String[0]) != null)
      paramControl.getStyleClass().add("mandatory-icon"); 
  }
  
  public static void a(Control paramControl, String paramString) {
    if (paramString != null) {
      paramControl.getStyleClass().add("tooltip-icon");
      Tooltip tooltip = new Tooltip();
      if (paramString.toLowerCase().startsWith("<html>")) {
        HtmlLabel htmlLabel = new HtmlLabel(paramString, false);
        htmlLabel.setMinWidth(Double.NEGATIVE_INFINITY);
        tooltip.setGraphic((Node)htmlLabel);
        tooltip.setContentDisplay(ContentDisplay.BOTTOM);
      } else {
        tooltip.setText(paramString);
      } 
      tooltip.setHideOnEscape(true);
      paramControl.setOnMouseEntered(paramMouseEvent -> {
            Bounds bounds = paramControl.localToScreen(paramControl.getBoundsInLocal());
            paramTooltip.show((Node)paramControl, bounds.getMinX(), bounds.getMaxY() + 15.0D);
          });
      paramControl.setOnMouseExited(paramMouseEvent -> paramTooltip.hide());
    } 
  }
  
  private void c(Control paramControl, String paramString) {
    String str = a(paramString + ".tooltip", new String[0]);
    if (str != null) {
      Tooltip tooltip = new Tooltip();
      if (str.toLowerCase().startsWith("<html>")) {
        tooltip.setContentDisplay(ContentDisplay.BOTTOM);
        tooltip.setGraphic((Node)new HtmlLabel(str));
      } else {
        tooltip.setText(str);
      } 
      paramControl.setTooltip(tooltip);
    } 
  }
  
  public List c(String... paramVarArgs) {
    ArrayList<Button> arrayList = new ArrayList();
    for (String str : paramVarArgs)
      arrayList.add(j(str)); 
    return arrayList;
  }
  
  public SplitMenuButton c(String paramString, boolean paramBoolean) {
    SplitMenuButton splitMenuButton = g(paramString, paramBoolean);
    splitMenuButton.setText(null);
    return splitMenuButton;
  }
  
  public MenuButton d(String paramString, boolean paramBoolean) {
    MenuButton menuButton = f(paramString, paramBoolean);
    menuButton.setContentDisplay(ContentDisplay.TOP);
    menuButton.setGraphicTextGap(0.0D);
    return menuButton;
  }
  
  public MenuButton e(String paramString, boolean paramBoolean) {
    MenuButton menuButton = f(paramString, paramBoolean);
    menuButton.setText(null);
    return menuButton;
  }
  
  private String J(String paramString) {
    return paramString.contains("$") ? paramString.substring(0, paramString.indexOf("$")) : paramString;
  }
  
  public MenuButton f(String paramString, boolean paramBoolean) {
    MenuButton menuButton = new MenuButton();
    String str = J(paramString);
    menuButton.setPadding(FxUtil.a);
    menuButton.setText(K(paramString));
    menuButton.setOnAction(paramBoolean ? a(str) : (paramActionEvent -> paramMenuButton.show()));
    menuButton.setGraphic(p(paramString));
    c((Control)menuButton, paramString);
    if (paramString.contains("$")) {
      menuButton.setContentDisplay(ContentDisplay.TOP);
      menuButton.setGraphicTextGap(0.0D);
    } 
    if (paramBoolean)
      a(menuButton, str); 
    b((Node)menuButton, paramString);
    return menuButton;
  }
  
  public SplitMenuButton g(String paramString, boolean paramBoolean) {
    SplitMenuButton splitMenuButton = new SplitMenuButton();
    String str = J(paramString);
    splitMenuButton.setText(K(paramString));
    splitMenuButton.setOnAction(paramBoolean ? a(str) : (paramActionEvent -> paramSplitMenuButton.show()));
    c((Control)splitMenuButton, paramString);
    splitMenuButton.setGraphic(p(paramString));
    if (paramString.contains("$")) {
      splitMenuButton.setContentDisplay(ContentDisplay.TOP);
      splitMenuButton.setGraphicTextGap(0.0D);
    } 
    if (paramBoolean)
      a(splitMenuButton, str); 
    b((Node)splitMenuButton, paramString);
    return splitMenuButton;
  }
  
  public void a(String paramString, ButtonBase paramButtonBase) {
    String str = J(paramString);
    paramButtonBase.setOnAction(a(str));
    paramButtonBase.setGraphic(p(paramString));
    paramButtonBase.setMnemonicParsing(true);
    c((Control)paramButtonBase, paramString);
    b((Node)paramButtonBase, paramString);
    a(paramButtonBase, str);
    if (paramString.contains("$")) {
      paramButtonBase.setContentDisplay(ContentDisplay.TOP);
      paramButtonBase.setGraphicTextGap(0.0D);
    } 
  }
  
  private void b(Node paramNode, String paramString) {
    String str1 = a(paramString + ".cssClass", new String[0]);
    if (str1 != null)
      paramNode.getStyleClass().addAll(Arrays.<String>stream(str1.split(",")).map(String::trim).toList()); 
    String str2 = a(paramString + ".stylesheet", new String[0]);
    if (str2 != null)
      paramNode.setStyle(str2); 
  }
  
  public Node p(String paramString) {
    String str;
    if ((str = a(paramString + ".icon", new String[0])) != null) {
      if (str.startsWith("bi-"))
        return (Node)r(str); 
      try {
        return (Node)q(str);
      } catch (IllegalArgumentException illegalArgumentException) {
        Log.a("Image '" + str + "' not found.", illegalArgumentException);
      } 
    } 
    return null;
  }
  
  public static ImageView c() {
    return q("folder-expanded.png");
  }
  
  public static ImageView q(String paramString) {
    ImageView imageView = new ImageView(c(paramString));
    imageView.getStyleClass().add("transparent-on-dark-image");
    return imageView;
  }
  
  public static Label r(String paramString) {
    Label label = null;
    String[] arrayOfString = paramString.split(":");
    if (arrayOfString[0].startsWith("bi-"))
      label = BootstrapIcons.resolve(arrayOfString[0].substring("bi-".length())); 
    if (label != null && arrayOfString.length == 2)
      label.getStyleClass().addAll((Object[])arrayOfString[1].split(",")); 
    return label;
  }
  
  public TextArea s(String paramString) {
    TextArea textArea = new TextArea();
    String str = H(paramString + ".promptText");
    if (str != null)
      textArea.setPromptText(str); 
    return textArea;
  }
  
  public TextField t(String paramString) {
    TextField textField = new TextField();
    String str = H(paramString + ".promptText");
    b((Control)textField, paramString);
    b((Node)textField, paramString);
    if (str != null)
      textField.setPromptText(str); 
    return textField;
  }
  
  public NumericTextField$ u(String paramString) {
    NumericTextField$ numericTextField$ = new NumericTextField$();
    String str = H(paramString + ".promptText");
    b((Control)numericTextField$, paramString);
    b((Node)numericTextField$, paramString);
    if (str != null)
      numericTextField$.setPromptText(str); 
    return numericTextField$;
  }
  
  public CheckBox h(String paramString, boolean paramBoolean) {
    CheckBox checkBox = v(paramString);
    checkBox.setSelected(paramBoolean);
    return checkBox;
  }
  
  public CheckBox v(String paramString) {
    CheckBox checkBox = new CheckBox(H(paramString) + "  ");
    checkBox.setMnemonicParsing(true);
    checkBox.textOverrunProperty().setValue(OverrunStyle.CLIP);
    b((Node)checkBox, paramString);
    b((Control)checkBox, paramString);
    return checkBox;
  }
  
  public RadioButton i(String paramString, boolean paramBoolean) {
    RadioButton radioButton = w(paramString);
    radioButton.setSelected(paramBoolean);
    return radioButton;
  }
  
  public ToggleSwitch j(String paramString, boolean paramBoolean) {
    ToggleSwitch toggleSwitch = new ToggleSwitch(H(paramString) + "  ");
    toggleSwitch.setContentDisplay(ContentDisplay.LEFT);
    toggleSwitch.setMnemonicParsing(true);
    toggleSwitch.textOverrunProperty().set(OverrunStyle.CLIP);
    b((Node)toggleSwitch, paramString);
    b((Control)toggleSwitch, paramString);
    toggleSwitch.setSelected(paramBoolean);
    return toggleSwitch;
  }
  
  public RadioButton w(String paramString) {
    RadioButton radioButton = new RadioButton(a(paramString + ".text", new String[0]) + "  ");
    radioButton.setMnemonicParsing(true);
    radioButton.textOverrunProperty().set(OverrunStyle.CLIP);
    b((Node)radioButton, paramString);
    b((Control)radioButton, paramString);
    return radioButton;
  }
  
  public Menu$ x(String paramString) {
    String str = H(paramString);
    return new Menu$(str);
  }
  
  public List d(String... paramVarArgs) {
    ArrayList<SeparatorMenuItem> arrayList = new ArrayList();
    for (String str : paramVarArgs) {
      if ("separator".equals(str)) {
        arrayList.add(new SeparatorMenuItem());
      } else {
        arrayList.add(y(str));
      } 
    } 
    return arrayList;
  }
  
  public CheckMenuItem y(String paramString) {
    b();
    String str = H(paramString);
    CheckMenuItem checkMenuItem = new CheckMenuItem(str);
    checkMenuItem.setOnAction(a(paramString));
    a(checkMenuItem, paramString);
    return checkMenuItem;
  }
  
  public RadioMenuItem z(String paramString) {
    b();
    String str = H(paramString);
    RadioMenuItem radioMenuItem = new RadioMenuItem(str);
    radioMenuItem.setOnAction(a(paramString));
    a(radioMenuItem, paramString);
    return radioMenuItem;
  }
  
  public List e(String... paramVarArgs) {
    ArrayList<SeparatorMenuItem> arrayList = new ArrayList();
    for (String str : paramVarArgs) {
      if ("separator".equals(str)) {
        arrayList.add(new SeparatorMenuItem());
      } else {
        arrayList.add(A(str));
      } 
    } 
    return arrayList;
  }
  
  public MenuItem A(String paramString) {
    MenuItem menuItem = new MenuItem(K(paramString), p(paramString));
    menuItem.setAccelerator(D(paramString));
    menuItem.setOnAction(a(paramString));
    a(menuItem, paramString);
    return menuItem;
  }
  
  public MenuItem B(String paramString) {
    MenuItem menuItem = new MenuItem(b(paramString, new String[0]), p(paramString));
    menuItem.setStyle("-fx-font-style: italic;");
    return menuItem;
  }
  
  public void a(String paramString1, String paramString2) {
    Pref.b(this.f.getSimpleName() + "." + this.f.getSimpleName() + ".accelerator", paramString2);
  }
  
  public void C(String paramString) {
    Pref.c(this.f.getSimpleName() + "." + this.f.getSimpleName() + ".accelerator");
  }
  
  public KeyCombination D(String paramString) {
    String str = E(paramString);
    return (str != null) ? KeyCombination.keyCombination(str) : null;
  }
  
  public String E(String paramString) {
    String str1 = a(paramString + ".accelerator", new String[0]);
    String str2 = Pref.d(this.f.getSimpleName() + "." + this.f.getSimpleName() + ".accelerator", (String)null);
    return (str2 != null) ? str2 : str1;
  }
  
  public boolean F(String paramString) {
    return (Pref.d(this.f.getSimpleName() + "." + this.f.getSimpleName() + ".accelerator", (String)null) != null);
  }
  
  private String K(String paramString) {
    return a(paramString + ".text", new String[0]);
  }
  
  public void a(ProgressBarWithText paramProgressBarWithText) {
    this.m = paramProgressBarWithText;
    this.m.setVisible(false);
  }
  
  public void a(MaskerPane paramMaskerPane) {
    this.n = paramMaskerPane;
  }
  
  public void a(Scene paramScene, String paramString, String... paramVarArgs) {
    b(paramScene, paramString, Alert.AlertType.INFORMATION, paramVarArgs).showAndWait();
  }
  
  public boolean a(Scene paramScene, String paramString1, String paramString2, String paramString3) {
    boolean bool = Pref.b(paramString3, true);
    if (bool) {
      Label label = new Label(b(paramString2, new String[0]));
      CheckBox checkBox = new CheckBox(b(paramString3 + ".text", new String[0]));
      checkBox.setSelected(true);
      VBox$ vBox$ = (new VBox$()).l().i().c(new Node[] { (Node)label, (Node)checkBox });
      Alert$ alert$ = b(paramScene, paramString2, Alert.AlertType.CONFIRMATION, new String[0]);
      alert$.getDialogPane().setContent((Node)vBox$);
      if (paramString1 != null)
        alert$.setHeaderText(paramString1); 
      Optional<ButtonType> optional = alert$.showAndWait();
      boolean bool1 = (optional.isPresent() && optional.get() == ButtonType.OK) ? true : false;
      Pref.a(paramString3, checkBox.isSelected());
      return bool1;
    } 
    return true;
  }
  
  public boolean a(Scene paramScene, String paramString, Alert.AlertType paramAlertType, String... paramVarArgs) {
    Optional<ButtonType> optional = b(paramScene, paramString, paramAlertType, paramVarArgs).showAndWait();
    return (optional.isPresent() && optional.get() == ButtonType.OK);
  }
  
  public boolean a(Scene paramScene, String paramString) {
    Optional<ButtonType> optional = b(paramScene, paramString, Alert.AlertType.CONFIRMATION, new String[0]).showAndWait();
    return (optional.isPresent() && optional.get() == ButtonType.OK);
  }
  
  public boolean a(Scene paramScene, String paramString1, String paramString2) {
    Alert$ alert$ = b(paramScene, paramString1, Alert.AlertType.CONFIRMATION, new String[0]);
    alert$.getButtonTypes().setAll((Object[])new ButtonType[] { ButtonType.OK, ButtonType.CANCEL });
    if (paramString2 != null)
      ((Button)alert$.getDialogPane().lookupButton(ButtonType.OK)).setText(paramString2); 
    Optional<ButtonType> optional = alert$.showAndWait();
    return (optional.isPresent() && optional.get() == ButtonType.OK);
  }
  
  public Alert$ b(Scene paramScene, String paramString, String... paramVarArgs) {
    Alert$ alert$ = b(paramScene, paramString, Alert.AlertType.CONFIRMATION, paramVarArgs);
    alert$.getButtonTypes().clear();
    String str;
    if ((str = H(paramString + ".yes")) != null) {
      alert$.getButtonTypes().add(ButtonType.YES);
      ((Button)alert$.getDialogPane().lookupButton(ButtonType.YES)).setText(str);
    } 
    if ((str = H(paramString + ".no")) != null) {
      alert$.getButtonTypes().add(ButtonType.NO);
      ((Button)alert$.getDialogPane().lookupButton(ButtonType.NO)).setText(str);
    } 
    if ((str = H(paramString + ".apply")) != null) {
      alert$.getButtonTypes().add(ButtonType.APPLY);
      ((Button)alert$.getDialogPane().lookupButton(ButtonType.APPLY)).setText(str);
    } 
    if ((str = H(paramString + ".close")) != null) {
      alert$.getButtonTypes().add(ButtonType.CLOSE);
      ((Button)alert$.getDialogPane().lookupButton(ButtonType.CLOSE)).setText(str);
    } 
    if ((str = H(paramString + ".cancel")) != null) {
      alert$.getButtonTypes().add(ButtonType.CANCEL);
      ((Button)alert$.getDialogPane().lookupButton(ButtonType.CANCEL)).setText(str);
    } 
    if (alert$.getButtonTypes().isEmpty())
      alert$.getButtonTypes().add(ButtonType.CLOSE); 
    return alert$;
  }
  
  public static final Pattern e = Pattern.compile("<html><h2>(.*)</h2>(.*)", 34);
  
  private String o;
  
  private long p;
  
  public Alert$ b(Scene paramScene, String paramString, Alert.AlertType paramAlertType, String... paramVarArgs) {
    return a(paramScene, paramString, paramAlertType, (Throwable)null, paramVarArgs);
  }
  
  public Alert$ a(Scene paramScene, String paramString, Alert.AlertType paramAlertType, Throwable paramThrowable, String... paramVarArgs) {
    String str1 = a(paramString + ".content.text", paramVarArgs);
    if (H(paramString + ".header") == null && str1 == null)
      str1 = paramString; 
    String str2 = H(paramString + ".title");
    if (str2 == null)
      switch (Rx$1.a[paramAlertType.ordinal()]) {
        case 1:
          str2 = "Confirmation Dialog";
          break;
        case 2:
          str2 = "Error Dialog";
          break;
        case 3:
          str2 = "Information Dialog";
          break;
        case 4:
          str2 = "Warning Dialog";
          break;
      }  
    String str3 = H(paramString + ".header");
    Matcher matcher;
    if (str1 != null && (matcher = e.matcher(str1)).matches()) {
      str3 = matcher.group(1);
      str1 = "<html>" + matcher.group(2);
    } 
    return (new Alert$(paramAlertType))
      .a(paramScene)
      .a(str2)
      .b(str3)
      .c(str1)
      .a(paramThrowable);
  }
  
  public Dialog a(Dialog paramDialog, String paramString) {
    paramDialog.setTitle(a(paramString + ".title.text", new String[0]));
    paramDialog.setHeaderText(a(paramString + ".header.text", new String[0]));
    paramDialog.setContentText(a(paramString + ".content.text", new String[0]));
    return paramDialog;
  }
  
  public String b(Scene paramScene, String paramString) {
    return b(paramScene, paramString, (String)null);
  }
  
  public String b(Scene paramScene, String paramString1, String paramString2) {
    TextInputDialog textInputDialog = new TextInputDialog(paramString2);
    if (paramScene != null)
      textInputDialog.initOwner(paramScene.getWindow()); 
    a((Dialog)textInputDialog, paramString1);
    String str1 = a(paramString1 + ".title.text", new String[0]);
    textInputDialog.setTitle((str1 != null) ? str1 : "Input Dialog");
    String str2 = a(paramString1 + ".header.text", new String[0]);
    String str3 = a(paramString1 + ".content.text", new String[0]);
    if (str2 == null && str3 == null)
      str2 = paramString1; 
    textInputDialog.setHeaderText(str2);
    textInputDialog.setContentText(str3);
    return textInputDialog.showAndWait().orElse(null);
  }
  
  public void a(Scene paramScene, String paramString, Throwable paramThrowable) {
    b(paramScene, DbmsAdvices.a(paramString).a(paramThrowable, (ConnectivityTip)null), paramThrowable);
  }
  
  public void a(Scene paramScene, Throwable paramThrowable) {
    StringBuilder stringBuilder = new StringBuilder();
    if (paramThrowable.getLocalizedMessage() == null) {
      stringBuilder.append(paramThrowable);
    } else {
      stringBuilder.append(paramThrowable.getLocalizedMessage());
    } 
    if (paramThrowable.getCause() != null && paramThrowable.getCause() != paramThrowable && !stringBuilder.toString().equalsIgnoreCase(paramThrowable.getCause().getLocalizedMessage()))
      stringBuilder.append("\n").append(paramThrowable.getCause().getLocalizedMessage()); 
    b(paramScene, stringBuilder.toString(), paramThrowable);
  }
  
  public void c(Scene paramScene, String paramString) {
    b(paramScene, paramString, (Throwable)null);
  }
  
  public void b(Scene paramScene, String paramString, Throwable paramThrowable) {
    a(paramScene, paramString, paramThrowable, (String)null, (Callback)null);
  }
  
  public void a(Scene paramScene, String paramString1, Throwable paramThrowable, String paramString2, Callback paramCallback) {
    Snapshot.a(paramScene);
    String str1 = H(paramString1 + ".content");
    if (str1 == null)
      str1 = paramString1; 
    if (str1 == null && paramThrowable != null)
      str1 = paramThrowable.getLocalizedMessage(); 
    String str2 = H(paramString1 + ".title");
    if (str2 == null)
      str2 = "Error Dialog"; 
    Alert$ alert$ = (new Alert$(Alert.AlertType.ERROR)).a(str2).a(paramScene).b(H(paramString1 + ".header")).c(str1).b(paramThrowable);
    if (paramString2 != null && paramCallback != null)
      alert$.a(paramString2, paramCallback); 
    alert$.a(paramScene, str1);
    Platform.runLater(() -> {
          if (paramScene != null && paramScene.getWindow() != null)
            paramAlert$.setX(paramScene.getWindow().getX() + paramScene.getWindow().getWidth() / 2.0D - Math.min(600.0D, paramAlert$.getWidth()) / 2.0D); 
        });
    alert$.showAndWait();
  }
  
  public void d() {}
  
  private static final Duration q = Duration.seconds(12.0D);
  
  public boolean d(Scene paramScene, String paramString) {
    String str = a(paramString + ".content.text", new String[0]);
    if (str == null)
      str = paramString; 
    boolean bool = (StringUtil.areEqual(str, this.o) && (System.currentTimeMillis() - this.p) < q.toMillis()) ? true : false;
    this.p = System.currentTimeMillis();
    this.o = str;
    if (!bool) {
      Notifications.create()
        .owner(paramScene.getWindow())
        .title(a(paramString + ".title.text", new String[0]))
        .text(str)
        .hideAfter(q)
        .showInformation();
      return true;
    } 
    return false;
  }
  
  public void a(Scene paramScene, String paramString, EventHandler paramEventHandler) {
    String str = a(paramString + ".content.text", new String[0]);
    if (str == null)
      str = paramString; 
    boolean bool = (StringUtil.areEqual(str, this.o) && (System.currentTimeMillis() - this.p) < q.toMillis()) ? true : false;
    this.p = System.currentTimeMillis();
    this.o = str;
    if (!bool) {
      HtmlLabel htmlLabel = new HtmlLabel(str);
      htmlLabel.a(paramEventHandler);
      Notifications.create()
        .owner(paramScene.getWindow())
        .title(a(paramString + ".title.text", new String[0]))
        .graphic((Node)htmlLabel)
        .hideAfter(q)
        .show();
    } 
  }
  
  public static void a(TextField paramTextField, LetterCase paramLetterCase) {
    paramTextField.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> {
          switch (Rx$1.b[paramLetterCase.ordinal()]) {
            case 1:
              paramTextField.setText(paramString2.toLowerCase());
              break;
            case 2:
              paramTextField.setText(paramString2.toUpperCase());
              break;
          } 
        });
  }
  
  public static void a(TextField paramTextField) {
    paramTextField.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> {
          if (!paramString2.matches("\\d*"))
            paramTextField.setText(paramString2.replaceAll("\\D", "")); 
        });
  }
  
  public static void b(TextField paramTextField) {
    paramTextField.textProperty().addListener((paramObservableValue, paramString1, paramString2) -> {
          if (paramString2 != null && (paramString2.contains(".") || paramString2.contains(",") || paramString2.contains(";") || paramString2.contains(":") || paramString2.contains("+") || paramString2.contains("*")))
            paramTextField.setText(paramString2.replaceAll("\\.", "_").replaceAll(";", "").replaceAll(":", "").replaceAll("\\+", "").replaceAll("\\*", "")); 
        });
  }
  
  public static void a(TableView paramTableView, double... paramVarArgs) {
    for (byte b = 0; b < paramVarArgs.length && b < paramTableView.getColumns().size(); b++)
      ((TableColumn)paramTableView.getColumns().get(b)).prefWidthProperty().bind((ObservableValue)paramTableView.widthProperty().multiply(paramVarArgs[b])); 
  }
  
  public static void a(TreeTableView paramTreeTableView, double... paramVarArgs) {
    for (byte b = 0; b < paramVarArgs.length && b < paramTreeTableView.getColumns().size(); b++)
      ((TreeTableColumn)paramTreeTableView.getColumns().get(b)).prefWidthProperty().bind((ObservableValue)paramTreeTableView.widthProperty().multiply(paramVarArgs[b])); 
  }
  
  public static void a(TableView paramTableView, TableColumn[] paramArrayOfTableColumn, double... paramVarArgs) {
    for (byte b = 0; b < paramVarArgs.length && b < paramArrayOfTableColumn.length; b++)
      paramArrayOfTableColumn[b].prefWidthProperty().bind((ObservableValue)paramTableView.widthProperty().multiply(paramVarArgs[b])); 
  }
  
  public static void b(TreeTableView paramTreeTableView, double... paramVarArgs) {
    for (byte b = 0; b < paramVarArgs.length && b < paramTreeTableView.getColumns().size(); b++)
      ((TreeTableColumn)paramTreeTableView.getColumns().get(b)).prefWidthProperty().bind((ObservableValue)paramTreeTableView.widthProperty().multiply(paramVarArgs[b])); 
  }
  
  public static void a(TreeTableView paramTreeTableView, TreeTableColumn[] paramArrayOfTreeTableColumn, double... paramVarArgs) {
    for (byte b = 0; b < paramArrayOfTreeTableColumn.length; b++)
      paramArrayOfTreeTableColumn[b].prefWidthProperty().bind((ObservableValue)paramTreeTableView.widthProperty().multiply(paramVarArgs[b])); 
  }
  
  public static void c(TreeTableView paramTreeTableView, double... paramVarArgs) {
    paramTreeTableView.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
    for (byte b = 0; b < paramVarArgs.length && b < paramTreeTableView.getColumns().size() - 1; b++)
      ((TreeTableColumn)paramTreeTableView.getColumns().get(b)).prefWidthProperty().bind((ObservableValue)paramTreeTableView.widthProperty().multiply(paramVarArgs[b])); 
  }
  
  public void e() {}
  
  public static void a(TabPane paramTabPane) {
    paramTabPane.getStyleClass().add("floating");
  }
  
  public static void b(TabPane paramTabPane) {
    paramTabPane.getStyleClass().add("floating-on-dark");
  }
  
  public void a(String paramString, Node paramNode, PopOver.ArrowLocation paramArrowLocation, String... paramVarArgs) {
    Platform.runLater(() -> {
          if (paramNode.getBoundsInLocal() != null && paramNode.localToScreen(paramNode.getBoundsInLocal()) != null)
            try {
              PopOver popOver = new PopOver();
              popOver.getStyleClass().add("balloon");
              String str = a(paramString, new String[0]);
              popOver.setContentNode(str.startsWith("<html>") ? (Node)new HtmlLabel(str) : (Node)new Label(str));
              if (paramArrayOfString != null)
                popOver.getStyleClass().addAll((Object[])paramArrayOfString); 
              popOver.setAutoHide(true);
              popOver.setDetachable(false);
              popOver.setAnimated(true);
              popOver.addEventFilter(MouseEvent.MOUSE_CLICKED, ());
              if (paramArrowLocation == null) {
                popOver.setArrowSize(0.0D);
              } else {
                popOver.setArrowLocation(paramArrowLocation);
              } 
              popOver.show(paramNode, paramNode.getBoundsInLocal().getHeight() / 3.0D);
            } catch (Throwable throwable) {
              throwable.printStackTrace();
              Exception exception = new Exception();
              StringWriter stringWriter = new StringWriter();
              exception.printStackTrace(new PrintWriter(stringWriter));
              System.out.println("Exception with popup fired by : " + String.valueOf(stringWriter));
            }  
        });
  }
  
  private static Rx$ScreenResolution r = Rx$ScreenResolution.c;
  
  static {
    try {
      Rectangle2D rectangle2D = Screen.getPrimary().getVisualBounds();
      double d1 = Math.max(0.1D, Screen.getPrimary().getOutputScaleY());
      double d2 = rectangle2D.getHeight() / d1;
      if (d2 < 800.0D) {
        r = Rx$ScreenResolution.a;
      } else if (d2 < 1000.0D) {
        r = Rx$ScreenResolution.b;
      } 
    } catch (Throwable throwable) {}
  }
  
  public static Rx$ScreenResolution f() {
    return r;
  }
  
  public static double g() {
    return Math.min(Screen.getPrimary().getOutputScaleX(), Screen.getPrimary().getOutputScaleY());
  }
  
  public static double h() {
    return (r == Rx$ScreenResolution.c) ? 1.0D : 0.75D;
  }
  
  public void a(String paramString, Dialog paramDialog) {
    paramDialog.setTitle(a(paramString + ".title.text", new String[0]));
    paramDialog.setHeaderText(a(paramString + ".header.text", new String[0]));
    paramDialog.setContentText(a(paramString + ".content.text", new String[0]));
  }
  
  public static void a(Menu... paramVarArgs) {
    for (Menu menu : paramVarArgs) {
      Objects.requireNonNull(menu.getItems());
      if (License.a().f())
        for (MenuItem menuItem : menu.getItems()) {
          if (menuItem.getStyleClass().contains("e4se"))
            a(menuItem); 
        }  
    } 
  }
  
  public static void a(MenuButton... paramVarArgs) {
    for (MenuButton menuButton : paramVarArgs) {
      Objects.requireNonNull(menuButton.getItems());
      if (License.a().f())
        for (MenuItem menuItem : menuButton.getItems()) {
          if (menuItem.getStyleClass().contains("e4se"))
            a(menuItem); 
        }  
    } 
  }
  
  public static void a(ToolBar paramToolBar) {
    Objects.requireNonNull(paramToolBar.getItems());
    Iterator<Node> iterator = paramToolBar.getItems().iterator();
    if (License.a().f())
      while (iterator.hasNext()) {
        Node node = iterator.next();
        if (node.getStyleClass().contains("e4se"))
          a(node); 
      }  
  }
  
  public static void a(Object paramObject) {
    if (paramObject instanceof MenuItem) {
      MenuItem menuItem = (MenuItem)paramObject;
      Node node = menuItem.getGraphic();
      if (node instanceof ImageView) {
        ImageView imageView = (ImageView)node;
        imageView.setImage((new CombinedImage(new Image[] { imageView.getImage(), c("pro.png") })).c());
      } else if (menuItem.getGraphic() instanceof Label && menuItem.getGraphic().getStyleClass().contains("bootstrapicons-glyph-font")) {
        menuItem.setGraphic((Node)q("pro.png"));
      } 
      menuItem.disableProperty().unbind();
      menuItem.setDisable(true);
    } else if (paramObject instanceof Node) {
      Node node = (Node)paramObject;
      if (node instanceof Labeled) {
        Labeled labeled = (Labeled)node;
        Node node1 = labeled.getGraphic();
        if (node1 instanceof ImageView) {
          ImageView imageView = (ImageView)node1;
          imageView.setImage((new CombinedImage(new Image[] { imageView.getImage(), c("pro.png") })).c());
        } 
      } 
      node.disableProperty().unbind();
      node.setDisable(true);
    } 
  }
}
