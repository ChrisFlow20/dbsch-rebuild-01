package com.wisecoders.dbs.project.fx;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.DbmsLocation;
import com.wisecoders.dbs.diagram.fx.FxProjectLoaderTask;
import com.wisecoders.dbs.dialogs.web.fx.FxVideoDialog;
import com.wisecoders.dbs.project.history.HistoryFile;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.FxWelcomeBox;
import com.wisecoders.dbs.sys.License;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.ToggleGroup$;
import com.wisecoders.dbs.sys.WebBrowserExternal;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.HBox$;
import com.wisecoders.dbs.sys.fx.VBox$;
import com.wisecoders.dbs.sys.fx.VGrowBox$;
import com.wisecoders.dbs.tips.fx.FxTipsDialog;
import com.wisecoders.dbs.tips.tips.QuickTour;
import com.wisecoders.dbs.tips.tips.QuickTour$Type;
import java.io.File;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.controlsfx.control.SegmentedButton;

public class FxWelcome extends BorderPane {
  private final FxFrame d;
  
  private final Rx e = new Rx(FxWelcome.class, this);
  
  private Hyperlink f;
  
  private final boolean g;
  
  final ToggleButton a = this.e.a("connectLocal", true);
  
  final ToggleButton b = this.e.a("connectRemote", false);
  
  final ToggleButton c = this.e.a("connectCloud", false);
  
  FxWelcome(FxFrame paramFxFrame) {
    this.d = paramFxFrame;
    this.g = License.a().e();
    if (this.g)
      this.e.G("pro"); 
    Hyperlink hyperlink1 = this.e.o("reopenModelReadMore");
    hyperlink1.setUnderline(true);
    FxWelcomeBox fxWelcomeBox1 = new FxWelcomeBox("reopenProject", this.e);
    fxWelcomeBox1.a.setDisable(!this.g);
    Label label1 = this.e.e("reopenModel");
    label1.setDisable(!this.g);
    fxWelcomeBox1.b(new Node[] { (Node)new VGrowBox$(), (Node)label1, (Node)new VGrowBox$() });
    Hyperlink hyperlink2 = this.e.o("selectProjectFile");
    hyperlink2.setDisable(!this.g);
    VBox vBox = fxWelcomeBox1.a(5, new Node[] { (Node)hyperlink2 });
    boolean bool = true;
    for (HistoryFile historyFile : FxFrame.c.c()) {
      Hyperlink hyperlink = new Hyperlink(historyFile.a);
      hyperlink.setContentDisplay(ContentDisplay.RIGHT);
      Label label = new Label(historyFile.b.getName());
      label.getStyleClass().addAll((Object[])new String[] { "text-gray" });
      hyperlink.setGraphic((Node)label);
      hyperlink.setTooltip(new Tooltip(historyFile.b.getPath()));
      hyperlink.setTextOverrun(OverrunStyle.CENTER_ELLIPSIS);
      hyperlink.setMaxWidth(400.0D);
      hyperlink.setMnemonicParsing(false);
      hyperlink.setDisable(!this.g);
      hyperlink.setOnAction(paramActionEvent -> paramFxFrame.a.a(new FxProjectLoaderTask(paramFxFrame, paramHistoryFile.b)));
      vBox.getChildren().add(hyperlink);
      bool = false;
    } 
    if (bool)
      fxWelcomeBox1.getChildren().add(this.f = this.e.o("sampleModels")); 
    fxWelcomeBox1.b(new Node[] { (Node)new VGrowBox$(), (Node)new VGrowBox$() });
    FxWelcomeBox fxWelcomeBox2 = new FxWelcomeBox("tutorials", this.e);
    fxWelcomeBox2.b(new Node[] { (Node)new VGrowBox$() });
    if (this.f == null) {
      fxWelcomeBox2.b(new Node[] { (Node)(this.f = this.e.o("sampleModels")), (Node)this.e
            .o("videoTutorials"), (Node)this.e
            .o("SQLTutorial"), (Node)this.e
            .o("quickTour"), (Node)this.e
            .o("documentation") });
    } else {
      fxWelcomeBox2.b(new Node[] { (Node)this.e
            .o("videoTutorials"), (Node)this.e
            .o("SQLTutorial"), (Node)this.e
            .o("quickTour"), (Node)this.e
            .o("documentation") });
    } 
    fxWelcomeBox2.b(new Node[] { (Node)new VGrowBox$(), (Node)new VGrowBox$() });
    getStyleClass().addAll((Object[])new String[] { "welcome", "wallpaper" });
    VBox.setVgrow((Node)this, Priority.ALWAYS);
    new ToggleGroup$(new ToggleButton[] { this.a, this.b, this.c });
    this.a.setGraphicTextGap(0.0D);
    this.b.setGraphicTextGap(0.0D);
    this.c.setGraphicTextGap(0.0D);
    SegmentedButton segmentedButton = new SegmentedButton(new ToggleButton[] { this.a, this.b, this.c });
    Button button1, button2;
    Label label2, label3;
    GridPane$ gridPane$1 = (new GridPane$()).g().a(10).a(new int[] { -2, -1, -2 }).b(new int[] { 
          -1, -2, -2, -1, -2, -2, -2, -1, -2, -2, 
          -2, -2, -2, -1 }).b("pane").a((Node)this.e.e("onlineLabel"), "0,4,0,5,c,c").a((Node)this.e.e("offlineLabel"), "0,7,0,11,c,c").a((Node)this.e.e("designTitle"), "1,1,c,c").a((Node)this.e.o("appearance"), "1,2,c,c").a((Node)segmentedButton, "1,4,c,c").a((Node)this.e.j("connectToDatabase"), "1,5,c,c").a((Node)this.e.e("connectToDatabaseDetail"), "1,6,c,c").a((Node)this.e.j("openSQLFile"), "1,7,c,c").a((Node)this.e.e("openSQLFileDetail"), "1,9,c,c").a((Node)(label2 = this.e.e("designOffline")), "1,10,c,c").a((Node)(new HBox$()).d().a(Pos.CENTER).a(new Node[] { (Node)(button2 = this.e.j("designPhysical")), (Node)(button1 = this.e.j("designLogical")) }, ), "1,11,c,c").a((Node)(label3 = this.e.e("designOfflineDetail")), "1,12,c,c");
    label2.setDisable(!this.g);
    label3.setDisable(!this.g);
    button1.setDisable(!this.g);
    button2.setDisable(!this.g);
    fxWelcomeBox1.getStyleClass().add("sponge");
    fxWelcomeBox2.getStyleClass().add("sponge");
    VBox$.setVgrow((Node)gridPane$1, Priority.ALWAYS);
    GridPane$ gridPane$2 = new GridPane$();
    gridPane$2.setStyle("-fx-background-color:transparent;");
    gridPane$2.a(30.0D, 19.0D);
    gridPane$2.setPadding(new Insets(7.0D, 20.0D, 7.0D, 20.0D));
    gridPane$2.b(new int[] { -1, -1, -2, -1, -1 });
    ColumnConstraints columnConstraints1 = new ColumnConstraints();
    columnConstraints1.setPercentWidth(30.0D);
    ColumnConstraints columnConstraints2 = new ColumnConstraints();
    columnConstraints2.setPercentWidth(40.0D);
    ColumnConstraints columnConstraints3 = new ColumnConstraints();
    columnConstraints3.setPercentWidth(30.0D);
    gridPane$2.getColumnConstraints().addAll((Object[])new ColumnConstraints[] { columnConstraints1, columnConstraints2, columnConstraints3 });
    gridPane$2.a((Node)new ImageView(Rx.c("welcome/logo.png")), "1,0,c,c");
    gridPane$2.a((Node)fxWelcomeBox1, "0,1,l,f");
    gridPane$2.a((Node)gridPane$1, "1,1,l,f");
    gridPane$2.a((Node)fxWelcomeBox2, "2,1,l,f");
    gridPane$2.a((Node)this.e.e("features"), "1,2,c,c");
    gridPane$2.setMaxWidth(1700.0D);
    setCenter((Node)gridPane$2);
    this.e.b();
    this.e.a(paramFxFrame.b.b);
  }
  
