package com.wisecoders.dbs.config.fx;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsTypes;
import com.wisecoders.dbs.dialogs.system.FxTechnicalSupportDialog;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;

public class FxSettingsDialog extends ButtonDialog$ {
  private final FxConfigurationPanel a;
  
  private final FxConfigurationPanel b;
  
  private final FxDataTypesPanel c;
  
  private final Tab d;
  
  private final Tab e;
  
  private final Tab f;
  
  private final Tab i;
  
  private final FxSettingsDialog$SelectTab j;
  
  private final TabPane k;
  
  private final ComboBox l;
  
  public FxSettingsDialog(Scene paramScene) {
    this(paramScene, FxSettingsDialog$SelectTab.a);
  }
  
  public FxSettingsDialog(Scene paramScene, FxSettingsDialog$SelectTab paramFxSettingsDialog$SelectTab) {
    this(paramScene, paramFxSettingsDialog$SelectTab, (Object)null);
  }
  
  public FxSettingsDialog(Scene paramScene, FxSettingsDialog$SelectTab paramFxSettingsDialog$SelectTab, Object paramObject) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: invokevirtual getWindow : ()Ljavafx/stage/Window;
    //   5: invokespecial <init> : (Ljavafx/stage/Window;)V
    //   8: aload_0
    //   9: new com/wisecoders/dbs/config/fx/FxConfigurationPanel
    //   12: dup
    //   13: invokespecial <init> : ()V
    //   16: putfield a : Lcom/wisecoders/dbs/config/fx/FxConfigurationPanel;
    //   19: aload_0
    //   20: new com/wisecoders/dbs/config/fx/FxConfigurationPanel
    //   23: dup
    //   24: invokespecial <init> : ()V
    //   27: putfield b : Lcom/wisecoders/dbs/config/fx/FxConfigurationPanel;
    //   30: aload_0
    //   31: new javafx/scene/control/TabPane
    //   34: dup
    //   35: invokespecial <init> : ()V
    //   38: putfield k : Ljavafx/scene/control/TabPane;
    //   41: aload_0
    //   42: new javafx/scene/control/ComboBox
    //   45: dup
    //   46: invokespecial <init> : ()V
    //   49: putfield l : Ljavafx/scene/control/ComboBox;
    //   52: aload_0
    //   53: aload_2
    //   54: putfield j : Lcom/wisecoders/dbs/config/fx/FxSettingsDialog$SelectTab;
    //   57: aload_0
    //   58: new com/wisecoders/dbs/config/fx/FxDataTypesPanel
    //   61: dup
    //   62: aload_1
    //   63: invokespecial <init> : (Ljavafx/scene/Scene;)V
    //   66: putfield c : Lcom/wisecoders/dbs/config/fx/FxDataTypesPanel;
    //   69: aload_0
    //   70: getfield a : Lcom/wisecoders/dbs/config/fx/FxConfigurationPanel;
    //   73: getstatic com/wisecoders/dbs/config/system/Sys.B : Lcom/wisecoders/dbs/config/system/Configuration;
    //   76: getfield root : Lcom/wisecoders/dbs/config/model/RootProperty;
    //   79: invokevirtual a : (Lcom/wisecoders/dbs/config/model/RootProperty;)V
    //   82: aload_0
    //   83: getstatic com/wisecoders/dbs/sys/fx/glyph/BootstrapIcons.gear_fill : Lcom/wisecoders/dbs/sys/fx/glyph/BootstrapIcons;
    //   86: invokevirtual setGraphic : (Lcom/wisecoders/dbs/sys/fx/glyph/BootstrapIcons;)V
    //   89: new com/wisecoders/dbs/sys/fx/FlowPane$
    //   92: dup
    //   93: invokespecial <init> : ()V
    //   96: invokevirtual a : ()Lcom/wisecoders/dbs/sys/fx/FlowPane$;
    //   99: astore #4
    //   101: aload #4
    //   103: invokevirtual getChildren : ()Ljavafx/collections/ObservableList;
    //   106: iconst_3
    //   107: anewarray javafx/scene/Node
    //   110: dup
    //   111: iconst_0
    //   112: aload_0
    //   113: getfield rx : Lcom/wisecoders/dbs/sys/Rx;
    //   116: ldc 'dbms'
    //   118: invokevirtual e : (Ljava/lang/String;)Ljavafx/scene/control/Label;
    //   121: aastore
    //   122: dup
    //   123: iconst_1
    //   124: aload_0
    //   125: getfield l : Ljavafx/scene/control/ComboBox;
    //   128: aastore
    //   129: dup
    //   130: iconst_2
    //   131: aload_0
    //   132: getfield rx : Lcom/wisecoders/dbs/sys/Rx;
    //   135: ldc 'addDbms'
    //   137: invokevirtual j : (Ljava/lang/String;)Ljavafx/scene/control/Button;
    //   140: aastore
    //   141: invokeinterface addAll : ([Ljava/lang/Object;)Z
    //   146: pop
    //   147: new com/wisecoders/dbs/sys/fx/VBox$
    //   150: dup
    //   151: invokespecial <init> : ()V
    //   154: invokevirtual k : ()Lcom/wisecoders/dbs/sys/fx/VBox$;
    //   157: astore #5
    //   159: aload #5
    //   161: invokevirtual getChildren : ()Ljavafx/collections/ObservableList;
    //   164: iconst_1
    //   165: anewarray javafx/scene/Node
    //   168: dup
    //   169: iconst_0
    //   170: new com/wisecoders/dbs/sys/fx/FlowPane$
    //   173: dup
    //   174: invokespecial <init> : ()V
    //   177: invokevirtual a : ()Lcom/wisecoders/dbs/sys/fx/FlowPane$;
    //   180: iconst_3
    //   181: anewarray javafx/scene/Node
    //   184: dup
    //   185: iconst_0
    //   186: aload_0
    //   187: getfield rx : Lcom/wisecoders/dbs/sys/Rx;
    //   190: ldc 'dbms'
    //   192: invokevirtual e : (Ljava/lang/String;)Ljavafx/scene/control/Label;
    //   195: aastore
    //   196: dup
    //   197: iconst_1
    //   198: aload_0
    //   199: getfield l : Ljavafx/scene/control/ComboBox;
    //   202: aastore
    //   203: dup
    //   204: iconst_2
    //   205: aload_0
    //   206: getfield rx : Lcom/wisecoders/dbs/sys/Rx;
    //   209: ldc 'addDbms'
    //   211: invokevirtual j : (Ljava/lang/String;)Ljavafx/scene/control/Button;
    //   214: aastore
    //   215: invokevirtual a : ([Ljavafx/scene/Node;)Lcom/wisecoders/dbs/sys/fx/FlowPane$;
    //   218: aastore
    //   219: invokeinterface addAll : ([Ljava/lang/Object;)Z
    //   224: pop
    //   225: aload #5
    //   227: invokevirtual getChildren : ()Ljavafx/collections/ObservableList;
    //   230: aload_0
    //   231: getfield k : Ljavafx/scene/control/TabPane;
    //   234: invokeinterface add : (Ljava/lang/Object;)Z
    //   239: pop
    //   240: aload_0
    //   241: getfield k : Ljavafx/scene/control/TabPane;
    //   244: getstatic javafx/scene/layout/Priority.ALWAYS : Ljavafx/scene/layout/Priority;
    //   247: invokestatic setVgrow : (Ljavafx/scene/Node;Ljavafx/scene/layout/Priority;)V
    //   250: aload_0
    //   251: new javafx/scene/control/Tab
    //   254: dup
    //   255: ldc 'Interface'
    //   257: aload_0
    //   258: getfield a : Lcom/wisecoders/dbs/config/fx/FxConfigurationPanel;
    //   261: invokespecial <init> : (Ljava/lang/String;Ljavafx/scene/Node;)V
    //   264: putfield d : Ljavafx/scene/control/Tab;
    //   267: aload_0
    //   268: getfield d : Ljavafx/scene/control/Tab;
    //   271: getstatic com/wisecoders/dbs/sys/fx/glyph/BootstrapIcons.display : Lcom/wisecoders/dbs/sys/fx/glyph/BootstrapIcons;
    //   274: iconst_0
    //   275: anewarray java/lang/String
    //   278: invokevirtual glyph : ([Ljava/lang/String;)Ljavafx/scene/control/Label;
    //   281: invokevirtual setGraphic : (Ljavafx/scene/Node;)V
    //   284: aload_0
    //   285: new javafx/scene/control/Tab
    //   288: dup
    //   289: ldc 'Database Specific'
    //   291: aload #5
    //   293: invokespecial <init> : (Ljava/lang/String;Ljavafx/scene/Node;)V
    //   296: putfield e : Ljavafx/scene/control/Tab;
    //   299: aload_0
    //   300: getfield e : Ljavafx/scene/control/Tab;
    //   303: getstatic com/wisecoders/dbs/sys/fx/glyph/BootstrapIcons.database : Lcom/wisecoders/dbs/sys/fx/glyph/BootstrapIcons;
    //   306: iconst_0
    //   307: anewarray java/lang/String
    //   310: invokevirtual glyph : ([Ljava/lang/String;)Ljavafx/scene/control/Label;
    //   313: invokevirtual setGraphic : (Ljavafx/scene/Node;)V
    //   316: aload_0
    //   317: new javafx/scene/control/Tab
    //   320: dup
    //   321: ldc 'Data Types'
    //   323: aload_0
    //   324: getfield c : Lcom/wisecoders/dbs/config/fx/FxDataTypesPanel;
    //   327: invokespecial <init> : (Ljava/lang/String;Ljavafx/scene/Node;)V
    //   330: putfield f : Ljavafx/scene/control/Tab;
    //   333: aload_0
    //   334: getfield f : Ljavafx/scene/control/Tab;
    //   337: getstatic com/wisecoders/dbs/sys/fx/glyph/BootstrapIcons.code_slash : Lcom/wisecoders/dbs/sys/fx/glyph/BootstrapIcons;
    //   340: iconst_0
    //   341: anewarray java/lang/String
    //   344: invokevirtual glyph : ([Ljava/lang/String;)Ljavafx/scene/control/Label;
    //   347: invokevirtual setGraphic : (Ljavafx/scene/Node;)V
    //   350: aload_0
    //   351: new javafx/scene/control/Tab
    //   354: dup
    //   355: ldc 'Settings'
    //   357: aload_0
    //   358: getfield b : Lcom/wisecoders/dbs/config/fx/FxConfigurationPanel;
    //   361: invokespecial <init> : (Ljava/lang/String;Ljavafx/scene/Node;)V
    //   364: putfield i : Ljavafx/scene/control/Tab;
    //   367: aload_0
    //   368: getfield i : Ljavafx/scene/control/Tab;
    //   371: getstatic com/wisecoders/dbs/sys/fx/glyph/BootstrapIcons.gear : Lcom/wisecoders/dbs/sys/fx/glyph/BootstrapIcons;
    //   374: iconst_0
    //   375: anewarray java/lang/String
    //   378: invokevirtual glyph : ([Ljava/lang/String;)Ljavafx/scene/control/Label;
    //   381: invokevirtual setGraphic : (Ljavafx/scene/Node;)V
    //   384: aload_0
    //   385: getfield k : Ljavafx/scene/control/TabPane;
    //   388: invokevirtual getTabs : ()Ljavafx/collections/ObservableList;
    //   391: iconst_2
    //   392: anewarray javafx/scene/control/Tab
    //   395: dup
    //   396: iconst_0
    //   397: aload_0
    //   398: getfield i : Ljavafx/scene/control/Tab;
    //   401: aastore
    //   402: dup
    //   403: iconst_1
    //   404: aload_0
    //   405: getfield f : Ljavafx/scene/control/Tab;
    //   408: aastore
    //   409: invokeinterface addAll : ([Ljava/lang/Object;)Z
    //   414: pop
    //   415: aload_0
    //   416: getfield k : Ljavafx/scene/control/TabPane;
    //   419: invokestatic a : (Ljavafx/scene/control/TabPane;)V
    //   422: aload_3
    //   423: ifnull -> 436
    //   426: aload_0
    //   427: aload_3
    //   428: <illegal opcode> run : (Lcom/wisecoders/dbs/config/fx/FxSettingsDialog;Ljava/lang/Object;)Ljava/lang/Runnable;
    //   433: invokestatic runLater : (Ljava/lang/Runnable;)V
    //   436: aload_0
    //   437: iconst_1
    //   438: invokevirtual setResizable : (Z)V
    //   441: aload_0
    //   442: getfield l : Ljavafx/scene/control/ComboBox;
    //   445: invokevirtual getItems : ()Ljavafx/collections/ObservableList;
    //   448: iconst_0
    //   449: anewarray java/lang/String
    //   452: invokestatic getKnownDbmsExclude : ([Ljava/lang/String;)Ljava/util/List;
    //   455: invokeinterface setAll : (Ljava/util/Collection;)Z
    //   460: pop
    //   461: aload_1
    //   462: instanceof com/wisecoders/dbs/project/fx/Workspace
    //   465: ifeq -> 500
    //   468: aload_1
    //   469: checkcast com/wisecoders/dbs/project/fx/Workspace
    //   472: astore #6
    //   474: aload #6
    //   476: invokevirtual m : ()Lcom/wisecoders/dbs/schema/Project;
    //   479: ifnull -> 500
    //   482: aload_0
    //   483: getfield l : Ljavafx/scene/control/ComboBox;
    //   486: aload #6
    //   488: invokevirtual m : ()Lcom/wisecoders/dbs/schema/Project;
    //   491: invokevirtual getDbId : ()Ljava/lang/String;
    //   494: invokevirtual setValue : (Ljava/lang/Object;)V
    //   497: goto -> 516
    //   500: invokestatic getLastUsedDbms : ()Ljava/lang/String;
    //   503: ifnull -> 516
    //   506: aload_0
    //   507: getfield l : Ljavafx/scene/control/ComboBox;
    //   510: invokestatic getLastUsedDbms : ()Ljava/lang/String;
    //   513: invokevirtual setValue : (Ljava/lang/Object;)V
    //   516: aload_0
    //   517: invokevirtual b : ()V
    //   520: aload_0
    //   521: getfield l : Ljavafx/scene/control/ComboBox;
    //   524: invokevirtual getSelectionModel : ()Ljavafx/scene/control/SingleSelectionModel;
    //   527: invokevirtual selectedItemProperty : ()Ljavafx/beans/property/ReadOnlyObjectProperty;
    //   530: aload_0
    //   531: <illegal opcode> changed : (Lcom/wisecoders/dbs/config/fx/FxSettingsDialog;)Ljavafx/beans/value/ChangeListener;
    //   536: invokevirtual addListener : (Ljavafx/beans/value/ChangeListener;)V
    //   539: return
    // Line number table:
    //   Java source line number -> byte code offset
    //   #50	-> 0
    //   #29	-> 8
    //   #30	-> 19
    //   #34	-> 30
    //   #36	-> 41
    //   #52	-> 52
    //   #53	-> 57
    //   #54	-> 69
    //   #55	-> 82
    //   #57	-> 89
    //   #58	-> 101
    //   #60	-> 147
    //   #61	-> 159
    //   #62	-> 225
    //   #63	-> 240
    //   #66	-> 250
    //   #67	-> 267
    //   #68	-> 284
    //   #69	-> 299
    //   #70	-> 316
    //   #71	-> 333
    //   #72	-> 350
    //   #73	-> 367
    //   #75	-> 384
    //   #76	-> 415
    //   #79	-> 422
    //   #80	-> 426
    //   #86	-> 436
    //   #87	-> 441
    //   #88	-> 461
    //   #89	-> 482
    //   #90	-> 500
    //   #91	-> 506
    //   #93	-> 516
    //   #94	-> 520
    //   #98	-> 539
  }
  
  public Node createContentPane() {
    b();
    TabPane tabPane = new TabPane();
    Rx.a(tabPane);
    tabPane.getTabs().addAll((Object[])new Tab[] { this.d, this.e });
    if (this.j == FxSettingsDialog$SelectTab.b || this.j == FxSettingsDialog$SelectTab.c) {
      tabPane.getSelectionModel().select(this.e);
      if (this.j == FxSettingsDialog$SelectTab.c)
        this.k.getSelectionModel().select(this.f); 
    } 
    setRegionPrefSize((Region)tabPane, 1100.0D, 700.0D);
    return (Node)tabPane;
  }
  
  private void b() {
    String str = (String)this.l.getValue();
    if (StringUtil.isFilled(str)) {
      this.b.a((Dbms.get(str)).root);
      this.c.a(DbmsTypes.get(str));
    } 
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
    createHelpButton("database-settings.html");
    createActionButton("reportBug", ButtonBar.ButtonData.LEFT);
  }
  
  @Action
  public void probeStatements() {}
  
  @Action
  public void reportBug() {
    (new FxTechnicalSupportDialog(getDialogScene().getWindow())).showDialog();
  }
  
  @Action
  public void addDbms() {
    String str;
    switch (FxSettingsDialog$1.a[showOptionsDialog("addDbmsDialog").c().ordinal()]) {
      case 1:
        c("If the database is not defined in the connection list,\nplease press 'Manage' and 'New Dbms'.");
        this.c.learnDbms();
        this.l.getItems().setAll(Dbms.getKnownDbmsExclude(new String[0]));
        break;
      case 2:
        str = showInputString("Which is name of the database ?");
        if (StringUtil.isFilled(str)) {
          this.l.getItems().add(0, str);
          this.l.setValue(str);
          b();
        } 
        break;
    } 
  }
  
  public void a() {
    (new FxSettingsDialog(getDialogScene(), FxSettingsDialog$SelectTab.a)).showDialog();
  }
  
  public boolean apply() {
    String str = (String)this.l.getValue();
    if (StringUtil.isFilled(str)) {
      (Dbms.get(str)).root.j();
      Sys.B.root.j();
      Sys.a(String.valueOf(Sys.B.locale.c()));
    } 
    return true;
  }
}
