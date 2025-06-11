package com.wisecoders.dbs.project.fx;

import com.wisecoders.dbs.browse.model.Browse;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.data.task.FxApplicationTask;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.fx.FxProjectLoaderTask;
import com.wisecoders.dbs.diagram.fx.FxSearchField;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.Folder;
import com.wisecoders.dbs.dialogs.layout.FxEditorFactory;
import com.wisecoders.dbs.dialogs.system.FxPurchaseDialog;
import com.wisecoders.dbs.project.history.HistoryFile;
import com.wisecoders.dbs.query.model.items.Query;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.Connector$Environment;
import com.wisecoders.dbs.schema.ConnectorManager;
import com.wisecoders.dbs.schema.Layout;
import com.wisecoders.dbs.schema.Script;
import com.wisecoders.dbs.sys.License;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.WebBrowserExternal;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.FxUtil;
import com.wisecoders.dbs.sys.fx.HGrowBox$;
import com.wisecoders.dbs.sys.fx.Menu$;
import com.wisecoders.dbs.sys.fx.ProgressBarWithText;
import com.wisecoders.dbs.sys.fx.ToolBar$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import com.wisecoders.dbs.tips.tips.Tips;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.stage.WindowEvent;
import org.controlsfx.control.PopOver;

public class FxMenu {
  private final FxFrame c;
  
  private MenuButton d;
  
  private final MenuButton e = new MenuButton();
  
  private final Button f = new Button();
  
  public Menu$ a;
  
  final ProgressBarWithText b = new ProgressBarWithText();
  
  FxMenu(FxFrame paramFxFrame) {
    this.c = paramFxFrame;
  }
  
