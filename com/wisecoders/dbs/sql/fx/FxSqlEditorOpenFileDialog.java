package com.wisecoders.dbs.sql.fx;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.dbms.DbmsLocation;
import com.wisecoders.dbs.dbms.connect.fx.FxConnectorEditor;
import com.wisecoders.dbs.dbms.reverseEngineer.tasks.FxImportSelectionTask;
import com.wisecoders.dbs.diagram.fx.FxLayoutPane;
import com.wisecoders.dbs.diagram.model.Point;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.ConnectorManager;
import com.wisecoders.dbs.schema.Project;
import com.wisecoders.dbs.sys.License;
import com.wisecoders.dbs.sys.ToggleGroup$;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import java.util.regex.Pattern;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleButton;

public class FxSqlEditorOpenFileDialog extends Dialog$ {
  private final ComboBox a = new ComboBox();
  
  private final RadioButton b;
  
  private final RadioButton c;
  
  private final FxLayoutPane d;
  
  private final FxSqlEditor e;
  
  public FxSqlEditorOpenFileDialog(FxLayoutPane paramFxLayoutPane, FxSqlEditor paramFxSqlEditor) {
    super(paramFxSqlEditor.getWorkspace());
    this.d = paramFxLayoutPane;
    this.e = paramFxSqlEditor;
    boolean bool = License.a().e();
    if (bool)
      this.rx.G("pro"); 
    this.a.getItems().addAll(Dbms.getKnownDbmsExclude(new String[0]));
    this.a.setValue(Pref.c("ParseRdbms", "MySql"));
    this.b = this.rx.w("parseRadio");
    this.c = this.rx.i("connectRadio", true);
    new ToggleGroup$(new ToggleButton[] { (ToggleButton)this.b, (ToggleButton)this.c });
    this.b.setDisable(!bool);
    String str = paramFxSqlEditor.i.t();
    if (bool && str != null && Pattern.compile("create\\s+(\\w+\\s+)?(\\w+\\s+)?table", 10).matcher(str).find())
      this.b.setSelected(true); 
  }
  
  public Node createContentPane() {
    return (Node)(new GridPane$()).l()
      .a((Node)this.rx.e("dbmsLabel"), "0,0,r,c")
      .a((Node)this.a, "1,0,l,c")
      .a((Node)this.rx.e("actionLabel"), "0,1,f,c")
      .a((Node)this.b, "1,1,l,c")
      .a((Node)this.c, "1,2,l,c");
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
  
  public boolean apply() {
    Project project = this.e.x();
    project.setDbId((String)this.a.getValue());
    Pref.a("ParseRdbms", (String)this.a.getValue());
    if (this.c.isSelected()) {
      (new FxConnectorEditor(this.e.getWorkspace(), ConnectorManager.createConnector((String)this.a.getValue()), DbmsLocation.a, false))
        .showDialog().ifPresent(paramConnector -> {
            paramConnector.learnDbmsIfRequired();
            paramProject.rename(paramConnector.getName());
            this.e.getWorkspace().u();
            this.e.getWorkspace().getRx().a(new FxImportSelectionTask(this.e.getWorkspace(), paramProject, paramConnector));
            this.d.setDividerPosition(0, 0.2D);
          });
    } else {
      this.e.getWorkspace().getRx().a(this.e.parseScript());
    } 
    this.d.d.diagram.createCallout(this.rx.H("messageCallout"), new Point(0.0D, 0.0D));
    this.d.q();
    return true;
  }
}
