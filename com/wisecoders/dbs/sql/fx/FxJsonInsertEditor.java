package com.wisecoders.dbs.sql.fx;

import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.editors.text.StyledEditor;
import com.wisecoders.dbs.editors.text.StyledEditorCompletion;
import com.wisecoders.dbs.loader.engine.JSONLoaderCallback;
import com.wisecoders.dbs.loader.engine.JSONParseException;
import com.wisecoders.dbs.loader.engine.JSONParser;
import com.wisecoders.dbs.loader.fx.FxLoaderFileDialog;
import com.wisecoders.dbs.loader.model.LoaderMode;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.Language;
import com.wisecoders.dbs.schema.Schema;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sql.generator.JsonScriptGenerator;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.RowPane$;
import java.util.List;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class FxJsonInsertEditor extends ButtonDialog$ implements WorkspaceWindow {
  private Connector a;
  
  private final Workspace b;
  
  private final StyledEditor c;
  
  private final ComboBox d = new ComboBox();
  
  private final TextArea e = new TextArea();
  
  private boolean f = false;
  
  public FxJsonInsertEditor(WorkspaceWindow paramWorkspaceWindow, Connector paramConnector, List<Entity> paramList) {
    this(paramWorkspaceWindow, paramConnector, (paramList.size() > 0 && paramList.get(0) instanceof Entity) ? paramList.get(0) : null);
  }
  
  public FxJsonInsertEditor(WorkspaceWindow paramWorkspaceWindow, Connector paramConnector, Entity paramEntity) {
    super(paramWorkspaceWindow);
    this.b = paramWorkspaceWindow.getWorkspace();
    this.c = new StyledEditor();
    this.a = paramConnector;
    this.rx.a("flagCanExecute", () -> !this.f);
    this.d.setEditable(true);
    this.d.setPromptText(this.rx.H("collectionComboPrompt"));
    HBox.setHgrow((Node)this.d, Priority.ALWAYS);
    this.d.setMaxWidth(Double.MAX_VALUE);
    this.e.setPromptText(this.rx.H("messagePrompt"));
    this.c.b((paramEntity != null) ? JsonScriptGenerator.a(paramEntity.getEntity()) : "{\n\"\":\"\"\n}");
    this.c.a(Language.d);
    this.c.J.a((StyledEditorCompletion)null);
    for (Schema schema : (this.b.m()).schemas) {
      for (Table table : schema.tables) {
        String str = table.ref();
        this.d.getItems().add(str);
        if (table == paramEntity)
          this.d.setValue(str); 
      } 
    } 
    this.rx.b();
    showDialog();
  }
  
  public Node createContentPane() {
    RowPane$ rowPane$ = (new RowPane$()).g().a(new Node[] { (Node)this.rx.e("collectionName"), (Node)this.d, (Node)this.rx.j("validateJson"), (Node)this.rx.j("showQuery"), (Node)this.rx.j("loadFromFile") }).b((Node)this.c);
    setRegionPrefHeight((Region)this.e, 120.0D);
    this.e.setEditable(false);
    SplitPane splitPane = new SplitPane(new Node[] { (Node)rowPane$, (Node)this.e });
    splitPane.setOrientation(Orientation.VERTICAL);
    setRegionPrefSize((Region)splitPane, 800.0D, 600.0D);
    Platform.runLater(() -> paramSplitPane.setDividerPositions(new double[] { 0.75D }));
    return (Node)splitPane;
  }
  
  public void createButtons() {
    createActionButton("execute");
    createCancelButton();
  }
  
  @Action(b = "flagCanExecute")
  public Task execute() {
    if (StringUtil.isEmptyTrim((String)this.d.getValue())) {
      showError("Please choose a collection.");
    } else if (StringUtil.isEmptyTrim(this.c.t())) {
      showError("Json Document cannot be empty.");
    } else {
      return new FxJsonInsertEditor$QueryExecutionEngine(this, a());
    } 
    return null;
  }
  
  public String a() {
    String str = (String)this.d.getValue();
    int i = str.indexOf(".");
    if (i > 0) {
      str = str.substring(0, i) + ".getCollection('" + str.substring(0, i) + "')";
    } else {
      str = "db.getCollection('" + str + "')";
    } 
    return str + ".insertMany([" + str + "]);";
  }
  
  @Action(b = "flagCanExecute")
  public void validateJson() {
    this.e.setText(null);
    String str = this.c.t();
    if (StringUtil.isEmptyTrim(str)) {
      showInformation("Nothing to validate", new String[0]);
    } else {
      try {
        JSONParser.parse("[" + this.c.t() + "]", new JSONLoaderCallback());
        this.e.setText("JSON is valid");
      } catch (JSONParseException jSONParseException) {
        this.e.setText("Error at position " + jSONParseException.a());
        this.c.b(jSONParseException.a(), jSONParseException.getLocalizedMessage());
      } catch (Exception exception) {
        this.e.setText(exception.toString());
        this.c.requestFocus();
      } 
    } 
  }
  
  @Action(b = "flagCanExecute")
  public void showQuery() {
    if (StringUtil.isEmptyTrim((String)this.d.getValue())) {
      showInformation("Please choose a collection.", new String[0]);
    } else {
      showInformation(a(), new String[0]);
    } 
  }
  
  @Action
  public void loadFromFile() {
    new FxLoaderFileDialog(this, null, LoaderMode.a);
  }
  
  public boolean apply() {
    return true;
  }
  
  public Workspace getWorkspace() {
    return this.b;
  }
}
