package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.config.fx.FxSyntaxOptionComboBox;
import com.wisecoders.dbs.config.model.LetterCase;
import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.diagram.model.AbstractUnit;
import com.wisecoders.dbs.diagram.model.FxUnitEditor;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Sequence;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.GridPane$;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FxSequenceEditor extends FxDbDialog implements FxUnitEditor {
  private final Schema a;
  
  private final Sequence c;
  
  private final boolean d;
  
  private final TextField e = new TextField();
  
  private final FxCommentPanel f;
  
  private final Label i = this.rx.e("nameLabel");
  
  private final FxSyntaxOptionComboBox j;
  
  FxSequenceEditor(WorkspaceWindow paramWorkspaceWindow, Schema paramSchema) {
    this(paramWorkspaceWindow, paramSchema, (Sequence)null);
  }
  
  FxSequenceEditor(WorkspaceWindow paramWorkspaceWindow, Sequence paramSequence) {
    this(paramWorkspaceWindow, paramSequence.getSchema(), paramSequence);
  }
  
  private FxSequenceEditor(WorkspaceWindow paramWorkspaceWindow, Schema paramSchema, Sequence paramSequence) {
    super(paramWorkspaceWindow, true);
    setDialogTitle((paramSequence == null) ? "New Sequence" : "Edit Sequence");
    this.a = paramSchema;
    this.c = paramSequence;
    this.d = (paramSequence == null);
    this.j = new FxSyntaxOptionComboBox(this, (Dbms.get(paramSchema.getDbId())).sequenceOptions);
    this.j.b("Options");
    this.f = new FxCommentPanel(getWorkspace(), paramSequence);
  }
  
  public Node createContentPane() {
    GridPane$ gridPane$ = (new GridPane$()).a(new int[] { -2, -1 }).b(new int[] { -2, -1, -2, -2 }).l();
    gridPane$.a((Node)this.i, "0,0,r,c");
    gridPane$.a((Node)this.e, "1,0,f,c");
    gridPane$.a((Node)this.rx.e("descriptionLabel"), "0,1,r,c");
    gridPane$.a((Node)this.f, "1,1,f,f");
    Label label = this.rx.e("optionsLabel");
    label.visibleProperty().bind((ObservableValue)this.j.visibleProperty());
    gridPane$.a((Node)label, "0,2,r,c");
    gridPane$.a((Node)this.j, "1,2,f,c");
    Dbms dbms = Dbms.get(this.a);
    if (dbms.autoLetterCases.b()) {
      LetterCase letterCase = dbms.getLetterCases();
      Rx.a(this.e, letterCase);
      this.i.setText("Name (" + String.valueOf(letterCase) + ")");
    } 
    if (this.c == null) {
      if (dbms.autoLetterCases.b())
        Rx.a(this.e, dbms.getLetterCases()); 
      this.e.setText(this.a.nameFinder.a());
      this.e.selectAll();
      setInitFocusedNode((Node)this.e);
    } else {
      this.e.setText(this.c.getName());
      if (dbms.autoLetterCases.b())
        Rx.a(this.e, dbms.getLetterCases()); 
      this.j.a(this.c.getOptions());
    } 
    return (Node)gridPane$;
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
    createHelpButton("schema.html#seq");
  }
  
  public boolean validate() {
    String str = this.e.getText();
    if (StringUtil.isEmptyTrim(str)) {
      showError("Please set the sequence name.");
      this.e.requestFocus();
      return false;
    } 
    if (this.a.sequences.isWrongName(str, this.c)) {
      showError("Please choose a different name.\n A sequence with this name already exists.");
      this.e.requestFocus();
      return false;
    } 
    return true;
  }
  
  public void applyChanges() {
    String str = this.e.getText();
    Sequence sequence = this.doImplement ? new Sequence(this.a, str) : ((this.c != null) ? this.c : this.a.createSequence(str));
    sequence.setOptions(this.j.a());
    sequence.setComment(this.f.b());
    sequence.setCommentTags(this.f.a());
    addSyncOperation(this.a, this.c, sequence);
  }
  
  public AbstractUnit a() {
    return this.c;
  }
  
  public void saveSucceeded() {
    Sequence sequence = (Sequence)this.a.sequences.getByName(this.e.getText());
    if (sequence != null)
      this.f.a(sequence); 
  }
}
