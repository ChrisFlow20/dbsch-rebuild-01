package com.wisecoders.dbs.generator.fx;

import com.wisecoders.dbs.diagram.model.Entity;
import com.wisecoders.dbs.diagram.model.Unit;
import com.wisecoders.dbs.dialogs.layout.FxEditorFactory;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.generator.engine.plan.DefinedPattern;
import com.wisecoders.dbs.project.fx.Workspace;
import com.wisecoders.dbs.schema.ChildEntity;
import com.wisecoders.dbs.schema.Column;
import com.wisecoders.dbs.schema.GeneratorTable;
import com.wisecoders.dbs.schema.Table;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Log;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.FlowPane$;
import com.wisecoders.dbs.sys.fx.FxEditableStringTableCell;
import com.wisecoders.dbs.sys.fx.FxEditableStringTableCell$Type;
import com.wisecoders.dbs.sys.fx.HGrowBox$;
import com.wisecoders.dbs.sys.fx.glyph.BootstrapIcons;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

public class FxGeneratorTableDialog extends ButtonDialog$ implements WorkspaceWindow {
  private final Workspace a;
  
  private final TableView b = new TableView();
  
  private final Entity c;
  
  private final boolean d;
  
  private final Map e = new HashMap<>();
  
  private final TableColumn f;
  
  private final TableColumn i;
  
  private final TableColumn j;
  
  private final TableColumn k;
  
  private final TableColumn l;
  
  public FxGeneratorTableDialog(WorkspaceWindow paramWorkspaceWindow, Entity paramEntity, boolean paramBoolean) {
    super(paramWorkspaceWindow);
    this.f = new TableColumn("Column");
    this.i = new TableColumn("Null%");
    this.j = new TableColumn("Generator");
    this.k = new TableColumn("Seed");
    this.l = new TableColumn("Sample Output");
    setDialogTitle("Table '" + String.valueOf(paramEntity) + "' Column Generators");
    setGraphic(BootstrapIcons.dice_5_fill);
    this.a = paramWorkspaceWindow.getWorkspace();
    this.c = paramEntity;
    this.d = paramBoolean;
    this.rx.a("flagSelected", () -> (this.b.getSelectionModel().getSelectedItem() != null));
    this.rx.a("flagCanEditNulls", () -> (this.b.getSelectionModel().getSelectedItem() != null && ((Column)this.b.getSelectionModel().getSelectedItem()).isMandatory()));
    this.rx.b();
    a();
    c();
    showDialog();
  }
  
