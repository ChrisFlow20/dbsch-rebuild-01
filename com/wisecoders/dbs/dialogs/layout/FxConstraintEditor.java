package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.config.fx.FxSyntaxOptionComboBox;
import com.wisecoders.dbs.config.model.LetterCase;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.FxUnitEditor;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.Constraint;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.DelayedTimeline;
import com.wisecoders.dbs.sys.DelayedTimelineHandler;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.GridPane$;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class FxConstraintEditor extends FxDbDialog implements FxUnitEditor {
  private final String a;
  
  private final Table c;
  
  private final Constraint d;
  
  private final FxSyntaxOptionComboBox e;
  
  private final boolean f;
  
  private final TextField i = new TextField();
  
  private final TextArea j = new TextArea();
  
  private final FxCommentPanel k;
  
  private final Label l;
  
  private static final String m = "CHECK";
  
  private final ComboBox n = new ComboBox();
  
  public FxConstraintEditor(WorkspaceWindow paramWorkspaceWindow, Table paramTable, boolean paramBoolean) {
    this(paramWorkspaceWindow, paramTable, (Constraint)null, paramBoolean);
  }
  
  public FxConstraintEditor(WorkspaceWindow paramWorkspaceWindow, Constraint paramConstraint, boolean paramBoolean) {
    this(paramWorkspaceWindow, paramConstraint.getEntity(), paramConstraint, paramBoolean);
  }
  
  private FxConstraintEditor(WorkspaceWindow paramWorkspaceWindow, Table paramTable, Constraint paramConstraint, boolean paramBoolean) {
    super(paramWorkspaceWindow, paramBoolean);
    this.c = paramTable;
    this.d = paramConstraint;
    this.f = (paramConstraint == null);
    this.a = paramTable.schema.project.getDbId();
    this.e = new FxSyntaxOptionComboBox(this, (Dbms.get(this.a)).constraintOptions);
    this.k = new FxCommentPanel(paramWorkspaceWindow.getWorkspace(), paramConstraint);
    if ((Dbms.get(this.a)).constraintTypes.p()) {
      String[] arrayOfString = (Dbms.get(this.a)).constraintTypes.c_().split(",");
      if (arrayOfString.length > 0) {
        this.n.getItems().addAll((Object[])arrayOfString);
        this.n.setValue(arrayOfString[0]);
      } 
    } else {
      this.n.getItems().add("CHECK");
      this.n.setDisable(true);
    } 
    this.l = this.rx.e("nameLabel");
  }
  
  public Node createContentPane() {
    GridPane$ gridPane$ = (new GridPane$()).e().g();
    gridPane$.a(new int[] { -2, -1 });
    gridPane$.b(new int[] { -2, -1, -2, -1 });
    gridPane$.a((Node)this.l, "0,0,r,c");
    gridPane$.a((Node)this.i, "1,0,f,c");
    gridPane$.a((Node)this.rx.e("descriptionLabel"), "0,1,r,c");
    gridPane$.a((Node)this.k, "1,1,f,f");
    gridPane$.a((Node)this.rx.e("typeLabel"), "0,2,r,c");
    gridPane$.a((Node)this.n, "1,2,l,c");
    gridPane$.a((Node)this.rx.e("scriptLabel"), "0,3,r,c");
    gridPane$.a((Node)this.j, "1,3,f,f");
    if ((Dbms.get(this.a)).constraintOptions.j()) {
      gridPane$.a((Node)this.rx.e("optionsLabel"), "0,3,r,c");
      gridPane$.a((Node)this.e, "1,3,f,f");
    } 
    if ((Dbms.get(this.a)).autoLetterCases.b()) {
      LetterCase letterCase = Dbms.get(this.a).getLetterCases();
      Rx.a(this.i, letterCase);
      this.l.setText("Name (" + String.valueOf(letterCase) + ")");
    } 
    String str = "CHECK";
    if (this.d == null) {
      this.i.setText(this.c.schema.nameFinder.a(this.c, (Column)null));
      this.i.selectAll();
    } else {
      this.i.setText(this.d.getName());
      this.j.setText(this.d.getText());
      if (this.d.getType() != null)
        str = this.d.getType(); 
    } 
    this.n.setValue(str);
    DelayedTimeline delayedTimeline = new DelayedTimeline(this);
    DelayedTimelineHandler delayedTimelineHandler = delayedTimeline.a(1000L, this::b);
    this.i.setOnKeyTyped(paramKeyEvent -> paramDelayedTimelineHandler.a());
    setInitFocusedNode((Node)this.i);
    return (Node)gridPane$;
  }
  
  private void b() {
    String str = this.i.getText();
    Constraint constraint = (Constraint)AbstractUnit.getByName(this.c.constraints, str);
    if (this.f)
      if (constraint != null && constraint != this.d) {
        showNotificationPane("duplicateNameWarning");
      } else if (Dbms.get(this.c).isReservedKeyword(str)) {
        showNotificationPane("reservedKeywordWarning");
      } else if (StringUtil.isFilledTrim(str) && this.c.columns.getByName(str) != null) {
        showNotificationPane("duplicateNameWithColumnWarning");
      }  
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
    createHelpButton("schema.html#cons");
  }
  
  public boolean validate() {
    String str1 = this.i.getText();
    if (str1 != null)
      str1 = str1.trim(); 
    String str2 = this.j.getText();
    if (StringUtil.isEmptyTrim(str1)) {
      showError("Please set the check name.");
      this.i.requestFocus();
      return false;
    } 
    if (StringUtil.isEmptyTrim(str2)) {
      showError("Please set the check text.");
      this.j.requestFocus();
      return false;
    } 
    if (this.c.constraints.isWrongName(str1, this.d)) {
      showError("Please choose a different name.\n A check with this name already exists.");
      this.i.requestFocus();
      return false;
    } 
    return true;
  }
  
  public void applyChanges() {
    String str1 = this.i.getText().trim();
    String str2 = this.j.getText();
    Constraint constraint = this.doImplement ? new Constraint(this.c, str1) : ((this.d != null) ? this.d : this.c.createConstraint(str1));
    constraint.setText(str2);
    constraint.setOptions(this.e.a());
    if (!"CHECK".equals(this.n.getValue()))
      constraint.setType((String)this.n.getValue()); 
    constraint.setComment(this.k.b());
    constraint.setCommentTags(this.k.a());
    addSyncOperation(this.c, this.d, constraint);
  }
  
  public AbstractUnit a() {
    return this.d;
  }
  
  public void saveSucceeded() {}
}
