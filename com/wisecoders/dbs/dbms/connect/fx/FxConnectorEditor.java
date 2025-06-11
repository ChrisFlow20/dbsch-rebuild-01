package com.wisecoders.dbs.dbms.connect.fx;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.Dbms$ParamSource;
import com.wisecoders.dbs.dbms.DbmsLocation;
import com.wisecoders.dbs.dbms.JdbcUrlParam;
import com.wisecoders.dbs.dbms.connect.model.FxPingTask;
import com.wisecoders.dbs.dbms.connect.model.envoy.Envoy;
import com.wisecoders.dbs.dbms.driver.fx.FxDriversDialog;
import com.wisecoders.dbs.dbms.driver.model.DriverJarClass;
import com.wisecoders.dbs.dbms.driver.model.DriverManager;
import com.wisecoders.dbs.dbms.driver.model.JdbcUrlTemplate;
import com.wisecoders.dbs.dialogs.layout.FxCreateDatabaseDialog;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.dialogs.system.FxTechnicalSupportDialog;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.Connector$Environment;
import com.wisecoders.dbs.schema.ConnectorManager;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.ToggleGroup$;
import com.wisecoders.dbs.sys.WebBrowserExternal;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.HBox$;
import com.wisecoders.dbs.sys.fx.HtmlLabel;
import com.wisecoders.dbs.sys.fx.IntTextField;
import com.wisecoders.dbs.sys.fx.PasswordFieldWithEye;
import com.wisecoders.dbs.sys.fx.Separator$;
import com.wisecoders.dbs.sys.fx.UnclosableTab;
import com.wisecoders.dbs.sys.fx.VBox$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Priority;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;

public class FxConnectorEditor extends Dialog$ implements WorkspaceWindow {
  private static final String c = "manual";
  
  private static final String d = "/\\; ?!\"$";
  
  private static final int e = 1;
  
  private static final int f = 2;
  
  private static final int i = 4;
  
  private static final int j = 8;
  
  private static final int k = 16;
  
  private static final int l = 32;
  
  private static final int m = 64;
  
  private static final int n = 128;
  
  private static final int o = 256;
  
  private static final int p = 512;
  
  private static final int q = 1024;
  
  private static final int r = 2048;
  
  private static final int s = 4096;
  
  private static final int t = 8192;
  
  private static final int u = 16384;
  
  private static final int v = 32768;
  
  private static final int w = 65536;
  
  private static final int x = 131072;
  
  private static final int y = 262144;
  
  private static final int z = 524288;
  
  private static final int A = 1048576;
  
  private final Label B = BootstrapIcons.pc_display.glyph(new String[] { "glyph-48", "glyph-3d" });
  
  private final Label C = BootstrapIcons.key_fill.glyph(new String[] { "glyph-48", "glyph-3d" });
  
  private final Label D = BootstrapIcons.database_fill.glyph(new String[] { "glyph-48", "glyph-3d" });
  
  private final WorkspaceWindow E;
  
  private final Connector F;
  
  private final boolean G;
  
  private boolean H = true;
  
  private boolean I = false;
  
  private boolean J = false;
  
  private final DbmsLocation K;
  
  private final TabPane L = new TabPane();
  
  private final GridPane$ M = (new GridPane$()).e();
  
  private final Label N;
  
  private final Label O;
  
  private final Label P;
  
  private final Hyperlink Q;
  
  private final Button R;
  
  private final FxSshPanel S;
  
  private final FxProxyPanel T;
  
  private final TextField U = new TextField();
  
  private final ComboBox V = new ComboBox();
  
  private final ComboBox W = new ComboBox();
  
  private FxTestConnectivityTask X;
  
  private final GridPane$ Y = (new GridPane$()).l().a(new int[] { -2, -2, -1, -2 });
  
  private final Label Z;
  
  private final Label aa;
  
  private final TextField ab = new TextField();
  
  private final IntTextField ac = new IntTextField();
  
  private final RadioButton ad;
  
  private final RadioButton ae;
  
  private final TextField af = new TextField();
  
  private final PasswordFieldWithEye ag = new PasswordFieldWithEye();
  
  private final CheckBox ah;
  
  private final Label ai;
  
  private final Label aj;
  
  private final Label ak;
  
  private final Label al;
  
  private final Label am;
  
  private final Label an;
  
  private final Label ao;
  
  private final Label ap;
  
  private final Label aq;
  
  private final Button ar;
  
  private final c as = new c(this, JdbcUrlParam.a);
  
  private final TextField at = new TextField();
  
  private final c au = new c(this, JdbcUrlParam.b);
  
  private final TextField av = new TextField();
  
  private final c aw = new c(this, JdbcUrlParam.c);
  
  private final TextField ax = new TextField();
  
  private final c ay = new c(this, JdbcUrlParam.d);
  
  private final TextField az = new TextField();
  
  private final c aA = new c(this, JdbcUrlParam.e);
  
  private final TextField aB = new TextField();
  
  private final c aC = new c(this, JdbcUrlParam.f);
  
  private final TextField aD = new TextField();
  
  private final TextField aE = new TextField();
  
  private final TextField aF;
  
  private final Button aG;
  
  private final Button aH;
  
  private final Button aI;
  
  private final CheckBox aJ;
  
  private final CheckBox aK;
  
  private final ComboBox aL = new ComboBox();
  
  private final Separator$ aM;
  
  private final Separator$ aN;
  
  private final Separator$ aO;
  
  private final RadioButton aP;
  