  private void a() {
    this.f.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue()));
    this.f.setCellFactory(paramTableColumn -> new f());
    this.i.setCellValueFactory(paramCellDataFeatures -> new SimpleStringProperty("" + ((Column)paramCellDataFeatures.getValue()).getGeneratorNullsPercentage()));
    this.i.setGraphic(Rx.a());
    this.i.setCellFactory(paramTableColumn -> new g());
    this.i.setEditable(true);
    this.i.setStyle("-fx-alignment: center;");
    this.i.setOnEditCommit(paramCellEditEvent -> {
          try {
            ((Column)paramCellEditEvent.getRowValue()).setGeneratorNullsPercentage(Short.parseShort((String)paramCellEditEvent.getNewValue()));
          } catch (NumberFormatException numberFormatException) {}
          this.a.u();
        });
    this.j.setCellValueFactory(paramCellDataFeatures -> new SimpleStringProperty(((Column)paramCellDataFeatures.getValue()).getOrGuessGeneratorPattern()));
    this.j.setCellFactory(paramTableColumn -> new FxEditableStringTableCell());
    this.j.setGraphic(Rx.a());
    this.j.setEditable(false);
    this.k.setCellValueFactory(paramCellDataFeatures -> new SimpleStringProperty("" + ((Column)paramCellDataFeatures.getValue()).getGeneratorSeed()));
    this.k.setGraphic(Rx.a());
    this.k.setCellFactory(paramTableColumn -> new FxEditableStringTableCell(FxEditableStringTableCell$Type.c));
    this.k.setOnEditCommit(paramCellEditEvent -> {
          Column column = (Column)paramCellEditEvent.getRowValue();
          try {
            column.setGeneratorSeed(Integer.parseInt((String)paramCellEditEvent.getNewValue()));
          } catch (NumberFormatException numberFormatException) {}
          c();
          this.b.refresh();
          this.a.u();
        });
    this.l.setCellValueFactory(paramCellDataFeatures -> new SimpleStringProperty((String)this.e.get(paramCellDataFeatures.getValue())));
    this.b.getColumns().addAll((Object[])new TableColumn[] { this.f, this.j, this.i, this.k, this.l });
    this.b.setItems((this.c instanceof ChildEntity) ? FXCollections.observableArrayList(((ChildEntity)this.c).columns) : 
        FXCollections.observableArrayList(((Table)this.c).columns));
    this.b.setEditable(true);
    this.b.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramColumn1, paramColumn2) -> this.rx.b());
    this.b.setOnMouseClicked(paramMouseEvent -> {
          if (paramMouseEvent.getClickCount() == 2 && this.j == ((TablePosition)this.b.getSelectionModel().getSelectedCells().get(0)).getTableColumn()) {
            b();
            this.b.refresh();
          } 
        });
    Dialog$.setRegionPrefSize((Region)this.b, 950.0D, 450.0D);
    Rx.a(this.b, new double[] { 0.25D, 0.4D, 0.08D, 0.07D, 0.2D });
  }
  
  private void b() {
    Column column = (Column)this.b.getSelectionModel().getSelectedItem();
    if (column != null)
      (new FxGeneratorPatternDialog(this, column, column.getOrGuessGeneratorPattern(), true))
        .showDialog().ifPresent(paramString -> {
            if (paramString.endsWith("%"))
              paramString = paramString.substring(0, paramString.length() - 1).trim(); 
            paramColumn.setGeneratorPattern(paramString);
          }); 
  }
  
  public Node createContentPane() {
    BorderPane borderPane = new BorderPane();
    borderPane.setTop((Node)(new FlowPane$()).h().a(new Node[] { (Node)new HGrowBox$(), (Node)new Label("= Mandatory (Not Null)", (Node)BootstrapIcons.asterisk.glyph(new String[] { "glyph-red" })) }));
    borderPane.setCenter((Node)this.b);
    borderPane.setBottom((Node)(new FlowPane$()).h().a(this.rx.c(new String[] { "editPattern", "showRepository", "editNulls" })));
    return (Node)borderPane;
  }
  
  public void createButtons() {
    createCloseButton();
    createHelpButton("random-data-generator.html#patterns");
    if (this.d)
      createActionButton("generate"); 
    createActionButton("samples");
  }
  
  @Action(b = "flagCanEditNulls")
  public void editNulls() {
    this.b.edit(this.b.getSelectionModel().getSelectedIndex(), this.i);
  }
  
  @Action(b = "flagSelected")
  public void editPattern() {
    Column column = (Column)this.b.getSelectionModel().getSelectedItem();
    (new FxGeneratorPatternDialog(this, column, column.getOrGuessGeneratorPattern(), true)).showDialog().ifPresent(paramString -> {
          if (paramColumn.setGeneratorPattern(paramString))
            this.a.u(); 
          c();
          this.b.refresh();
        });
  }
  
  @Action(b = "flagSelected")
  public void editColumn() {
    FxEditorFactory.a(this.a, (Unit)this.b.getSelectionModel().getSelectedItem());
  }
  
  @Action
  public void editTable() {
    FxEditorFactory.a(this.a, this.c);
  }
  
  @Action
  public void samples() {
    Table table = (this.c instanceof ChildEntity && this.c.getEntity() instanceof Table) ? (Table)this.c.getEntity() : ((this.c instanceof Table) ? (Table)this.c : null);
    if (table != null)
      new FxGeneratorSamplesDialog(this.a, table); 
  }
  
  @Action(b = "flagSelected")
  public void showRepository() {
    (new FxGeneratorRepositoryDialog(this)).showDialog().ifPresent(paramDefinedPattern -> {
          Column column = (Column)this.b.getSelectionModel().getSelectedItem();
          if (column != null) {
            if (column.setGeneratorPattern(paramDefinedPattern.e()))
              this.a.u(); 
            c();
            this.b.refresh();
          } 
        });
  }
  
  @Action
  public void generate() {
    close();
    new FxGeneratorEditor(this.a, Collections.singletonList(this.c));
  }
  
  public boolean apply() {
    return true;
  }
  
  private void c() {
    if (this.c instanceof Table) {
      GeneratorTable generatorTable = new GeneratorTable((Table)this.c, true);
      try {
        generatorTable.initialize();
        Map map = generatorTable.generateValuesSet();
        for (Column column : map.keySet())
          this.e.put(column, String.valueOf(map.get(column))); 
      } catch (Throwable throwable) {
        Log.b(throwable);
      } 
    } 
  }
  
  public Workspace getWorkspace() {
    return this.a;
  }
}
