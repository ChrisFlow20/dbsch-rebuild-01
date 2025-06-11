package com.wisecoders.dbs.dialogs.web.fx;

import com.sun.webkit.WebPage;
import com.wisecoders.dbs.config.system.NetworkProxy;
import com.wisecoders.dbs.dialogs.system.FxTextDialog;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.WebBrowserExternal;
import com.wisecoders.dbs.sys.fx.FxUtil;
import com.wisecoders.dbs.sys.fx.HBox$;
import java.lang.reflect.Field;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.web.WebErrorEvent;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

public class FxWebView extends AnchorPane {
  protected final WebView a = new WebView();
  
  private int b;
  
  private int c;
  
  private final Rx d = new Rx(FxWebView.class, this);
  
  private final ComboBox e = new ComboBox();
  
  private final Button f;
  
  private final HBox$ g;
  
  public FxWebView() {
    FxUtil.a(this.a);
    this.d.a("flagCanBack", () -> (this.c > 0 && this.b > 1));
    this.d.a("flagCanForward", () -> (this.b > 1 && this.c < this.b - 1));
    NetworkProxy.a();
    this.a.getEngine().documentProperty().addListener(paramObservable -> a());
    this.a.getEngine().setOnAlert(paramWebEvent -> Log.c("Alert in Help WebView" + paramWebEvent.toString()));
    this.a.getEngine().setOnError(paramWebErrorEvent -> {
          FxUtil.a(this.a, paramWebErrorEvent);
          Log.a(paramWebErrorEvent.getMessage(), paramWebErrorEvent.getException());
          this.d.c(getScene(), "Please reload this page.\n Got error: \n" + String.valueOf(paramWebErrorEvent));
        });
    this.e.setOnShowing(paramEvent -> {
          this.e.getItems().clear();
          for (WebHistory.Entry entry : this.a.getEngine().getHistory().getEntries())
            this.e.getItems().add(entry.getUrl()); 
        });
    this.a.getEngine().getHistory().currentIndexProperty().addListener(paramObservable -> {
          c(this.a.getEngine().getLocation());
          this.d.b();
        });
    this.e.setStyle("-fx-font-size:105%");
    this.e.setOnAction(paramActionEvent -> {
          String str = (String)this.e.getValue();
          if (StringUtil.isFilledTrim(str)) {
            if (str.toLowerCase().startsWith("www") || (!str.startsWith("http") && !str.startsWith("file")))
              str = "https://" + str; 
            this.a.getEngine().load(str);
          } 
        });
    this.e.setEditable(true);
    this.d.b();
    this.f = this.d.j("showLocation");
    this.g = (new HBox$()).a(5.0D).b(5.0D).a(this.d.c(new String[] { "goBack", "goForward", "reload", "showPageSource" })).a((Node)this.f);
    this.g.getStyleClass().add("tool-bar");
    this.g.setAlignment(Pos.CENTER_LEFT);
    HBox.setHgrow((Node)this.e, Priority.ALWAYS);
    this.e.setMaxWidth(Double.MAX_VALUE);
    AnchorPane.setTopAnchor((Node)this.g, Double.valueOf(0.0D));
    AnchorPane.setRightAnchor((Node)this.g, Double.valueOf(0.0D));
    AnchorPane.setTopAnchor((Node)this.a, Double.valueOf(0.0D));
    AnchorPane.setBottomAnchor((Node)this.a, Double.valueOf(0.0D));
    AnchorPane.setRightAnchor((Node)this.a, Double.valueOf(0.0D));
    AnchorPane.setLeftAnchor((Node)this.a, Double.valueOf(0.0D));
    getChildren().addAll((Object[])new Node[] { (Node)this.a, (Node)this.g });
  }
  
  @Action
  public void showLocation() {
    AnchorPane.setLeftAnchor((Node)this.g, Double.valueOf(0.0D));
    AnchorPane.setTopAnchor((Node)this.a, Double.valueOf(this.g.getHeight()));
    this.g.getChildren().remove(this.f);
    this.g.getChildren().add(this.e);
  }
  
  public void a(String paramString) {
    String str = "https://dbschema.com/documentation/" + paramString;
    this.e.setValue(str);
    this.a.getEngine().load(str);
  }
  
  public void b(String paramString) {
    this.a.getEngine().load(paramString);
    c(paramString);
  }
  
  private void c(String paramString) {
    if (paramString != null)
      this.e.setValue(paramString); 
  }
  
  private void a() {
    WebHistory webHistory = this.a.getEngine().getHistory();
    this.c = webHistory.getCurrentIndex();
    ObservableList observableList = webHistory.getEntries();
    this.b = observableList.size();
    this.d.b();
  }
  
  @Action
  public void proxySettings() {
    this.d.a(getScene(), "proxyInfo", new String[0]);
  }
  
  @Action(b = "flagCanBack")
  public void goBack() {
    this.a.getEngine().getHistory().go(-1);
    a();
  }
  
  @Action(b = "flagCanForward")
  public void goForward() {
    this.a.getEngine().getHistory().go(1);
    a();
  }
  
  @Action
  public void reload() {
    this.a.getEngine().reload();
    a();
  }
  
  @Action
  public void find() {
    try {
      Field field = this.a.getEngine().getClass().getDeclaredField("page");
      field.setAccessible(true);
      WebPage webPage = (WebPage)field.get(this.a.getEngine());
      webPage.find("dbschema", true, true, false);
    } catch (Exception exception) {
      Log.b(exception);
    } 
  }
  
  public static void a(Scene paramScene) {
    WebBrowserExternal.a(paramScene, "https://github.com/wise-coders/dbschema/issues");
  }
  
  @Action
  public void showPageSource() {
    String str = (String)this.a.getEngine().executeScript("document.documentElement.outerHTML");
    (new FxTextDialog(getScene().getWindow(), "Source Code", str)).show();
  }
}
