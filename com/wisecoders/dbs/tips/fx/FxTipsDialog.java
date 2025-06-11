package com.wisecoders.dbs.tips.fx;

import com.wisecoders.dbs.dialogs.web.fx.FxVideoDialog;
import com.wisecoders.dbs.dialogs.web.fx.FxWebViewDialog;
import com.wisecoders.dbs.project.fx.Theme;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.FxUtil;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.tips.tips.QuickTour;
import com.wisecoders.dbs.tips.tips.QuickTour$Type;
import com.wisecoders.dbs.tips.tips.SQLTips;
import com.wisecoders.dbs.tips.tips.SQLTutorialTips;
import com.wisecoders.dbs.tips.tips.Tips;
import java.util.Collection;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.web.WebErrorEvent;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class FxTipsDialog extends Stage {
  private Tips b;
  
  private final WebView c = new WebView();
  
  private final CheckBox d = new CheckBox("Show on startup");
  
  private final Rx e = new Rx(FxTipsDialog.class, this);
  
  private final Button f;
  
  String a;
  
  public FxTipsDialog(Scene paramScene, Tips paramTips) {
    FxUtil.a(this.c);
    setTitle(paramTips.getDialogTitle());
    this.b = paramTips;
    Tips.showBalloonTips(false);
    if ((Theme.a()).j)
      this.c.getEngine().setUserStyleSheetLocation(Theme.k); 
    this.e.a("flagHasPrevious", () -> (this.b.cycleTips() || this.b.getIndex() > 0));
    if (this.b.goToFirst())
      this.b.resetIndex(); 
    this.f = this.e.j("next");
    this.d.setOnAction(paramActionEvent -> this.b.setShowTipsDialogOnStartup(this.d.isSelected()));
    this.d.setSelected(this.b.isShowTipsDialogOnStartup());
    this.c.getEngine().setOnError(paramWebErrorEvent -> {
          FxUtil.a(this.c, paramWebErrorEvent);
          Log.a(paramWebErrorEvent.getMessage(), paramWebErrorEvent.getException());
        });
    this.c.getEngine().locationProperty().addListener((paramObservableValue, paramString1, paramString2) -> {
          if (paramString2 != null) {
            paramString2 = paramString2.toLowerCase();
            if (paramString2.startsWith("https://page")) {
              Log.c("Jump to tips page " + paramString2);
              this.b.setIndex(Integer.parseInt(paramString2.substring("https://page".length(), paramString2.length() - (paramString2.endsWith("/") ? 1 : 0))));
              b();
            } else if (paramString2.startsWith("https://mongodbtips")) {
              Log.c("Switch to MongoDB tips.");
              this.b = new QuickTour(QuickTour$Type.a, true);
              b();
            } else if (paramString2.startsWith("https://designtips")) {
              Log.c("Switch to Design tips.");
              this.b = new QuickTour(QuickTour$Type.b, true);
              b();
            } else if (paramString2.startsWith("https://sqltips")) {
              Log.c("Switch to SQL tips.");
              this.b = new SQLTips();
              b();
            } else if (paramString2.startsWith("https://sqltutorial")) {
              Log.c("Switch to SQL tutorial.");
              this.b = new SQLTutorialTips();
              b();
            } else if (paramString2.startsWith("https://video")) {
              Log.c("Open Video Tutorials.");
              (new FxVideoDialog(getScene(), paramString2.substring("https://video".length()))).showDialog();
              this.c.getEngine().loadContent(this.a);
            } else if (StringUtil.isFilledTrim(paramString2)) {
              (new FxWebViewDialog(paramScene)).a(paramString2);
              this.c.getEngine().loadContent(this.a);
            } 
          } 
        });
    b();
    setOnHiding(paramWindowEvent -> Tips.showBalloonTips(true));
    Scene scene = new Scene((Parent)a(), 950.0D, 600.0D);
    scene.getStylesheets().addAll((Collection)paramScene.getStylesheets());
    setScene(scene);
    initOwner(paramScene.getWindow());
    show();
  }
  
  public GridPane a() {
    GridPane$ gridPane$ = new GridPane$();
    gridPane$.a(new int[] { -2, -1 }).b(new int[] { -1, -2 }).e().k();
    gridPane$.a((Node)this.c, "0,0,1,0,f,f");
    ButtonBar buttonBar1 = new ButtonBar();
    buttonBar1.getButtons().addAll((Object[])new Node[] { (Node)this.d });
    gridPane$.a((Node)buttonBar1, "0,1,l,c");
    ButtonBar buttonBar2 = new ButtonBar();
    buttonBar2.getButtons().addAll((Object[])new Node[] { (Node)this.e.j("previous"), (Node)this.f });
    gridPane$.a((Node)buttonBar2, "1,1,r,c");
    Dialog$.setRegionPrefSize((Region)gridPane$, 450.0D, 350.0D);
    gridPane$.setOnKeyPressed(paramKeyEvent -> {
          if (paramKeyEvent.getCode() == KeyCode.LEFT) {
            previous();
          } else if (paramKeyEvent.getCode() == KeyCode.RIGHT) {
            next();
          } 
        });
    return gridPane$;
  }
  
  @Action
  public void next() {
    if (!this.b.cycleTips())
      if (this.b.getIndex() == this.b.getTipsCount() - 2) {
        this.f.setText("Close");
      } else if (this.b.getIndex() == this.b.getTipsCount() - 1) {
        close();
      }  
    this.b.incrementPage(1);
    b();
  }
  
  @Action(b = "flagHasPrevious")
  public void previous() {
    this.b.incrementPage(-1);
    b();
  }
  
  @Action
  public void videoTutorials() {
    (new FxVideoDialog(getScene(), "presentation")).showDialog();
  }
  
  @Action
  public void close() {
    hide();
  }
  
  public void b() {
    this.e.b();
    this.a = Tips.formatTip(this.b.getTip(), (Theme.a()).j);
    try {
      this.c.getEngine().loadContent(this.a);
    } catch (Throwable throwable) {
      Log.b(throwable);
    } 
    setTitle(this.b.getDialogTitle() + " - " + this.b.getDialogTitle());
  }
}
