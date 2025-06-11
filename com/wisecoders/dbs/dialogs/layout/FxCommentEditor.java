package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.FxUnitEditor;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Function;
import com.wisecoders.dbs.schema.Procedure;
import com.wisecoders.dbs.schema.Trigger;
import com.wisecoders.dbs.sys.fx.GridPane$;
import javafx.scene.Node;

public class FxCommentEditor extends FxDbDialog implements FxUnitEditor {
  private final Unit c;
  
  public final FxCommentPanel a;
  
  public FxCommentEditor(Workspace paramWorkspace, Unit paramUnit) {
    super(paramWorkspace, true);
    this.c = paramUnit;
    this.a = new FxCommentPanel(paramWorkspace, paramUnit instanceof AbstractUnit, paramUnit);
    setDialogTitle(getTitle() + " on " + getTitle());
  }
  
  public Node createContentPane() {
    GridPane$ gridPane$ = (new GridPane$()).l().a(new int[] { -2, -1 }).b(new int[] { -1 });
    gridPane$.a((Node)this.rx.e("description"), "0,0,r,c");
    gridPane$.a((Node)this.a, "1,0,f,f");
    return (Node)gridPane$;
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
  
  public boolean validate() {
    return true;
  }
  
  public void applyChanges() {
    if (this.doImplement) {
      Unit unit = this.c;
      if (unit instanceof Procedure) {
        Procedure procedure = (Procedure)unit;
        unit = new Procedure(procedure.schema, this.c.getName());
        unit.setText(procedure.getText());
        unit.setComment(this.a.b());
        unit.setCommentTags(this.a.a());
        addSyncOperation(procedure.schema, procedure, (AbstractUnit)unit);
      } else {
        unit = this.c;
        if (unit instanceof Function) {
          Function function = (Function)unit;
          unit = new Function(function.schema, this.c.getName());
          unit.setText(function.getText());
          unit.setComment(this.a.b());
          unit.setCommentTags(this.a.a());
          addSyncOperation(function.schema, function, (AbstractUnit)unit);
        } else {
          unit = this.c;
          if (unit instanceof Trigger) {
            Trigger trigger = (Trigger)unit;
            unit = new Trigger(trigger.schema, this.c.getName(), trigger.getOwningTable());
            unit.setText(trigger.getText());
            unit.setComment(this.a.b());
            unit.setCommentTags(this.a.a());
            addSyncOperation(trigger.schema, trigger, (AbstractUnit)unit);
          } 
        } 
      } 
    } else {
      this.c.setComment(this.a.b());
      this.c.setCommentTags(this.a.a());
    } 
  }
  
  public AbstractUnit a() {
    return (AbstractUnit)this.c;
  }
  
  public void saveSucceeded() {}
}