  private final RadioButton aQ;
  
  private final RadioButton aR;
  
  private final RadioButton aS;
  
  final ChangeListener a;
  
  final ChangeListener b;
  
  private String aT;
  
  private static final String aU = "text-gray";
  
  private boolean aV;
  
  public FxConnectorEditor(WorkspaceWindow paramWorkspaceWindow, String paramString, DbmsLocation paramDbmsLocation) {
    this(paramWorkspaceWindow, ConnectorManager.createConnector(paramString), paramDbmsLocation, true);
  }
  
  public FxConnectorEditor(WorkspaceWindow paramWorkspaceWindow, Connector paramConnector) {
    this(paramWorkspaceWindow, paramConnector, (DbmsLocation)null, false);
  }
  
  public FxConnectorEditor(WorkspaceWindow paramWorkspaceWindow, Connector paramConnector, DbmsLocation paramDbmsLocation, boolean paramBoolean) {
    super(paramWorkspaceWindow, Modality.WINDOW_MODAL);
    this.a = ((paramObservableValue, paramDriverJarClass1, paramDriverJarClass2) -> Platform.runLater(()));
    this.b = ((paramObservableValue, paramObject1, paramObject2) -> Platform.runLater(()));
    this.aV = true;
    this.F = paramConnector;
    this.G = paramBoolean;
    this.E = paramWorkspaceWindow;
    this.K = paramDbmsLocation;
    this.Q = this.rx.o("help");
    this.Q.setGraphic(null);
    this.rx.a("flagSelectedUrl", () -> (l() != null));
    this.rx.a("flagJdbcWebsite", () -> (l() != null && DriverManager.b(paramConnector.dbId, l().a())));
    this.S = new FxSshPanel(this, paramConnector);
    this.T = new FxProxyPanel(this, paramConnector);
    this.aM = this.rx.h("hostSeparator");
    this.aN = this.rx.h("authenticationSeparator");
    this.aO = this.rx.h("dbSeparator");
    this.N = this.rx.e("aliasLabel");
    this.ao = this.rx.e("userLabel");
    this.ap = this.rx.e("passwordLabel");
    this.ah = this.rx.v("rememberPasswordCheck");
    this.ai = this.rx.e("instanceLabel");
    this.aj = this.rx.e("dbParameterLabel");
    this.ak = this.rx.e("dbParameter2Label");
    this.al = this.rx.e("dbParameter3Label");
    this.am = this.rx.e("dbParameter4Label");
    this.an = this.rx.e("dbParameter5Label");
    this.aG = this.rx.j("chooseDatabaseFile");
    this.aq = this.rx.e("urlLabel");
    this.O = this.rx.e("driverLabel");
    this.P = this.rx.e("driverUrlLabel");
    this.Z = this.rx.e("hostLabel");
    this.aa = this.rx.e("portLabel");
    this.aI = this.rx.j("createDatabase");
    this.R = this.rx.j("addDriver");
    this.ad = this.rx.w("useLocalhost");
    this.ae = this.rx.w("useRemoteHost");
    this.aH = this.rx.j("urlHelp");
    this.aJ = this.rx.h("readOnlyCheck", false);
    this.aK = this.rx.h("timeZoneCheck", false);
    this.ar = this.rx.j("ping");
    this.aF = this.rx.t("connectionProperties");
    Rx.a(this.L);
    getDialogPane().setPrefWidth(800.0D);
    this.ab.setOnKeyTyped(paramKeyEvent -> {
          k();
          if (StringUtil.containsOneChar(this.ab.getText(), "/\\; ?!\"$"))
            this.rx.d(getDialogScene(), "Illegal character in the host field."); 
        });
    this.ac.setOnKeyTyped(paramKeyEvent -> k());
    this.at.setOnKeyTyped(paramKeyEvent -> k());
    this.av.setOnKeyTyped(paramKeyEvent -> k());
    this.ax.setOnKeyTyped(paramKeyEvent -> k());
    this.az.setOnKeyTyped(paramKeyEvent -> k());
    this.aB.setOnKeyTyped(paramKeyEvent -> k());
    this.aD.setOnKeyTyped(paramKeyEvent -> k());
    this.af.setOnKeyTyped(paramKeyEvent -> k());
    this.ag.setOnKeyTyped(paramKeyEvent -> k());
    this.aP = this.rx.i("HighlightNormalRadio", true);
    this.aQ = this.rx.w("HighlightProductionRadio");
    this.aS = this.rx.w("HighlightTestRadio");
    this.aR = this.rx.w("HighlightDevelopmentRadio");
    new ToggleGroup$(new ToggleButton[] { (ToggleButton)this.aR, (ToggleButton)this.aP, (ToggleButton)this.aQ, (ToggleButton)this.aS });
    setManagedVisible(new Node[] { 
          (Node)this.at, (Node)this.as, (Node)this.aG, (Node)this.aI, (Node)this.aj, (Node)this.av, (Node)this.au, (Node)this.ak, (Node)this.ax, (Node)this.aw, 
          (Node)this.al, (Node)this.az, (Node)this.ay, (Node)this.am, (Node)this.aB, (Node)this.aA, (Node)this.an, (Node)this.aD, (Node)this.aC });
    setResultConverter(paramButtonType -> {
          ButtonBar.ButtonData buttonData = (paramButtonType == null) ? null : paramButtonType.getButtonData();
          return (buttonData == ButtonBar.ButtonData.OK_DONE) ? paramConnector : null;
        });
    Platform.runLater(() -> new d(this, true, true));
  }
  
