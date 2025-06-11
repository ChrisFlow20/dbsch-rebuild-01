package com.wisecoders.dbs.dbms.connect.fx;

import com.wisecoders.dbs.config.fx.FxSettingsDialog;
import com.wisecoders.dbs.config.fx.FxSettingsDialog$SelectTab;
import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.Connector$ProxyType;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FxProxyPanel extends GridPane$ {
  private final Connector a;
  
  private final FxConnectorEditor e;
  
  private final Rx f = new Rx(FxProxyPanel.class, this);
  
  private final ComboBox g = new ComboBox();
  
  private final TextField h = new TextField();
  
  private final TextField i = new TextField();
  
  private final TextField j = new TextField();
  
  private final TextField k = new TextField();
  
  private final Label l = this.f.e("hostLabel");
  
  private final Label m = this.f.e("portLabel");
  
  private final Label n = this.f.e("userLabel");
  
  private final Label o = this.f.e("passwordLabel");
  
  private final CheckBox p = this.f.v("globalCheck");
  
  private final CheckBox q = this.f.v("useSystemProxyCheck");
  
  public FxProxyPanel(FxConnectorEditor paramFxConnectorEditor, Connector paramConnector) {
    this.a = paramConnector;
    e().g();
    this.f.a("flagEnabled", this::b);
    a(new int[] { -2, -1, -2, -1 });
    b(new int[] { 
          -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, 
          -1 });
    this.e = paramFxConnectorEditor;
    this.g.getItems().addAll((Object[])Connector$ProxyType.values());
    a((Node)BootstrapIcons.hdd_network_fill.glyph(new String[] { "glyph-48", "glyph-3d" }, ), "0,0,l,c")
      .a((Node)this.q, "1,0,l,c");
    a((Node)this.f.e("typeLabel"), "0,1,r,c");
    a((Node)(new FlowPane$()).a().a(new Node[] { (Node)this.g, (Node)this.p, (Node)this.f.j("globalProxySettings") }, ), "1,1,l,c");
    a((Node)this.l, "0,2,r,c");
    a((Node)this.h, "1,2,l,c");
    a((Node)this.m, "0,3,r,c");
    a((Node)this.i, "1,3,l,c");
    a((Node)this.n, "0,4,r,c");
    a((Node)this.j, "1,4,l,c");
    a((Node)this.o, "0,5,r,c");
    a((Node)this.k, "1,5,l,c");
    a((Node)this.f.j("apply"), "1,6,l,c");
    this.g.valueProperty().addListener((paramObservableValue, paramConnector$ProxyType1, paramConnector$ProxyType2) -> a());
    a();
  }
  
  @Action
  public void apply() {
    d();
    this.a.setProxy();
  }
  
  public void a() {
    this.f.b();
    boolean bool = this.f.b("flagEnabled");
    this.l.setDisable(!bool);
    this.m.setDisable(!bool);
    this.n.setDisable(!bool);
    this.o.setDisable(!bool);
    this.h.setDisable(!bool);
    this.i.setDisable(!bool);
    this.j.setDisable(!bool);
    this.k.setDisable(!bool);
  }
  
  public boolean b() {
    return (this.g.getValue() == Connector$ProxyType.b || this.g
      .getValue() == Connector$ProxyType.c || this.g
      .getValue() == Connector$ProxyType.d);
  }
  
  public void c() {
    this.q.setSelected(this.a.isUseSystemProxy());
    this.g.setValue(this.a.getProxyType());
    this.h.setText(this.a.getProxyHost());
    this.i.setText("" + this.a.getProxyPort());
    this.j.setText(this.a.getProxyUser());
    this.k.setText(this.a.getProxyPassword());
    this.p.setSelected((this.a
        .getProxyHost() != null && this.a
        .getProxyHost().equals(Sys.B.proxyHost.c_())));
    if (this.a.getProxyHost() == null && Sys.B.proxyHost.j()) {
      this.p.setSelected(true);
      if (Sys.B.proxyType.h() > 0)
        this.g.setValue(Connector$ProxyType.values()[Sys.B.proxyType.h()]); 
      this.h.setText(Sys.B.proxyHost.c_());
      this.i.setText(Sys.B.proxyPort.c_());
      this.j.setText(Sys.B.proxyUser.c_());
      this.k.setText(Sys.B.proxyPassword.c_());
    } 
    a();
  }
  
  public void d() {
    this.a.setProxyType((Connector$ProxyType)this.g.getValue());
    this.a.setUseSystemProxy(this.q.isSelected());
    this.a.setProxyHost(this.h.getText());
    this.a.setProxyUser(this.j.getText());
    try {
      this.a.setProxyPort(Integer.parseInt(this.i.getText()));
    } catch (NumberFormatException numberFormatException) {
      Log.b(numberFormatException);
    } 
    this.a.setProxyPassword(this.k.getText());
    if (this.p.isSelected()) {
      if (this.q.isSelected())
        Sys.B.useSystemProxy.a(true); 
      switch (FxProxyPanel$1.a[((Connector$ProxyType)this.g.getValue()).ordinal()]) {
        case 1:
          Sys.B.proxyType.a(Integer.valueOf(1));
          break;
        case 2:
          Sys.B.proxyType.a(Integer.valueOf(2));
          break;
        case 3:
          Sys.B.proxyType.a(Integer.valueOf(3));
          break;
      } 
      Sys.B.proxyHost.b(this.h.getText());
      Sys.B.proxyPort.b(this.i.getText());
      Sys.B.proxyUser.b(this.j.getText());
      Sys.B.proxyPassword.b(this.k.getText());
    } 
  }
  
  @Action
  public void globalProxySettings() {
    (new FxSettingsDialog(this.e.getDialogScene(), FxSettingsDialog$SelectTab.a, Sys.B.proxyType)).showDialog();
  }
}
