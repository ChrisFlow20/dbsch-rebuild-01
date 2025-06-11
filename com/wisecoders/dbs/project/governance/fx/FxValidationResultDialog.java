package com.wisecoders.dbs.project.governance.fx;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dialogs.layout.FxEditorFactory;
import com.wisecoders.dbs.dialogs.layout.WorkspaceWindow;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.project.governance.model.InspectorRoot;
import com.wisecoders.dbs.project.governance.model.InspectorWarning;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.ButtonDialog$;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.GridPane$;
import com.wisecoders.dbs.sys.fx.VBox$;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class FxValidationResultDialog extends ButtonDialog$ {
  private final WorkspaceWindow a;
  
  private final TableView b = new TableView();
  
  private final List c;
  
  private final boolean d;
  
  public FxValidationResultDialog(WorkspaceWindow paramWorkspaceWindow, boolean paramBoolean, List paramList) {
    super(paramWorkspaceWindow);
    this.a = paramWorkspaceWindow;
    this.c = paramList;
    this.d = paramBoolean;
    if (!paramBoolean)
      setHeaderText(null); 
  }
  
  public Node createContentPane() {
    TableColumn tableColumn1 = new TableColumn("Failing On");
    tableColumn1.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue()));
    tableColumn1.setCellFactory(paramTableColumn -> new e());
    tableColumn1.setSortable(false);
    tableColumn1.setGraphic(Rx.a());
    TableColumn tableColumn2 = new TableColumn("Failing Validators");
    tableColumn2.setCellValueFactory(paramCellDataFeatures -> new ReadOnlyObjectWrapper(paramCellDataFeatures.getValue()));
    tableColumn2.setCellFactory(paramTableColumn -> new d());
    tableColumn2.setSortable(false);
    this.b.setEditable(false);
    this.b.setOnMouseClicked(paramMouseEvent -> {
          InspectorWarning inspectorWarning = (InspectorWarning)this.b.getSelectionModel().getSelectedItem();
          if (paramMouseEvent.getClickCount() == 2 && inspectorWarning != null)
            FxEditorFactory.a(this.a, inspectorWarning.b); 
        });
    this.b.getColumns().addAll((Object[])new TableColumn[] { tableColumn1, tableColumn2 });
    this.b.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    Rx.a(this.b, new double[] { 0.3D, 0.7D });
    Dialog$.setRegionPrefSize((Region)this.b, 550.0D, 400.0D);
    VBox$.setVgrow((Node)this.b, Priority.ALWAYS);
    for (InspectorRoot inspectorRoot : this.c)
      this.b.getItems().addAll(inspectorRoot.c); 
    return (Node)(new GridPane$()).l().a(new int[] { -2, -1 }).b(new int[] { -1 }).a((Node)this.b, "1,0,f,f");
  }
  
  public void createButtons() {
    if (this.d) {
      createOkButton();
      createCancelButton();
    } else {
      createCloseButton();
      createActionButton("exportToFile");
    } 
  }
  
  @Action
  public void exportToFile() {
    File file = FxFileChooser.a(getDialogScene(), "Save Validation Result to File", Sys.d.toString(), FileOperation.b, new FileType[] { FileType.i });
    if (file != null) {
      StringBuilder stringBuilder = new StringBuilder();
      for (InspectorRoot inspectorRoot : this.c) {
        for (InspectorWarning inspectorWarning : inspectorRoot.c)
          stringBuilder.append(inspectorWarning.b.getName()).append(" ").append(inspectorWarning.a()).append("\n"); 
      } 
      try {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
        try {
          BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
          try {
            bufferedWriter.write(stringBuilder.toString());
            this.rx.d(getDialogScene(), "Export Done");
            bufferedWriter.close();
          } catch (Throwable throwable) {
            try {
              bufferedWriter.close();
            } catch (Throwable throwable1) {
              throwable.addSuppressed(throwable1);
            } 
            throw throwable;
          } 
          outputStreamWriter.close();
        } catch (Throwable throwable) {
          try {
            outputStreamWriter.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          } 
          throw throwable;
        } 
      } catch (IOException iOException) {
        this.rx.a(getDialogScene(), iOException);
      } 
    } 
  }
  
  public boolean apply() {
    return true;
  }
}