  void a(MenuBar paramMenuBar, boolean paramBoolean) {
    Menu$ menu$1 = this.c.a.x("projectMenu");
    menu$1.a(this.c.a, new String[] { "newProjectFromDb", "newProjectOffline", "openSqlFile", "openProjectFile" });
    if (License.a().e())
      menu$1.getItems().addAll((Object[])new MenuItem[] { (MenuItem)e() }); 
    menu$1.a(this.c.a, new String[] { "importModel", "separator", "saveProject", "saveProjectAs" });
    Menu$ menu$2 = this.c.a.x("exportAs");
    menu$2.a(this.c.a, new String[] { "exportDocumentation", "modelExporter", "separator", "printPng", "printGif", "printJpg", "printSVG" });
    menu$1.getItems().add(menu$2);
    menu$1.a(this.c.a, new String[] { "git", "separator", "print", "separator", "modelValidation", "projectProperties", "settings" });
    menu$1.a(this.c.a, new String[] { "separator", "closeProject", "exitDbSchema" });
    Menu$ menu$3 = this.c.a.x("editMenu");
    menu$3.a(this.c.a, new String[] { 
          "clipboardCopySQL", "clipboardPasteSQL", "undoDiagramEdit", "redoDiagramEdit", "search", "separator", "zoomIn", "zoomOut", "zoomNormal", "zoomChoose", 
          "separator", "autoplace", "appearance" });
    Menu$ menu$4 = this.c.a.x("layoutMenu");
    menu$4.a(this.c.a, new String[] { "newLayout", "newEmptyLayout", "duplicateLayout" });
    menu$4.getItems().add(f());
    menu$4.a(this.c.a, new String[] { "separator" });
    if (paramBoolean) {
      menu$4.b(this.c.a, new String[] { "showPhysicalName" });
      menu$4.b(this.c.a, new String[] { "showPhysicalDictionaryName" });
    } 
    menu$4.b(this.c.a, new String[] { "showSchemaName", "showDataType", "showPageBorders", "deducedFks", "joinedRouting" });
    Menu$ menu$5 = new Menu$(this.c.a.H("fkNotationMenu"), (Node)BootstrapIcons.arrow_90deg_right.glyph(new String[] { "glyph-blue" }));
    menu$5.b(this.c.a, new String[] { "notationIEWithArrows", "notationIE", "notationBarker", "notationIdef1x", "separator", "notationStrict" });
    menu$4.getItems().add(menu$5);
    Menu$ menu$6 = new Menu$(this.c.a.H("fkTextMenu"));
    menu$6.b(this.c.a, new String[] { "showFkName", "showFkColumns", "showFkCascade" });
    menu$4.getItems().add(menu$6);
    menu$4.getItems().addAll(this.c.a.e(new String[] { "separator", "manageVirtualForeignKeys", "projectDependencies" }));
    menu$4.a(this.c.a, new String[] { "separator", "documentation", "groupsManager", "layoutProperties" });
    Menu$ menu$7 = this.c.a.x("logicalDesignMenu");
    menu$7.a(this.c.a, new String[] { "newTable", "newRelation", "newGroup", "newShape", "newCallout", "separator", "tagManager", "gridEditor" });
    Menu$ menu$8 = this.c.a.x("logicalDeployMenu");
    menu$8.a(this.c.a, new String[] { "synchronizeFile", "separator", "conversionDictionary", "namingDictionary", "convertProject", "separator", "groovyScripts", "modelValidation" });
    this.a = this.c.a.x("schemaMenu");
    this.a.getItems().add(new MenuItem("Blind"));
    d(this.a);
    this.a.setOnShowing(paramEvent -> d(this.a));
    Menu$ menu$9 = this.c.a.x("connectionsMenu");
    menu$9.getItems().add(new MenuItem("Blind"));
    menu$9.setOnShowing(paramEvent -> a(paramMenu$));
    Menu$ menu$10 = this.c.a.x("editorsMenu");
    menu$10.getItems().add(new MenuItem("Blind"));
    c(menu$10);
    menu$10.setOnShowing(paramEvent -> c(paramMenu$));
    Menu$ menu$11 = this.c.a.x("toolsMenu");
    menu$11.getItems().add(new MenuItem("Blind"));
    b(menu$11);
    menu$11.setOnShowing(paramEvent -> b(paramMenu$));
    Menu$ menu$12 = this.c.a.x("helpMenu");
    menu$12.getItems().addAll(this.c.a.e(new String[] { 
            "help", "quickTour", "tipsTricks", "SQLTutorial", "videoTour", "separator", "technicalSupport", "outputLogs", "separator", "intabulare", 
            "detabulare", "separator", "fullScreen", "shortcutsManager" }));
    menu$12.getItems().add(this.c.a.x("checkForUpdatesMenu").a(this.c.a, new String[] { "checkForUpdates", "checkForUpdatesSettings" }));
    if (this.c.a.H("ceoEmail").equals((License.a()).c)) {
      Menu$ menu$ = this.c.a.x("adminMenu");
      menu$.getItems().addAll(this.c.a.e(new String[] { "ppgManager", "textToSpeech", "sparkPost", "amazonSES" }));
      menu$12.getItems().add(menu$);
    } 
    menu$12.getItems().addAll(this.c.a.e(new String[] { "separator", "aboutDialog" }));
    Rx.a(new Menu[] { menu$2, menu$1, menu$3, menu$4, menu$7, menu$8, menu$10, menu$11, menu$12 });
    paramMenuBar.getMenus().clear();
    if (paramBoolean) {
      paramMenuBar.getMenus().addAll((Object[])new Menu[] { menu$1, menu$3, menu$4, menu$7, menu$8, menu$12 });
    } else {
      paramMenuBar.getMenus().addAll((Object[])new Menu[] { menu$1, menu$3, menu$4, menu$9, this.a, menu$10, menu$11, menu$12 });
    } 
    if (Sys.k()) {
      Log.c("Set Mac systemMenuBar");
      FxUtil.a(paramMenuBar);
      paramMenuBar.setUseSystemMenuBar(true);
    } 
  }
  
  private void a(Menu$ paramMenu$) {
    paramMenu$.getItems().clear();
    if (this.c.l()) {
      for (Connector connector : ConnectorManager.getConnectors(this.c.m().getDbId())) {
        if (connector.isVisibleInMenu() || connector == this.c.s()) {
          CheckMenuItem checkMenuItem1 = new CheckMenuItem(connector.getName());
          checkMenuItem1.setOnAction(paramActionEvent -> this.c.a(paramConnector));
          checkMenuItem1.setSelected((connector == this.c.s()));
          paramMenu$.getItems().add(checkMenuItem1);
        } 
      } 
      CheckMenuItem checkMenuItem = new CheckMenuItem("Offline (design disconnected)");
      checkMenuItem.setOnAction(paramActionEvent -> this.c.h());
      checkMenuItem.setSelected((this.c.s() == null));
      paramMenu$.getItems().add(checkMenuItem);
      paramMenu$.getItems().add(new SeparatorMenuItem());
      if (this.c.r())
        paramMenu$.getItems().addAll(this.c.a.e(new String[] { "editConnector" })); 
    } 
    paramMenu$.getItems().addAll(this.c.a.e(new String[] { "newConnection", "editSchemaMapping", "separator", "manageConnections", "manageDrivers" }));
    Rx.a(new Menu[] { paramMenu$ });
    if (Sys.k())
      FxUtil.a(paramMenu$); 
  }
  