  @Action
  public Task connectDb() {
    return this.d.newProjectFromDb();
  }
  
  @Action
  public Task connectToDatabase() {
    this.d.a(this.a.isSelected() ? DbmsLocation.a : (this.b.isSelected() ? DbmsLocation.c : DbmsLocation.b));
    return this.d.newProjectFromDb();
  }
  
  @Action
  public void designConceptual() {
    this.d.newProjectLogicalDesign();
  }
  
  @Action
  public void designLogical() {
    this.d.newProjectLogicalDesign();
  }
  
  @Action
  public void designPhysical() {
    this.d.newProjectOffline();
  }
  
  @Action
  public void openSQLFile() {
    this.d.openSqlFile();
  }
  
  @Action
  public Task SQLTutorial() {
    return this.d.SQLTutorial();
  }
  
  @Action
  public Task sampleModels() {
    if (Sys.f.toFile().exists()) {
      a();
    } else {
      return new c(this);
    } 
    return null;
  }
  
  @Action
  public void reopenModelReadMore() {
    (new FxVideoDialog(getScene(), this.g ? "model" : "connectivity")).showDialog();
  }
  
  private void a() {
    File[] arrayOfFile = Sys.f.toFile().listFiles();
    if (arrayOfFile != null) {
      ContextMenu contextMenu = new ContextMenu();
      for (File file : arrayOfFile) {
        if (file.getName().endsWith(".dbs")) {
          MenuItem menuItem = new MenuItem(StringUtil.getFileNameWithoutExtension(file));
          menuItem.setMnemonicParsing(false);
          menuItem.setOnAction(paramActionEvent -> this.e.a(new FxProjectLoaderTask(this.d, paramFile)));
          contextMenu.getItems().add(menuItem);
        } 
      } 
      contextMenu.show((Node)this.f, Side.BOTTOM, 20.0D, 12.0D);
    } 
  }
  
  @Action
  public void documentation() {
    WebBrowserExternal.a(getScene(), "https://dbschema.com/documentation");
  }
  
  @Action
  public void quickTour() {
    new FxTipsDialog(this.d, new QuickTour(QuickTour$Type.b, true));
  }
  
  @Action
  public void videoTutorials() {
    (new FxVideoDialog(getScene(), "presentation")).showDialog();
  }
  
  @Action
  public void selectProjectFile() {
    this.d.openProjectFile();
  }
  
  @Action
  public void benefits() {
    WebBrowserExternal.a(getScene(), "https://dbschema.com/features.html#schema-synchronization");
  }
  
  @Action
  public void appearance() {
    new FxAppearanceDialog(getScene());
  }
}