  public FxConnectorEditor a() {
    this.J = true;
    return this;
  }
  
  public Node createContentPane() {
    this.M.a(new int[] { -2, -1, -2 });
    this.M.a((Node)this.N, "0,0,r,c");
    this.U.setPrefColumnCount(20);
    this.M.a((Node)this.U, "1,0,l,c");
    this.M.a((Node)this.O, "0,1,r,c");
    this.M.a((Node)(new FlowPane$()).a().a(new Node[] { (Node)this.V, (Node)this.R }, ), "1,1,2,1,f,f");
    this.M.a((Node)this.P, "0,2,r,c");
    this.M.a((Node)this.W, "1,2,2,2,l,c");
    this.M.setPadding(new Insets(5.0D, 5.0D, 15.0D, 5.0D));
    this.as.setEditable(true);
    this.as.setCellFactory(paramListView -> new FxConnectorEditor$DbComboCell(this));
    this.as.showingProperty().addListener((paramObservableValue, paramBoolean1, paramBoolean2) -> {
          if (paramBoolean2.booleanValue())
            a(JdbcUrlParam.a, (String)null); 
        });
    this.as.setCellFactory(paramListView -> new FxConnectorEditor$DbComboCell(this));
    new ToggleGroup$(new ToggleButton[] { (ToggleButton)this.ad, (ToggleButton)this.ae });
    this.ad.setOnAction(paramActionEvent -> useLocalhost());
    this.ae.setOnAction(paramActionEvent -> useRemoteHost());
    this.Y.a((Node)this.aM, "0,0,3,0,f,c");
    this.Y.a((Node)this.B, "0,0,0,3,c,c");
    this.Y.a((Node)(new FlowPane$()).a().a(new Node[] { (Node)this.ad, (Node)this.ae }, ), "2,1,3,1,l,c");
    ButtonBar buttonBar = new ButtonBar();
    buttonBar.getButtons().add(this.ar);
    this.Y.a((Node)this.Q, "1,4,2,4,c,c");
    this.Y.a((Node)buttonBar, "3,4,r,c");
    this.Q.getStyleClass().addAll((Object[])new String[] { "connectivity-tip", "font-large" });
    this.Y.a((Node)this.aN, "0,5,3,5,f,c");
    this.Y.a((Node)this.C, "0,6,0,7,c,c");
    this.Y.a((Node)this.ao, "1,6,r,c");
    this.Y.a((Node)this.af, "2,6,3,6,f,c");
    this.Y.a((Node)this.ap, "1,7,r,c");
    this.Y.a((Node)this.ag, "2,7,f,c");
    this.Y.a((Node)this.ah, "3,7,l,c");
    HBox$.setHgrow((Node)this.at, Priority.ALWAYS);
    HBox$.setHgrow((Node)this.as, Priority.ALWAYS);
    HBox$ hBox$ = (new HBox$()).d().a(new Node[] { (Node)this.at, (Node)this.as, (Node)this.aG, (Node)this.aI });
    this.Y.a((Node)this.aO, "0,8,3,8,f,c");
    this.Y.a((Node)this.D, "0,9,0,11,c,c");
    this.Y.a((Node)this.ai, "1,9,r,c");
    this.Y.a((Node)hBox$, "2,9,3,9,f,c");
    this.Y.a((Node)this.aj, "1,10,r,c");
    this.Y.a((Node)this.av, "2,10,3,10,f,c");
    this.Y.a((Node)this.au, "2,10,3,10,f,c");
    this.Y.a((Node)this.ak, "1,11,r,c");
    this.Y.a((Node)this.ax, "2,11,3,11,f,c");
    this.Y.a((Node)this.aw, "2,11,3,11,f,c");
    this.Y.a((Node)this.al, "1,12,r,c");
    this.Y.a((Node)this.az, "2,12,3,12,f,c");
    this.Y.a((Node)this.ay, "2,12,3,12,f,c");
    this.Y.a((Node)this.am, "1,13,r,c");
    this.Y.a((Node)this.aB, "2,13,3,13,f,c");
    this.Y.a((Node)this.aA, "2,13,3,13,f,c");
    this.Y.a((Node)this.an, "1,14,r,c");
    this.Y.a((Node)this.aD, "2,14,3,14,f,c");
    this.Y.a((Node)this.aC, "2,14,3,14,f,c");
    this.U.setText(this.F.getName());
    this.V.setCellFactory(paramListView -> new FxConnectorEditor$DriverCell());
    this.V.setButtonCell(new FxConnectorEditor$DriverCell());
    this.V.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramDriverJarClass1, paramDriverJarClass2) -> this.rx.b());
    this.W.setCellFactory(paramListView -> new FxConnectorEditor$URLCell(this, false));
    this.W.setButtonCell(new FxConnectorEditor$URLCell(this, true));
    this.W.setPromptText(this.rx.H("pleaseChooseLabel"));
    this.W.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramObject1, paramObject2) -> this.rx.b());
    b(true);
    String str = this.F.getTimeZone();
    for (String str1 : TimeZone.getAvailableIDs()) {
      this.aL.getItems().add(str1);
      if (StringUtil.isFilledTrim(str) && str.equalsIgnoreCase(str1)) {
        this.aL.setValue(str);
        this.aK.setSelected(true);
      } 
    } 
    if (!StringUtil.isFilledTrim(str) && TimeZone.getDefault() != null)
      this.aL.setValue(TimeZone.getDefault().getID()); 
    GridPane$ gridPane$ = (new GridPane$()).e().g().a(new int[] { -2, -1 }).a((Node)BootstrapIcons.tools.glyph(new String[] { "glyph-48", "glyph-3d" }, ), "0,0,c,c").a((Node)this.aJ, "1,0,l,c").a((Node)this.rx.e("schemaMappingLabel"), "0,1,r,c").a((Node)this.rx.j("editSchemaMapping"), "1,1,l,c").a((Node)this.aK, "0,2,r,c").a((Node)this.aL, "1,2,l,c").a((Node)this.rx.e("connectionProperties"), "0,3,r,c").a((Node)this.aF, "1,3,f,c").a((Node)this.rx.e("highlight"), "0,4,r,c").a((Node)(new FlowPane$()).a().a(new Node[] { (Node)this.aP, (Node)this.aQ, (Node)this.aR, (Node)this.aS }, ), "1,4,l,c").a((Node)this.rx.e("javaLabel"), "0,5,r,c").a((Node)(new FlowPane$()).a().a(new Node[] { (Node)new Label(System.getProperty("java.vendor") + " version " + System.getProperty("java.vendor") + " " + System.getProperty("java.version")), (Node)this.rx.j("openJavaFolder") }, ), "1,5,l,c");
    this.L.getTabs().add(new UnclosableTab(this.rx.H("connectionTab"), (Node)this.Y));
    this.L.getTabs().add(new UnclosableTab(this.rx.H("proxyTab"), (Node)this.T));
    this.L.getTabs().add(new UnclosableTab(this.rx.H("sshTab"), (Node)this.S));
    UnclosableTab unclosableTab = new UnclosableTab(this.rx.H("settingsTab"), (Node)gridPane$);
    this.L.getTabs().add(unclosableTab);
    if (this.J)
      this.L.getSelectionModel().select(unclosableTab); 
    this.L.selectionModelProperty().addListener((paramObservableValue, paramSingleSelectionModel1, paramSingleSelectionModel2) -> h());
    h();
    VBox$.setVgrow((Node)this.L, Priority.ALWAYS);
    return (Node)(new VBox$()).c(new Node[] { (Node)this.M, (Node)this.L });
  }
  
  private void b(boolean paramBoolean) {
    this.V.getSelectionModel().selectedItemProperty().removeListener(this.a);
    this.W.getSelectionModel().selectedItemProperty().removeListener(this.b);
    if (paramBoolean) {
      this.V.getSelectionModel().selectedItemProperty().addListener(this.a);
      this.W.getSelectionModel().selectedItemProperty().addListener(this.b);
    } 
  }
  
  @Action
  public void openJavaFolder() {
    try {
      Desktop.getDesktop().open(new File(System.getProperty("java.home")));
    } catch (IOException iOException) {
      this.rx.a(getDialogScene(), iOException);
    } 
  }
  
  public boolean b() {
    String str = this.U.getText();
    if (StringUtil.isEmptyTrim(str)) {
      showInformation("setAlias", new String[0]);
      this.U.requestFocus();
      return false;
    } 
    if (this.W.getValue() == null) {
      showInformation("setURL", new String[0]);
      this.W.requestFocus();
      return false;
    } 
    return true;
  }
  
  public boolean apply() {
    if (!b())
      return false; 
    boolean bool = this.F.setUser(this.af.getText());
    DriverJarClass driverJarClass = l();
    if (driverJarClass == null) {
      bool = (this.F.setDriverJarClass(null, null) || bool);
    } else {
      bool = (this.F.setDriverJarClass((driverJarClass.b()).b.getName(), driverJarClass.a()) || bool);
    } 
    bool = (this.F.setHost((this.ab.getText() != null) ? this.ab.getText().trim() : null) || bool);
    bool = (this.F.setPort(this.ac.a()) || bool);
    bool = (this.F.setInstance(f()) || bool);
    bool = (this.F.setRememberPassword(this.ah.isSelected()) || bool);
    bool = (this.F.setPassword(this.ag.a()) || bool);
    bool = (this.F.setReadOnly(this.aJ.isSelected()) || bool);
    bool = (this.F.setTimeZone(this.aK.isSelected() ? (String)this.aL.getValue() : null) || bool);
    bool = (this.F.setParam(this.av.isVisible() ? this.av.getText() : (String)this.au.getValue()) || bool);
    bool = (this.F.setParam2(this.ax.isVisible() ? this.ax.getText() : (String)this.aw.getValue()) || bool);
    bool = (this.F.setParam3(this.az.isVisible() ? this.az.getText() : (String)this.ay.getValue()) || bool);
    bool = (this.F.setParam4(this.aB.isVisible() ? this.aB.getText() : (String)this.aA.getValue()) || bool);
    bool = (this.F.setParam5(this.aD.isVisible() ? this.aD.getText() : (String)this.aC.getValue()) || bool);
    bool = (this.F.setConnectionProperties(this.aF.getText()) || bool);
    bool = (this.F.setCustomUrl(j() ? this.aE.getText() : null) || bool);
    bool = (this.F.setUrlTemplateName((i() != null) ? i().h() : null) || bool);
    bool = (this.F.setDriverUrl(i()) || bool);
    bool = (this.F.setEnvironment(e()) || bool);
    this.F.setName(this.U.getText());
    this.S.b();
    this.T.d();
    if (bool)
      this.F.closeAllEnvoysAndSsh(); 
    ConnectorManager.saveConnectors();
    ConnectorManager.savePasswords();
    return true;
  }
  
  private Connector$Environment e() {
    if (this.aS.isSelected())
      return Connector$Environment.Test; 
    if (this.aR.isSelected())
      return Connector$Environment.Development; 
    if (this.aQ.isSelected())
      return Connector$Environment.Production; 
    return Connector$Environment.Normal;
  }
  
  private String f() {
    if (this.as.isVisible())
      return (String)this.as.getValue(); 
    return this.at.getText();
  }
  
  public Connector c() {
    return this.F;
  }
  
  private void g() {
    JdbcUrlTemplate jdbcUrlTemplate = null;
    String str = (i() != null) ? i().h() : null;
    this.W.getItems().clear();
    for (JdbcUrlTemplate jdbcUrlTemplate1 : DriverManager.a(this.F.dbId).e()) {
      if (l() != null && jdbcUrlTemplate1.e() && jdbcUrlTemplate1.e(l().a())) {
        this.W.getItems().add(jdbcUrlTemplate1);
        if (StringUtil.equalNotNull(this.F.getUrlTemplateName(), jdbcUrlTemplate1.h()) || (jdbcUrlTemplate == null && StringUtil.equalNotNull(str, jdbcUrlTemplate1.h())))
          jdbcUrlTemplate = jdbcUrlTemplate1; 
      } 
    } 
    this.W.getItems().add("manual");
    if (jdbcUrlTemplate == null) {
      this.W.requestFocus();
    } else {
      this.W.setValue(jdbcUrlTemplate);
    } 
  }
  
  private void h() {
    String str2;
    JdbcUrlTemplate jdbcUrlTemplate = i();
    DriverJarClass driverJarClass = l();
    DriverManager driverManager = DriverManager.a(this.F.dbId);
    String str1 = null;
    int i = 0;
    if (driverJarClass != null) {
      Dbms dbms1 = Dbms.get((driverJarClass.b()).a);
      this.aq.setText("MongoDb".equals(dbms1.dbId) ? "Java URL" : "JDBC URL");
      Dbms dbms2 = Dbms.get((driverJarClass.b()).a);
      if (j()) {
        i |= 0x100000;
        if (driverManager.p())
          i |= 0x8; 
      } else if (jdbcUrlTemplate != null) {
        if (jdbcUrlTemplate.a())
          i |= 0x8; 
        if (jdbcUrlTemplate.a(2) || jdbcUrlTemplate.a(1))
          i |= 0x1; 
        if (!this.ad.isSelected()) {
          if (jdbcUrlTemplate.a(1) || jdbcUrlTemplate.a(2))
            i |= 0x2; 
          if (jdbcUrlTemplate.a(4))
            i |= 0x4; 
        } 
        if (jdbcUrlTemplate.a(32))
          i |= 0x40; 
        if ((Dbms.get(jdbcUrlTemplate.p)).databaseCreate.p())
          i |= 0x200; 
        if (jdbcUrlTemplate.a(1024)) {
          i |= 0x400;
          if (dbms1.getParamSource(JdbcUrlParam.b) != Dbms$ParamSource.c)
            i |= 0x800; 
        } 
        if (jdbcUrlTemplate.a(2048)) {
          i |= 0x1000;
          if (dbms1.getParamSource(JdbcUrlParam.c) != Dbms$ParamSource.c)
            i |= 0x2000; 
        } 
        if (jdbcUrlTemplate.a(4096)) {
          i |= 0x4000;
          if (dbms1.getParamSource(JdbcUrlParam.d) != Dbms$ParamSource.c)
            i |= 0x8000; 
        } 
        if (jdbcUrlTemplate.a(8192)) {
          i |= 0x10000;
          if (dbms1.getParamSource(JdbcUrlParam.e) != Dbms$ParamSource.c)
            i |= 0x20000; 
        } 
        if (jdbcUrlTemplate.a(16384)) {
          i |= 0x40000;
          if (dbms1.getParamSource(JdbcUrlParam.f) != Dbms$ParamSource.c)
            i |= 0x80000; 
        } 
        this.Z.setText(this.rx.H(jdbcUrlTemplate.a(2) ? "hostsLabel" : "hostLabel"));
        this.aa.setText(this.rx.H("portLabel") + this.rx.H("portLabel"));
        this.aj.setText(StringUtil.valueOfEmptyIfNull(jdbcUrlTemplate.k()) + StringUtil.valueOfEmptyIfNull(jdbcUrlTemplate.k()));
        this.af.setPromptText(jdbcUrlTemplate.q());
        this.ak.setText(StringUtil.valueOfEmptyIfNull(jdbcUrlTemplate.l()) + StringUtil.valueOfEmptyIfNull(jdbcUrlTemplate.l()));
        this.al.setText(StringUtil.valueOfEmptyIfNull(jdbcUrlTemplate.m()) + StringUtil.valueOfEmptyIfNull(jdbcUrlTemplate.m()));
        this.am.setText(StringUtil.valueOfEmptyIfNull(jdbcUrlTemplate.n()) + StringUtil.valueOfEmptyIfNull(jdbcUrlTemplate.n()));
        this.an.setText(StringUtil.valueOfEmptyIfNull(jdbcUrlTemplate.o()) + StringUtil.valueOfEmptyIfNull(jdbcUrlTemplate.o()));
        String str3 = this.rx.a("userLabel.tip", new String[0]), str4 = this.rx.a("passwordLabel.tip", new String[0]);
        if (dbms2.defaultUser.c_() != null)
          str3 = String.format("%s default user is '%s' %s", new Object[] { dbms1.dbId, dbms2.defaultUser.c_(), (dbms2.defaultPassword.c_() != null) ? ("with password '" + dbms2.defaultPassword.c_()) : "'" }); 
        if (dbms2.defaultPassword.c_() != null)
          str4 = str3; 
        this.ao.setText((jdbcUrlTemplate.g() != null) ? jdbcUrlTemplate.g() : "Database User");
        if (this.ao.getTooltip() != null)
          this.ao.getTooltip().setGraphic((Node)new HtmlLabel(str3)); 
        if (this.ap.getTooltip() != null)
          this.ap.getTooltip().setGraphic((Node)new HtmlLabel(str4)); 
        str1 = jdbcUrlTemplate.f();
        if (jdbcUrlTemplate.a(128) || jdbcUrlTemplate.a(64)) {
          i |= 0x80;
        } else if (jdbcUrlTemplate.a(512) || jdbcUrlTemplate.a(256)) {
          i |= 0x100;
        } else if (dbms1.getParamSource(JdbcUrlParam.a) != Dbms$ParamSource.c) {
          i |= 0x20;
        } else if (jdbcUrlTemplate.a(32)) {
          i |= 0x10;
        } 
      } 
    } 
    if (this.K == DbmsLocation.b && this.W.getValue() == "manual") {
      str2 = driverManager.n();
      if (str2 == null)
        str2 = this.rx.H("connectCloud"); 
    } else if (this.ad.isSelected()) {
      str2 = driverManager.l();
    } else {
      str2 = driverManager.m();
    } 
    this.Q.setText(str2);
    if (j()) {
      if (!this.Y.getChildren().contains(this.aq)) {
        this.Y.getChildren().removeAll((Object[])new Node[] { (Node)this.Z, (Node)this.ab, (Node)this.aa, (Node)this.ac });
        this.Y.a((Node)this.aq, "1,3,r,c");
        this.Y.a((Node)this.aE, "2,3,3,3,f,c");
        this.Y.a((Node)this.aH, "3,4,r,c");
      } 
    } else if (!this.Y.getChildren().contains(this.Z)) {
      this.Y.getChildren().removeAll((Object[])new Node[] { (Node)this.aq, (Node)this.aE, (Node)this.aH });
      this.Y.a((Node)this.Z, "1,2,r,c");
      this.Y.a((Node)this.ab, "2,2,3,2,f,c");
      this.Y.a((Node)this.aa, "1,3,r,c");
      this.Y.a((Node)this.ac, "2,3,3,3,f,c");
    } 
    if (a(i, 128)) {
      this.ai.setText((str1 != null) ? str1 : "File");
      this.ai.setTooltip(new Tooltip("The database file on disk."));
    } else if (a(i, 256)) {
      this.ai.setText((str1 != null) ? str1 : "Folder");
      this.ai.setTooltip(new Tooltip("The database file on disk."));
    } else {
      String str = (str1 != null) ? str1 : "Database";
      if (jdbcUrlTemplate != null && jdbcUrlTemplate.b(32))
        str = str + " (optional)"; 
      this.ai.setText(str);
      if (jdbcUrlTemplate != null)
        if ("Oracle".equalsIgnoreCase(jdbcUrlTemplate.p)) {
          this.ai.setTooltip(new Tooltip("The database SID.\nFrom SQLPlus execute 'show parameter instance_name' to see the correct value"));
        } else if ("MySql".equalsIgnoreCase(jdbcUrlTemplate.p)) {
          this.ai.setTooltip(new Tooltip("The schema to connect to."));
        } else {
          this.ai.setTooltip(new Tooltip("The name of the database"));
        }  
    } 
    this.ad.setVisible(a(i, 1));
    this.ae.setVisible(a(i, 1));
    this.aM.setVisible(a(i, 1));
    this.B.setVisible((a(i, 1) || a(i, 1048576)));
    this.Z.setVisible(a(i, 1));
    this.ab.setVisible(a(i, 1));
    this.aa.setVisible(a(i, 1));
    this.ac.setVisible(a(i, 1));
    this.Z.setVisible(a(i, 1));
    this.ar.setVisible(a(i, 1));
    this.Z.setDisable(!a(i, 2));
    this.ab.setDisable(!a(i, 2));
    this.aa.setVisible(a(i, 4));
    this.ac.setVisible(a(i, 4));
    this.aN.setVisible(a(i, 8));
    this.C.setVisible(a(i, 8));
    this.ao.setVisible(a(i, 8));
    this.af.setVisible(a(i, 8));
    this.ah.setVisible(a(i, 8));
    this.ap.setVisible(a(i, 8));
    this.ag.setVisible(a(i, 8));
    this.at.setVisible(a(i, 400));
    this.aO.setVisible(a(i, 432));
    this.D.setVisible(a(i, 432));
    this.ai.setVisible(a(i, 432));
    this.as.setVisible(a(i, 32));
    this.aG.setVisible(a(i, 384));
    this.aI.setVisible((a(i, 512) && a(i, 432)));
    this.aj.setVisible(a(i, 1024));
    this.av.setVisible((a(i, 1024) && !a(i, 2048)));
    this.au.setVisible((a(i, 1024) && a(i, 2048)));
    this.ak.setVisible(a(i, 4096));
    this.ax.setVisible((a(i, 4096) && !a(i, 8192)));
    this.aw.setVisible((a(i, 4096) && a(i, 8192)));
    this.al.setVisible(a(i, 16384));
    this.az.setVisible((a(i, 16384) && !a(i, 32768)));
    this.ay.setVisible((a(i, 16384) && a(i, 32768)));
    this.am.setVisible(a(i, 65536));
    this.aB.setVisible((a(i, 65536) && !a(i, 131072)));
    this.aA.setVisible((a(i, 65536) && a(i, 131072)));
    this.an.setVisible(a(i, 262144));
    this.aD.setVisible((a(i, 262144) && !a(i, 524288)));
    this.aC.setVisible((a(i, 262144) && a(i, 524288)));
    this.aq.setVisible(a(i, 1048576));
    this.aE.setVisible(a(i, 1048576));
    this.aH.setVisible(a(i, 1048576));
    this.L.requestLayout();
    if (getDialogScene() != null)
      getDialogScene().getWindow().sizeToScene(); 
  }
  
  private JdbcUrlTemplate i() {
    Object object = this.W.getValue();
    JdbcUrlTemplate jdbcUrlTemplate = (JdbcUrlTemplate)object;
    return (object instanceof JdbcUrlTemplate) ? jdbcUrlTemplate : null;
  }
  
  private boolean j() {
    return this.W.getValue() instanceof String;
  }
  
  private void k() {
    JdbcUrlTemplate jdbcUrlTemplate = i();
    if (jdbcUrlTemplate != null && this.W.getValue() instanceof JdbcUrlTemplate) {
      int i = this.ac.a();
      String str = jdbcUrlTemplate.a(this.S.d() ? "localhost" : this.ab.getText(), i, f(), this.av.getText(), this.ax.getText(), this.az.getText(), this.aB.getText(), this.aD.getText(), this.af.getText(), this.ag.a());
      this.aE.setText(str);
    } 
  }
  
  private static boolean a(int paramInt1, int paramInt2) {
    return ((paramInt1 & paramInt2) > 0);
  }
  
  @Action
  public void chooseDatabaseFile() {
    JdbcUrlTemplate jdbcUrlTemplate = i();
    if (jdbcUrlTemplate != null)
      if (jdbcUrlTemplate.a(128) || jdbcUrlTemplate.a(64)) {
        File file = FxFileChooser.a(getDialogPane().getScene(), "Choose database file", FileOperation.a, new FileType[] { FileType.w });
        if (file != null) {
          String str = file.getAbsolutePath();
          if (jdbcUrlTemplate.a(128))
            str = str.replaceAll("\\\\", "/"); 
          if (jdbcUrlTemplate.p() != null)
            for (String str1 : jdbcUrlTemplate.p().split(";")) {
              if (StringUtil.isFilledTrim(str1) && str.endsWith(str1)) {
                str = str.substring(0, str.length() - str1.length());
                break;
              } 
            }  
          this.at.setText(str);
        } 
      } else if (jdbcUrlTemplate.a(512) || jdbcUrlTemplate.a(256)) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(this.E.getWorkspace().getWindow());
        if (file != null) {
          String str = file.getAbsolutePath();
          if (jdbcUrlTemplate.a(512))
            str = str.replaceAll("\\\\", "/"); 
          this.at.setText(str);
        } 
      }  
  }
  
  @Action
  public void createDatabase() {
    apply();
    (new FxCreateDatabaseDialog(this, this.F, this.F.dbId)).showDialog().ifPresent(paramString -> {
          a(JdbcUrlParam.a, paramString);
          if (this.at.isVisible())
            this.at.setText(paramString); 
        });
  }
  
  @Action
  public void addDriver() {
    new FxDriversDialog(this, this.F.dbId);
    new d(this, false, false);
  }
  
  @Action
  public Task ping() {
    String str = this.ab.getText();
    if (StringUtil.isEmptyTrim(str)) {
      showError("Please specify a host");
    } else {
      apply();
      return new FxPingTask(this, this.F);
    } 
    return null;
  }
  
  @Action
  public void useLocalhost() {
    h();
    this.ab.setText("localhost");
    this.ac.a((Dbms.get(this.F.dbId)).defaultPort.a());
  }
  
  @Action
  public void useRemoteHost() {
    h();
  }
  
  @Action
  public void useCustomURL() {
    h();
  }
  
  @Action(b = "flagJdbcWebsite")
  public void urlHelp() {
    if (l() != null)
      WebBrowserExternal.a(getDialogScene(), DriverManager.a(this.F.dbId, l().a())); 
  }
  
  private DriverJarClass l() {
    return (this.V.getValue() != null) ? (DriverJarClass)this.V.getValue() : null;
  }
  
  @Action
  public Task connect() {
    if (!b())
      return null; 
    if (l() == null) {
      Log.f("Driver is not set");
      showInformation("missingDriverMessage", new String[0]);
      this.R.requestFocus();
      return null;
    } 
    String str = f();
    if (!j() && (str == null || str.isEmpty())) {
      JdbcUrlTemplate jdbcUrlTemplate = i();
      if (jdbcUrlTemplate != null && jdbcUrlTemplate.a(32) && !jdbcUrlTemplate.b(32)) {
        Log.d(this.ai.getText() + " is not set");
        showError("Please choose a " + this.ai.getText() + ".");
        return null;
      } 
    } 
    apply();
    this.X = new FxConnectorEditor$1(this, this, this.F, null, this.I, this.H);
    return this.X;
  }
  
  public boolean cancel() {
    if (this.X != null)
      this.X.cancel(true); 
    if (this.G) {
      this.F.markForDeletion();
      ConnectorManager.refresh();
    } 
    return true;
  }
  
  @Action
  public Task editSchemaMapping() {
    if (this.E.getWorkspace().m() == null || (this.E.getWorkspace().m()).schemas.isEmpty()) {
      showInformation("editSchemaMappingEmptyProject", new String[0]);
    } else {
      apply();
      return new FxConnectorEditor$2(this, this, this.F, null, true, true);
    } 
    return null;
  }
  
  private void m() {
    this.U.setText(this.F.getName());
    this.ab.setText(this.F.getHost());
    if (this.F.getPort() > -1)
      this.ac.a(this.F.getPort()); 
    if (this.K == DbmsLocation.c || !this.F.isLocalhostAndDefaultPort()) {
      this.ae.setSelected(true);
    } else {
      this.ad.setSelected(true);
    } 
    String str = this.F.getInstance();
    if (str != null) {
      this.as.setValue(str);
      this.at.setText(str);
    } 
    this.af.setText(this.F.getUserName());
    this.ag.a(this.F.getPassword());
    this.ah.setSelected((this.F.isRememberPassword() || this.G));
    this.aJ.setSelected(this.F.isReadOnly());
    this.au.setValue(this.F.getParameter());
    this.aw.setValue(this.F.getParameter2());
    this.ay.setValue(this.F.getParameter3());
    this.aA.setValue(this.F.getParameter4());
    this.aC.setValue(this.F.getParameter5());
    this.av.setText(this.F.getParameter());
    this.ax.setText(this.F.getParameter2());
    this.az.setText(this.F.getParameter3());
    this.aB.setText(this.F.getParameter4());
    this.aD.setText(this.F.getParameter5());
    if (this.F.getCustomUrl() != null || this.K == DbmsLocation.b) {
      this.aE.setText(this.F.getCustomUrl());
      this.W.setValue("manual");
    } else {
      this.W.setValue(this.F.getDriverUrl());
      k();
    } 
    this.S.a();
    this.T.c();
    this.aF.setText(this.F.getConnectionProperties());
    switch (FxConnectorEditor$4.a[this.F.getEnvironment().ordinal()]) {
      case 1:
        this.aQ.setSelected(true);
        return;
      case 2:
        this.aR.setSelected(true);
        return;
      case 3:
        this.aS.setSelected(true);
        return;
    } 
    this.aP.setSelected(true);
  }
  
  public Workspace getWorkspace() {
    return this.E.getWorkspace();
  }
  
  public void createButtons() {
    createActionButton("connect");
    createCancelButton();
    Button button = createActionButton("help", ButtonBar.ButtonData.LEFT);
    button.getStyleClass().add("font-bold");
    ButtonBar.setButtonUniformSize((Node)button, false);
  }
  
  @Action
  public void help() {
    String str = this.F.dbId.toLowerCase() + "/" + this.F.dbId.toLowerCase();
    WebBrowserExternal.a(getDialogScene(), str, "connect.html");
  }
  
  @Action
  public void reportBug() {
    (new FxTechnicalSupportDialog(getDialogScene().getWindow(), "Technical Support", null, false)).showDialog();
  }
  
  private boolean a(String paramString) {
    String str = (Dbms.get(this.F.dbId)).systemDatabases.c_();
    if (StringUtil.isFilledTrim(paramString) && StringUtil.isFilledTrim(str))
      for (String str1 : str.split(",")) {
        try {
          if (Pattern.compile(str1, 2).matcher(paramString).matches())
            return true; 
        } catch (Throwable throwable) {
          Log.b(throwable);
        } 
      }  
    return false;
  }
  
  public FxConnectorEditor a(boolean paramBoolean) {
    this.H = paramBoolean;
    return this;
  }
  
  public FxConnectorEditor d() {
    this.I = true;
    return this;
  }
  
  private void a(JdbcUrlParam paramJdbcUrlParam, String paramString) {
    if (this.aV) {
      apply();
      Dbms dbms = Dbms.get(this.F.dbId);
      if ((((paramJdbcUrlParam == JdbcUrlParam.a) ? 1 : 0) & ((paramString != null) ? 1 : 0)) != 0) {
        this.F.setInstance(paramString);
      } else {
        this.F.setInstance((dbms.defaultDatabase.c_() != null) ? dbms.defaultDatabase.c_() : "");
      } 
      if (dbms.getParamSource(paramJdbcUrlParam) == Dbms$ParamSource.a) {
        try {
          List list = dbms.listParam((Envoy)null, paramJdbcUrlParam);
          switch (FxConnectorEditor$4.c[paramJdbcUrlParam.ordinal()]) {
            case 1:
            
            case 2:
            
            case 3:
            
            default:
              break;
          } 
          c c1 = this.as;
          if (list != null && !list.isEmpty()) {
            c1.b.setAll(list);
            if (paramString != null && c1.b.contains(paramString))
              c1.setValue(paramString); 
          } 
        } catch (Exception exception) {}
      } else if (dbms.getParamSource(paramJdbcUrlParam) == Dbms$ParamSource.b) {
        this.X = new FxConnectorEditor$3(this, this, this.F, paramJdbcUrlParam, false, true, paramJdbcUrlParam, paramString);
        this.aV = false;
        this.rx.a(this.X);
      } 
    } 
  }
}