  private void b(Menu$ paramMenu$) {
    paramMenu$.getItems().clear();
    paramMenu$.getItems().addAll(this.c.a.e(new String[] { "dataLoader", "dataGenerator", "groovyScripts" }));
    if (this.c.l()) {
      ArrayList arrayList = new ArrayList(a(this.c.m().getDbId()));
      if (!arrayList.isEmpty()) {
        paramMenu$.getItems().add(new SeparatorMenuItem());
        for (File file : arrayList) {
          String str = "Db " + StringUtil.addSpaceBeforeUppercase(StringUtil.getFileNameWithoutExtension(file));
          MenuItem menuItem = new MenuItem(str, (Node)BootstrapIcons.database.glyph(new String[0]));
          menuItem.setOnAction(paramActionEvent -> this.c.getRx().a(new FxApplicationTask(this.c, paramFile)));
          menuItem.setDisable(!this.c.t());
          paramMenu$.getItems().add(menuItem);
        } 
      } 
    } 
    Rx.a(new Menu[] { paramMenu$ });
    if (Sys.k())
      FxUtil.a(paramMenu$); 
  }
  
  public static List a(String paramString) {
    ArrayList arrayList = new ArrayList();
    a(Sys.s, arrayList);
    a(Sys.z, arrayList);
    a(Sys.z.resolve(paramString), arrayList);
    String str = Dbms.getInheritedDbms(paramString);
    if (str != null)
      a(Sys.z.resolve(str), arrayList); 
    return arrayList;
  }
  
  private static void a(Path paramPath, List<File> paramList) {
    File file = paramPath.toFile();
    if (file.exists()) {
      File[] arrayOfFile = file.listFiles();
      if (arrayOfFile != null)
        for (File file1 : arrayOfFile) {
          if (file1.getName().endsWith(".groovy"))
            paramList.add(file1); 
        }  
    } 
  }
  
  private void c(Menu$ paramMenu$) {
    paramMenu$.getItems().clear();
    paramMenu$.getItems().addAll(this.c.a.e(new String[] { "newSqlEditor", "openSqlFile" }));
    paramMenu$.getItems().add(a(Script.class, true));
    paramMenu$.getItems().addAll(this.c.a.e(new String[] { "separator", "newBrowseEditor" }));
    if (License.a().e())
      paramMenu$.getItems().add(a(Browse.class, true)); 
    paramMenu$.getItems().addAll(this.c.a.e(new String[] { "separator", "querySelected" }));
    if (License.a().e())
      paramMenu$.getItems().add(a(Query.class, true)); 
    paramMenu$.getItems().addAll(this.c.a.e(new String[] { "separator", "csvEditor" }));
    Rx.a(new Menu[] { paramMenu$ });
    if (Sys.k())
      FxUtil.a(paramMenu$); 
  }
  
