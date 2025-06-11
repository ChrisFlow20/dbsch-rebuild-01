package com.wisecoders.dbs.dialogs.git.fx;

import com.wisecoders.dbs.sys.ColorUtil;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.paint.Color;
import org.eclipse.jgit.revplot.PlotCommit;

class c extends TableCell {
  private c(FxGitDialog paramFxGitDialog) {
    setStyle("-fx-padding: 0px; -fx-alignment: center-left;");
  }
  
  protected void a(PlotCommit paramPlotCommit, boolean paramBoolean) {
    super.updateItem(paramPlotCommit, paramBoolean);
    setGraphic(null);
    setText(null);
    if (!paramBoolean)
      if (paramPlotCommit == null) {
        setText("Current Work");
      } else if (paramPlotCommit.getRefCount() > 0) {
        String str = paramPlotCommit.getRef(0).getName();
        for (byte b = 0; b < paramPlotCommit.getRefCount(); b++) {
          if (paramPlotCommit.getRef(b).getName().startsWith("refs/heads")) {
            str = paramPlotCommit.getRef(b).getName();
            break;
          } 
        } 
        if (str != null) {
          Label label1 = null;
          if (str.startsWith("refs/remotes/origin/")) {
            str = str.substring("refs/remotes/origin/".length());
            label1 = BootstrapIcons.github.glyph(new String[0]);
          } else if (str.startsWith("refs/heads/")) {
            str = str.substring("refs/heads/".length());
            label1 = BootstrapIcons.laptop.glyph(new String[0]);
          } 
          Label label2 = new Label(str, (Node)label1);
          label2.setStyle("-fx-background-color:#" + ColorUtil.b(Color.web("#bccdde"), this.a.C.a(paramPlotCommit.getLane())) + "55;");
          setGraphic((Node)label2);
        } 
      }  
  }
}
