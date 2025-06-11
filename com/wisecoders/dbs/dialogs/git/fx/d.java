package com.wisecoders.dbs.dialogs.git.fx;

import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TableCell;
import javafx.scene.control.Tooltip;
import org.eclipse.jgit.revplot.PlotCommit;

class d extends TableCell {
  private final Canvas b;
  
  private final Tooltip c;
  
  private d(FxGitDialog paramFxGitDialog) {
    this.b = new Canvas(200.0D, 22.0D);
    this.c = new Tooltip();
    setStyle("-fx-padding: 0px;");
    setTooltip(this.c);
  }
  
  protected void a(PlotCommit paramPlotCommit, boolean paramBoolean) {
    super.updateItem(paramPlotCommit, paramBoolean);
    setText(null);
    setGraphic(null);
    this.c.setText(null);
    if (!paramBoolean) {
      this.a.C.a(paramPlotCommit, this.b);
      setGraphic((Node)this.b);
      if (paramPlotCommit != null)
        this.c.setText(paramPlotCommit.getName()); 
    } 
  }
}