  private void d(Menu$ paramMenu$) {
    paramMenu$.getItems().clear();
    if (this.c.j()) {
      paramMenu$.getItems().addAll(this.c.a.e(new String[] { "createCollectionAndInsertDocument", "newTable", "separator", "synchronizeCompareSelected", "synchronizeCommit", "synchronizeFile", "separator", "exportSchemaScript" }));
    } else {
      Menu menu = new Menu(this.c.a.H("createTableView"));
      if (!this.c.l())
        menu.setDisable(true); 
      menu.getItems().addAll(this.c.a.e(new String[] { "newTable", "newColumn", "newUserDataType", "newView", "newTrigger", "newFunction", "newProcedure", "newSequence", "separator", "cloneUnit" }));
      paramMenu$.getItems().add(menu);
      paramMenu$.getItems().addAll(this.c.a.e(new String[] { "separator", "synchronizeCompareSelected", "synchronizeCommit", "synchronizeFile", "syncSettings", "separator", "exportSchemaScript", "separator" }));
      if (this.c.m() != null && (Dbms.get(this.c.m().getDbId())).queriesUsesCatalogAndSchema.b())
        paramMenu$.getItems().add(this.c.a.y("catalogPrefixSchema")); 
      paramMenu$.getItems().addAll(this.c.a.d(new String[] { "schemaPrefixTable", "autoLetterCases", "autoRefreshSchema" }));
      paramMenu$.getItems().addAll(this.c.a.e(new String[] { "separator", "gridEditor", "tagManager" }));
    } 
    Rx.a(new Menu[] { paramMenu$ });
    if (Sys.k())
      FxUtil.a(paramMenu$); 
  }
  
  private Menu$ e() {
    Menu$ menu$ = this.c.a.x("reopenProjectFile");
    menu$.getItems().add(new MenuItem("Blind"));
    menu$.setOnShowing(paramEvent -> {
          paramMenu$.getItems().clear();
          if (FxFrame.c.b()) {
            paramMenu$.getItems().add(new MenuItem(this.c.a.H("nothingToReopen")));
          } else {
            for (HistoryFile historyFile : FxFrame.c.c()) {
              MenuItem menuItem = new MenuItem(historyFile.b.getPath(), (Node)Rx.c());
              menuItem.setMnemonicParsing(false);
              menuItem.setOnAction(());
              paramMenu$.getItems().add(menuItem);
            } 
          } 
        });
    if (Sys.k())
      FxUtil.a(menu$); 
    return menu$;
  }
  
  private Menu$ f() {
    Menu$ menu$ = new Menu$("Reopen ...", (Node)Rx.c());
    menu$.getItems().add(new MenuItem("Blind"));
    menu$.setOnShowing(paramEvent -> {
          paramMenu$.getItems().clear();
          if (this.c.l())
            for (Layout layout : (this.c.m()).layouts) {
              MenuItem menuItem = new MenuItem(layout.getName(), (Node)Rx.q("diagram.png"));
              menuItem.setOnAction(());
              paramMenu$.getItems().add(menuItem);
            }  
        });
    if (Sys.k())
      FxUtil.a(menu$); 
    return menu$;
  }
  
