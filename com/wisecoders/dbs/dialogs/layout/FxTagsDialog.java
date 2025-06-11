package com.wisecoders.dbs.dialogs.layout;

import com.wisecoders.dbs.dbms.Dbms;
import com.wisecoders.dbs.schema.CommentTag;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.StringUtil;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.FxEditableStringTableCell;
import com.wisecoders.dbs.sys.fx.HBox$;
import com.wisecoders.dbs.sys.fx.VBox$;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class FxTagsDialog extends Dialog$ {
  private final WorkspaceWindow a;
  
  private final TableView b = new TableView();
  
  public FxTagsDialog(WorkspaceWindow paramWorkspaceWindow, Scene paramScene) {
    super(paramWorkspaceWindow);
    this.a = paramWorkspaceWindow;
    this.rx.G(paramWorkspaceWindow.getWorkspace().m().getDbId());
    this.rx.a("selectedItem", () -> (this.b.getSelectionModel().getSelectedItem() != null));
    initOwner(paramScene.getWindow());
    this.b.setItems(Dbms.get(paramWorkspaceWindow.getWorkspace().m().getDbId()).getTags());
    this.b.getSelectionModel().selectedItemProperty().addListener((paramObservableValue, paramCommentTag1, paramCommentTag2) -> this.rx.b());
  }
  
  public Node createContentPane() {
    TableColumn tableColumn1 = new TableColumn(this.rx.H("nameColumn"));
    tableColumn1.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(((CommentTag)paramCellDataFeatures.getValue()).getName()));
    tableColumn1.setEditable(true);
    TableColumn tableColumn2 = new TableColumn(this.rx.H("projectColumn"));
    tableColumn2.setCellValueFactory(paramCellDataFeatures -> ((CommentTag)paramCellDataFeatures.getValue()).isForProject);
    tableColumn2.setCellFactory(CheckBoxTableCell.forTableColumn(tableColumn2));
    tableColumn2.setEditable(true);
    TableColumn tableColumn3 = new TableColumn(this.rx.H("entityColumn"));
    tableColumn3.setCellValueFactory(paramCellDataFeatures -> ((CommentTag)paramCellDataFeatures.getValue()).isForEntity);
    tableColumn3.setCellFactory(CheckBoxTableCell.forTableColumn(tableColumn3));
    tableColumn3.setEditable(true);
    TableColumn tableColumn4 = new TableColumn(this.rx.H("subjectAreaColumn"));
    tableColumn4.setCellValueFactory(paramCellDataFeatures -> ((CommentTag)paramCellDataFeatures.getValue()).isForSubjectArea);
    tableColumn4.setCellFactory(CheckBoxTableCell.forTableColumn(tableColumn3));
    tableColumn4.setEditable(true);
    TableColumn tableColumn5 = new TableColumn(this.rx.H("fieldColumn"));
    tableColumn5.setCellValueFactory(paramCellDataFeatures -> ((CommentTag)paramCellDataFeatures.getValue()).isForField);
    tableColumn5.setCellFactory(CheckBoxTableCell.forTableColumn(tableColumn5));
    TableColumn tableColumn6 = new TableColumn(this.rx.H("indexColumn"));
    tableColumn6.setCellValueFactory(paramCellDataFeatures -> ((CommentTag)paramCellDataFeatures.getValue()).isForIndex);
    tableColumn6.setCellFactory(CheckBoxTableCell.forTableColumn(tableColumn6));
    TableColumn tableColumn7 = new TableColumn(this.rx.H("relationColumn"));
    tableColumn7.setCellValueFactory(paramCellDataFeatures -> ((CommentTag)paramCellDataFeatures.getValue()).isForRelation);
    tableColumn7.setCellFactory(CheckBoxTableCell.forTableColumn(tableColumn3));
    TableColumn tableColumn8 = new TableColumn("Applies To");
    tableColumn8.getColumns().addAll((Object[])new TableColumn[] { tableColumn2, tableColumn4, tableColumn3, tableColumn5, tableColumn6, tableColumn7 });
    TableColumn tableColumn9 = new TableColumn(this.rx.H("valuesColumn"));
    tableColumn9.setCellValueFactory(paramCellDataFeatures -> new SimpleStringProperty(((CommentTag)paramCellDataFeatures.getValue()).getCommaSeparatedValuesOrGroovyScript()));
    tableColumn9.setCellFactory(paramTableColumn -> new FxEditableStringTableCell());
    tableColumn9.setEditable(true);
    tableColumn9.setOnEditCommit(paramCellEditEvent -> ((CommentTag)paramCellEditEvent.getRowValue()).setCommaSeparatedValuesOrGroovyScript((String)paramCellEditEvent.getNewValue()));
    this.b.getColumns().addAll((Object[])new TableColumn[] { tableColumn1, tableColumn8, tableColumn9 });
    this.b.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
    Rx.a(this.b, new TableColumn[] { tableColumn1, tableColumn2, tableColumn4, tableColumn3, tableColumn5, tableColumn6, tableColumn7, tableColumn9 }, new double[] { 0.2D, 0.1D, 0.1D, 0.1D, 0.1D, 0.1D, 0.1D, 0.2D });
    this.b.setEditable(true);
    this.b.setPrefSize(700.0D, 400.0D);
    HBox$ hBox$ = (new HBox$()).e().a(this.rx.c(new String[] { "addTag", "deleteTag" }));
    VBox.setVgrow((Node)this.b, Priority.ALWAYS);
    return (Node)(new VBox$()).l().c(new Node[] { (Node)this.b, (Node)hBox$ });
  }
  
  @Action
  public void addTag() {
    String str = this.rx.b(getDialogScene(), "addTag");
    if (StringUtil.isFilledTrim(str))
      this.b.getItems().add(new CommentTag(str, "efir")); 
  }
  
  @Action(b = "selectedItem")
  public void deleteTag() {
    this.b.getItems().remove(this.b.getSelectionModel().getSelectedItem());
  }
  
  public void createButtons() {
    createOkButton();
    createCancelButton();
  }
  
  public boolean apply() {
    Dbms dbms = Dbms.get(this.a.getWorkspace().m().getDbId());
    dbms.saveTags();
    dbms.root.j();
    return true;
  }
}
