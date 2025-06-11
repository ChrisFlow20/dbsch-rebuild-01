package com.wisecoders.dbs.browse.fx;

import com.wisecoders.dbs.data.model.result.ResultStatus;
import com.wisecoders.dbs.diagram.model.Attribute;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.sys.ToggleGroup$;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;

public class FxBrowseDetailsDialog extends ButtonDialog$ {
  private final FxBrowseTableView a;
  
  private final ComboBox b = new ComboBox();
  
  private final RadioButton c = new RadioButton("Image (gif, png or jpeg)");
  
  private final RadioButton d = new RadioButton("Plain text");
  
  private final RadioButton e = new RadioButton("Open in native application as ");
  
  private final Attribute f;
  
  public FxBrowseDetailsDialog(Workspace paramWorkspace, FxBrowseTableView paramFxBrowseTableView, Attribute paramAttribute) {
    super(paramWorkspace.getWindow());
    this.a = paramFxBrowseTableView;
    this.f = paramAttribute;
    this.b.getItems().addAll((Object[])new String[] { "html", "doc", "xls", "png", "gif", "other (edit)" });
    this.rx.a("flagCanView", () -> (paramFxBrowseTableView.d.e.j() != ResultStatus.e));
    new ToggleGroup$(new ToggleButton[] { (ToggleButton)this.c, (ToggleButton)this.d, (ToggleButton)this.e });
    this.d.setSelected(true);
    this.b.setEditable(true);
    this.rx.b();
    showDialog();
  }
  
  public Node createContentPane() {
    GridPane$ gridPane$ = (new GridPane$()).e().g();
    gridPane$.a((Node)this.d, "0,0,l,c");
    gridPane$.a((Node)this.c, "0,1,l,c");
    gridPane$.a((Node)this.e, "0,2,l,c");
    gridPane$.a((Node)this.b, "1,2,f,c");
    return (Node)gridPane$;
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
  
  public boolean apply() {
    if (this.c.isSelected()) {
      this.a.a(this.f, "png", false);
      close();
    } else if (this.d.isSelected()) {
      this.a.a(this.f, "txt", false);
      close();
    } else if (this.e.isSelected()) {
      String str = (String)this.b.getValue();
      if (str == null || str.length() == 0) {
        this.rx.d(getDialogPane().getScene(), "Please enter the document extension");
        return false;
      } 
      this.a.a(this.f, str, true);
      close();
    } 
    return true;
  }
}
