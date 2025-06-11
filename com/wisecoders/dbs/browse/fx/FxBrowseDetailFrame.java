package com.wisecoders.dbs.browse.fx;

import com.wisecoders.dbs.browse.flow.FxFlowFrame;
import com.wisecoders.dbs.browse.model.BrowseDetailResult;
import com.wisecoders.dbs.browse.model.BrowseTable;
import com.wisecoders.dbs.data.model.result.ResultStatus;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.sys.fx.HGrowBox$;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

public class FxBrowseDetailFrame extends FxFlowFrame {
  private final BrowseTable h;
  
  private final BrowseDetailResult i;
  
  private final TextArea j = new TextArea();
  
  private final Label k = new Label();
  
  private final Label l = new Label();
  
  FxBrowseDetailFrame(FxBrowseEditor paramFxBrowseEditor, BrowseTable paramBrowseTable, Attribute paramAttribute, BrowseDetailResult paramBrowseDetailResult) {
    super(paramFxBrowseEditor.c);
    this.h = paramBrowseTable;
    this.i = paramBrowseDetailResult;
    if (paramBrowseTable == null || paramAttribute == null || paramBrowseDetailResult == null)
      throw new NullPointerException("Browse Viewer initialized with some NULL parameters."); 
    Label label = new Label(paramAttribute.getName() + " as " + paramAttribute.getName());
    this.c.getChildren().addAll((Object[])new Node[] { (Node)new HGrowBox$(), (Node)label, (Node)new HGrowBox$(), (Node)


          
          a() });
    this.d.getChildren().addAll((Object[])new Node[] { (Node)new HGrowBox$(), (Node)this.f });
    setLayoutX((paramBrowseTable.k() + paramBrowseTable.m() + 30));
    setLayoutY(paramBrowseTable.l());
    paramBrowseDetailResult.b(paramVoid -> {
          Platform.runLater(this::h);
          return null;
        });
    this.j.setEditable(false);
    Node node = (Node)(paramBrowseDetailResult.e() ? this.k : this.j);
    ScrollPane scrollPane = new ScrollPane(node);
    scrollPane.setFitToHeight(true);
    scrollPane.setFitToWidth(true);
    this.e.setCenter((Node)scrollPane);
    h();
    toFront();
  }
  
  private void h() {
    if (this.h.c.isMarkedForDeletion()) {
      f();
    } else if (this.h.e.j() == ResultStatus.d) {
      this.k.setGraphic((Node)new ImageView(this.i.d()));
      this.j.setText(this.i.a());
      this.l.setText(this.i.b());
    } 
  }
  
  protected void e() {}
  
  public void a(int paramInt1, int paramInt2) {}
  
  public void b(int paramInt1, int paramInt2) {}
}
