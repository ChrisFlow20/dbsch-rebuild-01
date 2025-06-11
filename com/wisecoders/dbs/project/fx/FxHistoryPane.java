package com.wisecoders.dbs.project.fx;

import com.wisecoders.dbs.config.system.Sys;
import com.wisecoders.dbs.dbms.connect.model.SqlEvent;
import com.wisecoders.dbs.diagram.model.UnitProperty;
import com.wisecoders.dbs.dialogs.system.FileOperation;
import com.wisecoders.dbs.dialogs.system.FileType;
import com.wisecoders.dbs.dialogs.system.FxFileChooser;
import com.wisecoders.dbs.project.store.Pref;
import com.wisecoders.dbs.schema.Connector;
import com.wisecoders.dbs.schema.ConnectorManager;
import com.wisecoders.dbs.sys.Action;
import com.wisecoders.dbs.sys.Rx;
import com.wisecoders.dbs.sys.fx.ContextMenu$;
import com.wisecoders.dbs.sys.fx.Dialog$;
import com.wisecoders.dbs.sys.fx.LabeledPane$;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.Region;

public class FxHistoryPane extends LabeledPane$ {
  private static final int c = 100;
  
  private final FxFrame d;
  
  private final ListView e = new ListView();
  
  private final Rx f = new Rx(FxHistoryPane.class, this);
  
  public static final String a = "ExportFolderSQL";
  
  FxHistoryPane(FxFrame paramFxFrame) {
    a("SQL History");
    setCenter((Node)this.e);
    Dialog$.setRegionPrefSize((Region)this, 150.0D, 150.0D);
    this.d = paramFxFrame;
    this.f.a("flagSelectedItem", () -> (this.e.getSelectionModel().getSelectedItem() != null));
    this.e.setCellFactory(paramListView -> new a());
    this.e.setContextMenu((new ContextMenu$()).a(this.f, new String[] { "copySelected", "copyAll", "export", "separator", "clear" }));
    this.e.setStyle("-fx-background-insets: 0;");
    this.e.getStyleClass().add("project-structure");
  }
  
  @Action(d = "flagSelectedItem")
  public void copySelected() {
    SqlEvent sqlEvent = (SqlEvent)this.e.getSelectionModel().getSelectedItem();
    if (sqlEvent != null) {
      Clipboard clipboard = Clipboard.getSystemClipboard();
      ClipboardContent clipboardContent = new ClipboardContent();
      clipboardContent.putString(sqlEvent.toString());
      clipboard.setContent((Map)clipboardContent);
    } 
  }
  
  @Action
  public void copyAll() {
    StringBuilder stringBuilder = new StringBuilder();
    for (SqlEvent sqlEvent : this.e.getItems())
      stringBuilder.append(sqlEvent).append("\n"); 
    Clipboard clipboard = Clipboard.getSystemClipboard();
    ClipboardContent clipboardContent = new ClipboardContent();
    clipboardContent.putString(stringBuilder.toString());
    clipboard.setContent((Map)clipboardContent);
  }
  
  @Action
  public void clear() {
    this.e.getItems().clear();
    this.e.refresh();
  }
  
  @Action
  public void export() {
    String str = Pref.c("ExportFolderSQL", (this.d.m().getFile() != null) ? this.d.m().getFile().getParent() : null);
    File file = FxFileChooser.a(this.d, "Choose file", str, FileOperation.b, new FileType[] { FileType.r });
    if (file != null) {
      Pref.a("ExportFolderSQL", file.getParent());
      SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Sys.B.timestampFormat.c_());
      try {
        FileWriter fileWriter = new FileWriter(file);
        try {
          for (SqlEvent sqlEvent : this.e.getItems()) {
            fileWriter.write(simpleDateFormat.format(Long.valueOf(sqlEvent.d)));
            fileWriter.write(",");
            fileWriter.write(sqlEvent.toString());
            fileWriter.write("\n");
          } 
          this.f.d(this.d, "exportDone");
          fileWriter.close();
        } catch (Throwable throwable) {
          try {
            fileWriter.close();
          } catch (Throwable throwable1) {
            throwable.addSuppressed(throwable1);
          } 
          throw throwable;
        } 
      } catch (IOException iOException) {
        this.f.a(this.d, iOException);
      } 
    } 
  }
  
  void a() {
    if (this.d.l()) {
      if (this.d.m().is(UnitProperty.f).booleanValue())
        a("Query History"); 
      for (Connector connector : ConnectorManager.getConnectors(this.d.m().getDbId()))
        connector.transferSqlEventsTo((List)this.e.getItems()); 
    } 
    if (this.e.getItems().size() > 100)
      this.e.getItems().remove(100, this.e.getItems().size() - 1); 
    if (b())
      this.e.refresh(); 
  }
  
  private boolean b() {
    boolean bool = false;
    for (SqlEvent sqlEvent : this.e.getItems()) {
      if (sqlEvent.e > System.currentTimeMillis() - 1000L)
        bool = true; 
    } 
    return bool;
  }
}
