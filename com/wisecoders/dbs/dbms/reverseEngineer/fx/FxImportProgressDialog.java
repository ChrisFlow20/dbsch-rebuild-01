package com.wisecoders.dbs.dbms.reverseEngineer.fx;

import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.stage.WindowEvent;
import org.controlsfx.control.PopOver;

public class FxImportProgressDialog extends PopOver {
  private final Workspace a;
  
  private final Task b;
  
  private final Label c = new Label("Import progress");
  
  private final ProgressBar d = new ProgressBar();
  
  private final Rx e = new Rx(FxImportProgressDialog.class, this);
  
  public FxImportProgressDialog(Workspace paramWorkspace, Task paramTask) {
    this.a = paramWorkspace;
    this.b = paramTask;
    setOnCloseRequest(paramWindowEvent -> {
          paramTask.cancel();
          Log.f("Import canceled at user request.");
        });
    GridPane$ gridPane$ = (new GridPane$()).e().g();
    gridPane$.a(new int[] { -2, -1, -2 });
    gridPane$.a((Node)new ImageView(Rx.c("wait_animated48.gif")), "0,0,0,2,c,c");
    gridPane$.a((Node)new Label("Operation may take a while. Please wait..."), "1,0,2,0,l,c");
    gridPane$.a((Node)this.c, "1,1,2,1,l,c");
    gridPane$.a((Node)this.d, "1,2,f,c");
    gridPane$.a((Node)this.e.j("cancel"), "2,2,c,f");
    gridPane$.setStyle("-fx-background-color:-fx-base;");
    gridPane$.setStyle("quick-menu");
    this.c.textProperty().bind((ObservableValue)paramTask.messageProperty());
    Dialog$.setRegionPrefWidth((Region)this.c, 300.0D);
    setContentNode((Node)gridPane$);
    setArrowSize(0.0D);
    Point2D point2D = paramWorkspace.getRoot().localToScreen(paramWorkspace.getWidth() / 2.0D - 200.0D, paramWorkspace.getHeight() / 2.0D - 100.0D);
    show(paramWorkspace.getWindow(), point2D.getX(), point2D.getY());
  }
  
  public void a(String paramString) {
    this.c.setText(paramString);
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3) {
    this.d.setProgress((paramInt1 / paramInt3));
  }
  
  @Action
  public void cancel() {
    Log.f("Import canceled at user request.");
    this.b.cancel(true);
    hide();
  }
}