  void a(ToolBar$ paramToolBar$, boolean paramBoolean) {
    MenuButton menuButton1 = this.c.a.f("newProjectFromDb$toolbar", true);
    menuButton1.getItems().addAll(this.c.a.e(new String[] { "newProjectFromDb", "newProjectOffline", "openSqlFile" }));
    Rx.a(new MenuButton[] { menuButton1 });
    MenuButton menuButton2 = this.c.a.f("openProjectFile$toolbar", false);
    menuButton2.getItems().add(new MenuItem("generic"));
    menuButton2.showingProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          paramMenuButton.getItems().clear();
          paramMenuButton.getItems().addAll(this.c.a.e(new String[] { "openProjectFile", "separator" }));
          for (HistoryFile historyFile : FxFrame.c.c()) {
            MenuItem menuItem = new MenuItem(historyFile.b.getPath(), (Node)Rx.c());
            menuItem.setOnAction(());
            paramMenuButton.getItems().add(menuItem);
          } 
          Rx.a(new MenuButton[] { paramMenuButton });
        });
    MenuButton menuButton3 = this.c.a.f("newLayout$toolbar", true);
    menuButton3.getItems().add(new MenuItem("generic"));
    menuButton3.showingProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          paramMenuButton.getItems().clear();
          paramMenuButton.getItems().addAll(this.c.a.e(new String[] { "newLayout", "exportDocumentation", "separator" }));
          for (Layout layout : (this.c.m()).layouts) {
            MenuItem menuItem = new MenuItem(layout.getName(), (Node)Rx.c());
            menuItem.setOnAction(());
            paramMenuButton.getItems().add(menuItem);
          } 
          Rx.a(new MenuButton[] { paramMenuButton });
        });
    MenuButton menuButton4 = this.c.a.e("zoomIn", true);
    menuButton4.getItems().addAll(this.c.a.e(new String[] { "zoomIn", "zoomOut", "zoomNormal", "separator", "zoomChoose" }));
    this.e.getStyleClass().addAll((Object[])new String[] { "menu-button-with-border", "font-large" });
    this.e.setMnemonicParsing(false);
    this.e.setGraphic((Node)Rx.q("connector.png"));
    this.e.getItems().add(new MenuItem("generic"));
    this.e.showingProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          this.e.getItems().clear();
          if (this.c.l()) {
            for (Connector connector : ConnectorManager.getConnectors(this.c.m().getDbId())) {
              if (connector.isVisibleInMenu() || connector == this.c.s()) {
                CheckMenuItem checkMenuItem1 = new CheckMenuItem(connector.getName());
                checkMenuItem1.setMnemonicParsing(false);
                checkMenuItem1.setSelected((connector == this.c.s()));
                checkMenuItem1.setOnAction(());
                this.e.getItems().add(checkMenuItem1);
              } 
            } 
            CheckMenuItem checkMenuItem = new CheckMenuItem("Offline (design disconnected)");
            checkMenuItem.setSelected(!this.c.r());
            checkMenuItem.setOnAction(());
            this.e.getItems().add(checkMenuItem);
            this.e.getItems().add(new SeparatorMenuItem());
            if (this.c.r())
              this.e.getItems().addAll(this.c.a.e(new String[] { "editConnector" })); 
          } 
          this.e.getItems().addAll(this.c.a.e(new String[] { "newConnection", "manageConnections", "separator", "synchronizeRefresh" }));
          Rx.a(new MenuButton[] { this.e });
        });
    Tooltip tooltip = new Tooltip();
    this.e.setTooltip(tooltip);
    tooltip.setOnShowing(paramWindowEvent -> paramTooltip.setText(this.c.a.H("connectorTooltip") + this.c.a.H("connectorTooltip")));
    MenuButton menuButton5 = this.c.a.f("browseSelected$toolbar", true);
    menuButton5.getItems().add(new MenuItem("generic"));
    menuButton5.showingProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          paramMenuButton.getItems().clear();
          paramMenuButton.getItems().add(this.c.a.A("browseSelected"));
          for (Layout layout : (this.c.m()).layouts) {
            for (AbstractUnit abstractUnit : layout.browses) {
              MenuItem menuItem = new MenuItem(String.valueOf(layout) + " / " + String.valueOf(layout), (Node)Rx.c());
              menuItem.setOnAction(());
              paramMenuButton.getItems().add(menuItem);
            } 
          } 
          paramMenuButton.getItems().addAll(this.c.a.e(new String[] { "separator", "dataLoader", "dataGenerator" }));
          Rx.a(new MenuButton[] { paramMenuButton });
        });
    MenuButton menuButton6 = this.c.a.f("newSqlEditor$toolbar", true);
    menuButton6.getItems().add(new MenuItem("generic"));
    menuButton6.showingProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          paramMenuButton.getItems().clear();
          paramMenuButton.getItems().add(this.c.a.A("newSqlEditor"));
          for (Layout layout : (this.c.m()).layouts) {
            for (AbstractUnit abstractUnit : layout.scripts) {
              MenuItem menuItem = new MenuItem(String.valueOf(layout) + " / " + String.valueOf(layout), (Node)Rx.c());
              menuItem.setOnAction(());
              paramMenuButton.getItems().add(menuItem);
            } 
          } 
          paramMenuButton.getItems().addAll(this.c.a.e(new String[] { "separator", "groovyScripts" }));
          Rx.a(new MenuButton[] { paramMenuButton });
        });
    MenuButton menuButton7 = this.c.a.f("querySelected$toolbar", true);
    menuButton7.getItems().add(new MenuItem("generic"));
    menuButton7.showingProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          paramMenuButton.getItems().clear();
          paramMenuButton.getItems().add(this.c.a.A("querySelected"));
          for (Layout layout : (this.c.m()).layouts) {
            for (AbstractUnit abstractUnit : layout.queries) {
              MenuItem menuItem = new MenuItem(String.valueOf(layout) + " / " + String.valueOf(layout), (Node)Rx.c());
              menuItem.setOnAction(());
              paramMenuButton.getItems().add(menuItem);
            } 
          } 
          Rx.a(new MenuButton[] { paramMenuButton });
        });
    MenuButton menuButton8 = this.c.a.f("newTable$toolbar", true);
    menuButton8.getItems().addAll(this.c.a.e(new String[] { "newTable", "newRelation", "newGroup", "newCallout", "newShape", "separator", "gridEditor", "tagManager" }));
    MenuButton menuButton9 = this.c.a.f("showDataType$toolbar", true);
    menuButton9.getItems().addAll((Object[])new MenuItem[] { (MenuItem)this.c.a.y("showDataType"), (MenuItem)this.c.a.y("joinedRouting") });
    MenuButton menuButton10 = this.c.a.f("help$toolbar", true);
    menuButton10.getItems().addAll(this.c.a.e(new String[] { "help", "quickTour", "tipsTricks", "SQLTutorial", "videoTour", "separator", "technicalSupport", "outputLogs" }));
    MenuButton menuButton11 = this.c.a.f("convertProject$toolbar", true);
    menuButton11.getItems().addAll(this.c.a.e(new String[] { "convertProject", "separator", "conversionDictionary", "namingDictionary", "separator", "synchronizeFile", "groovyScripts", "separator", "modelValidation" }));
    this.d = (MenuButton)this.c.a.g("synchronizeRefresh$toolbar", true);
    this.d.getItems().addAll(this.c.a.e(new String[] { "synchronizeRefresh", "synchronizeCompareSelected", "synchronizeCommit", "synchronizeFile", "separator", "git", "exportSchemaScript" }));
    Rx.a(new MenuButton[] { menuButton8, menuButton9, menuButton10, menuButton11, this.d });
    paramToolBar$.getItems().clear();
    paramToolBar$.getItems().add(menuButton1);
    License license = License.a();
    if (license.e())
      paramToolBar$.getItems().add(menuButton2); 
    paramToolBar$.getItems().addAll((Object[])new Node[] { (Node)this.c.a
          .j("saveProjectIfNotSaved$toolbar"), (Node)new Separator(), (Node)menuButton3, (Node)menuButton9, (Node)new Separator() });
    if (!paramBoolean)
      paramToolBar$.getItems().addAll((Object[])new Node[] { (Node)this.e, (Node)this.d, (Node)new Separator() }); 
    paramToolBar$.getItems().addAll((Object[])new Node[] { (Node)menuButton8, (Node)new Separator() });
    if (paramBoolean) {
      paramToolBar$.getItems().addAll((Object[])new Node[] { (Node)menuButton11 });
    } else {
      paramToolBar$.getItems().addAll((Object[])new Node[] { (Node)menuButton5, (Node)menuButton7, (Node)menuButton6, (Node)new Separator() });
    } 
    Dialog$.setManagedVisible(new Node[] { (Node)this.f });
    Dialog$.setManagedVisible(new Node[] { (Node)this.b });
    paramToolBar$.getItems().addAll((Object[])new Node[] { (Node)menuButton10, (Node)this.f, (Node)this.b });
    this.f.setId("buy-now-button");
    switch (FxMenu$1.a[license.g().ordinal()]) {
      case 1:
        if (license.k() < 12L && !(License.a()).i) {
          this.f.setText((license.k() < 0L) ? this.c.a.H("getUpgrades") : this.c.a.H("upgradesExpires").replaceAll("\\{xxx\\}", "" + license.k()));
          this.f.setOnAction(paramActionEvent -> this.c.a(this.c.a.H("renewInfo"), this.c.a.H("renewNow"), paramLicense.d()));
          this.f.setVisible(true);
          break;
        } 
        this.f.setVisible(false);
        break;
      case 2:
        if (license.k() < 5L) {
          this.f.setText(this.c.a.H("proExpires").replaceAll("\\{xxx\\}", "" + license.k()));
          this.f.setOnAction(paramActionEvent -> WebBrowserExternal.a(this.c, this.c.a.a("editions.url", new String[0])));
          this.f.setVisible(true);
          break;
        } 
        if (license.k() < 10L) {
          this.f.setText(this.c.a.H("purchasePro"));
          this.f.setOnAction(paramActionEvent -> WebBrowserExternal.a(this.c, this.c.a.a("buy.url", new String[0])));
          this.f.setVisible(true);
          break;
        } 
        this.f.setVisible(false);
        break;
      case 3:
      case 4:
        this.f.setText(this.c.a.H("renewalRequired"));
        this.f.setOnAction(paramActionEvent -> this.c.a(this.c.a.H("renewLicense"), this.c.a.H("renewNow"), paramLicense.d()));
        this.f.setVisible(true);
        break;
      case 5:
        this.f.setText(this.c.a.H("activateTrial"));
        this.f.setOnAction(paramActionEvent -> {
              FxPurchaseDialog.a(this.c);
              this.c.a.a(this.c, "activateTrialSucceed", new String[0]);
            });
        this.f.setVisible(true);
        break;
      case 6:
        this.f.setText(this.c.a.H("purchasePro"));
        this.f.setOnAction(paramActionEvent -> WebBrowserExternal.a(this.c, this.c.a.a("editions.url", new String[0])));
        this.f.setVisible(true);
        break;
      case 7:
        this.f.setText(this.c.a.H("validatingFloatingLicense"));
        this.f.setOnAction(null);
        this.f.setVisible(true);
        break;
    } 
    paramToolBar$.getItems().addAll((Object[])new Node[] { (Node)new HGrowBox$(), (Node)new FxSearchField(this.c) });
    Rx.a(paramToolBar$);
  }
  
  public void a() {
    String str1 = this.c.r() ? this.c.s().getName() : "Offline";
    if (!StringUtil.areEqual(str1, this.e.getText()))
      this.e.setText(str1); 
    Connector$Environment connector$Environment = this.c.r() ? this.c.s().getEnvironment() : null;
    String str2 = "connector-default";
    if (connector$Environment != null) {
      switch (FxMenu$1.b[connector$Environment.ordinal()]) {
        case 1:
        
        case 2:
        
        case 3:
        
        default:
          break;
      } 
      str2 = "connector-default";
    } 
    if (!this.e.getStyleClass().contains(str2)) {
      this.e.getStyleClass().removeAll((Object[])new String[] { "connector-test", "connector-production", "connector-development", "connector-default" });
      this.e.getStyleClass().add(str2);
    } 
  }
  
  public void b() {
    this.f.setVisible(false);
  }
  
  void c() {
    if (Tips.shouldShowTip("tipRefreshSchema") && this.d != null)
      Platform.runLater(() -> this.c.getRx().a("tipRefreshSchema.text", (Node)this.d, PopOver.ArrowLocation.TOP_CENTER, new String[0])); 
  }
  
  public void d() {
    if (this.a != null)
      this.a.setText(this.c.a.H("schemaMenu")); 
  }
  
  public Menu a(Class paramClass, boolean paramBoolean) {
    Menu menu = new Menu(this.c.a.H("reopenWithDots"), (Node)Rx.c());
    menu.getItems().add(new MenuItem("Blind"));
    menu.setOnShowing(paramEvent -> {
          paramMenu.getItems().clear();
          if (this.c.l()) {
            for (Layout layout : (this.c.m()).layouts) {
              Folder folder;
              if (paramClass == Query.class) {
                folder = layout.queries;
              } else if (paramClass == Script.class) {
                folder = layout.scripts;
              } else {
                folder = layout.browses;
              } 
              for (AbstractUnit abstractUnit : folder) {
                MenuItem menuItem = new MenuItem(String.valueOf(layout) + " / " + String.valueOf(layout), (abstractUnit.getSymbolicIcon() == null) ? (Node)abstractUnit.getSymbolicGlyph().glyph(new String[0]) : (Node)new ImageView(Rx.c(abstractUnit.getSymbolicIcon())));
                menuItem.setOnAction(());
                paramMenu.getItems().add(menuItem);
              } 
            } 
            if (paramMenu.getItems().isEmpty()) {
              MenuItem menuItem = new MenuItem("Nothing to Reopen");
              menuItem.setDisable(true);
              paramMenu.getItems().add(menuItem);
            } 
          } 
          if (Sys.k())
            FxUtil.a(paramMenu); 
        });
    if (paramBoolean && !this.c.l())
      menu.setDisable(true); 
    if (Sys.k())
      FxUtil.a(menu); 
    return menu;
  }
}
