package com.wisecoders.dbs.dbms.connect.fx;

import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.PasswordFieldWithEye;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import java.io.File;
import java.util.Objects;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class FxSshPanel extends GridPane$ {
  private final Connector a;
  
  private final FxConnectorEditor e;
  
  private final Rx f = new Rx(FxSshPanel.class, this);
  
  private final Label g;
  
  private final Label h;
  
  private final Label i;
  
  private final Label j = this.f.e("authentication");
  
  private final Label k = this.f.e("password");
  
  private final Label l;
  
  private final Label m = this.f.e("passphrase");
  
  private final TextField n = new TextField();
  
  private final TextField o = new TextField();
  
  private final TextField p = new TextField();
  
  private final PasswordField q = new PasswordField();
  
  private final TextField r = new TextField();
  
  private final PasswordFieldWithEye s = new PasswordFieldWithEye();
  
  private final RadioButton t = this.f.i("password", true);
  
  private final RadioButton u = this.f.i("privateKey", false);
  
  private final CheckBox v;
  
  public FxSshPanel(FxConnectorEditor paramFxConnectorEditor, Connector paramConnector) {
    this.a = paramConnector;
    e().g();
    a(new int[] { -2, -1, -2, -1 });
    b(new int[] { 
          -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, 
          -1 });
    this.e = paramFxConnectorEditor;
    this.l = this.f.e("keyLabel");
    this.o.setText("22");
    this.f.a(paramFxConnectorEditor.maskerPane);
    ToggleGroup toggleGroup = new ToggleGroup();
    this.t.setToggleGroup(toggleGroup);
    this.u.setToggleGroup(toggleGroup);
    this.g = this.f.e("hostLabel");
    this.h = this.f.e("portLabel");
    this.i = this.f.e("userLabel");
    this.v = this.f.h("enable", false);
    Objects.requireNonNull(this.v);
    this.f.a("flagEnabled", this.v::isSelected);
    this.f.a("flagUsePassword", () -> (this.v.isSelected() && this.t.isSelected()));
    this.f.a("flagUseKey", () -> (this.v.isSelected() && !this.t.isSelected()));
    a((Node)BootstrapIcons.shield_lock_fill.glyph(new String[] { "glyph-48", "glyph-3d" }, ), "0,0,c,c")
      .a((Node)this.v, "1,0,l,c");
    a((Node)this.g, "0,1,r,c");
    a((Node)this.n, "1,1,f,c");
    a((Node)this.h, "0,2,r,c");
    a((Node)this.o, "1,2,l,c");
    a((Node)this.i, "0,3,r,c");
    a((Node)this.p, "1,3,f,c");
    a((Node)this.j, "0,4,r,c");
    a((Node)(new FlowPane$()).a().a(new Node[] { (Node)this.t, (Node)this.u }, ), "1,4,l,c");
    a((Node)this.k, "0,5,r,c");
    a((Node)this.q, "1,5,f,c");
    a((Node)this.l, "0,6,r,c");
    a((Node)this.r, "1,6,f,c");
    a((Node)this.f.j("chooseKey"), "2,6,c,c");
    a((Node)this.m, "0,7,r,c");
    a((Node)this.s, "1,7,f,c");
    a((Node)(new FlowPane$()).b().a(new Node[] { (Node)this.f.j("testTunnel") }, ), "1,8,l,c");
    this.t.setOnAction(paramActionEvent -> c());
    this.u.setOnAction(paramActionEvent -> c());
    this.v.setOnAction(paramActionEvent -> c());
    c();
  }
  
  @Action(b = "flagEnabled")
  public Task testTunnel() {
    this.e.apply();
    return new FxSshPanel$1(this);
  }
  
  @Action(b = "flagUseKey")
  public void chooseKey() {
    File file = FxFileChooser.a(getScene(), "Choose Key File", FileOperation.a, new FileType[] { FileType.v });
    if (file != null)
      this.r.setText(file.getAbsolutePath()); 
  }
  
  public void a() {
    this.v.setSelected(this.a.isSshEnable());
    this.n.setText(this.a.getSshHost());
    this.o.setText("" + this.a.getSshPort());
    this.p.setText(this.a.getSshUser());
    this.q.setText(this.a.getSshPassword());
    this.r.setText(this.a.getSshPrivateKeyFile());
    this.s.a(this.a.getSshPassphrase());
    this.t.setSelected(!this.a.isSshUseKey());
    this.u.setSelected(this.a.isSshUseKey());
    c();
  }
  
  public void b() {
    this.a.setSshEnable(this.v.isSelected());
    this.a.setSshHost(this.n.getText());
    this.a.setSshUser(this.p.getText());
    try {
      this.a.setSshPort(Integer.parseInt(this.o.getText()));
    } catch (NumberFormatException numberFormatException) {
      Log.b(numberFormatException);
    } 
    this.a.setSshUseKey(this.u.isSelected());
    this.a.setSshPassword(this.q.getText());
    this.a.setSshPrivateKeyFile(this.r.getText());
    this.a.setSshPassphrase(this.s.a());
  }
  
  public void c() {
    this.f.b();
    boolean bool1 = this.f.b("flagEnabled");
    boolean bool2 = this.f.b("flagUsePassword");
    this.g.setDisable(!bool1);
    this.h.setDisable(!bool1);
    this.i.setDisable(!bool1);
    this.j.setDisable(!bool1);
    this.n.setDisable(!bool1);
    this.o.setDisable(!bool1);
    this.p.setDisable(!bool1);
    this.t.setDisable(!bool1);
    this.u.setDisable(!bool1);
    this.q.setDisable((!bool1 || !bool2));
    this.r.setDisable((!bool1 || bool2));
    this.s.setDisable((!bool1 || bool2));
    this.k.setDisable((!bool1 || !bool2));
    this.l.setDisable((!bool1 || bool2));
    this.m.setDisable((!bool1 || bool2));
  }
  
  public boolean d() {
    return (this.v.isSelected() && StringUtil.isFilledTrim(this.n.getText()));
  }
}
