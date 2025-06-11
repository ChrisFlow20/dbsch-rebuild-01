package com.wisecoders.dbs.sys.fx;

import com.wisecoders.dbs.sys.License;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import java.util.Iterator;
import java.util.Objects;
import javafx.geometry.Bounds;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Window;

public class ContextMenu$ extends ContextMenu {
  private String a;
  
  private boolean b = false;
  
  private Tooltip c;
  
  private long d;
  
  public ContextMenu$() {
    addEventFilter(KeyEvent.KEY_PRESSED, paramKeyEvent -> {
          if (this.b) {
            if (this.a == null || System.currentTimeMillis() - this.d > 2000L) {
              this.a = paramKeyEvent.getText();
            } else if (paramKeyEvent.getCode() == KeyCode.BACK_SPACE && this.a.length() > 0) {
              this.a = this.a.substring(0, this.a.length() - 1);
            } else if (paramKeyEvent.getCode().isLetterKey() || paramKeyEvent.getCode().isDigitKey()) {
              this.a += this.a;
            } 
            this.d = System.currentTimeMillis();
            Bounds bounds = null;
            for (MenuItem menuItem : getItems()) {
              boolean bool = (StringUtil.isEmptyTrim(menuItem.getText()) || StringUtil.isEmptyTrim(this.a) || menuItem.getText().toLowerCase().contains(this.a.toLowerCase())) ? true : false;
              menuItem.setVisible(bool);
              if (bounds == null && menuItem.getGraphic() != null)
                bounds = menuItem.getGraphic().localToScreen(menuItem.getGraphic().getBoundsInLocal()); 
            } 
            if (bounds != null) {
              if (this.c == null)
                this.c = new Tooltip(); 
              this.c.setText("Filter: " + this.a);
              this.c.show((Window)this, bounds.getMaxX() + 200.0D, bounds.getMinY() - 20.0D);
            } 
          } 
        });
  }
  
  public void a(boolean paramBoolean) {
    this.b = paramBoolean;
  }
  
  public ContextMenu$ b(boolean paramBoolean) {
    setAutoHide(paramBoolean);
    return this;
  }
  
  public ContextMenu$ a(Rx paramRx, String... paramVarArgs) {
    setAutoHide(true);
    getItems().clear();
    for (String str : paramVarArgs) {
      if (Menu$.a.equals(str)) {
        getItems().add(new SeparatorMenuItem());
      } else {
        getItems().addAll((Object[])new MenuItem[] { paramRx.A(str) });
      } 
    } 
    return this;
  }
  
  public void a() {
    Objects.requireNonNull(getItems());
    Iterator<MenuItem> iterator = getItems().iterator();
    if (License.a().f())
      while (iterator.hasNext()) {
        MenuItem menuItem = iterator.next();
        if (menuItem.getStyleClass().contains("e4se"))
          Rx.a(menuItem); 
      }  
  }
}
